package org.csr.common.user.dao.impl;


import org.csr.common.user.dao.UserCommandDao;
import org.csr.common.user.domain.UserCommand;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;


/**
 * ClassName:UserCommand.java <br/>
 * Date: Thu Sep 17 11:29:36 CST 2015 <br/>
 * 
 * @author huayj-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("userCommandDao")
public class UserCommandDaoImpl extends JpaDao<UserCommand,Long> implements UserCommandDao{

	@Override
	public Class<UserCommand> entityClass(){
		return UserCommand.class;
	}

}
