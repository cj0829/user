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
<%@ include file="../common/header.jsp"%> 
	<!--end navigation-->
	<div class="content-wrap ml10">
		<!-- start adv -->
		<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}">
		</div>
		<div class="e-tabchange-wrap">
			<div class="e-tabchange-bd">
				<div class="main-tableContainer cl-margin">
					<div class="e-tabbtn-box mb2">
						<button class="tabbtn fr" type="button" onclick="addOrganizationParameter();">添加机构参数</button>
						<button class="tabbtn fr mr5" type="button" onclick="deleteOrganizationParameters();">删除机构参数</button>
					</div>
					<table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table>
				</div>	
				<!--start btn -->
				<div class="e-btn-box mt15">
				</div>
				<!--end btn -->
			</div>
		</div>
	</div>
	<!-- start foot -->
<%@ include file="/include/footer.jsp"%>  
 
 <script  type="text/javascript"> 

	$(function(){
		$("#datagridList").datagrid({
			url:"${cxt}/organizationParameter/ajax/list.action",
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'机构参数名',field:'name',width:200},
				{title:'机构参数值',field:'parameterValue',width:150},
				{title:'参数类型',field:'parameterType',dictionary:'parameterType',width:100},
				{title:'说明',field:'remark',width:150},
				{title:'操作',field:'id_1',width:110,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editParameter('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteOrganizationParameter('"+rec.id+"');\">删除</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			pagination:true
		});
	});
	
	//添加机构参数
	function addOrganizationParameter(){
	   $("#win").window({ 
			title:"添加机构参数",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/organizationParameter/preAdd.action",
		    modal:true
		});
	}
	
	// 编辑机构参数
	function editParameter(id){ 
	   $("#win").window({ 
			title:"编辑机构参数",
		    width:700,    
		    height:500,
		    iframe:"${cxt}/organizationParameter/preUserParameterUpdate.action?parameterId="+id,
		    modal:true
		});
	}

	//删除参数
	function deleteOrganizationParameters(){ 
	    if(confirm("确认是否删除?")){
	    	var row = $("#datagridList").datagrid("getSelections");
			var params=arrayToNameIds(row,"parameterIds","id");
			jQuery.post("${cxt}/organizationParameter/ajax/delete.action",params, function call(result){
				if(result.status){
					showMsg("success","删除成功");
				    reloadData();	
				}else{
					top.showMsg("error",result.message);
				}
			},'json'); 
		}
	}
	
	//删除参数
	function deleteOrganizationParameter(id){ 
		if(confirm("确认是否删除?")){
	        jQuery.post("${cxt}/organizationParameter/ajax/delete.action?parameterIds="+id, function call(result){
	        	if(result.status){
					showMsg("success","删除成功");
					reloadData();	
				}else{
					top.showMsg("error",result.message);
				}
			},'json'); 
		}
	}
	
	//重新刷新
	function reloadData(){
		$("#datagridList").datagrid('reload');
	}
    
</script>
<!-- 弹出窗口开始 -->
<div id="win"></div>
</body>
</html>