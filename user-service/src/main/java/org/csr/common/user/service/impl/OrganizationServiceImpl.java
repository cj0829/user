package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.constant.OrganizationStatus;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserRole;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.facade.InitDataOrganization;
import org.csr.common.user.facade.InitSafeResourceOrganization;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.RoleService;
import org.csr.common.user.service.SafeResourceCollectionService;
import org.csr.common.user.service.UserRoleService;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.common.user.service.UserService;
import org.csr.core.Param;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.dao.OrganizationDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;

/**
 * ClassName:OrganizationServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class OrganizationServiceImpl extends
		SimpleBasisService<Organization, Long> implements
		OrganizationService {
	@Resource
	private UserService userService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RoleService roleService;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private UserSafeResourceCollectionService userSafeResourceCollectionService;
	@Resource
	private SafeResourceCollectionService safeResourceCollectionService;

	private List<InitDataOrganization> initDataOrganizations;
	
	//注册初始化save调用
	private final static Map<String,InitSafeResourceOrganization> initSafeResourceOrganizationMap = new HashMap<String,InitSafeResourceOrganization>();
	/**
	 * registrationInitSafeResourceOrganization: 注册服务，在saveOrg时调用，初始化对应的数据安全资源，可以实现此接口  <br/>
	 * @author liurui
	 * @param initSafeResourceOrganization
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean registrationInitSafeResourceOrganization(InitSafeResourceOrganization initSafeResourceOrganization){
		try {
			initSafeResourceOrganizationMap.put(initSafeResourceOrganization.toString(), initSafeResourceOrganization);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	//注册初始化save调用
		private final static Map<String,InitDataOrganization> initDataOrganizationService = new HashMap<String,InitDataOrganization>();
		/**
		 * registrationInitDataOrganizationService: 注册服务，在saveOrg时调用，如果需要在初始化域时，初始化对应的数据，可以实现此接口 <br/>
		 * @author liurui
		 * @param initDataOrganization
		 * @return
		 * @since JDK 1.7
		 */
		public static boolean registrationInitDataOrganizationService(InitDataOrganization initDataOrganization){
			try {
				initDataOrganizationService.put(initDataOrganization.toString(), initDataOrganization);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
	
	@Override
	public BaseDao<Organization, Long> getDao() {
		return organizationDao;
	}
	
	@Override
	public PagedInfo<Organization> findAllPage(Page page){
		return organizationDao.findAllPage(page);
	}
	
	@Override
	public List<Organization> findListAll(Param... params) {
		return organizationDao.findListAll(params);
	}
	@Override
	public List<Organization> findAdminUserByUserId(Long id) {
		if(ObjUtil.isEmpty(id)){
			Exceptions.service("", "用户不能为空");
		}
		Param and = new AndEqParam("adminUserId", id);
		return organizationDao.findByParam(and);
	}
	
	@Override
	public PagedInfo<Organization> findOneLevelPage(Page page) {
		return organizationDao.findOneLevelDataPage(page);
	}

	
	/**
	 * 添加/更新数据字典信息
	 * 先判断是更新还是添加
	 * 然后判断数据名称是否已经存在，若存在则返回提示信息
	 */
	@Override
	public void save(Long parentId,Long adminUserId,Organization orga) {
		
//		if(checkOrganizationName(parentId,orga.getName())){
//			Exceptions.service("orgaNameIsExist", PropertiesUtil.getExceptionMsg("orgaNameIsExist"));
//		}
//		int yes = YesorNo.NO.intValue();
//		int no = YesorNo.YES.intValue();
//		if(ObjUtil.isEmpty(orga.getOrganizationStatus()) ||orga.getOrganizationStatus().intValue()<no|| orga.getOrganizationStatus().intValue()>yes){
//			Exceptions.service("", "安全域的状态，不是有效状态");
//		}
//		
//		if(parentId!=null){
//			Organization parent=findById(parentId);
//			if(parent!=null){
//				Integer level=parent.getOrganizationLevel()==null?1:parent.getOrganizationLevel();
//				orga.setOrganizationLevel(level+1);
//				orga.setParentId(parentId);
//			}else{
//				orga.setOrganizationLevel(1);
//			}
//		}else{
//			orga.setOrganizationLevel(1);
//		}
//		if(ObjUtil.isNotEmpty(adminUserId)){
//			User user = userService.findById(adminUserId);
//			orga.setAdminUserId(user.getId());
//			orga.setAdminUserName(user.getLoginName());
//		}
//		organizationDao.save(orga);
		if(checkOrganizationName(parentId,orga.getName())){
			Exceptions.service("orgaNameIsExist", PropertiesUtil.getExceptionMsg("orgaNameIsExist"));
		}
		if(parentId!=null){
			Organization parent=findById(parentId);
			if(parent!=null){
				Integer level=parent.getOrganizationLevel()==null?1:parent.getOrganizationLevel();
				orga.setOrganizationLevel(level+1);
				orga.setParentId(parentId);
			}else{
				orga.setOrganizationLevel(1);
			}
			//TODO : 2015-12-11，root之间不区分上下级
			
//			orga.setRoot(parent.getRoot());
		}else{
			orga.setOrganizationLevel(1);
		}
		if(ObjUtil.isNotEmpty(adminUserId)){
			User user = userService.findById(adminUserId);
			orga.setAdminUserId(user.getId());
			orga.setAdminUserName(user.getLoginName());
		}
		//-->创建系统资源。
		Long srcId=safeResourceCollectionService.createSystem(orga.getName());
		//-->域管理员的资源
		if(ObjUtil.isNotEmpty(adminUserId)){
			UserSafeResourceCollection usrc=new UserSafeResourceCollection();
			usrc.setSafeResourceCollection(new SafeResourceCollection(srcId));
			usrc.setUser(userService.findById(adminUserId));
			userSafeResourceCollectionService.save(usrc);
		}
		//<--
		orga.setSafeResourceCollectionId(srcId);
		//TODO : 2015-12-11 root就更改为id
//		orga.setRoot(orga.getId());
		organizationDao.save(orga);
		
		if(ObjUtil.isNotEmpty(initDataOrganizationService)){
			for (InitDataOrganization initDataOrganization : initDataOrganizationService.values()) {
				initDataOrganization.initDataOrganization(orga);
			}
		}
		if(ObjUtil.isNotEmpty(initSafeResourceOrganizationMap)){
			for (InitSafeResourceOrganization initSafeResourceOrganization : initSafeResourceOrganizationMap.values()) {
				initSafeResourceOrganization.initSafeResource(orga,srcId);
			}
		}
		
//		Class<SafeResourceType> c=SafeResourceType.class;
//		Field[] fields=c.getFields();
//		for(int i=0;i<fields.length;i++){
//			try {
//				safeResourceService.create(orga.getId(), new SafeResourceCollection(srcId), (Integer)fields[i].get(c));
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	public void update(Long adminUserId,Organization orga){
		
		if(findHasUpdateOrganizationName(orga.getParentId(),orga.getId(),orga.getName())){
			Exceptions.service("DictTypeHasExist", "");
		}
		if(Organization.global.equals(orga.getId())){
			Exceptions.service("", "全局域不能够修改");
		}
		
		
		if(checkOrganizationAliases(orga.getId(), orga.getAliases())){
//			TODO 2016-1-12暂时不验证
//			Exceptions.service("DictTypeHasExist", "别名已存在");
		}
		Organization oldOrga = organizationDao.findById(orga.getId());
		
		//--> 如果域已经激活,需要修改更改用户的安全角色
		if(YesorNo.YES.equals(oldOrga.getOrganizationStatus())){
			if(ObjUtil.isNotEmpty(adminUserId)){
				//需要判断一下，当前管理员是否已经产生变化，如果产生变化，需要先删除当前域的管理员的角色。
				if(!adminUserId.equals(oldOrga.getAdminUserId())){
					//--> 删除用户原有的 安全角色，及安全资源
					deleteUserRoleByOldOrga(oldOrga);
					
					User user=userService.findById(adminUserId);
					//给用户赋予管理员角色
					if(ObjUtil.isNotEmpty(oldOrga.getAdminRoleId())){
						UserRole userRole=new UserRole();
						userRole.setRole(new Role(oldOrga.getAdminRoleId()));
						userRole.setUser(user);
						userRoleService.save(userRole);
					}
					//-->给用户赋予安全资源
					Long collectionId=oldOrga.getSafeResourceCollectionId();
					if(ObjUtil.isNotEmpty(collectionId)){
						SafeResourceCollection src=safeResourceCollectionService.findById(collectionId);
						if(ObjUtil.isNotEmpty(src)){
							UserSafeResourceCollection oldUsrc=userSafeResourceCollectionService.findByUserIdAndCollectionId(adminUserId,src.getId());
							if(ObjUtil.isEmpty(oldUsrc)){
								UserSafeResourceCollection usrc=new UserSafeResourceCollection();
								usrc.setSafeResourceCollection(src);
								usrc.setUser(user);
								userSafeResourceCollectionService.save(usrc);
							}
						}
					}
					//<--
					oldOrga.setAdminUserId(user.getId());
					oldOrga.setAdminUserName(user.getLoginName());
				}
			}else{
				if(ObjUtil.isNotEmpty(oldOrga.getAdminUserId())){
					deleteUserRoleByOldOrga(oldOrga);
				}
				
				oldOrga.setAdminUserId(null);
			}
		
			if(ObjUtil.isEmpty(oldOrga.getName()) || !oldOrga.getName().equals(orga.getName())){
				oldOrga.setName(orga.getName());
				if(ObjUtil.isNotEmpty(initDataOrganizations)){
					for (InitDataOrganization initDataOrganization : initDataOrganizations) {
						initDataOrganization.updateDataOrganization(orga);
					}
				}
				//修改角色名称
				roleService.updateSystemNameByOrg(orga);
				//修改角色名称
				safeResourceCollectionService.updateSystemNameByOrg(orga);
			}
		}
		//--> end 激活完毕
		oldOrga.setParentId(orga.getParentId());
		oldOrga.setAdminUserId(adminUserId);
		oldOrga.setAliases(orga.getAliases());
		oldOrga.setLeader(orga.getLeader());
		oldOrga.setRemark(orga.getRemark());
		
		organizationDao.update(oldOrga);
	}
	
	@Override
	public void updateName(Long organizationId, String name) {
		Organization org=organizationDao.findById(organizationId);
		org.setName(name);
		organizationDao.update(org);
	}

	@Override
	public void updateFreeze(Long organizationId) {
		Organization org=organizationDao.findById(organizationId);
		org.setOrganizationStatus(OrganizationStatus.FREEZE);
		organizationDao.update(org);
	}

	@Override
	public void updateActivating(Long organizationId) {
		Organization org=organizationDao.findById(organizationId);
		org.setOrganizationStatus(OrganizationStatus.ACTIVATING);
		
		Long adminUserId = org.getAdminUserId();
		
		//-->创建系统资源。
		Long safeResourceCollectionId=safeResourceCollectionService.createSystem(org.getName());
		org.setSafeResourceCollectionId(safeResourceCollectionId);
		//-->设置域管理员的资源
		if(ObjUtil.isNotEmpty(adminUserId)){
			User adminUser = userService.findById(adminUserId);
			UserSafeResourceCollection usrc=new UserSafeResourceCollection();
			usrc.setSafeResourceCollection(new SafeResourceCollection(safeResourceCollectionId));
			usrc.setUser(adminUser);
			userSafeResourceCollectionService.save(usrc);
		}
		//--> 激活
		if(ObjUtil.isNotEmpty(initDataOrganizations)){
			for (InitDataOrganization initDataOrganization : initDataOrganizations) {
				initDataOrganization.activatingOrganization(org,safeResourceCollectionId);
			}
		}
		organizationDao.update(org);
	}
	
	/**
	 * 删除域
	 */
	@Override
	public void delete(Long id) {
		List<Long> childs=organizationDao.findChildrenIds(id);
		if(ObjUtil.isNotEmpty(childs)){
			Exceptions.service("", "域还有子域存在，请先删除子域");
		}
		
		//2017-05-14 22:23：当域的状态为未激活状态，可以删除，否则不能够删除
		Organization organization = organizationDao.findById(id);
		if(YesorNo.YES.equals(organization.getOrganizationStatus())){
			Exceptions.service("", "域已经被激活，不能删除");
		}
		
		organizationDao.delete(organization);
	}
	
	
	@Override
	public Long findCountUserByRoleIds(Long[] roleIds) {
		List<Long> list = new ArrayList<Long>();
		for (int i = 0; i < roleIds.length; i++) {
			list.add(roleIds[i]);
		}
		return organizationDao.countParam(new AndInParam("adminRoleId",list));
	}


	@Override
	public Organization findbyAdminUserIdAndRoleId(Long adminUserId, Long adminRoleId) {
		if(ObjUtil.isEmpty(adminUserId)){
			Exceptions.service("","域管理员不能为空！");
		}
		if(ObjUtil.isEmpty(adminRoleId)){
			Exceptions.service("","域管角色不能为空！");
		}
		List<Organization> list=organizationDao.findByParam(new AndEqParam("adminUserId",adminUserId),new AndEqParam("adminRoleId", adminRoleId));
		if(ObjUtil.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Organization existParam(Param param) {
		return organizationDao.existParam(param);
	}
	
	public boolean checkOrganizationAliases(Long id, String aliases) {
		Organization org = organizationDao.existParam(new AndEqParam("aliases",aliases));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(org) || org.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(org)) {
		    return false;
		}
		return true;
	}
	
	@Override
	public boolean checkOrganizationName(Long parentId,String name) {
//		Organization num=checkOrganization(parentId,name);
//		if(ObjUtil.isEmpty(num)){
//			return false;
//		}else{
//			return true;
//		}
		return false;
	}
	
//	private Organization checkOrganization(Long parentId,String name) {
//		Param and = new AndEqParam("name", name);
//		Param parent=new AndIsValue("parentId",parentId);
//		return organizationDao.existParam(and,parent);
//	}
	@Override
	public boolean findHasUpdateOrganizationName(Long parentId,Long id,String name) {
//		Organization num=checkOrganization(parentId,name);
//		if(ObjUtil.isEmpty(num) || num.getId().equals(id)){
//			return false;
//		}else{
//			return true;
//		}
		return false;
	}
	/**
	 * 删除原来的管理员的域角色 , 删除原来的安全资源
	 * deleteUserRoleByOldOrga: 描述方法的作用 <br/>
	 * @author liurui
	 * @param oldOrga
	 * @since JDK 1.7
	 */
	private void deleteUserRoleByOldOrga(Organization oldOrga){
		Long userId=oldOrga.getAdminUserId();
		//删除原来的管理员的域角色
		UserRole oldUserRole=userRoleService.checkUserRoleByroleId(userId, oldOrga.getAdminRoleId());
		if(ObjUtil.isNotEmpty(oldUserRole)){
			userRoleService.delete(oldUserRole);
		}
		//-->删除用户的安全域
		userSafeResourceCollectionService.deleteSystemByUserId(userId,oldOrga.getSafeResourceCollectionId());
	}

	@Override
	public List<Organization> findHasChildListAll(Param... params) {
		
		return organizationDao.findHasChildListAll(params);
	}

	public List<InitDataOrganization> getInitDataOrganizations() {
		return initDataOrganizations;
	}

	public void setInitDataOrganizations(
			List<InitDataOrganization> initDataOrganizations) {
		this.initDataOrganizations = initDataOrganizations;
	}
}
