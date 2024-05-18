<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("pageTitle", "Connexion");
request.setAttribute("isHeaderDisabled", true);

List<String> cssFiles = new ArrayList<>();
cssFiles.add("css/connexion.css");
request.setAttribute(AbstractServlet.CSS_FILES_KEY, cssFiles);
%>
<%@ include file="../template/start.jsp" %>
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
<%@ include file="../template/end.jsp" %>
