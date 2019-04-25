package org.csr.common.user.service;


import org.csr.common.flow.service.FormSerivce;

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
public interface UserApprovalService extends FormSerivce  {

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
