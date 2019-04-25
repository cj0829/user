package org.csr.common.user.facade;


import org.csr.common.user.domain.UserMore;
import org.csr.common.user.entity.UserMoreBean;
import org.csr.core.persistence.service.BasisFacade;

/*
 * @(#)Sss.java 
 *       
 * ClassName:UserMore.java <br/>
 * Date: Thu Dec 01 10:06:41 CST 2016 <br/>
 * @author liurui <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface UserMoreFacade extends BasisFacade<UserMoreBean, Long>,UserInitData{

	/**
	 * 根据所属的用户查更多信息
	 * @param userId
	 * @return
	 */
	public UserMoreBean findByUserId(Long userId);

	/**
	 * 修改或创建用户
	 * @param more
	 */
	public void updateOrCreateUser(UserMore more,Long userId);

}
