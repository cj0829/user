<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改黑名单--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="fKBlackListForm" id="fKBlackListForm" onsubmit="return updateFKBlackList(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr><th class="e-form-th" style="width:92px;">身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idCard" name="idCard" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">机构id</th>
						<td class="e-form-td">
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="required:true"  style="width:115px; height:27px;"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">备注</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="remark" name="remark" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					</table>
				</div>
			</div>
		</div>
	    <!--tab结束-->
		</div>
		<input type="hidden" id="id" name="id" value="${fKBlackList.id}"/>
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
 		$("#fKBlackListForm").form("load",{
			idCard:"${fKBlackList.idCard}",
			remark:"${fKBlackList.remark}",
			agenciesId:"${fKBlackList.agenciesId}",
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateFKBlackList(){
		try{
			if($("#fKBlackListForm").form("validate")){
				WaitingBar.getWaitingbar("updatefKBlackList","数据修改中，请等待...").show();
				var params=jQuery("#fKBlackListForm").serialize();
				jQuery.post("${cxt}/vistor/fKBlackList/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updatefKBlackList").hide();
					if(data.status){
						showMsg("info","编辑黑名单成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑黑名单失败!");
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
