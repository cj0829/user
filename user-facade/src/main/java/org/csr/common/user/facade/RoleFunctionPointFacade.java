/**
 * Project Name:user-facade
 * File Name:RoleFunctionPointFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午3:36:51
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import org.csr.common.user.entity.RoleFunctionPointBean;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:RoleFunctionPointFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午3:36:51 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface RoleFunctionPointFacade extends BasisFacade<RoleFunctionPointBean, Long>{

	void save(Long roleId, Long[] functionPointIds);

}

