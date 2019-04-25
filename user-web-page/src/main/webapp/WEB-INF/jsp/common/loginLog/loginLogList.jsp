<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="org.csr.core.Constants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords" />
  	<%@ include file="../common/common.jsp"%>
	<title>登录日志--${system_name}</title>  
  
</head>
<body> 
<%@ include file="../common/header.jsp"%>
<!-- start 内容 -->
<div class="content-wrap ml10">
	<!-- start adv -->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<!-- end adv -->
	<!-- start 主要内容区域 -->
	<table class="e-main-con-box" width=100% >
		<tr>
			<td class="e-main-con-left">
				<!--start left -->
				<div class="e-tabchange-wrap">
					<div class="e-tabchange-bar">
						<span class="title">登录日志</span>
					</div>
					<div class="e-tabchange-bd">
						<!-- start 搜索 -->
						<div class="e-search-wrap mb10">
						<form onsubmit="return reloadData();">
							<table width="100%">
								<tr>
									<td class="e-search-con">
									<ul>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">用户名:</label><input id="loginName" type="text" style="width:155px; height:22px;"/></li>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">姓名:</label><input id="name" type="text" style="width:155px; height:22px;"/></li>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">登录日期:</label><div class="expand-date" style="display:inline;"><input id="datepickerStart" name="startDate" type="text" class="easyui-datebox" data-options="editable:false,validator:setMaxDate,maxDate:'datepickerEnd'" style="width:155px; height:22px;" /></div></li>
										<li class="e-search-td"><label class="mr8 lab" style="width:60px;">至:</label><div class="expand-date" style="display:inline;"><input id="datepickerEnd" name="endDate" type="text" class="easyui-datebox" data-options="editable:false,validator:setMinDate,minDate:'datepickerStart'" style="width:155px; height:22px;" /></div></li>
									</ul>
									</td>
									<td class="e-search-btn-box" style="width:94px;">
										<button class="btn" type="submit" >搜索</button>
									</td>
								</tr>
							</table>
						</form>
						</div>
						<!-- end 搜索 -->
						<div class="main-tableContainer cl-margin">
							<!--start 表格操作btn -->
							<div class="e-tabbtn-box mb2 main-expand-form">
								 <a class="tabbtn fr" href="javascript:exportToExcel();">导出</a>
								 <div class="fr mr10 expand-dropdown-select">
									<select id="cc" class="easyui-combobox" data-options="editable:false," style="width:100px; height:25px;">
										<option value="-1">1个天以前</option>
									 	<option value="-30">1个月以前</option>
									    <option value="-90">3个月以前</option>
									    <option value="-180">6个月以前</option>
									    <option value="-365">1年以前</option>
									</select>
								 </div>
								 <div class="fr mr10">
								 	<label class="mr8 lab" style="width:60px;">导出后是否删除日志:</label>
								 	<input id="isDelete" name="isDelete" value="1" type="checkbox"/>
								 </div>
							</div>
							<!--end 表格操作btn -->
							<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
						</div>
					</div>
				</div>
				<!--end left -->
			</td>
			<!-- <td class="e-main-con-right" style="width:290px;">
				start right
				<div class="e-sm-tabchange-wrap ml15" id="tab">
					<h2 class="e-sm-tabchange-bar">
						<a href="javascript:;" class="curr">机构</a>
						<a href="javascript:;">我的题库</a>
					</h2>
					<div class="e-sm-tabchange-bd">
						start search
						<div class="e-tree-search">
							<input type="text" placeholder="搜索" />
							<button class="search-btn"></button>
						</div>
						end search
						<div class="e-tree-wrap">
							start tree
							<div class="e-tree-con curr">
								<ul id="orgTree" class="m-expand-tree"></ul>  
							</div>
							end tree
							start 我的试题库tree
							<div class="e-tree-con">
							我的tree树的内容
							</div>
							end 我的试题库tree
							<div class="e-tree-line"></div>
						</div>
					</div>
				</div>
				end right
			</td> -->
		</tr>
	</table>
	<!-- end 主要内容区域 -->
</div>
<!-- end 内容 -->
<!--版权开始-->
<%@ include file="/include/footer.jsp"%>
<!--版权结束-->

 <script  type="text/javascript"> 
 var agenciesId;
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/loginLog/ajax/list.action',
			collapsible:true,
			showfolder:true,
			border:false,
			emptyMessage:true,
			fitColumns:true,
			idField:'userId',
			scrollbarSize:0,
			queryParams:function(){
				try{
					return {
					"auto":true,//自动拼接
					"loginName!s":"like:$"+$("#loginName").val(),
					"userName!s":"like:$"+$("#name").val(),
					"loginTime!date!start":">=:$"+$("#datepickerStart").datebox("getValue"),
					"loginTime!date!end":"<=:$"+$("#datepickerEnd").datebox("getValue"),
					};
				}catch (e) {alert(e);}
			},
			
			columns:[[
				{title:'用户名',field:'loginName',width:120},
				{title:'姓名',field:'userName',width:120},
				{title:'登录方式',field:'logintype',width:120},
				{title:'登录时间',field:'loginTimeString',width:140},
				{title:'退出时间',field:'logoutTimeString',width:140},
				{title:'IP地址',field:'ipAdress',width:120},
				{title:'退出方式',field:'exitType',dictionary:'exitType',width:100},
				{title:'操作',field:'oper_',width:100,selected:true,
					formatter:function(value,rec){
						return "<a href=\"javascript:preOperationLog('"+rec.id+"');\">操作日志</a>";
					}
				}
			]],
			pagination:true,
		});
	});
	
	 //操作日志
	function preOperationLog(id){
		$('#win').window({ 
			title:"操作日志",   
		    width:800,    
		    height:600,
		    iframe:"${cxt}/loginLog/preListOperationLog.action?loginLogId="+id,
		    modal:true
		});
	}
	 function exportToExcel(){
		 var meg = $("#cc").combobox("getText");
		 var isDelete = 2;
		 if($("#isDelete").is(":checked")){
			 meg = "并删除"+$("#cc").combobox("getText");
			 isDelete = 1;
		 }
		 showConfirm("是否导出"+meg+"的数据",function(){
			 download_file("${cxt}/loginLog/ajax/loginLogExportToExcel.action?monthAgo="+$("#cc").combobox("getValue")+"&isDelete="+isDelete);
		 });
		 if($("#isDelete").is(":checked")){
			 callbackPage();
		 }
	 }
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
		return false;
	};
    
   //回调方法
	function callbackPage(){    
		reloadData();
	};
</script>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>