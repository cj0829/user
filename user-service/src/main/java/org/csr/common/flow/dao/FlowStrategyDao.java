package org.csr.common.flow.dao;

import org.csr.common.flow.domain.FlowStrategy;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:ExamStrategy.java <br/>
 * Date: Tue Jun 23 12:04:34 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 dao接口
 */
public interface FlowStrategyDao<Strategy extends FlowStrategy> extends
		BaseDao<Strategy, Long> {

}
