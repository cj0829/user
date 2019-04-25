<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改访客详情--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="visitorDetailForm" id="visitorDetailForm" onsubmit="return updateVisitorDetail(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="formtit">基本信息</div>
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">访客详细表</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="fk_visitordetail" name="fk_visitordetail" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="id" name="id" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorName" name="visitorName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorIdcard" name="visitorIdcard" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">记录id</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="orderid" name="orderid" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">创建时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="createtime" name="createtime" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccid" name="iccid" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">民族</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="nation" name="nation" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证居住地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idcardaddress" name="idcardaddress" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证签发机关</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="departmentadd" name="departmentadd" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">出生日期</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="birth" name="birth" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">性别</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="sex" name="sex" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签发时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="effectDate" name="effectDate" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">到期时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="expireDate" name="expireDate" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">app版本</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="appVersion" name="appVersion" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">OS版本</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="osVersion" name="osVersion" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">认证情况</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="contrast" name="contrast" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">录入方式</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="accessMethod" name="accessMethod" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证照片</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idcardimgFileId" name="idcardimgFileId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客照片</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="picturesFileId" name="picturesFileId" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
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
	               	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
			<div class="main-formbox">
	       		<div class="formtit">第二</div>
	           	<div class="e-tabbox">
	               	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
		<!--tab 二  	结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${visitorDetail.id}"/>
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
		//加载数据
 		$("#visitorDetailForm").form("load",{
			fk_visitordetail:"${visitorDetail.fk_visitordetail}",
			id:"${visitorDetail.id}",
			visitorName:"${visitorDetail.visitorName}",
			visitorIdcard:"${visitorDetail.visitorIdcard}",
			orderid:"${visitorDetail.orderid}",
			createtime:"${visitorDetail.createtime}",
			iccid:"${visitorDetail.iccid}",
			nation:"${visitorDetail.nation}",
			idcardaddress:"${visitorDetail.idcardaddress}",
			departmentadd:"${visitorDetail.departmentadd}",
			birth:"${visitorDetail.birth}",
			sex:"${visitorDetail.sex}",
			effectDate:"${visitorDetail.effectDate}",
			expireDate:"${visitorDetail.expireDate}",
			appVersion:"${visitorDetail.appVersion}",
			osVersion:"${visitorDetail.osVersion}",
			contrast:"${visitorDetail.contrast}",
			accessMethod:"${visitorDetail.accessMethod}",
			idcardimgFileId:"${visitorDetail.idcardimgFileId}",
			picturesFileId:"${visitorDetail.picturesFileId}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateVisitorDetail(){
		try{
			if($("#visitorDetailForm").form("validate")){
				WaitingBar.getWaitingbar("updatevisitorDetail","数据修改中，请等待...").show();
				var params=jQuery("#visitorDetailForm").serialize();
				jQuery.post("${cxt}/visitorDetailvisitorDetail/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updatevisitorDetail").hide();
					if(data.status){
						showMsg("info","编辑访客详情成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑访客详情失败!");
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
