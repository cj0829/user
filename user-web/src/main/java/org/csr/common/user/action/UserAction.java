package org.csr.common.user.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.csr.common.user.constant.SafeResourceType;
import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.AgenciesNode;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.entity.UserPasswordProblemBean;
import org.csr.common.user.entity.UserRoleBean;
import org.csr.common.user.entity.UserSafeResourceCollectionBean;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.AgenciesUserFacade;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.MenuFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.common.user.facade.UserPasswordProblemFacade;
import org.csr.common.user.facade.UserRoleFacade;
import org.csr.common.user.facade.UserSafeResourceCollectionFacade;
import org.csr.core.Constants;
import org.csr.core.MenuNode;
import org.csr.core.constant.YesorNo;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.csr.core.util.support.ToString;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 * ClassName:UserAction.java <br/>
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
@RequestMapping(value = "/user")
public class UserAction extends BasisAction {

	protected String preList = "common/user/userList";
	protected String preInfo = "common/user/userInfo";
	protected String preAdd = "common/user/userAdd";
	protected String preUpdate = "common/user/userUpdate";
	protected String preUpdateResetPassword = "common/user/updateResetPassword";
	protected String preUserPreferences = "common/user/userPreferences";
	
	protected String preHome="common/user/userHome";

	@Resource
	private AgenciesUserFacade agenciesUserFacade;
	@Resource
	private UserRoleFacade userRoleFacade;
	@Resource
	private FunctionpointFacade functionpointFacade;
	@Resource
	private UserPasswordProblemFacade userPasswordProblemFacade;
	@Resource
	private MenuFacade menuFacade;
	@Resource
	private AgenciesFacade agenciesFacade;
	@Resource
	private UserSafeResourceCollectionFacade userSafeResourceCollectionFacade;
	@Resource
	private SafeResourceFacade safeResourceFacade;
	
	@RequestMapping(value="preHome",method=RequestMethod.GET)
	public String preHome(ModelMap model){
		UserBean userBean = agenciesUserFacade.findById(UserSessionContext.getUserSession().getUserId());
		setRequest("user", userBean);
		return preHome;
	}
	/**
	 * @description:进入用户管理页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model) {
		return preList;
	}
	/**
	 * 用户查询，根据用户自身的查询权限，来查询用户是否能够查询全部域<br>
	 * 目前，在用户管理页面使用。
	 * 
	 * @param loginName
	 * @param name
	 * @param id
	 * @param agenciesId
	 * @return
	 */
	@RequestMapping(value = "ajax/usersByAgenciesList", method = RequestMethod.POST)
	public ModelAndView usersByAgenciesList(@RequestParam(value="loginName",required=false)String loginName,
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="agenciesId",required=false)Long agenciesId) {// 此处
		boolean has = agenciesFacade.findHasAuthorityByUserId(agenciesId);
		if(!has){
			return resultExcludeJson(PersistableUtil.createPagedInfo(0, page, new ArrayList<>()));
		}
		ArrayList<Long> list = new ArrayList<Long>();
		List<AgenciesNode> childIds = agenciesFacade.findCountChildByIds(agenciesId);
		if(ObjUtil.isNotEmpty(childIds)){
			list.addAll(PersistableUtil.arrayTransforList(childIds));
		}
		list.add(agenciesId);
		PagedInfo<UserBean> result = agenciesUserFacade.findListPage(page, loginName, name, id, null,list);// 此处
		return resultExcludeJson(result);
	}

	/**
	 * @description:查看详细信息
	 */
	@RequestMapping(value = "preInfo", method = RequestMethod.GET)
	public String preInfo(ModelMap model,@RequestParam(value="id")Long id) {
		UserBean user=agenciesUserFacade.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("agencies",user.getAgenciesName());
		model.addAttribute("manager",user.getManagerUserName());
		FunctionPointNode fp=functionpointFacade.findById(user.getDufHome());
		if(ObjUtil.isNotEmpty(fp)){
			model.addAttribute("dufHome",fp.getName());
		}
		return preInfo;
	}

	/**
	 * @description:进入用户添加页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preAdd", method = RequestMethod.GET)
	public String preAdd(ModelMap model,@RequestParam(value="agenciesId")Long agenciesId) {
		AgenciesNode agencies=agenciesFacade.findById(agenciesId);
		model.addAttribute("agenciesId",agenciesId);
		if(ObjUtil.isNotEmpty(agencies)){
			model.addAttribute("agenciesName",agencies.getName());
		}
		return preAdd;
	}

	/**
	 * @description:保存新增用户信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/add")
	public ModelAndView add(User user,@RequestParam(value="roleIds",required=false)Long[] roleIds,
			@RequestParam(value="safeResourceIds",required=false)Long[] safeResourceIds) {
		//设置用户状态
		user.setUserStatus(UserStatus.NORMAL);
		if (!agenciesUserFacade.saveUser(user)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("loginNameIsExist"));
		}
		return successMsgJson("");
	}

	/**
	 * @description:进入用户编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preUpdate", method = RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam(value="id")Long id) {
		UserBean bean = agenciesUserFacade.findById(id);
		model.addAttribute("user", bean);
		List<UserRoleBean> urBean = userRoleFacade.findUserRoleList(id);
		String userRole=PersistableUtil.arrayTransforString(urBean, new ToString<UserRoleBean>(){
			@Override
			public String getValue(UserRoleBean obj) {
				if(ObjUtil.isNotEmpty(obj)){
					return ObjUtil.toString(obj.getRoleId());
				}
				return null;
			}
		});
		model.addAttribute("userRoleList",userRole);
		List<UserSafeResourceCollectionBean> userSrces=userSafeResourceCollectionFacade.findByUserId(bean.getId());
		String safeResourceIds=PersistableUtil.arrayTransforString(userSrces, new ToString<UserSafeResourceCollectionBean>(){
			@Override
			public String getValue(UserSafeResourceCollectionBean obj) {
				if(ObjUtil.isNotEmpty(obj.getSafeResourceCollectionBean())){
					return ObjUtil.toString(obj.getSafeResourceCollectionBean().getId());
				}
				return null;
			}
		});
		model.addAttribute("safeResourceIds",safeResourceIds);
		return preUpdate;
	}

	/**
	 * @description:保存修改用户信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/update")
	public ModelAndView update(User user,@RequestParam(value="roleIds",required=false)Long[] roleIds,
			@RequestParam(value="safeResourceIds",required=false)Long[] safeResourceIds) {
		agenciesUserFacade.updateUser(user);
		return successMsgJson("");
	}
	
	
	/**
	 * @description:重置密码
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/updateResetPassword", method = RequestMethod.POST)
	public ModelAndView updateResetPassword(@RequestParam(value="userId")Long userId,
			@RequestParam(value="oldPassword",required=false)String oldPassword,
			@RequestParam(value="password",required=false)String password, 
			@RequestParam(value="problemId",required=false)Long problemId, 
			@RequestParam(value="problemName",required=false)String problemName,
			@RequestParam(value="answer",required=false)String answer) {
		userPasswordProblemFacade.updateUserAndPasswordProblem(userId,	oldPassword, password, problemId, problemName, answer);
		return successMsgJson("");
	}


	/**
	 * 进入首选项编辑页面
	 * 
	 * @author caijin
	 * @param model
	 * @param userId
	 * @param isInternal
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preUserPreferences", method = RequestMethod.GET)
	public String preUserPreferences(ModelMap model, @RequestParam(value="userId")Long userId) {
		model.addAttribute("user", agenciesUserFacade.findById(userId));
		model.addAttribute("userId", userId);
		return preUserPreferences;
	}
	
	
	/**
	 * @description:检测用户名是否存在
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findLoginName", method = RequestMethod.POST)
	public ModelAndView findLoginName(@RequestParam(value="userId",required=false)Long userId,@RequestParam(value="loginName")String loginName) {
		if (agenciesUserFacade.checkUpdateLoginName(userId,loginName)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("loginNameIsExist"));
		}
		return successMsgJson("");
	}


	/**
	 * @description:删除用户信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value="ids")Long[] ids) {
		if (ids != null && ids.length > 0) {
			agenciesUserFacade.deleteUser(ids);
		} else {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		return successMsgJson("");
	}

	/////////////提供别的action
	/**
	 * @description:根据参数查询用户列表
	 * @author yjY
	 * @param loginName
	 * @param name
	 * @param familyName
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ajax/findUsersList", method = RequestMethod.POST)
	public ModelAndView findUsersList(@RequestParam(value="loginName",required=false)String loginName,
			@RequestParam(value="name",required=false)String name,@RequestParam(value="userId",required=false)Long userId
			,@RequestParam(value="agenciesIds",required=false)Long[] agenciesIds) {
		PagedInfo<UserBean> result = agenciesUserFacade.findListPage(page,loginName,name,userId,ObjUtil.toList(UserRoleType.GENERAL),ObjUtil.asList(agenciesIds));
		return resultExcludeJson(result);
	}
	/**
	 * @description:根据参数查询用户列表
	 * @author yjY
	 * @param loginName
	 * @param name
	 * @param familyName
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ajax/findDropDownList", method = RequestMethod.POST)
	public ModelAndView findDropDownList(@RequestParam(value="allRoot",required=false)boolean allRoot,
			@RequestParam(value="oldId",required=false)String oldId,
			@RequestParam(value="loginName",required=false)String loginName, 
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="agenciesIds",required=false)Long[] agenciesIds) {// 此处
		//需要判断是否查询域，还是全部域
//		Long root=null;
//		if(!allRoot){
//			root=UserSessionContext.getUserSession().getPrimaryOrgId();
//		}
		PagedInfo<UserBean> result = agenciesUserFacade.findListPage(page, loginName, name, id, ObjUtil.toList(UserRoleType.ADVANCED,UserRoleType.ADMIN),null);// 此处
		if(ObjUtil.isNotBlank(oldId)){
			UserBean user=agenciesUserFacade.findById(ObjUtil.toLong(oldId));
			if(ObjUtil.isNotEmpty(user)){
				if(ObjUtil.isEmpty(result.hasRow(user))){
					result.getRows().add(user);
				}
			}
		}
		return resultExcludeJson(result);
	}
	
	/**
	 * 查询组管理 列表数据
	 * @author  LAPTOP-IBR0N68F
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/findAuthorityList",method=RequestMethod.POST)
	public ModelAndView findAuthorityList(@RequestParam(value="loginName",required=false)String loginName, 
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="agenciesId",required=false)Long agenciesId){
		final Set<Long> agenciesSet = new HashSet<Long>();
		//设置机构，根据权限
		if(ObjUtil.isNotEmpty(agenciesId)){
			agenciesSet.add(agenciesId);
		}else{
			List<Long> agenciesIds = safeResourceFacade.findCategoryIdsBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(), SafeResourceType.AGENCIES);
			if(ObjUtil.isNotEmpty(agenciesIds)){
				agenciesSet.addAll(agenciesIds);
			}else{
				agenciesSet.add( UserSessionContext.getUserSession().getAgenciesId());
			}
		}
		//获取机构ids
		PagedInfo<UserBean> result = agenciesUserFacade.findListPage(page,loginName, name, null, null,new ArrayList<>(agenciesSet));// 此处
		return resultExcludeJson(result);
	}
	/**
	 * 下拉列表查询用户，根据用户权限查询。
	 * @author  caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/findAuthorityDropDownList",method=RequestMethod.POST)
	public ModelAndView findAuthorityDropDownList(@RequestParam(value="allRoot",required=false)boolean allRoot,
			@RequestParam(value="oldId",required=false)String oldId,
			@RequestParam(value="loginName",required=false)String loginName, 
			@RequestParam(value="name",required=false)String name,
			@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="agenciesId",required=false)Long agenciesId){
		final Set<Long> agenciesSet = new HashSet<Long>();
		//设置机构，根据权限
		if(ObjUtil.isNotEmpty(agenciesId)){
			agenciesSet.add(agenciesId);
		}else{
			List<Long> agenciesIds = safeResourceFacade.findCategoryIdsBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(), SafeResourceType.AGENCIES);
			if(ObjUtil.isNotEmpty(agenciesIds)){
				agenciesSet.addAll(agenciesIds);
			}else{
				agenciesSet.add( UserSessionContext.getUserSession().getAgenciesId());
			}
		}
		PagedInfo<UserBean> result = agenciesUserFacade.findListPage(page,loginName, name, null, null,new ArrayList<>(agenciesSet));// 此处
		if(ObjUtil.isNotBlank(oldId)){
			UserBean user=agenciesUserFacade.findById(ObjUtil.toLong(oldId));
			if(ObjUtil.isNotEmpty(user)){
				if(ObjUtil.isEmpty(result.hasRow(user))){
					result.getRows().add(user);
				}
			}
		}
		return resultExcludeJson(result);
	}

	/**
	 * @description:进入用户编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preUpdateResetPassword", method = RequestMethod.GET)
	public String preUpdateResetPassword(ModelMap model, @RequestParam(value="userId",required=false)Long userId) {
		model.addAttribute("userId", userId);
		model.addAttribute("userLoginName",agenciesUserFacade.findNameById(userId, "loginName"));
		model.addAttribute("userPasswordProblem", userPasswordProblemFacade.findByUserIdAndStatus(userId, YesorNo.YES));
		return preUpdateResetPassword;
	}

	@RequestMapping(value = "ajax/findUserDufMeun", method = RequestMethod.POST)
	public ModelAndView findUserDufMeun(Long userId) {
		UserBean user = agenciesUserFacade.findById(userId);
		UserSessionBasics userSession = new UserSessionBasics(user.getLoginName());
		userSession.setUserId(user.getId());
		userSession.setPrimaryOrgId(user.getPrimaryOrgId());
		List<SecurityResourceBean> srList = (List<SecurityResourceBean>) functionpointFacade.findResourcesByUser(userSession);
		List<MenuNodeBean> findAllMenu = (List<MenuNodeBean>) menuFacade.findAllMenu();
		Iterator<MenuNodeBean> it = findAllMenu.iterator();
		while (it.hasNext()) {
			MenuNodeBean node = it.next();
			if (ObjUtil.isEmpty(node.getSecurityResource())) {
				it.remove();
				continue;
			}
			boolean isAdd = true;
			if (isMenu(node)) {
				for (int i = 0; i < srList.size(); i++) {
					if (node.getSecurityResource().getId().equals(srList.get(i).getId())) {
						isAdd = false;
						break;
					}
				}
			}
			if (isAdd) {
				it.remove();
			}
		}
		return resultExcludeJson(findAllMenu);
	}
	// 判断是否为菜单数据
	boolean isMenu(MenuNode node) {
		if (ObjUtil.isNotEmpty(node)) {
			MenuNode p = node.getParent();
			while (ObjUtil.isNotEmpty(p)) {
				if (Constants.MENU5.equals(p.getId()) ||Constants.MENU2.equals(p.getId()) || Constants.MENU3.equals(p.getId()) ||Constants.MENU4.equals(p.getId())) {
					return true;
				} else {
					p = p.getParent();
				}
			}
			return false;
		} else {
			return false;
		}
	}
	/**
	 * @description:保存用户密码提示
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/saveUserPreferences", method = RequestMethod.POST)
	public ModelAndView addUserPreferences(User user) {
		agenciesUserFacade.updateUserPreferences(user);
		return successMsgJson("");
	}
	
	
	/**
	 * @description:查询用来密码提示列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findUserPasswordProblemList", method = RequestMethod.POST)
	public ModelAndView userPasswordProblemList(Long userId) {
		List<UserPasswordProblemBean> result = userPasswordProblemFacade.findUserPreferencesList(userId);
		return resultExcludeJson(result);
	}
	
}
