package org.csr.common.user.entity;

import java.io.Serializable;

/**
 * ClassName:UploadFileResult.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:08:13 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class UploadFileResult implements Serializable {
	/**
	 * (用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
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
