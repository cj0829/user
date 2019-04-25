<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
  	<meta content="签到系统" name="keywords"/>
	<%@ include file="../common/common.jsp"%>
 	<title>用户策略设置--${system_name}</title>  
</head>
  
  <body>
	<!--start navigation-->
	<%@ include file="../common/header.jsp"%> 
	<!--end navigation-->
	<div class="content-wrap ml10">
		<!-- start adv -->
		<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}">
		</div>
		<!-- end adv -->
		<!-- start 主要内容区域 -->
		<table class="e-main-con-box" width=100% >
			<tr>
				<td class="e-main-con-left">
					<!--start left -->
					<div class="e-tabchange-wrap">
						<!--<div class="e-tabchange-bar">
							<a class="curr" href="javascript:;">试题策略</a>
						</div>-->
						<div class="e-tabchange-bd">
							<!-- start one -->
							<form action="#" id="categoryItemStartegyForm" onsubmit="return saveTaskNodeApprovalChain();">
							<div class="e-illustrate mb15">说明：只有导入审批和共享审批是针对我的题库，其他项都是针对公共题库。</div>
							<div class="e-form-mod">
							</div>
							<!-- end one -->
							<div class="main-tableContainer cl-margin">
								<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
							</div>	
							</form>
						</div>
					</div>
					<!--end left -->
				</td>
			</tr>
		</table>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@ include file="/include/footer.jsp"%>
	<!-- end foot -->
<script type="text/javascript">
	$(function(){
		$('#datagridList').datagrid({
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			idField:'orgId',
			url:"${cxt}/userStrategy/ajax/findStrategy.action",    
			scrollbarSize:0,
			columns:[[
				//{field:'ck',checkbox:true},
				{field:'orgName',title:'域名',width:180},
				{field:'agenciesString',title:'包含组织机构',width:280,formatter:function(value,rec){
					return "<span title=\""+value+"\">"+value+"</span>";
					}
				},
				{title:'导入用户策略',field:'isImport',width:200,formatter:function(value,rec){
						var buttonHtml=[];
						if(value==<%=YesorNo.YES%>){
							buttonHtml.push("<a href=\"javascript:editTaskTemp('"+rec.importTaskTempId+"');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">编辑</a>","   ");
							
							buttonHtml.push("<a href=\"javascript:disableTask('"+rec.importId+"','imporUpdate');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">关闭</a>","   ");
						}else{
							buttonHtml.push("<a href=\"javascript:enableTask('"+rec.importId+"','imporUpdate');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">启用</a>","   ");
						}
						return buttonHtml.join("");
					}
				},
				{title:'注册用户策略',field:'isRegister',width:200,formatter:function(value,rec){
						var buttonHtml=[];
						if(value==<%=YesorNo.YES%>){
							buttonHtml.push("<a href=\"javascript:editTaskTemp('"+rec.registerTaskTempId+"');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">编辑</a>","   ");
							
							buttonHtml.push("<a href=\"javascript:disableTask('"+rec.registerId+"','registered');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">关闭</a>","   ");
						}else{
							buttonHtml.push("<a href=\"javascript:enableTask('"+rec.registerId+"','registered');\"");
							buttonHtml.push("class=\"easyui-butcombo-menu\">启用</a>","   ");
						}
						return buttonHtml.join("");
					}
				}
				]],
			pagination:true
		});
	});
	<%--添加审批关系人窗口--%>
	function editTaskTemp(taskTempId){
		$('#win').window({ 
			title:"编辑审批节点管理人",   
		    width:700,    
		    height:368,
		    iframe:"${cxt}/taskTemp/userStrategy/preUpdate.action?taskTempId="+taskTempId,
		    modal:true
		});
	}
	
	<%--开启审批流程--%>
	function enableTask(strategyId,type){
		showConfirm("您确认要启用审批流程?",function(){
			try{
				var param={strategyId:strategyId,enable:"<%=YesorNo.YES%>"};
				jQuery.post("${cxt}/userStrategy/ajax/"+type+"/updateStrategy.action",param,function call(data){
					if(data.status){
						editTaskTemp(data.data);
						reloadData();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","审批流程启用失败!");
						}
					}
				},"json");
			}catch(e){}
		});
	}
	
	<%--关闭审批流程--%>
	function disableTask(strategyId,type){
		showConfirm("您确认要关闭审批流程?",function(){
			try{
				var param={strategyId:strategyId,enable:"<%=YesorNo.NO%>"};
				jQuery.post("${cxt}/userStrategy/ajax/"+type+"/updateStrategy.action",param,function call(data){
					if(data.status){
						showMsg("info","审批流程关闭成功");
						reloadData();
					}else{
						if(data.message){
							showMsg("error",data.message);
						}else{
							showMsg("error","审批流程关闭失败!");
						}
					}
				},"json");
			}catch(e){}
		});
	}
	
	function reloadData(){
		$('#datagridList').datagrid("reload");
	}
	
	$(function(){dropdown("#examgl","#examgl .e-menu-navbg");});
	//tab切换
	$(function(){tabsclick("#tab .e-sm-tabchange-bar a","#tab .e-sm-tabchange-bd .e-tree-con");});
</script>
<div id="win"></div>
</body>
</html>