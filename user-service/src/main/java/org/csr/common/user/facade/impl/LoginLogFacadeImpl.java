package org.csr.common.user.facade.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.LoginLogDao;
import org.csr.common.user.domain.LoginLog;
import org.csr.common.user.entity.LoginLogBean;
import org.csr.common.user.facade.LoginLogFacade;
import org.csr.common.user.service.LoginLogService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.springframework.stereotype.Service;

@Service("loginLogFacade")
public class LoginLogFacadeImpl extends SimpleBasisFacade<LoginLogBean,LoginLog, Long> implements LoginLogFacade {
	@Resource
	private LoginLogService loginLogService;
	@Resource
	private LoginLogDao loginLogDao;

	@Override
	public LoginLogBean wrapBean(LoginLog doMain) {
		return LoginLogBean.wrapBean(doMain);
	}

//	@Override
//	public LoginLog wrapDomain(LoginLogBean entity) {
//		LoginLog doMain = new LoginLog();
//		doMain.setId(entity.getId());
//		doMain.setIpAdress(entity.getIpAdress());
//		doMain.setSessionId(entity.getSessionId());
//		doMain.setUserId(entity.getUserId());
//		doMain.setUserName(entity.getUserName());
//		doMain.setLoginName(entity.getLoginName());
//		doMain.setLoginTime(entity.getLoginTime());
//		doMain.setLogoutTime(entity.getLogoutTime());
//		doMain.setExitType(entity.getExitType());
//		doMain.setRoot(entity.getOrganizationRoot());
//		return doMain;
//	}
	@Override
	public List<LoginLogBean> findByOperationTime(Date date) {
		return PersistableUtil.toListBeans(loginLogService.findByOperationTime(date),new SetBean<LoginLog>() {
			@Override
			public LoginLogBean setValue(LoginLog doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public BaseDao<LoginLog, Long> getDao() {
		return loginLogDao;
	}

}
