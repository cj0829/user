<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
   <%@ include file="/include/common.jsp"%>
   <%@ include file="/include/popAddWin.jsp"%>
   <script type="text/javascript" src="${cxt}/jwplayer/jwplayer.js"></script>
   <title>视频播放--${system_name }</title>   
</head>
<body>  

<div class="mainWindow">
    <div class="mainTitle">
      	<h3>${attachment.fileName}</h3>
    </div>
    <div class="mainContainer">
      	<div class="formBox">
            <form action="" method="post" name="newsForm" id="newsForm">  
	            <div class="formBoxButton">
	            	<div id="mediaplayer">loader video</div> 
		            
	            </div>
            </form>
        </div>
    </div>
</div> 
<script  type="text/javascript">   
jQuery(document).ready(function(){ 
	playerPlay();  
});

function playerPlay() {  
    
	thePlayer=jwplayer("mediaplayer").setup({
		flashplayer: "${cxt}/jwplayer/player.swf",
		file:"${attachment.attachmentPath}",
		streamer: "rtmp://192.168.10.19/oflaDemo",
		starttiem:"start",
		controlbar: "bottom",
		display:"hide",  
		width: 480, 
		events: { 
			
        },
        autostart:true,
        repeat:"always",
        height: 360,  
        showdigits: "total"
	});  
}
</script>
</body>
</html>