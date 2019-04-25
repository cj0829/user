/**
 * Project Name:common
 * File Name:TaskTempDao.java
 * Package Name:org.csr.common.flow.dao
 * Date:2014年3月14日下午3:15:50
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.dao.impl;

import org.csr.common.flow.dao.TaskInstanceDao;
import org.csr.common.flow.domain.TaskInstance;
import org.csr.core.persistence.query.jpa.JpaDao;
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
 *  功能描述： <br/>
 *  公用方法描述： <br/>
 * 
 */
@Repository("taskInstanceDao")
public class TaskInstanceDaoImpl extends JpaDao<TaskInstance, Long> implements TaskInstanceDao{

	@Override
	public Class<TaskInstance> entityClass() {
		return TaskInstance.class;
	}
	
}
