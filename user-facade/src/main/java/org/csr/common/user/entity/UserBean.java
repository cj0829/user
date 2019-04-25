package org.csr.common.user.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.csr.common.storage.entity.DatastreamBean;
import org.csr.common.storage.supper.FileSystemContext;
import org.csr.common.user.domain.User;
import org.csr.core.userdetails.SecurityUser;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:UserResult.java <br/>
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
public class UserBean extends VOBase<Long> implements SecurityUser {
	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String loginName;
	/**手机号*/
	private String mobile;
	private String password;
	private Integer userStatus;
	private String organizationName;
	private String UserTaskInstance;
	private Long organizationId;
	/**机构组织id*/
	private Long agenciesId;
	/**机构组织Name*/
	private String agenciesName;
	/**电子邮件*/
	private String email;
	/**用户类型*/
	private Byte userRoleType;
	/**管理人*/
	private Long managerUserId;
	
	private String managerUserName;
	
	private Long primaryOrgId;
	
	private Long root;
	
	private Byte gender;
	/** 样式名称 */
	private String skinName = "dark";

	/** 默认登录首页 */
	private Long dufHome;
	/** 语言 */
	private Long languagesId;
	/** 小头像*/
	private DatastreamBean avatar;
	/** 小头像*/
	private String avatarUrl;
	/**中头像*/
	private DatastreamBean middleHead;
	/**中头像*/
	private String middleHeadUrl;
	/**大头像*/
	private DatastreamBean head;
	/**大头像*/
	private String headUrl;
	/**生日*/
	private Date birthday;
	/**自我介绍*/
	private DatastreamBean profile;
	/**积分*/
	private Integer points1;
	/**说说*/
	private String saysome;
	/**是否选择当前节点*/
	private boolean checked;
	
	private Long updateTime;
	
	private String callName;
	
	private String address;
	
	public UserBean() {}
	public UserBean(Long id) {
		this.id=id;
	}
	@Override
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}


	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}


	public Long getAgenciesId() {
		return agenciesId;
	}

	public void setAgenciesId(Long agenciesId) {
		this.agenciesId = agenciesId;
	}

	public String getAgenciesName() {
		return agenciesName;
	}

	public void setAgenciesName(String agenciesName) {
		this.agenciesName = agenciesName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getUserRoleType() {
		return userRoleType;
	}
	public void setUserRoleType(Byte userRoleType) {
		this.userRoleType = userRoleType;
	}
	
	public Long getManagerUserId() {
		return managerUserId;
	}
	public void setManagerUserId(Long managerUserId) {
		this.managerUserId = managerUserId;
	}
	public String getManagerUserName() {
		return managerUserName;
	}
	public void setManagerUserName(String managerUserName) {
		this.managerUserName = managerUserName;
	}
	public Long getDufHome() {
		return dufHome;
	}
	public void setDufHome(Long dufHome) {
		this.dufHome = dufHome;
	}
	public Long getRoot() {
		return root;
	}
	public void setRoot(Long root) {
		this.root = root;
	}
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getPrimaryOrgId() {
		return primaryOrgId;
	}
	public void setPrimaryOrgId(Long primaryOrgId) {
		this.primaryOrgId = primaryOrgId;
	}
	public String getSkinName() {
		return skinName;
	}
	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}
	public Long getLanguagesId() {
		return languagesId;
	}
	public void setLanguagesId(Long languagesId) {
		this.languagesId = languagesId;
	}
	public DatastreamBean getAvatar() {
		return avatar;
	}
	public void setAvatar(DatastreamBean avatar) {
		this.avatar = avatar;
	}
	public DatastreamBean getHead() {
		return head;
	}
	public void setHead(DatastreamBean head) {
		this.head = head;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	
	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public DatastreamBean getProfile() {
		return profile;
	}
	public void setProfile(DatastreamBean profile) {
		this.profile = profile;
	}
	public Integer getPoints1() {
		return points1;
	}
	public void setPoints1(Integer points1) {
		this.points1 = points1;
	}
	public String getSaysome() {
		return saysome;
	}
	public void setSaysome(String saysome) {
		this.saysome = saysome;
	}
	
	public DatastreamBean getMiddleHead() {
		return middleHead;
	}
	public void setMiddleHead(DatastreamBean middleHead) {
		this.middleHead = middleHead;
	}
	public String getMiddleHeadUrl() {
		return middleHeadUrl;
	}
	public void setMiddleHeadUrl(String middleHeadUrl) {
		this.middleHeadUrl = middleHeadUrl;
	}
	public String getBirthdays(){
		if(ObjUtil.isNotEmpty(birthday)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(birthday);
		}
		return "";
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserTaskInstance() {
		return UserTaskInstance;
	}
	public void setUserTaskInstance(String userTaskInstance) {
		UserTaskInstance = userTaskInstance;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public static UserBean wrapBean(User doMain) {
		if( null == doMain){
			return null;
		}
		UserBean bean=new UserBean();
		bean.setId(doMain.getId());
		bean.setLoginName(doMain.getLoginName());
		bean.setGender(doMain.getGender());
		bean.setCallName(doMain.getCallName());
		bean.setName(doMain.getName());
		bean.setMobile(doMain.getMobile());
		bean.setPassword(doMain.getPassword());
		bean.setOrganizationId(doMain.getPrimaryOrgId());
		bean.setOrganizationName(doMain.getOrganizationName());
		bean.setUserRoleType(doMain.getUserRoleType());
		User managerUser = doMain.getManagerUser();
		if(ObjUtil.isNotEmpty(managerUser)){
			bean.setManagerUserId(managerUser.getId());
			bean.setManagerUserName(managerUser.getLoginName());
		}
		bean.setSkinName(doMain.getSkinName());
		bean.setDufHome(doMain.getDufHome());
		
		bean.setPrimaryOrgId(doMain.getPrimaryOrgId());
		bean.setDufHome(doMain.getDufHome());
		
		bean.setAvatarUrl(FileSystemContext.getDefaultUserAvatar(doMain.getAvatar()));
		bean.setAvatar(DatastreamBean.toBean(doMain.getAvatar()));
		
		bean.setHeadUrl(FileSystemContext.getDefaultUserHead(doMain.getHead()));
		bean.setHead(DatastreamBean.toBean(doMain.getHead()));
		
		bean.setMiddleHeadUrl(FileSystemContext.getDefaultUserMiddleHead(doMain.getMiddleHead()));
		bean.setMiddleHead(DatastreamBean.toBean(doMain.getMiddleHead()));
		
		bean.setEmail(doMain.getEmail());
		bean.setBirthday(doMain.getBirthday());
		bean.setUserStatus(doMain.getUserStatus());
		if(ObjUtil.isEmpty(doMain.getUpdateTime())){
			bean.setUpdateTime(0l);
		}else{
			bean.setUpdateTime(doMain.getUpdateTime());
		}
		
		if(ObjUtil.isNotEmpty(doMain.getAgencies())){
			bean.setAgenciesId(doMain.getAgencies().getId());
			bean.setAgenciesName(doMain.getAgencies().getName());
		}
		return bean;
	}
	
	
	public static UserBean wrapBean(User doMain,Long agenciesId) {
		if( null == doMain){
			return null;
		}
		UserBean bean=new UserBean();
		bean.setId(doMain.getId());
		bean.setLoginName(doMain.getLoginName());
		bean.setGender(doMain.getGender());
		bean.setCallName(doMain.getCallName());
		bean.setName(doMain.getName());
		bean.setMobile(doMain.getMobile());
		bean.setPassword(doMain.getPassword());
		bean.setOrganizationId(doMain.getPrimaryOrgId());
		bean.setOrganizationName(doMain.getOrganizationName());
		bean.setUserRoleType(doMain.getUserRoleType());
		User managerUser = doMain.getManagerUser();
		if(ObjUtil.isNotEmpty(managerUser)){
			bean.setManagerUserId(managerUser.getId());
			bean.setManagerUserName(managerUser.getLoginName());
		}
		bean.setSkinName(doMain.getSkinName());
		bean.setDufHome(doMain.getDufHome());
		
		bean.setPrimaryOrgId(doMain.getPrimaryOrgId());
		bean.setDufHome(doMain.getDufHome());
		
		bean.setAvatarUrl(FileSystemContext.getDefaultUserAvatar(doMain.getAvatar()));
		bean.setAvatar(DatastreamBean.toBean(doMain.getAvatar()));
		
		bean.setHeadUrl(FileSystemContext.getDefaultUserHead(doMain.getHead()));
		bean.setHead(DatastreamBean.toBean(doMain.getHead()));
		
		bean.setMiddleHeadUrl(FileSystemContext.getDefaultUserMiddleHead(doMain.getMiddleHead()));
		bean.setMiddleHead(DatastreamBean.toBean(doMain.getMiddleHead()));
		
		bean.setEmail(doMain.getEmail());
		bean.setBirthday(doMain.getBirthday());
		bean.setUserStatus(doMain.getUserStatus());
		if(ObjUtil.isEmpty(doMain.getUpdateTime())){
			bean.setUpdateTime(0l);
		}else{
			bean.setUpdateTime(doMain.getUpdateTime());
		}
		bean.setAgenciesId(agenciesId);
		return bean;
	}
}
