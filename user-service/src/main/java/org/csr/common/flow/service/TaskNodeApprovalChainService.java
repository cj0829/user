package org.csr.common.flow.service;

import java.util.List;

import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.core.Param;
import org.csr.core.persistence.service.BasisService;

/**
 * @(#)Sss.java
 * 
 *              ClassName:TaskNodeApprovalChain.java <br/>
 *              Date: Fri Jun 26 14:12:55 CST 2015 <br/>
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 任务节点审批管理 service接口
 */
public interface TaskNodeApprovalChainService extends BasisService<TaskNodeApprovalChain, Long> {

	/**
	 * 根据nodeids查询，当前的审批条件
	 * 
	 * @author caijin
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findByTaskNodeIds(List<Long> taskNodeIds);

	
	/**
	 * 根据审批节点的类型为审批人，当期用户是否能够审批。
	 * 只需要返回一种类型[用户]的，
	 * @author caijin
	 * @param params
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findPeopleByTaskTempId(List<Long> taskTempIds,Long userId);
	/**
	 * 根据审批节点的类型为审批人，当期用户是否能够审批。
	 * 只需要返回一种类型[用户]的，
	 * @author caijin
	 * @param params
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findPeopleByTaskTempIdTaskNode(List<Long> taskTempIds,String taskNodeName,Long userId);

	
	/**
	 * 只查询出当前审批流程中，为关系的处理数据
	 * 只需要返回一种类型[关系]的，
	 * @author caijin
	 * @param params
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findReleationByTaskTempId(List<Long> taskTempId);
	/**
	 * 根据TaskTempId查询，相关的处理节点。
	 * 
	 * @author caijin
	 * @param params
	 * @param taskNodeIds
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findByTaskTempIds(List<Long> taskTempIds, boolean isEndNode, List<Param> params);

	/**
	 * 创建节点的处理人
	 * 
	 * @author caijin
	 * @param taskNode
	 * @param taskNodeApprovalChainList
	 * @return
	 * @since JDK 1.7
	 */
	public void saveTaskNodeApprovalChain(TaskNode taskNode,
			List<TaskNodeApprovalChain> taskNodeApprovalChainList);

	/**
	 * 根据节点查询，节点上的处理人员和关系
	 * 
	 * @author caijin
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findByTaskNodeId(Long taskNodeId, boolean isEndNode, List<Param> params);

	/**
	 * 查询节点上的，对应类型的数据，（类型包含，关系，和人员）
	 * 
	 * @author caijin
	 * @param id
	 * @param type
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskNodeApprovalChain> findByTaskNodeIdAndType(Long taskNodeId, Integer type,
			List<Param> params);

	/**
	 * 验证，用户实例，当前用户是否觉有操作权限。
	 * 
	 * @author caijin
	 * @param userId
	 * @param userTaskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	boolean verificationUserTaskInstance(Long userId, Long userTaskInstanceId);

	/**
	 * 验证，用户实例，当前用户是否觉有操作权限。
	 * 
	 * @author caijin
	 * @param userId
	 * @param userTaskInstance
	 * @return
	 * @since JDK 1.7
	 */
	boolean verificationUserTaskInstance(Long userId, UserTaskInstance userTaskInstance);


	/**
	 * 验证，用户实例，当前用户是否觉有操作权限。具体验证方法。提供外部使用
	 * 
	 * @author caijin
	 * @param userId
	 * @param userTaskInstance
	 * @param taskNodeApprovalChainList
	 * @return
	 * @since JDK 1.7
	 */
	boolean verificationTaskNodeUser(Long userId, UserTaskInstance userTaskInstance,
			List<TaskNodeApprovalChain> taskNodeApprovalChainList);


	/**
	 * 根据当前节点状态，返回对应的状态的处理节点
	 * @author caijin
	 * @param taskTempId
	 * @param taskNodeName
	 * @return
	 * @since JDK 1.7
	 */
	List<TaskNodeApprovalChain> findReleationByTaskTempIdTaskNode(List<Long> taskTempId, String taskNodeName);

}
