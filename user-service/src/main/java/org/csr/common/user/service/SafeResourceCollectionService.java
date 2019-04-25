package org.csr.common.user.service;


import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:SafeResourceCollection.java <br/>
 * Date: Tue Dec 01 17:02:25 CST 2015 <br/>
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface SafeResourceCollectionService extends BasisService<SafeResourceCollection, Long> {

	
	/**
	 * save: 保存方法<br/>
	 * @author liurui-PC
	 * @param safeResourceCollection 
	 */
	public void save(SafeResourceCollection safeResourceCollection);

	/**
	 * update: 编辑方法<br/>
	 * @author liurui-PC
	 * @param safeResourceCollection 
	 */


	public boolean checkNameIsExist(Long id, String name);

	/**
	 * 
	 * createSystem: 系统资源库 <br/>
	 * @author liurui
	 * @param orgName
	 * @return
	 * @since JDK 1.7
	 */
	public Long createSystem(String orgName);
	
	/**
	 * 
	 * createSystem: 修改系统资源库 <br/>
	 * @author liurui
	 * @param orgName
	 * @return
	 * @since JDK 1.7
	 */
	public int updateSystemNameByOrg(Organization orga);
	/**
	 * 
	 * delete: 删除资源库及资源 <br/>
	 * @author liurui
	 * @param id
	 * @since JDK 1.7
	 */
	public void delete(Long id);

	void update(SafeResourceCollection safeResourceCollection);

	

}
