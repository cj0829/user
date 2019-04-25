/**
 * Project Name:exam
 * File Name:FlowStrategy.java
 * Package Name:org.csr.exam.domain
 * Date:2015年6月23日上午10:37:12
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.common.flow.domain.FlowStrategy;
import org.csr.core.Comment;
import org.csr.core.constant.YesorNo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * ClassName: FlowStrategy.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月23日上午10:37:12 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 *        库策略，
 */
@Entity
@Table(name = "u_UserRegisterStrategy")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch = "用户注册策略表",en="pmt_common_UserStrategy")
public class UserRegisterStrategy extends FlowStrategy {

	/**
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = -2940739518232668347L;
	/** 用户库策略编号 */
	private Long id;
	/** 导入用户是否需要审批 */
	private Byte isRegister = YesorNo.NO;
	
	public UserRegisterStrategy(){
	}
	
	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "用户库策略ID", en = "id", search = true)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "isRegister", nullable = false)
	@Comment(ch = "导入用户是否需要审批", en = "isImport")
	public Byte getIsRegister() {
		return isRegister;
	}

	public void setIsRegister(Byte isRegister) {
		this.isRegister = isRegister;
	}

}
