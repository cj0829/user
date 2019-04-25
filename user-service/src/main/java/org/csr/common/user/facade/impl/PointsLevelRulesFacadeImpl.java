package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.PointsLevelRulesDao;
import org.csr.common.user.domain.PointsLevelRules;
import org.csr.common.user.entity.PointsLevelRulesBean;
import org.csr.common.user.facade.PointsLevelRulesFacade;
import org.csr.common.user.service.PointsLevelRulesService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("pointsLevelRulesFacade")
public class PointsLevelRulesFacadeImpl extends SimpleBasisFacade<PointsLevelRulesBean, PointsLevelRules, Long> implements PointsLevelRulesFacade{

	@Resource
	private PointsLevelRulesService pointsLevelRulesService;
	@Resource
	private PointsLevelRulesDao pointsLevelRulesDao;
	@Override
	public void saveSimple(PointsLevelRules pointsLevelRules) {
		pointsLevelRulesService.saveSimple(pointsLevelRules);
	}

	@Override
	public void updateSimple(PointsLevelRules pointsLevelRules) {
		pointsLevelRulesService.updateSimple(pointsLevelRules);
	}

	@Override
	public int deleteSimple(Long[] ids) {
		return pointsLevelRulesService.deleteSimple(ids);
	}

	@Override
	public boolean checkUpdateName(Long id, String levelName) {
		return pointsLevelRulesService.checkUpdateName(id, levelName);
	}

	@Override
	public PointsLevelRulesBean wrapBean(PointsLevelRules doMain) {
		return PointsLevelRulesBean.wrapBean(doMain);
	}

	@Override
	public List<PointsLevelRules> findListByPoints(Integer pointsSum) {
		return pointsLevelRulesService.findListByPoints(pointsSum);
		
	}

	@Override
	public PointsLevelRules findListByLevel(int size) {
		return pointsLevelRulesService.findListByLevel(size);
	}

	@Override
	public BaseDao<PointsLevelRules, Long> getDao() {
		return pointsLevelRulesDao;
	}

//	@Override
//	public PointsLevelRules wrapDomain(PointsLevelRulesBean bean) {
//		PointsLevelRules doMain=new PointsLevelRules();
//		doMain.setId(bean.getId());
//		doMain.setLevel(bean.getLevel());
//		doMain.setLevelName(bean.getLevelName());
//		doMain.setPoints(bean.getPoints());
//		return doMain;
//	}

}
