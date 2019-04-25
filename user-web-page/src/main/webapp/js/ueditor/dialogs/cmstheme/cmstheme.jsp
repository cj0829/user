<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge " />
<meta content="CMS系统" name="keywords" />
<script src="${jsPath}/js/jquery.min.js" type="text/javascript"></script>
<script src="${jsPath}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${jsPath}/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${jsPath}/js/jquery.extend.validatebox.js"></script>
  <title>添加新闻--${system_name}</title>  
</head>
<body>
<!-- start 内容 -->
<form action="#" method="post" name="cmsTopicForm" id="cmsTopicForm" onsubmit="return saveCmsTopic(this);">
<div class="e-tabbox">
	<!-- start one -->
	<div class="e-form-mod">
		<div class="e-form-mod-bd">
			<table class="e-form-tab" width="100%">
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>主题标题：</label>
					</th>
					<td class="e-form-td">
						<input id="title" name="title" value="实验评价"/>
		              	<span class="advert" id="titleMsg"></span>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" >
						<label>标题：</label>
					</th>
					<td class="e-form-td">
						<input id="titleName" name="titleName" value="实验评价"/>
		              	<span class="advert" id="titleMsg"></span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<!-- end one -->
</div>
</form>
<script type="text/javascript">
var dialog;
jQuery(document).ready(function(){
	dialog = parent.$EDITORUI[window.frameElement.id.replace( /_iframe$/, '' )];
	initButtons();
	
});

/* 初始化onok事件 */
function initButtons() {
    dialog.onok = function () {
    	saveCmsTopic();
    	return false;
    };
}
	//提交表单的方法
	function saveCmsTopic(){
		if($("#cmsTopicForm").form("validate")){
			var params=jQuery('#cmsTopicForm').serialize();
			jQuery.post("${cxt}/cmsTopic/ajax/add.action",params,function call(data){
				if(data.status){
					if(data.data){
						dialog.editor.execCommand('insertfile',[{url:"${cxt}/cmsTopic/preAddComment.action?topicId="+data.data,title:$("#titleName").val()}]);
						dialog.close();
					}else{
						alert("添加失败!");
					}
					
				}else{
					if(data.message){
						alert(data.message);
					}else{
						alert("添加失败!");
					}
				}
			},"json");
		}	
		return false;
	}
</script>
</body>
</html>
