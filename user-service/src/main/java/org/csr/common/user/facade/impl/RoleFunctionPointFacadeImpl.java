/**
 * Project Name:user-Service
 * File Name:RoleFunctionPointFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-2下午2:37:25
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.RoleFunctionPointDao;
import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.common.user.entity.RoleFunctionPointBean;
import org.csr.common.user.facade.RoleFunctionPointFacade;
import org.csr.common.user.service.RoleFunctionPointService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

/**
 * ClassName:RoleFunctionPointFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-2下午2:37:25 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("roleFunctionPointFacade")
public class RoleFunctionPointFacadeImpl extends SimpleBasisFacade<RoleFunctionPointBean, RoleFunctionPoint, Long> implements RoleFunctionPointFacade{

	@Resource
	RoleFunctionPointService roleFunctionPointService;
	@Resource
	RoleFunctionPointDao roleFunctionPointDao;
	
	@Override
	public RoleFunctionPointBean wrapBean(RoleFunctionPoint doMain) {
		return RoleFunctionPointBean.wrapBean(doMain);
	}

//	@Override
//	public RoleFunctionPoint wrapDomain(RoleFunctionPointResult entity) {
//		return null;
//	}

	@Override
	public void save(Long roleId, Long[] functionPointIds) {
		roleFunctionPointService.save(roleId, functionPointIds);
	}

	@Override
	public BaseDao<RoleFunctionPoint, Long> getDao() {
		return roleFunctionPointDao;
	}

}

