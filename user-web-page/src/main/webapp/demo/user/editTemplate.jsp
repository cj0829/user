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
  	<!-- start tab切换按钮 -->
  	<div class="e-tabbar">
  		<a href="javascript:;" class="t-btn curr">主要</a>
  		<a href="javascript:;" class="t-btn">相关信息</a>
  	</div>
	<!-- end tab切换按钮 -->
	<!-- start 内容 -->
	<div class="e-tabbox main-expand-form">
		<!-- start 步骤 -->
		<ul class="main-tab-step mb10">
	      <li class="completed-step"><a href="javascript:;"><span>1</span>职位详细信息</a></li>
	      <li class="active-step"><a href="javascript:;"><span>2</span>任职者信息</a></li>
	    </ul>
	    <!-- end 步骤 -->
		<!-- start one -->
		<div class="e-form-mod">
			<h2 class="e-form-mod-tit mb15"><span class="mr5">第一步</span>设置试卷基本属性</h2>
			<table class="e-form-tab" width=100%>
				<tr>
					<th class="e-form-th" style="width:106px;" ><label>试卷小标题</label></th>
					<td class="e-form-td"><input class="inp" type="text" style="width:230px;" /></td>
				</tr>
				<tr>
					<th class="e-form-th"><label>试卷大标题</label></th>
					<td class="e-form-td"><input class="inp" type="text" style="width:230px;" /></td>
				</tr>
				<tr>
					<th class="e-form-th"><label>学科</label></th>
					<td class="e-form-td">
						<select style="width:90px; height:22px;">
							<option>数学</option>
							<option>语文</option>
							<option>英语</option>
						</select>
					</td>
				</tr>
				<tr>
					<th class="e-form-th"><label>年级</label></th>
					<td class="e-form-td">
						<select style="width:90px; height:22px;">
							<option>一年级</option>
							<option>二年级</option>
							<option>三年级</option>
						</select>
					</td>
				</tr>
				<tr>
					<th class="e-form-th"><label>答卷时长</label></th>
					<td class="e-form-td"><input class="inp" type="text" style="width:60px;" /><span class="ml15">（单位：分钟  为0表示不限时长）</span></td>
				</tr>
				<tr>
					<th class="e-form-th"><label>多选题漏选得分</label></th>
					<td class="e-form-td"><input class="inp" type="text" style="width:60px;" /><span class="ml15">（为0表示不给分）</span></td>
				</tr>
				<tr>
					<th class="e-form-th last"><label>选题方式</label></th>
					<td class="e-form-td last">
						<label><input class="vera mr8" type="radio" name="selectionway" value="1" />手工选题</label>
						<input class="vera mr8 ml20" type="radio" name="selectionway" value="1" id="intelligent" /><label for="intelligent">智能选题</label>
						<a href="javascript:;" class="small-btn ml8">编辑选题规则</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- end one -->
		<!-- start 第二步 -->
		<div class="e-form-mod mt25">
			<h2 class="e-form-mod-tit mb15"><span class="mr5">第二步</span>设置试卷题型</h2>
			<div class="e-form-mod-bd">
				<table class="e-form-tab" width="100%">
					<tr>
						<th class="e-form-th" style="width:136px;"><label>试卷包含的大题数量</label></th>
						<td class="e-form-td">
							<select style="width:90px; height:22px;">
								<option>1</option>
								<option>2</option>
								<option>3</option>
							</select>
						</td>
					</tr>
				</table>
				<table class="e-test-set" width="100%">
					<tr>
						<th style="width:100px;">重命名试题类型</th>
						<th style="width:100px;">试题原始类型</th>
						<th style="width:50px;">顺序</th>
						<th style="width:50px;">分值</th>
						<th>题型说明文字</th>
						<th style="width:20px;"></th>
					</tr>
					<tr>
						<td><input style="width:92px; height:16px;" class="test-name" type="text" /></td>
						<td>
							<select style="width:85px; height:22px;">
								<option>混合类型</option>
								<option>单选题</option>
								<option>多选题</option>
							</select>
						</td>
						<td>1</td>
						<td>2</td>
						<td><input style="width:100%; height:16px;" class="test-explain" type="text" /></td>
						<td><a href="javascript:;" class="del"></a></td>
					</tr>
					<tr>
						<td><input style="width:92px; height:16px;" class="test-name" type="text" /></td>
						<td>
							<select style="width:85px; height:22px;">
								<option>混合类型</option>
								<option>单选题</option>
								<option>多选题</option>
							</select>
						</td>
						<td>1</td>
						<td>2</td>
						<td><input style="width:100%; height:16px;" class="test-explain" type="text" /></td>
						<td><a href="javascript:;" class="del"></a></td>
					</tr>
				</table>
			</div>
		</div>
		<!-- end 第二步 -->
		<!-- start 第三步 -->
		<div class="e-form-mod mt25">
			<h2 class="e-form-mod-tit mb15"><span class="mr5">第三步</span>设置试题显示方式</h2>
			<table class="e-form-tab" width=100%>
				<tr>
					<th class="e-form-th" style="width:164px;"><label>选择题选项排序依据</label></th>
					<td class="e-form-td">
						<label><input class="vera mr8" type="radio" name="reorder" value="1" checked="checked" />录入顺序</label>
						<input class="vera mr8 ml20" type="radio" name="reorder" value="2" id="reorder2" /><label for="reorder2">乱序</label>
					</td>
				</tr>
				<tr>
					<th class="e-form-th" style="width:150px;"><label>试题显示方式</label></th>
					<td class="e-form-td">
						<label><input class="vera mr8" type="radio" name="showway" value="1" checked="checked" />全部在一页</label>
						<input class="vera mr8 ml20" type="radio" name="showway" value="2" id="way2" /><label for="way2">一小题一页</label>
						<input class="vera mr8 ml20" type="radio" name="showway" value="2" id="way3" /><label for="way3">一大题一页</label>
					</td>
				</tr>
				<tr>
					<th class="e-form-th"><label>自动添加选择题选项序号</label></th>
					<td class="e-form-td">
						<label><input class="vera mr8" type="radio" name="autonum" value="1" checked="checked" />是</label>
						<input class="vera mr8 ml20" type="radio" name="autonum" value="2" id="no2" /><label for="no2">否</label>
					</td>
				</tr>
				<tr>
					<th class="e-form-th last"><label>选择试卷模板</label></th>
					<td class="e-form-td last">
						<select style="width:85px; height:22px;">
							<option>古典风格</option>
							<option>清新风格</option>
							<option>经典风格</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<!-- end 第三步 -->
		<!-- start 第四步 -->
		<div class="e-form-mod mt25">
			<h2 class="e-form-mod-tit mb15"><span class="mr5">第四步</span>编辑试卷说明文字</h2>
			<div class="e-form-mod-bd">
				<table class="e-form-tab" width=100%>
					<tr>
						<th class="e-form-th" style="width:68px; vertical-align:top;"><label>卷首说明</label></th>
						<td class="e-form-td">
							<textarea class="texa" rows="3" cols=""></textarea>
						</td>
					</tr>
					<tr>
						<th class="e-form-th" style="width:68px; vertical-align:top;"><label>卷尾说明</label></th>
						<td class="e-form-td">
							<textarea class="texa" rows="3" cols=""></textarea>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<!-- end 第四步 -->
	</div>
	<!-- end 内容 -->
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" >提&nbsp;&nbsp;交</button>
		<button class="btn mr25" type="button" >恢复默认值</button>
		<button class="btn cancel" type="button" >取&nbsp;&nbsp;消</button>
	</div>
	<script  type="text/javascript"> 
	window.onload=function(){
		$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-40);
	};

	   jQuery(document).ready(function(){
			$(window).unbind('#winBody').bind('resize.winBody', function(){
				$(".e-tabbox").height($(this).height()-$(".e-tab-btnbox").height()-$(".e-tabbar").height()-40);
			});
		});
	</script>
  </body>
</html>
