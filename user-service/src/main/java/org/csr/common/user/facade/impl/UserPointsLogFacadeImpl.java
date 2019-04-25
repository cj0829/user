package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserPointsLogDao;
import org.csr.common.user.domain.UserPointsLog;
import org.csr.common.user.entity.UserPointsLogBean;
import org.csr.common.user.facade.UserPointsLogFacade;
import org.csr.common.user.service.UserPointsLogService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("userPointsLogFacade")
public class UserPointsLogFacadeImpl extends SimpleBasisFacade<UserPointsLogBean, UserPointsLog, Long> implements UserPointsLogFacade {

	@Resource
	private UserPointsLogService userPointsLogService;
	@Resource
	private UserPointsLogDao userPointsLogDao;
	
	@Override
	public UserPointsLogBean wrapBean(UserPointsLog doMain) {
		return UserPointsLogBean.wrapBean(doMain);
	}

	@Override
	public BaseDao<UserPointsLog, Long> getDao() {
		return userPointsLogDao;
	}

//	@Override
//	public UserPointsLog wrapDomain(UserPointsLogBean entity) {
//		UserPointsLog doMain = new UserPointsLog();
//		doMain.setId(entity.getId());
//		doMain.setOperation(entity.getOperation());
//		doMain.setPoints1(entity.getPoints1());
//		doMain.setPoints2(entity.getPoints2());
//		doMain.setOperationDate(entity.getOperationDate());
//		doMain.setRemark(entity.getRemark());
//		return doMain;
//	}

}



