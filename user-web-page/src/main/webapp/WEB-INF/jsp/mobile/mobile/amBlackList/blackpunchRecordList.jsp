﻿<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%@include file="../common/common.jsp"%>
	<%@include file="/include/webuploader.jsp"%>
	<title>打卡记录管理--${system_name}</title>  
  	<style>
  	.datagrid-cell-c1-localImage{padding:15px 0px;}
  	.datagrid-cell-c1-localImage span span{position: relative; width:60px; }
  	.datagrid-cell-c1-localImage span span img{float:left;padding:0px 5px;display: block;}
  	.datagrid-cell-c1-localImage span span span{z-index: 999;position:absolute; display:block;right: 0px;bottom: 0px;text-align: center;}
  	
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
		<div class="e-tabchange-bar">
			<a href="${cxt}/mobile/amBlackList/blackpreList.action" class="curr">按条件搜索</a>
            <a href="${cxt}/mobile/amBlackList/preBlackpunchRecordImgList.action">按图搜索</a>
		</div>
	
		<div class="e-tabchange-bd">
			<!-- 搜索 start  -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">签到人姓名:</label><input id="name" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
						<li class="e-search-td"><label class="mr8 lab" style="width:90px;">签到人设备:</label><input id="iccid" class="main-inp" type="text" type="text"style="width:115px; height:25px;"/></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:74px;">
						<button class="btn" type="submit" >搜索</button>
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
			url:"${cxt}/mobile/amBlackList/ajax/blackList.action",
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true, 
			idField:"id",
			scrollbarSize:0,
			emptyMessage:true,
			queryParams:queryParams,
			columns:[[
				{field:"ck",checkbox:true},
						{title:"姓名",field:"name",width:150},
						{title:"设备",field:"iccid",width:120},
						{title:"时间",field:"createTime",width:120},
						{title:"比对照片",field:"dataUrl",width:50,formatter:function(value,rec){
							var buttonHtml=[];
							if(rec.dataUrl){
								buttonHtml.push("<img onclick=\"bigBDImg('"+rec.dataUrl+"');\" width=\"50px\" height=\"50px\" src=\""+rec.dataUrl+"\">"," ");
							}
							return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
						}},
						{title:"比分",field:"score",width:50},
						{title:"黑名单照片",field:"localImage",width:150,formatter:function(value,rec){
							var buttonHtml=[];
							var img;
							for(var i=0;rec.blacklistMatchResults && i<rec.blacklistMatchResults.length;i++){
								img=rec.blacklistMatchResults[i];
								if(img.facePath){
									var name ="";
									if(img.name){
										name=img.name;
									}
									buttonHtml.push("<span><img onclick=\"bigBDImg('"+img.facePath+"');\" width=\"50px\" height=\"50px\" src=\""+img.facePath+"\"/><span>"+img.fraction+"</span><span style=\"position: absolute;top:50px;\">"+name+"</span></span>"," ");
								}
							}
							return "<span>"+buttonHtml.join("")+"</span>";
						}},
						{title:"操作",field:"id_1",width:200,selected:true,formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:deletePunchRecord('"+rec.id+"');\">删除</a>"," ");
						return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
		//告警声音
		requestExamMessageData();
	});
	
	
	 //查询消息的个数。
    function requestExamMessageData(){
    	jQuery.post("${cxt}/mobile/amBlackList/ajax/pushMarker.action",function call(data){
   			if(data.status){
   				try{
   					$("#datagridList").datagrid("reload");
   		      		var audio = document.getElementById("bgMusic");
   		          	audio.play();
   		          	playTimes(audio, 3);
   		          	
   		      	}catch (e) {
   				}
   			}
   			if(data && data.data>0){
   				setTimeout("requestExamMessageData()",data.data);
   			}
    	},"json");
    }
	
	
	//查询方法名称
	function queryParams(){
		try{
			return {
			"auto":true,//自动拼接
			"name":"like:$"+$("#name").val(),
			"iccid":"like:$"+$("#iccid").val()
			};
		}catch (e) {}
		return {};
	};
	
	function addPunchRecord(){
		$("#win").window({
			title:"新建",
			width:800,
			height:600,
			iframe:"${cxt}/mobile/punchRecord/preAdd.action",
			modal:true
		});
	}
	
	// 编辑
	function editPunchRecord(id){
		$("#win").window({
			title:"编辑",   
			width:800,
			height:600,
			iframe:"${cxt}/mobile/punchRecord/preUpdate.action?id="+id,
			modal:true
		});
	}
	function bigBDImg(dataUrl){
		$("#win").window({
			title:"大图",   
			width:400,
			height:450,
			iframe:"${cxt}/mobile/amBlackList/preBigBD.action?url="+dataUrl,
			modal:true
		});
	}
	
	//删除全部
	function deletePunchRecords(){
		var row = $("#datagridList").datagrid("getSelections");
		if(row.length<=0){jQuery.messager.alert("至少选择一个删除项","error");return;}
		var param=arrayToNameIds(row,"ids","id");
		WaitingBar.getWaitingbar("deletes","删除数据中，请等待...","${jsPath}").show();
        if(confirm("确认是否删除?")){
			jQuery.post("${cxt}/mobile/punchRecord/ajax/delete.action",param, function call(data){
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
	function deletePunchRecord(id){
	     if(confirm("确认是否删除?")){
			WaitingBar.getWaitingbar("delete","删除数据中，请等待...","${jsPath}").show();
			jQuery.post("${cxt}/mobile/punchRecord/ajax/delete.action",{"ids":id}, function call(data){
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
    
   //回调方法
	function callbackPage(){
		reloadData();
	}
   
	function playTimes(elem, times) {
 	    var start = 0;
 	    elem.addEventListener("ended",function() {
 	        start++;
 	        if(start < times){
 	        	elem.play();
 	        	
 	        } else{
 	        	elem.pause();
 	        }  
 	      
 	    });
 	}
</script>
<audio id="bgMusic">
    <source = src="${cxt}/css/mp3/alam.mp3" type="audio/mp3">
</audio>
<!--弹出窗口开始-->
<div id="win"></div>
</body>
</html>
