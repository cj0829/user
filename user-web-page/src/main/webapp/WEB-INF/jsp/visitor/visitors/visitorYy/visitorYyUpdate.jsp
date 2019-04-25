<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改预约管理--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="visitorYyForm" id="visitorYyForm" onsubmit="return updateVisitorYy(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
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
						<input class="easyui-numberspinner" id="visitorNum" name="visitorNum"  data-options="" style="width:200px;height: 25px;" type="text"/>
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
		<input type="hidden" id="id" name="id" value="${visitorYy.id}"/>
		<div style="text-align:center;">
			<div class="e-tab-btnbox">
				<button class="btn mr25" type="submit" >保存</button>
				<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
			</div>		
		</div>
	</form>
<!--内容结束-->
</div>
<script type="text/javascript">
		window.onload=function(){
			//微调
			$('#visitorNum').numberspinner({    
		    min: 1,    
		    max: 20,    
		    editable: true  
		}); 
		//加载数据
 		$("#visitorYyForm").form("load",{
			respondentsName:"${visitorYy.respondentsName}",
			idCard:"${visitorYy.idCard}",
			createtime:"${visitorYy.createtime}",
			createUser:"${visitorYy.createUser}",
			visitorNum:"${visitorYy.visitorNum}",
			agenciesId:"${visitorYy.agenciesId}",
			yyContent:"${visitorYy.yyContent}",
			yyName:"${visitorYy.yyName}",
			address:"${visitorYy.address}",
			smtimeStr:"${visitorYy.smtime}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateVisitorYy(){
		try{
			if($("#visitorYyForm").form("validate")){
				WaitingBar.getWaitingbar("updatevisitorYy","数据修改中，请等待...").show();
				var params=jQuery("#visitorYyForm").serialize();
				jQuery.post("${cxt}/visitors/visitorYy/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updatevisitorYy").hide();
					if(data.status){
						showMsg("info","编辑预约管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑预约管理失败!");
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
