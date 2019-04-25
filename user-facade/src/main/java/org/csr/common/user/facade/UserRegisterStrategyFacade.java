package org.csr.common.user.facade;

import org.csr.common.user.domain.UserRegisterStrategy;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.core.persistence.service.BasisFacade;

public interface UserRegisterStrategyFacade extends BasisFacade<UserStrategyBean, Long>{

	UserRegisterStrategy saveFindByOrgId(Long id);

	UserRegisterStrategy update(Long strategyId, Byte enable);

	void update(UserRegisterStrategy categoryItemStrategy);

}
