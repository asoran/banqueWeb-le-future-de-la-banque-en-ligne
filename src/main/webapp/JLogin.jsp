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

	<form method="POST" action="j_security_check">
	    <label for="j_username">Identifiant de l'utilisateur :
		    <input type="text" name="j_username">
		</label>
		<br>
		<label for="j_password"> Mot de passe :
		    <input type="password" name="j_password">
        </label>
		<input type="submit" name="demande" value="Authentifiez vous">
	</form>

</body>
</html>