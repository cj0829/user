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
	<form action="#" method="get" name="punchRecordForm" id="punchRecordForm" onsubmit="return savePunchRecord()">
	<input id="agenciesId" name="agenciesId" value="${bean.agenciesId }" type="hidden"/>
	<input id="data" name="data" value="${bean.data }" type="hidden"/>
	<input id="iccid" name="iccid" value="${bean.iccid }" type="hidden"/>
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" style="height:310px;">
			<div class="main-formbox">
            	<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
                	
					<tr><th class="e-form-th" style="width:92px;">姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="name" name="name" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">用户名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="loginName" name="loginName" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">机构名称</th>
						<td class="e-form-td">
						${agenciesName}
					</td>
					</tr>
					<tr>
						<th class="e-form-th"><label>性别</label></th>
						<td class="e-form-td">
							<div class="expand-dropdown-select" style="display:inline;">
							<core:select className="easyui-combobox"  id="gender" name="gender" dictType="gender" style="width:200px;height:25px;" data-options="required:true,panelHeight:'95px'" />
							</div>
						</td>
					</tr>
					<tr>
						<th class="e-form-th"><label>手机号</label></th>
						<td class="e-form-td">
						<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="mobile" validtype="mobile" invalidMessage="手机格式不正确" name="mobile" data-options=""/>
						</td>
					</tr>
					<tr>
						<th class="e-form-th"><label>电子邮件</label></th>
						<td class="e-form-td">
						<input class="easyui-textbox" style="width:200px;height: 25px;" type="text" id="email" maxlength="128" validtype="email" invalidMessage="邮箱格式不正确" name="email" data-options="regChars:[]"/>
						</td>
					</tr>
					<tr>
						<th class="e-form-th"><label>照片</label></th>
						<td class="e-form-td">
						<img src="${bean.dataUrl }" alt="暂时未找到图片" height="200" width="200"/>
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
	function savePunchRecord(){
		try{
			if($("#punchRecordForm").form("validate")){
				WaitingBar.getWaitingbar("addpunchRecord","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#punchRecordForm").serialize();
				jQuery.post("${cxt}/mobile/amWhiteList/ajax/addWhite.action",params,function call(data){
					WaitingBar.getWaitingbar("addpunchRecord").hide();
					if(data.status){
						showMsg("info","添加打卡记录管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加打卡记录管理失败!");
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
