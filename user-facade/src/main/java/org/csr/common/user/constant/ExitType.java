package org.csr.common.user.constant;

/**
 * ClassName:ExitType.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface ExitType {
	// 1 正常关闭退出；2 session失效退出；3 被踢下线；
	/**
	 * 正常关闭退出
	 */
	Byte NORMAL = 1;
	/**
	 * session失效退出
	 */
	Byte INVALID = 2;
	/**
	 * 被踢下线
	 */
	Byte KICK = 3;
}
