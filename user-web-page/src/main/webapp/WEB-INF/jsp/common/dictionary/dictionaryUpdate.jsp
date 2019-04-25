<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
 <title>修改数据字典--${system_name }</title>  
</head>
<body>  

<%-- <div class="mainWindow">
      <div class="mainTitle">
      	<h3>修改数据字典</h3>
      </div>
      <div class="mainContainer">
      	<div class="formBox">
            <form action="#" method="post" name="dictionaryForm" id="dictionaryForm" onsubmit="return saveDictionary(this);"> 
	            <div class="formBoxItem">
	              <label for="parameterType">上级数据字典名称：</label>
	              <span>${empty dict.dictionary.dictType?'根节点':dict.dictionary.dictType }</span>
	              <input type="hidden" id="id"  name="id" value="${dict.id}"/> 
	              <input type="hidden" id="parentId"  name="parentId" value="${dict.dictionary.id}"/> 
	            </div> 
	            <div class="formBoxItem">
	              <label for="dictType"><span>*</span>数据字典类型：</label>
	              <input type="text" class="text" id="dictType" onblur="checkHasDictName();" check="empty" 
	              min="1" max="32" maxlength="32"  msg="数据字典名称" msgId="dictNameMsg" name="dictType" value="${dict.dictType}"/> 
	              <span class="advert" id="dictNameMsg"></span>
	            </div>
	            <div class="formBoxItem">
	              <label for="dictValue"><span>*</span>数据字典值：</label>
	              <input type="text" class="text" id="dictValue" onblur="checkHasDictValue();" 
	              check="num" min="1" max="16" maxlength="16"  msg="数据字典编码" msgId="dictCodeMsg" name="dictValue"  value="${dict.dictValue}"/>
	              <span class="advert" id="dictCodeMsg"></span>
	            </div>
	            <div class="formBoxItem">
	              <label for="remark">备注：</label>
	              <input type="text" class="text" id="remark" check="empty" max="512" maxlength="512" msg="备注" msgId="remarkMsg" name="remark" value="${dict.remark}" />
	              <span class="advert" id="remarkMsg"></span>
	            </div>
	            <div class="formBoxItem formBoxButton">
	              <input type="submit" value="提交" class="btn" /> 
	              <input type="button" value="取消" class="btn" onclick="cancelWindow()" />
	            </div>
            </form>
        </div>
      </div>
    </div>  --%>
<form action="#" method="post" name="dictionaryForm" id="dictionaryForm" onsubmit="return saveDictionary(this);">
  <div class="e-tabbox main-expand-form">
  	<div class="e-form-mod">
  		<table class="e-form-tab" width=100%>
  			<tr>
				<th class="e-form-th" style="width:106px;" ><label for="id">父数据字典名称：</label></th>
				<td class="e-form-td">
					<span>${empty dict.dictionary.dictType?'根节点':dict.dictionary.dictType }</span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>数据字典类型：</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="dictType" name="dictType" data-options="required:true,validType:'unique[\'${cxt}/dictionary/ajax/findName.action?id=${dict.id}\',\'name\',\'名称必须唯一,您输入的名称已经存在\']'" value="${dict.dictType}"/> 
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>数据字典值：</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="dictValue" name="dictValue" data-options="required:true" value="${dict.dictValue}"/>
					<span class="advert" id="dictCodeMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><label>备注：</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="remark" name="remark" value="${dict.remark}"/> 
				</td>
			</tr>
  		</table>
  	</div>
  </div>
  <input type="hidden" id="id"  name="id" value="${dict.id}"/> 
  <input type="hidden" id="parentId"  name="parentId" value="${dict.dictionary.id}"/> 
  <input type="hidden" id="hasChild"  name="hasChild" value="${dict.hasChild}"/>
  <input type="hidden" id="rank"  name="rank" value="${dict.rank}"/>
  <div class="e-tab-btnbox">
	<button class="btn mr25" type="submit" >提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
  </div>
</form>
 <script  type="text/javascript">  
window.onload=function(){
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
	
var hasValue="";
//验证字典数据名是否已经存在的方法
function checkHasDictValue(){
	//获得字典数据名称
	var dictValue = jQuery("#dictValue").val();
	var parentId = jQuery("#parentId").val();
	jQuery("#dictCodeMsg").html("");
	//检查是否存在
	 if(parentId!=""){
		jQuery.post("${cxt}/dictionary/ajax/findUpdateHasDictValue.action", {"id":"${dict.id}","dictValue":dictValue,"parentId":parentId}, function call(result){
			hasValue = result.message;
			if(!result.status){
				jQuery("#dictCodeMsg").html(result.message); 
				jQuery("#dictValue").focus();
			}		
		},'json');
	} 
}

function saveDictionary(form1){
	if($('#dictionaryForm').form('validate')){
		var params=jQuery('#dictionaryForm').serialize();
		jQuery.post("${cxt}/dictionary/ajax/update.action",params,function call(result){
			if(result.status){
				top.reloadTree();
				cancelWindow();
			}else{
				if(result.message){
					showMsg("error",result.message);
				}else{
					showMsg("error","修改字典数据失败!");
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