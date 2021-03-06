package org.csr.common.user.support.taglib;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.csr.core.util.ObjUtil;


/**
 *ClassName:RoleTaglib.java<br/>
 *Date:2015-10-29 下午4:20:07
 *@author huayj
 *@version 1.0
 *@since JDK 1.7
 *功能描述:角色自定义标签
 */
public class RoleTaglib extends TagSupport {

	private static final long serialVersionUID = 1L;
	private String id = "";
	private String value = "";
	
	private String name = "";
	
	private String style = "width:200px;height: 25px;";
	
	private String clazz = "";

	private String idField = "id";

	private String textField = "name";

	private String url = "/role/ajax/findDropDownList.action";

	private String method = "post";

	private String dataOptions = "";
	
	private String multiple = "false";

	private String columns = "{field:'name',title:'安全角色名 ',width:70},{field:'roleType',title:'安全角色类型 ',width:100,dictionary:'roleType'},{field:'remark',title:'备注',width:100}";
	
	private String editable = "false";
	
	private String fitColumns = "true";
	
	private String panelWidth =  "550";
	
	private String panelHeight =  "260";
	
	private String pagination = "true";

	private String onChange ="";
	
	private String onClickRow="";
	
	private String toolbarId;
	
	private String agenciesId;

	
	public int doStartTag() throws JspException {
		try {
			ServletContext context = pageContext.getServletContext();
			StringBuffer htmlStr = new StringBuffer();
			
			htmlStr.append("<div class=\"pop-dropdownmenu-con pop-dropdownmenu-con-float\" style=\"display:inline;\">");
			
			htmlStr.append("<input");
			htmlStr.append(" class=\"easyui-combogrid\" ");
			htmlStr.append(" id=").append(id);
			htmlStr.append(" name=\"").append(name).append("\"");
			if (ObjUtil.isNotBlank(value)) {
				htmlStr.append(" value=\"").append(value).append("\"");
			}
			
			htmlStr.append(" data-options=").append("\"");
			if ("true".equals(multiple)) {
				htmlStr.append("multiple:").append(multiple).append(",");
			}
			
			if(ObjUtil.isNotBlank(dataOptions)){
				htmlStr.append(dataOptions).append(",");
			}
			if(ObjUtil.isNotBlank(onChange)){
				htmlStr.append("onChange:").append(onChange).append(",");
			}
			if(ObjUtil.isNotBlank(onClickRow)){
				htmlStr.append("onClickRow:").append(onClickRow).append(",");
			}
			if(ObjUtil.isNotBlank(panelHeight)){
				htmlStr.append("panelHeight:").append(panelHeight).append(",");
			}
			htmlStr.append("editable:").append(editable).append(",");
			htmlStr.append("panelWidth:'").append(panelWidth).append("',");
			htmlStr.append("idField:'").append(idField).append("',");
			htmlStr.append("textField:'").append(textField).append("',");
			htmlStr.append("url:'").append(context.getContextPath()+url).append("',");
			htmlStr.append("method:'").append(method).append("',");
			htmlStr.append("columns:[[").append(columns).append("]],");
			htmlStr.append("fitColumns:").append(fitColumns).append(",");
			htmlStr.append("pagination:").append(pagination).append(",");
			htmlStr.append("queryParams:'oldIds=").append(value).append("&");
			if(ObjUtil.isNotBlank(agenciesId)){
				htmlStr.append("agenciesId="+agenciesId);
			}
			htmlStr.append("',");
			if(ObjUtil.isNotEmpty(toolbarId)){
				htmlStr.append("toolbar:'#").append(toolbarId).append("'");
			}else{
				htmlStr.append("toolbar:'#").append(id).append("_roleToolbar'");
			}
			htmlStr.append("\"");
			htmlStr.append(" style=\"").append(style).append("\"");
			htmlStr.append(" type=\"text\"");
			htmlStr.append("></input>");
			
			if(ObjUtil.isEmpty(toolbarId)){
				htmlStr.append("<div id=\""+id+"_roleToolbar\" style=\"display: none;\" class=\"pop-dropdownmenu-top\">");
				htmlStr.append("<div class=\"btn-f fr\" style=\"width:106px;\">");
				htmlStr.append("<button class=\"operate-btn mr5\" type=\"button\" onclick=\"$('#"+id+"').combogrid('grid').datagrid('load',{'auto':true,'name!s':'like:$'+$('#"+id+"_name').val(),'agenciesId':'"+agenciesId+"'});\">查询</button>");
				htmlStr.append("</div><div class=\"pop-dropdown-search fl\" style=\"width:400px;\">");
				htmlStr.append("<dl><dd><label class=\"lab\" style=\"width:60px;\">角色名:</label><input id=\""+id+"_name\" style=\"width:100px; height:25px;\" type=\"text\"  class=\"easyui-textbox\"/>");
				htmlStr.append("</dd></dl></div></div>");
			}
			htmlStr.append("</div>");
			pageContext.getOut().write(htmlStr.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return super.doStartTag();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public String getPanelWidth() {
		return panelWidth;
	}

	public void setPanelWidth(String panelWidth) {
		this.panelWidth = panelWidth;
	}

	public String getMultiple() {
		return multiple;
	}

	public String getFitColumns() {
		return fitColumns;
	}

	public String getPagination() {
		return pagination;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String isMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}
	
	public String getOnClickRow() {
		return onClickRow;
	}

	public void setOnClickRow(String onClickRow) {
		this.onClickRow = onClickRow;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String isFitColumns() {
		return fitColumns;
	}

	public void setFitColumns(String fitColumns) {
		this.fitColumns = fitColumns;
	}

	public String isPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getToolbarId() {
		return toolbarId;
	}

	public void setToolbarId(String toolbarId) {
		this.toolbarId = toolbarId;
	}

	public String getPanelHeight() {
		return panelHeight;
	}

	public void setPanelHeight(String panelHeight) {
		this.panelHeight = panelHeight;
	}

	public String getAgenciesId() {
		return agenciesId;
	}

	public void setAgenciesId(String agenciesId) {
		this.agenciesId = agenciesId;
	}
	
}
