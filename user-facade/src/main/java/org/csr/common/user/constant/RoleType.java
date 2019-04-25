package org.csr.common.user.constant;

/**
 * ClassName:RoleType.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：System 1.系统管理员,Custom 2.自定义角色
 */
public interface RoleType { 
	/**
	 * 系统管理员
	 */
	Byte SYSTEM = 1;
    /**
     * 自定义角色
     */
	Byte CUSTOM = 2;
	   /**
     * 全局角色
     */
	Byte GLOBAL = 3;
}

