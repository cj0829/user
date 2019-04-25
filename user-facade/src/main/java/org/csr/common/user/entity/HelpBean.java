package org.csr.common.user.entity;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import org.csr.common.user.domain.Help;
import org.csr.core.web.bean.VOBase;

/**
 * 帮助
 */
public class HelpBean extends VOBase<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7589326938797722765L;
	private Long id;
	private String title;
	private String content;
	private String functionPointCode;

	public HelpBean() {
	}

	public HelpBean(Long id) {
		this.id = id;
	}

	public HelpBean(Long id, String title, String content,
			String functionPointCode) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.functionPointCode = functionPointCode;
	}

	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFunctionPointCode() {
		return functionPointCode;
	}

	public void setFunctionPointCode(String functionPointCode) {
		this.functionPointCode = functionPointCode;
	}
	public static HelpBean wrapBean(Help doMain) {
		HelpBean bean = new HelpBean(doMain.getId());
		bean.setFunctionPointCode(doMain.getFunctionPointCode());
		bean.setTitle(doMain.getTitle());
		bean.setContent(doMain.getContent());
		return bean;
	}

}
