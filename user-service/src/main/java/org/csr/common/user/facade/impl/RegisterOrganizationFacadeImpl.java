package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.constant.AuthenticationMode;
import org.csr.common.user.constant.RoleType;
import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserRole;
import org.csr.common.user.entity.RegisterBean;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.RegisterOrganizationFacade;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.RoleFunctionPointService;
import org.csr.common.user.service.RoleService;
import org.csr.common.user.service.UserRoleService;
import org.csr.common.user.service.UserService;
import org.csr.core.Param;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.business.domain.Parameters;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.business.service.OrganizationParameterService;
import org.csr.core.persistence.business.service.ParameterService;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.Md5PwdEncoder;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:RegisterOrganizationServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("registerOrganizationFacade")
public class RegisterOrganizationFacadeImpl implements RegisterOrganizationFacade{
	
	@Resource
	OrganizationService organizationService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserService userService; 
	@Resource
	private RoleService roleService;
	@Resource
	private RoleFunctionPointService roleFunctionPointService;
	@Resource
	private FunctionpointService functionpointService;
	@Resource
	private OrganizationParameterService organizationParameterService;
	@Resource
	private ParameterService parameterService;
	
	public RegisterOrganizationFacadeImpl() {
	}
	
	@Override
	public Organization saveAuthorize(Long orgId, Long[] funs) {
		if(ObjUtil.isEmpty(orgId)){
			Exceptions.service("", "必须选择要授权的域");
		}
		if(ObjUtil.isEmpty(funs)){
			Exceptions.service("", "必须选择授权功能点");
		}
		Organization orga=organizationService.findById(orgId);
		if(ObjUtil.isEmpty(orgId)){
			Exceptions.service("", "必须选择的域不存在");
		}
		Long roleId=orga.getAdminRoleId();
		Role role=null;
		if(ObjUtil.isNotEmpty(roleId)){
			role=roleService.findById(roleId);
		}
		//判断是否存在角色
		if(ObjUtil.isEmpty(role)){
			role=new Role();
			role.setName(orga.getName()+"管理员");
			role.setOrgId(orga.getId());
			role.setRemark(orga.getName()+"管理员");
			roleService.saveAdmin(role);
			orga.setAdminRoleId(role.getId());
		}
		//给用户赋予管理员角色
		if(ObjUtil.isNotEmpty(orga.getAdminUserId())){
			UserRole userRole=new UserRole();
			userRole.setRole(role);
			userRole.setUser(new User(orga.getAdminUserId()));
			userRoleService.save(userRole);
		}
		//给指定角色，编辑功能点 
		roleFunctionPointService.saveByFunctionPointType(role.getId(), funs);
		return orga;
	}
	/**
	 * saveAuthorize: 保存或编辑授权机构，赋权库和功能点 <br/>
	 * 只按照，功能点，自己的id赋权。
	 * @author caijin
	 * @param orgId
	 * @param funs
	 * @return
	 * @since JDK 1.7
	 */
	public Organization saveMyselfAuthorize(Long orgId,Long[] funs){
		if(ObjUtil.isEmpty(orgId)){
			Exceptions.service("", "必须选择要授权的域");
		}
		if(Organization.global.equals(orgId)){
			Exceptions.service("", "全局域不能够修改");
		}
//		if(ObjUtil.isEmpty(funs)){
//			Exceptions.service("", "必须选择授权功能点");
//		}
		Organization orga=organizationService.findById(orgId);
		if(ObjUtil.isEmpty(orgId)){
			Exceptions.service("", "必须选择的域不存在");
		}
		Long roleId=orga.getAdminRoleId();
		Role role=null;
		if(ObjUtil.isNotEmpty(roleId)){
			role=roleService.findById(roleId);
		}
		//判断是否存在角色
		if(ObjUtil.isEmpty(role)){
			role=new Role();
			role.setName(orga.getName()+"管理员");
			role.setOrgId(orga.getId());
			role.setRemark(orga.getName()+"管理员");
			roleService.saveAdmin(role);
			orga.setAdminRoleId(role.getId());
		}
		//给用户赋予管理员角色
		if(ObjUtil.isNotEmpty(orga.getAdminUserId())){
			UserRole oldUserRole=userRoleService.checkUserRoleByroleId(orga.getAdminUserId(), role.getId());
			if(ObjUtil.isEmpty(oldUserRole)){
				UserRole userRole=new UserRole();
				userRole.setRole(role);
				userRole.setUser(new User(orga.getAdminUserId()));
				userRoleService.save(userRole);
			}
		}
		//给指定角色，编辑功能点 
		roleFunctionPointService.save(role.getId(), funs);
		return orga;
	}
	
	
	/**
	 * 注册保存机构。创建机构，创建系统管理员用户，<br>
	 * 创建管理员角色，分配功能点。
	 * @param register
	 * @author caijin
	 * @return
	 */
	@Override
	public UserBean saveOrganization(RegisterBean register,Byte createOrChoose) {
		//创建机构
		Organization orga=new Organization();
		orga.setName(register.getOrganame());
		orga.setOrganizationStatus(YesorNo.YES);
		orga.setParentId(Organization.global);
		//设置机构Root的机构id
		organizationService.save(null,null, orga);
		//修改机构rootId
//		orga.setRoot(orga.getId());
//		//创建角色
		Role role=new Role();
		roleService.checkAddRoleName(orga.getName()+"管理员");
		role.setName(orga.getName()+"管理员");
		
		role.setOrgId(orga.getId());
		role.setRoleType(RoleType.SYSTEM);
		role.setRemark(orga.getName()+"管理员");
		roleService.saveAdmin(role);
//		//创建用户
		User adminUser = null;
		if(!YesorNo.YES.equals(createOrChoose)&&ObjUtil.isNotEmpty(register.getUserId())){
			adminUser = userService.findById(register.getUserId());
			adminUser.setUserRoleType(UserRoleType.ADMIN);
			adminUser.setPrimaryOrgId(orga.getId());
		}else {
			adminUser = new User();
			adminUser.setUserRoleType(UserRoleType.ADMIN);
			adminUser.setLoginName(register.getLoginName());
			adminUser.setPassword(register.getPassword());
			adminUser.setPrimaryOrgId(orga.getId());
			adminUser.setPrimaryOrgId(orga.getId());
			adminUser.setEmail(register.getEmail());
			adminUser.setUserStatus(UserStatus.NORMAL);
			adminUser.setName(orga.getName()+"管理员");
			userService.saveSimple(adminUser);
		}
		//设置机构管理员
		orga.setAdminUserId(adminUser.getId());
		//设计机构角色
		orga.setAdminRoleId(role.getId());
		//关联角色
		roleFunctionPointService.save(role.getId(),register.getFunctionPointIds());
		//修改机构参数
		List<Parameters> parameters=register.getParameterValues();
		for(int i=0;i<parameters.size();i++){
			organizationParameterService.update(orga.getId(),parameters.get(i).getId(), parameters.get(i).getParameterValue());
		}
		//发送邮件
//		mail.sendMail("机构注册提醒", "机构注册成功，用户为："+register.getLoginName()+" 密码："+register.getPassword(), register.getEmail());
		return new UserFacadeImpl().wrapBean(adminUser);
	}
	
	
	/**
	 * 删除注册机构，如果，机构，还没有新增用户，及子结构，可以被删除<br>
	 * 并删除机构下面相关所有信息<br>
	 * 包含有，管理员，角色，角色关联的功能点，及机构对应的参数配置。
	 * @param organizationId
	 * @author caijin
	 */
	@Override
	public void delete(Long organizationId) {
		if(Organization.global.equals(organizationId)){
			Exceptions.service("", "global域不能删除");
		}
		Organization orga=organizationService.findById(organizationId);
		if(UserSessionContext.getUserSession().getPrimaryOrgId().equals(organizationId)){
			Exceptions.service("", "当前"+orga.getName()+"域不能删除");
		}
		Long roleId=orga.getAdminRoleId();
		Role role=null;
		if(ObjUtil.isNotEmpty(roleId)){
			role=roleService.findById(roleId);
			User admin=userService.findById(orga.getAdminUserId());
			
			if(ObjUtil.isNotEmpty(roleId)){
				if(ObjUtil.isNotEmpty(admin)){
					//如果用户是所属的删除的当前域，需要更改用户的域。更为global域
					if(orga.getId().equals(admin.getPrimaryOrgId())){
						//TODO 在删除域是，更改用户是否设置为Organization.global 或者是设置null ，设置null，的用户，谁都能能够查询到，方便再次设权限？
						admin.setPrimaryOrgId(Organization.global);
					}
					//如果用户,都在机构下了。则更改用户类型为自定用户。
					List<Organization> orgs=organizationService.findAdminUserByUserId(admin.getId());
					if(ObjUtil.isEmpty(orgs) || orgs.size()<=1){
						admin.setUserRoleType(UserRoleType.GENERAL);
					}
					userRoleService.deleteByUserIdRoleId(admin.getId(),role.getId());
				}
				roleFunctionPointService.deleteByRoleId(role.getId());
				roleService.deleteSimple(role.getId());
			}
		}
		//删除全部的机构canshu
		organizationParameterService.deleteForOrgId(organizationId);
		//删除机构
		organizationService.delete(organizationId);
	}

	
	/**
	 * 查询当前机构下的全部注册信息，包含机构信息<br>
	 * 角色信息，用户信息，功能点信息，参数信息
	 * @param organizationId
	 * @return
	 * @author caijin
	 */
	@Override
	public RegisterBean findRegisterResult(Long organizationId){
		RegisterBean result=new RegisterBean();
		User user=userService.findByOrganizationAdmin(organizationId);
		//判断用户是否为null
		if(ObjUtil.isEmpty(user)){
			Exceptions.service("DictTypeHasExist", "DictTypeHasExist");
		}
		//判断机构是否为null
		Organization orga=organizationService.findById(organizationId);
		if(ObjUtil.isEmpty(orga)){
			Exceptions.service("DictTypeHasExist", "DictTypeHasExist");
		}
		//判断角色是否为null
		Long roleId=orga.getAdminRoleId();
		Role role=null;
		if(ObjUtil.isNotEmpty(roleId)){
			role=roleService.findById(roleId);
			if(ObjUtil.isEmpty(role)){
				Exceptions.service("DictTypeHasExist", "DictTypeHasExist");
			}
		}
		result.setUserId(user.getId());
		result.setOrganame(orga.getName());
		result.setLoginName(user.getLoginName());
		result.setPassword(user.getPassword());
		result.setEmail(user.getEmail());
		//设置机构id
		result.setOrganizationId(organizationId);
		if(ObjUtil.isNotEmpty(role)){
			result.setRoleFunctionPointList(functionpointService.findIdsByRoleId(role.getId()));
		}
		return result;
	}
	
	/**
	 * 修改注册机构，修改用户名，机构名，功能点<br>
	 * 如果，只有机构，而没有用户，角色，需要抛出异常。
	 * @param register
	 * @author caijin
	 */
	@Override
	public UserBean updateOrganization(RegisterBean register) {
		//判断用户是否为null
		User user=userService.findByOrganizationAdmin(register.getOrganizationId());
		if(ObjUtil.isEmpty(user)){
			Exceptions.service("DictTypeHasExist", "DictTypeHasExist");
		}
		//判断角色是否为null
		Organization orga=organizationService.findById(register.getOrganizationId());
		Long roleId=orga.getAdminRoleId();
		Role role=roleService.findById(roleId);
		//如果角色不存在，重新创建一个新的系统角色
		if(ObjUtil.isEmpty(role)){
			role=new Role();
			roleService.checkAddRoleName(orga.getName()+"管理员");
			role.setName(orga.getName()+"管理员");
			role.setOrgId(orga.getId());
			role.setRoleType(RoleType.SYSTEM);
			role.setRemark(orga.getName()+"管理员");
			roleService.saveAdmin(role);
			//设置机构管理员
			orga.setAdminUserId(user.getId());
			//设计机构角色
			orga.setAdminRoleId(role.getId());
			//关联角色
			roleFunctionPointService.save(role.getId(),register.getFunctionPointIds());
		}
		//修改用户
		user.setLoginName(register.getLoginName());
		user.setEmail(register.getEmail());
		userService.updateSimple(user);
		
		//给指定角色，编辑功能点 
		roleFunctionPointService.save(role.getId(), register.getFunctionPointIds());
		
		//修改机构参数
		editParameter(register);
		organizationService.updateName(register.getOrganizationId(),register.getOrganame());
		//发送邮件
//		new SendMail().sendMail("机构修改提醒", "机构修改成功，用户为："+user.getLoginName(), user.getEmail());
		return new UserFacadeImpl().wrapBean(user);
	}
	
	/**
	 * 初始化修改管理员密码
	 * @param organizationId
	 * @author caijin
	 */
	@Override
	public void updateAdminPassword(Long organizationId) {
		User user=userService.findByOrganizationAdmin(organizationId);
		if(ObjUtil.isEmpty(user)){
			Exceptions.service("DictTypeHasExist", "管理员不存在!");
		}
		String password=organizationParameterService.findParameterValue(organizationId, "pass");
		//修改用户密码
		user.setPassword(Md5PwdEncoder.encodePassword(password));
		user.setResetPassword(null);
		userService.updateSimple(user);
		//发送邮件
//		mail.sendMail("机构管理用户初始化密码", "管理员 密码："+password, user.getEmail());
	}
	
	/**
	 * 修改注册机构，修改用户名，机构名，功能点
	 * @param register
	 * @author caijin
	 */
	public PagedInfo<Organization> findOrganizationPage(Page page){
		PagedInfo<Organization> result = organizationService.findOneLevelPage(page);
		List<Long> userIds=PersistableUtil.arrayTransforList(result, new ToValue<Organization,Long>(){
			@Override
			public Long getValue(Organization obj) {
				return obj.getAdminUserId();
			}
		});
		
		Map<Long,User> userMap=userService.findMapByIds(new ArrayList<Long>(userIds));
		List<Organization> organizationList = result.getRows();
		for(int i=0;i<organizationList.size();i++){
			Organization orga = organizationList.get(i);
//			User users=userService.findByOrganizationAdmin(orga.getId());
			User users=userMap.get(orga.getAdminUserId());
			if(users!=null){
				orga.setRemark(users.getLoginName());
			}
		}
		return result;
	}
	
	/**
	 * 修改机构参数
	 * @param register
	 */
	private void editParameter(RegisterBean register) {
		//先 删除机构删除。
		organizationParameterService.deleteForOrgId(register.getOrganizationId());
		List<Parameters> parameters=register.getParameterValues();
		for(int i=0;i<parameters.size();i++){
			organizationParameterService.update(register.getOrganizationId(),parameters.get(i).getId(), parameters.get(i).getParameterValue());
		}
	}
	@Override
	public boolean checkUpdateName(Long id, String name) {
		Organization organization = existUserLoginName(name);
		if (ObjUtil.isEmpty(organization) || organization.getId().equals(id)) {
			return false;
		}
		return true;
	}

	/**
	 * 查询是否存在name
	 * 
	 * @param name
	 * @return
	 */
	private Organization existUserLoginName(String name) {
		Param param = new AndEqParam("name", name);
		return organizationService.existParam(param);
	}

	@Override
	public void saveOrgDefaultPermissions(Long organizationId,Long[] functionPointIds) {
		if(ObjUtil.isEmpty(functionPointIds)){
			return;
		}
		
		if(ObjUtil.isEmpty(organizationId)){
			Exceptions.service("", "必须选择要授权的域");
		}
		if(Organization.global.equals(organizationId)){
			Exceptions.service("", "全局域不能够修改");
		}
//		if(ObjUtil.isEmpty(funs)){
//			Exceptions.service("", "必须选择授权功能点");
//		}
		Organization orga=organizationService.findById(organizationId);
		if(ObjUtil.isEmpty(organizationId)){
			Exceptions.service("", "必须选择的域不存在");
		}
		Long roleId=orga.getAdminRoleId();
		Role role=null;
		if(ObjUtil.isNotEmpty(roleId)){
			role=roleService.findById(roleId);
		}
		//判断是否存在角色
		if(ObjUtil.isEmpty(role)){
			Exceptions.service("", "域还没有具体授权");
		}
		//修改全部的状态
		int size = roleFunctionPointService.updateMustAll(roleId);
		//查询当前机构，功能点是否存在
		for (Long funId : functionPointIds) {
			RoleFunctionPoint roleFunctionPoint = roleFunctionPointService.findByOrganizationIdFunId(roleId,funId);
			if(ObjUtil.isNotEmpty(roleFunctionPoint)){
				roleFunctionPoint.setAuthenticationMode(AuthenticationMode.DEFAULT);
				roleFunctionPointService.updateSimple(roleFunctionPoint);
			}
		}
	}
}
