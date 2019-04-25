/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service;

import java.util.List;

import org.csr.common.flow.domain.TaskNode;
import org.csr.core.persistence.service.BasisService;

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
public interface TaskNodeService extends BasisService<TaskNode, Long> {

	/**
	 * 根据模板查询全部的数据
	 * 
	 * @author caijin
	 * @param taskTempId
	 * @return
	 * @since JDK 1.7
	 */
	List<TaskNode> findByTaskTempId(Long taskTempId);
	/**
	 * 根据模板查询全部的数据
	 * @author caijin
	 * @param taskTempId
	 * @return
	 * @since JDK 1.7
	 */
	List<TaskNode> findByTaskTempIdNotStartEnd(Long taskTempId);

}
