<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
	<%@ include file="/include/user.jsp"%>
	<%@ include file="/include/nicEdit.jsp"%>
	<%@ include file="/include/webuploader.jsp"%>
	<script type="text/javascript" src="${jsPath}/js/nicEdit/dialogs/image/uploadImage.js"></script>
	<link rel="stylesheet" type="text/css" href="${jsPath}/css/exam/${SECURITY_CONTEXT.skinName}/uploadImg.css"/>
	<script type="text/javascript" src="${cxt}/js/jqueryWaitingBar.js"></script>
 	<title>插入图片--${system_name}</title>  
</head>
  
  <body>
  	<div class="e-tabhead">
	  	<div class="e-tabhdL">
			<span class="tab curr" type="remote">插入图片</span>
			<span class="tab" type="upload">本地上传</span>
			<span class="tab" type="online">在线管理</span>
		</div>
		<div class="img-alignBar">
			<!-- 
			<label class="algnLabel">图片浮动方式：</label>
			<span id="alignIcon" class="alignIcon">
				<span class="none-align curr" value="none" title="无浮动"></span>
				<span class="left-align"  value="left" title="左浮动"></span>
				<span class="right-align"  value="right" title="右浮动"></span>
				<span class="center-align"  value="center" title="居中独占一行"></span>
			</span>
			 -->
		</div>
	</div>
	<div class="e-tabbox main-expand-form e-tabbody">
		<div class="panel curr">
			<div class="top">
				<div class="row"> 
                    <label>地 址：</label>
                    <span><input id="src_img" onkeyup="changeImage(this)" type="text" class="text" style="width:87%; height:22px;" /></span>
                </div>
			</div>
			<table>
				<tr>
					<td class="left veraT">
						<div class="row">
	                        <label>大 小：</label>
	                        <span><em class="mr3">宽度</em><input id="width_img" style="width:60px; height:24px;" type="text" class="easyui-numberspinner" value="100"/><em class="ml3">px</em> </span>
	                        <span><em class="mr3">高度</em><input id="height_img" style="width:60px; height:24px;" type="text" class="easyui-numberspinner" value="100"/><em class="ml3">px</em> </span>
	                    </div>
	                    <div class="row">
	                        <label>边 框：</label>
	                        <span><input id="border_img" style="height:24px;" type="text" class="easyui-numberspinner mr5" value="0"/><em class="ml5">px</em> </span>
	                    </div>
	                    <div class="row">
	                        <label>边 距：</label>
	                        <span><input id="vspace_img" style="height:24px;" type="text" class="easyui-numberspinner mr5" value="0"/><em class="ml5">px</em> </span>
	                    </div>
	                    <div class="row">
	                        <label>描 述：</label>
	                        <span><input id="alt_img" style="height:22px;" type="text" class="text" /></span>
	                    </div>
					</td>
					<td class="veraT">
						<div class="right" style="width:200px;">
							<div id="preview" class="preview" ></div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div class="panel">
		<div class="queueList">
				<div id="statusBar" class="statusBar">
					<div id="progress" class="progress">
                            <span class="text">0%</span>
                            <span class="percentage"></span>
                        </div>
					<div class="info" id="info"></div>
					<div class="btns">
						<div id="filePickerBtn" class="filePickerBtn"></div>
						<!-- <button id="filePickerReady" type="button" class="tabbtn other mr10">继续添加</button> -->
						<button type="button" id="uploadBtn" class="tabbtn fl ml10">开始添加</button>
					</div>
				</div>
				<ul id="filelist" class="filelist">
					<li id="filePickerBlock" class="filePickerBlock"></li>
				</ul>
			</div></div>
		<div class="panel">
			<div id="onlineImageQueueList" style="height: 218px;" class="queueList">
				<div id="onlineImageQueueMessage"></div>
				<ul id="onlineImageWrap" class="filelist online"></ul>
			</div>
			<div id="onlineImagePagination" class="e-expand-page mt10 "></div>
		</div>
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" onclick="saveImg()">保&nbsp;&nbsp;存</button>
		<button class="btn cancel" type="button" onclick="cancelWindow()">取&nbsp;&nbsp;消</button>
	</div>
	<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tabhead").height()-$(".e-tab-btnbox").height()-30);
	};
	
	var editor,parentDiv;
	var uploadImage;
	var waitingbar;
	/**
	*
	*/
	function iniWindowParam(ed,t){
		this.editor=ed;
		this.parentDiv=t;
		
		 var img = editor.selection.getRange().getClosedNode();
	        if (img) {
	            this.setImage(img);
	        }
	}
   jQuery(document).ready(function(){
		$(window).unbind('#winBody').bind('resize.winBody', function(){
			$(".e-tabbox").height($(this).height()-$(".e-tabhead").height()-$(".e-tab-btnbox").height()-30);
		});
	});
   function setImage(img){
       /* 不是正常的图片 */
       if (!img.tagName || img.tagName.toLowerCase() != 'img' && !img.getAttribute("src") || !img.src) return;

       var wordImgFlag = img.getAttribute("word_img"),
           src = wordImgFlag ? wordImgFlag.replace("&amp;", "&") : (img.getAttribute('_src') || img.getAttribute("src", 2).replace("&amp;", "&"));

       /* 防止onchange事件循环调用 */
       if (src !== $("#src_img").val()) $("#src_img").val(src);
       if(src) {
           /* 设置表单内容 */
           $("#width_img").numberspinner("setValue",img.width || '') ;
           $("#height_img").numberspinner("setValue",img.height || '');
           $("#border_img").numberspinner("setValue",img.getAttribute("border") || '0');
           $("#vspace_img").numberspinner("setValue",img.getAttribute("vspace") || '0');
           $("#alt_img").val(img.title || img.alt || '');
           changeImage($("#src_img")[0]);
       }
   }
	function saveImg(){
		var list=[];
		var type= $(".e-tabhdL .tab.curr").attr("type");
		if(type){
			switch (type) {
		       case 'remote':
		    	   list.push({
						_src:$("#src_img").val(),
						alt:$("#alt_img").val(),
						border:$("#border_img").numberspinner("getValue"),
						floatStyle:$("#_img"),
						height:$("#height_img").numberspinner("getValue"),
						src:$("#src_img").val(),
						style:"",
						vspace:$("#vspace_img").numberspinner("getValue"),
						width:$("#width_img").numberspinner("getValue"),
					});
		           break;
		       case 'upload':
		           list = uploadImage.getInsertList();
		           var count = uploadImage.getQueueCount();
		           if (count) {
		               $("#info").html('<span style="color:red;">' + '还有2个未上传文件'.replace(/[\d]/, count) + '</span>');
		               return false;
		           }
		           break;
		       case 'online':
		    	   var align = getAlign();
		    	   $("#onlineImageWrap li.selected").each(function(){
		    		   var img=$(this).find("img");
		    		   list.push({
	                       src: img.attr("src"),
	                       _src: img.attr("src"),
	                       alt: img.attr("alt"),
	                       width:300,
	                       height:300,
	                       floatStyle: align
	                   });
		    	   });
		           break;
			}
			if(list) {
				editor.execCommand('insertimage', list);
				cancelWindow();
	        }
		}
   }
   function changeImage(imgPath){
       /* 不是正常的图片 */
       var img=$("<img width='100%' src='"+imgPath.value+"'/>");
       $("#preview").empty();
       $("#preview").append(img);
   };
   //tab切换
   $(function(){
	   $(".e-tabhdL .tab").click(function(){
			var type=$(this).attr("type");
			$(this).addClass("curr").siblings().removeClass("curr");
			$(".e-tabbody").children().eq($(this).index()).addClass("curr").siblings().removeClass("curr");
			
			switch (type) {
		        case 'remote':
		            remoteImage = remoteImage || new RemoteImage();
		            break;
		        case 'upload':
		           // setAlign(editor.getOpt('imageInsertAlign'));
		            uploadImage = uploadImage || new UploadImage('queueList',"${file_server}");
		            break;
		        case 'online':
		        	loadOnlineImage(1,8);
		            break;
			}
		});
   });
   
   function loadOnlineImage(pageNumber,pageSize){
		var paramObj={page:pageNumber,rows:pageSize};
		
		$("#onlineImageWrap").empty();
		waitingbar=jQuery.getWaitingbar("数据加载中，请等待...",true,"${cxt}",true);
		jQuery.post("${file_server}/attachment/ajax/findImgList.action",paramObj,function call(data){
			$("#onlineImagePagination").pagination({
				total:data.total,
				showPageStyle:"manual",
				pageSize:data.pageSize,
				showPageList:false,
				onSelectPage:function(pageNumber, pageSize){
					$(this).pagination('loading');
					loadOnlineImage(pageNumber,pageSize);
					$(this).pagination('loaded');
				}
			});
			for(var i=0;i<data.rows.length;i++){
				var liItem=$("<li id=\""+data.rows[i].id+"\"></li>");
				liItem.append("<p class=\"title\">"+data.rows[i].fileName+"</p>");
				liItem.append("<p class=\"imgWrap\"><img width=\"100\" height=\"100\" alt=\""+data.rows[i].fileName+"\" src=\"${file_server}/attachment/download.action?uuid="+data.rows[i].id+"\"></p>");
				liItem.append("<span class=\"icon\"></span>");
				liItem.bind("click",function(){
					if($(this).hasClass("selected")){
						$(this).removeClass("selected");
					}else{
						$(this).addClass("selected");
					}
				});

				$("#onlineImageWrap").append(liItem);
			}
			setTimeout(function(){waitingbar.hide();},200);
		},"json");
   }
   function cancelWindow(){
		parentDiv.window("destroy");
	}
	</script>
  </body>
</html>
