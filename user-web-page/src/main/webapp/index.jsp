<%@ page language="java"  session="false" %>
<%
final String queryString = request.getQueryString();
final String url = request.getContextPath() + "/attendance/attendSign/preUserStatistics.action" + (queryString != null ? "?" + queryString : "");
response.sendRedirect(response.encodeURL(url));%>