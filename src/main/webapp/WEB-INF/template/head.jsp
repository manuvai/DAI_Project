
   <header class="main-header">
  <%@page import="models.Utilisateur.Role"%>
   <a href="<%= request.getContextPath() %>/home" ><img class="logo" src="<%= request.getContextPath() %>/images/logo-supermarket.png"></a>
        <nav class="icon-nav">
         	<a href="#" class="icon" ><i class="fas fa-store" title="Choisis ton magasin"></i></a>
            <input id="search-input" type="text" class="search-input" placeholder="Search..">
            <a href="<%= request.getContextPath() %>/panier" class="icon"><i class="fas fa-shopping-cart" title="Panier"></i></a>
            <p id="nbrPanier">
            <% 
            Integer nbrArticleTotal = (Integer) session.getAttribute("nbrArticleTotal");
			 if (nbrArticleTotal != null ){%>
			<%= nbrArticleTotal %>
			 <%} else {%>
			 0
				 <% }%>
            </p>
             <% 
			 	Role role = (Role) session.getAttribute("role");
			 if (role == null){%>
            <a href="<%= request.getContextPath() %>/connexion" class="icon"><i class="fas fa-user"  title="Connexion"></i></a>
            <% }else{ %>
             <a href="<%= request.getContextPath() %>" class="icon"><i class="fas fa-user"  title="Mon profil"></i></a>
             <a href="<%= request.getContextPath() %>/DeconnexionServlet" class="icon"> <i class="fas fa-sign-out-alt" title="Deconnexion"></i></a>
       		<%} %>
        </nav>
    </header>
