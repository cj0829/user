/**
 * Project Name:exam
 * File Name:CategoryOrgSeachService.java
 * Package Name:org.csr.common.user.SafeResource
 * Date:2015-12-17下午3:43:55
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.safeResource;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:CategoryOrgSeachService.java <br/>
 * System Name：    签到系统 <br/>
 * Date:     2015-12-17下午3:43:55 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 
 * 通过域查询类别，查询实现
 */
public class DefaultCategoryParentSearchServiceImpl implements CategoryParentSearchService{
	/**
	 * 
	 * findCategoryParentById: 通过查询库及其父类 <br/>
	 * @author liurui
	 * @param categoryIds
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("rawtypes")
	public List<Category> findCategoryParentById(List<Long> categoryIds,Integer safeResourceType){
		return new ArrayList<Category>(0);
	};
}

