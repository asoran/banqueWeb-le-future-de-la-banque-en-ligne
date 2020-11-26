<%@page import="java.util.Optional"%>
<%@page import="gestionErreurs.MessagesDErreurs"%>
<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title>JSaisieNoDeCompte</title>
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
	String oldNoDeCompte = (String) Optional.ofNullable(request.getAttribute("oldNoDeCompte")).orElse("");
%>
<body>

	<H1>Saisie du N° de compte:</H1>
	<form action="" method="POST">
		<label for="NoDeCompte">Entrer le N° de compte: <input
			type="text" name="NoDeCompte" value="<%=oldNoDeCompte%>">
		</label> <input type="submit" name="demande" value="Consulter">
	</form>

	<%
	if (!CodeAffichage.equals("0")) {
	%>
		<p class="error"><%=MessagesDErreurs.getMessageDerreur(CodeAffichage)%></p>
	<%
	}
	%>

</body>
</html>
