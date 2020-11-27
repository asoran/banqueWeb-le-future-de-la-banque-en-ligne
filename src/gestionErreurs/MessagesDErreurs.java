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
	public static final String DATE_SUPP_INF_ERROR = "31";
	public static final String NO_OP_THAT_DATE = "32";
	public static final String DATE_EMPTY = "33";

	public static final String RESERVED_0 = "0";
	public static final String RESERVED_10 = "10";
	public static final String RESERVED_11 = "11";
	public static final String RESERVED_12 = "12";

	static {
		// Main page first access
		messages.put(RESERVED_0, "RESERVED NUMBER");
		// JOperations first access
		messages.put(RESERVED_10, "RESERVED NUMBER");
		// A new operation passed successfully
		messages.put(RESERVED_11, "RESERVED NUMBER");
		// List operations passed successfully
		messages.put(RESERVED_12, "RESERVED NUMBER");

		messages.put(EMPTY_VALUE, "Aucune valeur n'a �t� saisie");
		messages.put(NOT_NUMERIC_VALUE, "La valeur doit �tre num�rique");
		messages.put(NOT_FOUR_DIGITS, "Le N� de compte doit contenir 4 caract�res");
		messages.put(ACCESS_ERROR, "Probl�me pour acc�der � ce compte client, v�rifiez qu'il est bien valide");
		messages.put(CONNECTION_ERROR,
				"Probl�me d�acc�s � la base de donn�es, veuillez le signaler � votre administrateur");
		messages.put(CLOSE_ERROR,
				"Probl�me apr�s traitement. Le traitement a �t� effectu� correctement mais il y a eu un probl�me � signaler � votre administrateur");
		messages.put(NEGATIV_DEBIT_ERROR, "Op�ration refus�e, d�bit demand� sup�rieur au cr�dit du compte");
		messages.put(DATE_SUPP_INF_ERROR, "La date initial doit �tre inf�rieur � la date finale");
		messages.put(NO_OP_THAT_DATE, "Il n'y a eu aucune op�ration durant cette p�riode");
		messages.put(DATE_EMPTY, "Veuillez remplir les champs de dates");
	}

	public static String getMessageDerreur(String key) throws NumberFormatException {
		return messages.get(key);
	}
}
