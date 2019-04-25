/**
 * Project Name:common
 * File Name:TaskInstanceService.java
 * Package Name:org.csr.common.flow.service.impl
 * Date:2014年3月14日上午10:40:12
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.flow.service;

import org.csr.common.flow.domain.TaskInstance;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName: TaskInstanceService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014年3月14日上午10:40:12 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述： 工作流程服务 <br/>
 * 公用方法描述： 主要是表单流程的生命周期的管理 <br/>
 * 主要的功能能有，如下
 * 1，根据流程模板，实例工作流
 * 2，流程的流转。
 * 3，流程结束。
 */
public interface TaskInstanceService  extends BasisService<TaskInstance, Long>{
	
	
	
	
	/**
	 * 根据流程模板，创建新工作流程实例 <br/>
	 * 不同的流程模板，对应的流程不一样。 <br/>
	 * 任务发布之后，需要根据任务模板判断当前任务是否需要启动,根据挑剔是否提交流程
	 * @author caijin
	 * @param taskTempId
	 * @param form
	 * @param name
	 * @param userIds
	 * @param isStartFolw
	 * @return
	 * @since JDK 1.7
	 */
	public TaskInstance createTaskInstance(Long taskTempId,Form form,String name,Long[] userIds,boolean isStartFolw);
	/**
	 * 根据流程模板，创建新工作流程实例 <br/>
	 * 不同的流程模板，对应的流程不一样。 <br/>
	 * 任务发布之后，需要根据任务模板判断当前任务是否需要启动,根据挑剔是否提交流程
	 * @author caijin
	 * @param taskTempId
	 * @param form
	 * @param operType
	 * @param name
	 * @param userIds
	 * @param isStartFolw
	 * @return
	 * @since JDK 1.7
	 */
	public TaskInstance createTaskInstance(Long taskTempId,Form form,Integer operType,String name,Long[] userIds,boolean isStartFolw);
	
	
	/**
	 * taskApproval: 审批通过 <br/>
	 * @author yjY
	 * @param userTaskInstanceId
	 * @param formId
	 * @return
	 * @since JDK 1.7
	 */
	int updateTaskPass(Long[] userTaskInstanceId,String opinion,final BusinessStorage businessStorage);
	
	/**
	 * 通过审批
	 * @author caijin
	 * @param userTaskInstanceId
	 * @param form
	 * @param opinion
	 * @param businessStorage
	 * @return
	 * @since JDK 1.7
	 */
	UserTaskInstance updateTaskPass(Long userTaskInstanceId, Form form, String opinion, BusinessStorage businessStorage);
	/**
	 * 
	 * taskApproval: 审批驳回 <br/>
	 * @author yjY
	 * @param userTaskInstanceId
	 * @param formId
	 * @return
	 * @since JDK 1.7
	 */
	int updateTaskRebut(Long[] userTaskInstanceId,String opinion);


	/**
	 * 关闭当前流程
	 * @author caijin
	 * @param userTaskInstanceId
	 * @since JDK 1.7
	 */
	public void updateCloseTask(Long userTaskInstanceId);


	void updateClose(Long userTaskInstanceId);
	
}

