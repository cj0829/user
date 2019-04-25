<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>添加功能点--${system_name }</title>
   	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
  </head> 
  <body>
    <form action="#" method="post" name="funForm" id="funForm" onsubmit="return savefunctionPoint(this);"> 
		<div class="e-tabbox main-expand-form">
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="parentId">功能点类型名称：</label>
	         <div class="formBoxItemR">
	         	<span>${empty functionName?'根节点':functionName}</span>
	         	<input type="hidden" id="parentId"  name="parentId" value="${functionId}"/> 
	         </div>
	      </div> 
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="name"><span>*</span>功能点名称：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="inptext" id="name" name="name" onblur="checkHasFunName();"
		        check="empty" min="1" max="32" maxlength="32"  msg="功能点名称" msgId="functionNameMsg"/> 
		        <span class="advert" id="functionNameMsg"></span>
	        </div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="code"><span>*</span>功能点编码：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="inptext" id="code" name="code" onblur="checkHasFunCode();" check="empty" 
		        min="1" max="16" maxlength="16" msg="功能点编码" msgId="functionCodeMsg"/>
		        <span class="advert" id="functionCodeMsg"></span>
	        </div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="code"><span>*</span>功能点图标：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="easyui-textbox" id="icon" name="icon" maxlength="16" />
	        </div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="code"><span>*</span>功能点图标：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="easyui-textbox" id="dufIcon" name="dufIcon" maxlength="16" />
	        </div>
	      </div>
	      
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="forwardUrl"><span>*</span>功能点url：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="inptext" id="forwardUrl" name="forwardUrl" check="empty" min="1" max="256" maxlength="256" 
		        	msg="功能点url" msgId="functionUrlMsg"/>
		        <span class="advert" id="functionUrlMsg"></span>
	        </div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="urlRule"><span>*</span>功能点规则url：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="inptext" id="urlRule" name="urlRule"  check="empty" min="1" max="256" maxlength="256" 
		        	msg="功能点url" msgId="functionUrlMsg"/>
		        <span class="advert" id="functionUrlMsg"></span>
	        </div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="isAnonymous"><span>*</span>是否匿名访问：</label>
	        <div class="formBoxItemR">
	        	<core:radio id="isAnonymous" name="isAnonymous" dictType="yesOrNo" className="radio"/>
	        </div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL"><span>*</span>功能点类型：</label>
	        <div class="formBoxItemR">
		        <span>功能点</span>
		        <input type="hidden" class="inptext" name="functionPointType" value="${typeId}"/>
		        <span class="advert" id="functionCodeMsg"></span>
	        </div>
	      </div>
	      <div class="formBoxItem">
	       	<label class="formBoxItemL" for="operationLogLevel"><span>*</span>是否记日志：</label>
	       	<div class="formBoxItemR">
	       		<core:radio id="operationLogLevel" name="operationLogLevel" dictType="operationLogLevel" className="radio"/>
	     	</div>
	      </div>
	      <div class="formBoxItem">
	       	<label class="formBoxItemL" for="authenticationMode"><span>*</span>授权方式：</label>
	       	<div class="formBoxItemR">
	       		<core:radio id="authenticationMode" name="authenticationMode" dictType="authenticationMode" className="radio"/>
	      	</div>
	      </div>
	      <div class="formBoxItem">
	        <label class="formBoxItemL" for="remark"><span>*</span>描述：</label>
	        <div class="formBoxItemR">
		        <input type="text" class="inptext" id="remark"  check="empty" min="1" max="512" maxlength="512"  msg="描述" msgId="remarkMsg" name="remark" />
		        <span class="advert" id="remarkMsg"></span>
	        </div>
	      </div>
	  </div>
      <div class="e-tab-btnbox">
        <input type="submit" value="确定" class="btn mr10" /> 
        <input type="button" value="取消" class="btn cancel" onclick="cancelWindow()" />
      </div>
    </form>
  </body>
  
  <script  type="text/javascript"> 
  window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
	};

   jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-100);
		});
	});
	    //功能点名是否存在，0为不存在，1为存在
		var hasFunName = "";
		//验证功能点名是否已经存在的方法
		function checkHasFunName(){
			//获得功能点名称
			var name = jQuery("#name").val();
			var parentId = jQuery("#parentId").val();
			jQuery("#functionNameMsg").html("");
			//检查是否存在
			if(name!=""){
				jQuery.post("${cxt}/functionPoint/ajax/findHasAddName.action", "name="+name+"&parentId="+parentId, function call(result){
					hasFunName = result.message;
					if(!result.status){
						jQuery("#functionNameMsg").html(result.message); 
						jQuery("#name").focus();
					}		
				},'json');
			} 
		}
		//功能点编码是否存在，0为不存在，1为存在
		var hasFunCode = "";
		//验证功能点编码是否已经存在的方法
		function checkHasFunCode(){
			//获得功能点名称
			var name = jQuery("#code").val();
			jQuery("#functionCodeMsg").html("");
			//检查是否存在
			if(name!=""){
				jQuery.post("${cxt}/functionPoint/ajax/findHasCode.action", "code="+name, function call(result){
					hasFunCode = result.message;
					if(!result.status){
						jQuery("#functionCodeMsg").html(result.message); 
						jQuery("#code").focus();
					}
				},'json');
			} 
		}
		//提交表单的方法
		function savefunctionPoint(form1){
			try{
				//检查功能点名是否存在
				if(hasFunName!=""){
					alert("功能点名已经存在，请换一个功能点名。");
					return false;
				}
				//检查功能点名是否存在
				if(hasFunCode!=""){
					alert("功能点编码已经存在，请换一个功能点编码。");
					return false;
				}
				//提交表单
				if($("#funForm").form("validate")){
					var params=jQuery('#funForm').serialize();
					jQuery.post("${cxt}/functionPoint/ajax/add.action",params,function call(result){
						if(result.status){
							parent.callbackAdd(result.data.fun,result.data.parentId);
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
			}catch(e){console.log(e);}
			return false;
		}
		
		function cancelWindow(){
			top.jQuery("#win").window("close");
		}
</script>
</html>
