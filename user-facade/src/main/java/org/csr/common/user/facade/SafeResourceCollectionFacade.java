/**
 * Project Name:user-facade
 * File Name:SafeResourceCollectionFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午3:14:15
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.entity.SafeResourceCollectionBean;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:SafeResourceCollectionFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午3:14:15 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface SafeResourceCollectionFacade extends BasisFacade<SafeResourceCollectionBean, Long>{

	void save(SafeResourceCollection safeResourceCollection);

	void update(SafeResourceCollection safeResourceCollection);

	void delete(Long id);

	boolean checkNameIsExist(Long id, String name);

}

