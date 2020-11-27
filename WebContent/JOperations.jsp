<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="javaBeans.BOperations"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="gestionErreurs.MessagesDErreurs"%>
<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title>JOperations</title>
<style>
	.error {
		color: red;
	}
	.success {
		color: blue;
	}
	table {
		border: 1px solid black;
	}
	td, td {
		border: 1px solid black;
	}
	th {
		background-color: rgb(100, 100, 100);
	}
</style>
</head>
<%
	String CodeAffichage = (String) request.getAttribute("CodeAffichage");
	BOperations op = (BOperations) request.getSession().getAttribute("op");

	String noDeCompte = op.getNoDeCompte();
	String nom = op.getNom();
	String prenom = op.getPrenom();
	String solde = op.getSolde().toPlainString();

	String dateInf = "";
	String dateSup = "";
%>
<body>
	<h1>Consulation du client <%=noDeCompte%></h1>
	<table>
		<thead>
			<tr>
				<th>N° de compte</th>
				<th>Nom</th>
				<th>Prénom</th>
				<th>Solde</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><%=noDeCompte%></td>
				<td><%=nom%></td>
				<td><%=prenom%></td>
				<td><b><%=solde%></b></td>
			</tr>
		</tbody>
	</table>

	<%
    if ( request.isUserInRole("ecriture") ) {
    	String value = "";
    	if(!(CodeAffichage.equals(MessagesDErreurs.RESERVED_10) || CodeAffichage.equals(MessagesDErreurs.RESERVED_11))) {
    		value = op.getValeur();
    	}
	%>
	<hr />
	<form action="" method="POST">
		<input type="radio" name="operation" value="+" checked> (+)
		<input type="radio" name="operation" value="-"> (-)
		<input type="text" name="valeur" value="">
		<input type="submit" name="demande" value="Traiter">
	</form>

	<%
		if(CodeAffichage.equals(MessagesDErreurs.RESERVED_11)) {
			String operation = op.getOp();
			String valeur = op.getValeur();
			String ancienSolde = op.getAncienSolde().toPlainString();
			String nouveauSolde = op.getNouveauSolde().toPlainString();
			%>
			<p class="success">Opération réalisée: <%=operation%> <%=valeur%> Ancien Solde: <%=ancienSolde%> Nouveau Solde: <%=nouveauSolde%></p>
			<%
		}
	}
	%>

	<%
		if(CodeAffichage.equals(MessagesDErreurs.RESERVED_10)) {
			String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			dateInf = today;
			dateSup = today;
		} else {
			dateInf = op.getDateInf();
			dateSup = op.getDateSup();
		}
	%>
	<form action="" method="POST">
		<input type="date" name="dateInf" value="<%=dateInf%>">
		<input type="date" name="dateSup" value="<%=dateSup%>">
		<input type="submit" name="demande" value="Lister">
	</form>

	<%
	// If not 10 or 11 or 12, display error
		if (!(
			CodeAffichage.equals(MessagesDErreurs.RESERVED_10)
			|| CodeAffichage.equals(MessagesDErreurs.RESERVED_11)
			|| CodeAffichage.equals(MessagesDErreurs.RESERVED_12)
		)) {
	%>
		<p class="error"><%=MessagesDErreurs.getMessageDerreur(CodeAffichage)%></p>	
	<%
		}	
	%>

	<hr/>

	<form action="" method="POST">
		<input type="submit" name="demande" value="Fin de traitement">
	</form>
	
</body>
</html>