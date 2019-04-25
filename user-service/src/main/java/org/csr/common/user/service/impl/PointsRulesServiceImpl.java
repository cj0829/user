package org.csr.common.user.service.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.PointsRulesDao;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.PointsRules;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.PointsRulesService;
import org.csr.core.Param;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:PointsRulesServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("pointsRulesService")
public class PointsRulesServiceImpl  extends SimpleBasisService<PointsRules, Long> 
	implements PointsRulesService {
    @Resource
    private PointsRulesDao pointsRulesDao;
    @Resource
    private FunctionpointService functionpointService;

	@Override
	public BaseDao<PointsRules, Long> getDao() {
		return pointsRulesDao;
	}

    /**
     * @description:根据id取积分规则明细
     * @param: id：积分规则id
     * @return: Parameter
     * @author:wangxiujuan
     */
    public PointsRules findById(Long id) {
		PointsRules pRules = pointsRulesDao.findById(id);
		return pRules;
    }

	@Override
	public PointsRules findByFunctionPointId(Long orgId,Long functionPointId) {
//		Param idParem = new AndEqParam("or",  orgId);
		Param nameParem = new AndEqParam("functionPoint",new FunctionPoint(functionPointId));
		return pointsRulesDao.existParam(nameParem);
	}

	/**
	 * 用于验证添加用户名是否唯一
	 * 
	 * @see org.csr.core.user.service.UserService#checkUpdateLoginName(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public boolean checkUpdateName(Long id, String name) {
		PointsRules pointsRules = existUserLoginName(name);
		if (ObjUtil.isEmpty(pointsRules) || pointsRules.getId().equals(id)) {
			return false;
		}
		return true;
	}

	/**
	 * 查询是否存在name
	 * 
	 * @param name
	 * @return
	 */
	private PointsRules existUserLoginName(String name) {
		Param param = new AndEqParam("operation", name);
		return pointsRulesDao.existParam(param);
	}
}
