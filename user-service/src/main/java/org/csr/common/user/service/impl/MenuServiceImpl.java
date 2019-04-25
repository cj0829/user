/**
 * Project Name:common
 * File Name:MenuServiceImpl.java
 * Package Name:org.csr.common.user.service.impl
 * Date:2014-2-17下午10:08:02
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.csr.common.user.dao.MenuDao;
import org.csr.common.user.domain.Menu;
import org.csr.common.user.service.MenuService;
import org.csr.core.MenuNode;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName: MenuServiceImpl.java <br/>
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
@Service("menuService")
public class MenuServiceImpl extends SimpleBasisService<Menu, Long>
	implements MenuService {
	
	@Resource
	MenuDao menuDao;
	static List<Menu> allMenu = null;
	
	@Override
	public BaseDao<Menu, Long> getDao() {
		return menuDao;
	}
	@PostConstruct
	protected void findIniCache(){
		System.out.println("iniCache");
		findAllMenu();
	}
	
	
	@Override
	public List<MenuNodeBean> findAllMenu() {
		List<MenuNodeBean> menuNodeBeans = null;
		try {
			if(ObjUtil.isEmpty(allMenu)){
				allMenu = (List<Menu>) menuDao.findAllOrderByRank();
			}
			menuNodeBeans = PersistableUtil.toListBeans(allMenu, new SetBean<Menu>() {
				@Override
				public MenuNodeBean setValue(Menu doMain) {
					Menu parent = doMain.getParent();
					MenuNodeBean bean=new MenuNodeBean();
					if(ObjUtil.isNotEmpty(parent)){
						MenuNodeBean parentBean=new MenuNodeBean();
						parentBean.setId(doMain.getId());
						parentBean.setIcon(doMain.getIcon());
						parentBean.setParent(doMain.getParent());
						parentBean.setDisplay(doMain.getDisplay());
						parentBean.setName(doMain.getName());
						parentBean.setSecurityResource(SecurityResourceBean.toNode(parent.getFunctionPoint()));
						parentBean.setDefIcon(doMain.getDefIcon());
						bean.setParent(parentBean);
					}
					bean.setId(doMain.getId());
					bean.setIcon(doMain.getIcon());
					bean.setParent(doMain.getParent());
					bean.setDisplay(doMain.getDisplay());
					bean.setName(doMain.getName());
					bean.setSecurityResource(SecurityResourceBean.toNode(doMain.getFunctionPoint()));
					bean.setDefIcon(doMain.getDefIcon());
					return bean;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menuNodeBeans;
	}
	
	public List<? extends MenuNode> findMenuByType(Long id) {
		try {
			return (List<? extends MenuNode>) menuDao.findChildById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void update(Menu menu) {
		Menu oldMenu=menuDao.findById(menu.getId());
		oldMenu.setId(menu.getId());
		oldMenu.setIcon(menu.getIcon());
		oldMenu.setDefIcon(menu.getDefIcon());
		oldMenu.setDisplay(menu.getDisplay());
		oldMenu.setName(menu.getName());
		oldMenu.setRank(menu.getRank());
		oldMenu.setRemark(menu.getRemark());
		oldMenu.setFunctionPoint(menu.getFunctionPoint());
		oldMenu.setParent(menu.getParent());
//		oldMenu.setRoot(menu.getRoot());
		menuDao.update(oldMenu);
	}
	@Override
	public void deleteSelfAndChildren(List<Long> ids) {
		List<Menu> list=menuDao.findByParam(new AndInParam("parent.id",ids));
		if(ObjUtil.isNotEmpty(list)){
			deleteSelfAndChildren(PersistableUtil.arrayTransforList(list));
		}
		deleteSimple(ids.toArray(new Long[ids.size()]));
	}
	@Override
	public MenuNodeBean getCacheById(Long dufHome) {
		List<MenuNodeBean> allMenu = findAllMenu();
		Map<Long,MenuNodeBean> menuMap=PersistableUtil.toMap(allMenu);
		if(ObjUtil.isNotEmpty(menuMap)){
			menuMap.get(dufHome);
		}
		return null;
	}
}

