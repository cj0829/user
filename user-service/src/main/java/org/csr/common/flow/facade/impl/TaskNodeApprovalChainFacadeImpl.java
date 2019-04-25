package org.csr.common.flow.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.dao.TaskNodeApprovalChainDao;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.entity.TaskNodeApprovalChainBean;
import org.csr.common.flow.facade.TaskNodeApprovalChainFacade;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.user.facade.UserInitData;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.web.bean.VOBase;
import org.springframework.stereotype.Service;
@Service("taskNodeApprovalChainFacade")
public class TaskNodeApprovalChainFacadeImpl extends SimpleBasisFacade<TaskNodeApprovalChainBean, TaskNodeApprovalChain, Long> implements TaskNodeApprovalChainFacade,UserInitData{
	@Resource
	private TaskNodeApprovalChainService taskNodeApprovalChainService;
	@Resource
	private TaskNodeApprovalChainDao taskNodeApprovalChainDao;
	
	
	@Override
	public List<TaskNodeApprovalChain> findByTaskNodeIds(List<Long> taskIds) {
		
		return taskNodeApprovalChainService.findByTaskNodeIds(taskIds);
	}

	@Override
	public TaskNodeApprovalChainBean wrapBean(TaskNodeApprovalChain doMain) {
		
		return TaskNodeApprovalChainBean.wrapBean(doMain);
	}

	@Override
	public void saveTaskNodeApprovalChain(TaskNode taskNode,List<TaskNodeApprovalChain> taskNodeApprovalChainList) {
		taskNodeApprovalChainService.saveTaskNodeApprovalChain(taskNode, taskNodeApprovalChainList);		
	}

	@Override
	public List<TaskNodeApprovalChainBean> listByTaskNode(Long taskNodeId) {
		List<TaskNodeApprovalChain> chainList = taskNodeApprovalChainService.findByTaskNodeId(taskNodeId,
				true,null);
		List<TaskNodeApprovalChainBean> chainBeanList = PersistableUtil.toListBeans(chainList,
				new SetBean<TaskNodeApprovalChain>() {
					@Override
					public VOBase<Long> setValue(TaskNodeApprovalChain doMain) {
						return TaskNodeApprovalChainBean.wrapBean(doMain);
					}
				});
		return chainBeanList;
	}

	@Override
	public BaseDao<TaskNodeApprovalChain, Long> getDao() {
		return taskNodeApprovalChainDao;
	}

	@Override
	public void delInitData(Long userId) {
		taskNodeApprovalChainDao.deleteByParams(new AndEqParam("approver.id",userId));
	}

}
