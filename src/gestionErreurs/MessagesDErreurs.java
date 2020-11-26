package gestionErreurs;

import java.util.HashMap;

public class MessagesDErreurs {
	private static final HashMap<String, String> messages = new HashMap<>();
	public static final String ACCESS_ERROR = "3";
	public static final String CONNECTION_ERROR = "21";
	public static final String CLOSE_ERROR = "22";
	public static final String NEGATIV_DEBIT_ERROR = "24";
	public static final String NOT_FOUR_DIGITS = "5";
	public static final String EMPTY_VALUE = "26";
	public static final String NOT_NUMERIC_VALUE = "25";

	public static final String RESERVED_0 = "0";
	public static final String RESERVED_10 = "10";
	public static final String RESERVED_11 = "11";
	public static final String RESERVED_12 = "12";

	
	static {
		messages.put(RESERVED_0, "RESERVED NUMBER");
		messages.put(RESERVED_10, "RESERVED NUMBER");
		messages.put(RESERVED_11, "RESERVED NUMBER");
		messages.put(RESERVED_12, "RESERVED NUMBER");

		messages.put(EMPTY_VALUE, "Aucune valeur n'a �t� saisie");
		messages.put(NOT_NUMERIC_VALUE, "La valeur doit �tre num�rique");
		messages.put(NOT_FOUR_DIGITS, "Le N� de compte doit contenir 4 caract�res");
		messages.put(ACCESS_ERROR, "Problème pour accéder à ce compte client, vérifiez qu'il est bien valide");
		messages.put(CONNECTION_ERROR, "Problème d’accès à la base de données, veuillez le signaler à votre administrateur");
		messages.put(CLOSE_ERROR, "Problème après traitement. Le traitement a été effectué correctement mais il y a eu un problème à signaler à votre administrateur");
		messages.put(NEGATIV_DEBIT_ERROR, "Opération refusée, débit demandé supérieur au crédit du compte");
	}

	public static String getMessageDerreur(String key) throws NumberFormatException {
		return messages.get(key);
	}
}
