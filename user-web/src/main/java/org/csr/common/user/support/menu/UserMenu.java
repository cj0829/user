/**
 * Project Name:core
 * File Name:MainMenu.java
 * Package Name:org.csr.core.security.menu
 * Date:2014年9月15日上午10:41:50
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.user.support.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.csr.common.user.facade.MenuFacade;
import org.csr.core.Constants;
import org.csr.core.SecurityResource;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.security.resource.RegistrationMenu;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;

/**
 * ClassName:MainMenu.java <br/>
 * System Name： 基础框架 <br/>
 * Date: 2014年9月15日上午10:41:50 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
public class UserMenu extends RegistrationMenu {

	protected final MenuFacade menuFacade;

	public UserMenu(MenuFacade menuFacade) {
		this.menuFacade = menuFacade;
	}


	@Override
	public String registration(HttpServletRequest request,List<SecurityResource> securityResources,Long rootId) {
		List<MenuNodeBean> menus = (List<MenuNodeBean>) menuFacade.findAllMenu();
		if(ObjUtil.isEmpty(rootId)){
			rootId=Constants.MENU1;
		}
		
		List<MenuNodeBean> filterMenus = filterMenu(menus,securityResources, rootId);
		StringBuffer html = new StringBuffer("<ul class='nav ml20'>");
		if (ObjUtil.isEmpty(filterMenus)) {
			return "";
		}
		@SuppressWarnings("unchecked")
		boolean status = secondMenu(html, filterMenus.get(0).getChildren(), request);
		html.append("</ul>");
		if (status) {
			return html.toString();
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	private boolean secondMenu(StringBuffer html,List<MenuNodeBean> filterMenus,HttpServletRequest request) {
		boolean status = false;
		if (ObjUtil.isEmpty(filterMenus)) {
			return status;
		}
		for (int i = 0; ObjUtil.isNotEmpty(filterMenus)
				&& i < filterMenus.size(); i++) {
			status = true;
			MenuNodeBean menu = filterMenus.get(i);
			String url = "javascript:;";
			if (ObjUtil.isNotEmpty(menu.getSecurityResource())) {
				System.out.println(menu.getSecurityResource().getName());
				url = request.getContextPath()+ menu.getSecurityResource().getForwardUrl();
			}
			html.append("<li class='");
			boolean s = filterParent(UserSessionContext.getUserSession().getSelectMenu(), menu);
			if (s) {
				html.append("curr ");
			}
			html.append("exam-nav-item' adv-box='" + menu.getIcon()+ "'>");
			html.append("<a class='e-menu-a' href=\'" + url+ "\'><em class='drop-down-arrow " + menu.getDefIcon()+ "'></em>" + menu.getName() + "</a>");
			html.append("<div class='e-menu-navbg'><div class='e-menu-navbox'>");
			html.append("<div class='e-menu-navthre'>");
			thirdMenu(html, menu.getChildren(), request);
			html.append("</div></div></div></li>");
		}
		return status;

	}

	@SuppressWarnings("unchecked")
	private void thirdMenu(StringBuffer html,List<MenuNodeBean> filterMenus,HttpServletRequest request) {
		for (int i = 0; ObjUtil.isNotEmpty(filterMenus) && i < filterMenus.size(); i++) {
			MenuNodeBean menu = filterMenus.get(i);
			String url = "javascript:;";
			if (ObjUtil.isNotEmpty(menu.getSecurityResource())) {
				url = request.getContextPath() + menu.getSecurityResource().getForwardUrl();
			}
			html.append("<a class='");
			if (filterParent(UserSessionContext.getUserSession().getSelectMenu(), menu)) {
				html.append("curr ");
			}
			if(ObjUtil.isNotEmpty(menu.getChildren())){
				html.append("twoa ");
			}
			
			html.append("menu-nava t_elli' href='" + url + "' title='" + menu.getName() + "'><span>" + menu.getName() + "</span>");
			if(ObjUtil.isNotEmpty(menu.getChildren())){
				html.append("<em>></em>");
			}
			html.append("</a>");
			fourMenu(html, menu.getChildren(), request);
		}
	}
	
	private void fourMenu(StringBuffer html,List<MenuNodeBean> filterMenus,HttpServletRequest request) {
		if(ObjUtil.isEmpty(filterMenus)){
			return;
		}
		html.append("<div class='e-menu-navthrebox'>");
		for (int i = 0; ObjUtil.isNotEmpty(filterMenus) && i < filterMenus.size(); i++) {
			MenuNodeBean menu = filterMenus.get(i);
			String url = "javascript:;";
			if (ObjUtil.isNotEmpty(menu.getSecurityResource())) {
				url = request.getContextPath() + menu.getSecurityResource().getForwardUrl();
			}
			html.append("<a class='");
			if (filterParent(UserSessionContext.getUserSession().getSelectMenu(), menu)) {
				html.append("curr ");
			}
			html.append("menu-nava t_elli' href='" + url + "' title='" + menu.getName() + "'><span>" + menu.getName() + "</span></a>");
		}
		html.append("</div>");
	}
}
