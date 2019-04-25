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
	<!--start navigation-->
	<script type="text/javascript" src="${cxt}/person/userHeader.action"></script> 
	<!--end navigation-->
	<div class="content-wrap ml20 mr20 mt10">
		<!-- start now Navigation-->
		<div class="current-nav-bar mt10 mb10">
			当前位置：
			<a href="javascript:;">首页</a><span> > </span>
			<a href="javascript:;">试题库</a><span> > </span>
			<a href="javascript:;" class="curr">编辑试题</a>
		</div>
		<!-- end now Navigation-->
		<!-- start 主要内容区域 -->
		<div class="e-tabchange-wrap">
			<div class="e-tabchange-bd">
				<!-- start 搜索 -->
				<div class="e-search-wrap main-expand-form mb10">
				<table width="100%">
					<tr>
						<td class="e-search-con">
						<ul>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">题目</label><input type="text" style="width:115px; height:22px;" /></li>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">关键词</label><input type="text" style="width:115px; height:22px;" /></li>
							<li class="e-search-td">
								<label class="mr8 lab" style="width:60px;">状态</label>
								<select style="width:115px; height:22px;">
									<option>待评阅</option>
									<option>已评阅</option>
								</select>
							</li>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">试题类型</label><input type="text" style="width:115px; height:22px;" /></li>
							<li class="e-search-td"><label class="mr8 lab" style="width:60px;">创建时间</label><input type="text" style="width:115px; height:22px;" /></li>
						</ul>
						</td>
						<td class="e-search-btn-box" style="width:94px;">
							<button class="btn" type="button" >搜索</button>
						</td>
					</tr>
				</table>
				</div>
				<!-- end 搜索 -->
				<div class="main-tableContainer cl-margin">
					<!--start 表格操作btn -->
					<div class="e-tabbtn-box mb2">
						<button class="tabbtn fr" type="button" >创建试题</button>
						<button class="tabbtn mr15 fr" type="button" >导出文档</button>
						<button class="tabbtn mr15 fr" type="button" >编辑答题卡</button>
					</div>
					<!--end 表格操作btn -->
					<table class="e-ordinary-tab" width="100%" cellpadding="0" cellspacing="0">
						<thead>
							<tr>
								<th style="width:40px;">题号</th>
								<th style="width:60px;">题型</th>
								<th>答案</th>
								<th style="width:50px;"><input class="inp" type="text" value="" /></th>
								<th style="width:270px;">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>1.</td>
								<td>单选题</td>
								<td>单选题</td>
								<td><input class="inp" type="text" value="" /></td>
								<td>
									<a class="alink" href="javascript:;">添加选项</a>
									<a class="alink" href="javascript:;">删除选项</a>
									<a class="alink" href="javascript:;">复制</a>
									<a class="alink" href="javascript:;">删除</a>
									<a class="alink" href="javascript:;">上移</a>
									<a class="alink" href="javascript:;">下移</a>
								</td>
							</tr>
							<tr>
								<td>1.</td>
								<td>单选题</td>
								<td>单选题</td>
								<td><input class="inp" type="text" value="" /></td>
								<td>
									<a class="alink" href="javascript:;">添加选项</a>
									<a class="alink" href="javascript:;">删除选项</a>
									<a class="alink" href="javascript:;">复制</a>
									<a class="alink" href="javascript:;">删除</a>
									<a class="alink" href="javascript:;">上移</a>
									<a class="alink" href="javascript:;">下移</a>
								</td>
							</tr>
							<tr>
								<td>1.</td>
								<td>单选题</td>
								<td>单选题</td>
								<td><input class="inp" type="text" value="" /></td>
								<td>
									<a class="alink" href="javascript:;">添加选项</a>
									<a class="alink" href="javascript:;">删除选项</a>
									<a class="alink" href="javascript:;">复制</a>
									<a class="alink" href="javascript:;">删除</a>
									<a class="alink" href="javascript:;">上移</a>
									<a class="alink" href="javascript:;">下移</a>
								</td>
							</tr>
						</tbody>
					</table>
				</div>	
			</div>
		</div>
		<!-- end 主要内容区域 -->
	</div>
	<!-- start foot -->
	<%@include file="/include/footer.jsp"%>
	<!-- end foot -->
  </body>
</html>
