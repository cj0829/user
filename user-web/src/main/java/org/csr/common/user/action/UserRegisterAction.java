package org.csr.common.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.flow.facade.TaskInstanceFacade;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserRegisterStrategyFacade;
import org.csr.common.user.facade.UserTaskInstanceFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller
@Scope("prototype")
@RequestMapping(value = "/userRegister")
public class UserRegisterAction extends BasisAction {
	protected String preList = "common/user/userRegisterList";
	protected String preRegUser = "common/user/regUser";
	@Resource
	private UserFacade userFacade;
	@Resource
	private AgenciesFacade agenciesFacade;
	@Resource
	private UserRegisterStrategyFacade userRegisterStrategyFacade;
	@Resource
	protected UserTaskInstanceFacade userTaskInstanceFacade;
	@Resource
	protected TaskInstanceFacade taskInstanceFacade;
	
	protected String userTitle;
	protected String userUrl;
	/**
	 * @description:进入用户管理页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model) {
		model.addAttribute("userTitle", userTitle);
		model.addAttribute("userUrl", userUrl);
		return preList;
	}
	

	@RequestMapping(value = "ajax/findRegisterUsers", method = RequestMethod.POST)
	public ModelAndView findUsersByAgenciesList() {// 此处
		List<UserStrategyBean> userStrategy = userRegisterStrategyFacade.findAllList();
		List<Long> taskTempIds= new ArrayList<Long>(PersistableUtil.arrayTransforList(userStrategy,new ToValue<UserStrategyBean, Long>(){
			@Override
			public Long getValue(UserStrategyBean obj) {
				return obj.getRegisterTaskTempId();
			}
		}));

		PagedInfo<UserTaskInstanceBean> ciList = userTaskInstanceFacade.findApprovePage(page,taskTempIds,"",UserSessionContext.getUserSession().getUserId());
		List<Long> userids=PersistableUtil.arrayTransforList(ciList, new ToValue<UserTaskInstanceBean, Long>(){
			@Override
			public Long getValue(UserTaskInstanceBean obj) {
				if(ObjUtil.isNotEmpty(obj)){
					return obj.getUpdateUserId();
				}
				return null;
			}
			
		});
		final Map<Long, UserBean> userMap=userFacade.findMapByIds(userids);
		
		return resultExcludeJson(userMap.values());
	}
	
	
	/**
	 * @description:保存修改用户信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/updatePassUser")
	public ModelAndView updatePassUser(Long id) {
		userFacade.updatePassUser(id);
		return successMsgJson("");
	}
	/**
	 * 拒绝用户注册
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(Long id) {
		userFacade.deleteRegisterUser(id);
		return successMsgJson("");
	}
	
	/**
	 * 注册用户
	 * @return
	 */
	@RequestMapping(value="preRegUser")
	public String preRegUser(){
		return preRegUser;
	}
	/**
	 * 用户注册
	 * @return
	 */
	@RequestMapping(value="ajax/regUser")
	public ModelAndView regUser(User user){
		userFacade.saveRegUser(user);
		return successMsgJson("");
	}
}
