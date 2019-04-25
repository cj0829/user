<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加白名单--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="whiteListForm" id="whiteListForm" onsubmit="return saveWhiteList()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" style="height:310px;">
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idCard" name="idCard" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					
					<tr><th class="e-form-th" style="width:92px;">姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userName" name="userName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">机构</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="required:true"  style="width:115px; height:27px;"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">备注</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="remark" name="remark" data-options="required:false" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
		<!--tab结束-->
	</div>
	<div style="text-align:center;">
	  	<div class="e-tab-btnbox">
			<button class="btn mr25" type="submit" >保存</button>
			<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
		</div>		
	</div>
	</form>
	<!--内容结束-->
</div>
<!--弹出窗口结束-->
<script type="text/javascript">
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
	//提交表单的方法
	function saveWhiteList(){
		try{
			if($("#whiteListForm").form("validate")){
				WaitingBar.getWaitingbar("addwhiteList","数据添加中，请等待...").show();
				var params=jQuery("#whiteListForm").serialize();
				jQuery.post("${cxt}/visitor/whiteList/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addwhiteList").hide();
					if(data.status){
						showMsg("info","添加白名单成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加白名单失败!");
						}
					}
				},"json");
			}
		} catch (e) {}
		return false;
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>
