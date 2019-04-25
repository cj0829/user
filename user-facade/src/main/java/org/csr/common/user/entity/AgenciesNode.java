package org.csr.common.user.entity;


import java.util.ArrayList;
import java.util.List;

import org.csr.common.user.constant.AgenciesType;
import org.csr.common.user.domain.Agencies;
import org.csr.core.constant.YesorNo;
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
public class AgenciesNode extends VOBase<Long> implements TreeNode<Long>{

	/**
	 * serialVersionUID:
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -3365266456674798098L;
	private Long id;
	private Long parentId;
	private String parentName;
	private String name;
	private Integer leader;
	private Integer childCount; 
	private String state = "closed";
	private String remark;
	private String city;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;
	/**智慧工项目编号*/
	private String agenciesCode;
	/**地址 1*/
	private String address1;
	/**地址2*/
	private String address2;
	/**地址 3*/
	private String address3;
	/**州/省*/
	private String stateAndProvince;
	
	Long organizationId;
	
	String organizationName;
	
	private String aliases;
	/**域*/

	/**使命声明*/
	private String missionStatement;
	private Integer orginternalType;
	private Long root;
	/**当前节点是否有权限*/
	private Byte popedom = YesorNo.YES;
	/**是否选择当前节点*/
	private boolean checked;
	/**是否选择节点类型*/
	private Integer type;
	
	private Long updateTime;
	
	public AgenciesNode() {}

	public AgenciesNode(long id) {
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getStateAndProvince() {
		return stateAndProvince;
	}
	public void setStateAndProvince(String stateAndProvince) {
		this.stateAndProvince = stateAndProvince;
	}
	
	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}


	public String getMissionStatement() {
		return missionStatement;
	}
	public void setMissionStatement(String missionStatement) {
		this.missionStatement = missionStatement;
	}

	public Integer getOrginternalType() {
		return orginternalType;
	}
	public void setOrginternalType(Integer orginternalType) {
		this.orginternalType = orginternalType;
	}

	public Long getRoot() {
		return root;
	}
	public void setRoot(Long root) {
		this.root = root;
	}
	
	public Byte getPopedom() {
		return popedom;
	}
	public void setPopedom(Byte popedom) {
		this.popedom = popedom;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getAgenciesCode() {
		return agenciesCode;
	}

	public void setAgenciesCode(String agenciesCode) {
		this.agenciesCode = agenciesCode;
	}

	public static AgenciesNode wrapBean(Agencies value) {
		if(ObjUtil.isNotEmpty(value)){
			AgenciesNode node = new AgenciesNode();
			node.setId(value.getId());
			node.setName(value.getName());
			if(value.getParentId()!=null){
				node.setParentId(value.getParentId());
			}
			if(value.getChildCount()==null || value.getChildCount()<=0){
				node.setState("open");
			}
			
			node.setAgenciesCode(value.getCode());
			node.setRemark(value.getRemark());
			node.setChildCount(value.getChildCount());
			node.setCity(value.getCity());
			
			node.setAddress1(value.getAddress1());
			node.setAddress2(value.getAddress2());
			node.setAddress3(value.getAddress3());
			node.setStateAndProvince(value.getStateAndProvince());
			if(ObjUtil.isNotEmpty(value.getOrg())){
				node.setOrganizationId(value.getOrg().getId());
				node.setOrganizationName(value.getOrg().getName());
			}
			
			if(ObjUtil.isNotEmpty(value.getType())){
				node.setType(value.getType());
			}else{
				node.setType(AgenciesType.PARENT);
			}
			node.setUpdateTime(value.getUpdateTime());
			
			return node;
		}
		return null;
	}
	
	public static AgenciesNode toNode(Agencies value){
		AgenciesNode node = new AgenciesNode();
		node.setId(value.getId());
		node.setName(value.getName());
		if(value.getParentId()!=null){
			node.setParentId(value.getParentId());
		}
		if(value.getChildCount()==null || value.getChildCount()<=0){
			node.setState("open");
		}
		node.setRemark(value.getRemark());
		node.setChildCount(value.getChildCount());
		node.setCode(value.getCode());
		node.setCity(value.getCity());
		return node;
	}
	
	public static AgenciesNode toNode(Agencies value,Agencies parent){
		AgenciesNode node = new AgenciesNode();
		node.setId(value.getId());
		node.setName(value.getName());
		if(value.getParentId()!=null){
			node.setParentId(value.getParentId());
		}
		if(ObjUtil.isNotEmpty(parent)){
			node.setParentName(parent.getName());
		}
		if(value.getChildCount()==null || value.getChildCount()<=0){
			node.setState("open");
		}
		node.setRemark(value.getRemark());
		node.setCode(value.getCode());
		node.setChildCount(value.getChildCount());
		return node;
	}
}
