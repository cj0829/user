<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
  <%@ include file="../common/common.jsp"%>
  <title>添加行政区模板--${system_name}</title>  
</head>
<body>
<!-- start 内容 -->
<form action="#" method="get" name="provinceForm" id="provinceForm" onsubmit="return updateProvince(this)">
<div class="e-tabbox">
	<!-- start one -->
	<div class="e-form-mod">
		<div class="e-form-mod-bd">
			<table class="e-form-tab" width="100%">
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>行政区名称</label>
					</th>
					<td class="e-form-td">
						<input class="easyui-textbox inp" id="name" name="name" type="text"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>父行政区ID</label>
					</th>
					<td class="e-form-td">
						<span>${empty parentName?'没有上级行政区':parentName }</span>
						<input name="parentId" type="hidden"/>
					</td>
				</tr>
				<!-- <tr>
					<th class="e-form-th" style="width:136px;" >
						<label>有子行政区</label>
					</th>
					<td class="e-form-td">
						<input id="hasChild1" name="hasChild" type="checkbox" value="1" onchange="invalid()"/>
						<input id="hasChild2" name="hasChild" type="checkbox" value="2" disabled="disabled" style="display:none;"/>
					</td>
				</tr> -->
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>行政区编码</label>
					</th>
					<td class="e-form-td">
						<input class="easyui-textbox inp" name="code" type="text"/>
					</td>
				</tr>
				
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>备注</label>
					</th>
					<td class="e-form-td">
						<input class="easyui-textbox inp" name="remark" type="text"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>显示顺序</label>
					</th>
					<td class="e-form-td">
						<input class="easyui-textbox inp" name="rank" type="text"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- end one -->
</div>
<!-- end 内容 -->
<input type="hidden" id="id" name="id" value="${province.id}"/>
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit" >提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
</div>
</form>
<script  type="text/javascript"> 
 	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		//加载数据
	 	$("#provinceForm").form("load",{
	 		name:"${province.name}",
	 		parentId:"${province.parentId}",
	 		hasChild:"${province.hasChild}",
	 		code:"${province.code}",
	 		remark:"${province.remark}",
	 		rank:"${province.rank}",
		});
	 	invalid();
	};
 	jQuery(document).ready(function(){
		$(window).unbind("#winBody").bind("resize.winBody", function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
		
	//提交表单的方法
	function updateProvince(form1){
		//提交表单
		 if($("#provinceForm").form("validate")){
			var params=jQuery("#provinceForm").serialize();
			jQuery.post("${cxt}/province/ajax/update.action",params,function call(data){
				if(data.status){
					showMsg("info","编辑行政区模板成功");
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","编辑行政区模板失败!");
					}
				}
			},"json");
		}	
		return false;
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	};//
	
	function invalid(){
		if($("#hasChild1")[0].checked){
			$("#hasChild2").attr("disabled","disabled");
		}else{
			$("#hasChild2").removeAttr("disabled");
		}
	}
</script>
</body>
</html>
