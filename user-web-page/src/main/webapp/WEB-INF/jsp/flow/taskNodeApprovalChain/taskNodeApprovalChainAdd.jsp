<%@page import="org.csr.common.flow.constant.FormApproverType"%>
<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords"/>
	<%@ include file="../common/common.jsp"%>
 	<title>签到系统--${system_name}</title>  
</head>
  
  <body>
  	<form action="" name="taskNodeForm" id="taskNodeForm" onsubmit="return saveTaskNodeApprovalChain();">
	<div class="e-tabbox">
		<div class="form-tab-wrap mt10">
			<!-- start one -->
			<div class="e-form-mod">
				<%-- <table class="e-form-tab" width=100%>
					
					<tr>
						<th class="e-form-th" style="width:180px;" ><label>是否能够编辑</label></th>
						<td class="e-form-td">
							 <label><input class="vera mr8" type="checkbox" id="isEdit" name="isEdit" value="<%=YesorNo.YES%>" />是</label>
						</td>
					</tr>
				</table> --%>
				<input class="vera mr8" type="hidden" id="isEdit"  name="isEdit" value="<%=YesorNo.NO%>" />
			</div>
			<!-- end one -->
			
			<!--start 表格操作btn -->
			<div class="e-tabbtn-box mb2" id="e-tabbtn-box">
				<span class="fl title">用户关系列表</span>
				<button class="tabbtn fr" type="button" onclick="addShow()">添加用户关系</button>
			</div>
			<!--end 表格操作btn -->
			<table id="datagridList" > </table>
		</div>	
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="submit">保存</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
	</div>
	</form>
	<script  type="text/javascript">
	
	var parentDiv;
	/**
	*
	*/
	function iniWindowParam(parent){
		this.parentDiv=parent;
	}
	
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	};

	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
		
		<%--
		$("#taskNodeForm").form("load",{
			isEdit:"${taskNode.isEdit}"
		});
		isClose:"${taskNode.isClose}",
		isOrderApproval:"${taskNode.isOrderApproval}",
		isAllApproval:"${taskNode.isAllApproval}" 
		--%>
	});
	<%--选择审批节点管理人--%>
	function addShow(id){
		var divAddMange=top.document.createElement("DIV");
		top.document.body.appendChild(divAddMange);
		top.$(divAddMange).window({ 
			title:"选择审批节点管理人",
		 	onClose:function(){
		    	top.jQuery(divAddMange).window("destroy");
		    },
		    width:700,
		    height:568,
		    onIfarmeLoad:function(t){
				var iframe=top.jQuery(divAddMange).window("getIframe");
				if(iframe){
					iframe.contentWindow.iniWindowParam(divAddMange,parentDiv);
				}
			},
		    iframe:"${cxt}/taskNodeApprovalChain/winReleation.action?taskNodeId=${taskNodeId}",
		    modal:true
		});
	}
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			url:"${cxt}/taskNodeApprovalChain/ajax/listByTaskNode.action?taskNodeId=${taskNodeId}",
			collapsible:true,
			showfolder:true,
			border:false,
			idField:'id',
			scrollbarSize:0,
			fitColumns:true,
			emptyMessage:true,
			onLoadSuccess:function(){
				$(this).datagrid("resize");
			},
			columns:[[
				{title:'流程名称',field:'taskNodeName',width:120},
				{title:'类型',field:'type',dictionary:"examApproverType",width:120},
				{title:'是否为新增',field:'type',dictionary:"yesorNo",width:100},
				{title:'批准人',field:'approver',width:150,
					formatter:function(value,rec){
						if(rec.type==<%=FormApproverType.People%>){
							return rec.approver.loginName;
						}else if(rec.type==<%=FormApproverType.Releation%>){
							return dictionaryJson["approvalChainType"][rec.approverReleation];
						}else{
							return "";
						}
				}},
				{title:'操作',field:'id_1',width:80,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a id='menu_"+rec.id,"' ");
						buttonHtml.push("href=\"javascript:deleteNode('"+rec.id+"');\"");
						buttonHtml.push("class=\"easyui-butcombo-menu\">删除</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
			]]
		});
	});
	<%--本地调用，添加用户-需要把对象转化为对象--%>
	function addPeople(userList){
		var userRows=$('#datagridList').datagrid("getRows");
		for(var i=0;i<userList.length;i++){
			var isAdd=true;
			for(var n=0;userRows && n<userRows.length;n++){
				if(userRows[n].type==<%=FormApproverType.People%>){
					if(userRows[n].approver.userId==userList[i].id){
						isAdd=false;
						break;
					}
				}
			}
			if(isAdd){
				$('#datagridList').datagrid("appendRow",{
					id:userList[i].uuid,
					taskNodeId:"${taskNode.id}",
					taskNodeName:"${taskNode.name}",
					approver:userList[i],
					type:<%=FormApproverType.People%>,
					isNew:<%=YesorNo.YES%>
				});
			}
		}
		$("#datagridList").datagrid("resize");
	}
	<%--本地调用，添加用户-需要把对象转化为对象--%>
	function addReleation(approverReleation){
		var userRows=$('#datagridList').datagrid("getRows");
		for(var i=0;i<approverReleation.length;i++){
			var isAdd=true;
			for(var n=0;userRows && n<userRows.length;n++){
				if(userRows[n].type==<%=FormApproverType.Releation%>){
					if(userRows[n].approverReleation==Number(approverReleation[i].approverReleation)){
						isAdd=false;
						break;
					}
				}
			}
			if(isAdd){
				$('#datagridList').datagrid("appendRow",{
					id:approverReleation[i].uuid,
					taskNodeId:"${taskNode.id}",
					taskNodeName:"${taskNode.name}",
					approverReleation:Number(approverReleation[i].approverReleation),
					type:<%=FormApproverType.Releation%>,
					isNew:<%=YesorNo.YES%>
				});
			}
		}
		$("#datagridList").datagrid("resize");
	}
	<%--判断是否，如果是本地数据，需要直接删除，如果是远程数据，需要调用删除--%>
	function deleteNode(id){
		var selectRow = $('#datagridList').datagrid("getRowIndex",id);
		if(selectRow!=-1){
			$('#datagridList').datagrid("deleteRow",selectRow);
		}
	}
	
	<%--保存数据--%>
	function saveTaskNodeApprovalChain(){
		var userRows=$('#datagridList').datagrid("getRows");
		if(!userRows || userRows.length<=0){
			showMsg("info","请您选择用户");
			return false;
		}
		var chains=[];
		for(var i=0;i<userRows.length;i++){
			var user=userRows[i];
			var taskNodeApprovalChain=[];
			taskNodeApprovalChain.push(user.type,"_");
			taskNodeApprovalChain.push("${taskNode.id}_");
			if(user.type==<%=FormApproverType.People%>){
				taskNodeApprovalChain.push(user.approver.id);
			}else if(user.type==<%=FormApproverType.Releation%>){
				taskNodeApprovalChain.push(user.approverReleation);
			}
			chains.push(taskNodeApprovalChain.join(""));
		}
		
		var param={
			id:"${taskNode.id}",
			isEdit:$("#isEdit:checked").val()?$("#isEdit:checked").val():"<%=YesorNo.NO%>",
			taskNodeApprovalChain:chains
		};
		<%-- 	
			isClose:$("#isClose:checked").val()?$("#isClose:checked").val():"<%=YesorNo.NO%>",
			isOrderApproval:$("#isOrderApproval:checked").val(),
			isAllApproval:$("#isAllApproval").numberspinner("getValue"),--%>
		jQuery.post("${cxt}/taskNodeApprovalChain/ajax/saveTaskNodeApprovalChain.action",jQuery.param(param,true),function call(data){
			if(data.status){
				showMsg("error","添加用户处理关系成功");
				top.reloadData();
				var iframe=top.jQuery(parentDiv).window("getIframe");
				iframe.contentWindow.jQuery("#datagridList").datagrid("reload");
				cancelWindow();
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","添加用户处理关系失败!");
				}
			}
		},"json");
		return false;
	}
	
	function cancelWindow(){
		top.jQuery(parentDiv).window("close");
	};//
	</script>
  </body>
</html>
