<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="签到系统" name="keywords" />
	<%@ include file="/include/user.jsp"%>
 	<title>签到系统--${system_name}</title>  
</head>
  
  <body>
	<div class="e-tabbox">
		<!-- start 编写题的内容 -->
		<!-- start one -->
		<div class="e-item-mod">
			<h2 class="e-item-hd mb3">题干</h2>
			<div class="e-item-bd">
				<textarea class="texa" rows="3" cols=""></textarea>
			</div>
		</div>
		<!-- end one -->
		<!-- start 选项 -->
		<div class="e-item-mod mt15">
			<h2 class="e-item-hd mb3">选项</h2>
			<div class="e-item-bd">
				<p class="explain">（说明：每个选项前不需要加A、B、C、D，在制卷的时候系统会自动加上）</p>
				<table width="100%" class="e-option-wrap">
					<tr class="bar">
						<td class="order">A</td>
						<td class="radio"><input type="radio" /></td>
						<td><input class="inp" type="text" /></td>
						<td class="operate"><span class="del"></span></td>
					</tr>
					<tr class="bar">
						<td class="order">B</td>
						<td class="radio"><input type="radio" /></td>
						<td><input class="inp" type="text" /></td>
						<td class="operate"><span class="del"></span></td>
					</tr>
					<tr class="bar">
						<td class="order">C</td>
						<td class="radio"><input type="radio" /></td>
						<td><input class="inp" type="text" /></td>
						<td class="operate"><span class="del"></span></td>
					</tr>
					<tr class="bar">
						<td class="order">D</td>
						<td class="radio"><input type="radio" /></td>
						<td><input class="inp" type="text" /></td>
						<td class="operate"><span class="del"></span></td>
					</tr>
				</table>
				<div class="e-btn-box mt12">
					<button class="btn fr mr20" type="button" >增加选项</button>
				</div>
			</div>
		</div>
		<!-- end 选项 -->
		<!-- start 题目基本属性 -->
		<div class="e-item-mod mt15">
			<h2 class="e-item-hd mb3">题目基本属性</h2>
			<div class="e-item-bd">
				<table width="100%" class="e-tabform-wrap">
					<tr class="tbar">
						<td class="lab" style="width:70px;">难度：</td>
						<td>
							<ul class="e-star-wrap">
								<li title="基础题"><input class="mr5" type="radio" /><a class="star one" href="javascript:;"></a></li>
								<li title="综合基础题"><input class="mr5" type="radio" /><a class="star two" href="javascript:;"></a></li>
								<li title="基本扩展题"><input class="mr5" type="radio" /><a class="star three" href="javascript:;"></a></li>
								<li title="中等扩展题"><input class="mr5" type="radio" /><a class="star four" href="javascript:;"></a></li>
								<li title="综合扩展题"><input class="mr5" type="radio" /><a class="star five" href="javascript:;"></a></li>
							</ul>
						</td>
					</tr>
					<tr class="tbar">
						<td class="lab">知识点：</td>
						<td><input class="inp" type="text" placeholder="知识点之间用空格分隔" /></td>
					</tr>
					<tr class="tbar">
						<td class="lab">状态：</td>
						<td>
							<label><input class="vera mr8" type="radio" name="state" value="1" />可用</label>
							<input class="vera mr8 ml20" type="radio" name="state" value="2" id="stateno" /><label for="stateno">禁用</label>
						</td>
					</tr>
					<tr class="tbar">
						<td class="lab">加入题库：</td>
						<td>
							<label><input class="vera mr8" type="radio" name="jiontest" value="1" />是</label>
							<input class="vera mr8 ml20" type="radio" name="jiontest" value="2" id="fou" /><label for="fou">否</label>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- end 题目基本属性 -->
		<!-- start 添加反馈信息 -->
		<div class="e-item-mod mt15">
			<h2 class="e-item-hd mb3">添加反馈信息</h2>
			<div class="e-item-bd">
				<p class="explain mb3">解题思路分析</p>
				<textarea class="texa" rows="3" cols=""></textarea>
				<p class="explain mb3 mt10">答对后的反馈信息</p>
				<textarea class="texa" rows="3" cols=""></textarea>
				<p class="explain mb3 mt10">答错后的反馈信息</p>
				<textarea class="texa" rows="3" cols=""></textarea>
			</div>
		</div>
		<!-- end 添加反馈信息 -->
		<!-- end 编写题的内容 -->
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" >提&nbsp;&nbsp;交</button>
		<button class="btn mr25" type="button" >提交后继续添加</button>
		<button class="btn cancel" type="button" >取&nbsp;&nbsp;消</button>
	</div>
	<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
	};

	   jQuery(document).ready(function(){
			$(window).unbind('#winBody').bind('resize.winBody', function(){
				$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-30);
			});
		});
	</script>
  </body>
</html>
