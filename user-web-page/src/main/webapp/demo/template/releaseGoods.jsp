<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge " />
  <meta content="" name="keywords" />
	<%@ include file="../../WEB-INF/jsp/common/common/common.jsp"%>
 	<title>发布商品</title>  
</head>
  
  <body>
  <div style="padding:10px 0;">
	<div class="s_mformmod">
		<h2 class="s_mformhd">一口价宝贝发布</h2>
		<div class="s_mformbd">
			<div class="framemod">
				<h2 class="framehd">1. 宝贝基本信息</h2>
				<div class="framebd">
					<table class="e-form-tab" width="100%" >
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em>
								<label class="lab">宝贝类型</label>
							</th>
							<td class="e-form-td">
								<div class="checkbox-wrap">
									<label><input type="radio" name="radio0" value="" checked="checked" />全新</label>
									<label><input type="radio" name="radio0" value="" checked="checked" />二手</label>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em>
								<label class="lab">宝贝标题</label>
							</th>
							<td class="e-form-td">
								<div class="inputbox">
									<input class="easyui-textbox"  style="width:700px;height:25px" />
									<span class="input-num"><strong>0</strong>/60</span>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<label class="lab">宝贝卖点</label>
							</th>
							<td class="e-form-td">
								<div class="inputbox">
									<input class="easyui-textbox" data-options="multiline:true" value="" style="width:700px;height:60px" />
									<span class="input-num"><strong>0</strong>/60</span>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<label class="lab">宝贝属性</label>
							</th>
							<td class="e-form-td">
								<div class="tips-info">错误填写宝贝属性，可能会引起宝贝下架或搜索流量减少，影响您的正常销售，请认真准确填写！</div>
								<div class="property">
									<table class="property-tab" width="50%">
										<tr>
											<th class="barth" style="width:180px;"><label class="lab">货号</label></th>
											<td class="bartd">
												<input class="easyui-textbox" style="width:200px;height:25px" />
											</td>	
										</tr>
										<tr>
											<th class="barth"><em class="mustOptions">*</em><label class="lab">品牌</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><label class="lab">上市时间</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><em class="mustOptions">*</em><label class="lab">适合季节</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><label class="lab">袖型</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
									</table>
									<table class="property-tab" width="50%">
										<tr>
											<th class="barth" style="width:100px;"><label class="lab">厚薄</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><em class="mustOptions">*</em><label class="lab">版型</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><label class="lab">材质</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><em class="mustOptions">*</em><label class="lab">适用场景</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
										<tr>
											<th class="barth"><label class="lab">服装款式细节</label></th>
											<td class="bartd">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
											</td>	
										</tr>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em><label class="lab">电脑端宝贝图片</label>
							</th>
							<td class="e-form-td">
								<div class="tips-info">宝贝主图大小不能超过3MB；700*700 以上图片上传后宝贝详情页自动提供放大镜功能。第五张图发商品白底图可增加首页曝光机会 </div>
								<div class="multimediaimg">
									<div class="image-wrap">
										<ul class="image-list">
											<li>
												<a class="uploadImg" href="javascript:;" title="上传图片"></a>
												<span class="info"><em class="mustOptions">*</em>宝贝主图</span>
											</li>
											<li>
												<a class="uploadImg" href="javascript:;" title="上传图片"></a>
											</li>
											<li>
												<a class="uploadImg" href="javascript:;" title="上传图片"></a>
											</li>
											<li>
												<a class="uploadImg" href="javascript:;" title="上传图片"></a>
											</li>
											<li>
												<a class="uploadImg" href="javascript:;" title="上传图片"></a>
												<span class="info">宝贝白底图</span>
											</li>
										</ul>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<label class="lab">宝贝规格</label>
							</th>
							<td class="e-form-td">
								<div class="standard-ma">
									<div class="categorymod">
										<h2 class="hd">颜色分类</h2>
										<div class="bd color">
											<div class="tips-info">选择标准颜色可增加搜索/导购机会，标准颜色还可填写颜色备注信息（偏深、偏亮等）！</div>
											<div class="bar">
												<input type="checkbox" class="colorSelect" name="colorSelect" />
												<input class="easyui-textbox" data-options="prompt:'选择或输入主色'" style="width:300px;height:25px" />
												<input class="easyui-textbox" data-options="prompt:'备注（如偏深偏浅等）'" style="width:150px;height:25px" />
												<input class="btn" type="button" value="上传图片" />
											</div>
										</div>
									</div>
									<div class="categorymod">
										<h2 class="hd">尺码</h2>
										<div class="bd size">
											<div class="tips-info">选择标准尺码搜索/导购机会，标准颜色还可填写尺码备注信息（偏大、偏小等）！</div>
											<div class="checkbox-wrap">
												<label><input type="radio" name="radio0" value="" checked="checked" />通用</label>
												<label><input type="radio" name="radio0" value="" checked="checked" />中国码</label>
												<label><input type="radio" name="radio0" value="" checked="checked" />英码</label>
												<label><input type="radio" name="radio0" value="" checked="checked" />均码</label>
											</div>
											<ul class="sizelist">
												<li>
													<input type="checkbox" value="1" />XS
												</li>
												<li>
													<input type="checkbox" value="1" />S
												</li>
												<li>
													<input type="checkbox" value="1" />M
												</li>
												<li>
													<input type="checkbox" value="1" />L
												</li>
												<li>
													<input type="checkbox" value="1" />XL
												</li>
												<li>
													<input type="checkbox" value="1" />XXL
												</li>
												<li>
													<input type="checkbox" value="1" />XXXL
												</li>
											</ul>
											<div class="customsize">
												<input type="checkbox" value="1" />
												<input class="easyui-textbox" data-options="prompt:'自定义尺码'" style="width:150px;height:25px" />
											</div>
										</div>
									</div>
									<div class="categorymod">
										<h2 class="hd">宝贝销售规格</h2>
										<div class="bd color">
											<div class="tips-info">该类目下：颜色分类、尺码，请全选或全不选，如果只选一部分则无法保存对应的价格和库存</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em><label class="lab">一口价及总库存</label>
							</th>
							<td class="e-form-td">
								<div class="price-wrap">
									<span class="tips-info">本类目下，宝贝价格不能低于8.00元</span>
									<table class="price-table">
										<thead> 
											<tr>
												<th><em class="mustOptions">*</em>价格（元）</th>
												<th><em>*</em>总数量（件）</th>
												<th>商家编码</th>
												<th>商品条形码</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><div class="inputbox"><input type="text" maxlength="9" /></div></td>
												<td><div class="inputbox"><input type="text" maxlength="9" /></div></td>
												<td><div class="inputbox"><input type="text" /></div></td>
												<td><div class="inputbox"> <input type="text" /></div></td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em>
								<label class="lab">采购地</label>
							</th>
							<td class="e-form-td">
								<div class="checkbox-wrap">
									<label><input type="radio" name="radio0" value="" checked="checked" />国内</label>
									<label><input type="radio" name="radio0" value="" checked="checked" />海外及港澳台</label>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em>
								<label class="lab">电脑端描述</label>
							</th>
							<td class="e-form-td">
								<input class="easyui-textbox" data-options="multiline:true" value="" style="width:100%;height:80px" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="framemod">
				<h2 class="framehd">2. 宝贝物流服务</h2>
				<div class="framebd">
					<table class="e-form-tab" width="100%">
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em>
								<label class="lab">提取方式</label>
							</th>
							<td class="e-form-td">
								<div class="checkbox-wrap">
									<label ><input type="checkbox" name="radio0" value="1" checked="checked" />国内</label>
									<label ><input type="checkbox" name="radio0" value="2"  />海外及港澳台</label>
								</div>
								<div class="tips-info"><b>物流设置  </b>为了提升消费者购物体验，淘宝要求全网商品设置运费模板，如何<a href="javacript:;">使用模板</a></div>
								<div class="freightTemplate">
									<table class="e-form-tab" width="100%">
									<tr>
										<th class="e-form-th" style="width:80px;">
											<em class="mustOptions">*</em><label class="lab">运费模板</label>
										</th>	
										<td class="e-form-td">
											<div class="freightTemplateRtop">
												<div class="expand-dropdown-select" style="display:inline;">
													<select class="easyui-combobox" name="language" style="width:200px;height:25px" data-options="panelHeight:'auto'">
														<option value="1">1</option>
														<option value="2">2</option>
														<option value="3">3</option>
														<option value="4">4</option>
													</select>
												</div>
												<input class="btn" type="button" value="新建运费模板" />
											</div>
											<div class="freightTemplateRfoot">
												<div class="btnbox"><input class="btn" type="button" value="快递" /></div>
												<table class="tab" width="100%">
													<tr>
														<td class="left">默认运费：1件内8元， 每增加1件，加8元</td>
														<td class="right" style="width:80px;"><a href="javacript:;">查看详情</a></td>
													</tr>
												</table>
												<div class="address">发货地：北京市东城区</div>
											</div>
										</td>
									</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="framemod">
				<h2 class="framehd">3. 售后保障信息</h2>
				<div class="framebd">
					<table class="e-form-tab" width="100%">
						<tr>
							<th class="e-form-th" style="width:100px;">
								<label class="lab">售后服务</label>
							</th>
							<td class="e-form-td">
								<div class="checkbox-wrap">
									<label ><input type="checkbox" name="checkbox0" value="1" />提供发票</label>
								</div>
								<div class="checkbox-wrap">
									<label ><input type="checkbox" name="checkbox0" value="2" />保修服务</label>
								</div>
								<div class="checkbox-wrap">
									<label ><input type="checkbox" name="checkbox0" value="3" />退换货承诺：凡使用支付宝服务付款购买本店商品，若存在质量问题或与描述不符，本店将主动提供退换货服务并承担来回邮费</label>
								</div>
								<div class="checkbox-wrap">
									<label ><input type="checkbox" name="checkbox0" value="4" checked="checked" disabled="disabled" />服务承诺：该类商品，必须支持【七天退货】服务，承诺更好服务可通过【交易合约】设置</label>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
			<div class="framemod">
				<h2 class="framehd">4. 宝贝其他信息</h2>
				<div class="framebd">
					<table class="e-form-tab" width="100%">
						<tr>
							<th class="e-form-th" style="width:100px;">
								<label class="lab">库存计数</label>
							</th>
							<td class="e-form-td">
								<div class="checkbox-wrap">
									<label><input type="radio" name="radio0" value="" checked="checked" />买家拍下减库存</label>
									<label><input type="radio" name="radio0" value="" />买家付款减库存</label>
								</div>
							</td>
						</tr>
						<tr>
							<th class="e-form-th" style="width:100px;">
								<em class="mustOptions">*</em>
								<label class="lab">上架时间</label>
							</th>
							<td class="e-form-td">
								<div class="checkbox-wrap">
									<label><input type="radio" name="radio0" value="" checked="checked" />立刻上架</label>
									<label><input type="radio" name="radio0" value="" />定时上架</label>
									<label><input type="radio" name="radio0" value="" />放入仓库</label>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			
		</div>
	</div>
	
	</div>
	
	<div class="e-btn-box mt15 center">
		<button class="btn mr25" type="submit">发布</button>
		<button class="btn" type="button" >保存草稿</button>
	</div>
	<script  type="text/javascript"> 
	
	</script>
  </body>
</html>
