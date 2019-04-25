package org.csr.common.flow.dao.impl;


import org.csr.common.flow.dao.EndNodeDao;
import org.csr.common.flow.domain.EndNode;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;


/**
 * ClassName:EndNode.java <br/>
 * Date: Mon Jun 29 17:29:25 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("endNodeDao")
public class EndNodeDaoImpl extends JpaDao<EndNode,Long> implements EndNodeDao{

	@Override
	public Class<EndNode> entityClass(){
		return EndNode.class;
	}

}
