<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<title>用户注册--${system_name }</title>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <style>
 .formBoxItem .formBoxItemL{width:80px;}
 </style>
</head>
<body>
	  <form name="registerForm" id="registerForm" onsubmit="return registerUser(this);">
			<div class="e-tabbox main-expand-form">
				<h2 class="formItemTitle mb10"><span class="mr5"></span>创建新的机构</h2>
		        <div class="formBoxItem">
	              <label for="name" class="formBoxItemL"><span>*</span>机构名称：</label>
	              <div class="formBoxItemR">
	               <input class="easyui-textbox" id="name" name="organame" style="width:200px;height: 25px;" data-options="required:true,validType:'unique[\'${cxt}/registerOrganization/ajax/checkUpdateName.action\',\'name\',\'机构名称已存在\']'" type="text"/>
             	  </div>
	            </div>
		        <div class="formBoxItem">
	              <label for="name" class="formBoxItemL"><span></span>创建/选择机构的管理员：</label>
	              <div class="formBoxItemR">
	              <div class="expand-dropdown-select">
	               <select id="createOrChoose" class="easyui-combobox" name="createOrChoose" style="width:200px;" data-options="editable:false,onChange:createOrChoose">   
					    <option value="1">创建机构管理员</option>
					    <option value="2">选择机构管理员</option>
					</select>
					</div>
             	  </div>
	            </div>
	            <div id="choose" class="formBoxItem">
	              <label for="loginName" class="formBoxItemL"><span>*</span>选择机构管理员：</label>
	              <div class="formBoxItemR">
	              	<user:usergrid id="managerUser" name="userId" dataOptions="required:true"/>
	              </div>
	            </div>
	            <div id="create" >
		   		<h2 class="formItemTitle mb10"><span class="mr5"></span>创建机构的管理员</h2>	
		        <div class="formBoxItem">
	              <label for="loginName" class="formBoxItemL"><span>*</span>用户名：</label>
	              <div class="formBoxItemR">
	              <input class="easyui-textbox" id="name" name="loginName" style="width:200px;height: 25px;" data-options="required:true,validType:'unique[\'${cxt}/user/ajax/findLoginName.action\',\'loginName\',\'管理员名称已存在\']'" type="text"/>
	              </div>
	            </div>
	            <div class="formBoxItem">
	              <label for="password" class="formBoxItemL"><span>*</span>用户密码：</label>
	              <div class="formBoxItemR">
	              <input class="easyui-textbox" id="password" name="password" style="width:200px;height: 25px;" type="password" data-options="required:true"/>
	              <span  class="f-message"  id="passwordMsg"></span>
	           	  </div>
	            </div>
	            <div class="formBoxItem">
	              <label for="email" class="formBoxItemL"><span>*</span>邮箱：</label>
	              <div class="formBoxItemR">
	              <input class="easyui-validatebox" id="email" name="email" style="width:200px;height: 25px;" type="text" validtype="email" invalidMessage="邮箱格式不正确" data-options="required:true"/>
	              <span  class="f-message"  id="emailMsg"></span>
	              </div>
	            </div>
	            </div>
			    <h2 class="formItemTitle mb10"><span class="mr5"></span>给新的机构分配权限。必须选择功能点 <em  class="f-message" id="parameterMsg"></em></h2>
			    <table class="main-ordinary-tab"><c:forEach items="${functionPointList }" var="topfuns">
					<tr id="roleList" sort="false" >
						<th width="140px" >${topfuns.name }模块</th>
						<th>功能点</th>
					</tr><c:forEach items="${topfuns.children }" var="functionPoint">
					<tr>
			            <td style="text-align:right">${functionPoint.name }<input type='checkbox' style="margin-left:10px;margin-right:20px;" 
			            onclick="selectAll(this, 'code_${functionPoint.id}', 'checkbox')" /></td>
			            <td id="code_${functionPoint.id }"><c:forEach items="${functionPoint.children }" var="subPoint"><div class="f-left">
						<input type='checkbox'  path="${subPoint.path}" onclick="selectCheckBox(this)"  name="functionPointIds" value='${subPoint.id }' <c:forEach items="${register.roleFunctionPointList }" var="functionPointId"><c:if test="${functionPointId == subPoint.id}">checked</c:if></c:forEach> />
	   					<span>${subPoint.name }</span>
						</div><c:if test="${fn:length(subPoint.children) > 0}"><c:set var="children" value="${subPoint.children}" scope="request" /><jsp:include page="/WEB-INF/jsp/common/role/recursion.jsp"/></c:if></c:forEach></td></tr></c:forEach></c:forEach><tr>
						<td style="text-align:center">全选</td>
						<td><input type='checkbox' style="margin-left:20px;" onclick='selectAll(this, "", "checkbox")' /></td>
					</tr>
				</table>
		</div>
	 	<div class="e-tab-btnbox">
             <input type="submit" value="确定" class="btn mr10" /> 
             <input type="button" value="取消" class="btn cancel" onclick="cancelWindow()" />
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
	createOrChoose(1);
});
function submit(){
	$("#registerForm").submit();
}
//设置参数指定
function setName(input){
	if(input){
		var value=jQuery("#value_"+input).val();
		var name=jQuery("#name_"+input).attr("paramName");
		jQuery("#name_"+input).val(name+"_"+value);
	}
}
//提交表单的方法
function registerUser(form1){
	var createOrChoose = $("#createOrChoose").combobox("getValue");
	if(createOrChoose==1){
		$("#choose").hide();
		choose.innerHTML="";
	}else{
		create.innerHTML="";
	}
	try{
		var form1 = jQuery("#registerForm");
		var checkes=jQuery("#registerForm").find("input[name='functionPointIds']:checked");
		var c=checkes.length<=0;
		if(c){
			top.showMsg("info","请选择功能点");
		}else{
			jQuery("#parameterMsg").html("");
		}
		//提交表单
		if(form1.form('validate') && !c){ 
		 	var params=jQuery("#registerForm").serialize();
			jQuery.post("${cxt}/registerOrganization/ajax/add.action",params,function call(result){
				if(result.status){
					top.showMsg("success","机构注册成功");
					parent.callbackPage();
					cancelWindow();
				}else{
					top.showMsg("error",result.message);
				}	
			},'json');
		};
	}catch(e){console.log(e);};
	return false;
}

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
    };
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
			if(path.indexOf(lpath)>-1){
				parentCheckBox(this);
			};
		}else{
			if(lpath.indexOf(path)>-1){
				this.checked=checker.checked;
			};
		};
	});
}

function parentCheckBox(checker){
	var check=jQuery(checker),path=check.attr("path"),pathids=path.split("/");
	check.closest("td").find("div>input").each(function(){
		var lpath=jQuery(this).attr("path"),lpathids=lpath.split("/");
		jQuery(this).attr("path");
		//父节点
		if(pathids.length<lpathids.length){
			if(lpath.indexOf(path)>-1){
				if(this.checked){
					checker.checked=true;
					return false;
				};
			};
		};
		//
	});
}
//=======系统参数
//进入详细页
function callbackInfo(user){
	window.location.href="preInfo.action?loginName="+user.loginName+"&password="+user.password+"&email="+user.email;
};
function createOrChoose(){
	var createOrChoose = $("#createOrChoose").combobox("getValue");
	if(createOrChoose==1){
		$("#create").show();
		$("#choose").hide();
	}else{
		$("#create").hide();
		$("#choose").show();
	}
}
//返回列表页
function callbackPage(){
	window.location.href="${cxt}/registerOrganization/preList.action";
};
//关闭窗口
function cancelWindow(){
	top.$("#win").window("close");
}
$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
</script>
</body>
</html>