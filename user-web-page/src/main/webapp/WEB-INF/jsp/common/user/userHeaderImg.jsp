<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="../common/common.jsp"%>
	<script type="text/javascript" src="${jsPath}/js/jqueryWaitingBar.js"></script>
	<%@ include file="/include/webuploader.jsp"%>
	<script type="text/javascript" src="${jsPath}/js/jcrop/jquery.Jcrop.js"></script>
  	<script type="text/javascript" src="${jsPath}/js/Map.js"></script>
  	<link rel="stylesheet" type="text/css" href="${jsPath}/js/jcrop/jquery.Jcrop.css"/>

	<title>个人空间--${system_name}</title> 
	<style>
		img {
			width: auto\9;
			height: auto;
			max-width: 100%;
			vertical-align: middle;
			border: 0;
			-ms-interpolation-mode: bicubic;
		}
		.crop_preview{width:120px; height:120px; overflow:hidden;}
		.crop_preview1{ width:70px; height:70px; overflow:hidden;}
		.crop_preview2{ width:40px; height:40px; overflow:hidden;}
	</style>
</head>
<body> 
<div class="e-tabbar">
	<a class="t-btn" href="${cxt}/user/preUpdate.action?id=${userId}" ><span class="btntxt">主要</span></a>
	<a class="t-btn" href="${cxt}/user/preUpdateResetPassword.action?userId=${userId}" ><span class="btntxt">密码</span></a>
	<a class="t-btn curr" href="javascript:;" >用户头像</a>
	<a class="t-btn" href="${cxt}/user/preUserPreferences.action?userId=${userId}" ><span class="btntxt">首选项</span></a>
	<a class="t-btn" href="${cxt}/userMore/preAdd.action?userId=${userId}" >用户详细信息</a>
</div>
	<div class="middle_box">
	  	<div class="personal_box">
   			<!--教学应用开始-->
            <div class="">
            	<div class="box_bjzl bc_1">
		       		 <div class="headerimg-portrait-content">
			                <div class="headerimg-portrait-left">
			                <div id="uploadBut">
				                <div class="portrait-local">
				                	<div class="swfupload-controlbox">
		                    		<a id="swfupload-control" href="javascript:;"></a>
		                    		<span class="shuom">从本地照片，上传编辑自己的头像  支持上传4M内的JPG、PNG文件</span>
		                    		</div>
			                    </div>
		                    </div>
			                <div class="w_768 mauto" id="UpdateInitDIV" style="display: none">
			                    <ul class="w_587 fl myimgbox" style="position:relative;">
				                      <li>
				                        <div class="fl mt10 headerimg-portrait-save">
				                            <form id="streamForm" action="#" onsubmit="return savePhoto(this);">
											    <input type="hidden" id="streamId" name="streamId"/>
											    <input type="hidden" id="photo" name="photo" value="0"/>
											    <input type="hidden" id="x1" name="left" />
											    <input type="hidden" id="y1" name="top" />
											    <input type="hidden" id="w" name="width" />
											    <input type="hidden" id="h" name="height" />
											    <input type="hidden" name="userId" value="${userId}"/>
				                            	<input type="submit" value='保存头像' class="com_btn" />
				                            	<a id="filePickerBlock" href="javascript:;">重新选择</a>
			                            	</form>
			                            </div>
			                        </li>
			                        <li class="myimg">
			                        	<div id="uploadImg"></div>
			                        </li>
			                        <li class="w_120 fl">
			                           <div id="no_width"></div>
			                        </li>
			                    </ul>
			                </div>
			                </div>
			                <div class="headerimg-portrait-right">
			                	<p class="headerimg-portrain-commonp">头像预览</p>
			                	<ul class="headerimg-portrait-ulbox" style="position:relative;">
			                	<li class="w_120 fl">
	                           		<div id="no_width" class="previewbig">
		                            	<p id="preview_box0" class="crop_preview">
			                                <c:choose>
				                            	<c:when test="${not empty head}">
						                     		<img id="crop_preview0" src="${head}" width="120px" height="120px"/>
							                  	</c:when>
							                  	<c:otherwise>
					                      			<img src="${cxt}/css/img/user_img_120.jpg" id="crop_preview0" width="120px" height="120px"/>
							                   	</c:otherwise>
				                            </c:choose>
			                            </p>
			                            <em>大头像120*120</em>
		                            </div>
		                        </li>
		                        <li class="w_120 fl">
	                           		<div id="no_width" class="previewsmiddle">
			                            <p id="preview_box1" class="crop_preview1">
			                                <c:choose>
				                            	<c:when test="${not empty middleHead}">
							                     	<img src="${middleHead}" id="crop_preview1" width="80px" height="80px"/>
							                  	</c:when>
							                  	<c:otherwise>
							                      	<img src="${cxt}/css/img/user_img_80.jpg" id="crop_preview1" width="80px" height="80px"/>
							                   	</c:otherwise>
				                            </c:choose>
			                            </p>
			                            <em>中头像80*80</em>
		                            </div>
		                        </li>
		                        <li class="w_120 fl">
	                           		<div id="no_width" class="previewsmall">
			                            <p id="preview_box2" class="crop_preview2">
			                                <c:choose>
				                            	<c:when test="${not empty avatar}">
							                     	<img src="${avatar}" id="crop_preview2" width="50px" height="50px"/>
							                  	</c:when>
							                  	<c:otherwise>
							                      	<img src="${cxt}/css/img/user_img_50.jpg" id="crop_preview2" width="50px" height="50px"/>
							                   	</c:otherwise>
				                            </c:choose>
			                            </p>
			                            <em>小头像50*50</em>
		                            </div>
		                        </li>
		                        </ul>
			                </div>
	                	</div>
			</div>
        </div>
   </div>
</div>
<div id="backBox" style="display: none;">
	<img id="backBox_x"/>
</div>
<div id="showPhotoWin"></div>
<script type="text/javascript">
var WebUploaderError=new Map();
WebUploaderError.put("Q_TYPE_DENIED","请选择正确格式的图片！");
WebUploaderError.put("F_EXCEED_SIZE","请上传小于10M的图片！");
WebUploaderError.put("Q_EXCEED_SIZE_LIMIT","请上传小于10M的图片！");
WebUploaderError.put("Q_EXCEED_NUM_LIMIT","只会有一张图片生效！");
var waitingBar=WaitingBar.getWaitingbar("login","正在上传头像，请等待...","${jsPath}");

//-->上传
var uploader = WebUploader.create({
	auto : true,
	pick : {
		id : '#swfupload-control',
		label : "选择图片"
	},
	accept : {
		title: 'Images',
       	extensions: 'gif,jpg,jpeg,png,bmp',
       	mimeTypes: 'image/.gif,image/.jpg,image/.jpeg,image/.png,image/.bmp'
	},
	swf : "${cxt}/js/webuploader/Uploader.swf",
	server : "${cxt}/file/ajax/upload.action",
	fileVal : "image",
	duplicate : true,
	fileNumLimit : 1,
	fileSingleSizeLimit : 10*1024*1024, 
	formData:{}
});
uploader.on('uploadStart', function(type){
	showUpdate();
	waitingBar.show();
});
uploader.addButton({
    id: '#filePickerBlock'
});
uploader.on('error', function(type){
	showMsg("error",WebUploaderError.get(type));
	waitingBar.hide();
});
uploader.on( 'uploadSuccess', function( file,response ) {
	if(response.status==1){
		reloadImg();
		singleUpload(response.data,0);
		initJcrop();
	}else{
		if(response.message){
			showMsg("error",response.message);
		}else{
			showMsg("error","文件格式不正确或文件已损坏");
		}
	}
	uploader.removeFile( file, true );
	waitingBar.hide();
});
uploader.on( 'uploadError', function( file,reason ) {
	uploader.removeFile( file, true );
	showMsg("error","文件格式不正确或文件已损坏");
	waitingBar.hide();
});
uploader.on( 'uploadComplete', function( file,reason ) {
	waitingBar.hide();
});
//<--
//显示上传头像部分
function showUpdate(){
	$("#streamId").val("");
 	$("#UpdateInitDIV").show();
 	$("#uploadBut").hide();
}
//头像预览
function singleUpload(data,photoType) {
	var url=data.url;
	$("#backBox_x").attr("src",url);
	$("#target").attr("src",url);
	$("#crop_preview0").attr("src",url);
	$("#crop_preview1").attr("src",url);
	$("#crop_preview2").attr("src",url);
	$("#streamId").val(data.id);
	$("#photo").val(photoType);
}
//显示坐标
function showCoords(c){
	var rx = $("#backBox").width() / $("#target").width(); 
    var ry = $("#backBox").height() / $("#target").height(); 

	jQuery("#x1").val(Math.round(rx*c.x));
	jQuery("#y1").val(Math.round(ry*c.y));
	jQuery("#w").val(Math.round(rx*c.w));
	jQuery("#h").val(Math.round(ry*c.h));
    shrikImage(c,0);
	shrikImage(c,1);
	shrikImage(c,2);
}
//缩略图
function shrikImage(c,boxId){
	if(parseInt(c.w) > 0){ //计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到 
  		var rx = jQuery("#preview_box"+boxId).width() / c.w; 
   		var ry = jQuery("#preview_box"+boxId).height() / c.h; //通过比例值控制图片的样式与显示 
   		var imgSrc =  jQuery("#target").attr('src');
   		jQuery("#crop_preview"+boxId).attr('src',imgSrc);
   		jQuery("#crop_preview"+boxId).css({ 
	   		width:Math.round(rx * jQuery("#target").width()) + "px", //预览图片宽度为计算比例值与原图片宽度的乘积
	   		height:Math.round(ry * jQuery("#target").height()) + "px", //预览图片高度为计算比例值与原图片高度的乘积
			marginLeft:"-" + Math.round(rx * c.x) + "px",
			marginTop:"-" + Math.round(ry * c.y) + "px"
		}); 
	}
}
//重置截图区
function reloadImg(){
	$("#uploadImg").empty();
	$("#uploadImg").html("<img id=\"target\"/>");
}
//初始化Jcrop
function initJcrop(){
 	$('#target').Jcrop({
     	bgFade: true,
      	bgOpacity: 0.5,
      	onChange: showCoords,
      	onSelect: showCoords,
	    aspectRatio: 1,
	    setSelect: [0, 0, 160, 160]
    },function(){
   		jcrop_api = this;
   	});
}
//保存头像
function savePhoto(form1){
	//提交表单
 	if($('#streamForm').form('validate')){
 		waitingBar.show();
		var params=jQuery('#streamForm').serialize();
		jQuery.post("${cxt}/avatar/ajax/crop.action",params,function call(data){
			if(data.status){
				window.location.reload();
			}else{
				if(data.message){
					showMsg("error",data.message);
				}else{
					showMsg("error","保存头像失败!");
				}
			}
		},'json');
	}
 	waitingBar.hide();
	return false;
}


function setPhoto(data){
	showUpdate();
	reloadImg();
	singleUpload(data,1);
	initJcrop();
}

</script>
</body>
</html>