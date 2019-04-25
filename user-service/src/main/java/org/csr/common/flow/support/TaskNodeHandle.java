/**
 * Project Name:common
 * File Name:TaskNodeHandle.java
 * Package Name:org.csr.common.flow.support
 * Date:2014-4-9下午4:24:39
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.flow.support;

import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.domain.UserTaskInstance;

/**
 * ClassName: TaskNodeHandle.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-4-9下午4:24:39 <br/>
 * @author   yjY <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface TaskNodeHandle {

	/**
	 * end: 流程处理完毕的回调 <br/>
	 * @author yjY
	 * @param userTaskInstance
	 * @param toNode
	 * @since JDK 1.7
	 */
	void end(UserTaskInstance userTaskInstance, TaskNode toNode);

	/**
	 * process: 流程处理中的回调 <br/>
	 * @author yjY
	 * @param userTaskInstance
	 * @param taskNode
	 * @since JDK 1.7
	 */
	void process(UserTaskInstance userTaskInstance, TaskNode taskNode);

}

