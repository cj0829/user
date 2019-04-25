/**
 * Project Name:exam
 * File Name:BusinessStorage.java
 * Package Name:org.csr.common.flow.support
 * Date:2015年7月8日下午5:56:43
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.flow.support;

import org.csr.common.flow.domain.UserTaskInstance;

/**
 * ClassName: BusinessStorage.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年7月8日下午5:56:43 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 */
public interface BusinessStorage {
	boolean save(UserTaskInstance userTaskInstance);

}
