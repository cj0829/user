package org.csr.common.user.constant;
/**
 * ClassName:UserType.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 1、超级管理员；2 管理员；3普通用户；
 */
public interface UserRoleType {
	
	/**
	 *  通讯录用户（学生）
	 */
	Byte GENERAL = 1;
	/**
	 * 普通用户
	 */
	Byte ADVANCED=2;
	/**
	 *  管理员
	 */
	Byte ADMIN = 4;
	/**
	 * 超级管理员
	 */
	Byte SUPER = 7;
  
}
