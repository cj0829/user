package org.csr.common.user.service.impl;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.SafeResourceTipType;
import org.csr.common.user.dao.SafeResourceDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.SafeResource;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.entity.CategoryListBean;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.SafeResourceService;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.param.AndNeParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:SafeResource.java <br/>
 * Date:     Wed Dec 02 11:54:01 CST 2015
 * @author   liurui-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("safeResourceService")
public class SafeResourceServiceImpl extends SimpleBasisService<SafeResource, Long> implements SafeResourceService {
    @Resource
    private SafeResourceDao safeResourceDao;
    @Resource
    private UserSafeResourceCollectionService userSafeResourceCollectionService;
    @Resource
    private AgenciesService agenciesService;
    
    @Override
	public BaseDao<SafeResource, Long> getDao() {
		return safeResourceDao;
	}
	
	@Override
	public void save(SafeResource safeResource){
		//如果需要判断这里更改
		
		safeResourceDao.save(safeResource);
	}

	@Override
	public void update(SafeResource safeResource){
	
		//如果需要判断这里更改
		SafeResource oldsafeResource = safeResourceDao.findById(safeResource.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		oldsafeResource.setId(safeResource.getId());
		oldsafeResource.setCode(safeResource.getCode());
		oldsafeResource.setType(safeResource.getType());
		oldsafeResource.setSafeResourceCollection(safeResource.getSafeResourceCollection());
		oldsafeResource.setTipId(safeResource.getTipId());
		oldsafeResource.setTipType(safeResource.getTipType());

		safeResourceDao.update(oldsafeResource);
	}
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		SafeResource safeResource = safeResourceDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(safeResource) || safeResource.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(safeResource)) {
		    return false;
		}
		return true;
	}
	@Override
	public void save(Long tipId,String code,Integer type,SafeResourceCollection safe){
		SafeResource sr=new SafeResource();
		sr.setCode(code.toString());
		sr.setSafeResourceCollection(safe);
		sr.setType(type);
		sr.setTipId(tipId);
		String last=code.substring(0,1);
		if("o".equals(last)){
			sr.setTipType(SafeResourceTipType.ORGANIZATION);
		}
		if("c".equals(last)){
			sr.setTipType(SafeResourceTipType.CATEGORY);
		}
		if("a".equals(last) || "q".equals(last)||"t".equals(last)){
			sr.setTipType(SafeResourceTipType.RESOURCE);
		}
		safeResourceDao.save(sr);
	}
	protected void save(Long tipId, Integer tipType, Integer type, SafeResourceCollection collection) {
		SafeResource sr=new SafeResource();
		sr.setSafeResourceCollection(collection);
		sr.setType(type);
		sr.setTipId(tipId);
		sr.setTipType(tipType);
		safeResourceDao.save(sr);
	}
	@Override
	public void create(Long tipId,SafeResourceCollection safeResourceCollection,Integer type){
		SafeResource sr=new SafeResource();
		sr.setCode(tipId+"g");
		sr.setSafeResourceCollection(safeResourceCollection);
		sr.setTipId(tipId);
		sr.setTipType(SafeResourceTipType.ORGANIZATION);
		sr.setType(type);
		safeResourceDao.save(sr);
	}

	@Override
	public List<SafeResource> findByCollectionIdsType(List<Long> safeResourceCollectionIds,Integer safeResourceType) {
		if(ObjUtil.isEmpty(safeResourceCollectionIds)){
			return new ArrayList<SafeResource>();
		}
		return safeResourceDao.findByParam(new AndInParam("safeResourceCollection.id",safeResourceCollectionIds)
		,new AndEqParam("type", safeResourceType)
		,new AndNeParam("tipType", SafeResourceTipType.RESOURCE));
	}


	@Override
	public List<Long> collectionResource(Long collectionId, List<Long> resourceIds, Integer resourceType, List<Integer> tipType) {
		if(ObjUtil.isEmpty(resourceIds)&&ObjUtil.isEmpty(collectionId)){
			return new ArrayList<Long>(0);
		}
		return safeResourceDao.collectionResource(collectionId,resourceIds,resourceType,tipType);
	}

	@Override
	public void delete(Long collectionId, Long[] tipIds, Integer type) {
		if(ObjUtil.isEmpty(collectionId)){
			Exceptions.service("", "请指定资源库id！");
		}
		if(ObjUtil.isNotEmpty(tipIds)){
			List<Long> tips=Arrays.asList(tipIds);
			safeResourceDao.delete(collectionId,tips,type);
		}
	}

	@Override
	public void delete(Long safeResourceCollectionId, Integer type) {
		if(ObjUtil.isEmpty(safeResourceCollectionId)){
			Exceptions.service("", "请指定资源库id！");
		}
		if(ObjUtil.isEmpty(type)){
			Exceptions.service("", "请指定删除的资源类型");
		}
		safeResourceDao.delete(safeResourceCollectionId,type);
	}
	
	@Override
	public List<SafeResource> findByCollectionId(Long safeResourceCollectionId,List<Integer> tipType) {
		if(ObjUtil.isEmpty(safeResourceCollectionId)){
			return new ArrayList<SafeResource>(0);
		}
		List<Param> params = new ArrayList<Param>();
		params.add(new AndEqParam("safeResourceCollection.id", safeResourceCollectionId));
		if(ObjUtil.isNotEmpty(tipType)){
			params.add(new AndInParam("tipType", tipType));
		}
		return safeResourceDao.findByParam(params);
	}

	@Override
	public void delete(Long collectionId) {
		if(ObjUtil.isEmpty(collectionId)){
			Exceptions.service("", "请指定资源库id！");
		}
		safeResourceDao.delete(collectionId);
	}
	
	@Override
	public CategoryListBean findCategoryListBeanByUserSafeResourceType(Long userId, Integer safeResourceType) {
		List<UserSafeResourceCollection> usrcList = userSafeResourceCollectionService.findByUserId(userId);
		List<Long> collectionIds = new ArrayList<Long>();
		for (UserSafeResourceCollection usrc : usrcList) {
			collectionIds.add(usrc.getSafeResourceCollection().getId());
		}
		List<SafeResource> srList = findByCollectionIdsType(collectionIds, safeResourceType);// 资源。
		CategoryListBean bean = new CategoryListBean(collectionIds);
		for (SafeResource sr : srList) {
			if (SafeResourceTipType.ORGANIZATION.equals(sr.getTipType())) {// 域层
				bean.getOrgIds().add(sr.getTipId());
			}
			if (SafeResourceTipType.CATEGORY.equals(sr.getTipType())) {// 类别层
				bean.getCategoryIds().add(sr.getTipId());
			}
		}
		//增加当前的权限
		if(ObjUtil.isNotEmpty(UserSessionContext.getUserSession())){
			List<Agencies> childList = agenciesService.findCountChildByIds(UserSessionContext.getUserSession().getAgenciesId());
			bean.getCategoryIds().addAll(PersistableUtil.arrayTransforList(childList));
			if(UserSessionContext.getUserSession().getAgenciesId()!=null) {
				bean.getCategoryIds().add(UserSessionContext.getUserSession().getAgenciesId());
			}
		}
		return bean;
	}
	
	public Long findCountByTipId(List<Long> safeResourceCollectionIds,List<Long> resourceIds){
		if(ObjUtil.isEmpty(safeResourceCollectionIds)){
			return 0l;
		}
		return safeResourceDao.countParam(new AndInParam("safeResourceCollection.id",safeResourceCollectionIds),new AndInParam("tipId", resourceIds)
		);
	}

}
