package org.csr.common.flow.service;

import org.csr.common.flow.domain.FlowStrategy;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:ExamStrategy.java <br/>
 * Date: Tue Jun 23 12:04:34 CST 2015 <br/>
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface FlowStrategyService<Strategy extends FlowStrategy> extends
		BasisService<Strategy, Long> {

	/**
	 * 查询，机构策略，如果机构策略不存在，则创建一个新的。
	 * 
	 * @author caijin
	 * @param orgId
	 * @return
	 * @since JDK 1.7
	 */
	Strategy saveFindByOrgId(Long orgId);

	/**
	 * save: 保存方法<br/>
	 * 
	 * @author n-caijin
	 * @param examStrategy
	 */
	public void save(Strategy strategy);

	/**
	 * update: 编辑方法<br/>
	 * 
	 * @author n-caijin
	 * @param examStrategy
	 */
	public void update(Strategy strategy);

	/**
	 * 根据策略，更改
	 * @author caijin
	 * @param strategyId
	 * @param enable
	 * @return
	 * @since JDK 1.7
	 */
	Strategy update(Long strategyId, Byte enable);
	
	public boolean checkNameIsExist(Long id, String name);

	

}
