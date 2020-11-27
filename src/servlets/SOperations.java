package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;
import javaBeans.BOperations;

/**
 * Servlet implementation class SOperations
 */
@WebServlet(value = "/GestionOperations/")
public class SOperations extends HttpServlet {
	private static final long serialVersionUID = -3_583_070_544_823_826_455L;

	// The data source
	private DataSource ds = null;

	@Override
	public void init() throws ServletException {
		super.init();
		ds = (DataSource) getServletContext().getAttribute("ds");
	}

	// Check if the account number is correct
	private void verifierNoCompte(String no) throws TraitementException {
		if (no.length() < 4 || no.length() > 4) {
			throw new TraitementException(MessagesDErreurs.NOT_FOUR_DIGITS);
		}
	}

	// Check if the operation value is correct
	private void verifValeur(String valeur) throws TraitementException {
		// Check if the value is empty or not
		if (valeur.isEmpty()) {
			throw new TraitementException(MessagesDErreurs.EMPTY_VALUE);
		}
		// Throw if the given string isn't a number
		try {
			Integer.parseInt(valeur);
		} catch (NumberFormatException e) {
			throw new TraitementException(MessagesDErreurs.NOT_NUMERIC_VALUE);
		}
	}

	// Check the dates
	private void verifDate(String dateInf, String dateSup) throws TraitementException {
		if (dateInf.isBlank() || dateSup.isBlank()) {
			throw new TraitementException(MessagesDErreurs.DATE_EMPTY);
		}

		// If dateInf > dateSup, throws exception
		// Because the format is year-month-date, this is ok
		if (dateInf.compareTo(dateSup) > 0) {
			throw new TraitementException(MessagesDErreurs.DATE_SUPP_INF_ERROR);
		}
	}

	private void consultation(HttpServletRequest request) throws TraitementException {
		// Create the BOperation object and store it in the session
		var noDeCompte = request.getParameter("NoDeCompte");
		var operation = new BOperations();
		operation.setNoDeCompte(noDeCompte);
		request.getSession().setAttribute("op", operation);

		// Check if noDeCompte is valid
		verifierNoCompte(noDeCompte);

		operation.ouvrirConnexion(ds);
		try {
			operation.consulter();
			request.getSession().setAttribute("op", operation);
		} finally {
			operation.fermerConnexion();
		}
	}

	private void traiter(HttpServletRequest request) throws TraitementException, IOException {
		var op = (BOperations) request.getSession().getAttribute("op");

		var operation = (String) request.getParameter("operation");
		var valeur = (String) request.getParameter("valeur");

		// Check the value
		verifValeur(valeur);

		op.ouvrirConnexion(ds);

		try {
			op.setOp(operation);
			op.setValeur(valeur);
			op.traiter();

			request.getSession().setAttribute("op", op);
		} finally {
			op.fermerConnexion();
		}
	}

	private void lister(HttpServletRequest request, HttpServletResponse response)
			throws TraitementException, IOException {
		var op = (BOperations) request.getSession().getAttribute("op");

		var dateInf = (String) request.getParameter("dateInf");
		var dateSup = (String) request.getParameter("dateSup");
		// Check if the date are correct
		verifDate(dateInf, dateSup);

		op.ouvrirConnexion(ds);
		try {
			op.setDateInf(dateInf);
			op.setDateSup(dateSup);
			op.listerParDates();

			if (op.getOperationsParDates().isEmpty()) {
				throw new TraitementException(MessagesDErreurs.NO_OP_THAT_DATE);
			}
			request.getSession().setAttribute("op", op);
		} finally {
			op.fermerConnexion();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Forward the request to doPost
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Tell the response we are sending HTML
		response.setContentType("text/html");

		// Check the parameter demande, if null then it means it's the first connection
		// so redirect to the first page
		var action = (String) request.getParameter("demande");
		if (action == null) {
			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_0);
			request.getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
			return;
		}

		// Now switch the action to see how the user got to this page
		switch (action) {
		// Stop treating current customer, go back to main page
		case "Fin de traitement": {
			// Remove operation
			request.getSession().removeAttribute("op");
			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_0);
			request.getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
			break;
		}
		// Consult a new client
		case "Consulter": {
			try {
				consultation(request);
				// Show the operation page with code = 10
				request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_10);
				request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
			} catch (TraitementException e) {
				// Show the main page with an error
				request.setAttribute("CodeAffichage", e.getMessage());
				request.getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
			}
			break;
		}
		// Treat a new operation
		case "Traiter": {
			try {
				traiter(request);
				request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_11);
				request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
			} catch (TraitementException e) {
				request.setAttribute("CodeAffichage", e.getMessage());
				request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}
			break;
		}
		// List the operations by dates
		case "Lister": {
			try {
				lister(request, response);
				request.getRequestDispatcher("/JListeOperations.jsp").forward(request, response);
			} catch (TraitementException e) {
				request.setAttribute("CodeAffichage", e.getMessage());
				request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}
			break;
		}
		case "Retour": {
			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_12);
			request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
			break;
		}
		case "Deconnexion": {
			request.getSession().invalidate();
			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_0);
			request.getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
			break;
		}
		default:
			response.setStatus(500);
		}

		return;
	}

}
