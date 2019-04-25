<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
  <%@ include file="../common/common.jsp"%>
  <title>编辑菜单管理--${system_name}</title>  
</head>
<body>
<!-- start 内容 -->
<form action="#" method="get" name="menuForm" id="menuForm" onsubmit="return updateMenu(this)">
<div class="e-tabbox">
	<!-- start one -->
	<div class="e-form-mod">
		<div class="e-form-mod-bd">
			<table class="e-form-tab" width="100%">
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>菜单名称</label></th>
					<td class="e-form-td"><input class="easyui-textbox inp" name="name" style="width:230px;" type="text"/></td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>父菜单</label></th>
					<td class="e-form-td">
						<span>${empty parentName?"根节点":parentName }</span>
						<input name="parent" style="width:230px;" type="hidden"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>不显示菜单</label></th>
					<td class="e-form-td"><input name="display" type="checkbox" value="2"/></td>
				</tr>
				<%-- <tr>
					<th class="e-form-th" style="width:136px;" ><label>入口功能点</label></th>
					<td class="e-form-td">
						<div class="main-search-right">
	                		<div class="pop-dropdownmenu-con pop-dropdownmenu-con-float" style="display:inline;">
	                		<core:combogrid toolbarId="functionPointToolbar" id="functionPointId" name="functionPoint" idField="id" panelWidth="460px" textField="name"
			                 columns="{field:'id',title:'ID',width:100},{field:'name',title:'名称',width:100}" 
			                 url="${cxt}/functionPoint/ajax/paged.action?menuId=${menu.id }" style="width:200px;height:25px;"/>
			                <div class="pop-dropdownmenu-top" id="functionPointToolbar" style="display: none;">
								<div class="pop-dropdown-search fl" style="width:228px;">
									<dl class="pop-dl">
										<dd><label class="lab" style="width:30px;">名称</label><input id="functionPoint_searchBox_name" class="easyui-searchbox" style="width:100px; height:22px;" type="text"/></dd>
									</dl>
								</div>
								<div class="btn-f fr" style="width:100px;">
									<button class="operate-btn mr5" onclick="$('#functionPointId').combogrid('grid').datagrid('load',{'auto':true,'name!s':'like:$'+$('#functionPoint_searchBox_name').val()});" type="button">查询</button>
									<button class="operate-btn" onclick="$('#functionPointId').combogrid('clear');" type="button">置空</button>
								</div>
						    </div>
						    </div>
	                	</div>
					</td>
				</tr> --%>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>菜单显示顺序</label></th>
					<td class="e-form-td"><input class="easyui-textbox inp" name="rank" style="width:230px;"/></td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>图标</label></th>
					<td class="e-form-td"><input class="easyui-textbox inp" name="icon" type="text"/></td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>默认图标</label></th>
					<td class="e-form-td"><input class="easyui-textbox inp" name="defIcon" type="text"/></td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:136px;" ><label>备注</label></th>
					<td class="e-form-td"><input class="easyui-textbox inp" name="remark" style="width:230px;" type="text"/></td>
				</tr>
			</table>
		</div>
	</div>
	<!-- end one -->
</div>
<!-- end 内容 -->
<input type="hidden" id="id" name="id" value="${menu.id}"/>
<%-- <input type="hidden" id="root" name="root" value="${menu.root}"/>
 --%><div class="e-tab-btnbox">
	<button class="btn mr25" type="submit" >提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
</div>
</form>
<script  type="text/javascript"> 
 	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		//加载数据
	 	$("#menuForm").form("load",{
	 		name:"${menu.name}",
	 		parent:"${menu.parent.id}",
	 		display:"${menu.display}",
	 		rank:"${menu.rank}",
	 		icon:"${menu.icon}",
	 		defIcon:"${menu.defIcon}",
	 		remark:"${menu.remark}",
		});
	};
 	jQuery(document).ready(function(){
		$(window).unbind("#winBody").bind("resize.winBody", function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
		
	//提交表单的方法
	function updateMenu(form1){
		//提交表单
		 if($("#menuForm").form("validate")){
			var params=jQuery("#menuForm").serialize();
			jQuery.post("${cxt}/menu/ajax/update.action",params,function call(data){
				if(data.status){
					showMsg("info","编辑菜单管理成功");
					top.callbackPage();
					cancelWindow();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","编辑菜单管理失败!");
					}
				}
			},"json");
		}	
		return false;
	}
	
	function cancelWindow(){
		top.jQuery("#win").window("close");
	};//
	
</script>
<%-- functionPoint:"${menu.functionPoint.id}", --%>
</body>
</html>
