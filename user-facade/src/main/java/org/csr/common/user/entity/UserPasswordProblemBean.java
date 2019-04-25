/**
 * Project Name:training
 * File Name:UserJobs.java
 * Package Name:org.csr.system.domain
 * Date:2015年1月4日下午8:21:52
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.entity;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserPasswordProblem;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

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
public class UserPasswordProblemBean extends VOBase<Long> {

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 3781897542617973247L;
	Long id;
	Long userId;
	String userLoginName;
	String userName;
	/**
	 * 问题
	 */
	String question;
	/**
	 * 回答
	 */
	String answer;
	
	
	private String userEmail;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id=id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public static UserPasswordProblemBean wrapBean(UserPasswordProblem doMain) {
		UserPasswordProblemBean bean = new UserPasswordProblemBean();
		bean.setId(doMain.getId());
		User user=doMain.getUser();
		if(ObjUtil.isNotEmpty(user)){
			bean.setUserId(user.getId());
			bean.setUserLoginName(user.getLoginName());
			bean.setUserName(user.getName());
			bean.setUserEmail(user.getEmail());
		}
		bean.setQuestion(doMain.getQuestion());
		bean.setAnswer(doMain.getAnswer());
		return bean;
	}
}

