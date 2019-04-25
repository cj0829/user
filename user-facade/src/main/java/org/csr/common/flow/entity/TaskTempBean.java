/**
 * Project Name:exam
 * File Name:TaskTempBean.java
 * Package Name:org.csr.common.flow.result
 * Date:2015年6月29日上午10:20:18
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.entity;

import org.csr.core.web.bean.VOBase;

/**
 * ClassName: TaskTempBean.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年6月29日上午10:20:18 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 */
public class TaskTempBean extends VOBase<Long> {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/** 工作流程模板编号 */
	private Long id;
	/** 工作流程模板名称 */
	private String name;
	/** 开始流程 */
	private Long startId;
	private String startName;
	/** 结束流程 */
	private Long endId;
	private String endName;
	/** 是否有效 */
	private boolean valid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStartId() {
		return startId;
	}

	public void setStartId(Long startId) {
		this.startId = startId;
	}

	public String getStartName() {
		return startName;
	}

	public void setStartName(String startName) {
		this.startName = startName;
	}

	public Long getEndId() {
		return endId;
	}

	public void setEndId(Long endId) {
		this.endId = endId;
	}

	public String getEndName() {
		return endName;
	}

	public void setEndName(String endName) {
		this.endName = endName;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/*public static TaskTempBean toBean(TaskTemp doMain) {
		TaskTempBean bean = new TaskTempBean();
		bean.setId(doMain.getId());
		bean.setName(doMain.getName());
		if (ObjUtil.isNotEmpty(doMain.getStart())) {
			bean.setStartId(doMain.getId());
			bean.setStartName(doMain.getName());
		}
		if (ObjUtil.isNotEmpty(doMain.getEnd())) {
			bean.setEndId(doMain.getEnd().getId());
			bean.setEndName(doMain.getEnd().getName());
		}
		bean.setValid(doMain.getValid());
		return bean;

	}*/
}
