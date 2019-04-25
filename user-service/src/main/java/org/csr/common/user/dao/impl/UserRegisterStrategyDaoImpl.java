package org.csr.common.user.dao.impl;

import org.csr.common.flow.dao.impl.FlowStrategyDaoImpl;
import org.csr.common.user.dao.UserRegisterStrategyDao;
import org.csr.common.user.domain.UserRegisterStrategy;
import org.springframework.stereotype.Repository;

/**
 * ClassName:QuestionStrategy.java <br/>
 * Date: Thu Jul 02 17:30:31 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 用户策略 dao实现
 */
@Repository("userRegisterStrategyDao")
public class UserRegisterStrategyDaoImpl extends
		FlowStrategyDaoImpl<UserRegisterStrategy> implements UserRegisterStrategyDao {

	@Override
	public Class<UserRegisterStrategy> entityClass() {
		return UserRegisterStrategy.class;
	}

}
