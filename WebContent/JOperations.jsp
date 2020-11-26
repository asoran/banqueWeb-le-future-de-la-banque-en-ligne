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

	String noDeCompte = (String) request.getAttribute("noDeCompte");
	String nom = (String) request.getAttribute("nom");
	String prenom = (String) request.getAttribute("prenom");
	String solde = ((BigDecimal) request.getAttribute("solde")).toPlainString();
%>
<body>
	<h1>La saisie s'est bien passée</h1>
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

	<hr />

	<form action="" method="POST">
		<input type="radio" name="operation" value="+" checked> (+)
		<input type="radio" name="operation" value="-"> (-)
		<input type="text" name="valeur">
		<input type="submit" name="demande" value="Traiter">
	</form>

	<%
	if (!(CodeAffichage.equals("10") || CodeAffichage.equals("12"))) {

		if(CodeAffichage.equals("11")) {
			String op = (String) request.getAttribute("op");
			String valeur = (String) request.getAttribute("valeur");
			String ancienSolde = (String) ((BigDecimal) request.getAttribute("ancienSolde")).toPlainString();
			String nouveauSolde = (String) ((BigDecimal) request.getAttribute("nouveauSolde")).toPlainString();
			%>
			<p class="success">Opération réalisée: <%=op%> <%=valeur%> Ancien Solde: <%=ancienSolde%> Nouveau Solde: <%=nouveauSolde%></p>
			<%
		} else {
			%>
			<p class="error"><%=MessagesDErreurs.getMessageDerreur(CodeAffichage)%></p>
			<%
		}
	}
	%>
	<hr/>

	<form action="" method="POST">
		<input type="date" name="dateInf">
		<input type="date" name="dateSup">
		<input type="submit" name="demande" value="Lister">
	</form>

	<%
	if(!CodeAffichage.equals("10")) {
		if(CodeAffichage.equals("12")) {
			ArrayList<String> operationsParDates = (ArrayList<String>) request.getAttribute("operationsParDates");

			for(String op : operationsParDates) {
			%>
				<p class="success"><%=op%></p>
			<%
			}
		} else {
			%>
			<p class="error"><%=MessagesDErreurs.getMessageDerreur(CodeAffichage)%></p>
			<%
		}
	}
	%>
</body>
</html>