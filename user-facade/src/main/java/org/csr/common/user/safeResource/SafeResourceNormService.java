/**
 * Project Name:exam
 * File Name:Snippet.java
 * Package Name:com.pmt.common.SafeResource
 * Date:2015-12-17下午3:21:08
 * Copyright (c) 2015, 博海云领版权所有 ,All rights reserved 
*/

package org.csr.common.user.safeResource;

import java.util.List;

/**
 * System Name：    考试系统 <br/>
 * Date:     2015-12-17下午3:21:08 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 * 安全资源的查询标准接口
 */

public interface SafeResourceNormService {
	/**
	 *  findPublicCategoryItem: 查询用户资源（公有类别）,包含所有的父节点，跟节点（如果父节点，根节点没有权限，也将显示出来） <br/>
	 *  查询用户资源（公有类别）,只查询存，自己拥有的库资源<br/>
	 * @param userId
	 * @param safeResourceType
	 * @param resourceFindNormService
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Category> findCategoryByUser(Long userId,ResourceFindNormService resourceFindNormService);
	
}

