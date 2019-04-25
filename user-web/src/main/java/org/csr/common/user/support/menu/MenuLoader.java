/*
 * Copyright (c) 2013, Pointdew Inc. All rights reserved.
 * 
 * http://www.pointdew.com
 */
package org.csr.common.user.support.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.MenuFacade;
import org.csr.core.SecurityResource;
import org.csr.core.UserSession;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ClassBeanFactory;
import org.csr.core.util.ObjUtil;

/**
 * @author caijin
 */
public class MenuLoader {
	
	public static String loadSpaceNavigationMenus(HttpServletRequest request,String rootMenu) {
		try {
			System.out.println("开始菜单");
			UserSession userSession=UserSessionContext.getUserSession();
			
			
			//2016-04-27 修改为.这里的菜单最好只查询一次（）在菜单中，如果全部菜单在缓存中，那么直接从缓存中去取
			List<SecurityResource> securityResources = isExistSecurityResource(userSession);
		
			MenuFacade menuFacade=((MenuFacade) ClassBeanFactory.getBean("menuFacade"));
			
			return new UserMenu(menuFacade).registration(request,securityResources,ObjUtil.toLong(rootMenu));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
		
	}

	private  static List<SecurityResource>  isExistSecurityResource(UserSession userSession) {
		//如果当前的权限已经存在，则就不作查询了。
		if(ObjUtil.isNotEmpty(userSession.getResources())){
			return userSession.getResources();
		}
		FunctionpointFacade functionpointFacade=((FunctionpointFacade) ClassBeanFactory.getBean("functionpointFacade"));
		//验证是否为默认权限
		@SuppressWarnings("unchecked")
		List<SecurityResource> defaults=(List<SecurityResource>) functionpointFacade.findResourcesByDefault();
		@SuppressWarnings("unchecked")
		List<SecurityResource> securityResources=(List<SecurityResource>) functionpointFacade.findResourcesByUser(userSession);
//		List<SecurityResource> securityResources=(List<SecurityResource>) functionpointFacade.findResourcesByAnonymous(userSession);
		securityResources.addAll(defaults);
		return  securityResources;
		
	}
}
