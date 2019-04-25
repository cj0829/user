package org.csr.common.user.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.csr.common.user.constant.RoleType;
import org.csr.common.user.constant.SafeResourceType;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.entity.RoleBean;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.RoleFacade;
import org.csr.common.user.facade.RoleFunctionPointFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserRoleFacade;
import org.csr.core.Constants;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.DictionaryUtil;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:RoleAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/role")
public class RoleAction extends BasisAction {
	
	protected String preList = "common/role/roleList";
	protected String preInfo = "common/role/roleInfo";
	protected String preAdd = "common/role/roleAdd";
	protected String preUpdate = "common/role/roleUpdate";
	
	protected String preRoleFunctionPointInfo = "common/role/roleFunctionPointInfo";
	protected String preAddRoleFunctionPoint = "common/role/roleFunctionPointAdd";
	protected String preAddUserRole = "common/role/userRoleAdd";
	protected String preDeleteUserRole = "common/role/userRoleDelete";

	@Resource
	protected RoleFacade roleFacade;
	@Resource
	protected RoleFunctionPointFacade roleFunctionPointFacade;
	@Resource
	protected FunctionpointFacade functionpointFacade;
	@Resource
	protected UserRoleFacade userRoleFacade;
	@Resource
	protected UserFacade userFacade;
	@Resource
	private SafeResourceFacade safeResourceFacade;
	
    /**
     * @description:进入角色管理页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preList", method = RequestMethod.GET)
    public String preList(ModelMap model) {
		model.addAttribute("roleType", DictionaryUtil.getDictToJson("roleType"));
		return preList;
    }

    /**
     * @description:查询角色列表
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/list", method = RequestMethod.POST)
    public ModelAndView list() {
    	Long primaryOrgId = UserSessionContext.getUserSession().getPrimaryOrgId();
    	PagedInfo<RoleBean> result  = roleFacade.findAllListPage(page,primaryOrgId);
    	return resultExcludeJson(result);
    }

    /**
     * @description:查看详细信息
     */
    @RequestMapping(value = "preInfo", method = RequestMethod.GET)
    public String preInfo(ModelMap model,@RequestParam("id")Long id) {
		model.addAttribute("roleType", DictionaryUtil.getDictToJson("roleType"));
		model.addAttribute("role", roleFacade.findById(id));
		return preInfo;
    }

    /**
     * @description:进入角色添加页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAdd", method = RequestMethod.GET)
    public String preAdd() {
		Long primaryOrgId = UserSessionContext.getUserSession().getPrimaryOrgId();
		if(!Organization.global.equals(primaryOrgId)){
			setRequest("organizationId", primaryOrgId);
		}
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			setRequest("USER_SUPER", true);
		}
		return preAdd;
    }

    /**
     * @description:保存新增角色信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/add", method = RequestMethod.POST)
    public ModelAndView add(Role role) {
//    	role.setRoot(UserSessionContext.getUserSession().getPrimaryOrgId());
    	roleFacade.saveCustom(role);
		return successMsgJson("");
    }

    /**
     * @description:进入角色编辑页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preUpdate", method = RequestMethod.GET)
    public String preUpdate(ModelMap model,@RequestParam("roleId")Long roleId) {
		model.addAttribute("role", roleFacade.findById(roleId));
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			setRequest("USER_SUPER", true);
		}
		return preUpdate;
    }

    /**
     * @description:保存修改角色信息
     * @param:
     * @return: String
     */
    
    @RequestMapping(value = "ajax/update", method = RequestMethod.POST)
    public ModelAndView update(Role role) {
    	roleFacade.updateCustom(role);
		return successMsgJson("");
    }

    /**
     * @description:删除角色信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("roleIds")Long[] roleIds) {
    	roleFacade.deleteRole(roleIds);
		return successMsgJson("");
    }

    /**
     * @description:检测添加角色名是否存在
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/findAddRoleName", method = RequestMethod.POST)
    public ModelAndView findAddRoleName(@RequestParam("name")String name) {
		if (roleFacade.checkAddRoleName(name)) {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("roleNameIsExist"));
		}
		return successMsgJson("");
    }

    /**
     * @description:检测修改角色名是否存在
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/findUpdateRoleName", method = RequestMethod.POST)
    public ModelAndView findUpdateRoleName(@RequestParam("roleId")Long roleId,@RequestParam("name")String name) {
		if (roleFacade.checkUpdateRoleName(roleId,name)) {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("roleNameIsExist"));
		}
		return successMsgJson("");
    }
    
    /**
     * @description:进入角色关联功能点页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAddRoleFunctionPoint", method = RequestMethod.GET)
    public String preAddRoleFunctionPoint(ModelMap model,@RequestParam("roleId")Long roleId){
    	if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
    		List<FunctionPointNode> canBeAuthorizedWrapStructure = functionpointFacade.findCanBeAuthorizedWrapStructure(true);
			model.addAttribute("functionPointList",canBeAuthorizedWrapStructure);
			model.addAttribute("roleFunctionPointList",functionpointFacade.findIdsByRoleId(roleId));
    	}else{
    		if(!ObjUtil.longNotNull(UserSessionContext.getUserSession().getPrimaryOrgId())){
     			Exceptions.service("organizationRootIsNull", "organizationRootIsNull");
     		}
        	RoleBean admin = roleFacade.findOrgRole(UserSessionContext.getUserSession().getPrimaryOrgId());
        	if(ObjUtil.isEmpty(admin)){
    			return NoOrganization;
        	}
    		model.addAttribute("functionPointList",functionpointFacade.findByRoleIdWrapStructure(admin.getId(),true));
    		model.addAttribute("roleFunctionPointList",functionpointFacade.findIdsByRoleId(roleId));
    	}
//    	model.addAttribute("functionPointList",functionpointService.wrapStructure(functionpointService.findAllCanBeAuthorized(),true));
//    	model.addAttribute("roleFunctionPointList",functionpointService.findIdsByRoleId(roleId));
    	model.addAttribute("roleId",roleId);
		return preAddRoleFunctionPoint;
    }
    
    /**
     * @description:根据角色查询-功能点
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preRoleFunctionPointInfo", method = RequestMethod.GET)
    public String preRoleFunctionPointInfo(ModelMap model,@RequestParam("roleId")Long roleId){
		model.addAttribute("functionPointList", functionpointFacade.findAllByRoleIdWrapStructure(roleId,true));
		return preRoleFunctionPointInfo;
    }
    
    
    /**
     * @description:保存角色关联功能点信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/addRoleFunctionPoint", method = RequestMethod.POST)
    public ModelAndView addRoleFunctionPoint(ModelMap model,@RequestParam("roleId")Long roleId,@RequestParam("functionPointIds")Long[] functionPointIds){
    	roleFunctionPointFacade.save(roleId,functionPointIds);
		return successMsgJson("");
    }
    
    /**
     * @description:进入批量授权页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAddUserRole", method = RequestMethod.GET)
    public String preAddUserRole(ModelMap model,@RequestParam("roleId")Long roleId){
    	model.addAttribute("roleId",roleId);
    	return preAddUserRole;
    }
    
    /**
     * @description:保存批量授权信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/addUserRole", method = RequestMethod.POST)
    public ModelAndView addUserRole(@RequestParam("roleId")Long roleId,@RequestParam("userIds")Long[] userIds){
    	userRoleFacade.saveUserRole(roleId, userIds);
		return successMsgJson("");
    }
    
   
    
    /**
     * @description:查询未授权用户列表
     * @param: 
     * @return: String 
     */
    @RequestMapping(value = "ajax/listAddUserRole", method = RequestMethod.POST)
    public ModelAndView listAddUserRole(@RequestParam("roleId")Long roleId,
    		@RequestParam(value="loginName",required=false)String loginName,
    		@RequestParam(value="agenciesIds",required=false)Long[] agenciesIds,
    		@RequestParam(value="name",required=false)String name){
    	final Set<Long> agenciesSet = new HashSet<Long>();
    	//设置机构，根据权限
		if(ObjUtil.isNotEmpty(agenciesIds)){
			agenciesSet.addAll(ObjUtil.asList(agenciesIds));
		}else{
			List<Long> agenciIds = safeResourceFacade.findCategoryIdsBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(), SafeResourceType.AGENCIES);
			if(ObjUtil.isNotEmpty(agenciIds)){
				agenciesSet.addAll(agenciIds);
			}else{
				if(!User.SUPER.equals(UserSessionContext.getUserSession().getUserId())) {
					agenciesSet.add( UserSessionContext.getUserSession().getAgenciesId());
				}
				
			}
		}
    	PagedInfo<UserBean>  pages= userFacade.findUnJoinUserByRoleId(page, roleId,loginName,name,new ArrayList<>(agenciesSet));
		return resultExcludeJson(pages);
    }
    
    /**
     * @description:进入批量取消授权页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preDeleteUserRole", method = RequestMethod.GET)
    public String preDeleteUserRole(ModelMap model,@RequestParam("roleId")Long roleId){
    	model.addAttribute("roleId",roleId);
    	return preDeleteUserRole;
    }
    
    /**
     * @description:批量取消授权
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/deleteUserRole", method = RequestMethod.POST)
    public ModelAndView deleteUserRole(@RequestParam("roleId")Long roleId,@RequestParam("userIds")Long[] userIds){
    	userRoleFacade.deleteUserRole(roleId, userIds);
		return successMsgJson("");
    }
    
    /**
     * @description:查询已授权用户列表
     * @param: 
     * @return: String 
     */
    @RequestMapping(value = "ajax/listDeleteUserRole", method = RequestMethod.POST)
    public ModelAndView  listDeleteUserRole(@RequestParam("roleId")Long roleId,
    		@RequestParam(value="loginName",required=false)String loginName,
    		@RequestParam(value="agenciesIds",required=false)Long[] agenciesIds,
    		@RequestParam(value="name",required=false)String name){
    	PagedInfo<UserBean>  pages= userFacade.findJoinUserByRoleId(page, roleId,loginName,name,ObjUtil.asList(agenciesIds));
		return resultExcludeJson(pages);
    }
  
    /**
     * @description:查询已授权用户列表
     * @param: 
     * @return: String 
     */
    @RequestMapping(value = "ajax/findRoleListByRoot", method = RequestMethod.POST)
    public ModelAndView  findRoleListByRoot(){
    	List<RoleBean> roles = roleFacade.findRoleList();
		return resultExcludeJson(roles);
    }
    /**
	 * findByAgenciesId:根据机构查域下面的所有角色
	 * @author huayj
	 * @param agenciesId
	 * @return
	 * @return ModelAndView
	 * @date&time 2015-10-29 下午5:14:07
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/findDropDownList", method = RequestMethod.POST)
    public ModelAndView findDropDownList(@RequestParam(value="agenciesId",required=false)Long agenciesId,
    		@RequestParam(value="oldIds",required=false)String oldIds) {
		List<Long> ids = PersistableUtil.stringToList(oldIds);
		List<RoleBean> roleList=roleFacade.findByIds(ids);
    	PagedInfo<RoleBean> result  = roleFacade.findAllPage(page);
    	if(ObjUtil.isNotEmpty(roleList)){
			for (RoleBean role : roleList) {
				if(ObjUtil.isEmpty(result.hasRow(role))){
					result.getRows().add(role);
				}
			}
		}
    	return resultExcludeJson(result);
    }
	
	 /**
		 * findByAgenciesId:根据机构查域下面的所有角色
		 * @author huayj
		 * @param agenciesId
		 * @return
		 * @return ModelAndView
		 * @date&time 2015-10-29 下午5:14:07
		 * @since JDK 1.7
		 */
		@RequestMapping(value = "ajax/findGlobalDropDownList", method = RequestMethod.POST)
	    public ModelAndView findGlobalDropDownList(@RequestParam(value="oldIds",required=false)String oldIds) {
			List<Long> ids = PersistableUtil.stringToList(oldIds);
			List<RoleBean> roleList=roleFacade.findByIds(ids);
			page.toParam().add(new AndEqParam("roleType", RoleType.GLOBAL));
	    	PagedInfo<RoleBean> result  = roleFacade.findAllPage(page);
	    	if(ObjUtil.isNotEmpty(roleList)){
				for (RoleBean role : roleList) {
					if(ObjUtil.isEmpty(result.hasRow(role))){
						result.getRows().add(role);
					}
				}
			}
	    	return resultExcludeJson(result);
	    }
}
