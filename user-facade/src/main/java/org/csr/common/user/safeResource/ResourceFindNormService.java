/**
 * Project Name:exam
 * File Name:ResourceFindNormService.java
 * Package Name:com.pmt.common.SafeResource
 * Date:2015-12-17下午3:32:20
 * Copyright (c) 2015, 博海云领版权所有 ,All rights reserved 
*/

package org.csr.common.user.safeResource;


/**
 * ClassName:ResourceFindNormService.java <br/>
 * System Name：    考试系统 <br/>
 * Date:     2015-12-17下午3:32:20 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 管理安全资源的查询的总包接口
 *
 */
public interface ResourceFindNormService {

	/**
	 * 通过域查询类别
	 * @author liurui
	 * @param oList
	 * @return
	 * @since JDK 1.7
	 */
	CategoryOrgSearchService getCategoryOrg();
	
	/**
	 * 通过库，查询父类只到跟节点的全部父节点
	 * @author liurui
	 * @param cList
	 * @return
	 * @since JDK 1.7
	 */
	CategoryParentSearchService getCategoryParent();
	
	/**
	 * 通过资源查询库，并去重
	 * @author liurui
	 * @param qList
	 * @return
	 * @since JDK 1.7
	 */
	CategoryResourceSearchService getCategoryResource();
}

