<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
  <%@ include file="../common/common.jsp"%>
  <title>添加模板管理--${system_name}</title>  
</head>
<body>
<!-- start tab切换按钮 -->
<div class="e-tabbar">
	<a href="javascript:;" class="t-btn curr">主要</a>
	<a href="javascript:;" class="t-btn">相关信息</a>
</div>
<!-- end tab切换按钮 -->
<!-- start 内容 -->
<form action="#" method="get" name="remindTemplateForm" id="remindTemplateForm" onsubmit="return saveRemindTemplate(this)">
<div class="e-tabbox">
	<!-- start one -->
	<div class="e-form-mod">
		<h2 class="e-form-mod-tit mb15">设置试卷基本属性</h2>
		<div class="e-form-mod-bd">
			<table class="e-form-tab" width="100%">
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>模板名称</label></th>
					<td class="e-form-td"><input class="easyui-textbox inp" id="name" name="name"  maxlength="0" style="width:230px;" type="text" data-options="required:true"/></td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>模板编码</label>
					</th>
					<td class="e-form-td">
						<input class="easyui-textbox inp" id="code" name="code" data-options="" maxlength="0" style="width:230px;" type="text"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>模板内容</label></th>
					<td class="e-form-td">
						<textarea style="width: 500px; height: 250px;" id="content" name="content"></textarea>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- end one -->
</div>
<!-- end 内容 -->
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
</div>
</form>
<script type="text/javascript"> 
 	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};
 	jQuery(document).ready(function(){
		$(window).unbind("#winBody").bind("resize.winBody", function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
		});
	});
	//提交表单的方法
	function saveRemindTemplate(form1){
		if($("#remindTemplateForm").form("validate")){
			var params=jQuery("#remindTemplateForm").serialize();
			jQuery.post("${cxt}/remindTemplate/ajax/add.action",params,function call(data){
				if(data.status){
					showMsg("info","添加模板管理成功");
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","添加模板管理失败!");
					}
				}
			},"json");
		}	
		return false;
	}
	function cancelWindow(){
		top.jQuery("#win").window("close");
	};
</script>
</body>
</html>
