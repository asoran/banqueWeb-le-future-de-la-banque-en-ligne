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
<title>JListeOperations</title>
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
%>
<body>

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
		ArrayList<String> operationsParDates = op.getOperationsParDates();
		// La fonction renvoie une string et pas un objet operation car on en a pas ...
		// Je crois pas qu'on nous a demandé d'en créer dans le sujet donc on l'a pas fait
		// On affiche le toString a la place d'un beau tableau du coup :(
		for(String o : operationsParDates) {
		%>
			<p class="success"><%=op%></p>
		<%
		}
	%>

	<form action="" method="POST">
		<input type="submit" name="demande" value="Retour">
	</form>

</body>
</html>