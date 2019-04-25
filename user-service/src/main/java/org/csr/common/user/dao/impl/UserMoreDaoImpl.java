package org.csr.common.user.dao.impl;


import org.csr.common.user.dao.UserMoreDao;
import org.csr.common.user.domain.UserMore;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;


/**
 * ClassName:UserMore.java <br/>
 * Date: Thu Dec 01 10:06:41 CST 2016 <br/>
 * 
 * @author liurui <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("userMoreDao")
public class UserMoreDaoImpl extends JpaDao<UserMore,Long> implements UserMoreDao{

	@Override
	public Class<UserMore> entityClass(){
		return UserMore.class;
	}

}
