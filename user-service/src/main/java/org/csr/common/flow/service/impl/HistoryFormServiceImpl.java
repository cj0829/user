/**
 * Project Name:common
 * File Name:HistoryFormServiceImpl.java
 * Package Name:org.csr.common.flow.service.impl
 * Date:2014-4-8下午5:08:29
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.dao.HistoryFormDao;
import org.csr.common.flow.domain.HistoryForm;
import org.csr.common.flow.domain.TaskTemp;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.service.HistoryFormService;
import org.csr.common.flow.service.UserTaskInstanceService;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Direction;
import org.csr.core.page.Page;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.PageRequest;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName: HistoryFormServiceImpl.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-4-8下午5:08:29 <br/>
 * 
 * @author yjY <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Service("historyFormService")
public class HistoryFormServiceImpl extends
		SimpleBasisService<HistoryForm, Long> implements HistoryFormService {

	@Resource
	HistoryFormDao historyFormDao;
	@Resource
	UserTaskInstanceService userTaskInstanceService;

	@Override
	public BaseDao<HistoryForm, Long> getDao() {
		return this.historyFormDao;
	}

	@Override
	public HistoryForm findEndByUserTaskInstanceId(Long userTaskInstanceId) {
		Page page=new PageRequest(1, 1,Direction.DESC, "processingTime");
		page.toParam().add(new AndEqParam("userTaskInstanceId",userTaskInstanceId));
		List<HistoryForm> pageList=historyFormDao.findAll(page);
		if(ObjUtil.isNotEmpty(pageList)){
			return pageList.get(0);
		}
		return null;
	}
	
	@Override
	public List<HistoryForm> findByUserTaskInstanceId(Long userTaskInstanceId,List<String> nodeTypes) {
		if(ObjUtil.isEmpty(userTaskInstanceId)){
			return new ArrayList<>(0);
		}
		List<Param> params=new ArrayList<Param>();
		params.add(new AndEqParam("userTaskInstanceId",userTaskInstanceId));
		if(ObjUtil.isNotEmpty(nodeTypes)){
			params.add(new AndInParam("nodeType",nodeTypes));
		}
		return historyFormDao.findByParam(params.toArray(new Param[params.size()]));
	}
	
	@Override
	public HistoryForm save(Long userTaskInstanceId,String opinion,Form form) {
		if (ObjUtil.isEmpty(userTaskInstanceId)) {
			Exceptions.service("2006011", "您的用户任务实例id不能为空");
		}
		UserTaskInstance userTaskInstance = userTaskInstanceService.findById(userTaskInstanceId);
		// 判断id是否为空
		if (ObjUtil.isEmpty(userTaskInstance)) {
			Exceptions.service("2006011", "您的用户任务实例id不能为空");
		}
		if (ObjUtil.isEmpty(form)) {
			Exceptions.service("2006012", "您的表单id不能为空");
		}
		// 保存任务处理流程历史记录
		HistoryForm historyForm = new HistoryForm();;
		historyForm.setFormObj(form);
		historyForm.setClassName(form.getClass().getName());
		historyForm.setFormId(form.getId());
		historyForm.setProcessingTime(new Date());
		historyForm.setOpinion(opinion);
		// 当前节点id
		historyForm.setTaskNodeId(userTaskInstance.getCurrent().getId());
		historyForm.setTaskNodeName(userTaskInstance.getCurrent().getName());
		historyForm.setNodeType(userTaskInstance.getCurrent().getNodeType());
		historyForm.setTaskNodeIntervalTime(userTaskInstance.getCurrent().getIntervalTime());
		historyForm.setTaskNodeIsAllApproval(userTaskInstance.getCurrent().getIsAllApproval());
		historyForm.setTaskNodeIsClose(userTaskInstance.getCurrent().getIsClose());
		historyForm.setTaskNodeIsEdit(userTaskInstance.getCurrent().getIsEdit());
		historyForm.setTaskNodeIsOrderApproval(userTaskInstance.getCurrent().getIsOrderApproval());
		historyForm.setTaskNodeRemark(userTaskInstance.getCurrent().getRemark());
		historyForm.setTaskNodeStartTime(userTaskInstance.getCurrent().getStartTime());
		// 当前节点的
		if(ObjUtil.isEmpty(UserSessionContext.getUserSession())){
			historyForm.setCurrentBy(userTaskInstance.getCreatedBy());
			historyForm.setCurrentLoginName("");
			historyForm.setCurrentName("");
		}else{
			historyForm.setCurrentBy(UserSessionContext.getUserSession().getUserId());
			historyForm.setCurrentLoginName(UserSessionContext.getUserSession().getLoginName());
			historyForm.setCurrentName(UserSessionContext.getUserSession().getUserName());
		}
		historyForm.setUserTaskInstanceId(userTaskInstance.getId());
		//模板信息
		historyForm.setTaskTempId(userTaskInstance.getTaskTempId());
		TaskTemp taskTemp=userTaskInstance.getTaskInstance().getTaskTemp();
		if(ObjUtil.isNotEmpty(taskTemp)){
			historyForm.setTaskTempName(taskTemp.getName());
			historyForm.setStarTtaskNodeId(taskTemp.getStart().getId());
			historyForm.setStarTtaskNodeName(taskTemp.getStart().getName());
			historyForm.setEndTaskNodeId(taskTemp.getEnd().getId());
			historyForm.setEndTaskNodeName(taskTemp.getEnd().getName());
		}
		return getDao().save(historyForm);
	}


}
