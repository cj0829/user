/**
 * Project Name:common
 * File Name:StartNode.java
 * Package Name:org.csr.common.flow.domain
 * Date:2014-3-7下午2:27:03
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.domain;

import javax.persistence.Entity;

import org.csr.core.constant.YesorNo;

/**
 * ClassName: StartNode.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-3-7下午2:27:03 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 开始节点 <br/>
 *        公用方法描述： <br/>
 * 
 */
@Entity
public class StartNode extends TaskNode {
	private static final long serialVersionUID = 1L;

	public StartNode() {
		super();
		this.nodeType = "StartNode";
		setIsEdit(YesorNo.YES);
		setIsClose(YesorNo.YES);
	}

	public StartNode(String name) {
		super(name);
		this.nodeType = "StartNode";
		setIsEdit(YesorNo.YES);
		setIsClose(YesorNo.YES);
	}

}
