package org.csr.common.user.dao;


import java.util.List;

import org.csr.common.user.domain.Agencies;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:Agencies.java <br/>
 * Date: Tue Sep 09 23:05:42 CST 2014 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 */
public interface AgenciesDao extends BaseDao<Agencies,Long>{
	/**
	 * 递归查询出，所有的机构id
	 * @param id
	 * @return
	 */
	List<Agencies> findCountChildByIds(Long id, List<Integer> types);
	
	/**
	 * 查询全部
	 * @param categoryIds
	 * @param orgId
	 * @param name
	 * @return
	 */
	List<Agencies> findListByCategoryIdsOrgIdName(List<Long> categoryIds,List<Integer> types, List<Long> orgId,String name);
	List<Agencies> findListByCategoryIdsOrgId(List<Long> categoryIds, List<Long> orgId);
	
	/**
	 * 查询分类id 是否在域 orgIds ，返回不在指定的域中的 分类id
	 * @param orgId
	 * @param categoryIds
	 * @return
	 */
	List<Long> findByIdsOrgIds(List<Long> orgIds, List<Long> categoryIds);
	
	
	/**
	 * 带权限的查询，自己创建的机构，也默认返回出去
	 * @param page
	 * @param categoryIds
	 * @param orgId
	 * @return
	 */
	PagedInfo<Agencies> findPageByCategoryIdsOrgId(Page page,List<Long> categoryIds, List<Long> orgId);

	List<Long> findIdsListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgId);

	/**
	 * @param agenciesIds
	 * @return
	 */
	List<Agencies> findPermissionsByIds(List<Long> agenciesIds);

	/**
	 * 检测是否有子机构
	 * @param parentId
	 * @return
	 */
	Long findCountChildById(List<Long> parentIds);

	/**
	 * 根据机构查询
	 * @param orgId
	 * @return
	 */
	boolean findByIdsOrgId(List<Long> orgIds,Long agenciesId);

	/**
	 * 带
	 * @param types
	 * @param name
	 * @return
	 */
	List<Agencies> findListByTypeName(List<Integer> types, String name);

	/**
	 * 只查询当前子机构
	 * @param id
	 * @param types
	 * @return
	 */
	List<Agencies> findChildByIds(Long id, List<Integer> types);

	
	
}
