
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 	<title>用户首选项--${system_name}</title>  
</head>
<body>
<!--内容开始-->
<div class="e-tabbar">
	<a class="t-btn" href="${cxt}/mobile/usercontacts/preUpdate.action?id=${userId}" ><span class="btntxt">主要</span></a>
	<core:security code="0201409">
	<a class="t-btn" href="${cxt}/mobile/usercontacts/preUpdateResetPassword.action?userId=${userId}" ><span class="btntxt">密码</span></a>
	</core:security>
	<a class="t-btn curr" href="javascript:;" >用户详细信息</a>
	
</div>
<div class="e-tabbox main-expand-form">
<form action="#" method="get" name="userForm" id="userForm" onsubmit="return updateUserMore(this)">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:80px;"><label>固定电话</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="homePhone" name="homePhone" data-options="validType:'length[1,120]'" value="${userMore.homePhone}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>地址</label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="address" name="address" data-options="validType:'length[1,120]'""  value="${userMore.address}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize1"/></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize1" name="customize1" data-options="validType:'length[1,120]'" value="${userMore.customize1}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize2"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize2" name="customize2" data-options="validType:'length[1,120]'" value="${userMore.customize2}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize3"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize3" name="customize3" data-options="validType:'length[1,120]'" value="${userMore.customize3}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize4"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize4" name="customize4" data-options="validType:'length[1,120]'" value="${userMore.customize4}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize5"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize5" name="customize5" data-options="validType:'length[1,120]'" value="${userMore.customize5}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize6"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize6" name="customize6" data-options="validType:'length[1,120]'" value="${userMore.customize6}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize7"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize7" name="customize7" data-options="validType:'length[1,120]'" value="${userMore.customize7}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize8"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize8" name="customize8" data-options="validType:'length[1,120]'" value="${userMore.customize8}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize9"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize9" name="customize9" data-options="validType:'length[1,120]'" value="${userMore.customize9}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label><core:property dictType="UserCustomize" value="customize10"/></label></label></th>
			<td class="e-form-td">
			<input class="easyui-textbox" id="customize10" name="customize10" data-options="validType:'length[1,120]'" value="${userMore.customize10}" style="width:200px;height: 25px;" type="text"/>
			</td>
		</tr>
		
	</table>
	<input type="hidden" id="id" name="id" value="${userMore.id}"/>
	<input type="hidden" id="userId" name="userId" value="${userId}"/>
	</form>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="button" onclick="submit();">保存</button>
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
<!--内容结束-->
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
		});
	});
 	//
 	function submit(){
 		$("#userForm").submit();
 	}
	//保存信息
	function updateUserMore(){
		if($("#userForm").form("validate")){
			var params=jQuery('#userForm').serialize();
			jQuery.post("${cxt}/userMore/ajax/update.action",params,function call(data){  
				if(data.status){
	            	showMsg("info","修改成功");
	            }else{
	            	if(data.message){
	            		showMsg("error",data.message);
	            	}else{
	            		showMsg("error","修改失败");
	            	}
	            }
			},'json');  
		}
		return false;
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
	
</script>
</body>
</html>

