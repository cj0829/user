/**
 * Project Name:common
 * File Name:AuthenticationMode.java
 * Package Name:org.csr.common.user.constant
 * Date:2014-1-29上午10:07:21
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.constant;

/**
 * ClassName:AuthenticationMode.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 功能点授权方式 <br/>
 *        NotAllowed、不允许授权，Default、默认已授权，Must、需要授权
 */
public interface AuthenticationMode {
	/**
	 * 不允许授权
	 */
	Byte NOTALLOWED = 1;
	/**
	 * 默认已授权
	 */
	Byte DEFAULT = 2;
	/**
	 * 需要授权
	 */
	Byte MUST = 3;
}
