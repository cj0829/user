/**
 * 横向柱形图
 * 
 * @author 纵横(lrenwang)
 * @email lrenwang@qq.com
 * @blog blog.lrenwang.com
 * @qq 3142442
 * @version 1.2 兼容 IE7 FireFox
 */
var SimpleBar = function(cxt,id, title, data) {
	// 展示的id
	this.id = '';
	// 标题
	this.title = '';
	// 数据
	this.data = '';
	// 宽
	this.width = 500;
	// 当前用户的以选择的项
	this.userData = [];
	// 投票已过期
	this.userIsValid = 0;
	// 发帖是单选还是多选
	this.ballotType = null;
	// 背景图片位置
	this.bgimg = cxt+'/images/bbs/plan.gif';
	// 动画速度
	this.speed = 1000;
	// 投票总数
	var num_all = 0;
	this.show = function() {
		// 添加一个table对象
		$("#" + this.id).append("<table width='"+ this.width + "' cellpadding=0 cellspacing=6 border=0 style='font-size:12px;' ></table>");
		//$("#" + this.id + " table").append("<tr><td colspan=3 align='center' ><span style='font:900 14px ;color:#444'>" + this.title + "</span></td></tr>");
		// 计算总数
		$(this.data).each(function(i, n) {
			num_all += parseInt(n[1]);
		});
		var that = this;
		// 起始
		var s_img = [ 0, -52, -104, -156, -208 ];
		// 中间点起始坐标
		var c_img = [ -312, -339, -367, -395, -420 ];
		// 结束
		var e_img = [ -26, -78, -130, -182, -234 ];
		var div;
		for (var index = 0; this.data && index < this.data.length; index++) {
			obj = this.data[index];
			var randomNum = parseInt(Math.random()*4);
			// 计算比例
			var bili = (obj[1] * 100 / num_all).toFixed(2);
			if (bili == 'NaN') {
				bili = 0;
			}
			// 计算图片长度, *0.96是为了给前后图片留空间
			var img = parseFloat(bili) * 0.96;
			if (img > 0) {
				div = "<div style='width:3px;height:16px;background:url(" + that.bgimg + ") 0px " + s_img[randomNum]
						+ "px ;float:left;'></div><div fag='" + img + "' style='width:0%;height:16px;background:url(" + that.bgimg + ") 0 "
						+ c_img[randomNum]
						+ "px repeat-x ;float:left;'></div><div style='width:3px;height:16px;background:url("
						+ that.bgimg + ") 0px " + e_img[randomNum]
						+ "px ;float:left;'></div>";
			} else {
				div = '';
			}
			var htmlStr = [];
			htmlStr.push("<tr><td width='100' align='right'>");
			// 判断用户选择过的项
			for(var userIndex=0;this.userData && userIndex<this.userData.length;userIndex++){
				if(obj[2]==this.userData[userIndex].ballotBean.id){
					htmlStr.push("<img src='"+cxt+"/images/bbs/checked.gif' width='16' height='16' />");
					break;
				}
			}
			htmlStr.push(obj[0] + "：</td><td width='200' bgcolor='#fffae2' >"+ div + "</td><td width='80' nowrap class=\"pl10\">" + obj[1] + "("+ bili + "%)</td>");
			if(this.userIsValid == 1){
				htmlStr.push("<td width='80' align='right'><input ");
				// 是否能够选择
				if (this.ballotType != null && this.ballotType == 1) {
					htmlStr.push("type='radio'");
				} else if (this.ballotType != null && this.ballotType == 2) {
					htmlStr.push("type='checkbox'");
				}
				htmlStr.push(" name='ballotIds' value='"+ obj[2] + "'></td>");
			}
			htmlStr.push("</tr>");
			$("#" + that.id + " table").append(htmlStr.join(""));
		}
		;
		this.play();
	};
	this.play = function() {
		var that = this;
		$("#" + this.id + " div").each(function(i, n) {
			if ($(n).attr('fag')) {
				$(n).animate({
					width : $(n).attr('fag') + '%'
				}, that.speed);
			}
		});
	};
};