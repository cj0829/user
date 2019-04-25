<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>修改功能点--${system_name }</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
  </head> 
  
  <body>
  <form action="#" method="post" name="funForm" id="funForm" onsubmit="return savefunctionPoint(this);">
  <div class="e-tabbox main-expand-form">
  	<div class="e-form-mod">
  		<h2 class="e-form-mod-tit mb15">修改功能点</h2>
  		<table class="e-form-tab" width=100%>
  			<tr>
				<th class="e-form-th" style="width:106px;" ><label for="id">功能点类型名称</label></th>
				<td class="e-form-td">
					<span>${empty function.functionPoint.name?'根节点':function.functionPoint.name}</span>
					<input type="hidden" id="id"  name="id" value="${function.id}"/>
	             	<input type="hidden" id="parentId"  name="parentId" value="${function.functionPoint.id}"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点名称</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="name" name="name" data-options="required:true,validType:'unique[\'${cxt}/functionPoint/ajax/findHasUpdateName.action?id=${function.id}&parentId=${function.functionPoint.id}\',\'name\',\'功能点名称已重复\']'" msg="功能点名称" value="${function.name}"/> 
	                <span class="advert" id="functionNameMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点编码</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="code" name="code" data-options="required:true,validType:'unique[\'${cxt}/functionPoint/ajax/findHasCode.action?id=${function.id}\',\'code\',\'功能点编码已重复\']'"  value="${function.code}"/>
	                <span class="advert" id="functionCodeMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点图标</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="icon" name="icon" value="${function.icon}"/> 
	                <span class="advert" id="functionNameMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点图标</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="dufIcon" name="dufIcon" value="${function.dufIcon}"/>
	                <span class="advert" id="functionCodeMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点url</label></th>
				<td class="e-form-td">
					<input type="text" name="forwardUrl" style="width: 400px;"  class="easyui-textbox" id="forwardUrl" value="${function.forwardUrl}"/>
	                <span class="advert" id="functionUrlMsg"></span>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点规则url</label></th>
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
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>是否匿名访问</label></th>
				<td class="e-form-td">
					<core:radio id="isAnonymous" name="isAnonymous" dictType="yesOrNo" className="radio" value="${function.isAnonymous}"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>功能点类型</label></th>
				<td class="e-form-td">
					<span>功能点</span>
		            <input type="hidden" class="easyui-textbox" name="functionPointType" value="${typeId}"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>是否记日志</label></th>
				<td class="e-form-td">
					<input type="radio" name="operationLogLevel" <c:if test="${function.operationLogLevel==1}">checked="checked"</c:if>  class="radio" value="1" id="logLevel1"/><label for="logLevel1" class="txt">不记</label>
	                <input type="radio" name="operationLogLevel" <c:if test="${function.operationLogLevel==2}">checked="checked"</c:if> class="radio" value="2" id="logLevel2"/><label for="logLevel2" class="txt">只记录操作</label>
	                <c:if test="${function.functionPointType==2}">
	                <input type="radio" name="operationLogLevel" <c:if test="${function.operationLogLevel==3}">checked="checked"</c:if> class="radio" value="3" id="logLevel3"/><label for="logLevel3" class="txt">记录操作和数据</label>
	           	    </c:if>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>授权方式</label></th>
				<td class="e-form-td">
					<core:radio id="authenticationMode" name="authenticationMode" dictType="authenticationMode" className="radio" value="${function.authenticationMode}"/>
				</td>
			</tr>
			<tr>
				<th class="e-form-th" style="width:106px;" ><span>*</span><label>描述</label></th>
				<td class="e-form-td">
					<input type="text" class="easyui-textbox" id="remark" name="remark" value="${function.remark}"/>
	                <span class="advert" id="remarkMsg"></span>
				</td>
			</tr>
  		</table>
  	</div>
  </div>
  <div class="e-tab-btnbox">
	<button class="btn mr25" type="submit" >提交</button>
	<button class="btn cancel" type="button" onclick="cancelWindow()">取消</button>
  </div>
  </form>
  </body>
  
  <script  type="text/javascript"> 
  window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
	};

 jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-30);
		});
	});
	    //功能点名是否存在，0为不存在，1为存在
		var hasFunName = "";
		//功能点编码是否存在，0为不存在，1为存在
	    var hasFunCode = "";
	    
		//验证功能点名是否已经存在的方法
		function checkHasFunName(){
			//获得功能点名称
			var name = jQuery("#name").val();
			var parentId = jQuery("#parentId").val();
			jQuery("#functionNameMsg").html("");
			//检查是否存在
			if(name!=""){
				jQuery.post("${cxt}/functionPoint/ajax/findHasUpdateName.action", "id=${function.id}&name="+name+"&parentId="+parentId, function call(result){
					hasFunName = result.message;
					if(!result.status){
						jQuery("#functionNameMsg").html(result.message); 
						jQuery("#name").focus();
					}
				},'json');
			}
		}
		
	  //验证功能点编码是否已经存在的方法
		function checkHasFunCode(){
			//获得功能点名称
			var name = jQuery("#name").val();
			jQuery("#functionNameMsg").html("");
			//检查是否存在
			if(name!=""){
				jQuery.post("${cxt}/examFunctionPoint/ajax/findHasCode.action", "name="+name, function call(msg){
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
					jQuery.post("${cxt}/functionPoint/ajax/update.action",params,function call(result){
						if(result.status){
							parent.callbackUpdate(result.data);
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
			}catch(e){}
			return false;
		}
		
		function tianjia(){
			var inpWarp=$("<div class=\"advert\"></div>");
			var inp=$("<input type=\"text\" style=\"width: 400px;\"  name=\"urlRules\" class=\"easyui-textbox\"/>");
			var butDel=$("<button>删除</button>").bind("click",function(){
				$(this).parent().remove();
			});
			inpWarp.append(inp);
			inpWarp.append(butDel);
			$("#tianjia").append(inpWarp);
		}
		
		function delInp(inp){
			$(inp).parent().remove();
		}
		function cancelWindow(){
			top.jQuery("#win").window("close");
		}
</script>
</html>
