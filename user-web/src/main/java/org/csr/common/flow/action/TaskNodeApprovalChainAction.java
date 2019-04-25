/**
 * Project Name:common
 * File Name:TaskInstanceAction.java
 * Package Name:org.csr.common.flow.action
 * Date:2014年3月14日下午1:48:35
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormApproverType;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.entity.TaskNodeApprovalChainBean;
import org.csr.common.flow.facade.TaskNodeApprovalChainFacade;
import org.csr.common.flow.facade.TaskNodeFacade;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.UserFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName: TaskInstanceAction.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日下午1:48:35 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"/taskNodeApprovalChain"})
public class TaskNodeApprovalChainAction extends BasisAction {

	final String preAdd = "flow/taskNodeApprovalChain/taskNodeApprovalChainAdd";
	final String winPeople = "flow/taskNodeApprovalChain/winPeople";
	final String winReleation = "flow/taskNodeApprovalChain/winReleation";

	@Resource
	TaskNodeApprovalChainFacade taskNodeApprovalChainFacade;
	@Resource
	TaskNodeFacade taskNodeFacade;
	@Resource
	UserFacade userFacade;
	/**
	 * @author caijin
	 * @param model
	 * @param taskNodeId
	 * @param winId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/{moduleName}/preAdd", method = RequestMethod.GET)
	public String preAdd(@PathVariable("moduleName")String moduleName,
			@RequestParam(value="taskNodeId") Long taskNodeId, 
			@RequestParam(value="parentWinId",required=false)String parentWinId, 
			@RequestParam(value="winId",required=false)String winId) {
		setRequest("taskNodeId", taskNodeId);
		setRequest("winId", winId);
		setRequest("parentWinId", parentWinId);
		setRequest("moduleName", moduleName);
		setRequest("taskNode", taskNodeFacade.findById(taskNodeId));
		return preAdd;
	}

	/**
	 * 根据节点查询
	 * 
	 * @author caijin
	 * @param taskNodeId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/{moduleName}/ajax/listByTaskNode", method = RequestMethod.POST)
	public ModelAndView listByTaskNode(@RequestParam(value="taskNodeId")Long taskNodeId) {
		List<TaskNodeApprovalChainBean> chainBeanList=taskNodeApprovalChainFacade.listByTaskNode(taskNodeId);
//		List<TaskNodeApprovalChain> chainList = taskNodeApprovalChainFacade.findByTaskNodeId(taskNodeId,
//				true,null);
//		List<TaskNodeApprovalChainBean> chainBeanList = taskNodeApprovalChainFacade.toListBeans(chainList,
//				new SetBean<TaskNodeApprovalChain>() {
//					@Override
//					public VOBase<Long> setValue(TaskNodeApprovalChain doMain) {
//						return TaskNodeApprovalChainBean.toBean(doMain);
//					}
//				});
		return resultExcludeJson(chainBeanList);
	}

	/**
	 * 修改或创建，新的节点审批关系数据
	 * 
	 * @author caijin
	 * @param taskNodeApprovalChain
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/{moduleName}/ajax/saveTaskNodeApprovalChain", method = RequestMethod.POST)
	public ModelAndView saveTaskNodeApprovalChain(TaskNode taskNode, 
			@RequestParam(value="taskNodeApprovalChain",required=false)String[] taskNodeApprovalChain) {
		// 整理数据。
		if (ObjUtil.isEmpty(taskNodeApprovalChain)) {
			Exceptions.service("", "请您选择要保存的关系");
		}
		List<TaskNodeApprovalChain> taskNodeApprovalChainList = new ArrayList<TaskNodeApprovalChain>();
		try {
			for (int i = 0; i < taskNodeApprovalChain.length; i++) {
				TaskNodeApprovalChain chain = new TaskNodeApprovalChain();
				String[] chains = taskNodeApprovalChain[i].split("_");
				chain.setType(ObjUtil.toInteger(chains[0]));
				chain.setTaskNode(new TaskNode(ObjUtil.toLong(chains[1])));

				if (FormApproverType.People.equals(chain.getType())) {
					ObjUtil.toLong(chains[2]);				
					chain.setApprover(new User(ObjUtil.toLong(chains[2])));
				} else if (FormApproverType.Releation.equals(chain.getType())) {
					chain.setApproverReleation(ObjUtil.toInteger(chains[2]));
				}
				taskNodeApprovalChainList.add(chain);
			}
		} catch (Exception e) {
			Exceptions.service("", "您的数据格式有问题！");
		}
		taskNodeApprovalChainFacade.saveTaskNodeApprovalChain(taskNode, taskNodeApprovalChainList);
		return successMsgJson("");
	}

	@RequestMapping(value = "/winPeople", method = RequestMethod.GET)
	public String winPeople(ModelMap model,
			@RequestParam(value="taskNodeId") Long taskNodeId, 
			@RequestParam(value="parentWinId",required=false)String parentWinId, 
			@RequestParam(value="winId",required=false)String winId) {
		model.addAttribute("taskNodeId", taskNodeId);
		model.addAttribute("winId", winId);
		model.addAttribute("parentWinId", parentWinId);

		return winPeople;
	}

	/**
	 * 查询用户，用来添加审批者。
	 * @return
	 */
	@RequestMapping(value = "ajax/findUserList", method = RequestMethod.POST)
	public ModelAndView findUserList(@RequestParam(value="loginName",required=false) String loginName, 
			@RequestParam(value="name",required=false) String name,
			@RequestParam(value="taskNodeId",required=false) Long agenciesId) {
		
		PagedInfo<UserBean> listPage = userFacade.findListPage(page, loginName, name, null, null, ObjUtil.toList(agenciesId));
		return resultExcludeJson(listPage);
		
	}
	
	@RequestMapping(value = "/winReleation", method = RequestMethod.GET)
	public String winReleation(ModelMap model, 
			@RequestParam(value="taskNodeId") Long taskNodeId, 
			@RequestParam(value="parentWinId",required=false)String parentWinId, 
			@RequestParam(value="winId",required=false)String winId) {
		model.addAttribute("taskNodeId", taskNodeId);
		model.addAttribute("winId", winId);
		model.addAttribute("parentWinId", parentWinId);
		return winReleation;
	}
}
