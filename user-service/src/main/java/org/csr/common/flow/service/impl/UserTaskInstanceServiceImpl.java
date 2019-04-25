/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.dao.UserTaskInstanceDao;
import org.csr.common.flow.domain.FlowStrategy;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.entity.FormBean;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.flow.service.FormSerivce;
import org.csr.common.flow.service.HistoryFormService;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.flow.service.TaskNodeService;
import org.csr.common.flow.service.TaskTempService;
import org.csr.common.flow.service.UserTaskInstanceService;
import org.csr.common.flow.support.FormSerivceFactory;
import org.csr.common.flow.support.TaskNodeHandle;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.param.AndNeParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName: TaskTempService.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日下午2:04:04 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Service("userTaskInstanceService")
public class UserTaskInstanceServiceImpl extends SimpleBasisService<UserTaskInstance, Long> implements
		UserTaskInstanceService {
	@Resource
	FormSerivceFactory formSerivceFactory;
	@Resource
	UserTaskInstanceDao userTaskInstanceDao;
	// 过程服务
	@Resource
	HistoryFormService historyFormService;
	@Resource
	TaskNodeService taskNodeService;
	@Resource
	TaskTempService taskTempService;
	@Resource
	TaskNodeApprovalChainService taskNodeApprovalChainService;

	@Override
	public BaseDao<UserTaskInstance, Long> getDao() {
		return userTaskInstanceDao;
	}

	@Override
	public PagedInfo<UserTaskInstance> findPageByTaskTempTaskNodeNameUserId(Page page, List<Long> taskTempIds,String taskNodeName, Long userId) {
		if(ObjUtil.isEmpty(taskTempIds) || ObjUtil.isEmpty(userId)){
			return PersistableUtil.createPagedInfo(0, page, new ArrayList<UserTaskInstance>());
		}
		// 如果不存在处理节点，直接返回空数组/查询用能是否能够处理节点
		List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainService.findPeopleByTaskTempIdTaskNode(taskTempIds,taskNodeName,userId);
		List<Long> chainIds = new ArrayList<Long>(PersistableUtil.arrayTransforList(taskNodeApprovalChainList,
				new ToValue<TaskNodeApprovalChain, Long>() {
					@Override
					public Long getValue(TaskNodeApprovalChain obj) {
						TaskNode node = obj.getTaskNode();
						if (ObjUtil.isNotEmpty(node)) {
							return node.getId();
						}
						return null;
					}
				}));

		// 查询节点
		List<TaskNodeApprovalChain> releationChain = taskNodeApprovalChainService.findReleationByTaskTempIdTaskNode(taskTempIds,taskNodeName);
		PagedInfo<UserTaskInstance> userTaskInstanceList = userTaskInstanceDao.findPageByTaskTempIdAndUserIdsAndCurrentIds(page,taskTempIds,releationChain,chainIds);
		return userTaskInstanceList;
	}


	@Override
	public List<UserTaskInstance> findListByTaskTempTaskNodeNameUserId(List<Long> taskTempIds,List<Long> formIds,String taskNodeName, Long userId) {
		if(ObjUtil.isEmpty(taskTempIds) || ObjUtil.isEmpty(userId)){
			return new ArrayList<UserTaskInstance>();
		}
		// 如果不存在处理节点，直接返回空数组/查询用能是否能够处理节点
		List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainService.findPeopleByTaskTempIdTaskNode(taskTempIds,taskNodeName,userId);
		List<Long> chainIds = new ArrayList<Long>(PersistableUtil.arrayTransforList(taskNodeApprovalChainList,
				new ToValue<TaskNodeApprovalChain, Long>() {
					@Override
					public Long getValue(TaskNodeApprovalChain obj) {
						TaskNode node = obj.getTaskNode();
						if (ObjUtil.isNotEmpty(node)) {
							return node.getId();
						}
						return null;
					}
				}));

		// 查询节点
		List<TaskNodeApprovalChain> releationChain = taskNodeApprovalChainService.findReleationByTaskTempIdTaskNode(taskTempIds,taskNodeName);
		List<UserTaskInstance> userTaskInstanceList = userTaskInstanceDao.findListByTaskTempIdAndUserIdsAndCurrentIds(taskTempIds,formIds,releationChain,chainIds);
		return userTaskInstanceList;
	}

	
	@Override
	public Long countByTaskTempIdAndUserIdsAndCurrentIds(List<Long> taskTempIds,String taskNodeName, Long userId) {
		if(ObjUtil.isEmpty(taskTempIds) || ObjUtil.isEmpty(userId)){
			return 0l;
		}
		// 如果不存在处理节点，直接返回空数组/查询用能是否能够处理节点
		List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainService.findPeopleByTaskTempIdTaskNode(taskTempIds,taskNodeName,userId);
		List<Long> chainIds = new ArrayList<Long>(PersistableUtil.arrayTransforList(taskNodeApprovalChainList,new ToValue<TaskNodeApprovalChain, Long>() {
			@Override
			public Long getValue(TaskNodeApprovalChain obj) {
				TaskNode node = obj.getTaskNode();
				if (ObjUtil.isNotEmpty(node)) {
					return node.getId();
				}
				return null;
			}
		}));
		// 查询节点
		List<TaskNodeApprovalChain> releationChain = taskNodeApprovalChainService.findReleationByTaskTempIdTaskNode(taskTempIds,taskNodeName);
		return userTaskInstanceDao.countByTaskTempIdAndUserIdsAndCurrentIds(taskTempIds,releationChain,chainIds);
	}
	
	@Override
	public List<Long> findIdApproveList(Long userId, List<Long> taskTempIds,String taskNodeName, List<Param> param) {
		// 如果不存在处理节点，直接返回空数组
		List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainService.findPeopleByTaskTempIdTaskNode(taskTempIds,taskNodeName,userId);
		List<Long> chainIds = new ArrayList<Long>(PersistableUtil.arrayTransforList(taskNodeApprovalChainList,
				new ToValue<TaskNodeApprovalChain, Long>() {
					@Override
					public Long getValue(TaskNodeApprovalChain obj) {
						TaskNode node = obj.getTaskNode();
						if (ObjUtil.isNotEmpty(node)) {
							return node.getId();
						}
						return null;
					}

				}));

		// 查询节点
		List<TaskNodeApprovalChain> releationChain = taskNodeApprovalChainService.findReleationByTaskTempIdTaskNode(taskTempIds,taskNodeName);
		return userTaskInstanceDao.findIdApproveList(taskTempIds,releationChain,chainIds,param);
	}
	
	@Override
	public UserTaskInstance save(UserTaskInstance taskUserNode) {
		return userTaskInstanceDao.save(taskUserNode);
	}

	@Override
	public UserTaskInstance startFolw(Long userTaskInstanceId, Form form) {
		UserTaskInstance userTaskInstance = userTaskInstanceDao.findById(userTaskInstanceId);
		if (ObjUtil.isEmpty(userTaskInstance)) {
			Exceptions.service("2006010", "您的用户任务实例为空");
		}
		return startFolw(userTaskInstance, form);
	}

	@Override
	public UserTaskInstance startFolw(UserTaskInstance userTaskInstance, Form form) {
		if (ObjUtil.isEmpty(userTaskInstance)) {
			Exceptions.service("2006010", "您的用户任务实例为空");
		}
		TaskNode taskNode = userTaskInstance.getCurrent();
		if (taskNode.getNodeType().equals("StartNode") || taskNode.getNodeType().equals("RejectNode")) {
			userTaskInstance.setCurrent(taskNode.getToNode());
			userTaskInstance.setFormObj(form);
			userTaskInstance.setFormId(form.getId());
			userTaskInstance.setClassName(form.getClass().getName());
			// form.setUserTaskInstance(userTaskInstance);
			if (form instanceof FormBean) {
				form.setUserTaskInstance(toBean(userTaskInstance));
			} else {
				form.setUserTaskInstance(userTaskInstance);
			}
			userTaskInstanceDao.update(userTaskInstance);
			//记录历史
			historyFormService.save(userTaskInstance.getId(),taskNode.getToNode().getName(),form);
		} else {
			Exceptions.service("2006012", "您的节点已经启动");
		}
		FormSerivce formSerivce = formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
		formSerivce.handleFolw(userTaskInstance);
		return userTaskInstance;
	}

	@Override
	public UserTaskInstance nextFolw(Form form, Long userTaskInstanceId, String opinion, TaskNodeHandle handle) {
		UserTaskInstance userTaskInstance = userTaskInstanceDao.findById(userTaskInstanceId);
		if (ObjUtil.isEmpty(userTaskInstance)) {
			Exceptions.service("2006007", "您的用户任务实例不存在");
		}
		// 需要判断当前权限,当前用户是否有权限验证
		if (!taskNodeApprovalChainService.verificationUserTaskInstance(UserSessionContext.getUserSession().getUserId(), userTaskInstance)) {
			Exceptions.service("2006009", "您没权限操作流程节点");
		}
		TaskNode taskNode = userTaskInstance.getCurrent();
		if (taskNode.getNodeType().equals("EndNode")) {
			Exceptions.service("2006008", "当前的节点为结束节点");
		}
		// 直接修改状态
		userTaskInstance.setFormObj(form);
		userTaskInstance.setFormId(form.getId());
		userTaskInstance.setCurrent(taskNode.getToNode());
		userTaskInstance.setOpinion(opinion);
		userTaskInstanceDao.update(userTaskInstance);
		// 记录操作流程
		historyFormService.save(userTaskInstance.getId(),opinion, form);
		// 处理流程
		FormSerivce formSerivce = formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
		formSerivce.handleFolw(userTaskInstance);
		// 流程结束回调函数
		if (taskNode.getToNode().getNodeType().equals("EndNode")) {
			if (ObjUtil.isNotEmpty(handle)) {
				handle.end(userTaskInstance, taskNode.getToNode());
			}
		} else {
			if (ObjUtil.isNotEmpty(handle)) {
				handle.process(userTaskInstance, taskNode);
			}
		}
		return userTaskInstance;
	}

	@Override
	public UserTaskInstance backFolw(Long userTaskInstanceId, String opinion, TaskNodeHandle handle) {

		UserTaskInstance userTaskInstance = userTaskInstanceDao.findById(userTaskInstanceId);
		// 需要判断当前处理人是否有权限处理
		if (!taskNodeApprovalChainService.verificationUserTaskInstance(UserSessionContext.getUserSession()
				.getUserId(), userTaskInstance)) {
			Exceptions.service("2006010", "您没权限操作流程节点");
		}
		TaskNode taskNode = userTaskInstance.getCurrent();
		userTaskInstance.setOpinion(opinion);
		userTaskInstance.setCurrent(taskNode.getRejectNode());
		userTaskInstanceDao.update(userTaskInstance);
		// 记录操作流程
		historyFormService.save(userTaskInstance.getId(),opinion, userTaskInstance.getFormObj());
		// 结束回调函数
		if (ObjUtil.isNotEmpty(handle)) {
			handle.process(userTaskInstance, taskNode);
		}
		// 处理流程
		FormSerivce formSerivce = formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
		formSerivce.handleFolw(userTaskInstance);
		return userTaskInstance;
	}

	@Override
	public int deleteByTaskInstanceId(Long taskInstanceId) {
		List<Long> ids = findByByTaskInstanceId(taskInstanceId);
		// 然后在删除，用户实例表中的数据
		return userTaskInstanceDao.deleteByTaskInstanceIds(ids);
	}

	@Override
	public int deleteById(Long id) {
		List<Long> ids = new ArrayList<Long>();
		// 先删除，过程表中的
		ids.add(id);
		userTaskInstanceDao.deleteById(id);
		return ids.size();
	}

	/**
	 * 查询当前流程模板
	 * 
	 * @author caijin
	 * @param isEndNode
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	@Override
	public List<UserTaskInstance> findByTaskTempId(Long taskTempId, List<Param> paramList, boolean isEndNode) {
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<UserTaskInstance>(0);
		}
		if (ObjUtil.isEmpty(paramList)) {
			paramList = new ArrayList<Param>();
		}
		paramList.add(new AndInParam("taskTempId", taskTempId));
		if (!isEndNode) {
			paramList.add(new AndNeParam("current.nodeType", "EndNode"));
		}
		return userTaskInstanceDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}

	/**
	 * 查询当前流程模板
	 * 
	 * @author caijin
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	private List<Long> findByByTaskInstanceId(Long taskInstanceId) {
		if (ObjUtil.isEmpty(taskInstanceId)) {
			return new ArrayList<Long>(0);
		}
		return userTaskInstanceDao.findIdsByByTaskInstanceId(taskInstanceId);
	}

	@Override
	public String validationProcess(Long[] formIds) {
		Long count = userTaskInstanceDao.countParam(new AndInParam("formId", Arrays.asList(formIds)));
		if (ObjUtil.isNotEmpty(count) && count > 0) {
			return "您的表单数据已存在流程中,不能重复提交审批！";
		} else {
			return null;
		}
	}

	@Override
	public UserTaskInstance findByFormId(Long formId) {
		return userTaskInstanceDao.existParam(new AndEqParam("formId", formId));
	}

	
	@Override
	public PagedInfo<UserTaskInstance> findApprovePage(Page page, List<Long> taskTempIds,String taskNodeName, Long userId) {
		
		return findPageByTaskTempTaskNodeNameUserId(page, taskTempIds,taskNodeName, UserSessionContext.getUserSession().getUserId());
	}
	public static UserTaskInstanceBean toBean(UserTaskInstance doMain) {
		UserTaskInstanceBean bean = new UserTaskInstanceBean();
		bean.setId(doMain.getId());
		bean.setCurrentId(doMain.getCurrent().getId());
		bean.setCurrentName(doMain.getCurrent().getName());
		bean.setIsEdit(doMain.getCurrent().getIsEdit());
		bean.setTaskInstanceId(doMain.getTaskInstance().getId());
		bean.setTaskInstanceName(doMain.getTaskInstance().getName());
		bean.setClassName(doMain.getClassName());
		bean.setOpinion(doMain.getOpinion());
		return bean;
	}
}
