/**
 * Project Name:user-facade
 * File Name:RemindTemplateBean.java
 * Package Name:org.csr.common.user.entity
 * Date:2016-9-1下午3:27:55
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.entity;

import org.csr.common.user.domain.RemindTemplate;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:RemindTemplateBean.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午3:27:55 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public class RemindTemplateBean extends VOBase<Long>{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String code;
	private String mailTemplate;
	private String smsTemplate;
	private String remark;  

	public RemindTemplateBean(){}
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMailTemplate() {
		return this.mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	public String getSmsTemplate() {
		return this.smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static RemindTemplateBean wrapBean(RemindTemplate doMain) {
		RemindTemplateBean bean = new RemindTemplateBean();
		bean.setId(doMain.getId());
		bean.setCode(doMain.getCode());
		bean.setMailTemplate(doMain.getMailTemplate());
		bean.setSmsTemplate(doMain.getSmsTemplate());
		bean.setRemark(doMain.getRemark());
		return bean;
	}
}

