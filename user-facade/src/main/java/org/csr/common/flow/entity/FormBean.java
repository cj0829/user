package org.csr.common.flow.entity;

import org.csr.core.web.bean.VOBase;

/**
 * ClassName:Form.java <br/>
 * System Name： <br/>
 * Date: 2014-3-7下午2:05:16 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： 表单 <br/>
 *        公用方法描述： <br/>
 */
public abstract class FormBean extends VOBase<Long> implements Form {

	/**
	 * (用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected String name;
	protected Integer operType;
	
	public FormBean() {
	
	}
	
	public FormBean(Long id) {
		this.id = id;
	}
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
	private UserTaskInstanceBean userTaskInstance;
	public UserTaskInstanceBean getUserTaskInstance() {
		return userTaskInstance;
	}

	public void setUserTaskInstance(UserTaskImpl userTaskInstance) {
		this.userTaskInstance = (UserTaskInstanceBean) userTaskInstance;
	}
	public Integer getOperType(){
		return operType;
	};

	public void setOperType(Integer operType){
		this.operType=operType;
	};
}
