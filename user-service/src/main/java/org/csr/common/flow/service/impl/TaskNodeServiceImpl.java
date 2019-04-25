/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.dao.TaskNodeDao;
import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.flow.service.TaskNodeService;
import org.csr.common.flow.service.TaskTempService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName: TaskTempService.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日下午2:04:04 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Service("taskNodeService")
public class TaskNodeServiceImpl extends SimpleBasisService<TaskNode, Long>
		implements TaskNodeService {

	@Resource
	TaskNodeDao taskNodeDao;
	@Resource
	TaskTempService taskTempService;
	@Resource
	TaskNodeApprovalChainService taskNodeApprovalChainService;

	@Override
	public BaseDao<TaskNode, Long> getDao() {
		return taskNodeDao;
	}

	
	@Override
	public List<TaskNode> findByTaskTempId(Long taskTempId) {
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<TaskNode>(0);
		}
		return taskNodeDao.findByParam(new AndEqParam("taskTemp.id", taskTempId));
	}


	@Override
	public List<TaskNode> findByTaskTempIdNotStartEnd(Long taskTempId) {
		if (ObjUtil.isEmpty(taskTempId)) {
			return new ArrayList<TaskNode>(0);
		}
		return taskNodeDao.findByParam(new AndEqParam("taskTemp.id", taskTempId),new AndInParam("nodeType",ObjUtil.asList(new String[]{"TaskNode"})));
	}


}
