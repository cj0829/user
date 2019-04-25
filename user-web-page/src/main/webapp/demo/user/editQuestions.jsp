<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
	<%@ include file="/include/user.jsp"%>
 	<title>签到系统--${system_name}</title> 
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
					<table class="e-form-tab" width=100%>
						<tr>
							<th class="e-form-th" style="width:80px;" ><label>试卷小标题</label></th>
							<td class="e-form-td"><input class="inp" type="text" style="width:230px;" /></td>
						</tr>
						<tr>
							<th class="e-form-th"><label>是否共享</label></th>
							<td class="e-form-td">
								 <label><input class="vera mr8" type="radio" name="selectionway" value="1" />是</label>
							</td>
						</tr>
					</table>
				</div>
				<!-- end one -->
				<!--start btn -->
				<div class="e-btn-box mt5">
					<button class="btn mr25" type="button" >保&nbsp;&nbsp;存</button>
				</div>
				<!--end btn -->
			</div>
		</div>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@include file="/include/footer.jsp"%>
	<!-- end foot -->
	<script type="text/javascript">
	//下拉菜单
	$(function(){dropdown("#examgl","#examgl .e-menu-navbg");});
	</script>
  </body>
</html>
