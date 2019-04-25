<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<link type="text/css" href="${cxt}/css/user/dark-transparent/userSpecialFunctionPoint.css" rel="stylesheet" />
 	<title>新建${userTitle}--${system_name}</title>
</head>
<body>
	<!--内容开始-->
	<form action="#" name="roleFunctionPointForm" id="roleFunctionPointForm" onsubmit="return saveRoleFunctionPoint();">
	<div class="e-tabbox main-expand-form">
	<div class="main-ordinary-tab">
	<table width="100%"><c:forEach items="${functionPointList }" var="topfuns"><tr id="roleList" sort="false" >
		<th width="165px" >${topfuns.name }模块</th>
		<th>功能点</th>
	</tr><c:forEach items="${topfuns.children }" var="functionPoint">
	<tr><td style="text-align:center" class="td_1">${functionPoint.name }<input type='checkbox' style="margin-left:10px;margin-right:20px;" onclick='selectAll(this, "code_${functionPoint.id }", "checkbox")' /></td>
	<td id="code_${functionPoint.id }"><c:forEach items="${functionPoint.children }" var="subPoint">
<div class="f-left">
	<input type='checkbox' path="${subPoint.path}" onclick="selectCheckBox(this)"  name="functionPointIds" value='${subPoint.id }' <c:forEach items="${roleFunctionPointList }" var="functionPointId"><c:if test="${functionPointId == subPoint.id}">checked</c:if></c:forEach> />
	<span>${subPoint.name }</span>
</div><c:if test="${fn:length(subPoint.children) > 0}"><c:set var="children" value="${subPoint.children}" scope="request" /><jsp:include page="/WEB-INF/jsp/common/role/recursion.jsp"/></c:if></c:forEach></td></tr></c:forEach></c:forEach>
	<tr>
		<td style="text-align:center">全选</td>
		<td><input type='checkbox' style="margin-left:20px;" onclick='selectAll(this, "", "checkbox")' /></td>
	</tr>
	</table>
	</div>
	<input type="hidden" id="roleId" name="roleId" value="${roleId}"/>
	<input type="hidden" id="functionPointIds" name="functionPointIds" value=""/>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form> 
<script  type="text/javascript"> 
window.onload=function(){
	$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
};

jQuery(document).ready(function(){
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	});
});
	//保存选中的数组
	var checkin = new Array();
	function selectAll(checker, scope, type){ 
	    if(scope){
			jQuery('#' + scope + ' input').each(function(){
				this.checked=checker.checked;
			});
	    }else{
            jQuery('input').each(function(){
            	 this.checked=checker.checked;
            });
	    }
	}
	function selectCheckBox(checker){
		var check=jQuery(checker);
		var search=check.attr("search");
		var path=check.attr("path");
		var pathids=path.split("/");
		if(checker.checked && search){
			return;
		}
		check.closest("td").find("div>input").each(function(){
			var lpath=jQuery(this).attr("path");
			var lpathids=lpath.split("/");
			//长度小于，或者
			if(pathids.length>lpathids.length){
				if(path.indexOf(lpath)>=0){
					if(check[0] && check[0].checked){
						this.checked=true;
						return;
					}
				}
			}else if(pathids.length<lpathids.length){
				if(lpath.indexOf(path)>=0){
					if(check[0] && check[0].checked==false){
						this.checked=false;
						return;
					}
				}
			}
		});
	}
	

	//新建安全角色关联功能点
	function saveRoleFunctionPoint(){
		var params=jQuery('#roleFunctionPointForm').serialize();
	    //提交表单
		if(checkin){
			jQuery.post("ajax/addRoleFunctionPoint.action",params,function call(result){
				if(result.status){
					checkin = [];
					top.showMsg("success","组件授权安全角色成功");
					cancelWindow();
				}else{
					top.showMsg("error",result.message);
				}
			},'json');
		}
		return false;
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
    }
</script>
</body>
</html>