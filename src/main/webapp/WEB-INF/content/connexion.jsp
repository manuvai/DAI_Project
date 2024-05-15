<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
<link rel="stylesheet" type="text/css" href="css/connexion.css">
</head>
<body>
 <h1>Connexion</h1>

    <form action="ConnexionServlet" method="post">
        <div>
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <button type="submit">Se connecter</button>
        </div>
    </form>

    <p>Pas encore inscrit ? <a href="inscription">Inscrivez-vous ici</a></p>
</body>
</html>