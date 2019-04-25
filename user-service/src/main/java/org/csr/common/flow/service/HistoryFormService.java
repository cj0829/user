/**
 * Project Name:common
 * File Name:HistoryFormService.java
 * Package Name:org.csr.common.flow.service
 * Date:2014-4-8下午5:07:23
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service;

import java.util.List;

import org.csr.common.flow.domain.HistoryForm;
import org.csr.common.flow.entity.Form;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName: HistoryFormService.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-4-8下午5:07:23 <br/>
 * 
 * @author yjY <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *功能描述： <br/>
 *公用方法描述： <br/>
 * 
 */
public interface HistoryFormService extends BasisService<HistoryForm, Long> {

	/**
	 * 保存历史
	 * @author caijin
	 * @param taskProcess
	 * @param form
	 * @return
	 * @since JDK 1.7
	 */
	HistoryForm save(Long userTaskInstanceId, String opinion, Form form);

	/**
	 * 查询最后一次
	 * @param userTaskInstanceId
	 * @return
	 */
	HistoryForm findEndByUserTaskInstanceId(Long userTaskInstanceId);

	/**
	 * 查询，审批历史记录
	 * @param userTaskInstanceId
	 * @return
	 */
	List<HistoryForm> findByUserTaskInstanceId(Long userTaskInstanceId,List<String> nodeTypes);
}
