package org.csr.common.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.csr.common.user.domain.Province;
import org.csr.core.constant.YesorNo;
import org.csr.core.tree.TreeNode;
import org.csr.core.web.bean.VOBase;


/**
 * ClassName:ProvinceNode.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class ProvinceNode extends VOBase<Long> implements TreeNode<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long parentId;
	private String code;
	private String name;
	private String remark;
	private Byte hasChild=YesorNo.NO;
	@SuppressWarnings("rawtypes")
	private List<TreeNode> children;
	private Integer rank;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return the hasChild
	 */
	public Byte getHasChild() {
		return hasChild;
	}
	/**
	 * @param hasChild the hasChild to set
	 */
	public void setHasChild(Byte hasChild) {
		this.hasChild = hasChild;
	}
	/**
	 * @return the children
	 */
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
	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		if(children==null){
			return "open";
		}else{
			if(children.size()>0){
				return "closed";
			}else{
				return "open";
			}
		}
	}
	public static ProvinceNode wrapBean(Province doMain) {
		ProvinceNode bean = new ProvinceNode();
		bean.setId(doMain.getId());
		bean.setName(doMain.getName());
		bean.setCode(doMain.getCode());
		bean.setParentId(doMain.getParentId());
		bean.setRemark(doMain.getRemark());
		bean.setRank(doMain.getRank());
		return bean;
	}
}
