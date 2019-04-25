package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.csr.core.MenuNode;
import org.csr.core.SecurityResource;
import org.csr.core.constant.MenuDisplay;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 菜单
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "u_Menu")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="菜单表",en="pmt_common_Menu")
public class Menu extends RootDomain<Long> implements MenuNode {
	/**
	 * 
	 */
	private static final long serialVersionUID = -588125099095659257L;
	private Long id;
	@AutoSetProperty(message="父节点",required=false)
	private Menu parent;
	private String name;
	private String icon;
	private String defIcon;
	@AutoSetProperty(message="功能点",required=false)
	private FunctionPoint functionPoint;
	
	/**菜单显示方式（(可以根据自己的需求来确定显示方式）*/
	private Byte display=MenuDisplay.YES;
	
	private Integer rank;
	/**
	 * 子总数
	 */
	private int childCount;
	/** 描述 */
	protected String remark;
	/**
	 * 子菜单
	 */
	private List<MenuNode> children = new ArrayList<MenuNode>(0);

	public Menu() {
	}

	public Menu(Long id) {
		this.id = id;
	}

	public Menu(Long id, Menu parent, String name, FunctionPoint functionPoint,int rank) {
		this.id = id;
		this.parent = parent;
		this.name = name;
		this.functionPoint = functionPoint;
		this.rank = rank;
	}
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId")
	@Comment(ch="父菜单",en="parentId")
	public Menu getParent() {
		return this.parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
	
	@Column(name = "display")
	@Comment(ch="菜单是否显示")
	@Override
	public Byte getDisplay() {
		return display;
	}

	public void setDisplay(Byte display) {
		this.display = display;
	}

	@Column(name = "name", length = 64)
	@Comment(ch="菜单名称")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "functionPointId")
	@Comment(ch="入口功能点")
	public FunctionPoint getFunctionPoint() {
		return this.functionPoint;
	}

	public void setFunctionPoint(FunctionPoint functionPoint) {
		this.functionPoint = functionPoint;
	}

	@Column(name = "rank")
	@Comment(ch="菜单显示顺序")
	public Integer getRank() {
		return this.rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	@Column(name = "remark",length=255)
	@Comment(ch="备注")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}
	
	@Override
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Override
	public String getDefIcon() {
		return defIcon;
	}

	public void setDefIcon(String defIcon) {
		this.defIcon = defIcon;
	}

	/**
	 * 获取子
	 */
	@Transient
	@Override
	public  List<MenuNode> getChildren() {
		return this.children;
	}
	
	@Transient
	public Integer getChildCount() {
		return childCount;
	}
	
	@Transient
	@Override
	public SecurityResource getSecurityResource() {
		return functionPoint;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}


}
