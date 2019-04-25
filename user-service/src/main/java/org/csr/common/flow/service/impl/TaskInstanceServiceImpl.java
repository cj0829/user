/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service.impl;

import javax.annotation.Resource;

import org.csr.common.flow.constant.TaskInstanceStatus;
import org.csr.common.flow.dao.TaskInstanceDao;
import org.csr.common.flow.domain.TaskInstance;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskTemp;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.entity.FormBean;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.flow.service.FormSerivce;
import org.csr.common.flow.service.HistoryFormService;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.flow.service.TaskNodeService;
import org.csr.common.flow.service.TaskTempService;
import org.csr.common.flow.service.UserTaskInstanceService;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.common.flow.support.FormSerivceFactory;
import org.csr.common.flow.support.TaskNodeHandleImpl;
import org.csr.common.user.domain.User;
import org.csr.common.user.service.UserService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
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
@Service("taskInstanceService")
public class TaskInstanceServiceImpl extends SimpleBasisService<TaskInstance, Long> implements
		TaskInstanceService {
	@Resource
	FormSerivceFactory formSerivceFactory;
	@Resource
	TaskInstanceDao taskInstanceDao;
	@Resource
	HistoryFormService historyFormService;
	@Resource
	UserService userService;
	@Resource
	TaskTempService taskTempService;
	@Resource
	TaskNodeService taskNodeService;
	@Resource
	UserTaskInstanceService userTaskInstanceService;

	@Override
	public BaseDao<TaskInstance, Long> getDao() {
		return taskInstanceDao;
	}

	@Override
	public TaskInstance createTaskInstance(Long taskTempId, Form form, String name, Long[] userIds,
			boolean isStartFolw) {
		if (ObjUtil.isEmpty(taskTempId)) {
			Exceptions.service("", "请您选择要发布的任务");
		}
		if (ObjUtil.isBlank(name)) {
			Exceptions.service("", "请您填写发布任务的名称");
		}
		if (ObjUtil.isEmpty(userIds)) {
			Exceptions.service("", "请您选择上报的用户");
		}
		if((ObjUtil.isEmpty(form.getOperType()))){
			Exceptions.service("", "表单数据必须有操作方式");
		}

		TaskTemp taskTemp = taskTempService.findById(taskTempId);
		if (ObjUtil.isEmpty(taskTemp)) {
			Exceptions.service("", "");
		}
		TaskInstance taskInstance = new TaskInstance();
		// 设置流程开始状态
		taskInstance.setName(name);
		taskInstance.setTaskInstanceStatus(TaskInstanceStatus.ACTIVATING);
		taskInstance.setTaskTemp(taskTemp);
		taskInstance.setTotal(userIds.length);
		taskInstance.setCompletionNumber(0);
		taskInstanceDao.save(taskInstance);
		for (Long userId : userIds) {
			User user = userService.findById(userId);
			UserTaskInstance userTaskInstance = new UserTaskInstance(user, taskTemp.getStart(), taskInstance);
			userTaskInstance.setId(userTaskInstanceService.nextSequence());
			userTaskInstance.setTaskTempId(taskTemp.getId());
			//设置form
			if(form instanceof FormBean){
				form.setUserTaskInstance(UserTaskInstanceBean.toBean(userTaskInstance));
			}else{
				form.setUserTaskInstance(userTaskInstance);
			}
			userTaskInstance.setFormOperType(form.getOperType());
			userTaskInstance.setFormId(form.getId());
			userTaskInstance.setFormObj(form);
			userTaskInstance.setClassName(form.getClass().getName());
			userTaskInstance.setUserTaskInstanceStatus(true);
			userTaskInstanceService.save(userTaskInstance);
			//生产用户流程后表单的处理
			FormSerivce formSerivce=formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
			formSerivce.createTaskInstanceAfter(userTaskInstance);
			historyFormService.save(userTaskInstance.getId(),taskTemp.getStart().getName() ,form);
			if (isStartFolw) {
				userTaskInstanceService.startFolw(userTaskInstance,userTaskInstance.getFormObj());
			}
		}
		return taskInstance;
	}
	
	@Override
	public TaskInstance createTaskInstance(Long taskTempId, Form form,Integer operType,String name, Long[] userIds,boolean isStartFolw) {
		if (ObjUtil.isEmpty(taskTempId)) {
			Exceptions.service("", "请您选择要发布的任务");
		}
		if (ObjUtil.isBlank(name)) {
			Exceptions.service("", "请您填写发布任务的名称");
		}
		if (ObjUtil.isEmpty(userIds)) {
			Exceptions.service("", "请您选择上报的用户");
		}
		if (ObjUtil.isEmpty(operType)) {
			Exceptions.service("", "请您选择上报的用户");
		}
		TaskTemp taskTemp = taskTempService.findById(taskTempId);
		if (ObjUtil.isEmpty(taskTemp)) {
			Exceptions.service("", "");
		}
		TaskInstance taskInstance = new TaskInstance();
		// 设置流程开始状态
		taskInstance.setName(name);
		taskInstance.setTaskInstanceStatus(TaskInstanceStatus.ACTIVATING);
		taskInstance.setTaskTemp(taskTemp);
		taskInstance.setTotal(userIds.length);
		taskInstance.setCompletionNumber(0);
		taskInstanceDao.save(taskInstance);
		for (Long userId : userIds) {
			User user = userService.findById(userId);
			UserTaskInstance userTaskInstance = new UserTaskInstance(user, taskTemp.getStart(), taskInstance);
			userTaskInstance.setId(userTaskInstanceService.nextSequence());
			userTaskInstance.setTaskTempId(taskTemp.getId());
			//设置form
			if(form instanceof FormBean){
				form.setUserTaskInstance(UserTaskInstanceServiceImpl.toBean(userTaskInstance));
			}else{
				form.setUserTaskInstance(userTaskInstance);
			}
			userTaskInstance.setFormOperType(operType);
			userTaskInstance.setFormId(form.getId());
			userTaskInstance.setFormObj(form);
			userTaskInstance.setClassName(form.getClass().getName());
			userTaskInstance.setUserTaskInstanceStatus(true);
			userTaskInstanceService.save(userTaskInstance);
			//生产用户流程后表单的处理
			FormSerivce formSerivce=formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
			formSerivce.createTaskInstanceAfter(userTaskInstance);
			historyFormService.save(userTaskInstance.getId(),taskTemp.getStart().getName() ,form);
			if (isStartFolw) {
				userTaskInstanceService.startFolw(userTaskInstance,userTaskInstance.getFormObj());
			}
		}
		return taskInstance;
	}

	@Override
	public int updateTaskPass(Long[] userTaskInstanceIds, final String opinion,
			final BusinessStorage businessStorage) {
		for (Long userTaskInstanceId : userTaskInstanceIds) {
			UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
			if (ObjUtil.isEmpty(userTaskInstance)) {
				Exceptions.service("", "您要审批的流程不存在，或已结束");
			}
			final TaskInstance taskInstance = userTaskInstance.getTaskInstance();
			//需要重新设置formid，防止其他地方设置。formid、对formid造成变化
			userTaskInstance.getFormObj().setId(userTaskInstance.getFormId());
			
			userTaskInstanceService.nextFolw(userTaskInstance.getFormObj(), userTaskInstance.getId(), opinion,
				new TaskNodeHandleImpl() {
					@Override
					public void end(UserTaskInstance userTaskInstance, TaskNode toNode) {
						if (ObjUtil.isEmpty(taskInstance.getCompletionNumber())) {
							taskInstance.setCompletionNumber(0);
						}
						taskInstance.setCompletionNumber(taskInstance.getCompletionNumber() + 1);
						if (ObjUtil.isEmpty(taskInstance.getExceptionNumber())) {
							taskInstance.setExceptionNumber(0);
						}
						if (!businessStorage.save(userTaskInstance)) {
							taskInstance.setExceptionNumber(taskInstance.getExceptionNumber() + 1);
						}
						taskInstanceDao.update(taskInstance);
						// 当前流程完全结束
						if (taskInstance.getCompletionNumber() + taskInstance.getExceptionNumber() >= taskInstance.getTotal()) {
							userTaskInstanceService.deleteByTaskInstanceId(taskInstance.getId());
						}
					}
				});
		}
		return userTaskInstanceIds.length;
	}

	@Override
	public UserTaskInstance updateTaskPass(Long userTaskInstanceId, Form form, final String opinion,final BusinessStorage businessStorage) {
		UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
		if (ObjUtil.isEmpty(userTaskInstance)) {
			Exceptions.service("", "您要审批的流程不存在，或已结束");
		}
		final TaskInstance taskInstance = userTaskInstance.getTaskInstance();
		// 需要重新设置formid，防止其他地方设置。formid、对formid造成变化
		userTaskInstance.getFormObj().setId(userTaskInstance.getFormId());
		userTaskInstanceService.nextFolw(form, userTaskInstance.getId(), opinion, new TaskNodeHandleImpl() {
			@Override
			public void end(UserTaskInstance userTaskInstance, TaskNode toNode) {
				if (ObjUtil.isEmpty(taskInstance.getCompletionNumber())) {
					taskInstance.setCompletionNumber(0);
				}
				taskInstance.setCompletionNumber(taskInstance.getCompletionNumber() + 1);
				if (ObjUtil.isEmpty(taskInstance.getExceptionNumber())) {
					taskInstance.setExceptionNumber(0);
				}
				if (!businessStorage.save(userTaskInstance)) {
					taskInstance.setExceptionNumber(taskInstance.getExceptionNumber() + 1);
				}
				taskInstanceDao.update(taskInstance);
				// 当前流程完全结束
				int completionNumber=ObjUtil.toInt(taskInstance.getCompletionNumber());
				int exceptionNumber=ObjUtil.toInt(taskInstance.getExceptionNumber());
				int total=ObjUtil.toInt(taskInstance.getTotal());
				// 当前流程完全结束
				if (completionNumber + exceptionNumber >= total) {
//					businessStorage.end(userTaskInstance);
					userTaskInstanceService.deleteByTaskInstanceId(taskInstance.getId());
				}
			}
		});
		return userTaskInstance;
	}
	
	
	@Override
	public int updateTaskRebut(Long[] userTaskInstanceIds, final String opinion) {
		for (Long userTaskInstanceId : userTaskInstanceIds) {
		// 查询当前流程的上一个流程的操作者
			UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
			if (ObjUtil.isEmpty(userTaskInstance)) {
				Exceptions.service("", "您要审批的流程不存在，或已结束");
			}
			//需要重新设置formid，防止其他地方设置。formid、对formid造成变化
			userTaskInstance.getFormObj().setId(userTaskInstance.getFormId());
//			final TaskInstance taskInstance = userTaskInstance.getTaskInstance();
			userTaskInstanceService.backFolw(userTaskInstanceId, opinion, new TaskNodeHandleImpl() {
				@Override
				public void process(UserTaskInstance userTaskInstance, TaskNode taskNode) {
	
				}
			});
		}
		return userTaskInstanceIds.length;
	}

	@Override
	public void updateCloseTask(Long userTaskInstanceId) {
		UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
		if(ObjUtil.isEmpty(userTaskInstance)){
			Exceptions.service("", "当前用户流程不存在");
		}
		//需要重新设置formid，防止其他地方设置。formid、对formid造成变化
		userTaskInstance.getFormObj().setId(userTaskInstance.getFormId());
//		if(YesorNo.NO.equals(userTaskInstance.getCurrent().getIsClose())){
//			Exceptions.service("", "当前节点不允许关闭流程");
//		}
		FormSerivce formSerivce=formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
		//先记录操作
		historyFormService.save(userTaskInstance.getId(),"关闭流程" ,userTaskInstance.getFormObj());
		//通过注册流程，得到需要处理的流程数据
		
		formSerivce.updateCloseTask(userTaskInstance);
		TaskInstance taskInstance = userTaskInstance.getTaskInstance();
		if(ObjUtil.isEmpty(taskInstance.getCompletionNumber())){
			taskInstance.setCompletionNumber(1);
		}else{
			taskInstance.setCompletionNumber(taskInstance.getCompletionNumber()+1);
		}
		userTaskInstanceService.deleteById(userTaskInstance.getId());
		int completionNumber=ObjUtil.toInt(taskInstance.getCompletionNumber());
		int exceptionNumber=ObjUtil.toInt(taskInstance.getExceptionNumber());
		int total=ObjUtil.toInt(taskInstance.getTotal());
		// 当前流程完全结束
		if (completionNumber + exceptionNumber >= total) {
			userTaskInstanceService.deleteByTaskInstanceId(taskInstance.getId());
		}
	}
	
	@Override
	public void updateClose(Long userTaskInstanceId) {
		UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
		if(ObjUtil.isEmpty(userTaskInstance)){
			Exceptions.service("", "当前用户流程不存在");
		}
		//需要重新设置formid，防止其他地方设置。formid、对formid造成变化
		userTaskInstance.getFormObj().setId(userTaskInstance.getFormId());
		if(YesorNo.NO.equals(userTaskInstance.getCurrent().getIsClose())){
			Exceptions.service("", "当前节点不允许关闭流程");
		}
		// 当前流程完全结束
		userTaskInstanceService.deleteByTaskInstanceId(userTaskInstance.getTaskInstance().getId());
	}

	
}
