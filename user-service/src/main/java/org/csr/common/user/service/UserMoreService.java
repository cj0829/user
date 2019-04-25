package org.csr.common.user.service;


import org.csr.common.user.domain.UserMore;
import org.csr.core.persistence.service.BasisService;

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
public interface UserMoreService extends BasisService<UserMore, Long>{
	/**
	 * save: 保存方法<br/>
	 * @author liurui
	 * @param userMore 
	 */
	public void save(UserMore userMore);

	/**
	 * update: 编辑方法<br/>
	 * @author liurui
	 * @param userMore 
	 */
	public void update(UserMore userMore);


	public boolean checkNameIsExist(Long id, String name);

}
