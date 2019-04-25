package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.flow.dao.FlowStrategyDao;
import org.csr.common.flow.domain.FlowStrategy;
import org.csr.common.flow.service.FlowStrategyService;
import org.csr.common.user.entity.FlowStrategyBean;
import org.csr.common.user.facade.FlowStrategyFacade;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("flowStrategyFacade")
public class FlowStrategyFacadeImpl extends SimpleBasisFacade<FlowStrategyBean, FlowStrategy, Long> implements FlowStrategyFacade{
	@Resource
	private FlowStrategyService<FlowStrategy> flowStrategyService;
	@Resource
	private FlowStrategyDao<FlowStrategy> flowStrategyDao;
	
	@Override
	public FlowStrategyBean wrapBean(FlowStrategy doMain) {
		return FlowStrategyBean.wrapBean(doMain);
	}

	@Override
	public BaseDao<FlowStrategy, Long> getDao() {
		return flowStrategyDao;
	}

}
