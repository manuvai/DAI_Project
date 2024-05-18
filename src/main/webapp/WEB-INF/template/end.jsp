<%@page import="servlets.AbstractServlet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
</div>
<%@include file="footer.jsp" %>
<%
List<String> _jsLibs = (List<String>) request.getAttribute(AbstractServlet.JS_LIBS_KEY);

_jsLibs = _jsLibs == null
    ? new ArrayList<>()
    : _jsLibs;
    
_jsLibs.add("https://code.jquery.com/jquery-3.5.1.slim.min.js");
_jsLibs.add("https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js");

for (String jsLib : _jsLibs) {
%>
<script src="<%= jsLib %>"></script>
<%
}
%>
<%
List<String> _jsFiles = (List<String>) request.getAttribute(AbstractServlet.JS_FILES_KEY);

_jsFiles = _jsFiles == null
    ? new ArrayList<>()
    : _jsFiles;

for (String jsFile : _jsFiles) {
%>
<script src="<%= request.getContextPath() + "/" + jsFile %>"></script>
<%
}
%>
</body>
</html>