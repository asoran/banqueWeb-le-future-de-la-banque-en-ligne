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
</style>
</head>
<body>
	<p class="error">
		Identifiant de l'utilisateur ou mot de passe invalide. <br>
		Veuiller renouvler la saisie :
	</p>

	<%@include file="JLogin.jsp"%>

</body>
</html>
