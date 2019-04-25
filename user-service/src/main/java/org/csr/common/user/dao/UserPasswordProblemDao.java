package org.csr.common.user.dao;


import org.csr.common.user.domain.UserPasswordProblem;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:UserPasswordProblem.java <br/>
 * Date: Fri Mar 20 20:33:20 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao接口
 */
public interface UserPasswordProblemDao extends BaseDao<UserPasswordProblem,Long>{

	/**
	 * setNoByUserId: 把用户置为不可用 <br/>
	 * @author caijin
	 * @param userId
	 * @since JDK 1.7
	 */
	void setNoByUserId(Long userId);

	UserPasswordProblem findByUserIdAndStatus(Long userId, Byte status);

	void updatePasswordProblem(Long userId, String answer,Byte status);

	UserPasswordProblem findMapByUserLoginName(String userLoginName);


}
