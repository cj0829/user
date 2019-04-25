package org.csr.common.flow.domain;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.csr.common.flow.entity.Form;
import org.csr.common.flow.entity.UserTaskImpl;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;

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
@MappedSuperclass
public abstract class FormDomain extends SimpleDomain<Long> implements Form {
	/**
	 * (用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;

	private UserTaskInstance userTaskInstance;
	
	protected Integer operType;
	
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name="userTaskInstanceId", referencedColumnName="id",nullable=true,insertable=true,updatable=true) })
    @Comment(ch="流程id",en="userTaskInstanceId")
	public UserTaskInstance getUserTaskInstance() {
		return userTaskInstance;
	}

	public void setUserTaskInstance(UserTaskImpl userTaskInstance) {
		this.userTaskInstance = (UserTaskInstance) userTaskInstance;
	}
	
	@Column(name = "operType")
	@Comment(ch="操作类型",en="operType")
	public Integer getOperType(){
		return operType;
	};

	public void setOperType(Integer operType){
		this.operType=operType;
	};
}
