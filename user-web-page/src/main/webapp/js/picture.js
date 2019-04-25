(function(){
	function Picture(){
		this.urlList=[];//图片的url
		this.imgList=[];//图片的id
		this.hiddenId=1;//设置显示隐藏用
		this.original=[];//原图数据
		this.initStyle=new Map();
		this.pic=[];
		this.defaultView=function(){
			$("#pictureUploadList"+this.hiddenId).attr("style","display:none;");
			var num=this.imgList.length;
			if(num){
				$("#pictureUploadList"+num).removeAttr("style");
				$("#wrap"+num+">:first").addClass("tpl-item-on").siblings().removeClass("tpl-item-on");
				$("#exhibition"+num).attr("class",this.initStyle.get(num));
				this.hiddenId=num;
				
				$("#showStyle").val(this.initStyle.get(num));//设置显示样式。
				var picture=this.pic[num];//获取num个图片的样板集。
				var ref=picture.get(1);//获取样板。
				
				for(var i=0;i<num;i++){
					if(ref[i].width/(this.original[i].width/this.original[i].height)>=ref[i].height){
						$("#picture"+num+i).removeAttr("height");
						$("#picture"+num+i).attr("width",ref[i].width);
					}else{
						$("#picture"+num+i).removeAttr("width");
						$("#picture"+num+i).attr("height",ref[i].height);
					}
					$("#picture"+num+i).attr("src",this.urlList[i]);
				}
			}
		};
		this.setOriginal=function(width,height){
			this.original[this.original.length]={"width":width,"height":height,"aspectRatio":width/height};//宽、高、宽高比
		};
		this.exhibition=function(model,index){
			var len=this.imgList.length;//imgList的长度即是上传图片的数量。
			if(model){
				$("#showStyle").val($(model).attr("class"));
				$(model).addClass("tpl-item-on").siblings().removeClass("tpl-item-on");//样板选中效果。
			}
			$("#exhibition"+len).attr("class",$(model).attr("class"));//预览区的基础样式。
			
			var picture=this.pic[len];//获取len个图片的样板集。
			var ref=picture.get(index);//获取样板。
			
			for(var i=0;i<len;i++){
				if(ref[i].width/this.original[i].aspectRatio>=ref[i].height){
					$("#picture"+len+i).removeAttr("height");
					$("#picture"+len+i).attr("width",ref[i].width);
				}else{
					$("#picture"+len+i).removeAttr("width");
					$("#picture"+len+i).attr("height",ref[i].height);
				}
			}
		};
		this.addData=function(data){
			this.imgList.push(data.id);
			this.urlList.push(data.url);
			this.setOriginal(data.width,data.height);
		};
		this.remove=function(i){
			this.imgList.splice(i,1);
			this.urlList.splice(i,1);
			this.original.splice(i,1);
		};
		this.resetParam=function(){
			this.urlList=[];
			this.imgList=[];
			this.hiddenId=1;
			this.original=[];
		};
		this.init=function(){
			//-->初始化样式
			this.initStyle.put(1,"tpl-item tpl-1-1");
			this.initStyle.put(2,"tpl-item tpl-2-1");
			this.initStyle.put(3,"tpl-item tpl-3-1");
			this.initStyle.put(4,"tpl-item tpl-4-1");
			this.initStyle.put(5,"tpl-item tpl-5-1");
			this.initStyle.put(6,"tpl-item tpl-6-1");
			this.initStyle.put(7,"tpl-item tpl-7-1");
			this.initStyle.put(8,"tpl-item tpl-8-1");
			this.initStyle.put(9,"tpl-item tpl-9-1");
			//<--
			//-->扩展样板
			var A=new Map();
			A.put(1,[{width:572,height:320}]);//1:[{width:282,height:200}]--1表示第1个样板。
			this.pic[1]=A;//pic[1]--1表示上传了1张图片。
			var B=new Map();
			B.put(1,[{width:282,height:200},{width:282,height:200}]);
			B.put(2,[{width:572,height:206},{width:572,height:206}]);
			B.put(3,[{width:282,height:416},{width:282,height:416}]);
			this.pic[2]=B;
			var C=new Map();
			C.put(1,[{width:187,height:130},{width:187,height:130},{width:187,height:130}]);
			C.put(2,[{width:572,height:206},{width:284,height:186},{width:284,height:186}]);
			C.put(3,[{width:318,height:416},{width:282,height:416},{width:250,height:206}]);
			this.pic[3]=C;
			//<--
		};
		//删除要上传的图片
		this.deleteImg=function(url,i){
			var _this=this;
			jQuery.post(url,{"streamId":this.imgList[i]},function call(data){
				if(data.status){
					_this.remove(i);
					_this.defaultView();
				}else{
					if(data.message){
						showMsg("error",data.message);
					}else{
						showMsg("error","文件删除失败");
					}
				}
			},"json");
		};
	}
	window.pmtPicture = {
		getPicture:new Picture()
	};
})(window);