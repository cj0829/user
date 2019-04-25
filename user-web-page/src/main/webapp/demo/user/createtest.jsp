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
		<table class="e-main-con-box" width=100% >
			<tr>
				<td class="e-main-con-left">
					<!--start left -->
					<div class="e-test-wrap">
						<div class="e-test-questions">
							<!-- start one -->
							<div class="test-mod mt10">
								<table class="test-mod-hd" width=100%>
									<tr>
										<td class="num">
											<span>1</span>
										</td>
										<td class="tit">
											<p>
											<font class="font-c">(单选题)</font>
											10月1号是？
											</p>
										</td>
									</tr>
								</table>
								<div class="test-mod-bd">
								<ul class="e-questionCheckbar">
									<li class="bar">A.<p>春节</p></li>
									<li class="bar">B.<p>儿童节</p></li>
									<li class="bar">C.<p>元宵节</p></li>
									<li class="bar">D.<p>国庆节</p></li>
								</ul>
								</div>
							</div>
							<!-- end one -->
							<!-- start 多选题 -->
							<div class="test-mod mt10">
								<table class="test-mod-hd" width=100%>
									<tr>
										<td class="num">
											<span>2</span>
										</td>
										<td class="tit">
											<p>
											<font class="font-c">(多选题)</font>
											10月1号是？
											</p>
										</td>
									</tr>
								</table>
								<div class="test-mod-bd">
								<ul class="e-questionCheckbar">
									<li class="bar">A.<p>春节</p></li>
									<li class="bar">B.<p>儿童节</p></li>
									<li class="bar">C.<p>元宵节</p></li>
									<li class="bar">D.<p>国庆节</p></li>
								</ul>
								</div>
							</div>
							<!-- end 多选题 -->
							<!-- start 连线题 -->
							<div class="test-mod mt10">
								<table class="test-mod-hd" width=100%>
									<tr>
										<td class="num">
											<span>3</span>
										</td>
										<td class="tit">
											<p>
											<font class="font-c">(连线题)</font>
											找出下面对应节日的日期
											</p>
										</td>
									</tr>
								</table>
								<div class="test-mod-bd">
								<ul class="e-questionCheckbar">
									<li class="bar">
										<div class="ovh">
											<div class="bar-L">A.<p>元旦</p></div>
											<div class="bar-R">a.<p>6月1日</p></div>
										</div>
									</li>
									<li class="bar">
										<div class="ovh">
											<div class="bar-L">B.<p>儿童节</p></div>
											<div class="bar-R">b.<p>3月8日</p></div>
										</div>
									</li>
									<li class="bar">
										<div class="ovh">
											<div class="bar-L">C.<p>植树节</p></div>
											<div class="bar-R">c.<p>1月1日</p></div>
										</div>
									</li>
									<li class="bar">
										<div class="ovh">
											<div class="bar-L">D.<p>妇女节</p></div>
											<div class="bar-R">d.<p>3月12日</p></div>
										</div>
									</li>
								</ul>
								</div>
							</div>
							<!-- end 连线题 -->
						</div>
					</div>
					<!--end left -->
				</td>
				<td class="e-main-con-right" style="width:203px;">
					<!--start right -->
					<div class="e-operate-box">
						<div class="e-small-mode">
							<h2 class="e-sm-mode-hd"><span></span>题型库</h2>
							<ul class="e-sm-mode-bd">
								<li>
									<a class="curr" href="javascript:;" title="单选题">
										<em class="radio"></em>
										<span class="t_elli">单选题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="多选题">
										<em class="multiple-choice"></em>
										<span class="t_elli">多选题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="判断题">
										<em class="judgment"></em>
										<span class="t_elli">判断题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="排序题">
										<em class="sort"></em>
										<span class="t_elli">排序题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="连线题">
										<em class="on-line"></em>
										<span class="t_elli">连线题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="填空题">
										<em class="fill-blanks"></em>
										<span class="t_elli">填空题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="改错题">
										<em class="error-correction"></em>
										<span class="t_elli">改错题</span>
									</a>
								</li>
								<li>
									<a href="javascript:;" title="问答题">
										<em class="question-answer"></em>
										<span class="t_elli">问答题</span>
									</a>
								</li>
							</ul>
						</div>
						<div class="e-small-mode mt15">
							<h2 class="e-sm-mode-hd"><span></span>空白文本输入框</h2>
							<ul class="e-sm-mode-bd">
								<li>
									<a href="javascript:;" title="空白文本">
										<em class="radio"></em>
										<span class="t_elli">空白文本</span>
									</a>
								</li>
							</ul>
						</div>
					</div>
					<!--end right -->
				</td>
			</tr>
		</table>
	</div>
	<div class="e-tab-btnbox">
		<button class="btn mr25" type="button" >保&nbsp;&nbsp;存</button>
		<button class="btn" type="button" >生成试卷</button>
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
