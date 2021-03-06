<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>添加积分等级--${system_name }</title>  
</head>
<body>  

<form action="#" method="post" name="pointsLevelRulesForm" id="pointsLevelRulesForm" onsubmit="return savePointsLevelRules(this)">
<div class="e-tabbox main-expand-form">
	<table class="e-form-tab" width=100%>
		<tr>
			<th class="e-form-th" style="width:92px;"><label>积分等级：</label></th>
			<td class="e-form-td">
				<input class="easyui-numberbox" id="level" name="level" style="width:200px;height: 25px;" data-options="min:0,precision:0,max:100" type="text"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>积分等级名称：</label></th>
			<td class="e-form-td">
				<input type="text" class="easyui-textbox" type="text" id="levelName" name="levelName" data-options="required:true,validType:'unique[\'${cxt}/pointsLevelRules/ajax/checkUpdateName.action\',\'levelName\',\'积分等级名称已存在\']'" style="width:200px;height: 25px;"/>
			</td>
		</tr>
		<tr>
			<th class="e-form-th"><label>分数：</label></th>
			<td class="e-form-td">
				<input type="text" class="easyui-numberbox" type="text" id="points" name="points" data-options="min:0,precision:0,max:100"  style="width:200px;height: 25px;"/>
			</td>
		</tr>
	</table>
</div>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">保存</button>
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
	function submit(){
		$("#pointsLevelRulesForm").submit();
	}
	//提交表单的方法
	function savePointsLevelRules(form1){
		//提交表单
		if($('#pointsLevelRulesForm').form('validate')){ 
			var params=jQuery('#pointsLevelRulesForm').serialize();
			jQuery.post("${cxt}/pointsLevelRules/ajax/add.action?ajaxFlag=1",params,function call(data){
				if(data.status){
					showMsg("info","积分等级添加成功");
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","积分等级添加失败!");
					}
				}
			},'json');
		}
		return false;
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	}
	$(function(){tabsclick("#tab .main-tabbar .main-btn-radius","#tab .main-tabbox .mod");});//
</script>
</body>
</html>