package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserImportStrategyDao;
import org.csr.common.user.domain.UserImportStrategy;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.common.user.facade.UserImportStrategyFacade;
import org.csr.common.user.service.UserImportStrategyService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("userImportStrategyFacade")
public class UserImportStrategyFacadeImpl extends SimpleBasisFacade<UserStrategyBean, UserImportStrategy, Long> implements UserImportStrategyFacade{

	
	@Resource
	private UserImportStrategyService userStrategyService;
	@Resource
	private UserImportStrategyDao userStrategyDao;

	@Override
	public UserImportStrategy saveFindByOrgId(Long id) {
		return userStrategyService.saveFindByOrgId(id);
	}


	@Override
	public void update(UserImportStrategy categoryItemStrategy) {
		//UserImportStrategy wrapDomain = wrapDomain(categoryItemStrategy);
		userStrategyService.update(categoryItemStrategy);
	}

	@Override
	public UserStrategyBean wrapBean(UserImportStrategy doMain) {
		return UserStrategyBean.wrapBean(doMain);
	}

//	@Override
//	public UserImportStrategy wrapDomain(UserImportStrategyBean entity) {
//		UserImportStrategy doMain = new UserImportStrategy();	
//		doMain.setId(entity.getId());
//		doMain.setIsImport(entity.getIsImport());
//		return doMain;
//	}

	@Override
	public UserImportStrategy update(Long strategyId, Byte enable) {
		userStrategyService.update(strategyId, enable);
		return userStrategyService.update(strategyId, enable);
	}


	@Override
	public BaseDao<UserImportStrategy, Long> getDao() {
		return userStrategyDao;
	}

}
