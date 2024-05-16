<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Connexion</title>
<link rel="stylesheet" type="text/css" href="css/connexion.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
 <h1>Connexion</h1>

    <form action="ConnexionServlet" method="post">
        <div>
        <%if(request.getAttribute("wrongMail") != null){%>
            <p id="mailNotExisted">Le mail n'existe pas</p>
            <%}%>
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div>
        <%if(request.getAttribute("wrongMdp") != null){%>
            <p id="pwdIncorrect">Le mdp est incorrect</p>
            <%}%>
            <label for="password">Mot de passe :</label>
            <input type="password" id="mdp" name="mdp" required>
        </div>
        <div>
            <button type="submit">Se connecter</button>
        </div>
    </form>

    <p>Pas encore inscrit ? <a href="inscription">Inscrivez-vous ici</a></p>
    </div>
</body>
</html>
<%@ include file="../template/footer.jsp" %>