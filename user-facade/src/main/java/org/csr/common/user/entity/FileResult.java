/**
 * Project Name:common
 * File Name:FileResult.java
 * Package Name:org.csr.common.user.result
 * Date:2014-3-2下午10:21:14
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.entity;

import org.csr.core.web.bean.ResultJson;

/**
 * ClassName: FileResult.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-3-2下午10:21:14 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
public class FileResult extends ResultJson {
	
	private static final long serialVersionUID = -4780948235741547546L;
	private Long attachmentId;
	private String attachmentPath;
	
	public Long getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Long attachmentId) {
		this.attachmentId = attachmentId;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}
	
	
}
