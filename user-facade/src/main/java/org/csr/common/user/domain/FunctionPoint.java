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

import org.csr.common.user.constant.EnableBan;
import org.csr.core.Comment;
import org.csr.core.SecurityResource;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.domain.RootDomain;
import org.csr.core.util.ObjUtil;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 功能点树
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name = "u_FunctionPoint")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="功能点树",en="pmt_common_FunctionPoint")
public class FunctionPoint extends RootDomain<Long> implements SecurityResource {
	/**
	 * 
	 */
	private static final long serialVersionUID = -588125099095659257L;
	private Long id;
	private FunctionPoint functionPoint;
	private String name;
	private String code;
	private String icon;
	private String dufIcon;
	private String forwardUrl;
	private String urlRule;
	private Byte enableBan=EnableBan.Disable;
	private Byte helpStatus;
	
	/**
	 * 功能点类型: 1.库、2.功能点
	 */
	private Byte functionPointType;
	/**
	 * 是否分配给匿名角色：1、是，2.、否
	 */
	private Byte isAnonymous;
	private Byte browseLogLevel;
	private Byte operationLogLevel;
	/**
	 * 授权方式:1、不允许授权，2、默认已授权，3、需要授权
	 */
	private Byte authenticationMode;
	
	private Integer childCount;
	/**
	 * 
	 */
	private List<FunctionPoint> children = new ArrayList<FunctionPoint>(0);

	/** 描述 */
	protected String remark;
	
	public FunctionPoint() {
	}

	public FunctionPoint(Long id) {
		this.id = id;
	}

	public FunctionPoint(Long id, FunctionPoint functionPoint, String name,
			String code, String forwardUrl,String urlRule) {
		this.id = id;
		this.functionPoint = functionPoint;
		this.name = name;
		this.code = code;
		this.forwardUrl = forwardUrl;
		this.urlRule = urlRule;
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	@Comment(ch="父功能点ID")
	public FunctionPoint getFunctionPoint() {
		return this.functionPoint;
	}

	public void setFunctionPoint(FunctionPoint functionPoint) {
		this.functionPoint = functionPoint;
	}

	@Column(name = "name", length = 64)
	@Comment(ch="功能点名称")
	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "code", length = 16)
	@Comment(ch="功能点编码")
	@Override
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "icon", length = 16)
	@Comment(ch="菜单图片",en="icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	@Column(name = "dufIcon", length = 16)
	@Comment(ch="默认菜单图片",en="dufIcon")
	public String getDufIcon() {
		return dufIcon;
	}

	public void setDufIcon(String dufIcon) {
		this.dufIcon = dufIcon;
	}
	@Column(name = "forwardUrl", length = 128)
	@Comment(ch="功能点入口url")
	@Override
	public String getForwardUrl() {
		return forwardUrl;
	}
	
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	
	@Column(name = "urlRule", length = 1536)
	@Comment(ch="功能点规则url（范围）")
	@Override
	public String getUrlRule() {
		return urlRule;
	}

	public void setUrlRule(String urlRule) {
		this.urlRule = urlRule;
	}
	
	@Column(name = "helpStatus",length=1)
	@Comment(ch="帮助状态：1.有，2.没有")
	public Byte getHelpStatus() {
		return helpStatus;
	}

	public void setHelpStatus(Byte helpStatus) {
		this.helpStatus = helpStatus;
	}
	
	@Column(name = "functionPointType",length=1)
	@Comment(ch="功能点类型: 1.库、2.功能点")
	public Byte getFunctionPointType() {
		return this.functionPointType;
	}

	public void setFunctionPointType(Byte functionPointType) {
		this.functionPointType = functionPointType;
	}
	
	@Column(name = "isAnonymous",length=1)
	@Comment(ch="是否分配给匿名角色：1、否，2、是")
	public Byte getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Byte isAnonymous) {
		this.isAnonymous = isAnonymous;
	}
	@Column(name = "browseLogLevel",length=1)
	@Comment(ch="浏览数据的日志记录方式：1 不记；2记")
	@Override
	public Byte getBrowseLogLevel() {
		return browseLogLevel;
	}

	public void setBrowseLogLevel(Byte browseLogLevel) {
		this.browseLogLevel = browseLogLevel;
	}
	
	@Column(name = "operationLogLevel",length=1)
	@Comment(ch="1 、不记；2 、只记录操作；3 、记录操作和数据")
	@Override
	public Byte getOperationLogLevel() {
		return operationLogLevel;
	}

	public void setOperationLogLevel(Byte operationLogLevel) {
		this.operationLogLevel = operationLogLevel;
	}
	
	@Column(name = "authenticationMode",length=1) 
	@Comment(ch="授权方式:1、不允许授权，2、默认已授权，3、需要授权")
	public Byte getAuthenticationMode() {
		return authenticationMode;
	}

	public void setAuthenticationMode(Byte authenticationMode) {
		this.authenticationMode = authenticationMode;
	}
	
	@Column(name = "enableBan",length=1)
	@Comment(ch="启用:1、禁用:2")
	public Byte getEnableBan() {
		return enableBan;
	}

	public void setEnableBan(Byte enableBan) {
		this.enableBan = enableBan;
	}

	@Column(name = "remark",length=128)
	@Comment(ch="备注")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Transient
	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public void setChildren(ArrayList<FunctionPoint> arrayList) {
		this.children = arrayList;
	}
	

	/**
	 * 获取子
	 */
	@Transient
	@Override
	public  List<? extends SecurityResource> getChildren() {
		return this.children;
	}

	
	@Transient
	public String getPath() {
		StringBuffer path=new StringBuffer(id!=null?id.toString():"");
		FunctionPoint parent=functionPoint;
		while (ObjUtil.isNotEmpty(parent)) {
			path.insert(0, parent.getId()+"/");
			parent=parent.functionPoint;
		}
		return path.toString();
	}
	private Byte have=YesorNo.NO;
	@Transient
	public Byte getHave() {
		return have;
	}

	public void setHave(Byte have) {
		this.have = have;
	}
	
	private String source;
	@Transient
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	@Transient
	public String getSourceEllipsis() {
		if(ObjUtil.isNotBlank(source)){
			if(source.length()>7){
				return source.substring(0, 6)+"...";
			}else{
				return source;
			}
		}
		return "";
	}
	
	public boolean exist=false;
	@Transient
	public boolean getExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
	
	
}
