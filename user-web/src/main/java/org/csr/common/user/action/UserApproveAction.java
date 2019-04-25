/**
 * Project Name:exam
 * File Name:TestPaperApproveAction.java
 * Package Name:org.csr.exam.action
 * Date:2015-7-22下午5:09:05
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.user.constant.ImportApprovalStatus;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.RoleBean;
import org.csr.common.user.entity.UserApprovalBean;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.entity.UserImportFileBean;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.common.user.facade.RoleFacade;
import org.csr.common.user.facade.UserApprovalFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserImportFileFacade;
import org.csr.common.user.facade.UserImportStrategyFacade;
import org.csr.common.user.facade.UserTaskInstanceFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.PageRequest;
import org.csr.core.persistence.param.AndEqParam;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:UserApproveAction.java <br/>
 * System Name：    签到系统 <br/>
 * Date:     2015-7-22下午5:09:05 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 * 用户审批action
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/userApprove")
public class UserApproveAction extends BasisAction{

	/**试卷审批列表*/
	final String preList = "common/userApprove/userApproveList";
	final String preUserImportApproveList = "common/userApprove/userImportApproveList";
	final String preUserPendingList = "common/userApprove/userPendingList";
	final String preUpdate="common/userApprove/userUpdate";
	final String preUserPassTotal="common/userApprove/userPassTotal";
	final String preUserUnPassTotal="common/userApprove/userUnPassTotal";
	
	@Resource
	protected UserTaskInstanceFacade userTaskInstanceFacade;
	@Resource
	private UserImportStrategyFacade userStrategyFacade;
//	@Resource
//	private TaskNodeService taskNodeService;
	@Resource
	protected UserFacade userFacade;
	@Resource
	protected RoleFacade roleFacade;
	@Resource
	protected UserApprovalFacade userApprovalFacade;
	@Resource
	private UserImportFileFacade userImportFileFacade;
	
	public UserApproveAction() {
	}
	
	/**
	 * preList:试卷审批列表<br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model) {
		return preList;
	}
	
	/**
	 * @author  caijin-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preUpdate",method=RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="from",required=false)String from){
		UserBean user = userFacade.findById(id);
		if(ObjUtil.isNotEmpty(user)){
			model.addAttribute("user", user);
			
			PagedInfo<RoleBean> roleList = roleFacade.findByUserId(new PageRequest(1, 2), user.getId(), "");
			if(ObjUtil.isNotEmpty(roleList.getRows())){
				model.addAttribute("role", roleList.getRows().get(0));
			}
			model.addAttribute("user", user);
			model.addAttribute("userId", id);
		}else{
			Exceptions.service("", "要修改的用户不存在");
		}
		return preUpdate;
	}
	
	
	/**
	 * 修改用户，并提交
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "ajax/updateUserTask", method = RequestMethod.POST)
	public ModelAndView updateUserTask(User user) {
		userFacade.updateUserTask(user);
		return successMsgJson("");
		
	}
	/**
	 * findTestPaperApproveList:查询用户审批list<br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/findUserApproveList", method = RequestMethod.POST)
	public ModelAndView findUserApproveList() {
		List<UserStrategyBean> userStrategy = userStrategyFacade.findAllList();
		List<Long> taskTempIds= new ArrayList<Long>(PersistableUtil.arrayTransforList(userStrategy,new ToValue<UserStrategyBean, Long>(){
			@Override
			public Long getValue(UserStrategyBean obj) {
				return obj.getImportTaskTempId();
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
		return resultExcludeJson(userMap);
	}
	
	/**
	 * 进入用户导入审批试题页面
	 * @author Administrator
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preUserImportApproveList", method = RequestMethod.GET)
	public String preUserImportApproveList(ModelMap model) {
		return preUserImportApproveList;
	}
	
	/**
	 * 进入用户待批列表
	 * @author Administrator
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preUserPendingList", method = RequestMethod.GET)
	public String preUserPendingList(ModelMap model,@RequestParam(value="importFileId",required=false)Long importFileId) {
		model.addAttribute("importFileId", importFileId);
		return preUserPendingList;
	}

	
	/**
	 * 查询当前用户的审批
	 * @author  caijin-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/userPendingList",method=RequestMethod.POST)
	public ModelAndView userPendingList(@RequestParam(value="importFileId",required=false)Long importFileId){
		if(ObjUtil.isEmpty(importFileId)){
			return resultExcludeJson(PersistableUtil.createPagedInfo(0, page, new ArrayList<UserApprovalBean>(0)));
		}
		page.toParam().add(new AndEqParam("fileId", importFileId));
		page.toParam().add(new AndEqParam("userStatus", ImportApprovalStatus.PENDING));
		PagedInfo<UserApprovalBean> result = userApprovalFacade.findAllPage(page);
		
		
		return resultExcludeJson(result);
	}
	
	@RequestMapping(value="preUserPassTotal",method=RequestMethod.GET)
	public String preUserPassTotal(ModelMap model,@RequestParam(value="importFileId",required=false)Long importFileId){
		model.addAttribute("importFileId", importFileId);
		return preUserPassTotal;
	}
	/**
	 * 查询用户里的文件，如果文件中的用户状态以及是完成状态了。当前用户就是已经审批通过的用户
	 * @author caijin
	 * @return
	 * @return ModelAndView
	 * @date&time 2015-9-17 上午9:58:27
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/findUserPassTotal", method = RequestMethod.POST)
	public ModelAndView findUserPassTotal(@RequestParam(value="importFileId",required=false)Long importFileId) {
		
		PagedInfo<UserBean> result = userFacade.findByImportFileId(page,importFileId);
		return resultExcludeJson(result);
	}

	@RequestMapping(value="preUserUnPassTotal",method=RequestMethod.GET)
	public String preUserUnPassTotal(ModelMap model,@RequestParam(value="importFileId",required=false)Long importFileId){
		model.addAttribute("importFileId", importFileId);
		return preUserUnPassTotal;
	}
	
	/**
	 * 查询未通过数
	 * @author caijin
	 * @return
	 * @return ModelAndView
	 * @date&time 2015-9-17 上午9:58:27
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/findUserUnPassTotal", method = RequestMethod.POST)
	public ModelAndView findUserUnPassTotal(@RequestParam(value="importFileId",required=false)Long importFileId) {
		if(ObjUtil.isEmpty(importFileId)){
			return resultExcludeJson(PersistableUtil.createPagedInfo(0, page, new ArrayList<UserApprovalBean>(0)));
		}
		page.toParam().add(new AndEqParam("fileId", importFileId));
		page.toParam().add(new AndEqParam("userStatus", ImportApprovalStatus.REFUSAL));
		PagedInfo<UserApprovalBean> result = userApprovalFacade.findAllPage(page);
	
		return resultExcludeJson(result);
	}
	
	/**
	 * @author  caijin-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/findUserImportApproveList",method=RequestMethod.POST)
	public ModelAndView findUserImportApproveList(){
		PagedInfo<UserImportFileBean> result=userImportFileFacade.findApproveUserImportFilePage(page,UserSessionContext.getUserSession().getUserId());
		
		return resultExcludeJson(result);
	}
	
	/**
	 * 查询未通过数
	 * @author caijin
	 * @return
	 * @return ModelAndView
	 * @date&time 2015-9-17 上午9:58:27
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/passUsers", method = RequestMethod.POST)
	public ModelAndView passUsers(@RequestParam(value="userCommandIds",required=false)Long[] userCommandIds) {
		if(ObjUtil.isEmpty(userCommandIds)){
			return errorMsgJson("请您选择要审批的用户");
		}
		int num=userApprovalFacade.updatePass(userCommandIds);
		return successMsgJson("",num);
	}
	/**
	 * 查询未通过数
	 * @author caijin
	 * @return
	 * @return ModelAndView
	 * @date&time 2015-9-17 上午9:58:27
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/refusalUser", method = RequestMethod.POST)
	public ModelAndView refusalUser(@RequestParam(value="userCommandIds",required=false)Long[] userCommandIds,
			@RequestParam(value="explain",required=false)String explain) {
		if(ObjUtil.isEmpty(userCommandIds)){
			return errorMsgJson("请您选择要审批的用户");
		}
		int num=userApprovalFacade.updateRefusa(userCommandIds,explain);
		return successMsgJson("",num);
	}
	
}
