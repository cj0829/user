/**
 * Project Name:user-Service
 * File Name:SafeResourceFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-1下午4:31:22
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.SafeResourceDao;
import org.csr.common.user.domain.SafeResource;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.entity.CategoryListBean;
import org.csr.common.user.entity.SafeResourceBean;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.common.user.service.SafeResourceService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:SafeResourceFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午4:31:22 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("safeResourceFacade")
public class SafeResourceFacadeImpl extends SimpleBasisFacade<SafeResourceBean, SafeResource, Long> implements SafeResourceFacade {

	@Resource
	private SafeResourceService safeResourceService;
	@Resource
	private SafeResourceDao safeResourceDao;
	
	@Resource
	AgenciesFacade agenciesFacade;
	
	@Override
	public List<SafeResourceBean> findByCollectionId(Long safeResourceCollectionId, List<Integer> tipType) {
		List<SafeResource> list = safeResourceService.findByCollectionId(safeResourceCollectionId, tipType);
		List<SafeResourceBean> beanList = PersistableUtil.toListBeans(list, new SetBean<SafeResource>(){
			@Override
			public SafeResourceBean setValue(SafeResource doMain) {
				return wrapBean(doMain);
			}
		});
		return beanList;
	}

	@Override
	public int deleteSimple(Long[] ids) {
		return safeResourceService.deleteSimple(ids);
	}

	@Override
	public SafeResourceBean wrapBean(SafeResource doMain) {
		SafeResourceBean bean = new SafeResourceBean();
		bean.setId(doMain.getId());
		bean.setCode(doMain.getCode());
		bean.setType(doMain.getType());
		SafeResourceCollection src=doMain.getSafeResourceCollection();
		if(ObjUtil.isNotEmpty(src)){
			bean.setSafeResourceCollectionBean(new SafeResourceCollectionFacadeImpl().wrapBean(src));
		}
		bean.setTipId(doMain.getTipId());
		bean.setTipType(doMain.getTipType());
		return bean;
	}


	@Override
	public List<Long> collectionResource(Long collectionId,List<Long> resourceIds, Integer resourceType, List<Integer> tipType) {
		return safeResourceService.collectionResource(collectionId, resourceIds, resourceType, tipType);
	}


	@Override
	public BaseDao<SafeResource, Long> getDao() {
		return safeResourceDao;
	}

	@Override
	public CategoryListBean findCategoryListBeanByUserSafeResourceType(Long userId, Integer safeResourceType) {
		return safeResourceService.findCategoryListBeanByUserSafeResourceType(userId, safeResourceType);
	}

	@Override
	public List<Long> findCategoryIdsBeanByUserSafeResourceType(Long userId, Integer safeResourceType) {
		CategoryListBean bean = safeResourceService.findCategoryListBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(),safeResourceType);
		return agenciesFacade.findIdsListByCategoryIdsOrgId(bean.getCategoryIds(),bean.getOrgIds());
	}

	
}

