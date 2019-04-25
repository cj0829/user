package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.PointsRulesDao;
import org.csr.common.user.domain.PointsRules;
import org.csr.common.user.entity.PointsRulesBean;
import org.csr.common.user.facade.PointsRulesFacade;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.PointsRulesService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("pointsRulesFacade")
public class PointsRulesFacadeImpl extends SimpleBasisFacade<PointsRulesBean, PointsRules, Long> implements PointsRulesFacade{

	@Resource
	private PointsRulesService pointsRulesService;
	@Resource
	private PointsRulesDao pointsRulesDao;
	@Resource
	private FunctionpointService functionpointService;
	@Override
	public void saveSimple(PointsRules pointsRules) {
		pointsRulesService.saveSimple(pointsRules);
	}

	@Override
	public void updateSimple(PointsRules pointsRules) {
		pointsRulesService.updateSimple(pointsRules);
	}

	@Override
	public int deleteSimple(Long[] ids) {
		return pointsRulesService.deleteSimple(ids);
	}

	@Override
	public boolean checkUpdateName(Long id, String name) {
		return pointsRulesService.checkUpdateName(id, name);
	}

	@Override
	public PointsRulesBean wrapBean(PointsRules doMain) {
		return PointsRulesBean.wrapBean(doMain);
	}

	@Override
	public void save(PointsRules pointsRules, Long functionPointId) {
		pointsRules.setFunctionPoint(functionpointService.findById(functionPointId));
		pointsRulesService.saveSimple(pointsRules);
	}

	@Override
	public void update(PointsRules pointsRules, Long functionPointId) {
		pointsRules.setFunctionPoint(functionpointService.findById(functionPointId));
		pointsRulesService.updateSimple(pointsRules);
	}

	@Override
	public BaseDao<PointsRules, Long> getDao() {
		return pointsRulesDao;
	}

//	@Override
//	public PointsRules wrapDomain(PointsRulesBean bean) {
//		PointsRules doMain=new PointsRules();
//		doMain.setId(bean.getId());
//		doMain.setCountDay(bean.getCountDay());
//		doMain.setFunctionPoint(functionpointService.findById(bean.getFunctionPointId()));
//		doMain.setOperation(bean.getOperation());
//		doMain.setPoints1(bean.getPoints1());
//		doMain.setPoints2(bean.getPoints2());
//		doMain.setPointsMark1(bean.getPointsMark1());
//		doMain.setPointsMark2(bean.getPointsMark2());
//		doMain.setRemark(bean.getRemark());
//		return doMain;
//	}

}
