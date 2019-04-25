/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service;

import java.util.List;

import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.support.TaskNodeHandle;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;

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
public interface UserTaskInstanceService extends BasisService<UserTaskInstance, Long> {
	
	/**
	 * 跟用户，查询用户能够处理的流程<br>
	 * 需要根据流程模板来库。
	 * @author caijin
	 * @param taskTempId
	 * @param userId
	 * @param isEndNode
	 * @param list 
	 * @return
	 * @since JDK 1.7
	 */
	PagedInfo<UserTaskInstance> findPageByTaskTempTaskNodeNameUserId(Page page,List<Long> taskTempIds,String taskNodeName, Long userId);
	/**
	 * 跟用户，查询用户能够处理的流程<br>
	 * 需要根据流程模板来库。
	 * @author caijin
	 * @param taskTempId
	 * @param userId
	 * @param isEndNode
	 * @param list 
	 * @return
	 * @since JDK 1.7
	 */
	List<UserTaskInstance> findListByTaskTempTaskNodeNameUserId(List<Long> taskTempIds,List<Long> formIds,String taskNodeName, Long userId);

	/**
	 * 查询模板下的全部流程。
	 * @author caijin
	 * @param taskTempId
	 * @param isEndNode
	 * @return
	 * @since JDK 1.7
	 */
	List<UserTaskInstance> findByTaskTempId(Long taskTempId,List<Param> paramList, boolean isEndNode);
	
	UserTaskInstance save(UserTaskInstance taskUserNode);

	/**
	 * nextFolw: 执行下一步流程 <br/>
	 * @author caijin
	 * @param fromId  表单id
	 * @param userTaskInstanceId
	 *            当前处理的用户流程实例id
	 * @return 下一个流程实例对象
	 * @since JDK 1.7
	 */
	UserTaskInstance nextFolw(Form form, Long userTaskInstanceId,String opinion, TaskNodeHandle handle);

	/**
	 * nextFolw: 执行下一步流程 <br/>
	 * 
	 * @author caijin
	 * @param fromId
	 *            表单id
	 * @param userTaskInstanceId
	 *            当前处理的用户流程实例id
	 * @return 下一个流程实例对象
	 * @since JDK 1.7
	 */
	UserTaskInstance backFolw(Long userTaskInstanceId,String opinion, TaskNodeHandle handle);

	/**
	 * startFolw: 启动流程 <br/>
	 * 
	 * @author caijin
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	UserTaskInstance startFolw(Long userTaskInstanceId,Form form);

	/**
	 * startFolw: 启动流程 <br/>
	 * 
	 * @author caijin
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	UserTaskInstance startFolw(UserTaskInstance userTaskInstance, Form form);

	/**
	 * 当前的流程，全部结束，删除，用户流程，并把过程表的记录也删除掉。<br>
	 * 只保留流程，流程包
	 * @author caijin
	 * @param id
	 * @return 
	 * @since JDK 1.7
	 */
	int deleteByTaskInstanceId(Long taskInstanceId);


//	/**
//	 * 验证form表达是否在流程中
//	 * @author caijin
//	 * @param userTaskInstanceId
//	 * @return
//	 * @since JDK 1.7
//	 */
//	String validationProcess(Long formId);

	/**
	 * 根据formId查询，当前流程
	 * @author caijin
	 * @param formId
	 * @return
	 * @since JDK 1.7
	 */
	UserTaskInstance findByFormId(Long formId);


	/**
	 * 删除，用户任务
	 * @author caijin
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	int deleteById(Long id);

	/**
	 * 查询，需要审批的数据
	 * @author caijin
	 * @param page
	 * @param examStrategy
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	PagedInfo<UserTaskInstance> findApprovePage(Page page, List<Long> taskTempIds,String taskNodeName, Long userId);
	
	/**
	 * 查询能够审批的，全部实例id
	 * @author caijin
	 * @param userId
	 * @param param
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findIdApproveList(Long userId,List<Long> taskTempIds,String taskNodeName, List<Param> param);
	
	String validationProcess(Long[] formIds);
	
	/**
	 * countByTaskTempIdAndUserIdsAndCurrentIds:统计驳回数量<br/>
	 * @author Administrator
	 * @param taskTempId
	 * @param taskNodeApprovalChainList
	 * @param chainIds
	 * @return
	 * @since JDK 1.7
	 */
	Long countByTaskTempIdAndUserIdsAndCurrentIds(List<Long> taskTempIds, String taskNodeName, Long userId);
	
	
	
}
