<%@page import="org.csr.common.flow.constant.FormApproverType"%>
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
  	<!-- start tab切换按钮 -->
  	<div class="e-tabbar">
  		<a href="${cxt}/taskNodeApprovalChain/winReleation.action?taskNodeId=${taskNodeId}" class="t-btn">关系</a>
  		<a href="javascript:;" class="t-btn curr">人员</a>
  	</div>
	<!-- end tab切换按钮 -->
	<div class="e-tabbox">
		<div class="e-search-wrap">
		<form action="" onsubmit="return reload();">
		<table width="100%">
			<tr>
				<td class="e-search-con">
				<ul>
					<li class="e-search-td"><label class="mr8 lab" style="width:78px;">用户名:</label><input id="loginName" name="loginName" type="text" style="width:115px; height:25px;" class="easyui-textbox"/></li>
					<li class="e-search-td"><label class="mr8 lab" style="width:78px;">姓名:</label><input id="name" name="name" type="text" style="width:115px; height:25px;" class="easyui-textbox"/></li>
					<li class="e-search-td"><label class="mr8 lab" style="width:74px;">组织 :</label>
					<div class="m-expand-tree drop-down-tree">
					<input style="width:200px;height: 25px;" class="easyui-combotree" type="text" id="agenciesName" name="agenciesName"
					data-options="editable:true,url:'${cxt}/agencies/ajax/findDropDownTree.action?agenciesType=1',method:'post'" 
					style="width:115px; height:22px;" />
					</div>
					</li>
				</ul>
				</td>
				<td class="e-search-btn-box" style="width:94px;">
					<button class="btn" type="submit" >查询</button>
				</td>
			</tr>
		</table>
		</form>
		</div>
		<div class="form-tab-wrap mt10">
			<table id="datagridList"></table>
		</div>	
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" onclick="submitChange()">保存</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
	</div>
	<script  type="text/javascript">
	var parentDiv;
	var thisDiv;
	/**
	*
	*/
	function iniWindowParam(tDiv,pWin){
		this.thisDiv=tDiv;
		this.parentDiv=pWin;
		$('#datagridList').datagrid({
			nowrap: true,
			url:'${cxt}/taskNodeApprovalChain/ajax/findUserList.action',
			collapsible:true,
			showfolder:true,
			border:false,
			fitColumns:true,
			emptyMessage:true,
			idField:'id',
			scrollbarSize:0,
			loadFilter: function(data){
				try{
					if(data.rows && data.rows.length>0){
						//如果自己使用这个月，需要把当前的比较数据去掉
						var iframe=top.jQuery(parentDiv).window("getIframe");
						var rows=iframe.contentWindow.jQuery("#datagridList").datagrid("getRows");
						for(var i=0;i<data.rows.length;i++){
							var user=data.rows[i];
							for(var n=0;n<rows.length;n++){
								if(rows[n].type==<%=FormApproverType.People%> && rows[n].approver.userId==user.userId){
									user.checked=true;
									break;
								}
							}
						}
					}
				}catch(e){};
				return data;
			},
			queryParams:function(){
				try{
					return {
					"auto":true,//自动拼接
					"loginName!s":"like:$"+$("#loginName").val(),
					"name!s":"like:$"+$("#name").val(),
					"agencies.name!s":"like:$"+$("#agenciesName").combotree("getText")
					};
				}catch (e) {alert(e);}
				return {};
			},
			columns:[[
				{field:'ck',checkbox:true},
				{title:'用户编码',field:'id',width:120},
				{title:'用户名',field:'loginName',width:160},
				{title:'姓名',field:'name',width:160},
				{title:'组织',field:'agenciesName',width:150}
				]],
			pagination:true
		});
	}
	
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
	};

	jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-100);
		});
	});
	<%--选择表格--%>
	function submitChange(){
		var checkedsUser=$("#datagridList").datagrid("getChecked");
		if(!checkedsUser || checkedsUser.length<=0){
			showMsg("info","请您选择用户");
			return;
		}
		<%--获取，当前的用户id数据--%>
		jQuery.post("${cxt}/randomuuid/ajax/uuid.action",{number:checkedsUser.length},function call(data){
			if(data.status){
				var iframe=top.jQuery(parentDiv).window("getIframe");
				//try{
					for(var i=0;i<data.size;i++){
						checkedsUser[i].uuid=data.uuids[i];
					}
					iframe.contentWindow.addPeople(checkedsUser);
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
	}
	
	function reload(){
		$("#datagridList").datagrid("reload");
		return false;
	}
	
	function cancelWindow(){
		top.jQuery(thisDiv).window("close");
	};//
	</script>
  </body>
</html>
