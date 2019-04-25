package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.Help;
import org.csr.common.user.entity.HelpBean;
import org.csr.core.persistence.service.BasisFacade;

public interface HelpFacade extends BasisFacade<HelpBean, Long>{

	List<HelpBean> queryByCode(String functionPointCode);

	boolean save(Help help);

	void update(Help help);

}
