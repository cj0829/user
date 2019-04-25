/**
 * Project Name:common
 * File Name:TaskTempService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日下午2:04:04
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.flow.service;

import java.util.List;

import org.csr.common.flow.domain.TaskTemp;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName: TaskTempService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014年3月14日下午2:04:04 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface TaskTempService extends BasisService<TaskTemp, Long>{

	/**
	 * 创建考试的审批流程模板
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public TaskTemp createExamFlow(String taskTempName,Long orgId);
	
	public void setStartNode(TaskTemp taskTemp);

	
	/**
	 * 查询用户能够处理，几个流程模板的数据。
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<TaskTemp> findByUser(Long userId);
	

	/**
	 * 查询用户能够处理，几个流程模板的数据。
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findIdByUser(Long userId);
}

