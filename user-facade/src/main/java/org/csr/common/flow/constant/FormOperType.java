/**
 * Project Name:exam
 * File Name:FormOperType.java
 * Package Name:org.csr.common.flow.constant
 * Date:2015年7月10日下午3:11:19
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.constant;

/**
 * ClassName: FormOperType.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年7月10日下午3:11:19 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 *        标题的操作方式(相当于，发送的命令方式，可以是添加，修改，删除，发布，合并)
 */
public interface FormOperType {
	/** 新建操作 */
	Integer ADD = 1;
	/** 修改操作 */
	Integer UPDATE = 2;
	/** 删除操作 */
	Integer DELETE = 3;
	/**发布操作*/
	Integer RELEASE = 4;
	/**合并操作*/
	Integer MERGE = 5;
	/**共享操作*/
	Integer SHARE = 6;
	/**移动操作*/
	Integer MOVE = 7;
	/**导入操作*/
	Integer IMPORT = 8;
	/**变更库操作*/
	Integer CHANGECATEGORY = 9;
	/**考试申请操作*/
	Integer APPLICANT = 10;
	/**考试申请操作*/
	Integer REGUSER = 11;
}
