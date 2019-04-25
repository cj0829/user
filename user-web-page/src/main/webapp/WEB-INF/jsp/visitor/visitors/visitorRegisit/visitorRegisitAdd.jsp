<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加安全刷卡记录--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div>
	<!--内容开始-->
	<form action="#" method="get" name="visitorRegisitForm" id="visitorRegisitForm" onsubmit="return saveVisitorRegisit()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" style="height:310px;">
			<div class="main-formbox">
        		<div class="formtit">基本信息</div>
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">访客详细表</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="fk_visitorRegisit" name="fk_visitorRegisit" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">记录id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="id" name="id" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">用户id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userId" name="userId" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证名称</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userName" name="userName" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">性别</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="sex" name="sex" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">民族</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="nation" name="nation" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">出生日期</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="birth" name="birth" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="address" name="address" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idcard" name="idcard" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">发证时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="qftime" name="qftime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">有效时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="yxtime" name="yxtime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签发机关</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="qfadd" name="qfadd" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证照片id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="cardimageFileId" name="cardimageFileId" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">本人照片</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="photoFileId" name="photoFileId" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccid" name="iccid" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccidId" name="iccidId" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备公司</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesId" name="agenciesId" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">比对时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="recordTime" name="recordTime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">比对分值</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="score" name="score" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">注册人员的类型是否，为黑名单或者白名单</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="type" name="type" data-options="" style="width:200px;height: 25px;" type="text"/>
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
	function saveVisitorRegisit(){
		try{
			if($("#visitorRegisitForm").form("validate")){
				WaitingBar.getWaitingbar("addvisitorRegisit","数据添加中，请等待...").show();
				var params=jQuery("#visitorRegisitForm").serialize();
				jQuery.post("${cxt}/visitor/visitorRegisit/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addvisitorRegisit").hide();
					if(data.status){
						showMsg("info","添加安全刷卡记录成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加安全刷卡记录失败!");
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
