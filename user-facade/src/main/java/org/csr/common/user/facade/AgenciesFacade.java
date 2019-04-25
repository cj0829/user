/**
 * Project Name:user-facade
 * File Name:AgenciesFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-2上午10:17:20
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import java.util.List;
import java.util.Map;

import org.csr.common.user.domain.Agencies;
import org.csr.common.user.entity.AgenciesNode;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * ClassName:AgenciesFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-2上午10:17:20 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface AgenciesFacade {
	
	
	Map<Long, AgenciesNode> findMapByIds(List<Long> id);
	
	Map<Long, AgenciesNode> findMapByIds(Long[] ids);
	
	PagedInfo<AgenciesNode> findAllPage(Page page);
	/**
	 * 查询全部第一级别的公司
	 * @return
	 */
	List<AgenciesNode> findFirstLevelList();
	/**
	 * 判断当前操作是有权限。
	 * @param userId
	 * @param categoryIds
	 * @param orgId
	 * @return
	 */
	boolean findHasAuthorityByUserId(Long agenciesId);
	/**
	 * 查询全部
	 * @return
	 */
	List<AgenciesNode> findAllList();
	
	AgenciesNode findById(Long agenciesId);
	/**
	 * 查询出当前用户能够看到的组织机构，返回分页对象
	 * @param page
	 * @param userId
	 * @param categoryIds
	 * @param orgId
	 * @return
	 */
	PagedInfo<AgenciesNode> findPage(Page page);

	/**
	 * 查询出当前用户能够看到的组织机构，返回全部的集合，
	 * @param userId
	 * @param categoryIds
	 * @param orgId
	 * @return
	 */
	List<AgenciesNode> findList(String name, List<Integer> types);

	AgenciesNode save(Agencies agencies);

	AgenciesNode update(Agencies agencies);

	boolean checkNameIsExist(Long id, String name);

	boolean checkCodeIsExist(Long id, String code);

	/**
	 * 查询下面的所有的子类，不包含当前自己的id
	 * @param id
	 * @return
	 */
	List<AgenciesNode> findCountChildByIds(Long id);

	int delete(Long[] ids);

	/**
	 * 保存安全资源。目前安全资源，只控制到分类一级。不控制具体的资源
	 * @param orgId
	 * @param categoryIds
	 * @param safeResourceCollection
	 * @param delTipIds
	 */
	public void saveSafeResource(Long safeResourceCollectionId, List<Long> orgId, List<Long> categoryIds);
	
	
	List<AgenciesNode> findByOrgid(Long orgid);
	
	
	/**
	 * 获取权限，的分组
	 * @param categoryIds
	 * @param orgIds
	 * @return
	 */
	List<AgenciesNode> findListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgIds);
	
	
	/**
	 * 根据域id 与 分类id ，查询全部有效的ids
	 * @param categoryIds
	 * @param orgIds
	 * @return
	 */
	List<Long> findIdsListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgIds);
	
	/**
	 * 查询全部机构，只待公司
	 * @param categoryIds
	 * @param orgIds
	 * @return
	 */
	List<AgenciesNode> findCompanyList(String name);
	
	
	/**
	 * 只查询机构，不查询公司，根据权限查询
	 * @param name
	 * @return
	 */
	List<AgenciesNode> findParentNodeList(String name);
	
	/**
	 * 查询
	 * @param types
	 * @param name
	 * @param agenciesId
	 * @return
	 */
	List<AgenciesNode> findNotIncludedList(List<Integer> types, String name,Long agenciesId);

	void createAndSaveAgencies(Map<String, MultipartFile> fileMap,
			Long agenciesId, StringBuffer errors, Byte general);

	/**
	 * 根据条件查询。返回满足条件的所有
	 * @param name
	 * @param types
	 * @return
	 */
	List<AgenciesNode> findAllListByNameType(String name, List<Integer> types);

	/**
	 * 根据查询机构
	 * @param agenciesCode
	 * @return
	 */
	AgenciesNode findByCode(String agenciesCode);

	/**
	 * 根据机构code 查询出机构id
	 * @param agenciesCodes
	 * @return
	 */
	List<Long> findbIdsByCodes(List<String> agenciesCodes);

}

