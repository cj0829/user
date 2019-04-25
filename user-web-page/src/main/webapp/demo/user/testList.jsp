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
	<!-- start now Navigation-->
		<div class="current-nav-bar mt10 mb10 ml20 mr20">
			当前位置：
			<a href="javascript:;">首页</a><span> > </span>
			<a href="javascript:;">试题库</a><span> > </span>
			<em>编辑试题</em>
		</div>
		<!-- end now Navigation-->
	<!--end navigation-->
	<div class="content-wrap ml20 mr20 mt10">
		
		<!-- start 主要内容区域 -->
		<div class="e-tabchange-wrap">
			<!-- start Navigation
			<div class="navigation-bar">
				<ul class="navigation-menu">
					<li><a href="javascript:;">首页</a></li>
					<li><a href="javascript:;">试题库</a></li>
					<li class="active"><a href="javascript:;">编辑试题</a></li>
				</ul>
			</div>-->
			
			<div class="e-tabchange-bd">
				<!-- start 搜索 -->
				<div class="e-search-wrap main-expand-form mb10">
				<table width="100%">
					<tr>
						<td class="e-search-con">
						<ul>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">题目</label><input type="text" style="width:115px; height:22px;" /></li>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">关键词</label><input type="text" style="width:115px; height:22px;" /></li>
							<li class="e-search-td">
								<label class="mr8 lab" style="width:60px;">状态</label>
								<select style="width:115px; height:22px;">
									<option>待评阅</option>
									<option>已评阅</option>
								</select>
							</li>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">试题类型</label><input type="text" style="width:115px; height:22px;" /></li>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">创建时间</label><input type="text" style="width:115px; height:22px;" /></li>
						</ul>
						</td>
						<td class="e-search-btn-box" style="width:94px;">
							<button class="btn" type="button" >搜索</button>
						</td>
					</tr>
				</table>
				</div>
				<!-- end 搜索 -->
				<div class="main-tableContainer cl-margin">
					<!--start 表格操作btn -->
					<div class="e-tabbtn-box mb2">
						<button class="tabbtn fr" type="button" >创建试题</button>
						<button class="tabbtn mr15 fr" type="button" >导出文档</button>
						<button class="tabbtn mr15 fr" type="button" >添加到试卷</button>
					</div>
					<!--end 表格操作btn -->
					<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
				</div>	
			</div>
		</div>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@include file="/include/footer.jsp"%>
	<!-- end foot -->
	<div id="win"></div>
	<script  type="text/javascript"> 
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
					/* 	var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editUser('"+rec.userId+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteUser('"+rec.userId+"');\">删除</a>"," ");
						buttonHtml.push("<a href=\"javascript:authorizedSecurityRole('"+rec.userId+"');\">授权安全角色</a>"," ");
						buttonHtml.push("<a href=\"javascript:addUserSpecialFunctionPoint('"+rec.userId+"');\">添加权限</a>"," ");
						buttonHtml.push("<a href=\"javascript:resetPassword('"+rec.userId+"');\">重置密码</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>"; */
						var buttonHtml=[];
						buttonHtml.push("<a id='menu_"+rec.id,"' ");
					/* 	buttonHtml.push("index=\""+itemData.learningRequestId,"\" ");
						buttonHtml.push("subjectCoursesId=\""+itemData.subjectCoursesId,"\" ");
						buttonHtml.push("learningRequestUserId=\""+rec.id,"\" ");
						buttonHtml.push("learningRequestUserState=\""+rec.learningRequestUserState,"\" "); */
						buttonHtml.push("href=\"javascript:menuShow('"+rec.id+"');\"");
						buttonHtml.push("class=\"easyui-butcombo-menu\">操作</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
				]],
			pagination:true
		});
	});
	function selectCandidates(){ 
		$('#win').window({ 
			title:"选择考生",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/demo/exam/selectCandidates.jsp",
		    modal:true
		});
	}
	
	</script>
  </body>
</html>
