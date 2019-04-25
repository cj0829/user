<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="/WEB-INF/c.tld"%>
<%@ taglib prefix="cb" uri="/WEB-INF/core-taglib.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<cb:bean id="menuList" className="org.csr.common.user.support.menu.MenuLoader" methodName="loadSpaceNavigationMenus" param1="(String)500" scope="request" />
<script type="text/javascript"> 
$(function(){
	 $(".exam-nav-item").mouseover(function(){
		$(this).children("a.e-menu-a").addClass("hover");
		$(this).children(".e-menu-navbg").show();
		var bg=$(this).attr("adv-box");
	}),
	$(".exam-nav-item").mouseout(function(){
		$(this).children("a.e-menu-a").removeClass("hover");
		$(this).children(".e-menu-navbg").hide();
	});
	$(".e-menu-navthre").mouseover(function(){
		$(this).children("a.twoa").addClass("hover");
		$(this).children(".e-menu-navthrebox").show();
	}),
	$(".e-menu-navthre").mouseout(function(){
		$(this).children("a").removeClass("hover");
		$(this).children(".e-menu-navthrebox").hide();
	});
	$(".main-skin-box").mouseover(function(){
		$(this).addClass("hover");
		$(".skin-menu").show();
		$(".skin-menu li").click(function(){
			$(this).addClass("selected").siblings().removeClass("selected");
		});
	}),
	$(".main-skin-box").mouseout(function(){
		$(this).removeClass("hover");
		$(".skin-menu").hide();
	});
	//dropdown("#user","#user .users-infobox");
});

function changeStyle(style){
	jQuery.post("${cxt}/person/ajax/updateStyle.action",{skinName:style},function call(data){
		if(data.status){
			window.location.reload(true);
		}else{
			if(data.message){
				showMsg("error",data.message);
			}else{
				showMsg("error","皮肤修改失败!");
			}
		}
	},"json");
};
</script>
<%-- head开始 --%>
<div class='nav-bar'>
	${menuList}
	<%--start user --%>
	<div class='user-box fr mr20'>
		<div class='main-skin-box'>
			<div class='skin-ico'>
				<a href='javascript:;' class='imgbox' title='换肤'><em class='img'></em></a>
			</div>
			<div class='skin-menu' style='display:none;'>
				<ul>
					<c:forEach items="${skinNames}" var="skin">
						<li onclick='changeStyle(\"${skin.dictValue}\")' class='${SECURITY_CONTEXT.skinName==skin.dictValue?'selected':''} mt8'>
						<span class='${skin.remark}'></span><a href='javascript:;'>${skin.dictType}</a><em></em></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class='users' id='user'>
			<div class='img' title='${SECURITY_CONTEXT.loginName}'>
				<img
					src='${jsPath}/css/img/user_img_50.jpg' width=24 height=24 /><span
					class='name t_elli'>${SECURITY_CONTEXT.loginName}</span>
					<%-- <a href='${cxt}/person/logout.action' title='退出' class='users-logout'>退出</a> --%>
			</div>
		</div>
		<div class="tabbtn fr">
		</div>
		<a href='${cxt}/person/logout.action' title='退出' class='users-logout'>
			<%--<a class='alink' href='javascript:;'>个人简档</a>--%> <i class='icon-logout'></i>
		</a>
	</div>
</div>
