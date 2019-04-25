/**
 * Project Name:exam
 * File Name:UserTaskInstance.java
 * Package Name:org.csr.common.flow.domain
 * Date:2015年8月17日下午4:10:06
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;


/**
 * ClassName: UserTaskInstance.java <br/>
 * System Name：    elearning系统 <br/>
 * Date:     2015年8月17日下午4:10:06 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 */
public interface UserTaskImpl {

	public abstract Long getId();

	public abstract Long getFormId();

	public abstract void setFormId(Long formId);

	public abstract Form getFormObj();

	public abstract void setFormObj(Form formObj);

	public abstract String getClassName();

	public abstract void setClassName(String className);

	public abstract String getOpinion();

	public abstract void setOpinion(String opinion);

}
