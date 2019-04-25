<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加访客记录--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="visitorLogForm" id="visitorLogForm" onsubmit="return saveVisitorLog()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" style="height:310px;">
			<div class="main-formbox">
        		<div class="formtit">基本信息</div>
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">被访人姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="respondentsName" name="respondentsName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorsIdcard" name="visitorsIdcard" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorsName" name="visitorsName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">来访时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="startTime" name="startTime" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">来访是否打印</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="startappStatus" name="startappStatus" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">部门id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesId" name="agenciesId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>

					</table>
				</div>
			</div>
		</div>
		<!--tab结束-->
		<!--tab 二  	开始-->
		<div  title="第二teb "  style="padding:10px">
			<div class="main-formbox">
	       		<div class="formtit">基本信息</div>
	           	<div class="e-tabbox">
	               	<table width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
			<div class="main-formbox">
	       		<div class="formtit">第二</div>
	           	<div class="e-tabbox">
	               	<table width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
		<!--tab 二  	结束-->
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
	function saveVisitorLog(){
		try{
			if($("#visitorLogForm").form("validate")){
				WaitingBar.getWaitingbar("addvisitorLog","数据添加中，请等待...").show();
				var params=jQuery("#visitorLogForm").serialize();
				jQuery.post("${cxt}/visitorLog/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addvisitorLog").hide();
					if(data.status){
						showMsg("info","添加访客记录成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加访客记录失败!");
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
