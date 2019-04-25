/**
 * Project Name:exam
 * File Name:DefaultCategoryResourceSearchServiceImpl.java
 * Package Name:org.csr.common.user.SafeResource
 * Date:2015-12-17下午5:20:08
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.safeResource;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:DefaultCategoryResourceSearchServiceImpl.java <br/>
 * System Name：    签到系统 <br/>
 * Date:     2015-12-17下午5:20:08 <br/>
 * @author   lishengmei <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public class DefaultCategoryResourceSearchServiceImpl implements CategoryResourceSearchService{

	@Override
	public List<Long> findResourceExistByCategory(List<Long> resourceIds, List<Long> categoryIds) {
		return new ArrayList<Long>(0);
	}

	@Override
	public List<Category> findCategoryByCollectionIds(List<Long> collectionIds) {
		return new ArrayList<Category>(0);
	}

	@Override
	public Integer getSafeResourceType() {
		return 0;
	}

}

