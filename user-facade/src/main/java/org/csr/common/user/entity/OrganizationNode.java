package org.csr.common.user.entity;


import java.util.ArrayList;
import java.util.List;

import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.tree.TreeNode;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:OrganizationNode.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class OrganizationNode extends VOBase<Long> implements TreeNode<Long>{

	/**
	 * serialVersionUID:
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -3365266456674798098L;
	private Long id;
	private Long parentId;
	private String name;
	private String parentName;
	private String aliases;
	private String adminUserName;
	private Long adminUserId;
	private Long adminRoleId;
	private Long safeResourceCollectionId;
	private Byte organizationStatus;
	private Integer organizationLevel;
	private Integer leader;
	private Integer childCount; 
	private String state = "closed";
	private String remark;
	private Long root;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;
	
	/**在资源库中是否存在*/
	private boolean checked;

	public OrganizationNode() {
	}

	public OrganizationNode(long id) {
		this.id = id;
	}


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrganizationLevel() {
		return this.organizationLevel;
	}

	public void setOrganizationLevel(Integer organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public Integer getLeader() {
		return this.leader;
	}

	public void setLeader(Integer leader) {
		this.leader = leader;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public Long getAdminUserId() {
		return adminUserId;
	}

	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}
	
	public Long getAdminRoleId() {
		return adminRoleId;
	}

	public void setAdminRoleId(Long adminRoleId) {
		this.adminRoleId = adminRoleId;
	}

	public Long getSafeResourceCollectionId() {
		return safeResourceCollectionId;
	}

	public void setSafeResourceCollectionId(Long safeResourceCollectionId) {
		this.safeResourceCollectionId = safeResourceCollectionId;
	}

	public Byte getOrganizationStatus() {
		return organizationStatus;
	}

	public void setOrganizationStatus(Byte organizationStatus) {
		this.organizationStatus = organizationStatus;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	@SuppressWarnings("rawtypes")
	public List<TreeNode> getChildren() {
		if(children==null){
			children=new ArrayList<TreeNode>();
		}
		return children;
	}
	/**
	 * @param children the children to set
	 */
	@SuppressWarnings("rawtypes")
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getRoot() {
		return root;
	}

	public void setRoot(Long root) {
		this.root = root;
	}

	/*public static OrganizationNode toNode(Organization value){
		OrganizationNode node = new OrganizationNode();
		node.setId(value.getId());
		node.setName(value.getName());
		node.setOrganizationLevel(value.getOrganizationLevel());
		if(value.getParentId()!=null){
			node.setParentId(value.getParentId());
		}
		if(value.getChildCount()==null || value.getChildCount()<=0){
			node.setState("open");
		}
		node.setAliases(value.getAliases());
		node.setAdminUserId(value.getAdminUserId());
		node.setAdminUserName(value.getAdminUserName());
		node.setLeader(value.getLeader());
		node.setRemark(value.getRemark());
		node.setSafeResourceCollectionId(value.getSafeResourceCollectionId());
		node.setOrganizationStatus(value.getOrganizationStatus());
		return node;
	}*/

	public static OrganizationNode wrapBean(Organization doMain) {
		if(ObjUtil.isEmpty(doMain)){
			return null;
		}
		OrganizationNode node = new OrganizationNode(doMain.getId());
		node.setParentId(doMain.getParentId());
		node.setName(doMain.getName());
		node.setAliases(doMain.getAliases());
		node.setAdminUserId(doMain.getAdminUserId());
		node.setAdminRoleId(doMain.getAdminRoleId());
		node.setOrganizationLevel(doMain.getOrganizationLevel());
		if(doMain.getParentId()!=null){
			node.setParentId(doMain.getParentId());
		}
		if(doMain.getChildCount()==null || doMain.getChildCount()<=0){
			node.setState("open");
		}
		node.setSafeResourceCollectionId(doMain.getSafeResourceCollectionId());
		node.setOrganizationLevel(doMain.getOrganizationLevel());
		node.setLeader(doMain.getLeader());
		node.setOrganizationStatus(doMain.getOrganizationStatus());
		node.setRemark(doMain.getRemark());
		return node;
	}
}
