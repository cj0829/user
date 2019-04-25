/**
 * Project Name:common
 * File Name:TaskInstanceAction.java
 * Package Name:org.csr.common.flow.action
 * Date:2014年3月14日下午1:48:35
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.action;

import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * ClassName: TaskInstanceAction.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月14日下午1:48:35 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/taskTemp")
public class TaskTempAction extends BasisAction {
//	TaskTempService taskTempService;

	final String preUpdate="flow/taskTemp/update";
	/**
	 * preList:消息列表页面<br/>
	 * @author Administrator
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="/{moduleName}/preUpdate",method=RequestMethod.GET)
	public String preUpdate(@PathVariable("moduleName") String moduleName,
			@RequestParam(value="taskTempId",required=false)Long taskTempId){
		setRequest("taskTempId", taskTempId);
		setRequest("moduleName", moduleName);
		return preUpdate;
	}
}
