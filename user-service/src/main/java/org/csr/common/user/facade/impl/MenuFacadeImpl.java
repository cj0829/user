package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.MenuDao;
import org.csr.common.user.domain.Menu;
import org.csr.common.user.facade.MenuFacade;
import org.csr.common.user.service.MenuService;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

@Service("menuFacade")
public class MenuFacadeImpl extends SimpleBasisFacade<MenuNodeBean, Menu, Long> implements MenuFacade {
	@Resource
	private MenuService menuService;
	@Resource
	private MenuDao menuDao;
	
	@Override
	public MenuNodeBean wrapBean(Menu doMain) {
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
	
//	@Override
//	public Menu wrapDomain(MenuNodeBean entity) {
//		Menu doMain = new Menu(entity.getId());
//		doMain.setId(entity.getId());
//		doMain.setIcon(entity.getIcon());
//		doMain.setDisplay(entity.getDisplay());
//		doMain.setName(entity.getName());
//		doMain.setDefIcon(doMain.getDefIcon());
//		return doMain;
//	}

	@Override
	public List<MenuNodeBean> findAllMenu() {
		return menuService.findAllMenu();
	}
	

	@Override
	public void save(Menu menu) {
		
		if(ObjUtil.isEmpty(menu.getDisplay())){
			menu.setDisplay(YesorNo.YES);
		}
		//Menu entity=wrapDomain(menu);
		menuService.saveSimple(menu);
	}

	@Override
	public void update(Menu menu) {
		//Menu entity=wrapDomain(menu);
		menuService.update(menu);
	}

	@Override
	public void deleteSelfAndChildren(List<Long> asList) {
		menuService.deleteSelfAndChildren(asList);
		
	}

	@Override
	public List<MenuNodeBean> findMenuByRootMenuId(Long rootMenuId) {
		List<Menu> list=menuDao.findByParam(new AndInParam("parent.id",rootMenuId));
		return PersistableUtil.toListBeans(list, new SetBean<Menu>() {
			@Override
			public MenuNodeBean setValue(Menu doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public BaseDao<Menu, Long> getDao() {
		return menuDao;
	}

}
