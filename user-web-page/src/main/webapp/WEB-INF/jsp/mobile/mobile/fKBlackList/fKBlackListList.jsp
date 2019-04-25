<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<title>黑名单--${system_name}</title>  
  <style>
  		.tabbtn{
  			margin:0 5px;
  		}
  	</style>
</head>
<body>
<%@include file="../common/header.jsp"%>
<!--content开始-->
<div class="content-wrap ml20 mr20">
	<!--当前标题开始-->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!--当前标题结束-->
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<!-- 搜索 start  -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">身份证号:</label><input id="idCard" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:40px;">机构</label>
						<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="rememberAll:true"  multiple="true" style="width:115px; height:27px;"/></li>
					</ul>
					</td>
				
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="submit" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<%-- <button class="tabbtn fr ml10" type="button" onclick="javascript:importBlackList();">批量导入黑名单</button> --%>
					<button class="tabbtn fr ml10" type="button" onclick="javascript:importBlackListImg();">批量导入黑名单照片</button>
					<button class="tabbtn fr" type="button" onclick="addFKBlackList();">新建</button>
					<button class="tabbtn fr mr5" type="button" onclick="deleteFKBlackLists();">删除</button>
				</div>
				<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0" class="tablemode"></table>
			</div>			
			 <!--table表格结束-->
		</div>
	</div>
</div>
<!--content结束-->
<!--版权开始-->
<%@include file="../common/footer.jsp"%>
<!--版权结束-->

<script type="text/javascript">
	$(function(){
		$("#datagridList").datagrid({
			nowrap: true,
			url:"${cxt}/mobile/fKBlackList/ajax/list.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:"id",
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:"ck",checkbox:true},
					{title:"身份证号",field:"idCard",width:150},
					{title:"名称",field:"name",width:150},
					{title:"照片",field:"logType",width:150,formatter:function(value,rec){
						var buttonHtml=[];
						if(rec.facepath){
							buttonHtml.push("<img width=\"50px\" height=\"50px\" src=\""+rec.facepath+"\">"," ");
						}
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}},
					{title:"备注",field:"remark",width:150},
					{title:"机构",field:"agenciesName",width:150},
					{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a href=\"javascript:editFKBlackList('"+rec.id+"');\">编辑</a>"," ");
					buttonHtml.push("<a href=\"javascript:addUserFace('"+rec.id+"','"+rec.loginName+"');\">添加人脸照片</a>"," "); 
					buttonHtml.push("<a href=\"javascript:deleteFKBlackList('"+rec.id+"');\">删除</a>"," ");
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	//查询方法名称
	function queryParams(){
		try{
			return $.param({
			"idCard":$("#idCard").val(),
			"agenciesIds":$("#agenciesId").combogrid("getValues")
			},true);
		}catch (e) {alert(e)}
		return {};
	};
	
	function addFKBlackList(){
		$("#win").window({
			title:"新建",
			width:800,
			height:600,
			iframe:"${cxt}/mobile/fKBlackList/preAdd.action",
			modal:true
		});
	}
	
	// 编辑
	function editFKBlackList(id){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:600,
			iframe:"${cxt}/mobile/fKBlackList/preUpdate.action?id="+id,
			modal:true
		});
	}
	
	//删除全部
	function deleteFKBlackLists(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/mobile/fKBlackList/ajax/delete.action",param, function call(data){
			WaitingBar.getWaitingbar("deletes").hide();
				if(data.status){
					showMsg("info","删除成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败!");
					}
				}
			},"json"); 
		}
	}
	
	// 删除
	function deleteFKBlackList(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...").show();
			jQuery.post("${cxt}/mobile/fKBlackList/ajax/delete.action",{"ids":id}, function call(data){
				WaitingBar.getWaitingbar("delete").hide();
				if(data.status){
					showMsg("info","删除成功");
					reloadData();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","删除失败!");
					}
				}
			},"json"); 
		}
	}
	
	//上传人脸
	function addUserFace(id){
		 $('#win').window({
			title:"授权安全资源",
		    width:700,
		    height:500,
		    iframe:"${cxt}/mobile/fKBlackList/preUserFace.action?id="+id,
		    modal:true
		}); 
	}
	
	//批量导入用户
	function importBlackList(){
		if(confirm("确认是否导入黑名单!!")){
			 $("#win").window({
				title:"导入用户",
			    width:800,
			    height:500,
			    iframe:"${cxt}/mobile/fKBlackList/preImportBlackList.action",
			    modal:true
			}); 
		}
	}
	
	//批量导入用户
	function importBlackListImg(){
		if(confirm("确认是否导入黑名单照片!!")){
			 $("#win").window({
				title:"导入用户",
			    width:800,
			    height:500,
			    iframe:"${cxt}/mobile/fKBlackList/preImportUserImg.action",
			    modal:true
			}); 
		}
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid("reload");
		return false;
	}
    
   //回调方法
	function callbackPage(){
		reloadData();
	}
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
