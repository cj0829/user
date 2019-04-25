<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改功能点分类--${system_name }</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
  </head> 
  
  <body>
  <form action="#" method="post" name="funForm" id="funForm" onsubmit="return savefunctionPoint(this);">
  <div class="e-tabbox main-expand-form">
  	<div class="e-form-mod">
  		<h2 class="e-form-mod-tit mb15">修改功能点分类</h2>
  		<table class="e-form-tab" width=100%>
  			<tr>
				<th class="e-form-th" style="width:106px;" ><label>功能点分类名称</label></th>
				<td class="e-form-td">
					${empty function.functionPoint.name?'根节点':function.functionPoint.name}
					<input type="hidden" id="id"  name="id" value="${function.id}"/> 
	              	<input type="hidden" id="parentId"  name="parentId" value="${function.functionPoint.id}"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点名称</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="name" name="name" data-options="required:true,validType:'unique[\'${cxt}/functionPoint/ajax/findHasUpdateName.action?id=${function.id}&parentId=${function.functionPoint.id}\',\'name\',\'功能点名称已重复\']'" msg="功能点名称" value="${function.name}" />
					<span class="advert" id="functionNameMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点类型</label></th>
				<td class="e-form-td">
					分类
					<input type="hidden" class="easyui-textbox" name="functionPointType" value="${typeId}"/>
	              	<span class="advert" id="functionCodeMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>描述</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="remark"  name="remark" value="${function.remark}"/>
	              	<span class="advert" id="remarkMsg"></span>
				</td>
			</tr>
  		</table>
  	</div>
  </div>
  <div class="e-tab-btnbox">
	<button class="btn mr25" type="submit" >提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
  </div>
  </form>
  </body>
  
  <script  type="text/javascript">  
  window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
	};

   jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
	    //功能点名是否存在，0为不存在，1为存在
		var hasFunName = "";
	    
		//验证功能点名是否已经存在的方法
		function checkHasFunName(){
			//获得功能点名称
			var name = jQuery("#name").val();
			jQuery("#functionNameMsg").html("");
			//检查是否存在
			if(name!=""){
				jQuery.post("ajax/findHasUpdateName.action", "id=${function.id}&name="+name, function call(result){
					hasFunName = result.message;
					if(!result.status){
						jQuery("#functionNameMsg").html(result.message); 
						jQuery("#name").focus();
					}			
				},'json');
			} 
		}
		
		//提交表单的方法
		function savefunctionPoint(form1){
			try{
				//检查功能点名是否存在
				if(hasFunName!=""){
					alert("功能点名已经存在，请换一个功能点名。");
					return false;
				}
				//提交表单
				if($("#funForm").form("validate")){ 
					var params=jQuery('#funForm').serialize();
					jQuery.post("${cxt}/functionPoint/ajax/update.action",params,function call(result){
						if(result.status){
							parent.callbackUpdate(result.data);
							cancelWindow();
						}else{
							if(result.message){
								showMsg("error",result.message);
							}else{
								showMsg("error","字典数据失败!");
							}
						}
					},'json');
				}
			}catch(e){}
			return false;
		}
		
		function cancelWindow(){
			top.jQuery("#win").window("close");
		}
</script>
</html>
