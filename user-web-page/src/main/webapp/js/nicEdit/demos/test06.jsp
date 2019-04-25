<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'test06.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="${cxt}/themes/default/easyui.css" rel="stylesheet" type="text/css" /> 
	<script src="${cxt}/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${cxt}/js/jquery.easyui.min.js"></script>
	<script src="${cxt}/js/nicEdit/jquery-nicEdit.js" type="text/javascript"></script>
</head>
<body>

<div id="menu"></div>

<div id="intro">
<p>NicEdit is highly configurable by passing the configuration when you create the NicEditor. Pass your configuration when you call:</p>
<p>new NicEditor({CONFIG HERE})</p>
<p>Add .panelInstance('ID TO TEXTAREA HERE') to add the editor to the textarea.</p>
<p>See the examples below:</p>
</div>
<br />

<div id="sample">

<script type="text/javascript">
jQuery(document).ready(function(){
	var ss=$.fn.nicEditor({fullPanel : true});
	ss.panelInstance('area1',{hasPanel : true});
	//ss.panelInstance('area1');
	//alert(ss);
	/* new nicEditor({contentEditable:false}).panelInstance('area1');
	new nicEditor({fullPanel : true}).panelInstance('area2');
	new nicEditor({iconsPath : '../nicEditorIcons.gif'}).panelInstance('area3');
	new nicEditor({buttonList : ['fontSize','bold','italic','underline','strikeThrough','subscript','superscript','html','image']}).panelInstance('area4');
	new nicEditor({maxHeight : 100}).panelInstance('area5'); */
});
</script>

<h4>Default (No Config Specified)</h4>
<p>new nicEditor().panelInstance('area1');</p>

<div cols="50" id="area133">adf</div>
<textarea cols="50" id="area1"></textarea>

<h4>All Available Buttons {fullPanel : true}</h4>
<p>new nicEditor({fullPanel : true}).panelInstance('area2');</p>
<textarea cols="60" id="area2">Some Initial Content was in this textarea</textarea>

<h4>Change Path to Icon File {iconsPath : 'path/to/nicEditorIcons.gif'}</h4>

<p>new nicEditor({iconsPath : 'nicEditorIcons.gif'}).panelInstance('area3');</p>
<textarea cols="50" id="area3"></textarea>

<h4>Customize the Panel Buttons/Select List</h4>

<p>{buttonList : ['fontSize','bold','italic','underline','strikeThrough','subscript','superscript']}</p>
<textarea cols="50" id="area4">HTML <b>content</b> <i>default</i> in textarea</textarea>	

<h4>Set a maximum expansion size (maxHeight)</h4>

<p>{maxHeight : 100}</p>
<textarea style="height: 100px;" cols="50" id="area5">HTML <b>content</b> <i>default</i> in textarea</textarea>	
</div>

</div>
<div id="adsfasdf"></div>
</body>
</html>
