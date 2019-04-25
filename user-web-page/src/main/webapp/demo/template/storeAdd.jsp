<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加商店--${system_name}</title>  
</head>
<body>
<!--弹出窗口开始-->
<div class="dialog_content">
	<!--内容开始-->
	<div class="main-tabwrap main-expand-form" id="tab">
	<form action="#" method="get" name="storeForm" id="storeForm" onsubmit="return saveStore()">
	<div id="easyui-tabs">
		<!--tab开始-->
		<div title="第一teb" class="main-tabbox" style="height:310px;">
			<div class="main-formbox">
        		<div class="formtit">基本信息</div>
            	<div class="main-formtab">
                	<table width="100%" cellpadding="0" cellspacing="0">
					<tr><th>商店</th>
					<td>
						<input class="easyui-textbox" id="ep_Store" name="ep_Store" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th>编码</th>
					<td>
						<input class="easyui-textbox" id="id" name="id" data-options="" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th>商店拥有者id</th>
					<td>
						<input class="easyui-textbox" id="user" name="user" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th>名称</th>
					<td>
						<input class="easyui-textbox" id="name" name="name" data-options="required:true,validType:['length[1,64]']" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th>商店描述</th>
					<td>
						<input class="easyui-textbox" id="describtion" name="describtion" data-options="required:true,validType:['length[1,256]']" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th>商店标签</th>
					<td>
						<input class="easyui-textbox" id="keywords" name="keywords" data-options="validType:['length[0,32]']" style="width:200px;height: 25px;" type="text"/>
					</td>
					</tr>
					<tr><th>商店类型 </th>
					<td>
						<input class="easyui-textbox" id="storeType" name="storeType" data-options="required:true" style="width:200px;height: 25px;" type="text"/>
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
	           	<div class="main-formtab">
	               	<table width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
			<div class="main-formbox">
	       		<div class="formtit">第二</div>
	           	<div class="main-formtab">
	               	<table width="100%" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
		<!--tab 二  	结束-->
	</div>
	<div style="text-align:center;">
		<div class="main-btnbox" style="margin:0 auto;width: 170px;">
			<button class="main-button mr40 mb2 fl" type="submit">保存</button>
			<a class="main-button-cancel mb2 fl" href="javascript:cancelWindow()" ><span class="btntxt">取消</span></a>
		</div>
	</div>
	</form>
	</div>
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
	function saveStore(){
		try{
			if($("#storeForm").form("validate")){
				WaitingBar.getWaitingbar("addstore","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#storeForm").serialize();
				jQuery.post("${cxt}/ecommerce/store/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addstore").hide();
					if(data.status){
						showMsg("info","添加商店成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加商店失败!");
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
