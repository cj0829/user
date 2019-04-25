package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csr.common.flow.domain.FormDomain;
import org.csr.common.storage.domain.Datastream;
import org.csr.common.user.safeResource.SafeData;
import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 如果用户分类型,而且各类用户有自己的特性字段,则另建用户扩展表
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "c_User")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="c_User",ch="系统用户")
public class User extends  FormDomain implements SafeData<Long>{
	private static final long serialVersionUID = -6669474592738489238L;

	public static final Long SUPER = 1l;

	private Long id;
	/** 昵称 */
	protected String callName;
	/** 登录名 */
	protected String loginName;
	/** 手机号 */
	private String mobile;
	/** 用户姓名 */
	protected String name;
	/** 密码 */
	protected String password;
	/** 重置密码的口令 */
	private String resetPassword;
	/** 性别 */
	protected Byte gender;
	/**生日*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date birthday;
	/** 电子邮件 */
	protected String email;
	/**大头像*/
	private Datastream head;
	/**中头像*/
	private Datastream middleHead;
	/**小头像*/
	private Datastream avatar;
	/** 用户角色类型 */
	private Byte userRoleType;
	/** 状态 */
	protected Integer userStatus;
	
	/** 管理人 */
	@AutoSetProperty(message = "管理人", required = false)
	private User managerUser;
	/** 机构组织 */
	@AutoSetProperty(message = "机构组织", required = false)
	private Agencies agencies;
	/** 主域 */
	private Long primaryOrgId;
	
	/** 默认登录首页 */
	private Long dufHome;
	/** 样式名称 */
	private String skinName = "dark";
	/**用户导入文件Id*/
	private Long fileId;
	
	public User() {}
	
	public User(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Length(min=0,max=32)
	@Column(name = "callName", length = 32)
	@Comment(ch = "昵称", en = "callName", len = 32)
	public String getCallName() {
		return callName;
	}

	public void setCallName(String callName) {
		this.callName = callName;
	}

	@Length(min=5,max=40)
	@Column(name = "loginName", length = 40, unique = true)
	@Comment(ch = "用户名", en = "loginName", len = 40, search = true)
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Length(min=1,max=40)
	@Column(name = "name", length = 40)
	@Comment(ch = "姓名", en = "name", vtype = "required:true", len = 40, search = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "mobile", length = 11)
	@Comment(ch = "手机号", en = "mobile", len = 11)
	@Length(min = 0, max = 11)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "password", length = 64)
	@Comment(ch = "密码", en = "password", len = 64, vtype = "required:true")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 也可以直接存入session中，这样子处理起来就更加简单 getResetPassword: 重置密码的口令，在修改密码的时候需要清空掉 <br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	@Column(name = "resetPassword", length = 64)
	@Comment(ch = "重置密码的口令", en = "resetPassword")
	public String getResetPassword() {
		return resetPassword;
	}
	public void setResetPassword(String resetPassword) {
		this.resetPassword = resetPassword;
	}
	
	@Column(name = "gender")
	@Comment(ch = "用户性别", en = "gender", vtype = "required:true")
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}

	@Length(min=0,max=64)
	@Column(name = "email", length = 64)
	@Comment(ch = "电子邮件", en = "email", len = 64)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name="birthday")
	@Comment(ch="生日",en="birthday")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "avatarId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "小头像", en = "avatarId")
	public Datastream getAvatar() {
		return avatar;
	}
	public void setAvatar(Datastream avatar) {
		this.avatar = avatar;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "middleHead", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "中头像", en = "middleHead")
	public Datastream getMiddleHead() {
		return middleHead;
	}
	public void setMiddleHead(Datastream middleHead) {
		this.middleHead = middleHead;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "headId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "大头像", en = "headId")
	public Datastream getHead() {
		return head;
	}
	public void setHead(Datastream head) {
		this.head = head;
	}
	@Column(name = "userRoleType")
	public Byte getUserRoleType() {
		return userRoleType;
	}
	public void setUserRoleType(Byte userRoleType) {
		this.userRoleType = userRoleType;
	}
	
	@Column(name = "userStatus")
	@Comment(ch = "用户状态", en = "userStatus", vtype = "required:true")
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}


	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "managerUserId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "管理人", en = "managerUserId")
	public User getManagerUser() {
		return managerUser;
	}
	public void setManagerUser(User managerUser) {
		this.managerUser = managerUser;
	}
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "agenciesId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "组织", en = "agenciesId")
	public Agencies getAgencies() {
		return agencies;
	}
	public void setAgencies(Agencies agencies) {
		this.agencies = agencies;
	}

	@Column(name = "primaryOrgId")
	@Comment(ch = "主域", en = "primaryOrgId")
	public Long getPrimaryOrgId() {
		return primaryOrgId;
	}
	public void setPrimaryOrgId(Long primaryOrgId) {
		this.primaryOrgId = primaryOrgId;
	}
	

	// =================系统一些常用配置=====================
	@Column(name = "skinName", length = 32)
	@Comment(ch = "用户皮肤", en = "skinName")
	public String getSkinName() {
		return skinName;
	}
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}
	@Column(name = "dufHome")
	@Comment(ch = "默认显示菜单", en = "dufHome")
	public Long getDufHome() {
		return dufHome;
	}
	public void setDufHome(Long dufHome) {
		this.dufHome = dufHome;
	}
	@Column(name = "fileId")
	@Comment(ch="用户导入文件Id",en="fileId")
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	
	// =================以下的不作为数据字段，使用==============
	// 用来存储级别
	private int level = 0;

	public void setLevel(int level) {
		this.level = level;
	}

	@Transient
	public int getLevel() {
		return this.level;
	}

	private Integer childCount;

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}
	@Transient
	public Integer getChildCount() {
		return this.childCount;
	}

	/** 域名 */
	private String organizationName;

	@Transient
	public String getOrganizationName() {
		return organizationName;
	}

	@Transient
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
}
