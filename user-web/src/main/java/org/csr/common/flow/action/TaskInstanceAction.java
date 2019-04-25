/**
 * File Name:TaskInstanceAction.java
 * Project Name:common
 * Package Name:org.csr.common.flow.action
 * Date:2014年3月14日下午1:48:35
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.HistoryFormBean;
import org.csr.common.flow.entity.TaskNodeBean;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.flow.facade.HistoryFormFacade;
import org.csr.common.flow.facade.TaskInstanceFacade;
import org.csr.common.flow.facade.TaskNodeFacade;
import org.csr.common.user.facade.UserTaskInstanceFacade;
import org.csr.core.exception.Exceptions;
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
@RequestMapping(value = "/taskInstance")
public class TaskInstanceAction extends BasisAction {

	final String preProcessFlow = "flow/taskInstance/processFlow";
	final String batchProcessFlow = "flow/taskInstance/batchProcessFlow";
	final String preViewApprovalDetail="flow/taskInstance/viewApprovalDetail";
//	@Resource
//	private TaskTempService taskTempService;
//	@Resource
//	FormSerivceFactory formSerivceFactory;
	@Resource
	private TaskInstanceFacade taskInstanceFacade;
	@Resource
	private HistoryFormFacade historyFormFacade;
	@Resource
	private UserTaskInstanceFacade userTaskInstanceFacade;
	@Resource
	private TaskNodeFacade taskNodeFacade;

	/**
	 * 进入到，流程审批页面
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/{moduleName}/preProcessFlow", method = RequestMethod.GET)
	public String preProcessFlow(ModelMap model,@RequestParam(value="winParentId",required=false)String winParentId,
			@RequestParam(value="userTaskInstanceId")Long userTaskInstanceId) {
		UserTaskInstanceBean userTaskInstanceBean = userTaskInstanceFacade.findById(userTaskInstanceId);
		UserTaskInstance userTaskInstance=userTaskInstanceFacade.wrapDomain(userTaskInstanceBean);
		if(ObjUtil.isEmpty(userTaskInstance)){
			Exceptions.service("", "您数据没在流程中，无法审批");
		}
//		Form form=userTaskInstance.getFormObj();
//		FormSerivce formSerivce=formSerivceFactory.registrationForm(form);
//		//当需要验证的时候，前期操作
//		formSerivce.beforeForm(userTaskInstance);
		
		TaskNode taskNode = userTaskInstance.getCurrent();
		TaskNode toNode = taskNode.getToNode();
		TaskNode rejectNode = taskNode.getRejectNode();
		
		model.addAttribute("isTo", !taskNode.getId().equals(toNode.getId()));
		model.addAttribute("isreject",  !taskNode.getId().equals(rejectNode.getId()));
		model.addAttribute("userTaskInstance", userTaskInstance);
		model.addAttribute("taskNode", taskNode);
		model.addAttribute("toNode", toNode);
		model.addAttribute("rejectNode", rejectNode);
		model.addAttribute("winParentId", winParentId);
		return preProcessFlow;
	}
	/**
	 * 进入到，流程审批页面查看详细
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/{moduleName}/preViewApprovalDetail", method = RequestMethod.GET)
	public String preViewApprovalDetail(ModelMap model,@PathVariable String moduleName,
			@RequestParam(value="winParentId",required=false)String winParentId,
			@RequestParam(value="userTaskInstanceId")Long userTaskInstanceId) {
//		UserTaskInstance userTaskInstance = userTaskInstanceFacade.findById(userTaskInstanceId);
		UserTaskInstanceBean userTaskInstanceBean = userTaskInstanceFacade.findById(userTaskInstanceId);
		UserTaskInstance userTaskInstance=userTaskInstanceFacade.wrapDomain(userTaskInstanceBean);
		if(ObjUtil.isEmpty(userTaskInstance)){
			Exceptions.service("", "您数据没在流程中，无法审批");
		}
//		Form form=userTaskInstance.getFormObj();
//		FormSerivce formSerivce=formSerivceFactory.registrationForm(form);
		//当需要验证的时候，前期操作
//		formSerivce.beforeForm(userTaskInstance);
		
		TaskNode taskNode = userTaskInstance.getCurrent();
		TaskNode toNode = taskNode.getToNode();
		TaskNode rejectNode = taskNode.getRejectNode();
		
		model.addAttribute("isTo", !taskNode.getId().equals(toNode.getId()));
		model.addAttribute("isreject",  !taskNode.getId().equals(rejectNode.getId()));
		model.addAttribute("userTaskInstance", userTaskInstance);
		model.addAttribute("taskNode", taskNode);
		model.addAttribute("toNode", toNode);
		model.addAttribute("rejectNode", rejectNode);
		model.addAttribute("winParentId", winParentId);
		model.addAttribute("moduleName", moduleName);
		return preViewApprovalDetail;
	}
	/**
	 * 进入到，流程审批页面
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "batchProcessFlow", method = RequestMethod.GET)
	public String batchProcessFlow(ModelMap model,
			@RequestParam(value="winParentId",required=false)String winParentId,
			@RequestParam(value="taskNodeid",required=false)Long taskNodeid) {
		TaskNodeBean taskNodeBean = taskNodeFacade.findById(taskNodeid);
		TaskNode taskNode=taskNodeFacade.wrapDomain(taskNodeBean);
		TaskNode toNode = taskNode.getToNode();
		TaskNode rejectNode = taskNode.getRejectNode();
		
		model.addAttribute("isTo", !taskNode.getId().equals(toNode.getId()));
		model.addAttribute("isreject",  !taskNode.getId().equals(rejectNode.getId()));
		model.addAttribute("taskNode", taskNode);
		model.addAttribute("toNode", toNode);
		model.addAttribute("rejectNode", rejectNode);
		model.addAttribute("winParentId", winParentId);
		return batchProcessFlow;
	}
	
	/**
	 * 查询。当前的用户的审批流程记录
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "{fncode}/ajax/listTaskProcessByUserTaskInstanceId", method = RequestMethod.POST)
	public ModelAndView listTaskProcessByUserTaskInstanceId(@RequestParam(value="userTaskInstanceId")Long userTaskInstanceId) {
		List<HistoryFormBean> paged=historyFormFacade.listTaskProcessByUserTaskInstanceId(userTaskInstanceId);
//		List<HistoryForm> taskProcessList = historyFormFacade.findByUserTaskInstanceId(userTaskInstanceId,ObjUtil.toList("RejectNode"));
//		List<HistoryFormBean> paged = historyFormFacade.toListBeans(taskProcessList, new SetBean<HistoryForm>(){
//			@Override
//			public VOBase<Long> setValue(HistoryForm doMain) {
//				return HistoryFormBean.toBean(doMain);
//			}
//		});
		return resultExcludeJson(paged);
	}
	
	/**
	 * 查询。当前的用户的审批流程记录
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/validationProcess", method = RequestMethod.POST)
	public ModelAndView validationProcess(@RequestParam(value="formId",required=false)Long[] formId) {
		String isProcess = userTaskInstanceFacade.validationProcess(formId);
		if(ObjUtil.isNotBlank(isProcess)){
			Exceptions.service("", isProcess);
		}
		return successMsgJson("");
	}
	/**
	 * 批准流程，到指定的下一节点。
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @param opinion
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "{fncode}/ajax/taskPass", method = RequestMethod.POST)
	public ModelAndView taskPass(@RequestParam(value="userTaskInstanceId",required=false)Long[] userTaskInstanceId,@RequestParam(value="opinion",required=false)String opinion) {
		taskInstanceFacade.taskPass(userTaskInstanceId,opinion);
		
//		taskInstanceFacade.updateTaskPass(userTaskInstanceId,opinion,new BusinessStorage() {
//			@Override
//			public boolean save(UserTaskInstance userTaskInstance) {
//				FormSerivce formSerivce=formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
//				return formSerivce.saveForm(userTaskInstance);
//			}
//		});
		return successMsgJson("");
	}
	
	/**
	 * 批准流程，到指定的下一节点。
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @param opinion
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "{fncode}/ajax/taskRebut", method = RequestMethod.POST)
	public ModelAndView taskRebut(@RequestParam(value="userTaskInstanceId",required=false)Long[] userTaskInstanceId,
			@RequestParam(value="opinion",required=false)String opinion) {
		taskInstanceFacade.updateTaskRebut(userTaskInstanceId,opinion);
		return successMsgJson("");
	}
	/**
	 * 关闭流程。
	 * @author caijin
	 * @param model
	 * @param userTaskInstanceId
	 * @param opinion
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "{fncode}/ajax/closeTask", method = RequestMethod.POST)
	public ModelAndView closeTask(@RequestParam(value="userTaskInstanceId")Long userTaskInstanceId) {
		taskInstanceFacade.updateCloseTask(userTaskInstanceId);
		return successMsgJson("");
	}
}
