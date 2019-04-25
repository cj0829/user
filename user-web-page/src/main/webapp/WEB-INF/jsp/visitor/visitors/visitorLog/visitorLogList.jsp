<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<title>访客记录--${system_name}</title>  
  
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
											<li class="e-search-td"><label class="mr8 lab" style="width:90px;">访客身份证号:</label><input id="visitorsIdcard" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
											<li class="e-search-td"><label class="mr8 lab" style="width:90px;">访客姓名:</label><input id="visitorsName" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
											
						<li class="e-search-td">
							<label class="mr8 lab" style="width:40px;">公司</label>
							<user:agenciesgrid id="agenciesId" name="agenciesId" dataOptions="rememberAll:true"  multiple="true" style="width:115px; height:27px;"/>
						</li>
						<li class="e-search-td"><label class="mr8 lab" style="width:40px;">设备</label><input id="iccid" class="main-inp" type="text" style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:70px;">开始时间</label><input id="datepickerStart" class="easyui-datetimebox" data-options="validator:setMaxDate,maxDate:'datepickerEnd'" type="text"style="width:155px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:70px;">结束时间</label><input id="datepickerEnd" class="easyui-datetimebox" data-options="validator:setMinDate,minDate:'datepickerStart'" type="text"style="width:155px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:70px;">仅展示未离开</label><input type="checkbox" id="noLeave" name="checked" value="checked" /></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="submit" >搜索</button>
						<button class="btn" type="button" onclick="exportExcel()">导出</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- 搜索 end  -->
			<!--table表格开始-->
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
				<!--<button class="tabbtn fr" type="button" onclick="addVisitorLog();">新建</button>-->
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
			url:"${cxt}/visitorLog/ajax/list.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:"id",
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:"ck",checkbox:true},
						{title:"被访人姓名",field:"respondentsName",width:150},
						{title:"访客身份证号",field:"visitorsIdcard",width:150},
						{title:"访客姓名",field:"visitorsName",width:150},
						{title:"来访时间",field:"startTime",width:150,
						formatter:function (value, row, index) {
                                var date = new Date(value);
                                var year = date.getFullYear().toString();
                                var month = (date.getMonth() + 1);
                                var day = date.getDate().toString();
                                var hour = date.getHours().toString();
                                var minutes = date.getMinutes().toString();
                                var seconds = date.getSeconds().toString();
                                if (month < 10) {
                                    month = "0" + month;
                                }
                                if (day < 10) {
                                    day = "0" + day;
                                }
                                if (hour < 10) {
                                    hour = "0" + hour;
                                }
                                if (minutes < 10) {
                                    minutes = "0" + minutes;
                                }
                                if (seconds < 10) {
                                    seconds = "0" + seconds;
                                }
                                return year + "/" + month + "/" + day + " " + hour + ":" + minutes + ":" + seconds;
                            }
                        },
						
						{title:"来访是否打印",field:"startappStatus",width:150},
						{title:"部门",field:"agenciesId",width:150},
						{title:"离开时间",field:"endTime",width:150},
						{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:viewVisitorLog('"+rec.id+"');\">查看</a>","");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true,
		});
	}); 
	//查询方法名称
		function queryParams(){
			try{
				if($("#noLeave").get(0).checked){
					return $.param({
						"auto":true,//自动拼接
						/* "visitorsIdcard":$("#visitorsIdcard").val(),
						"visitorsName":$("#visitorsName").val(),
						"agenciesId":$("#agenciesId").combogrid("getValues"),
						"iccid":$("#iccid").val(),
						"datepickerStart":$("#datepickerStart").datetimebox("getValue"),
						"datepickerEnd":$("#datepickerEnd").datetimebox("getValue"),
						"noLeave":$("#noLeave").val(), */
					},true);
				}else {
				return $.param({
					"auto":true,//自动拼接
					"visitorsIdcard":$("#visitorsIdcard").val(),
					"visitorsName":$("#visitorsName").val(),
					"agenciesId":$("#agenciesId").combogrid("getValues"),
					"iccid":$("#iccid").val(),
					"datepickerStart":$("#datepickerStart").datetimebox("getValue"),
					"datepickerEnd":$("#datepickerEnd").datetimebox("getValue"), 
				},true);
				}
			}catch (e) {alert(e)}
			return {};
		};
	
	function addVisitorLog(){
		$("#win").window({
			title:"新建",
			width:800,
			height:600,
			iframe:"${cxt}/visitorLog/preAdd.action",
			modal:true
		});
	}
	function viewVisitorLog(id){
		$("#win").window({
			title:"查看",
			width:800,
			height:400,
			iframe:"${cxt}/visitorLog/viewDetail.action?id="+id,
			model:true
		})
	}
	// 编辑
	function editVisitorLog(id){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:400,
			iframe:"${cxt}/visitorLog/preUpdate.action?id="+id,
			modal:true
		});
	}
	
	//删除全部
	function deleteVisitorLogs(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/visitorLog/ajax/delete.action",param, function call(data){
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
	function deleteVisitorLog(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...").show();
			jQuery.post("${cxt}/visitorLog/ajax/delete.action",{"ids":id}, function call(data){
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
	
	//导出excel文档
	function exportExcel(){
	debugger;
		var datepickerStart=$("#datepickerStart").datetimebox("getValue");
		var datepickerEnd=$("#datepickerEnd").datetimebox("getValue");
		try{
			WaitingBar.getWaitingbar("export","导出，请等待...").show();
			jQuery.post("${cxt}/visitorLog/ajax/ValidationParm.action",{"datepickerStart":datepickerStart,"datepickerEnd":datepickerEnd}, function call(data){
				alert(data.status);
				if(data.status){
				debugger;
					var visitorsName=$("#visitorsName").val();
					var agenciesId=$("#agenciesId").combogrid("getValues");
					var iccid=$("#iccid").val();
				    jQuery('<form action="${cxt}/visitorLog/ajax/findPersonalStatisticsToExl.action" method="post">' +
		                '<input type="text" name="visitorsName" value="'+visitorsName+'"/>' + // 文件路径
		                '<input type="text" name="agenciesId" value="'+agenciesId+'"/>' + // 文件名称
		                '<input type="text" name="iccid" value="'+iccid+'"/>' + // 文件名称
		                '<input type="text" name="datepickerStart" value="'+datepickerStart+'"/>' + // 文件名称
		                '<input type="text" name="datepickerEnd" value="'+datepickerEnd+'"/>' + // 文件名称
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
