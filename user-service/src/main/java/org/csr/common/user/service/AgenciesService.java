package org.csr.common.user.service;


import java.util.List;

import org.csr.common.user.domain.Agencies;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:Agencies.java <br/>
 * Date: Tue Sep 09 23:05:42 CST 2014 <br/>
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 */
public interface AgenciesService extends BasisService<Agencies, Long>{

	
	
	/**
	 *查询 组织机构，通过能够看到的权限进行查询
	 * @param categoryIds
	 * @param orgId
	 * @return
	 */
	List<Agencies> findListByCategoryIdsOrgIdName(List<Long> categoryIds,List<Integer> types,List<Long> orgId,String name);
	
	
	/**
	 *查询 组织机构，通过能够看到的权限进行查询
	 * @param categoryIds
	 * @param orgId
	 * @return
	 */
	List<Agencies> findListByCategoryIdsOrgId(List<Long> categoryIds, List<Long> orgId);
	
	
	/**
	 * 查询 组织机构，通过能够看到的权限进行查询
	 * @author caijin
	 * @param page
	 * @param agenciesType
	 * @return
	 * @since JDK 1.7
	 */
	PagedInfo<Agencies> findPageByCategoryIdsOrgId(Page page,List<Long> categoryIds,List<Long> orgId);
	
	public boolean checkNameIsExist(Long id, String name);

	public Agencies save(Agencies agenciese);
	/**
	 * delete:
	 * @author huayj
	 * @param ids
	 * @return void
	 * @date&time 2016-1-5 下午2:44:09
	 * @since JDK 1.7
	 */
	int delete(Long[] ids);
	/**
	 * update:
	 * @author huayj
	 * @param agencies
	 * @return void
	 * @date&time 2016-1-5 下午2:45:39
	 * @since JDK 1.7
	 */
	Agencies update(Agencies agencies);
	
	
	/**
	 * findByNotExitsAgenciesIds: 描述方法的作用 <br/>
	 * @author Administrator
	 * @param page
	 * @param agenciesIds
	 * @return
	 * @since JDK 1.7
	 */
	PagedInfo<Agencies> findByNotExitsAgenciesIds(Page page, List<Long> agenciesIds);
	/**
	 * checkCodeIsExist:
	 * @author huayj
	 * @param id
	 * @param code
	 * @return
	 * @return boolean
	 * @date&time 2016-2-23 下午5:09:02
	 * @since JDK 1.7
	 */
	boolean checkCodeIsExist(Long id, String code);
	
	
	List<Agencies> findCountChildByIds(Long id);


	boolean findHasAuthorityByUserId(Long userId, Long agenciesIds);


	List<Agencies> findByOrgid(Long orgid);


	List<Long> findIdsListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgIds);

	/**
	 * 
	 * @return
	 */
	List<Agencies> findParentNodeList(String name);

}
