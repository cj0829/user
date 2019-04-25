package org.csr.common.user.service;


import java.util.List;

import org.csr.common.user.domain.UserPasswordProblem;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:UserPasswordProblem.java <br/>
 * Date: Fri Mar 20 20:33:20 CST 2015 <br/>
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface UserPasswordProblemService extends BasisService<UserPasswordProblem, Long>{

	
	/**
	 * save: 保存方法<br/>
	 * @author n-caijin
	 * @param userPasswordProblem 
	 */
	public void save(UserPasswordProblem userPasswordProblem);

	/**
	 * savePasswordProblem: 根据用户id和保存问题
	 * @author caijin
	 * @param userId
	 * @param question
	 * @since JDK 1.7
	 */
	public void savePasswordProblem(Long userId, String question);
	/**
	 * update: 编辑方法<br/>
	 * @author n-caijin
	 * @param userPasswordProblem 
	 */
	public void update(UserPasswordProblem userPasswordProblem);



	public List<UserPasswordProblem> findByUserId(Long userId);

	boolean checkNameIsExist(Long id, Long userId, String question);

	public void updateUserAndPasswordProblem(Long userId, String oldPassword,String password, Long problemId, String problemName, String answer);

	public UserPasswordProblem findByUserIdAndStatus(Long userId, Byte yes);

	public UserPasswordProblem findMapByUserLoginName(String userLoginName);

	

}
