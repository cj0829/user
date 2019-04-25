/**
 * Project Name:core
 * File Name:RegistrationMenu.java
 * Package Name:com.pmt.core.security.resource
 * Date:2014-2-18上午10:11:26
 * Copyright (c) 2014, 博海云领版权所有 ,All rights reserved 
*/

package org.csr.common.user.service;

import java.util.List;

import org.csr.core.MenuNode;

/**
 * ClassName:RegistrationMenuService.java <br/>
 * System Name：    博海云领 <br/>
 * Date:     2014-2-18上午10:35:09 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface RegistrationMenuService {
	
	List<? extends MenuNode> findAllMenu();
	/**
	 * findMenuByType: 根据菜类型查询 <br/>
	 * @author caijin
	 * @param type
	 * @return
	 * @since JDK 1.7
	 */
	List<? extends MenuNode> findMenuByType(Long type);
	
	
}

