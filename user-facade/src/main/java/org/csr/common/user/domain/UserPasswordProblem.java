/**
 * Project Name:training
 * File Name:UserJobs.java
 * Package Name:org.csr.system.domain
 * Date:2015年1月4日下午8:21:52
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.domain.RootDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * ClassName: UserJobs.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2015年1月4日下午8:21:52 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 * 用户取回密码问题提示
 */
@Entity  
@Table(name = "u_UserPasswordProblem")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_common_UserPasswordProblem")
public class UserPasswordProblem extends RootDomain<Long>{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 3781897542617973247L;
	Long id;
	User user;
	/**
	 * 问题
	 */
	String question;
	/**
	 * 回答
	 */
	String answer;
	
	/**
	 * 是否为当前的答案
	 */
	Byte status=YesorNo.NO;
	
	public UserPasswordProblem(){
		
	}
	
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
    @Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "用户职位ID", en = "id", search = true)
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "userId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "用户", en = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@Length(min=1,max=128)
	@Column(name = "question")
	@Comment(ch = "问题", en = "question")
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	@Length(min=1,max=128)
	@Column(name = "answer")
	@Comment(ch = "答案", en = "answer")
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	@Column(name = "status")
	@Comment(ch = "是否选中", en = "status")
	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}
	
}

