<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
 <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%> 
var styleObject = document.createElement("LINK");
styleObject.rel = "stylesheet";
styleObject.type = "text/css";
styleObject.href = '<%=basePath%>/css/bbs/style.css';

var scriptObject = document.createElement("script");
scriptObject.type = "text/javascript";
scriptObject.href = '<%=basePath%>/js/bbs/jquery.min.js';

document.getElementsByTagName("HEAD")[0].appendChild(styleObject);
document.getElementsByTagName("HEAD")[0].appendChild(scriptObject);  

var bannerStr='<div class="app_imgplay"><div class="app_banner_index">';
bannerStr+='<span href="javascript:void(0);" class="btn2 btnPre" id="banner_index_pre"></span>';
bannerStr+='<span href="javascript:void(0);" class="btn2 btnNext" id="banner_index_next"></span>';
bannerStr+='<ul class="banner_wrap">';
bannerStr+='<li><a><img src="${cxt}/images/${code}/banner_01.jpg"/></a></li>';
bannerStr+='<li><a><img src="${cxt}/images/${code}/banner_02.jpg"/></a></li>';
bannerStr+='<li><a><img src="${cxt}/images/${code}/banner_03.jpg"/></a></li>'; 
bannerStr+='<li><a><img src="${cxt}/images/${code}/banner_04.jpg"/></a></li>';
bannerStr+='<li><a><img src="${cxt}/images/${code}/banner_05.jpg"/></a></li>';
bannerStr+='</ul></div></div>';

var head_user_info='<div class="top_menu_container"><div class="head_user_info">';
head_user_info+='<a href="<%=basePath%>/login.jsp">登录</a>';
<c:if test="${sessionScope.SECURITY_CONTEXT.loginName!=null}">
   head_user_info+='欢迎<a href="${cxt}/person/preCenter.action">${sessionScope.SECURITY_CONTEXT.loginName }</a>|在线用户数：|<a href="<%=basePath%>/person!logout.action">退出</a>';
</c:if>
head_user_info+='</div></div>';
document.write("<div id='head_user_info'>"+bannerStr+"</div>");
document.write("<div id='banner_index'>"+head_user_info+"</div>");
