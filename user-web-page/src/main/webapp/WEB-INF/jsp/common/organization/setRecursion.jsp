<%@page import="org.csr.common.user.constant.AuthenticationMode"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@ taglib uri="/WEB-INF/c.tld" prefix="c"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	pageContext.setAttribute("DEFAULT", AuthenticationMode.DEFAULT);
%>
<c:forEach items="${children }" var="subPoint"><div class="f-left"><c:if test="${!checkbox}">
	<input type='checkbox' path="${subPoint.path}" onclick="selectCheckBox(this)" name="functionPointIds" value='${subPoint.id }' <c:if test="${DEFAULT == subPoint.authenticationMode}">checked</c:if> /></c:if>
	<span>${subPoint.name }</span>
</div><c:if test="${fn:length(subPoint.children) > 0}"><c:set var="children" value="${subPoint.children}" scope="request" /><jsp:include page="/WEB-INF/jsp/common/role/recursion.jsp"/></c:if></c:forEach>
