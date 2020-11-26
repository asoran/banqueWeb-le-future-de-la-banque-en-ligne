package javaBeans;

import gestionErreurs.TraitementException;

public class TestTraitement {
	public static void main(String[] args) {
		try {
			var op = new BOperations();
//			op.ouvrirConnexion();

			op.setNoDeCompte("E85S");
			op.consulter();
			System.out.println(op);

			op.setOp("+");
			op.setValeur(Integer.toString(20));
			op.traiter();

			op.consulter();
			System.out.println(op);

			op.fermerConnexion();
		} catch (TraitementException e) {
			e.printStackTrace();
		}
	}
}
