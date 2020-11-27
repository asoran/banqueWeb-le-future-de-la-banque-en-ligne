<%@page import="javaBeans.BOperations"%>
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
</style>
</head>
<%
	String CodeAffichage = (String) request.getAttribute("CodeAffichage");
	String noDeCompte = "";
	if(!CodeAffichage.equals("0")) {
		BOperations op = (BOperations) request.getSession().getAttribute("op");
		noDeCompte = op.getNoDeCompte();
	}
%>
<body>

	<H1>Saisie du N° de compte:</H1>
	<form action="" method="POST">
		<label for="NoDeCompte">Entrer le N° de compte: <input
			type="text" name="NoDeCompte" value="<%= noDeCompte %>">
		</label> <input type="submit" name="demande" value="Consulter">
	</form>

	<br/>
	<form action="" method="POST">
		<input type="submit" name="demande" value="Deconnexion">
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
