package org.csr.common.user.facade;

import org.csr.common.user.domain.PointsRules;
import org.csr.common.user.entity.PointsRulesBean;
import org.csr.core.persistence.service.BasisFacade;

public interface PointsRulesFacade extends BasisFacade<PointsRulesBean, Long>{

	void saveSimple(PointsRules pointsRules);

	void updateSimple(PointsRules pointsRules);

	int deleteSimple(Long[] ids);

	boolean checkUpdateName(Long id, String name);

	void save(PointsRules pointsRules, Long functionPointId);

	void update(PointsRules pointsRules, Long functionPointId);
	
}
