<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="" name="keywords" />
	<%@ include file="../../WEB-INF/jsp/common/common/common.jsp"%>
 	<title>--${system_name}</title>  
</head>
  
  <body>
	<!--start navigation-->
	<%@ include file="../../WEB-INF/jsp/common/header/userMenu.jsp"%>  
	<!--end navigation-->
	<div id="pmt-tabwrap" class="content-wrap ml20 mr20">
		<!-- start now Navigation-->
		<div class="current-nav-bar mt10 mb10">
			当前位置：
			<a href="javascript:;">首页</a><span> > </span>
			<a href="javascript:;">试题库</a><span> > </span>
			<a href="javascript:;" class="curr">编辑试题</a>
		</div>
		<!-- end now Navigation-->
		<!-- start 主要内容区域 -->
		<table class="e-main-con-box" width="100%">
			<tr>
				<td class="e-main-con-left" style="width:209px;">
					<div class="e-sm-tabchange-wrap mr15">
						<h2 class="e-sm-tabchange-bar">
							<a href="javascript:;" class="curr">机构</a>
						</h2>
						<div class="e-sm-tabchange-bd">
							内容区域
						</div>
					</div>
				</td>
				<td class="e-main-con-right">
					<div id="easyui-tabs" style="width:100%;" class="expand-tabs">
						<div title="相册 " style="padding:10px 0;">
						 	<!-- start 搜索 -->
							<div class="e-search-wrap main-expand-form mb10">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">试卷名称</label><input type="text" style="width:115px; height:22px;" /></li>
									</ul>
									</td>
									<td class="e-search-btn-box" style="width:76px;">
										<button class="btn" type="button" >搜索</button>
									</td>
								</tr>
							</table>
							</div>
							<!-- end 搜索 -->
							<div class="main-tableContainer cl-margin">
								<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
							</div>	
							<!--start btn -->
							<div class="e-btn-box mt15">
								<button class="btn mr25" type="button" >保&nbsp;&nbsp;存</button>
								<button class="btn mr25" type="button" >上一步</button>
								<button class="btn" type="button" >下一步</button>
							</div>
							<!--end btn -->
						</div>
						<div title="最新照片" style="padding:10px 0;">
						</div>
					</div>
				</td>
			</tr>
		</table>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@include file="/include/footer.jsp"%>
	<!-- end foot -->
	<div id="win"></div>
	<script  type="text/javascript"> 
	$(function(){
		$("#easyui-tabs").tabs({
		    border:false
		});
		
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
				{field:'ck',checkbox:false},
				{title:'模板名称',field:'loginName',width:300},
				//{title:'用户编码',field:'userId',width:150},
				//{title:'用户名',field:'name',width:150},
				//{title:'主域',field:'organizationName',width:150},
				//{title:'组织',field:'agenciesName',width:150},
				//{title:'工作',field:'trainWorkName',width:150},
				{title:'操作',field:'id_1',width:200,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a id='menu_"+rec.id,"' ");
						//buttonHtml.push("href=\"javascript:editTemplate();\"");
						buttonHtml.push("class=\"easyui-butcombo-menu\" onclick=\"eidt("+rec.id+");\">编辑</a>"," ");
						buttonHtml.push("<a id='menu_"+rec.id,"' ");
						buttonHtml.push("href=\"javascript:;\"");
						buttonHtml.push("class=\"easyui-butcombo-menu\">删除</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
				]],
			pagination:true
		});
	});
	function eidt(id){ 
		
		$("#easyui-tabs").tabPanel("add",{
			title:"表格分类显示",
			href:"${cxt}/user/preUpdate.action?id="+id,
			type:"del",
			height:450
		});
		
	}
	</script>
  </body>
</html>
