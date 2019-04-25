<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>访客记录详细表--${system_name}</title>  
</head>
<body>
<div>
<!--内容开始-->
	<form action="#" method="get" name="visitorDetailForm" id="visitorDetailForm">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="基本信息" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
				<div class="e-tabbox">
                	<table class="e-form-tab" width="100%" cellpadding="0" cellspacing="0">
					<tr>
				<th class="e-form-th" style="width:92px;">被访人姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="respondentsName" name="respondentsName" data-options="" readonly="true" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">被访人部门</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="agenciesName" name="agenciesName" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客人数</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorNumber" name="visitorNumber" data-options="" style="width:200px;height: 25px;" type="text" />
					</td>
					<th class="e-form-th" style="width:92px;">访客订单状态</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorOrderStatus" name="visitorOrderStatus" data-options="" style="width:200px;height: 25px;" type="text" />
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客类型</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorType" name="visitorType" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">访客证件照</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorIdPic" name="visitorIdPic" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访客身份证号</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorsIdcard" name="visitorsIdcard" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">访客姓名</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="visitorsName" name="visitorsName" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">来访时间</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="startTime" name="startTime" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					<th class="e-form-th" style="width:92px;">会客地点</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="address" name="address" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th class="e-form-th" style="width:92px;">访问事由</th>
						<td class="e-form-td">
						<input class="easyui-textbox" id="content" name="content" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					
					</td>
				</tr>
					</table>
				</div>
			</div>
		</div>
		</div>
	</form>
<!--内容结束-->
</div>
<script type="text/javascript">
	window.onload=function(){
		//加载数据
 		$("#visitorDetailForm").form("load",{
			respondentsName:"${visitorLog.respondentsName}",
			agenciesName:"${visitorLog.agenciesName}",
			visitorNumber:"1",
			visitorOrderStatus:"访前登记",
			visitorType:"${visitorDetail.visitorType}",
			visitorIdPic:"${visitorDetail.idcardimgFileId}",
			visitorsIdcard:"${visitorLog.visitorsIdcard}",
			visitorsName:"${visitorLog.visitorsName}",
			startTime:"${visitorLog.startTime}",
			address:"${visitorLog.address}",
			endTime:"${visitorLog.endTime}",
			content:"${visitorDetail.content}"
		});
	};
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
</script>
</body>
</html>