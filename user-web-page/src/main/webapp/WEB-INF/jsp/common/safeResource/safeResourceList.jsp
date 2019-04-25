<%@page import="org.csr.common.user.constant.SafeResourceType"%>
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
<form action="#" id="categoryItemStartegyForm" onsubmit="return saveSafeResource();">
<div class="e-tabcover">
<div class="e-search-wrap main-expand-form">
<ul style="display: block;">
	<li class="e-search-td">
		<label class="mr8 lab" style="width:80px;">资源类型:</label>
		<div class="expand-dropdown-select" style="display:inline;">
		<input class="easyui-combobox" id="type" name="type" style="width:200px;height:25px;" type="text"/>
		<input id="id" name="id" type="hidden"/>
		<button class="btn mr25" type="submit" >保存</button>
		</div>
	</li>
</ul>
</div>
<table>
	<tbody>
		<tr>
			<td><table id="datagridList" width="100%" border="0" cellpadding="0" cellspacing="0"> </table></td>
			<td width="50%" valign="top"><ul id="orgTree" style="width: 100%" class="m-expand-tree"></ul>  </td>
		</tr>
	</tbody>
</table>

</div>
</form>
<script type="text/javascript">
	
	$(function(){
		
		$('#datagridList').datagrid({
			nowrap: true,
			collapsible:true,
			url:"${cxt}/organization/ajax/resourceList.action?collectionId=${safeResourceCollectionId}&safeResourceType=<%=SafeResourceType.AGENCIES%>",
			showfolder:true,
			border:false,
			emptyMessage:true,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			onLoadSuccess:function(data){
		    	if(data.length>0){
		    		
		    	}
		    },
			columns:[[
				{field:'ck',checkbox:true},
				{title:'姓名',field:'name',width:150},
			]],
			pagination:true
		});
		
		$('#orgTree').tree({
			url:"${cxt}/agencies/ajax/resourceList.action?collectionId=${safeResourceCollectionId}&safeResourceType=<%=SafeResourceType.AGENCIES%>",
			checkbox:true,
			cascadeCheck:false,
		    onContextMenu: function(e,node){
				e.preventDefault();
				$(this).tree('select',node.target);
		    }
		});
		
		//资源类型。
		$("#type").combobox({
			panelHeight:55,
			required:true,
			editable:false,
			valueField:'value',
			textField:'label',
			onChange:function(newValue,oldValue){
				//getFull($("#orgId").combogrid('getValue'),$("#type").combobox('getValue'));
				//categoryItem();
			},
			data:[
			      {'label':'用户','value':1,'selected':true},
			     ]
		});
	});
	//保存资源
	function saveSafeResource(){
		//域
		var orgIds=$("#datagridList").datagrid("getChecked");
		//类别
		var categorys=$("#orgTree").tree("getChecked");
	
		
		//参数
		var params={"safeResourceCollectionId":'${safeResourceCollectionId}',"orgIds":addIds(orgIds),"categoryIds":addIds(categorys)};
		WaitingBar.getWaitingbar("addSafeResource","数据添加中，请等待...","${jsPath}").show();
		$.post("${cxt}/safeResource/ajax/addSafeResource.action",params,function call(data){
			WaitingBar.getWaitingbar("addSafeResource").hide();
			if(data.status){
				showMsg("info","内部组织添加成功");				
				top.callbackPage(${parentId});
				cancelWindow();
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","内部组织添加失败！");
				}
				
			}
		},'json');
		return false;
	}
	//添加的项id
	function addIds(checks){
		var addIds=[];
		for(var i=0;i<checks.length;i++){
			addIds.push(checks[i].id);
		}
		return addIds.toString();
	}
	//关闭窗口。
	function cancelWindow(){
		top.$("#win").window('close');
	}
</script>
</body>
</html>
