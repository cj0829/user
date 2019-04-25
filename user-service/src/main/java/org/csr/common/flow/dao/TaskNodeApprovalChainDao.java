package org.csr.common.flow.dao;


import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:TaskNodeApprovalChain.java <br/>
 * Date: Fri Jun 26 14:12:55 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 任务节点审批管理 dao接口
 */
public interface TaskNodeApprovalChainDao extends BaseDao<TaskNodeApprovalChain,Long>{

	int deleteByTaskNodeId(Long id);


}
