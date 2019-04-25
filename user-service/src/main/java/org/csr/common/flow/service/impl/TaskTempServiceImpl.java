/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormApproverType;
import org.csr.common.flow.dao.TaskTempDao;
import org.csr.common.flow.domain.EndNode;
import org.csr.common.flow.domain.StartNode;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.domain.TaskTemp;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.flow.service.TaskNodeService;
import org.csr.common.flow.service.TaskTempService;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.springframework.stereotype.Service;

/**
 * ClassName: TaskTempService.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日下午2:04:04 <br/>
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
/**
 * ClassName:TaskTempServiceImpl.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月25日下午2:19:58 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 */
@Service("taskTempService")
public class TaskTempServiceImpl extends SimpleBasisService<TaskTemp, Long>
		implements TaskTempService {

	@Resource
	TaskTempDao taskTempDao;
	@Resource
	TaskNodeService taskNodeService;
	@Resource
	TaskNodeApprovalChainService taskNodeApprovalChainService;

	@Override
	public BaseDao<TaskTemp, Long> getDao() {
		return taskTempDao;
	}

	@Override
	public TaskTemp createExamFlow(String taskTempName,Long orgId) {
		// 开始
		StartNode startNode = new StartNode("新建");
		startNode.setIsEdit(YesorNo.YES);
		startNode.setRejectNode(startNode);
		startNode.setOrgId(orgId);
		
		
		// 驳回reject
		TaskNode rejectNode = new TaskNode("驳回");
		
		rejectNode.setNodeType("RejectNode");
		rejectNode.setIsEdit(YesorNo.YES);
		rejectNode.setRejectNode(rejectNode);
		
		rejectNode.setOrgId(orgId);
		
		TaskNode pending = new TaskNode("待批");
		pending.setOrgId(orgId);
		startNode.setToNode(pending);
		rejectNode.setToNode(pending);
		pending.setRejectNode(rejectNode);
		
		// 结束
		EndNode endNode = new EndNode("完成");
		endNode.setOrgId(orgId);
		endNode.setToNode(endNode);
		endNode.setRejectNode(endNode);
	
		// 保存模板
		TaskTemp taskTemp = new TaskTemp();
		taskTemp.setName(taskTempName);
		taskTemp.setStart(startNode);
		taskTemp.setOrgId(orgId);
		taskTemp.setValid(true);
		taskTempDao.save(taskTemp);
		
		// 设置模板 各个流程的模板
		//保存修改
		startNode.setTaskTemp(taskTemp);
		taskNodeService.saveSimple(startNode);
		//保存驳回
		rejectNode.setTaskTemp(taskTemp);
		taskNodeService.saveSimple(rejectNode);
		//保存待批
		pending.setTaskTemp(taskTemp);		
		taskNodeService.saveSimple(pending);
		//保存结束
		endNode.setTaskTemp(taskTemp);		
		taskNodeService.saveSimple(endNode);
		//修改
		taskTemp.setEnd(endNode);
		
		//保存开始节点或，结束节点处理人
		TaskNodeApprovalChain startSelf=new TaskNodeApprovalChain();
		startSelf.setOrgId(orgId);
		startSelf.setTaskNode(startNode);
		startSelf.setType(FormApproverType.Releation);
		startSelf.setApproverReleation(TaskNodeApprovalChain.Self);
		taskNodeApprovalChainService.saveSimple(startSelf);
		//驳回处理人
		TaskNodeApprovalChain rejectSelf=new TaskNodeApprovalChain();
		rejectSelf.setOrgId(orgId);
		rejectSelf.setTaskNode(rejectNode);
		rejectSelf.setType(FormApproverType.Releation);
		rejectSelf.setApproverReleation(TaskNodeApprovalChain.Self);
		taskNodeApprovalChainService.saveSimple(rejectSelf);
		//结束节点处理人
		TaskNodeApprovalChain endSelf=new TaskNodeApprovalChain();
		endSelf.setOrgId(orgId);
		endSelf.setTaskNode(endNode);
		endSelf.setType(FormApproverType.Releation);
		endSelf.setApproverReleation(TaskNodeApprovalChain.Self);
		
		taskNodeApprovalChainService.saveSimple(endSelf);
		
		pending.setToNode(endNode);
		taskNodeService.updateSimple(pending);

		//待批节点处理人
		TaskNodeApprovalChain pendingSelf=new TaskNodeApprovalChain();
		pendingSelf.setOrgId(orgId);
		pendingSelf.setTaskNode(pending);
		pendingSelf.setType(FormApproverType.Releation);
		pendingSelf.setApproverReleation(TaskNodeApprovalChain.Manager);
		taskNodeApprovalChainService.saveSimple(pendingSelf);
		
		
		return taskTemp;
	}

	/**
	 * 设置，开始节点的审批人
	 * 
	 * @author caijin
	 * @param taskTemp
	 * @since JDK 1.7
	 */
	@Override
	public void setStartNode(TaskTemp taskTemp) {

	}

	@Override
	public List<TaskTemp> findByUser(Long userId) {
		
		// Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findIdByUser(Long userId) {
		
		// Auto-generated method stub
		return null;
	}
}
