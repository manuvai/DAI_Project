
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// =============================
// GESTION DE LA PAGE
// =============================

/*
	Cette partie définit le nom de la page dans le html.head.title
*/
request.setAttribute("pageTitle", "Page exemple");

/*
	Cette partie définit si l'en-tête de la page doit être affichée ou non
*/
request.setAttribute("isHeaderDisabled", false||true);

// =============================
// GESTION DES LIBRAIRIES JS
// =============================
List<String> jsLibs = (List<String>) request.getAttribute(AbstractServlet.JS_LIBS_KEY);

jsLibs = jsLibs == null ? new ArrayList<>() : jsLibs;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		jsLibs.add("https://example.com/example.js");
*/

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsLibs);

// =============================
// GESTION DES FICHIERS JS
// =============================
List<String> jsFiles = (List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY);

jsFiles = jsFiles == null ? new ArrayList<>() : jsFiles;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		jsFiles.add("js/example.js");
*/

request.setAttribute(AbstractServlet.JS_LIBS_KEY, jsFiles);

// =============================
// GESTION DES LIBRAIRIES CSS
// =============================
List<String> cssLibs = (List<String>) request.getAttribute(AbstractServlet.CSS_LIBS_KEY);

cssLibs = cssLibs == null ? new ArrayList<>() : cssLibs;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		cssLibs.add("https://cdn.com/example.css");
*/

request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssLibs);

// =============================
// GESTION DES FICHIERS CSS
// =============================
List<String> cssFiles = (List<String>) request.getAttribute(AbstractServlet.CSS_FILES_KEY);

cssFiles = cssFiles == null ? new ArrayList<>() : cssFiles;
/*
	Cette partie traite l'ajout des CDN des librairies JS
	Pour ajouter une nouvelle librairie :
		cssFiles.add("css/example.css");
*/

request.setAttribute(AbstractServlet.CSS_LIBS_KEY, cssFiles);

%>
<%@ include file="../template/start.jsp" %>

<h1>Hello</h1>

<div class="row">
	Lorem ipsum dolor sit amet consectetur adipisicing elit. In voluptas quisquam impedit molestiae iste
	dignissimos maiores voluptates, accusamus veniam quaerat possimus. Distinctio voluptas et repellat 
	incidunt, aspernatur est modi ipsa.
</div>

<%@ include file="../template/end.jsp" %>
