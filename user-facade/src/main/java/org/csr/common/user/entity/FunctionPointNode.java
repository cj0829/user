package org.csr.common.user.entity;

import java.util.List;

import org.csr.common.user.domain.FunctionPoint;
import org.csr.core.tree.TreeNode;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:FunctionPointNode.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
public class FunctionPointNode extends VOBase<Long> implements TreeNode<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3614667370025562971L;
	private Long id;
	private FunctionPointNode functionPoint;
	private String name;
	private String code;
	private String icon;
	private String dufIcon;
	private String forwardUrl;
	private String urlRule;
	private Byte functionPointType;
	private Byte isAnonymous;
	private Byte browseLogLevel;
	private Byte operationLogLevel;
	private Byte enableBan;
	private String remark;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;
	private Byte helpStatus;
	private Byte authenticationMode;
	private String state = "open";

	public FunctionPointNode(){}
	public FunctionPointNode(Long id) {
		this.id=id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FunctionPointNode getFunctionPoint() {
		return functionPoint;
	}

	public void setFunctionPoint(FunctionPointNode functionPoint) {
		this.functionPoint = functionPoint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getUrlRule() {
		return urlRule;
	}

	public void setUrlRule(String urlRule) {
		this.urlRule = urlRule;
	}

	@Override
	public void setParentId(Long parentId) {

	}

	@Override
	public Long getParentId() {
		if (ObjUtil.isNotEmpty(functionPoint)) {
			return functionPoint.getId();
		}
		return null;
	}

	public Byte getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Byte isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
//
	public Byte getBrowseLogLevel() {
		return browseLogLevel;
	}

	public void setBrowseLogLevel(Byte browseLogLevel) {
		this.browseLogLevel = browseLogLevel;
	}

	public Byte getOperationLogLevel() {
		return operationLogLevel;
	}

	public void setOperationLogLevel(Byte operationLogLevel) {
		this.operationLogLevel = operationLogLevel;
	}

	public Byte getEnableBan() {
		return enableBan;
	}

	public void setEnableBan(Byte enableBan) {
		this.enableBan = enableBan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<TreeNode> getChildren() {
		return children;
	}

	public Byte getHelpStatus() {
		return helpStatus;
	}

	public void setHelpStatus(Byte helpStatus) {
		this.helpStatus = helpStatus;
	}

	public Byte getFunctionPointType() {
		return functionPointType;
	}

	public void setFunctionPointType(Byte functionPointType) {
		this.functionPointType = functionPointType;
	}

	public Byte getAuthenticationMode() {
		return authenticationMode;
	}

	public void setAuthenticationMode(Byte authenticationMode) {
		this.authenticationMode = authenticationMode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the dufIcon
	 */
	public String getDufIcon() {
		return dufIcon;
	}

	/**
	 * @param dufIcon the dufIcon to set
	 */
	public void setDufIcon(String dufIcon) {
		this.dufIcon = dufIcon;
	}
	
	public String getPath() {
		StringBuffer path=new StringBuffer(id!=null?id.toString():"");
		FunctionPointNode parent=functionPoint;
		while (ObjUtil.isNotEmpty(parent)) {
			path.insert(0, parent.getId()+"/");
			parent=parent.functionPoint;
		}
		return path.toString();
	}
	public static FunctionPointNode wrapBean(FunctionPoint doMain) {
		FunctionPointNode node = new FunctionPointNode();
		node.setId(doMain.getId());
		node.setName(doMain.getName());
		node.setCode(doMain.getCode());
		node.setForwardUrl(doMain.getForwardUrl());
		node.setUrlRule(doMain.getUrlRule());
		node.setIcon(doMain.getIcon());
		node.setDufIcon(doMain.getDufIcon());
		node.setEnableBan(doMain.getEnableBan());
		node.setFunctionPointType(doMain.getFunctionPointType());
		if(ObjUtil.isNotEmpty(doMain.getFunctionPoint())){
			node.setFunctionPoint(wrapBean(doMain.getFunctionPoint()));
		}
		node.setBrowseLogLevel(doMain.getBrowseLogLevel());
		node.setOperationLogLevel(doMain.getOperationLogLevel());
		node.setIsAnonymous(doMain.getIsAnonymous());
		node.setAuthenticationMode(doMain.getAuthenticationMode());
		node.setHelpStatus(doMain.getHelpStatus());
		node.setRemark(doMain.getRemark());
		return node;
	}
}
