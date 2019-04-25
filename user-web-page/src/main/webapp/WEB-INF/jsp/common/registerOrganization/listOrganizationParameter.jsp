<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>机构参数管理--${system_name }</title>  
</head>
<body>
<script type="text/javascript" src="${cxt}/header/userMenu.action?isShowMeun=false"></script>
	<div class="current-nav-bar mt10 mb10 ml20 mr20">
		当前位置：<em>首页</em><span> &gt; </span><em>运维管理</em>
		<span> &gt; </span><a href="${cxt}/registerOrganization/preList.action">注册机构</a>
	</div>

	<div class="content-wrap ml10">
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap main-expand-form mb10">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="e-search-con">
<!-- 					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">安全角色名称</label><input id="name" type="text" style="width:115px; height:22px;" /></li>
					</ul>
 -->					</td>
					<td class="e-search-btn-box" style="width:90px;">
						<button class="btn fl" type="submit">搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<a class="tabbtn fr" href="javascript:callbackOrganization();">返回注册机构</a>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
			</div>
		</div>
	</div>
</div>
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->
<div id="win"></div>
<script  type="text/javascript"> 
	  
	jQuery(document).ready(function(){ 
		$(window).unbind('div.right_content').bind('resize.right_datagrid', function(){
			$("#datagridList").datagrid('resize');
		});
		
		
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/registerOrganization/ajax/listOrganizationParameter.action?organizationId=${organizationId}',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'机构参数名',field:'name',width:120},
				{title:'机构参数值',field:'parameterValue',width:280},
				{title:'参数类型',field:'parameterType',dictionary:'parameterType',width:100},
				{title:'说明',field:'remark',width:180},
				{title:'操作',field:'oper_',width:100,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editParameter('"+rec.id+"');\">编辑</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true,
		});
	});
	// 编辑机构参数
	function editParameter(id){
	   $('#win').window({ 
			title:"编辑机构参数",
		    width:800,    
		    height:600,
		    iframe:"${cxt}/registerOrganization/preUpdateOrganizationParameter.action?organizationId=${organizationId}&parameterId="+id,
		    modal:true
		});
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
	}
   //回调方法
	function callbackPage(){    
		reloadData();
	}
	//返回	
	function callbackOrganization(){
		//window.location.href="${cxt}/backtrack";
		window.location.href="${cxt}/registerOrganization/preList.action";
	}
</script>
</body>
</html>