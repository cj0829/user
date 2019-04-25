package org.csr.common.flow.service;


import org.csr.common.flow.domain.EndNode;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:EndNode.java <br/>
 * Date: Mon Jun 29 17:29:25 CST 2015 <br/>
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface EndNodeService extends BasisService<EndNode, Long> {

	
	/**
	 * save: 保存方法<br/>
	 * @author n-caijin
	 * @param endNode 
	 */
	public void save(EndNode endNode);

	/**
	 * update: 编辑方法<br/>
	 * @author n-caijin
	 * @param endNode 
	 */
	public void update(EndNode endNode);


	public boolean checkNameIsExist(Long id, String name);

}
