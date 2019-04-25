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
 * 根据安全资源里的分类，来查询 ， 他的相关联的分类。<br>
 * 
 * 查询父类，直到根，这样子才能够在前台显示出一整个树。但是，这类的是没有操作权限的。
 * 
 * 在页面的实现，如果选择了分类，域就不会被选择。<br>
 * 
 */
public interface CategoryParentSearchService {
	/**
	 * 
	 * 通过查询库，其父类，根节点 <br/>
	 * 这里库，其父类，根节点，不再安全资源中。是通过已有的安全资源，向上推倒出来， <br/>
	 * 如果不需要，则可以使用默认的实现返回空数组) <br/>
	 * @author liurui
	 * @param categoryIds
	 * @return
	 * @since JDK 1.7
	 */
	@SuppressWarnings("rawtypes")
	public List<Category> findCategoryParentById(List<Long> categoryIds,Integer safeResourceType);
}

