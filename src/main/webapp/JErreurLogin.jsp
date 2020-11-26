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

<body>
    <p class="error">
        Identifiant de l'utilisateur ou mot de passe invalide. <br>
        Veuiller renouvler la saisie :
    </p>

	<%@include file="JLogin.jsp" %>

</body>
</html>