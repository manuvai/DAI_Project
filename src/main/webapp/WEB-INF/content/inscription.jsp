<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Inscription");

List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/inscription.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);

List<String> jsFiles = new ArrayList<>();
jsFiles.add("js/inscription.js");
request.setAttribute(AbstractServlet.JS_FILES_KEY, jsFiles);

%>
<%@ include file="../template/start.jsp" %>
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

<%@ include file="../template/end.jsp" %>
