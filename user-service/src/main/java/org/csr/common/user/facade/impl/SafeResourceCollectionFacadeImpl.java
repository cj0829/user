/**
 * Project Name:user-Service
 * File Name:SafeResourceCollectionFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-1下午4:09:37
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.SafeResourceCollectionDao;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.entity.SafeResourceCollectionBean;
import org.csr.common.user.facade.SafeResourceCollectionFacade;
import org.csr.common.user.service.SafeResourceCollectionService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

/**
 * ClassName:SafeResourceCollectionFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午4:09:37 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("safeResourceCollectionFacade")
public class SafeResourceCollectionFacadeImpl extends SimpleBasisFacade<SafeResourceCollectionBean, SafeResourceCollection, Long> implements SafeResourceCollectionFacade {

	@Resource
	private SafeResourceCollectionService safeResourceCollectionService;
	@Resource
	private SafeResourceCollectionDao safeResourceCollectionDao;
	
	@Override
	public void save(SafeResourceCollection safeResourceCollection) {
		//SafeResourceCollection wrapDomain = wrapDomain(safeResourceCollection);
		safeResourceCollectionService.save(safeResourceCollection);
	}

	@Override
	public void update(SafeResourceCollection safeResourceCollection) {
		//SafeResourceCollection wrapDomain = wrapDomain(safeResourceCollection);
		safeResourceCollectionService.update(safeResourceCollection);
	}

	@Override
	public void delete(Long id) {
		safeResourceCollectionService.delete(id);
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		return safeResourceCollectionService.checkNameIsExist(id, name);
	}

	@Override
	public SafeResourceCollectionBean wrapBean(SafeResourceCollection doMain) {
				return SafeResourceCollectionBean.wrapBean(doMain);
	}

	@Override
	public BaseDao<SafeResourceCollection, Long> getDao() {
		return safeResourceCollectionDao;
	}

//	@Override
//	public SafeResourceCollection wrapDomain(SafeResourceCollectionBean bean) {
//		SafeResourceCollection doMain = new SafeResourceCollection(bean.getId());
//		doMain.setName(bean.getName());
//		doMain.setRemarks(bean.getRemarks());
//		doMain.setIsSystem(bean.getIsSystem());
//		doMain.setRoot(bean.getRoot());
//		return doMain;
//	}
}

