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
				<!--table表格开始-->
		        <div class="main-tableContainer cl-margin">
		        	<!--<h2 class="e-tab-tit mb5">申请<a href="javascript:;" class="alink fr">添加审批人</a></h2>-->
		            <div class="e-tab-tit mb5">
						<span class="tit fl">申请</span>
						<div class="fr">
							<a href="javascript:;" class="alink">添加审批人</a>
						</div>
					</div>
		            <table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
		        </div>
		        <!--table表格结束-->
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
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/user/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户登录名',field:'loginName',width:150},
				{title:'用户编码',field:'userId',width:150},
				{title:'用户名',field:'name',width:150},
				{title:'主域',field:'organizationName',width:150},
				{title:'组织',field:'agenciesName',width:150},
				{title:'工作',field:'trainWorkName',width:150},
				{title:'操作',field:'id_1',width:230,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a id='menu_"+rec.id,"' ");
						buttonHtml.push("href=\"javascript:menuShow('"+rec.id+"');\"");
						buttonHtml.push("class=\"easyui-butcombo-menu\">操作</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
				]],
			pagination:true
		});
	});
	//下拉菜单
	$(function(){dropdown("#examgl","#examgl .e-menu-navbg");});
	//tab切换
	$(function(){tabsclick("#tab .e-sm-tabchange-bar a","#tab .e-sm-tabchange-bd .e-tree-con");});
	</script>
  </body>
</html>
