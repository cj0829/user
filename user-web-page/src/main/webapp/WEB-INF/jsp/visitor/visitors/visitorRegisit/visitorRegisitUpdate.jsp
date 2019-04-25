<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>修改安全刷卡记录--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="visitorRegisitForm" id="visitorRegisitForm" onsubmit="return updateVisitorRegisit(this)">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					</tr>
					<tr><th class="e-form-th" style="width:92px;">身份证名称</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="userName" name="userName" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">性别</th>
						<td class="e-form-td">
						
						<input class="easyui-textbox" id="sex" name="sex" data-options="" style="width:200px;height: 25px;" value="<core:property dictType="gender" value="${visitorRegisit.sex}"/>" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">民族</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="nation" name="nation" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">出生日期</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="birth" name="birth" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="address" name="address" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="idcard" name="idcard" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">发证时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="qftime" name="qftime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">有效时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="yxtime" name="yxtime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">签发机关</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="qfadd" name="qfadd" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">设备号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="iccid" name="iccid" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">设备公司</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesName" name="agenciesName" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">比对时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="recordTime" name="recordTime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">比对分值</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="score" name="score" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">登记类型</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="type" name="type" style="width:200px;height: 25px;" type="text" value="<core:property dictType="PeopListType" value="${visitorRegisit.type}"/>"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">公司地址</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesAddress" name="agenciesAddress" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
						
					</tr>
					<tr>
					<c:if test="${ not empty visitorRegisit.cardimageFileId}">
					</tr>
						<tr><th class="e-form-th" style="width:92px;">身份证照片id</th>
						<td class="e-form-td">
						<img src="${visitorRegisit.cardimageFileId}" alt="" />
					</td>
					</tr>
					</c:if>
					<c:if test="${not empty visitorRegisit.photoFileId }">
					<tr><th class="e-form-th" style="width:92px;">本人照片</th>
						<td class="e-form-td">
						<img src="${visitorRegisit.photoFileId}" alt="" />
					</td>
					</tr>
					</c:if>
					</table>
				</div>
			</div>
		</div>
		</div>
		<input type="hidden" id="id" name="id" value="${visitorRegisit.id}"/>
		<div style="text-align:center;">
		</div>
	</form>
<!--内容结束-->
</div>
<script type="text/javascript">
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-120);
		//加载数据
 		$("#visitorRegisitForm").form("load",{
			userId:"${visitorRegisit.userId}",
			userName:"${visitorRegisit.userName}",
			nation:"${visitorRegisit.nation}",
			birth:"${visitorRegisit.birth}",
			address:"${visitorRegisit.address}",
			idcard:"${visitorRegisit.idcard}",
			qftime:"${visitorRegisit.qftime}",
			yxtime:"${visitorRegisit.yxtime}",
			qfadd:"${visitorRegisit.qfadd}",
			cardimageFileId:"${visitorRegisit.cardimageFileId}",
			photoFileId:"${visitorRegisit.photoFileId}",
			iccid:"${visitorRegisit.iccid}",
			iccidId:"${visitorRegisit.iccidId}",
			agenciesName:"${visitorRegisit.agenciesName}",
			recordTime:"${visitorRegisit.recordTimeStr}",
			score:"${visitorRegisit.score}",
			agenciesAddress:"${visitorRegisit.agenciesAddress}"
		});
	};
 	jQuery(document).ready(function(){
 		$(window).unbind('#winBody').bind('resize.winBody', function(){
 			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-120);
 		});
		$("#easyui-tabs").tabs({
			border:false
		});
	});
		
	//提交表单的方法
	function updateVisitorRegisit(){
		try{
			if($("#visitorRegisitForm").form("validate")){
				WaitingBar.getWaitingbar("updatevisitorRegisit","数据修改中，请等待...").show();
				var params=jQuery("#visitorRegisitForm").serialize();
				jQuery.post("${cxt}/visitor/visitorRegisit/ajax/update.action",params,function call(data){
					WaitingBar.getWaitingbar("updatevisitorRegisit").hide();
					if(data.status){
						showMsg("info","编辑安全刷卡记录成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","编辑安全刷卡记录失败!");
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
