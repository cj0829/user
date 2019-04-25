/**
 * Project Name:common
 * File Name:MenuService.java
 * Package Name:org.csr.common.user.service
 * Date:2014-2-17下午10:05:48
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.Menu;
import org.csr.core.persistence.service.BasisService;
import org.csr.core.security.resource.MenuNodeBean;

/**
 * ClassName:BasisServiceImpl <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface MenuService extends RegistrationMenuService,BasisService<Menu, Long>{

	List<MenuNodeBean> findAllMenu();
	/**
	 * update: 更改菜单 <br/>
	 * @param menu
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	public void update(Menu menu);

	/**
	 * 
	 * deleteSelfAndChildren: 删除自身和子类 <br/>
	 * @param ids
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	public void deleteSelfAndChildren(List<Long> ids);

	
	public MenuNodeBean getCacheById(Long dufHome);
	



}

