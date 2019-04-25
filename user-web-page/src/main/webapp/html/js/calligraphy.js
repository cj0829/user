// JavaScript Document by LiRongSheng at 2013-07-06
function adv(advBox,picLi,numLi){
	var picnum=0;
	var timer=null;
	var sum=$(picLi).length;
	function play(){
		timer=setInterval(function(){
			$(picLi).eq(picnum).fadeIn(1000).siblings().hide();
			$(numLi).eq(picnum).addClass("active").siblings().removeClass("active");
			picnum++;
			if(picnum==sum){picnum=0;}
		},3000);
	}
	$(numLi).mouseover(function(){
		var index=$(this).index();
		picnum=index;
		$(picLi).eq(picnum).fadeIn(1000).siblings().hide();
		$(numLi).eq(picnum).addClass("active").siblings().removeClass("active");
	});
	$(advBox).mouseover(function(){
		clearInterval(timer);
		play();
	});
	play();
}