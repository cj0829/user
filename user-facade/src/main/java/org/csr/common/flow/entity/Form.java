/**
 * Project Name:exam
 * File Name:Form.java
 * Package Name:org.csr.common.flow.support
 * Date:2015-7-22下午2:09:21
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;

import org.csr.core.Persistable;

/**
 * ClassName:Form.java <br/>
 * System Name： 签到系统 <br/>
 * Date: 2015-7-22下午2:09:21 <br/>
 * 
 * @author Administrator <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
public interface Form extends Persistable<Long>{

	public UserTaskImpl getUserTaskInstance();
	
	public String getName();
	
	public void setUserTaskInstance(UserTaskImpl userTaskInstance);
	
	public Integer getOperType();

	public void setOperType(Integer operType);

}
