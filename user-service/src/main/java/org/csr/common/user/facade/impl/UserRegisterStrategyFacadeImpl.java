package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserRegisterStrategyDao;
import org.csr.common.user.domain.UserRegisterStrategy;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.common.user.facade.UserRegisterStrategyFacade;
import org.csr.common.user.service.UserRegisterStrategyService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("userRegisterStrategyFacade")
public class UserRegisterStrategyFacadeImpl extends SimpleBasisFacade<UserStrategyBean, UserRegisterStrategy, Long> implements UserRegisterStrategyFacade{

	
	@Resource
	private UserRegisterStrategyService userRegisterStrategyService;
	@Resource
	private UserRegisterStrategyDao userStrategyDao;

	@Override
	public UserRegisterStrategy saveFindByOrgId(Long id) {
		return userRegisterStrategyService.saveFindByOrgId(id);
	}


	@Override
	public void update(UserRegisterStrategy categoryItemStrategy) {
		//UserRegisterStrategy wrapDomain = wrapDomain(categoryItemStrategy);
		userRegisterStrategyService.update(categoryItemStrategy);
	}

	@Override
	public UserStrategyBean wrapBean(UserRegisterStrategy doMain) {
		return UserStrategyBean.wrapBean(doMain);
	}

//	@Override
//	public UserRegisterStrategy wrapDomain(UserRegisterStrategyBean entity) {
//		UserRegisterStrategy doMain = new UserRegisterStrategy();	
//		doMain.setId(entity.getId());
//		doMain.setIsImport(entity.getIsImport());
//		return doMain;
//	}

	@Override
	public UserRegisterStrategy update(Long strategyId, Byte enable) {
		userRegisterStrategyService.update(strategyId, enable);
		return userRegisterStrategyService.update(strategyId, enable);
	}


	@Override
	public BaseDao<UserRegisterStrategy, Long> getDao() {
		return userStrategyDao;
	}

}
