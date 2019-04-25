package org.csr.common.flow.dao.impl;


import org.csr.common.flow.dao.TaskNodeApprovalChainDao;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;


/**
 * ClassName:TaskNodeApprovalChain.java <br/>
 * Date: Fri Jun 26 14:12:55 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 任务节点审批管理 dao实现
 */
@Repository("taskNodeApprovalChainDao")
public class TaskNodeApprovalChainDaoImpl extends JpaDao<TaskNodeApprovalChain,Long> implements TaskNodeApprovalChainDao{

	@Override
	public Class<TaskNodeApprovalChain> entityClass(){
		return TaskNodeApprovalChain.class;
	}

	@Override
	public int deleteByTaskNodeId(Long taskNodeId) {
		if(ObjUtil.isEmpty(taskNodeId)){
			return 0;
		}
		Finder finder=FinderImpl.create("delete from TaskNodeApprovalChain t where t.taskNode.id=:taskNodeId");
		finder.setParam("taskNodeId",taskNodeId);
		return batchHandle(finder);
	}

}
