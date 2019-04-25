<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
	<%@ include file="/include/user.jsp"%>
 	<title>签到系统--考试中心--指派返回页</title>  
</head>
  
  <body>
	<!--start navigation-->
	<script type="text/javascript" src="${cxt}/person/userHeader.action"></script> 
	<!--end navigation-->
	<div class="content-wrap ml20 mr20 mt10">
		<!-- start now Navigation-->
		<div class="current-nav-bar mt10 mb10">
			当前位置：
			<a href="javascript:;">首页</a><span> > </span>
			<a href="javascript:;">试题库</a><span> > </span>
			<a href="javascript:;" class="curr">编辑试题</a>
		</div>
		<!-- end now Navigation-->
		<!-- start 主要内容区域 -->
		<div class="e-tabchange-wrap">
			<div class="e-tabchange-bd main-expand-form">
				<!-- start one -->
				<div class="e-form-mod">
					<table class="e-form-tab" width="100%" >
						<tr>
							<th class="e-form-th" style="width:115px;"><label>tabel的选择上传</label></th>
							<td class="e-form-td">
								<a class="icon-spirit uploading ml5" href="javascript:;"></a>
							</td>
						</tr>
					</table>
					<div>第二种方式div 带label的</div>
					<div class="uploadingBox" ><label class="lab">选择上传</label><a class="icon-spirit uploading ml5" href="javascript:;"></a></div>
					<div>第三种方式div 不带label的</div>
					<div class="uploadingBox" >选择上传<a class="icon-spirit uploading ml5" href="javascript:;"></a></div>
					<div class="uploadingBox" ><label class="lab">选择上传小</label><a class="icon-spirit uploading-small ml5" href="javascript:;"></a></div>
					<!--table表格开始-->
			        <div class="main-tableContainer cl-margin">
			            <table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
			        </div>
			        <!--table表格结束-->
				</div>
				<!-- end one -->
				<!--start btn -->
				<div class="e-btn-box mt15">
					<button class="btn mr25" type="button" >保&nbsp;&nbsp;存</button>
					<button class="btn cancel" type="button" >取&nbsp;&nbsp;消</button>
				</div>
				<!--end btn -->
			</div>
		</div>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@include file="/include/footer.jsp"%>
	<!-- end foot -->
	<div id="win"></div>
  </body>
</html>
