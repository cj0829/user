/**
 * Project Name:exam
 * File Name:CategoryOrgSeachService.java
 * Package Name:org.csr.common.user.SafeResource
 * Date:2015-12-17下午3:43:55
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.safeResource;

import java.util.List;

/**
 * ClassName:CategoryOrgSeachService.java <br/>
 * System Name：    签到系统 <br/>
 * Date:     2015-12-17下午3:43:55 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 
 * 根据在安全资源中的查询出的来的安全域，来查询所有分类。<br>
 * 
 * 在页面的实现，如果选择了域，则不会选择 分类，或者 具体的资源对象。<br>
 * 选择了域，则是包含了当前域下面的所有资源。<br>
 */
public interface CategoryOrgSearchService {
	/**
	 * 通过域，查询域下面所有分类。（包含tree结构的可以）<br>
	 * 用户的分类：是机构<br>
	 * 试题、试卷的分类：是库<br>
	 * @author liurui
	 * @param orgIds
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("rawtypes")
	public List<Category> findCategoryByOrgId(List<Long> orgIds,Integer safeResourceType);
}

