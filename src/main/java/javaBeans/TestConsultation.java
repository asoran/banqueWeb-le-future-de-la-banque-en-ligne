package javaBeans;

import gestionErreurs.TraitementException;

public class TestConsultation {

    public static void main(String[] args) {
        try {
            var op = new BOperations();
//            op.ouvrirConnexion();

            op.setNoDeCompte("X04E");
            op.consulter();
            System.out.println(op);

            op.fermerConnexion();
        } catch (TraitementException e) {
            e.printStackTrace();
        }
    }
}
