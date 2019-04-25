package org.csr.common.flow.facade;

import java.util.List;

import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.entity.TaskNodeApprovalChainBean;
import org.csr.core.persistence.service.BasisFacade;

public interface TaskNodeApprovalChainFacade extends BasisFacade<TaskNodeApprovalChainBean, Long>{

	List<TaskNodeApprovalChain> findByTaskNodeIds(List<Long> taskIds);

	void saveTaskNodeApprovalChain(TaskNode taskNode,List<TaskNodeApprovalChain> taskNodeApprovalChainList);

	List<TaskNodeApprovalChainBean> listByTaskNode(Long taskNodeId);

}
