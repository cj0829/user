<%@page import="org.csr.core.constant.YesorNo"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="CMS系统" name="keywords" />
  <%@include file="../common/common.jsp"%>
  <title>添加数据字典--${system_name}</title>  
</head>
<body>
<!-- start 内容 -->
<form action="#" method="post" name="funForm" id="funForm" onsubmit="return savefunctionPoint(this);">
<div class="e-tabbox">
	<!-- start one -->
	<div class="e-form-mod">
		<div class="e-form-mod-bd">
			<table class="e-form-tab" width="100%">
				<tr>
					<th class="e-form-th" style="width:106px;" >
						<label for="id">父数据字典名称:</label>
					</th>
					<td class="e-form-td">
						<span>${empty dictType?'根节点':dictType}</span>
		             	<input type="hidden" id="parentId"  name="parentId" value="${dictId}"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><label>数据字典类型:</label></th>
					<td class="e-form-td">
						<input type="text" class="easyui-textbox" id="dictValue" name="dictValue" data-options="required:true,validType:'unique[\'${cxt}/examFunctionPoint/ajax/findHasDictValue.action?parentId=${dictId}\',\'name\',\'功能点名称已重复\']'" msg="功能点名称" value="${function.name}"/> 
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>功能点编码</label></th>
					<td class="e-form-td">
						<input type="text" class="easyui-textbox" id="code" name="code" data-options="required:true,validType:'unique[\'${cxt}/examFunctionPoint/ajax/findHasCode.action?id=${function.id}\',\'code\',\'功能点编码已重复\']'"  value="${function.code}"/>
					</td>
				</tr>
				
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>功能点图标:</label></th>
					<td class="e-form-td">
						<input type="text" class="easyui-textbox" id="icon" name="icon" value="${function.icon}" data-options="required:true"/> 
					</td>
				</tr>
			
				<tr>
					<th class="e-form-th" style="width:106px;"><label>功能点图标</label></th>
					<td class="e-form-td">
						<input type="text" class="easyui-textbox" id="dufIcon" name="dufIcon" value="${function.dufIcon}" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>功能点url:</label></th>
					<td class="e-form-td">
						<input type="text" name="forwardUrl" style="width: 400px;" class="easyui-textbox" id="forwardUrl" value="${function.forwardUrl}" data-options="required:true"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>功能点规则url:</label></th>
					<td class="e-form-td" id="tianjia">
		            	<c:forEach var="urlRule" items="${urlRules}" varStatus="i">
			              <div class="advert">
			               <c:if test="${i.index==0}"><input type="text" style="width: 400px;"  name="urlRules" class="easyui-textbox" id="urlRule" value="${urlRule}"/><input class="small-btn" value="添加" type="button" onclick="tianjia();"/></c:if>
			              <c:if test="${i.index!=0}">
			              	<input type="text" name="urlRules"  id="urlRule" style="width: 400px;"  value="${urlRule}"/>
	            			<button onclick="delInp(this);">删除</button>
	            		</c:if>
			              </div>
		            	</c:forEach>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>是否匿名访问</label></th>
					<td class="e-form-td">
						<core:radio id="isAnonymous" name="isAnonymous" dictType="yesOrNo" className="radio" value="${function.isAnonymous}"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>功能点类型:</label></th>
					<td class="e-form-td">
						<span>功能点</span>
			            <input type="hidden" class="text" name="functionPointType" value="${typeId}"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><label>是否记日志:</label></th>
					<td class="e-form-td">
						<input type="radio" name="operationLogLevel" <c:if test="${function.operationLogLevel==1}">checked="checked"</c:if>  class="radio" value="1" id="logLevel1"/><label for="logLevel1" class="txt">不记</label>
		                <input type="radio" name="operationLogLevel" <c:if test="${function.operationLogLevel==2}">checked="checked"</c:if> class="radio" value="2" id="logLevel2"/><label for="logLevel2" class="txt">只记录操作</label>
		                <c:if test="${function.functionPointType==2}">
		                <input type="radio" name="operationLogLevel" <c:if test="${function.operationLogLevel==3}">checked="checked"</c:if> class="radio" value="3" id="logLevel3"/><label for="logLevel3" class="txt">记录操作和数据</label>
		           	    </c:if>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><label>授权方式:</label></th>
					<td class="e-form-td">
						<core:radio id="authenticationMode" name="authenticationMode" dictType="authenticationMode" className="radio" value="${function.authenticationMode}"/>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:106px;"><label>描述:</label></th>
					<td class="e-form-td">
						<input type="text" class="easyui-textbox" id="remark" name="remark" value="${function.remark}" data-options="required:true"/>
		                <span class="advert" id="remarkMsg"></span>
					</td>
				</tr>
		             
			</table>
		</div>
	</div>
	<!-- end one -->
</div>
<!-- end 内容 -->
<div class="e-tab-btnbox">
	<button class="btn mr25" type="submit">提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
</div>
</form>
  
<script type="text/javascript"> 
window.onload=function(){
	alert("${function}");
	
	$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
};
	jQuery(document).ready(function(){
	$(window).unbind('#winBody').bind('resize.winBody', function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	});
	$("#dictValue").textbox({  
	  	onChange: function(){ 
		  	checkHasDictValue();
	  	}
	}); 
});

var hasName = "";
//验证字典数据值是否已经存在的方法
function checkHasDictValue(){
	//获得字典数据名称
	var dictValue = jQuery("#dictValue").val();
	var parentId = jQuery("#parentId").val();
	jQuery("#dictCodeMsg").html("");
	//检查是否存在
	 if(parentId!=""){
		jQuery.post("${cxt}/dictionary/ajax/findHasDictValue.action", {"dictValue":dictValue,"parentId":parentId}, function call(result){
			hasValue = result.message;
			if(!result.status){
				jQuery("#dictCodeMsg").html(result.message); 
				jQuery("#dictValue").focus();
			}		
		},'json');
	} 
}
	
//提交表单的方法
function saveDictionary(form1){
	if($("#dictionaryForm").form("validate")){
		var params=jQuery('#dictionaryForm').serialize();
		jQuery.post("${cxt}/dictionary/ajax/add.action",params,function call(result){
			if(result.status){
				top.reloadTree();
				cancelWindow();
			}else{
				if(result.message){
					showMsg("error",result.message);
				}else{
					showMsg("error","字典数据失败!");
				}
			}	
		},'json');
	}
	return false;
}

function cancelWindow(){
	top.jQuery("#win").window("close");
}
</script>
</body>
</html>
