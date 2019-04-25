package org.csr.common.user.dao.impl;

import org.csr.common.user.dao.PointsRulesDao;
import org.csr.common.user.domain.PointsRules;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:PointsRulesDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("pointsRulesDao")
public class PointsRulesDaoImpl extends JpaDao<PointsRules,Long> implements PointsRulesDao {
	@Override
	public Class<PointsRules> entityClass() {
		return PointsRules.class;
	}
}
