<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>武汉大学-测绘地理信息虚拟仿真实验教学中心</title>
    <meta name="keywords" content="武汉大学-测绘地理信息虚拟仿真实验教学中心">
    <meta name="description" content="武汉大学-测绘地理信息虚拟仿真实验教学中心">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=2.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <link href="${cxt }/html/css/wh.css" rel="stylesheet" type="text/css" /> 
	<script src="${cxt }/js/jquery.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${jsPath}/js/jquery.easyui.min.js"></script>
	<script src="${cxt }/js/application.js" type="text/javascript"></script>
	<script type="text/javascript" src="${jsPath}/js/jquery.media.js"></script>
	<%--判断浏览器的js--%>
  	<script type="text/javascript" src="${jsPath}/js/nicEdit/ueditor-core.js"></script>
	
	<link href="${jsPath}/themes/default/easyui.css" rel="stylesheet" type="text/css" />
  </head>
  
  <body>
    <div class="wh_top area">
		<ul>
			<li class="left fl">
				<a class="logo" href="javascript:;"><img src="${cxt }/image/logo.png" /></a>
				<p class="txt">测绘地理信息虚拟仿真实验教学平台</p>
			</li>
		</ul>
	</div>
	<table class="navbar">
		<tr>
			<td>
		<script src="${cxt}/cms/menu.action?websiteId=236" type="text/javascript"></script>
		</td>
		</tr>
	</table>
	<div class="wh_detailcon area mt15">
<c:if test="${contentType=='image'}">
<img src="${cxt}/file/ajax/inline.action?id=${id}" title="${title}"></img>
</c:if>
<c:if test="${contentType=='flash'}">
<div id="videoPreview" class="videoPreview">
<object type="application/x-shockwave-flash" data="${cxt}/js/jwplayer/player.swf" width="950" height="600" bgcolor="#000000" id="mediaplayer" name="mediaplayer" tabindex="0">
	<param name="flashvars" value="${flvurl}">
	<param name="allowfullscreen" value="true">
	<param name="allowscriptaccess" value="always">
	<param name="seamlesstabbing" value="true">
	<param name="wmode" value="opaque">
</object>
</div>
</c:if>
<c:if test="${contentType=='media'}">
<embed src="${cxt}/file/ajax/inline.action?id=${id}" type="video/x-ms-asf-plugin" width="950" height="600" autostart="false" loop="true" title="${title}">
</c:if>
<c:if test="${contentType=='file'}">在此下载附件：<a href="${cxt}/file/ajax/download.action?id=${id}">${title}</a>
</c:if>
<c:if test="${contentType=='txt'}">${textContent}</c:if>
<c:if test="${contentType=='pdf'}">
<a id="media"></a>
<script type="text/javascript">
jQuery(document).ready(function() {
	//导入的试卷
	if(isAcrobatPluginInstall()){
		$("#media").media({
			src : "${cxt}/file/ajax/inline.action?id=${id}&.pdf",
			width : "100%",
			height : "800"});
	}
	
});

//检测是否有安装adobeReader
function isAcrobatPluginInstall() { 
	var browser = UE.browser;
	var flag = false;
	// 下面代码都是处理IE浏览器的情况 
	if (browser.ie) {
		for (x = 2; x < 10; x++) {
			try {
				oAcro = eval("new ActiveXObject('PDF.PdfCtrl." + x + "');");
				if (oAcro) {
					flag = true;
				}
			} catch (e) {}
		}
		try {
			oAcro4 = new ActiveXObject('PDF.PdfCtrl.1');
			if (oAcro4)
				flag = true;
		} catch (e) {}
		try {
			oAcro7 = new ActiveXObject('AcroPDF.PDF.1');
			if (oAcro7)
				flag = true;
		} catch (e) {}
		try {
			oAcroAP = new ActiveXObject('AcroPDF.PDF');
			if (oAcroAF)
				flag = true;
		} catch (e) {}
		try {
			oAcroPP = new ActiveXObject('PDF.PdfCtrl');
			if (oAcroPP)
				flag = true;
		} catch (e) {}
		try {
			oAcroAA = new ActiveXObject('Adobe Acrobat');
			if (oAcroAA)
				flag = true;
		} catch (e) {}
		try {
			oAcroAPP = new ActiveXObject('Adobe PDF Plug-in');
			if (oAcroAPP)
				flag = true;
		} catch (e) {}
	}
	// 如果是firefox浏览器 
	else if (navigator.plugins && navigator.plugins.length) {
		for (x = 0; x < navigator.plugins.length; x++) {
			if (navigator.plugins[x].name == 'Adobe Acrobat' || navigator.plugins[x].name == 'Chrome PDF Viewer' || navigator.plugins[x].name == 'Adobe Reader' || navigator.plugins[x].name == 'Adobe PDF' || navigator.plugins[x].name == 'Chromium PDF Viewer')
				flag = true;
		}
	}
	if (flag) {
		return true;
	} else {
		alert("对不起,您还没有安装PDF阅读器软件呢,为了方便预览PDF文档,请选择安装,重启浏览器再进入考试！");
		location = "${cxt}/html/AdbeRdr11000_zh_CN11.0.1.210.1459417824.exe";
	}
	return flag;
}
</script>
</c:if>
<c:if test="${contentTyp=='none'}">您上传的附件，无法正常打开。请您检查您的文件格式！</c:if>
	
	
	
	
	
	
	
	
	</div>
	<div class="wh_liank area ovh">
		<em>友情链接：</em>
		<a href="javascript:;">夏坚白奖</a><span>.</span>
		<a href="javascript:;">老版入口</a><span>.</span>
		<a href="javascript:;">武汉大学</a><span>.</span>
		<a href="javascript:;">教育部</a><span>.</span>
		<a href="javascript:;">科技部</a><span>.</span>
		<a href="javascript:;">基金委</a><span>.</span>
		<a href="javascript:;">国家测绘地理信息局</a><span>.</span>
	</div>
	<div class="wh_foot ovh">
		<div class="area">
		<ul class="left">
			<li>地址：湖北省武汉市珞喻路129号</li>
			<li>电话：027-68778558</li>
			<li>邮箱：vsmlab@sgg.whu.edu.cn</li>
		</ul>
		<div class="right">
			<div class="rtop">
				<a class="logo" href="javascript:;"><img src="${cxt }/image/logo.png" /></a>
				<a class="lin mr15" href="javascript:;">网页制作csr </a><span class="lin">|</span>
				<a  class="lin" href="javascript:;">威远图易 </a>
			</div>
			<p class="rbottom">@copyright 武汉大学测绘地理信息虚拟仿真实验教学中心湖北省武汉市珞喻路129号 </p>
		</div>
		</div>
	</div>
</body>
</html>
