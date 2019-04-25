/**
 * Project Name:exam
 * File Name:ReleationChainBean.java
 * Package Name:org.csr.common.flow.result
 * Date:2015年11月30日下午12:27:45
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;

import java.util.ArrayList;
import java.util.List;

import org.csr.core.web.bean.VOBase;

/**
 * ClassName:ReleationChainBean.java <br/>
 * System Name： 签到系统 <br/>
 * Date: 2015年11月30日下午12:27:45 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
public class ReleationChainBean extends VOBase<Long> {
	/**
	 * (用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	private Long taskNodeId;
	private List<Long> processorIds = new ArrayList<Long>();

	public ReleationChainBean(Long taskNodeId){
		this.taskNodeId=taskNodeId;
	}
	
	public Long getTaskNodeId() {
		return taskNodeId;
	}


	public List<Long> getProcessorIds() {
		return processorIds;
	}


}
