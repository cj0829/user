<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" import="java.text.SimpleDateFormat,java.util.Date"%><%@ taglib prefix="c" uri="/WEB-INF/c.tld" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib prefix="user" uri="/user-tags" %><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %><%@ taglib prefix="core" uri="/core-tags" %><link href="${jsPath}/themes/default/easyui.css" rel="stylesheet" type="text/css" /> 
<link type="text/css" href="${jsPath}/css/user/pure-fresh/style.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandCalendar.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandForm.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandGrid.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandPagination.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandWindow.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandTree.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandWinGrid.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandTooltip.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/userSpecialFunctionPoint.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandMenu.css" rel="stylesheet" />
<link type="text/css" href="${jsPath}/css/user/pure-fresh/expandTabs.css" rel="stylesheet" />
<script type="text/javascript" src="${jsPath}/dictionary/situationDictionary.action"></script>
<script src="${jsPath}/js/jquery.min.js" type="text/javascript"></script>
<script src="${jsPath}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${jsPath}/js/jquery.easyui.js"></script>
<script type="text/javascript" src="${jsPath}/js/jquery.extend.validatebox.js"></script>
<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
<%
	request.setAttribute("init_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
%>