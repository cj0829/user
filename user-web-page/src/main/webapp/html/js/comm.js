// JavaScript Document

//点击收缩隐藏
/*function hide(hidebtn,hidecon){
	$(hidebtn).click(function(){
		$(this).addClass("hiderightbtn_hide")
		$(hidecon).css("width","0px")
	})
}*/
//tab切换
function tabs(clickbtn,control){
	$(clickbtn).click(function(){
		$(this).addClass("curr").siblings().removeClass("curr");
		$(control).children().eq($(this).index()).addClass("curr").siblings().removeClass("curr");
	});
}
function tabsone(clickbtn,control){
	$(clickbtn).change(function(){
		var index=this.selectedIndex;
		$(control).eq(index).addClass("curr").siblings().removeClass("curr");
	});
}

function Choose(tagChoose,tagcontrol){
	$(tagChoose).click(function(){
		$(tagcontrol).show();
	});
	$(tagChoose).hover(
		function(){},
		function(){$(tagcontrol).hide();
	});
}
function Chooseone(tagChoose,tagcontrol){
	$(tagChoose).hover(function(){
		$(tagcontrol).show();
	});
	$(tagChoose).hover(
		function(){},
		function(){$(tagcontrol).hide();
	});
}
function hover(taghover,tagcontrol,tagspan){
	$(taghover).hover(function(){
		$(tagcontrol).show();
		$(tagspan).addClass("hover");
	});
	$(taghover).hover(
		function(){},
		function(){$(tagcontrol).hide();
		$(tagspan).removeClass("hover");
	});
}

/*解决input中placeholder值在ie中无法支持的问题*/
$(function(){
if(!placeholderSupport()){   // 判断浏览器是否支持 placeholder
    $('[placeholder]').focus(function() {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
            input.val('');
            input.removeClass('placeholder');
        }
    }).blur(function() {
        var input = $(this);
        if (input.val() == '' || input.val() == input.attr('placeholder')) {
            input.addClass('placeholder');
            input.val(input.attr('placeholder'));
        }
    }).blur();
};
});
function placeholderSupport() {
    return 'placeholder' in document.createElement('input');
}

function clickdj(tabObj){
	$(tabObj).click(function(){
		$(this).addClass("current").siblings().removeClass("current");
	});
	
}

//表格
//$(function(){
//	var oTab=document.getElementById("table");
//	var aTr=document.getElementsByTagName("tr");
//	var classname="";
//	for(var i=0;i<oTab.tBodies[0].rows.length;i++){
//		if(i%2){
//			oTab.tBodies[0].rows[i].className="even";
//		}else{
//			oTab.tBodies[0].rows[i].className="";
//		}
//	
//	}
//})
//表格里面点击选中样式
function clicktr(Objtr){
	$(Objtr).click(function(){
		if($(this).attr("class")=="even selected" || $(this).attr("class")=="selected"){
			$(this).addClass("selected").siblings().removeClass("selected");	
			$(this).removeClass("selected");
		}else{
			$(this).addClass("selected").siblings().removeClass("selected");
		}
	});
	
}