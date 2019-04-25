package org.csr.common.flow.facade.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormApproverType;
import org.csr.common.flow.dao.TaskNodeDao;
import org.csr.common.flow.domain.EndNode;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.entity.TaskNodeBean;
import org.csr.common.flow.facade.TaskNodeFacade;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.flow.service.TaskNodeService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.DictionaryUtil;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToString;
import org.csr.core.web.bean.VOBase;
import org.springframework.stereotype.Service;
@Service("taskNodeFacade")
public class TaskNodeFacadeImpl extends SimpleBasisFacade<TaskNodeBean, TaskNode, Long> implements TaskNodeFacade{
	@Resource
	private TaskNodeService taskNodeService;
	@Resource
	private TaskNodeDao taskNodeDao;
	@Resource
	private TaskNodeApprovalChainService taskNodeApprovalChainService;

	@Override
	public TaskNode wrapDomain(TaskNodeBean entity) {
		TaskNode bean=new TaskNode();
		bean.setId(entity.getId());
		bean.setToNode(taskNodeService.findById(entity.getToNodeId()));
		bean.setName(entity.getName());
		bean.setRejectNode(taskNodeService.findById(entity.getRejectNodeId()));
		bean.setStartTime(entity.getStartTime());
		bean.setIntervalTime(entity.getIntervalTime());
		bean.setRemark(entity.getRemark());
		bean.setIsAllApproval(entity.getIsAllApproval());
		bean.setIsClose(entity.getIsClose());
		bean.setIsEdit(entity.getIsEdit());	
		bean.setIsOrderApproval(entity.getIsOrderApproval());		
		return bean;
	}

	@Override
	public TaskNodeBean wrapBean(TaskNode doMain) {
		
		return TaskNodeBean.wrapBean(doMain);
	}

	@Override
	public List<TaskNodeBean> listByTaskTemp(Long taskTempId) {
		List<TaskNode> taskNodes = taskNodeService.findByTaskTempIdNotStartEnd(taskTempId);
		List<Long> taskIds = PersistableUtil.arrayTransforList(taskNodes);
		final List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainService.findByTaskNodeIds(taskIds);

		List<TaskNodeBean> taskNodeBeanList = PersistableUtil.toListBeans(taskNodes, new SetBean<TaskNode>() {
			public VOBase<Long> setValue(TaskNode doMain) {
				if(doMain instanceof EndNode){
					return null;
				}
				ArrayList<TaskNodeApprovalChain> taskNodeChain = new ArrayList<TaskNodeApprovalChain>();
				for (TaskNodeApprovalChain chain : taskNodeApprovalChainList) {
					if (chain.getTaskNode().getId().equals(doMain.getId())) {
						taskNodeChain.add(chain);
					}
				}
				TaskNodeBean taskBean = TaskNodeBean.wrapBean(doMain);
				taskBean.setChainId(PersistableUtil.arrayTransforString(taskNodeChain,
						new ToString<TaskNodeApprovalChain>() {
							@Override
							public String getValue(TaskNodeApprovalChain obj) {
								return ObjUtil.toString(obj.getId());
							}
						}));
				taskBean.setChainName(PersistableUtil.arrayTransforString(taskNodeChain,
						new ToString<TaskNodeApprovalChain>() {
							@Override
							public String getValue(TaskNodeApprovalChain obj) {
								if (FormApproverType.People.equals(obj.getType())) {
									return obj.getApprover().getLoginName();
								} else if (FormApproverType.Releation.equals(obj.getType())) {
									return DictionaryUtil.getDictName("approvalChainType", obj.getApproverReleation().toString());
								}
								return "";
							}

						}));
				return taskBean;
			}
		});
		return taskNodeBeanList;
	}

	@Override
	public BaseDao<TaskNode, Long> getDao() {
		return taskNodeDao;
	}

	
}
