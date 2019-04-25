/**
 * Project Name:common
 * File Name:TaskInstanceStatus.java
 * Package Name:org.csr.common.flow.constant
 * Date:2014年4月7日下午3:50:12
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.constant;

/**
 * ClassName: TaskInstanceStatus.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年4月7日下午3:50:12 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 *        流程1.激活状态，2.是否暂停流程,3,流程结束，4，异常结束
 */
public interface TaskInstanceStatus {
	/** 激活 */
	Integer ACTIVATING = 1;
	/** 暂停 */
	Integer PAUSE = 2;
	/** 正常结束 */
	Integer NORMALEND = 3;
	/** 异常结束 */
	Integer ABEND = 4;
}
