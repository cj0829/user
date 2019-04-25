<%@page import="org.csr.common.flow.constant.FormApproverType"%>
<%@page import="org.csr.common.flow.domain.TaskNodeApprovalChain"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	request.setAttribute("ApprovalChain_Self", TaskNodeApprovalChain.Self);
	request.setAttribute("ApprovalChain_Manager", TaskNodeApprovalChain.Manager);
	request.setAttribute("ApprovalChain_Manager2", TaskNodeApprovalChain.Manager2);
	request.setAttribute("ApprovalChain_Manager3", TaskNodeApprovalChain.Manager3);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
  <%@ include file="../common/common.jsp"%>
  <title>签到系统--${system_name}</title>  
</head>
  <body>
  	<!-- start tab切换按钮 -->
  	<div class="e-tabbar">
  		<a href="javascript:;" class="t-btn curr">关系</a>
  		<a href="${cxt}/taskNodeApprovalChain/winPeople.action?taskNodeId=${taskNodeId}" class="t-btn">人员</a>
  	</div>
	<!-- end tab切换按钮 -->
	<!-- start 内容 -->
	<form action="" id="releationForm" onsubmit="return addReleation();">
	<div class="e-tabbox">
		<!-- start one -->
		<div class="e-form-mod">
			<h2 class="e-form-mod-tit mb15">请选择关系</h2>
			<table class="e-form-tab" width=100%>
				<tr>
					<td class="e-form-td">
					<label><core:property dictType="approvalChainType" value="${ApprovalChain_Self}"/>
					<input name="self" type="checkbox" style="width:80px;" value="${ApprovalChain_Self}"/>
					</label>
					</td>
				</tr>
				<tr>
					<td class="e-form-td">
					<label><core:property dictType="approvalChainType" value="${ApprovalChain_Manager}"/> 
					<input name="manager" type="checkbox" style="width:80px;" value="${ApprovalChain_Manager}"/>
					</label>
					</td>
				</tr>
				<tr>
					<td class="e-form-td"><label><core:property dictType="approvalChainType" value="${ApprovalChain_Manager2}"/>
					 <input name="manager2" style="width:80px;" type="checkbox" value="${ApprovalChain_Manager2}"/></label></td>
				</tr>
				<tr>
					<td class="e-form-td"><label><core:property dictType="approvalChainType" value="${ApprovalChain_Manager3}"/>
					 <input  name="manager3" style="width:80px;" type="checkbox" value="${ApprovalChain_Manager3}"/></label></td>
				</tr>
			</table>
		</div>
		<!-- end one -->
	</div>
	<!-- end 内容 -->
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="submit">保存</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
	</div>
	</form>
<script  type="text/javascript">
	var parentDiv;
	var thisDiv;
	/**
	*
	*/
	function iniWindowParam(tDiv,pWin){
		this.thisDiv=tDiv;
		this.parentDiv=pWin;
		var iframe=top.jQuery(pWin).window("getIframe");
		var rows=iframe.contentWindow.jQuery("#datagridList").datagrid("getRows");
		var releation={};
		for(var n=0;n<rows.length;n++){
			if(rows[n].type==<%=FormApproverType.Releation%>){
				if(<%=TaskNodeApprovalChain.Self%>==rows[n].approverReleation){
					releation.self=<%=TaskNodeApprovalChain.Self%>;
				}else if(<%=TaskNodeApprovalChain.Manager%>==rows[n].approverReleation){
					releation.manager=<%=TaskNodeApprovalChain.Manager%>;
				}else if(<%=TaskNodeApprovalChain.Manager2%>==rows[n].approverReleation){
					releation.manager2=<%=TaskNodeApprovalChain.Manager2%>;
				}else if(<%=TaskNodeApprovalChain.Manager3%>==rows[n].approverReleation){
					releation.manager3=<%=TaskNodeApprovalChain.Manager3%>;
				}
			}
		}
		$("#releationForm").form("load",releation);
	}
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};
	
	
	function addReleation(){
		var cheks=$("#releationForm").find("input:checked");
		if(!cheks || cheks.length<=0){
			showMsg("info","请您选择用户关系");
			return false;
		}
		var checkedsReleaction=[];
		cheks.each(function(){
			checkedsReleaction.push({approverReleation:this.value});
		});
		<%--获取，当前的用户id数据--%>
		jQuery.post("${cxt}/randomuuid/ajax/uuid.action",{number:checkedsReleaction.length},function call(data){
			if(data.status){
				var iframe=top.jQuery(parentDiv).window("getIframe");
				//try{
					for(var i=0;i<data.size;i++){
						checkedsReleaction[i].uuid=data.uuids[i];
					}
					iframe.contentWindow.addReleation(checkedsReleaction);
				//}catch (e) {alert(e);return;}
				cancelWindow();
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","添加$!{templateName}失败!");
				}
			}
		},"json");
		return false;
	}
	
	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-40);
		});
	});
	function cancelWindow(){
		top.jQuery(thisDiv).window("close");
	};
</script>
  </body>
</html>
