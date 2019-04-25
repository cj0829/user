package org.csr.common.user.facade;


import java.util.List;

import org.csr.common.user.domain.PointsLevelRules;
import org.csr.common.user.entity.PointsLevelRulesBean;
import org.csr.core.persistence.service.BasisFacade;

public interface PointsLevelRulesFacade extends BasisFacade<PointsLevelRulesBean, Long>{

	void saveSimple(PointsLevelRules pointsLevelRules);

	void updateSimple(PointsLevelRules pointsLevelRules);

	int deleteSimple(Long[] ids);

	boolean checkUpdateName(Long id, String levelName);

	List<PointsLevelRules> findListByPoints(Integer pointsSum);

	PointsLevelRules findListByLevel(int size);

}
