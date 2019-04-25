package org.csr.common.flow.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormApproverType;
import org.csr.common.flow.dao.TaskNodeApprovalChainDao;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.flow.service.TaskNodeService;
import org.csr.common.flow.service.UserTaskInstanceService;
import org.csr.common.user.domain.User;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.param.AndNeParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.CompareValue;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:TaskNodeApprovalChain.java <br/>
 * Date: Fri Jun 26 14:12:55 CST 2015
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 任务节点审批管理 service实现
 */
@Service("taskNodeApprovalChainService")
public class TaskNodeApprovalChainServiceImpl extends SimpleBasisService<TaskNodeApprovalChain, Long>
		implements TaskNodeApprovalChainService {
	@Resource
	private TaskNodeApprovalChainDao taskNodeApprovalChainDao;
	@Resource
	private TaskNodeService taskNodeService;
	@Resource
	private UserTaskInstanceService userTaskInstanceService;

	@Override
	public BaseDao<TaskNodeApprovalChain, Long> getDao() {
		return taskNodeApprovalChainDao;
	}

	@Override
	public List<TaskNodeApprovalChain> findByTaskNodeIds(List<Long> taskNodeIds) {
		if (ObjUtil.isEmpty(taskNodeIds)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}

		return taskNodeApprovalChainDao.findByParam(new AndInParam("taskNode.id", taskNodeIds));
	}

	@Override
	public boolean verificationUserTaskInstance(Long userId, UserTaskInstance userTaskInstance) {
		if (ObjUtil.isEmpty(userTaskInstance)) {
			return false;
		}
		List<TaskNodeApprovalChain> taskNodeChainList = taskNodeApprovalChainDao.findByParam(new AndEqParam("taskNode.id", userTaskInstance.getCurrent().getId()));
		if (ObjUtil.isEmpty(taskNodeChainList)) {
			return false;
		}
		// 过滤当前用户处理
		return verificationTaskNodeUser(userId, userTaskInstance, taskNodeChainList);
	}

	@Override
	public boolean verificationUserTaskInstance(Long userId, Long userTaskInstanceId) {
		UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
		return verificationUserTaskInstance(userId, userTaskInstance);
	}
	@Override
	public boolean verificationTaskNodeUser(Long userId, UserTaskInstance userTaskInstance,List<TaskNodeApprovalChain> taskNodeApprovalChainList) {
		Iterator<TaskNodeApprovalChain> iterator = taskNodeApprovalChainList.iterator();
		while (iterator.hasNext()) {
			TaskNodeApprovalChain tac = iterator.next();
			if (userTaskInstance.getCurrent().getId().equals(tac.getTaskNode().getId())) {
				if (FormApproverType.Releation.equals(tac.getType())) {
					if (TaskNodeApprovalChain.Self.equals(tac.getApproverReleation())) {
						if (userTaskInstance.getUser().getId().equals(userId)) {
							return true;
						}
					} else if (TaskNodeApprovalChain.Manager.equals(tac.getApproverReleation())) {
						User parent = getManagerLevel(userTaskInstance.getUser(), 1);
						if (ObjUtil.isNotEmpty(parent)) {
							if (userId.equals(parent.getId())) {
								return true;
							}
						}
					} else if (TaskNodeApprovalChain.Manager2.equals(tac.getApproverReleation())) {
						User parent = getManagerLevel(userTaskInstance.getUser(), 2);
						if (ObjUtil.isNotEmpty(parent)) {
							if (userId.equals(parent.getId())) {
								return true;
							}
						}
					} else if (TaskNodeApprovalChain.Manager3.equals(tac.getApproverReleation())) {
						User parent = getManagerLevel(userTaskInstance.getUser(), 3);
						if (ObjUtil.isNotEmpty(parent)) {
							if (userId.equals(parent.getId())) {
								return true;
							}
						}
					}
				} else if (FormApproverType.People.equals(tac.getType())) {
					if (tac.getApprover().getId().equals(userId)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public List<TaskNodeApprovalChain> findByTaskNodeId(Long taskNodeId, boolean isEndNode, List<Param> params) {
		if (ObjUtil.isEmpty(taskNodeId)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndEqParam("taskNode.id", taskNodeId));
		if (!isEndNode) {
			paramList.add(new AndNeParam("taskNode.nodeType", "EndNode"));
		}
		if (ObjUtil.isNotEmpty(params)) {
			for (Param param : params) {
				paramList.add(param);
			}
		}
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}

	@Override
	public List<TaskNodeApprovalChain> findByTaskNodeIdAndType(Long taskNodeId, Integer type,
			List<Param> params) {
		if (ObjUtil.isEmpty(taskNodeId)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndEqParam("taskNode.id", taskNodeId));
		paramList.add(new AndEqParam("type", type));
		if (ObjUtil.isNotEmpty(params)) {
			for (Param param : params) {
				paramList.add(param);
			}
		}
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}

	@Override
	public void saveTaskNodeApprovalChain(TaskNode taskNode,
			List<TaskNodeApprovalChain> taskNodeApprovalChainList) {
		if (ObjUtil.isEmpty(taskNode)) {
			Exceptions.success("", "您必须选择要编辑的审批节点");
		}
		if (ObjUtil.isEmpty(taskNodeApprovalChainList)) {
			return;
		}
		final TaskNode oldTaskNode = taskNodeService.findById(taskNode.getId());
		if (ObjUtil.isEmpty(oldTaskNode)) {
			Exceptions.success("", "您必须选择要编辑的审批节点");
		}
		//TODO：2015-12-7 策略暂时不实现
//		oldTaskNode.setIsAllApproval(taskNode.getIsAllApproval());
//		oldTaskNode.setIsClose(taskNode.getIsClose());
		oldTaskNode.setIsEdit(taskNode.getIsEdit());
//		oldTaskNode.setIsOrderApproval(taskNode.getIsOrderApproval());
		// 修改策略
		taskNodeService.updateSimple(oldTaskNode);

		List<TaskNodeApprovalChain> oldList = findByTaskNodeId(taskNodeApprovalChainList.get(0).getTaskNode().getId(), true, null);

		// 把查询出的数据，转换成字符串数据，用于跟新的比较
		List<String> odlChainList = new ArrayList<String>(PersistableUtil.arrayTransforList(oldList,
				new ToValue<TaskNodeApprovalChain, String>() {
					public String getValue(TaskNodeApprovalChain obj) {
						if (FormApproverType.Releation.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApproverReleation();
						} else if (FormApproverType.People.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApprover().getId();
						} else {
							return "";
						}
					}
				}));
		// /把查询出的数据，转换为map数据，用于，删除使用
		final Map<String, TaskNodeApprovalChain> odlChainMap = PersistableUtil.toMap(oldList,
				new ToValue<TaskNodeApprovalChain, String>() {
					public String getValue(TaskNodeApprovalChain obj) {
						if (FormApproverType.Releation.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApproverReleation();
						} else if (FormApproverType.People.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApprover().getId();
						} else {
							return "";
						}
					}
				});
		// 新添数据，转换成字符串数据，用于跟查询出来的数据库数据比较
		List<String> newChainList = new ArrayList<String>(PersistableUtil.arrayTransforList(
				taskNodeApprovalChainList, new ToValue<TaskNodeApprovalChain, String>() {
					public String getValue(TaskNodeApprovalChain obj) {
						if (FormApproverType.Releation.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApproverReleation();
						} else if (FormApproverType.People.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApprover().getId();
						} else {
							return "";
						}
					}
				}));
		// /把添数据，转换为map数据，用于，新建使用
		final Map<String, TaskNodeApprovalChain> newChainMap = PersistableUtil.toMap(taskNodeApprovalChainList,
				new ToValue<TaskNodeApprovalChain, String>() {
					public String getValue(TaskNodeApprovalChain obj) {
						if (FormApproverType.Releation.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApproverReleation();
						} else if (FormApproverType.People.equals(obj.getType())) {
							return obj.getType() + "_" + obj.getApprover().getId();
						} else {
							return "";
						}
					}
				});

		ObjUtil.compareId(odlChainList, newChainList, new CompareValue<String>() {
			@Override
			public void add(List<String> chainListids) {
				for (String chainId : chainListids) {
					TaskNodeApprovalChain newChain = newChainMap.get(chainId);
					newChain.setOrgId(oldTaskNode.getOrgId());
					taskNodeApprovalChainDao.save(newChain);
				}
			}

			@Override
			public void update(List<String> chainListids) {
			}

			@Override
			public void delete(List<String> chainListids) {
				for (String chainId : chainListids) {
					TaskNodeApprovalChain oldChain = odlChainMap.get(chainId);
					taskNodeApprovalChainDao.delete(oldChain);
				}
			}
		});
	}
	
	@Override
	public List<TaskNodeApprovalChain> findByTaskTempIds(List<Long> taskTempIds, boolean isEndNode, List<Param> params) {
		if (ObjUtil.isEmpty(taskTempIds)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndInParam("taskNode.taskTemp.id", taskTempIds));
		if (!isEndNode) {
			paramList.add(new AndNeParam("taskNode.nodeType", "EndNode"));
		}
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}

	/**
	 * 根据当前用户，查询多级管理员
	 * @author caijin
	 * @param user
	 * @param level
	 * @return
	 * @since JDK 1.7
	 */
	private User getManagerLevel(User user, int level) {
		if (level <= 0) {
			level = 1;
		}
		User parent = user.getManagerUser();
		for (int i = 1; i < level; i++) {
			if (ObjUtil.isNotEmpty(parent)) {
				parent = parent.getManagerUser();
			} else {
				return null;
			}
		}
		return parent;
	}
	@Override
	public List<TaskNodeApprovalChain> findPeopleByTaskTempId(List<Long> taskTempId,Long userId){
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndInParam("taskNode.taskTemp.id", taskTempId));
		paramList.add(new AndEqParam("type", FormApproverType.People));
		paramList.add(new AndEqParam("approver.id", userId));
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}
	@Override
	public List<TaskNodeApprovalChain> findPeopleByTaskTempIdTaskNode(List<Long> taskTempId,String taskNodeName,Long userId){
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndInParam("taskNode.taskTemp.id", taskTempId));
		paramList.add(new AndEqParam("type", FormApproverType.People));
		paramList.add(new AndEqParam("approver.id", userId));
		if(ObjUtil.isNotBlank(taskNodeName)){
			paramList.add(new AndInParam("taskNode.nodeType", taskNodeName));
		}
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}

	@Override
	public List<TaskNodeApprovalChain> findReleationByTaskTempId(List<Long> taskTempId) {
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndInParam("taskNode.taskTemp.id", taskTempId));
		paramList.add(new AndEqParam("type", FormApproverType.Releation));
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}
	
	@Override
	public List<TaskNodeApprovalChain> findReleationByTaskTempIdTaskNode(List<Long> taskTempId,String taskNodeName) {
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<TaskNodeApprovalChain>(0);
		}
		List<Param> paramList = new ArrayList<Param>();
		paramList.add(new AndInParam("taskNode.taskTemp.id", taskTempId));
		paramList.add(new AndEqParam("type", FormApproverType.Releation));
		if(ObjUtil.isNotBlank(taskNodeName)){
			paramList.add(new AndInParam("taskNode.nodeType", taskNodeName));
		}
		return taskNodeApprovalChainDao.findByParam(paramList.toArray(new Param[paramList.size()]));
	}
}
