<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge " />
<title>签到系统--</title>  
<script type="text/javascript" src="${cxt}/js/jquery.min.js"></script>
<style>
body{ margin:0; padding:0; font-size:12px;}
ul,li{ padding:0px; margin-left:10px;}
li{ line-height:22px;}
a{text-decoration:none; cursor:pointer; noFocusLine:expression(this.onFocus=this.blur()); outline:none; color:#333; }
a:hover,#ulwrap li.curr a{/*text-decoration:underline;*/ color:#41B3EF; }
</style>
</head>
<body>
<ul id="ulwrap">
    <li><a href="testList.jsp" target="mainFrame">试题列表</a></li>
    <li><a href="editQuestions.jsp" target="mainFrame">编辑试题</a></li>
    <li><a href="strate.jsp" target="mainFrame">策略</a></li>
    <li><a href="tab-option.jsp" target="mainFrame">tab选项卡</a></li>
    <li><a href="assign.jsp" target="mainFrame">考试中心-指派返回页</a></li>
    <li><a href="uploading.jsp" target="mainFrame">上传</a></li>
    <li><a href="ordinaryTab.jsp" target="mainFrame">普通表格</a></li>
    <li><a href="navigation.jsp" target="mainFrame">菜单</a></li>
    <li><a href="formClassify.jsp" target="mainFrame">表单分类显示</a></li>
</ul>
<script type="text/javascript">
//tab切换鼠标点击
function ulclick(Obj){
	$(Obj).click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
	});
}
//tab切换
$(function(){ulclick("#ulwrap li");});
</script>
</body>
</html>
