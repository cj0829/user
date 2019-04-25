package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.entity.RoleBean;
import org.csr.common.user.facade.RoleFacade;
import org.csr.common.user.facade.UserRoleFacade;
import org.csr.core.page.PagedInfo;
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

/**
 * ClassName:UserAction.java <br/>
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
@Controller
@Scope("prototype")
@RequestMapping(value = "/userRole")
public class UserRoleAction extends BasisAction {
	
	@Resource
	private RoleFacade roleFacade;
	@Resource
	private UserRoleFacade userRoleFacade;
	
	protected String preUserAddRole = "common/user/userAddRole";
	
	//============未授权
	/**
	 * preAddUserRole: 进入取消角色授权页面 <br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preUserAddRole", method = RequestMethod.GET)
	public String preUserAddRole(ModelMap model,@RequestParam(value="userId")Long userId) {
		model.addAttribute("userId", userId);
		return preUserAddRole;
	}
	/**
	 * @description:查询未授权的安全角色
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/listAddUserRole", method = RequestMethod.POST)
	public ModelAndView listAddUserRole(@RequestParam(value="userId")Long userId,@RequestParam(value="name",required=false)String name) {
		if(ObjUtil.isEmpty(userId)){
			errorMsgJson("您没有选择用户");
		}
		List<Long> ids = roleFacade.findByUserId(userId);
		List<RoleBean> roleList=roleFacade.findByIds(ids);
//		if(obj)
		Long primaryOrgId = UserSessionContext.getUserSession().getPrimaryOrgId();
    	PagedInfo<RoleBean> result  = roleFacade.findAllListPage(page,primaryOrgId);
    	if(ObjUtil.isNotEmpty(roleList)){
			for (RoleBean role : roleList) {
				RoleBean persistable = result.hasRow(role);
				if(ObjUtil.isEmpty(persistable)){
					role.setChecked(true);
					result.getRows().add(role);
				}else{
					persistable.setChecked(true);
				}
			}
		}
    	return resultExcludeJson(result);
	}
	/**
	 * @description:保存用户角色
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/updateUserRole", method = RequestMethod.POST)
	public ModelAndView updateUserRole(@RequestParam(value="userId")Long userId,@RequestParam(value="roleIds")Long[] roleIds) {
		userRoleFacade.updateUserRoles(userId, ObjUtil.toList(roleIds));
		return successMsgJson("");
	}
	//============end未授权
	
}
