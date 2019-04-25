package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.Menu;
import org.csr.core.persistence.service.BasisFacade;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.security.resource.MenuNodeService;

public interface MenuFacade extends BasisFacade<MenuNodeBean, Long>,MenuNodeService{

	void save(Menu menu);

	void update(Menu menu);

	void deleteSelfAndChildren(List<Long> asList);
	
	List<MenuNodeBean> findMenuByRootMenuId(Long rootMenuId);

}
