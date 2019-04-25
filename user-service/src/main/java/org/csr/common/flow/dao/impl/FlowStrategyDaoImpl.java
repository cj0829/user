package org.csr.common.flow.dao.impl;

import org.csr.common.flow.dao.FlowStrategyDao;
import org.csr.common.flow.domain.FlowStrategy;
import org.csr.core.persistence.query.jpa.JpaDao;

/**
 * ClassName:ExamStrategy.java <br/>
 * Date: Tue Jun 23 12:04:34 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 dao实现
 */
public abstract class FlowStrategyDaoImpl<Strategy extends FlowStrategy>
		extends JpaDao<Strategy, Long> implements FlowStrategyDao<Strategy> {

}
