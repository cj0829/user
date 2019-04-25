/**
 * Project Name:common
 * File Name:TaskTempDao.java
 * Package Name:org.csr.common.flow.dao
 * Date:2014年3月14日下午3:15:50
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.csr.common.flow.dao.UserTaskInstanceDao;
import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName: TaskTempDao.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日下午3:15:50 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Repository("userTaskInstanceDao")
public class UserTaskInstanceDaoImpl extends JpaDao<UserTaskInstance, Long>
		implements UserTaskInstanceDao {

	@Override
	public Class<UserTaskInstance> entityClass() {
		return UserTaskInstance.class;
	}

	@Override
	public int deleteByTaskInstanceIds(List<Long> ids) {
		if(ObjUtil.isEmpty(ids)){
			return 0;
		}
		Finder finder=FinderImpl.create("delete from  UserTaskInstance u where u.id in (:ids)");
		finder.setParam("ids", ids);
		return batchHandle(finder);
	}

	@Override
	public List<Long> findIdsByByTaskInstanceId(Long taskInstanceId) {
		if(ObjUtil.isEmpty(taskInstanceId)){
			return new ArrayList<Long>(0);
		}
		Finder finder=FinderImpl.create("select u.id from UserTaskInstance u where u.taskInstance.id = :taskInstanceId");
		finder.setParam("taskInstanceId", taskInstanceId);
		return find(finder);
	}

	@Override
	public List<Long> findIdApproveList(List<Long> taskTempId, List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds,List<Param> param) {
		if(ObjUtil.isEmpty(taskTempId)){
			return new ArrayList<Long>(0);
		}
		if (ObjUtil.isEmpty(chainIds) && ObjUtil.isEmpty(taskNodeApprovalChainList)) {
			return new ArrayList<Long>(0);
		}
		Finder finder=FinderImpl.create("select u.id from UserTaskInstance u where (");
		finder.append("taskTempId in (:taskTempId)", "taskTempId", taskTempId);
		if (ObjUtil.isNotEmpty(taskNodeApprovalChainList)) {
			finder.append(" and (");
			boolean first=false;
			for (int i=0;i<taskNodeApprovalChainList.size();i++) {
				TaskNodeApprovalChain chain = taskNodeApprovalChainList.get(i);
				if(first){
					finder.append(" or ");
				}
				finder.append("(u.user.id in ("+getUserLevel(finder, i, chain)+")", "userId_"+i+"", UserSessionContext.getUserSession().getUserId());
				finder.append(" and u.current.id = :currentId_"+i+")", "currentId_"+i+"", chain.getTaskNode().getId());
				first=true;
			}
			finder.append(")");
			
		}
		if (ObjUtil.isNotEmpty(chainIds)) {
			finder.append(" or u.current.id in (:currentId)", "currentId", chainIds);
		}
		finder.append(")");
		finder.autoPackagingParams("taskTemp", param);
		return find(finder);
	}

	@Override
	public PagedInfo<UserTaskInstance> findPageByTaskTempIdAndUserIdsAndCurrentIds(Page page,List<Long> taskTempId, List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds) {
		if (ObjUtil.isEmpty(chainIds) && ObjUtil.isEmpty(taskNodeApprovalChainList)) {
			return createPagedInfo(0, page, new ArrayList<UserTaskInstance>(0));
		}
		Finder finder=FinderImpl.create("select u from UserTaskInstance u where (");
		finder.append("taskTempId in (:taskTempId)", "taskTempId", taskTempId);
		if (ObjUtil.isNotEmpty(taskNodeApprovalChainList)) {
			finder.append(" and (");
			boolean first=false;
			for (int i=0;i<taskNodeApprovalChainList.size();i++) {
				TaskNodeApprovalChain chain = taskNodeApprovalChainList.get(i);
				if(first){
					finder.append(" or ");
				}
				finder.append("(u.user.id in ("+getUserLevel(finder, i, chain)+")", "userId_"+i+"", UserSessionContext.getUserSession().getUserId());
				finder.append(" and u.current.id = :currentId_"+i+")", "currentId_"+i+"", chain.getTaskNode().getId());
				first=true;
			}
			finder.append(")");
			
		}
		if (ObjUtil.isNotEmpty(chainIds)) {
			finder.append(" or u.current.id in (:currentId)", "currentId", chainIds);
		}
		finder.append(")");
		
		return findPage(page,finder);
	}
	@Override
	public List<UserTaskInstance> findListByTaskTempIdAndUserIdsAndCurrentIds(List<Long> taskTempId,List<Long> formIds, List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds) {
		if (ObjUtil.isEmpty(chainIds) && ObjUtil.isEmpty(taskNodeApprovalChainList)) {
			return new ArrayList<UserTaskInstance>();
		}
		Finder finder=FinderImpl.create("select u from UserTaskInstance u where (");
		finder.append("taskTempId in (:taskTempId)", "taskTempId", taskTempId);
		if (ObjUtil.isNotEmpty(taskNodeApprovalChainList)) {
			finder.append(" and (");
			boolean first=false;
			for (int i=0;i<taskNodeApprovalChainList.size();i++) {
				TaskNodeApprovalChain chain = taskNodeApprovalChainList.get(i);
				if(first){
					finder.append(" or ");
				}
				finder.append("(u.user.id in ("+getUserLevel(finder, i, chain)+")", "userId_"+i+"", UserSessionContext.getUserSession().getUserId());
				finder.append(" and u.current.id = :currentId_"+i+")", "currentId_"+i+"", chain.getTaskNode().getId());
				first=true;
			}
			finder.append(")");
			
		}
		if (ObjUtil.isNotEmpty(chainIds)) {
			finder.append(" or u.current.id in (:currentId)", "currentId", chainIds);
		}
		finder.append(")");
		if(ObjUtil.isNotEmpty(formIds)){
			finder.append(" and u.formId in (:formIds)", "formIds", formIds);
		}
		return find(finder);
	}
	
	@Override
	public Long countByTaskTempIdAndUserIdsAndCurrentIds(List<Long> taskTempId, List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds) {
		if (ObjUtil.isEmpty(chainIds) && ObjUtil.isEmpty(taskNodeApprovalChainList)) {
			return 0l;
		}
		Finder finder=FinderImpl.create("select count(u.id) from UserTaskInstance u where (");
		finder.append("taskTempId in (:taskTempId)", "taskTempId", taskTempId);
		if (ObjUtil.isNotEmpty(taskNodeApprovalChainList)) {
			finder.append(" and (");
			boolean first=false;
			for (int i=0;i<taskNodeApprovalChainList.size();i++) {
				TaskNodeApprovalChain chain = taskNodeApprovalChainList.get(i);
				if(first){
					finder.append(" or ");
				}
				finder.append("(u.user.id in ("+getUserLevel(finder, i, chain)+")", "userId_"+i+"", UserSessionContext.getUserSession().getUserId());
				finder.append(" and u.current.id = :currentId_"+i+")", "currentId_"+i+"", chain.getTaskNode().getId());
				first=true;
			}
			finder.append(")");
			
		}
		if (ObjUtil.isNotEmpty(chainIds)) {
			finder.append(" or u.current.id in (:currentId)", "currentId", chainIds);
		}
		finder.append(")");
		return countOriginalHql(finder);
	}
	
	private String getUserLevel(Finder finder,int i,TaskNodeApprovalChain taskNodeApprovalChain){
		StringBuffer qhl=new StringBuffer();
		if (TaskNodeApprovalChain.Self.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append(":userId_"+i);
		} else if (TaskNodeApprovalChain.Manager.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id=:userId_"+i);
		} else if (TaskNodeApprovalChain.Manager2.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id in(");
			qhl.append("select ul2.id from User ul2 where ul2.managerUser.id=:userId_"+i+")");
		} else if (TaskNodeApprovalChain.Manager3.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id in(");
			qhl.append("select ul2.id from User ul2 where ul2.managerUser.id in(");
			qhl.append("select ul3.id from User ul3 where ul3.managerUser.id=:userId_"+i+"))");
		}
		return qhl.toString();
	}

}
