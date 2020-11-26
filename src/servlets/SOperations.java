package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gestionErreurs.MessagesDErreurs;
import gestionErreurs.TraitementException;
import javaBeans.BOperations;

/**
 * Servlet implementation class SOperations
 */
@WebServlet(value = "/GestionOperations/")
public class SOperations extends HttpServlet {
	private static final long serialVersionUID = -3_583_070_544_823_826_455L;

	private void verifierNoCompte(String no) throws TraitementException {
		if (no.length() < 4 || no.length() > 4) {
			throw new TraitementException(MessagesDErreurs.NOT_FOUR_DIGITS);
		}
		// oui
		// non
	}

	private String noDeCompte;
	private BOperations operation;

	private void consultation(HttpServletRequest request, HttpServletResponse response)
			throws TraitementException, IOException {
		noDeCompte = request.getParameter("NoDeCompte");
		verifierNoCompte(noDeCompte);

		operation = new BOperations();
		operation.ouvrirConnexion();

		operation.setNoDeCompte(noDeCompte);
		operation.consulter();

		operation.fermerConnexion();
	}

	private void verifValeur(String valeur) throws TraitementException {
		if (valeur.isEmpty()) {
			throw new TraitementException(MessagesDErreurs.EMPTY_VALUE);
		}
		try {
			Integer.parseInt(valeur);
		} catch (NumberFormatException e) {
			throw new TraitementException(MessagesDErreurs.NOT_NUMERIC_VALUE);
		}
	}

	private void traiter(HttpServletRequest request, HttpServletResponse response)
			throws TraitementException, IOException {
		var op = (String) request.getParameter("operation");
		var valeur = (String) request.getParameter("valeur");
		verifValeur(valeur);

		operation.ouvrirConnexion();

		operation.setOp(op);
		operation.setValeur(valeur);
		operation.traiter();

		operation.fermerConnexion();
	}

	private void verifDate(String dateInf, String dateSup) throws TraitementException {
		// TODO
	}

	private void lister(HttpServletRequest request, HttpServletResponse response)
			throws TraitementException, IOException {
		var dateInf = (String) request.getParameter("dateInf");
		var dateSup = (String) request.getParameter("dateSup");
		verifDate(dateInf, dateSup);

		operation.ouvrirConnexion();

		operation.setDateInf(dateInf);
		operation.setDateSup(dateSup);
		operation.listerParDates();

		operation.fermerConnexion();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ça marche po tarace la tepu");
		response.setContentType("text/html");
		request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_0);
		request.getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		var action = (String) request.getParameter("demande");
		if ("Consulter".equals(action)) {
			try {
				consultation(request, response);
			} catch (TraitementException e) {
				request.setAttribute("CodeAffichage", e.getMessage());
				request.setAttribute("oldNoDeCompte", noDeCompte);
				request.getRequestDispatcher("/JSaisieNoDeCompte.jsp").forward(request, response);
				return;
			}

			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_10);
			request.setAttribute("noDeCompte", noDeCompte);
			request.setAttribute("nom", operation.getNom());
			request.setAttribute("prenom", operation.getPrenom());
			request.setAttribute("solde", operation.getSolde());
			request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
		} else if ("Traiter".equals(action)) {

			request.setAttribute("noDeCompte", noDeCompte);
			request.setAttribute("nom", operation.getNom());
			request.setAttribute("prenom", operation.getPrenom());
			request.setAttribute("solde", operation.getSolde());

			try {
				traiter(request, response);
				request.setAttribute("solde", operation.getSolde());
			} catch (TraitementException e) {
				request.setAttribute("CodeAffichage", e.getMessage());
				request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}

			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_11);
			request.setAttribute("op", operation.getOp());
			request.setAttribute("valeur", operation.getValeur());
			request.setAttribute("ancienSolde", operation.getAncienSolde());
			request.setAttribute("nouveauSolde", operation.getNouveauSolde());
			request.getRequestDispatcher("/JOperations.jsp").forward(request, response);

		} else if ("Lister".equals(action)) {
			request.setAttribute("noDeCompte", noDeCompte);
			request.setAttribute("nom", operation.getNom());
			request.setAttribute("prenom", operation.getPrenom());
			request.setAttribute("solde", operation.getSolde());

			try {
				lister(request, response);
			} catch (TraitementException e) {
				request.setAttribute("CodeAffichage", e.getMessage());
				request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
				return;
			}

			request.setAttribute("CodeAffichage", MessagesDErreurs.RESERVED_12);
			request.setAttribute("operationsParDates", operation.getOperationsParDates());
			request.getRequestDispatcher("/JOperations.jsp").forward(request, response);
		} else {
			response.setStatus(500);
		}
	}

}
