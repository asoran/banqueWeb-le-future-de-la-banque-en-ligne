<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF8">
<title>JLogin</title>
</head>
<body>

	<form method="POST" action="j_security_check">
		<label for="j_username">Identifiant de l'utilisateur : <input
			type="text" name="j_username">
		</label> <br> <label for="j_password"> Mot de passe : <input
			type="password" name="j_password">
		</label> <input type="submit" name="demande" value="Authentifiez vous">
	</form>
</body>
</html>