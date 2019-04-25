<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
	 <%@ include file="/include/user.jsp"%> 
	<%--<%@ include file="../common/common.jsp"%>--%>
 	<title>签到系统--${system_name}</title>
</head>
  
  <body>
	<!--start navigation-->
	<div class="nav-bar">
		<ul class="nav ml20">
		 <li class="exam-nav-item curr">
		 	<a href="javascript:;" class="e-menu-a"><em class="drop-down-arrow"></em>基础数据</a>
		 	<div class="e-menu-navbg">
		 		<div class="e-menu-navbox">
			 		<div class="e-menu-navthre">
			 			<a class="twoa" title="数据字典" href="/user/dictionary/preList.action" class="menu-nava t_elli"><span>数据字典</span><em>></em></a>
			 			<div class="e-menu-navthrebox">
			 				<a title="行政区" href="/user/province/preList.action" class="menu-nava t_elli">行政区</a>
			 				<a title="行政区1" href="/user/province/preList.action" class="menu-nava t_elli">行政区</a>
			 				<a title="行政区2" href="/user/province/preList.action" class="menu-nava t_elli">行政区</a>
			 			</div>
			 		</div>
			 		<div class="e-menu-navthre">
			 			<a class="twoa" title="数据字典2" href="/user/dictionary/preList.action" class="menu-nava t_elli"><span>数据字典2</span><em>></em></a>
			 			<div class="e-menu-navthrebox">
			 				<a title="系统参数2" href="/user/province/preList.action" class="menu-nava t_elli">系统参数2</a>
			 				<a title="系统参数1" href="/user/province/preList.action" class="menu-nava t_elli">系统参数2</a>
			 				<a title="系统参数2" href="/user/province/preList.action" class="menu-nava t_elli">行政区</a>
			 			</div>
			 		</div>
			 		<div class="e-menu-navthre">
			 			<a  title="数据字典2" href="/user/dictionary/preList.action" class="menu-nava t_elli"><span>数据字典2</span></a>
			 		</div>
		 		</div>
		 	</div>
 		 </li>
 		 <li class="exam-nav-item">
	 		 <a href="javascript:;" class="e-menu-a"><em class="drop-down-arrow"></em>系统管理</a>
	 		 <div class="e-menu-navbg">
		 		 <div class="e-menu-navbox">
			 		 <a title="角色管理" href="/user/role/preList.action" class="menu-nava t_elli">角色管理</a>
			 		 <a title="机构管理" href="/user/organization/preList.action" class="menu-nava t_elli">机构管理</a>
			 		 <a title="用户管理" href="/user/user/preList.action" class="menu-nava t_elli">用户管理</a>
			 		 <a title="区域管理" href="/user/district/preList.action" class="menu-nava t_elli">区域管理</a>
			 		 <a title="积分规则" href="/user/pointsRules/preList.action" class="menu-nava t_elli">积分规则</a>
			 		 <a title="积分等级" href="/user/pointsLevelRules/preList.action" class="menu-nava t_elli">积分等级</a>
			 		 <a title="参数管理" href="/user/organizationParameter/preList.action" class="menu-nava t_elli">参数管理</a>
			 		 <a title="登录日志管理" href="/user/loginLog/preList.action" class="menu-nava t_elli">登录日志管理</a>
			 		 <a title="操作日志管理" href="/user/operationLog/preList.action" class="menu-nava t_elli">操作日志管理</a>
			 		 <a title="积分日志管理" href="/user/userPointsLog/preList.action" class="menu-nava t_elli">积分日志管理</a>
		 		 </div>
	 		 </div>
 		 </li>
 		 <li class="exam-nav-item">
	 		 <a href="javascript:;" class="e-menu-a"><em class="drop-down-arrow"></em>运维管理</a>
	 		 <div class="e-menu-navbg"><div class="e-menu-navbox">
	 		 	<a title="注册机构" href="/user/registerOrganization/preList.action" class="menu-nava t_elli">注册机构</a>
	 		 </div>
 		 </div>
 		 </li>
 		 <li class="exam-nav-item">
	 		 <a href="javascript:;" class="e-menu-a"><em class="drop-down-arrow"></em>内容管理</a>
	 		 <div class="e-menu-navbg">
		 		 <div class="e-menu-navbox">
			 		 <a title="发布位置" href="/user/position/preList.action" class="menu-nava t_elli">发布位置</a>
			 		 <a title="栏目管理" href="/user/node/preList.action" class="menu-nava t_elli">栏目管理</a>
			 		 <a title="模板管理" href="/user/template/preList.action" class="menu-nava t_elli">模板管理</a>
			 		 <a title="新闻管理" href="/user/news/preList.action" class="menu-nava t_elli">新闻管理</a>
			 		 <a title="焦点管理" href="/user/focus/preList.action" class="menu-nava t_elli">焦点管理</a>
		 		 </div>
	 		 </div>
 		 </li>
 		 <li class="exam-nav-item">
	 		 <a href="javascript:;" class="e-menu-a"><em class="drop-down-arrow"></em>信息库查询统计</a>
	 		 <div class="e-menu-navbg">
		 		 <div class="e-menu-navbox">
			 		 <a title="产品信息查询" href="/user/basicInfo/preProductNameFindList.action" class="menu-nava t_elli">产品信息查询</a>
			 		 <a title="产品信息统计" href="/user/basicInfo/preStatisticalProductAttributes.action" class="menu-nava t_elli">产品信息统计</a>
			 		 <a title="招投标信息查询" href="/user/biddingBasicInfo/preProductNameFindList.action" class="menu-nava t_elli">招投标信息查询</a>
			 		 <a title="招投标信息统计" href="/user/biddingBasicInfo/preStatisticalArea.action" class="menu-nava t_elli">招投标信息统计</a>
			 		 <a title="按地域比对" href="/user/biddingBasicInfo/preComparativeArea.action" class="menu-nava t_elli">按地域比对</a>
			 		 <a title="按时间比对" href="/user/biddingBasicInfo/preComparativeTime.action" class="menu-nava t_elli">按时间比对</a>
		 		 </div>
	 		 </div>
 		 </li>
 		</ul>
 		<div class="user-box fr mr20">
 			<div class="main-skin-box">
 				<div class="skin-ico">
 					<a title="换肤" class="imgbox" href="javascript:;"><em class="img"></em></a>
 				</div>
 				<div class="skin-menu" style="display:none;">
 					<ul>
 						<li class="selected mt8">
	 						<span class="col2"></span>
							<a href="javascript:;">黑透明</a>
							<em></em>
 						</li>
 						<li class="mt8">
 							<span class="col5"></span>
							<a href="javascript:;">紫透明</a>
							<em></em>
 						</li>
 						<li class="mt8">
 							<span class="col6"></span>
							<a href="javascript:;">可爱粉</a>
							<em></em>
 						</li>
 						<li class="mt8">
 							<span class="col7"></span>
							<a href="javascript:;">橘黑色</a>
							<em></em>
 						</li>
 					</ul>
 				</div>
 			</div>
 			<div class="users">
 				<div class="img" title="super">
 					<a href="/user/sysUser/preHome.action">
	 					<img height="24" width="24" src="/user/css/img/user_img_50.jpg" />
	 					<span class="name t_elli">super</span>
 					</a>
 				</div>
 			</div>
 			<a class="users-logout" title="退出" href="/user/person/logout.action"><i class="icon-logout"></i></a>
 		</div>
	</div>
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
	//下拉菜单
	$(function(){
		 $(".exam-nav-item").mouseover(function(){
			$(this).children("a.e-menu-a").addClass("hover");
			$(this).children(".e-menu-navbg").show();
			var bg=$(this).attr("adv-box");
		}),
		$(".exam-nav-item").mouseout(function(){
			$(this).children("a.e-menu-a").removeClass("hover");
			$(this).children(".e-menu-navbg").hide();
		});
		$(".e-menu-navthre").mouseover(function(){
			$(this).children("a.twoa").addClass("hover");
			$(this).children(".e-menu-navthrebox").show();
		}),
		$(".e-menu-navthre").mouseout(function(){
			$(this).children("a").removeClass("hover");
			$(this).children(".e-menu-navthrebox").hide();
		});
		$(".main-skin-box").mouseover(function(){
			$(this).addClass("hover");
			$(".skin-menu").show();
			$(".skin-menu li").click(function(){
				$(this).addClass("selected").siblings().removeClass("selected");
			});
		}),
		$(".main-skin-box").mouseout(function(){
			$(this).removeClass("hover");
			$(".skin-menu").hide();
		});
		//dropdown("#user","#user .users-infobox");
	});
	</script>
  </body>
</html>
