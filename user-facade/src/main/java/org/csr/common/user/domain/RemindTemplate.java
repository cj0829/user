package org.csr.common.user.domain;

// Generated Oct 30, 2011 3:40:55 PM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 邮件或短信模板
 */
@Entity
@Table(name = "u_RemindTemplate")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="邮件或短信模板",en="pmt_common_RemindTemplate")
public class RemindTemplate extends SimpleDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9031990078684483144L;
	private Long id;
	private String code;
	private String mailTemplate;
	private String smsTemplate;
	private String remark;  

	public RemindTemplate() {
	}

	public RemindTemplate(Long id) {
		this.id = id;
	}

	public RemindTemplate(Long id, String code, String mailTemplate,
			String smsTemplate, String remark) {
		this.id = id;
		this.code = code;
		this.mailTemplate = mailTemplate;
		this.smsTemplate = smsTemplate;
		this.remark = remark;  
	}
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "code", length = 64)
	@Comment(ch="模板编码",en="code",search=true)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "mailTemplate", length = 4000)
	@Comment(ch="邮件模板",en="mailTemplate")
	public String getMailTemplate() {
		return this.mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}

	@Column(name = "smsTemplate", length = 4000)
	@Comment(ch="短信模板",en="smsTemplate")
	public String getSmsTemplate() {
		return this.smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	@Column(name = "remark", length = 128) 
	@Comment(ch="备注",en="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
