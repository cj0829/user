/**
 * Project Name:common
 * File Name:TaskTempDao.java
 * Package Name:org.csr.common.flow.dao
 * Date:2014年3月14日下午3:15:50
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.flow.dao;

import java.util.List;

import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName: TaskTempDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014年3月14日下午3:15:50 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface UserTaskInstanceDao extends BaseDao<UserTaskInstance,Long> {

	/**
	 * @author caijin
	 * @param ids
	 * @since JDK 1.7
	 */
	int deleteByTaskInstanceIds(List<Long> ids);

	/**
	 * 查询返回流程ids
	 * @author caijin
	 * @param taskInstanceId
	 * @return
	 * @since JDK 1.7
	 */
	List<Long> findIdsByByTaskInstanceId(Long taskInstanceId);

	/**
	 * 查询所有的流程id。
	 * @author caijin
	 * @param taskTempId
	 * @param userIds
	 * @param chainIds
	 * @param param 
	 * @return
	 * @since JDK 1.7
	 */
	List<Long> findIdApproveList(List<Long> taskTempIds, List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds, List<Param> param);

	/**
	 * 根据，用户，或者是节点，查询出当前下能够审批的全部数据,用户或节点，采用或者。
	 * @author caijin
	 * @param page
	 * @param taskTempId
	 * @param userIds
	 * @param chainIds
	 * @return
	 * @since JDK 1.7
	 */
	PagedInfo<UserTaskInstance> findPageByTaskTempIdAndUserIdsAndCurrentIds(Page page,List<Long> taskTempId,List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds);
	/**
	 * 根据，用户，或者是节点，查询出当前下能够审批的全部数据,用户或节点，采用或者。
	 * @author caijin
	 * @param page
	 * @param taskTempId
	 * @param userIds
	 * @param chainIds
	 * @return
	 * @since JDK 1.7
	 */
	List<UserTaskInstance> findListByTaskTempIdAndUserIdsAndCurrentIds(List<Long> taskTempId,List<Long> formIds,List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds);

	/**
	 * countByTaskTempIdAndUserIdsAndCurrentIds: 统计驳回个数 <br/>
	 * @author Administrator
	 * @param taskTempId
	 * @param taskNodeApprovalChainList
	 * @param chainIds
	 * @return
	 * @since JDK 1.7
	 */
	Long countByTaskTempIdAndUserIdsAndCurrentIds(List<Long> taskTempId,List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> chainIds);

}

