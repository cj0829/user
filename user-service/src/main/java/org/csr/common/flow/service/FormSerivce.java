/**
 * Project Name:common
 * File Name:TaskNodeSerivce.java
 * Package Name:org.csr.common.flow.service
 * Date:2014年3月14日上午11:26:59
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.service;

import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.user.safeResource.Category;

/**
 * ClassName: TaskNodeSerivce.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日上午11:26:59 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 表单服务<br/>
 *        公用方法描述： <br/>
 * 
 */
public interface FormSerivce {
	
	/**
	 * 当需要验证的时候，前期操作
	 * beforeForm: 描述方法的作用 <br/>
	 * @author caijin
	 * @param userTaskInstance
	 * @since JDK 1.7
	 */
	public void beforeForm(UserTaskInstance userTaskInstance);
	
	public boolean saveForm(UserTaskInstance userTaskInstance);

	/**
	 * 处理关闭流程时，需要
	 * @author caijin
	 * @param userTaskInstance
	 * @since JDK 1.7
	 */
	public void updateCloseTask(UserTaskInstance userTaskInstance);

	/**
	 * 创建流程之后，表单的处理方式
	 * @author caijin
	 * @param userTaskInstance
	 * @since JDK 1.7
	 */
	public void createTaskInstanceAfter(UserTaskInstance userTaskInstance);

	/**
	 * 当节点被删除，需要掉，并且要删除节点，下的子数据
	 * @author caijin
	 * @param categoryItem
	 * @since JDK 1.7
	 */
	public void deleteCategoryItemChild(Category categoryItem);

	/**
	 * 检查库节点是否存在，数据
	 * @author huayj
	 * @param categoryItem
	 * @return void
	 * @date&time 2015-12-7 下午4:07:21
	 * @since JDK 1.7
	 */
	public long checkCategoryItemHasChild(Category categoryItem);
	/**
	 * 处理流程
	 * @author caijin
	 * @param userTaskInstance
	 * @since JDK 1.7
	 */
	public void handleFolw(UserTaskInstance userTaskInstance);

	/**
	 * 剪切修改域
	 * @param categoryItem
	 */
	public void updateOrg(Long categoryItemId, Long toParentId,Long orgId);

	/**
	 * 合并库的操作
	 * @param categoryItem
	 */
	public void updateMergeCurrentNode(Long categoryItemId, Long toParentId,Long orgId);

	
}
