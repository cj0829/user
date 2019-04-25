package org.csr.common.user.service;


import org.csr.common.flow.service.FormSerivce;
import org.csr.common.user.domain.UserCommand;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:UserCommand.java <br/>
 * Date: Thu Sep 17 11:29:36 CST 2015 <br/>
 * @author huayj-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface UserCommandService extends BasisService<UserCommand, Long>, FormSerivce  {

	/**
	 * save: 保存方法<br/>
	 * @author huayj-PC
	 * @param userCommand 
	 */
	public void save(UserCommand userCommand);

	/**
	 * update: 编辑方法<br/>
	 * @author huayj-PC
	 * @param userCommand 
	 */
	public void update(UserCommand userCommand);


	public boolean checkNameIsExist(Long id, String name);

	/**
	 * 
	 * @author caijin
	 * @param userCommandIds
	 * @return
	 * @since JDK 1.7
	 */
	public int updatePass(Long[] userCommandIds);

	public int updateRefusa(Long[] userCommandIds, String explain);

}
