package org.csr.common.user.dao.impl;


import org.csr.common.user.dao.SafeResourceCollectionDao;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;


/**
 * ClassName:SafeResourceCollection.java <br/>
 * Date: Tue Dec 01 17:02:25 CST 2015 <br/>
 * 
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("safeResourceCollectionDao")
public class SafeResourceCollectionDaoImpl extends JpaDao<SafeResourceCollection,Long> implements SafeResourceCollectionDao{

	@Override
	public Class<SafeResourceCollection> entityClass(){
		return SafeResourceCollection.class;
	}

}
