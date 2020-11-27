package javaBeans;

import static gestionErreurs.MessagesDErreurs.ACCESS_ERROR;
import static gestionErreurs.MessagesDErreurs.CLOSE_ERROR;
import static gestionErreurs.MessagesDErreurs.CONNECTION_ERROR;
import static gestionErreurs.MessagesDErreurs.NEGATIV_DEBIT_ERROR;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.sql.DataSource;

import gestionErreurs.TraitementException;

public class BOperations {
	private String noDeCompte;
	private String nom;
	private String prenom;
	private BigDecimal solde;

	public String getNoDeCompte() {
		return noDeCompte;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public BigDecimal getSolde() {
		return solde;
	}

	public void setNoDeCompte(String noDeCompte) {
		this.noDeCompte = noDeCompte;
	}

	private Connection connection = null;
	private Statement statement = null;

	public void ouvrirConnexion(DataSource ds) throws TraitementException {
		try {
			connection = ds.getConnection();
			connection.setAutoCommit(false);
			statement = connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TraitementException(CONNECTION_ERROR);
		}
	}

	public void fermerConnexion() throws TraitementException {
		try {
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TraitementException(CLOSE_ERROR);
		}
	}

	public void consulter() throws TraitementException {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT * FROM COMPTE where NOCOMPTE=\"" + noDeCompte + "\";");

			if (!rs.next()) {
				throw new TraitementException(ACCESS_ERROR);
			}

			nom = rs.getString("NOM");
			prenom = rs.getString("PRENOM");
			solde = rs.getBigDecimal("SOLDE");

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TraitementException(ACCESS_ERROR);
		}
	}

	private BigDecimal ancienSolde;
	private BigDecimal nouveauSolde;
	private BigDecimal valeur = BigDecimal.ZERO;
	private String op;

	public void setValeur(String valeur) {
		this.valeur = new BigDecimal(valeur);
	}

	public void setOp(String op) {
		this.op = op;
	}

	public BigDecimal getAncienSolde() {
		return ancienSolde;
	}

	public BigDecimal getNouveauSolde() {
		return nouveauSolde;
	}

	public String getValeur() {
		return valeur.setScale(2).toString();
	}

	public String getOp() {
		return op;
	}

	public void traiter() throws TraitementException {
		ResultSet rs = null;
		try {
			rs = statement.executeQuery("SELECT * FROM compte where NOCOMPTE=\"" + noDeCompte + "\";");
			rs.next();

			ancienSolde = rs.getBigDecimal("solde");
			switch (op) {
			case "+":
				nouveauSolde = ancienSolde.add(valeur);
				break;
			case "-":
				nouveauSolde = ancienSolde.subtract(valeur);
				break;
			default:
				throw new UnsupportedOperationException(op);
			}

			if (nouveauSolde.compareTo(BigDecimal.ZERO) < 0) {
				throw new TraitementException(NEGATIV_DEBIT_ERROR);
			} else {
				// solde = nouveauSolde;
				statement
						.execute("UPDATE compte SET SOLDE=" + nouveauSolde + " WHERE NOCOMPTE=\"" + noDeCompte + "\";");
				String sql = "INSERT INTO operation values (" + "\"" + noDeCompte + "\"," + "CURDATE()," + "CURTIME(),"
						+ "\"" + op + "\"," + valeur + ");";
				// System.out.println(sql);
				statement.execute(sql);
				connection.commit();
				this.solde = nouveauSolde;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException throwables) {
				System.exit(-1);
			}
		}
	}

	private String dateInf;
	private String dateSup;

	public void setDateInf(String dateInf) {
		this.dateInf = dateInf;
	}

	public void setDateSup(String dateSup) {
		this.dateSup = dateSup;
	}

	private ArrayList<String> operationsParDates;

	public String getDateInf() {
		return dateInf;
	}

	public String getDateSup() {
		return dateSup;
	}

	public ArrayList<String> getOperationsParDates() {
		return operationsParDates;
	}

	public void listerParDates() {
		operationsParDates = new ArrayList<>();
		try {
			var res = statement.executeQuery("SELECT * FROM operation where NOCOMPTE=\"" + noDeCompte + "\" "
					+ "AND DATE >= '" + dateInf + "' AND DATE <= '" + dateSup + "'" + ";");
			while (res.next()) {
				operationsParDates.add("{" + res.getDate("date").toString() + ", " + res.getTime("heure").toString()
						+ ", " + res.getString("op") + ", " + res.getBigDecimal("valeur").toString() + "}");
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "BOperations{" + "noDeCompte='" + noDeCompte + '\'' + ", nom='" + nom + '\'' + ", prenom='" + prenom
				+ '\'' + ", solde=" + solde + '}';
	}

}
