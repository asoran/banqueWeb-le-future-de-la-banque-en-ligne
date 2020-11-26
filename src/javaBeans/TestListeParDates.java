package javaBeans;

import gestionErreurs.TraitementException;

public class TestListeParDates {
	public static void main(String[] args) {
		try {
			var op = new BOperations();
			op.ouvrirConnexion();

			op.setNoDeCompte("E85S");
			op.setDateInf("2020-10-01");
			op.setDateSup("2020-10-03");
			op.listerParDates();
			System.out.println(op.getOperationsParDates());

			op.fermerConnexion();
		} catch (TraitementException e) {
			e.printStackTrace();
		}
	}
}
