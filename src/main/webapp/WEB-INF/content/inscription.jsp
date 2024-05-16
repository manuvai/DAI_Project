<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
<link rel="stylesheet" type="text/css" href="css/inscription.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
<div class="container">
        <h1>Inscription</h1>

        <form action="InscriptionServlet" method="post">
            <div>
                <label for="nom">Nom :</label>
                <input type="text" id="nom" name="nom" required>
            </div>
            <div>
                <label for="prenom">Prénom :</label>
                <input type="text" id="prenom" name="prenom" required>
            </div>
            <div>
            <%if(request.getAttribute("alreadyExist") != null){%>
            <p id="mailExisted">Le mail existe déjà</p>
            <%}%>
                <label for="email">Email :</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div>
                <label for="confirmEmail">Confirmation Email :</label>
                <input type="email" id="confirmEmail" name="confirmEmail" required>
            </div>
            <div>
                <label for="mdp">Mot de passe :</label>
                <input type="password" id="mdp" name="mdp" required>
            </div>
            <div>
                <label for="confirmMdp">Confirmation Mot de passe :</label>
                <input type="password" id="confirmMdp" name="confirmMdp" required>
            </div>
            <div>
            <p id="messageInformatif"></p>
                <button type="submit" disabled id="submitButton">Valider</button>
            </div>
        </form>

        <p>Déjà inscrit ? <a href="connexion">Connectez-vous ici</a></p>
    </div>
    <script src="js/inscription.js"></script>
</body>
</html>
<%@ include file="../template/footer.jsp" %>