<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge " />
	<meta content="签到系统" name="keywords" />
	<%@ include file="../common/common.jsp"%>
  
  <title>导航设置--${system_name }</title>   
</head>
<body>  

<div class="mainWindow">
      <div class="mainTitle">
      	<h3>导航设置<span style="color:#FF2D2D;margin-left:200px;">该设置需要重新登陆系统才能生效</span></h3>
      </div>
      
      <div class="mainContainer mainContainer2"> 
            <ul> 
	            <a id="menu_left" onclick="selectStyle('left');" href="javascript:;">
	               <img src="images/left.jpg"/>
	               <div class="name">左侧显示</div> 
	            </a> 
	             <a id="menu_right" onclick="selectStyle('right');" href="javascript:;">
	               <img src="images/right.jpg"/>
	               <div class="name">右侧浮动</div> 
	            </a> 
	             <a id="menu_top" onclick="selectStyle('top');" href="javascript:;">
	               <img src="images/top.jpg"/>
	               <div class="name">顶部显示</div> 
	            </a> 
             </ul>  
      </div>
</div> 
 <script  type="text/javascript">  
        var oldStyle='${sessionScope.SECURITY_CONTEXT.menuStyle}';
	    jQuery(document).ready(function(){ 
	        jQuery("#menu_${sessionScope.SECURITY_CONTEXT.menuStyle}").addClass("on");    
	    });
		function selectStyle(styleId){ 
		    if(styleId!=oldStyle){
			    jQuery("#"+styleId).addClass("on"); 
			    jQuery("#"+oldStyle).removeClass("on");  
			    var params="user.menuStyle="+styleId;
				jQuery.post("person!updateMenuStyle.action?ajaxFlag=1",params,function call(result){
					if(result.status){
						top.showMsg("success","编辑成功");
						top.callbackPage();
                        top.$.dialog.list['batchSendMsgDialog'].close();
					}else{
						top.showMsg("error",data.message);
					}
				},'json'); 
			}
		} 
		function cancelWindow(){
		   top.$.dialog.list['batchSendMsgDialog'].close();
		}
	 
  
</script>
</body>
</html>