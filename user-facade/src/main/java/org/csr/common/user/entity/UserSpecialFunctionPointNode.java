package org.csr.common.user.entity;

import java.util.List;

import org.csr.common.user.domain.UserSpecialFunctionPoint;
import org.csr.core.tree.TreeNode;
import org.csr.core.web.bean.VOBase;

/**
 * 用户权限
 */
public class UserSpecialFunctionPointNode extends VOBase<Long> implements
		TreeNode<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long parentId;
	private String name;
	private String code;
	private String icon;
	private String dufIcon;
	private String forwardUrl;
	private String urlRule;
	private Integer functionPointType;
	private Integer isAnonymous;
	private Integer browseLogLevel;
	private Integer operationLogLevel;
	private Integer enableBan;
	private String remark;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;
	private Integer helpStatus;
	private Integer authenticationMode;
	private String state = "open";
	/**
	 * 1 是；2 否 增加权限就是给用户赋予该功能的权限；否则就是把该功能权限取消
	 */
	private Integer isAddPrivilege;

	public UserSpecialFunctionPointNode() {
	}

	public UserSpecialFunctionPointNode(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDufIcon() {
		return dufIcon;
	}

	public void setDufIcon(String dufIcon) {
		this.dufIcon = dufIcon;
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

	public Integer getFunctionPointType() {
		return functionPointType;
	}

	public void setFunctionPointType(Integer functionPointType) {
		this.functionPointType = functionPointType;
	}

	public Integer getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public Integer getBrowseLogLevel() {
		return browseLogLevel;
	}

	public void setBrowseLogLevel(Integer browseLogLevel) {
		this.browseLogLevel = browseLogLevel;
	}

	public Integer getOperationLogLevel() {
		return operationLogLevel;
	}

	public void setOperationLogLevel(Integer operationLogLevel) {
		this.operationLogLevel = operationLogLevel;
	}

	public Integer getEnableBan() {
		return enableBan;
	}

	public void setEnableBan(Integer enableBan) {
		this.enableBan = enableBan;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getChildren() {
		return children;
	}

	@SuppressWarnings("rawtypes")
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Integer getHelpStatus() {
		return helpStatus;
	}

	public void setHelpStatus(Integer helpStatus) {
		this.helpStatus = helpStatus;
	}

	public Integer getAuthenticationMode() {
		return authenticationMode;
	}

	public void setAuthenticationMode(Integer authenticationMode) {
		this.authenticationMode = authenticationMode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getIsAddPrivilege() {
		return isAddPrivilege;
	}

	public void setIsAddPrivilege(Integer isAddPrivilege) {
		this.isAddPrivilege = isAddPrivilege;
	}

	@Override
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@Override
	public Long getParentId() {
		return this.parentId;
	}
	
	public static UserSpecialFunctionPointNode wrapBean(UserSpecialFunctionPoint doMain) {
		UserSpecialFunctionPointNode bean=new UserSpecialFunctionPointNode();
		bean.setId(doMain.getId());
		return bean;
	}
}
