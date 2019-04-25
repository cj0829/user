package org.csr.common.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.PointsLevelRulesDao;
import org.csr.common.user.domain.PointsLevelRules;
import org.csr.common.user.service.PointsLevelRulesService;
import org.csr.core.Param;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:PointsLevelRulesServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("pointsLevelRulesService")
public class PointsLevelRulesServiceImpl extends SimpleBasisService<PointsLevelRules, Long>
 	implements PointsLevelRulesService {
    @Resource
    private PointsLevelRulesDao pointsLevelRulesDao;

	@Override
	public BaseDao<PointsLevelRules, Long> getDao() {
		return pointsLevelRulesDao;
	}
	
	/**
     * @description:查询积分等级表信息
     * @param: points：分数
     * @return: List
     * @author:wangxiujuan
     */
    public List<PointsLevelRules> findListByPoints(Integer points) {
    	return pointsLevelRulesDao.findListByPoints(points);
    }
    
    /**
     * @description:查询积分等级表信息
     * @param: points：分数
     * @return: List
     * @author:wangxiujuan
     */
    public PointsLevelRules findListByLevel(Integer level){
    	return pointsLevelRulesDao.findListByLevel(level);
    }
    /**
	 * 用于验证添加用户名是否唯一
	 * 
	 * @see org.csr.core.user.service.UserService#checkUpdateLoginName(java.lang.Long,
	 *      java.lang.String)
	 */
	@Override
	public boolean checkUpdateName(Long id, String levelName) {
		PointsLevelRules pointsLevelRules = existUserLoginName(levelName);
		if (ObjUtil.isEmpty(pointsLevelRules) || pointsLevelRules.getId().equals(id)) {
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
	private PointsLevelRules existUserLoginName(String levelName) {
		Param param = new AndEqParam("levelName", levelName);
		return pointsLevelRulesDao.existParam(param);
	}

}
