package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserSpecialFunctionPointFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@Scope("prototype")
@RequestMapping(value="/userSpecialFunctionPoint")
public class UserSpecialFunctionPointAction  extends BasisAction {
	protected String preAddUserSpecialFunctionPoint = "common/user/userSpecialFunctionPointAdd";
	protected String preDeleteUserSpecialFunctionPoint = "common/user/userSpecialFunctionPointDelete";
	//已启用的特权
	protected String preExistEnableUserSpecialFunctionPoint = "common/user/existEnableUserSpecialFunctionPoint";
	
	@Resource
	private UserSpecialFunctionPointFacade userSpecialFunctionPointFacade;
	@Resource
	private UserFacade userFacade;
	@Resource
	private FunctionpointFacade functionpointFacade;
	/**
	 * @description:进入授权页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preAddUserSpecialFunctionPoint", method = RequestMethod.GET)
	public String preAddUserSpecialFunctionPoint(ModelMap model,@RequestParam(value="userId")Long userId) {
		UserBean user = userFacade.findById(userId);
		if(ObjUtil.isEmpty(user) || ObjUtil.isEmpty(user.getAgenciesId())){
			Exceptions.service("", "没有用户，请您指定要授权的用户");
		}
		model.addAttribute("functionPointList",functionpointFacade.findUnAuthorizeByUserIdWrapCompleteStructure(userId,UserSessionContext.getUserSession().getPrimaryOrgId(),true));
//    	model.addAttribute("roleFunctionPointList",functionpointService.findIdsByRoleId(organization.getAdminRoleId()));
		
		model.addAttribute("userId", userId);
		return preAddUserSpecialFunctionPoint;
	}

	/**
	 * @description:保存授权信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/addUserSpecialFunctionPoint", method = RequestMethod.POST)
	public ModelAndView addUserSpecialFunctionPoint(@RequestParam(value="userId")Long userId,@RequestParam(value="functionPointIds")Long[] functionPointIds) {
		userSpecialFunctionPointFacade.saveUserSpecialFunctionPoint(userId,functionPointIds);
		return successMsgJson("");
	}

	/**
	 * @description:进入取消授权页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preDeleteUserSpecialFunctionPoint", method = RequestMethod.GET)
	public String preDeleteUserSpecialFunctionPoint(ModelMap model,@RequestParam(value="userId")Long userId) {
		model.addAttribute("functionPointList", functionpointFacade.findCanBeAuthorizedWrapStructure(true));
		model.addAttribute("userId", userId);
		return preDeleteUserSpecialFunctionPoint;
	}

	/**
	 * @description:取消授权
	 * @param:
	 * @return: String
	 */
	
	@RequestMapping(value = "ajax/deleteUserSpecialFunctionPoint", method = RequestMethod.POST)
	public ModelAndView deleteUserSpecialFunctionPoint(ModelMap model,@RequestParam(value="userId")Long userId,@RequestParam(value="functionPointIds")Long[] functionPointIds) {
		userSpecialFunctionPointFacade.deleteUserSpecialFunctionPoint(userId,functionPointIds);
		return successMsgJson("");
	}

	
	/**
	 * 已经启用的特权
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "preExistEnableUserSpecialFunctionPoint", method = RequestMethod.GET)
	public String preExistEnableUserSpecialFunctionPoint(ModelMap model,@RequestParam(value="userId")Long userId) {
		UserBean user = userFacade.findById(userId);
		if(ObjUtil.isEmpty(user) || ObjUtil.isEmpty(user.getAgenciesId())){
			Exceptions.service("", "没有用户，请您指定要授权的用户");
		}
		model.addAttribute("functionPointList", functionpointFacade.findUnAuthorizeByUserIdWrapCompleteStructure(userId,user.getAgenciesId(),true));
		model.addAttribute("userId", userId);
		return preExistEnableUserSpecialFunctionPoint;
	}
}
