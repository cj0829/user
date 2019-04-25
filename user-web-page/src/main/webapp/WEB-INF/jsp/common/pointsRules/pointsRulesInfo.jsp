<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>积分规则详细信息--${system_name }</title>   
</head>
<body>   
<form action="#" method="post" name="pointsRulesForm" id="pointsRulesForm" onsubmit="return savePointsRules(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:92px;"><label>积分规则名：</label></th>
			<td class="e-form-td">
				${pointsRules.operation}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>功能点：</label></th>
			<td class="e-form-td">
				${pointsRules.functionPointName}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>分数1：</label></th>
			<td class="e-form-td">
				${pointsRules.operation}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>描述1：</label></th>
			<td class="e-form-td">
				${pointsRules.pointsMark1}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>分数2：</label></th>
			<td class="e-form-td">
				${pointsRules.points2}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>描述2：</label></th>
			<td class="e-form-td">
				${pointsRules.pointsMark2}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>每日次数：</label></th>
			<td class="e-form-td">
				${pointsRules.countDay}
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>备注：</label></th>
			<td class="e-form-td">
				${pointsRules.remark}
			</td>
		</tr>
	</table>
</div>
<div class="e-tab-btnbox">
	<!-- <button class="btn mr25" type="submit">保存</button> -->
	<button class="btn cancel" type="button" onclick="cancelWindow();">取消</button>
</div>
</form>
<script  type="text/javascript">    
 	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
	};
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
</script>
</body>
</html>