<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
 	<title>添加打卡记录管理--${system_name}</title>  
</head>
<body>
    <img src="${url }" width="400" height="400" alt="暂未找到此图片"/>
<script type="text/javascript">
 	jQuery(document).ready(function(){
		$("#easyui-tabs").tabs({
			border:false
		});
	});
	//提交表单的方法
	function savePunchRecord(){
		try{
			if($("#punchRecordForm").form("validate")){
				WaitingBar.getWaitingbar("addpunchRecord","数据添加中，请等待...","${jsPath}").show();
				var params=jQuery("#punchRecordForm").serialize();
				jQuery.post("${cxt}/mobile/punchRecord/ajax/add.action",params,function call(data){
					WaitingBar.getWaitingbar("addpunchRecord").hide();
					if(data.status){
						showMsg("info","添加打卡记录管理成功");
						top.callbackPage();
						cancelWindow();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","添加打卡记录管理失败!");
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
