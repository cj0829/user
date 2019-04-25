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
	<div class="content-wrap ml20 mr20">
		<!-- start now Navigation-->
		<div class="current-nav-bar mt10 mb10">
			当前位置：
			<a href="javascript:;">首页</a><span> > </span>
			<a href="javascript:;">试题库</a><span> > </span>
			<a href="javascript:;" class="curr">编辑试题</a>
		</div>
		<!-- end now Navigation-->
		<!-- start 主要内容区域 -->
		<div class="e-tabchange-wrap mt10">
			<div class="e-tabchange-bd main-expand-form">
				<!-- start one -->
				<div class="e-form-mod">
					<h2 class="e-form-mod-tit mb20"><span class="mr5">第二步</span>选择考生</h2>
					<table class="e-form-tab" width="100%" >
						<tr>
							<th class="e-form-th" style="width:105px;"><label>选择考生方式</label></th>
							<td class="e-form-td">
								 <label><input class="vera mr8" type="radio" name="selectionway" value="1" />指派</label>
								<input class="vera mr8 ml20" type="radio" name="selectionway" value="1" id="intelligent" /><label for="intelligent">申请</label>
							</td>
						</tr>
						<tr>
							<th class="e-form-th"><label>选择考生对象</label></th>
							<td class="e-form-td">
								<button class="btn" type="button" onclick="selectCandidates();">选择考生</button>
							</td>
						</tr>
					</table>
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
		    iframe:"${cxt}/demo/user/selectCandidates.jsp",
		    modal:true
		});
	}
	</script>
  </body>
</html>
