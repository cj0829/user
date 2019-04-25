/**
 * Project Name:common
 * File Name:OrganizationStatus.java
 * Package Name:org.csr.common.user.constant
 * Date:2014-1-27下午1:28:25
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.constant;
/**
 * ClassName:OrganizationStatus.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述： 机构状态类型 <br/>
 * Activating：激活状态
 * Freeze：冻结状态
 */
public interface OrganizationStatus {
	/**
	 * 激活状态
	 */
	Byte ACTIVATING = 1;
	/**
	 * 冻结状态
	 */
	Byte FREEZE = 2;
}

