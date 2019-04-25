/**
 * Project Name:common
 * File Name:TaskTempDao.java
 * Package Name:org.csr.common.flow.dao
 * Date:2014年3月14日下午3:15:50
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.dao.impl;

import org.csr.common.flow.dao.TaskTempDao;
import org.csr.common.flow.domain.TaskTemp;
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
@Repository("taskTempDao")
public class TaskTempDaoImpl extends JpaDao<TaskTemp, Long> implements TaskTempDao{

	@Override
	public Class<TaskTemp> entityClass() {
		return TaskTemp.class;
	}

}
