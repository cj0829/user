package org.csr.common.user.facade;

import org.csr.common.user.domain.UserImportStrategy;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.core.persistence.service.BasisFacade;

public interface UserImportStrategyFacade extends BasisFacade<UserStrategyBean, Long>{

	UserImportStrategy saveFindByOrgId(Long id);

	UserImportStrategy update(Long strategyId, Byte enable);

	void update(UserImportStrategy categoryItemStrategy);

}
