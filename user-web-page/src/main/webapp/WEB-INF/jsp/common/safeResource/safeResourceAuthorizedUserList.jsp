<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
	<link type="text/css" href="${cxt}/css/training/dark/userSpecialFunctionPoint.css" rel="stylesheet" />
 	<title>安全资源取消授权--${system_name}</title>
</head>
<body>
<div class="e-tabbar">
    <a class="t-btn" href="${cxt}/safeResource/preNotAuthorizedUserList.action?safeResourceCollectionId=${safeResourceCollectionId}">未授权</a>
    <a class="t-btn curr" href="javascript:void(0);">已授权</a>
</div>
<div class="e-tabbox main-expand-form">
	<div class="e-search-wrap">
		<form name="searchForm" id="searchForm" onsubmit="return reloadData()">
		<table width="100%">
			<tr>
				<td class="e-search-con">
				<ul>
					<li class="e-search-td"><label class="mr8 lab" style="width:60px;">用户名</label><input id="loginName" type="text" style="width:115px; height:22px;" /></li>
					<li class="e-search-td"><label class="mr8 lab" style="width:60px;">姓名</label><input id="name" type="text" style="width:115px; height:22px;" /></li>
				</ul>
				</td>
				<td class="e-search-btn-box" style="width:94px;">
					<button class="btn" type="submit" >搜索</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
	<div class="form-tab-wrap mt10">
		<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"></table>
	</div>
</div>
<!--弹出窗口开始-->
<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tabbar").height());
	};
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tabbar").height());
		});
	});
	 jQuery(document).ready(function(){ 
			$('#datagridList').datagrid({nowrap: true,
				url:"${cxt}/safeResource/ajax/authorizedUserList.action?safeResourceCollectionId=${safeResourceCollectionId}",
				collapsible:true,
				showfolder:true,
				border:false,
				fitColumns:true,
				idField:'id',
				scrollbarSize:0,
				showPageList : false,
				queryParams:queryParams,
				columns:[[
					{title:'用户名',field:'loginName',width:180},
					{title:'姓名',field:'name',width:180},
					{title:'操作',field:'id_1',width:100,
						formatter:function(value,rec){
							var buttonHtml=[];
							buttonHtml.push("<a href=\"javascript:deleteAuthorized('${safeResourceCollectionId}','"+rec.id+"');\">取消授权用户</a>"," ");
							return buttonHtml.join("");
						}
					}
				]],
				pagination:true
			});
		}); 
	
 	//删除用户安全资源
	function deleteAuthorized(collectionId,userId){
		var params={'safeResourceCollectionId':collectionId,'userId':userId};
	    //提交表单
		jQuery.post("${cxt}/userSafeResourceCollection/ajax/deleteAuthorized.action",params,function call(result){
			if(result.status){
				checkin = [];
				top.showMsg("success","取消用户安全资源成功");
				reloadData();
			}else{
				top.showMsg("error",result.message);
			}
		},'json');
	}
	//查询方法名称
	function queryParams(){
		try{
			return {
			'loginName':$("#loginName").val(),
			'name':$("#name").val(),
			};
		}catch (e) {}
		return {};
	};
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	}
	//关闭窗口。
	function cancelWindow(){
		top.$("#win").window('close');
	}
</script>
</body>
</html>