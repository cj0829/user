package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 帮助
 */
@Entity
@Table(name = "u_Help")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="帮助",en="pmt_common_Help")
public class Help extends RootDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7589326938797722765L;
	private Long id;
	private String title;
	private String content;
	private String functionPointCode; 
	
	public Help() {
	}

	public Help(Long id) {
		this.id = id;
	}

	public Help(Long id,String title, String content,
			String functionPointCode) {
		this.id = id; 
		this.title = title;
		this.content = content; 
		this.functionPointCode = functionPointCode; 
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
 
	@Column(name = "title", length = 512)
	@Comment(ch="帮助标题")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", length = 16777216)
	@Comment(ch="帮助文档")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	@Column(name = "functionPointCode",length=16) 
	@Comment(ch="功能点code")
	public String getFunctionPointCode() {
		return functionPointCode;
	}

	public void setFunctionPointCode(String functionPointCode) {
		this.functionPointCode = functionPointCode;
	}

}
