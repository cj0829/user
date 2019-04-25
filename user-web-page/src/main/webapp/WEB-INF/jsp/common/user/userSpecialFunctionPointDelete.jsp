<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<link type="text/css" href="${cxt}/css/exam/dark-transparent/userSpecialFunctionPoint.css" rel="stylesheet" />
 	<title>新建${userTitle}--${system_name}</title>
</head>
<body>
<!--弹出窗口开始-->
<!--内容开始-->
<div class="e-tabbar">
       <a class="t-btn" href="javascript:getNoFunctionPoint(0)" ><span class="btntxt">未授权</span></a>
       <a class="t-btn curr" href="javascript:void(0)" ><span class="btntxt">已授权</span></a>
</div>
<form action="#" method="post" name="userSpecialFunctionPointForm" id="userSpecialFunctionPointForm" onsubmit="return saveUserSpecialFunctionPoint();">
<div class="e-tabbox main-expand-form">
<div class="main-ordinary-tab">
<table width="100%"><c:forEach items="${functionPointList }" var="topfuns"><tr sort="false" >
<th width="165px" >${topfuns.name }模块</th>
<th>方法</th></tr><c:forEach items="${topfuns.children }" var="functionPoint"><tr>
<td style="text-align:center" class="td_1">${functionPoint.name}<input type='checkbox' style="margin-left:10px;margin-right:20px;" onclick='selectAll(this, "del_${functionPoint.id}", "checkbox")' /></td>
<td id="del_${functionPoint.id}"><c:forEach items="${functionPoint.children}" var="subPoint">
<div class="f-left">
	<input type='checkbox' path="${subPoint.path}" onclick="selectCheckBox(this)"  name="functionPointIds" value='${subPoint.id }'/>
<span>${subPoint.name }</span>
</div><c:if test="${fn:length(subPoint.children) > 0}"><c:set var="children" value="${subPoint.children}" scope="request" /><jsp:include page="/WEB-INF/jsp/common/user/recursion.jsp"/></c:if></c:forEach></c:forEach>
</td>
</tr></c:forEach> 
<tr>
<td style="text-align:center">全选</td>
<td><input type='checkbox' style="margin-left:20px;" onclick='selectAll(this, "", "checkbox")' /></td>
</tr>
<tr>
</table>
</div>
<input type="hidden" id="userId" name="userId" value="${userId}"/>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">撤销授权</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form> 
<script  type="text/javascript"> 
window.onload=function(){
	$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
};

jQuery(document).ready(function(){
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
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
		var check=jQuery(checker),search=check.attr("search"),path=check.attr("path"),pathids=path.split("/");
		if(checker.checked && search){
			return;
		}
		check.closest("td").find("div>input").each(function(){
			var lpath=jQuery(this).attr("path"),lpathids=lpath.split("/");
			jQuery(this).attr("path");
			if(pathids.length>lpathids.length){
				if(lpath==path){
					parentCheckBox(this);
				}
			}else{
				if(lpath==path){
					this.checked=checker.checked;
				}
			}
		});
	}

	function parentCheckBox(checker){
		var check=jQuery(checker),path=check.attr("path"),pathids=path.split("/");
		check.closest("td").find("div>input").each(function(){
			var lpath=jQuery(this).attr("path"),lpathids=lpath.split("/");
			jQuery(this).attr("path");
			//父节点
			if(pathids.length<lpathids.length){
				if(lpath==path){
					if(this.checked){
						checker.checked=true;
						return false;
					}
				}
			}
		});
	}

	//添加用户关联功能点
	function saveUserSpecialFunctionPoint(){
		var params=jQuery('#userSpecialFunctionPointForm').serialize();
	    //提交表单
	    try{
			if(checkin){
				jQuery.post("${cxt}/user/ajax/deleteUserSpecialFunctionPoint.action",params,function call(data){
					if(data.status){
						checkin = []; 
						window.location.href="${cxt}/user/preDeleteUserSpecialFunctionPoint.action?userId=${userId}&isInternal=${isInternal}";
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","用户刪除功能点成功!");
						}
					}
					
				},'json');
			}
	    }catch(e){
	    	alert(e);
	    }
		return false;
	}
	function getNoFunctionPoint(){
		window.location.href="${cxt}/user/preAddUserSpecialFunctionPoint.action?userId=${userId}&isInternal=${isInternal}";
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>