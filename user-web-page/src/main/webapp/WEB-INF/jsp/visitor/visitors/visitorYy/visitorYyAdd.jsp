<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加预约管理--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="visitorYyForm" id="visitorYyForm" onsubmit="return saveVisitorYy()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" style="height:390px;">
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">被访人姓名</th>
						<td class="e-form-td">
						<user:usergrid id="respondentsId" name="respondentsId"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">被访人身份证号码</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idCard" name="idCard" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客人数</th>
						<td class="e-form-td">
						<input class="easyui-numberspinner" id="visitorNum" name="visitorNum"  data-options="" style="width:200px;height: 25px;" type="text" value="1"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">预约时间</th>
						<td class="e-form-td">
						<input id="smtimeStr" class="easyui-datetimebox" name="smtimeStr" data-options="required:true,validator:setMinDate,minDate:'datepickerStart'" type="text"style="width:200px; height:25px;"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">预约内容</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="yyContent" name="yyContent" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">预约人姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="yyName" name="yyName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					
					<tr><th class="e-form-th" style="width:92px;">会客地点</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="address" name="address" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">机构</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="rememberAll:true"  multiple="true" style="width:200px; height:27px;"/></li>
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
	$('#visitorNum').numberspinner({    
	    min: 1,    
	    max: 20,    
	    editable: true  
	}); 
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
	//提交表单的方法
	function saveVisitorYy(){
		try{
			if($("#visitorYyForm").form("validate")){
				WaitingBar.getWaitingbar("addvisitorYy","数据添加中，请等待...").show();
				var params=jQuery("#visitorYyForm").serialize();
				jQuery.post("${cxt}/visitors/visitorYy/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addvisitorYy").hide();
					if(data.status){
						showMsg("info","添加预约管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加预约管理失败!");
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
