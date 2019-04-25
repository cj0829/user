/**
 * Project Name:common
 * File Name:MenuDao.java
 * Package Name:org.csr.common.user.dao
 * Date:2014-2-17下午10:17:52
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.Menu;
import org.csr.core.MenuNode;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName: MenuDao.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
public interface MenuDao extends BaseDao<Menu,Long> {

	/**
	 * findByUserId: 根据用户查询菜单，根据权限来显示菜单，如果用户有此权限，<br/>
	 * 
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	List<? extends MenuNode> findByUserId(Long userId);

	public List<? extends MenuNode> findChildById(Long id);

	public List<? extends MenuNode> findChilds(Long... ids);

	/**
	 * 查询所有菜单通过rank字段排序。
	 * @return
	 */
	List<Menu> findAllOrderByRank();
}
