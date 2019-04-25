/**
 * Project Name:common
 * File Name:TaskInstanceAction.java
 * Package Name:org.csr.common.flow.action
 * Date:2014年3月14日下午1:48:35
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.entity.TaskNodeBean;
import org.csr.common.flow.facade.TaskNodeApprovalChainFacade;
import org.csr.common.flow.facade.TaskNodeFacade;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
@RequestMapping(value = "/taskNode")
public class TaskNodeAction extends BasisAction {

	@Resource
	TaskNodeFacade taskNodeFacade;
	@Resource
	TaskNodeApprovalChainFacade taskNodeApprovalChainFacade;

	/**
	 * preAdd: 查询已经下发的任务<br/>
	 * 
	 * @author caijin
	 * @param model
	 * @param functionPointCode
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = {"/{moduleName}/ajax/listByTaskTemp"}, method = RequestMethod.POST)
	public ModelAndView listByTaskTemp(ModelMap model,@RequestParam(value="taskTempId") Long taskTempId) {
		List<TaskNodeBean> taskNodeBeanList =taskNodeFacade.listByTaskTemp(taskTempId);
//		List<TaskNode> taskNodes = taskNodeFacade.findByTaskTempIdNotStartEnd(taskTempId);
//		List<Long> taskIds = ObjUtil.arrayTransforList(taskNodes);
//		final List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainFacade.findByTaskNodeIds(taskIds);
//
//		List<TaskNodeBean> taskNodeBeanList = taskNodeFacade.toListBeans(taskNodes, new SetBean<TaskNode>() {
//			public VOBase<Long> setValue(TaskNode doMain) {
//				if(doMain instanceof EndNode){
//					return null;
//				}
//				ArrayList<TaskNodeApprovalChain> taskNodeChain = new ArrayList<TaskNodeApprovalChain>();
//				for (TaskNodeApprovalChain chain : taskNodeApprovalChainList) {
//					if (chain.getTaskNode().getId().equals(doMain.getId())) {
//						taskNodeChain.add(chain);
//					}
//				}
//				TaskNodeBean taskBean = TaskNodeBean.wrapBean(doMain);
//				taskBean.setChainId(ObjUtil.arrayTransforString(taskNodeChain,
//						new ToString<TaskNodeApprovalChain>() {
//							@Override
//							public String getValue(TaskNodeApprovalChain obj) {
//								return ObjUtil.toString(obj.getId());
//							}
//						}));
//				taskBean.setChainName(ObjUtil.arrayTransforString(taskNodeChain,
//						new ToString<TaskNodeApprovalChain>() {
//							@Override
//							public String getValue(TaskNodeApprovalChain obj) {
//								if (ExamApproverType.People.equals(obj.getType())) {
//									return obj.getApprover().getLoginName();
//								} else if (ExamApproverType.Releation.equals(obj.getType())) {
//									return DictionaryUtil.getDictName("approvalChainType", obj.getApproverReleation().toString());
//								}
//								return "";
//							}
//
//						}));
//				return taskBean;
//			}
//		});
		return resultExcludeJson(taskNodeBeanList);
	}

}
