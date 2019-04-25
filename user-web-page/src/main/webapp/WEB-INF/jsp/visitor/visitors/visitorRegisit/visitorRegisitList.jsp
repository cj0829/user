<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<title>安全刷卡记录--${system_name}</title>  
  
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
						<li class="e-search-td"><label class="mr8 lab" style="width:70px;">打卡人姓名:</label><input id="userName" class="main-inp" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td">
							<label class="mr8 lab" style="width:30px;">公司</label>
							<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="rememberAll:true"  multiple="true" style="width:115px; height:27px;"/>
						</li>
						<li class="e-search-td">
						<label class="mr8 lab" style="width:105px;">是否查询子公司:
						<input id="hasChlid" class="main-inp" type="checkbox" name="hasChlid"/>
						</label>
						</li>
						<li class="e-search-td"><label class="mr8 lab" style="width:30px;">设备</label><input id="iccid" class="main-inp" type="text" style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:60px;">开始时间</label><input id="datepickerStart" class="easyui-datetimebox" data-options="validator:setMaxDate,maxDate:'datepickerEnd'" type="text"style="width:155px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:60px;">结束时间</label><input id="datepickerEnd" class="easyui-datetimebox" data-options="validator:setMinDate,minDate:'datepickerStart'" type="text"style="width:155px; height:25px;"/></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="submit" >搜索</button>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="button" onclick="exportExcel()">导出</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
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
			url:"${cxt}/visitor/visitorRegisit/ajax/list.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:"id",
			scrollbarSize:0,
			height:550,
			queryParams:queryParams,
			columns:[[
						{field:"ck",checkbox:true},
						{title:"记录id",field:"id",width:150},
						{title:"身份证名称",field:"userName",width:120},
						{title:"性别",field:"sex",width:80,dictionary:'gender'},
						{title:"民族",field:"nation",width:80},
						{title:"出生日期",field:"birth",width:100},
						{title:"地址",field:"address",width:180},
						{title:"身份证号",field:"idcard",width:150},
						{title:"设备号",field:"iccid",width:150},
						{title:"设备公司",field:"agenciesName",width:150},
						{title:"公司地址",field:"agenciesAddress",width:150},
						{title:"比对分值",field:"score",width:80},
						{title:"名单",field:"type",dictionary:'PeopListType',width:80},
						{title:"登记时间",field:"createTime",width:120},
						{title:"操作",field:"id_1",width:80,selected:true,formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editVisitorRegisit('"+rec.id+"');\">详细</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	}); 
	//查询方法名称
	function queryParams(){
	debugger;
		try{
			var hasChlid=<%=YesorNo.NO%>;
			if($("#hasChlid").is(":checked")) {
				var hasChlid=<%=YesorNo.YES%>;
			}
			return $.param({
			"userName":$("#userName").val(),
			"agenciesId":$("#agenciesId").combogrid("getValues"),
			"iccid":$("#iccid").val(),
			"datepickerStart":$("#datepickerStart").datetimebox("getValue"),
			"datepickerEnd":$("#datepickerEnd").datetimebox("getValue"),
			"hasChlid":hasChlid
			},true);
		}catch (e) {}
		return {};
	};
	
	function addVisitorRegisit(){
		$("#win").window({
			title:"新建",
			width:800,
			height:600,
			iframe:"${cxt}/visitor/visitorRegisit/preAdd.action",
			modal:true
		});
	}
	
	// 编辑
	function editVisitorRegisit(id){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:600,
			iframe:"${cxt}/visitor/visitorRegisit/preUpdate.action?id="+id,
			modal:true
		});
	}
	
	//删除全部
	function deleteVisitorRegisits(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/visitor/visitorRegisit/ajax/delete.action",param, function call(data){
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
	function deleteVisitorRegisit(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...").show();
			jQuery.post("${cxt}/visitor/visitorRegisit/ajax/delete.action",{"ids":id}, function call(data){
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
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid("reload");
		return false;
	}
    
	//导出excel文档
	function exportExcel(){
		var datepickerStart=$("#datepickerStart").datetimebox("getValue");
		var datepickerEnd=$("#datepickerEnd").datetimebox("getValue");
		try{
			WaitingBar.getWaitingbar("export","导出，请等待...").show();
			jQuery.post("${cxt}/visitor/visitorRegisit/ajax/findValidationParm.action",{"datepickerStart":datepickerStart,"datepickerEnd":datepickerEnd}, function call(data){
				if(data.status){
					var hasChlid=<%=YesorNo.NO%>;
					if($("#hasChlid").is(":checked")) {
						var hasChlid=<%=YesorNo.YES%>;
					}
					var userName=$("#userName").val();
					var agenciesId=$("#agenciesId").combogrid("getValues");
					var iccid=$("#iccid").val();
				    jQuery('<form action="${cxt}/visitor/visitorRegisit/ajax/findPersonalStatisticsToExl.action" method="post">' +
		                '<input type="text" name="userName" value="'+userName+'"/>' + // 文件路径
		                '<input type="text" name="agenciesId" value="'+agenciesId+'"/>' + // 文件名称
		                '<input type="text" name="iccid" value="'+iccid+'"/>' + // 文件名称
		                '<input type="text" name="datepickerStart" value="'+datepickerStart+'"/>' + 
		                '<input type="text" name="datepickerEnd" value="'+datepickerEnd+'"/>' +
		                '<input type="text" name="hasChlid" value="'+hasChlid+'"/>' +
		            '</form>')
				    .appendTo('body').submit().remove();
				    WaitingBar.getWaitingbar("export").hide();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","检查参数失败!");
					}
					WaitingBar.getWaitingbar("export").hide();
				}
			},"json"); 
		}catch (e) {alert(e);}
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
