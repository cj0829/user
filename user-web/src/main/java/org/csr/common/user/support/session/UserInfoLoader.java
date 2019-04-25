/*
 * Copyright (c) 2013, Pointdew Inc. All rights reserved.
 * 
 * http://www.pointdew.com
 */
package org.csr.common.user.support.session;

import javax.servlet.http.HttpServletRequest;

import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.UserFacade;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ClassBeanFactory;
import org.csr.core.util.ObjUtil;

/**
 * 
 * @author admin
 *
 */
public class UserInfoLoader {

	public static UserBean getUserById(HttpServletRequest request, String userId) {
		if(ObjUtil.isNotBlank(userId)){
			UserFacade userService = (UserFacade) ClassBeanFactory.getBean("userFacade");
			UserBean user = userService.findById(ObjUtil.toLong(userId));
			return user;
		}else{
			UserBean user=new UserBean();
			UserSessionBasics us=UserSessionContext.getUserSession();
			user.setLoginName(us.getLoginName());
			user.setAvatarUrl(us.getAvatarUrl());
			user.setPoints1(us.getPoints1());
			user.setHeadUrl(us.getHeadUrl());
			user.setName(us.getUserName());
			return user;
		}
	}
}
