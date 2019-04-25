package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.PointsLevelRulesDao;
import org.csr.common.user.domain.PointsLevelRules;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:PointsLevelRulesDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:10:11 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("pointsLevelRulesDao")
public class PointsLevelRulesDaoImpl extends JpaDao<PointsLevelRules,Long> implements PointsLevelRulesDao {
	@Override
	public Class<PointsLevelRules> entityClass() {
		return PointsLevelRules.class;
	}
	
	/**
     * @description:查询积分等级表信息
     * @param: points：分数
     * @return: List
     * @author:wangxiujuan
     */
    public List<PointsLevelRules> findListByPoints(Integer points) {
		Finder finder=FinderImpl.create("select plr from PointsLevelRules plr where plr.points <= :points");
		finder.setParam("points", points);
    	return find(finder);
    }
    
    /**
     * @description:查询积分等级表信息
     * @param: points：分数
     * @return: List
     * @author:wangxiujuan
     */
    public PointsLevelRules findListByLevel(Integer level) {
		Finder finder=FinderImpl.create("select plr from PointsLevelRules plr where plr.level = :level");
		finder.setParam("level", level);
    	List<PointsLevelRules> list = find(finder);
    	return list != null && list.size() == 1 ? list.get(0) : null;
    }
}
