/**
 * Project Name:exam
 * File Name:InitSafeResourceOrganization.java
 * Package Name:com.pmt.common.service.impl
 * Date:2015-12-24上午9:57:53
 * Copyright (c) 2015, 博海云领版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import org.csr.core.persistence.business.domain.Organization;

/**
 * ClassName:InitSafeResourceOrganization.java <br/>
 * System Name：    考试系统 <br/>
 * Date:     2015-12-24上午9:57:53 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface InitSafeResourceOrganization {
	public void initSafeResource(Organization organization,Long safeResourceCollectionId);
}

