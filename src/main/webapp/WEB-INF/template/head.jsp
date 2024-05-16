
   <header class="main-header">
  <%@page import="models.Utilisateur.Role"%>
   <img class="logo" src="<%= request.getContextPath() %>/images/logo-supermarket.png">
        <nav class="icon-nav">
         	
            <input id="search-input" type="text" class="search-input" placeholder="Search..">
            <a href="#" class="icon"><i class="fas fa-shopping-cart" title="Panier"></i></a>
             <% 
			 	Role role = (Role) session.getAttribute("role");
			 if (role == null){%>
            <a href="<%= request.getContextPath() %>/connexion" class="icon"><i class="fas fa-user"  title="Connexion"></i></a>
            <% }else{ %>
            <a href="<%= request.getContextPath() %>/MagasinServlet" class="icon" ><i class="fas fa-store" title="Choisis ton magasin"></i></a>
             <a href="<%= request.getContextPath() %>" class="icon"><i class="fas fa-user"  title="Mon profil"></i></a>
             <a href="<%= request.getContextPath() %>/DeconnexionServlet" class="icon"> <i class="fas fa-sign-out-alt" title="Deconnexion"></i></a>
       		<%} %>
        </nav>
    </header>
