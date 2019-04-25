<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>区域管理--${system_name }</title>  
</head>
<body>  
<%@ include file="../common/header.jsp"%> 
<!--end navigation-->
<div class="content-wrap ml10">
	<!-- start adv -->
	<div id="adv-box" class="${SECURITY_CONTEXT.nveStyle}"></div>
	<div class="e-tabchange-wrap">
		<div class="e-tabchange-bd">
			<!-- start 搜索 -->
			<form id="searchInfoForm" action="#" onsubmit="return reloadData();">
			<div class="e-search-wrap mb10">
			<table width="100%">
				<tr>
					<td class="e-search-con">
					<ul>
						<li class="e-search-td"><label class="mr8 lab" style="width:80px;">区域名:</label><input id="name" class="easyui-textbox" type="text" style="width:115px; height:25px;" /></li>
					</ul>
					</td>
					<td class="e-search-btn-box" style="width:94px;">
						<button class="btn" type="submit" >搜索</button>
					</td>
				</tr>
			</table>
			</div>
			</form>
			<!-- end 搜索 -->
			<div class="main-tableContainer cl-margin">
				<div class="e-tabbtn-box mb2">
					<button class="tabbtn fr" type="button" onclick="addDistrict();">添加区域</button>
					<button class="tabbtn fr mr5" type="button" onclick="deleteDistricts();">删除区域</button>
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
			url:"${cxt}/district/ajax/list.action",
			nowrap: true,
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			idField:'id',
			scrollbarSize:0,
			queryParams:queryParams,
			columns:[[
				{field:'ck',checkbox:true},
				{title:'区域名',field:'name',width:200,formatter:function(value,rec){
					var buttonHtml=[];
					buttonHtml.push("<a href=\"javascript:infoDistrict('"+rec.id+"');\">"+rec.name+"</a>"," ");
					return "<span style=\"color:red\">"+buttonHtml.join("")+"</span>";
				}},
				{title:'备注',field:'remark',width:150},
				{title:'操作',field:'id_1',width:110,selected:true,
					formatter:function(value,rec){
						var buttonHtml=[];
						buttonHtml.push("<a href=\"javascript:editDistrict('"+rec.id+"');\">编辑</a>"," ");
						buttonHtml.push("<a href=\"javascript:deleteDistrict('"+rec.id+"');\">删除</a>"," ");
						return "<span>"+buttonHtml.join("")+"</span>";
					}
				}
			]],
			/* toolbar:[{
				text:'添加新区域',
				iconCls:'icon-add',
				handler:function(){
					addDistrict();
				}
			},'-',{
				text:'删除',
				iconCls:'icon-remove',
				handler:function(){
					var row = $('#tt').datagrid('getSelected');
					if (row){
						var index = $('#tt').datagrid('getRowIndex', row);
						$('#tt').datagrid('deleteRow', index);
					}
				}
			}], */
			pagination:true
		});
	});
	
	function queryParams(){
		try{
			return {
			'auto':true,//自动拼接
			'name!s':"like:$"+$("#name").val(),
		};
		}catch (e) {}
		return {};
	};
	
	//添加区域
	function addDistrict(){
	   $("#win").window({ 
			title:"添加区域",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/district/preAdd.action",
		    modal:true
		});
	}
	// 编辑区域
	function editDistrict(id){ 
	   $("#win").window({ 
			title:"编辑区域",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/district/preUpdate.action?id="+id,
		    modal:true
		});
	}
	// 区域详细信息
	function infoDistrict(id){ 
	   $("#win").window({ 
			title:"区域详细信息",   
		    width:700,    
		    height:500,
		    iframe:"${cxt}/district/preInfo.action?id="+id,
		    modal:true
		});
	}
	//删除区域
	function deleteDistricts(){ 
	    if(confirm("确认是否删除?")){
		    var row = $("#datagridList").datagrid("getSelections");
			var params=arrayToNameIds(row,"districtIds","id");
			jQuery.post("${cxt}/district/ajax/delete.action",params, function call(result){
				if(result.status){
					showMsg("success","删除成功");
					reloadData();
				}else{
					top.showMsg("error",result.message);
				}
			},'json'); 
		}
	}
	
	//删除区域
	function deleteDistrict(id){ 
		if(confirm("确认是否删除?")){
	        jQuery.post("${cxt}/district/ajax/delete.action","districtIds="+id, function call(result){
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