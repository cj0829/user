/**
 * Project Name:user-Service
 * File Name:AgenciesFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-2上午10:26:25
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.constant.AgenciesType;
import org.csr.common.user.constant.SafeResourceTipType;
import org.csr.common.user.constant.SafeResourceType;
import org.csr.common.user.dao.AgenciesDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.SafeResource;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.entity.AgenciesNode;
import org.csr.common.user.entity.CategoryListBean;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.InitDataOrganization;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.SafeResourceCollectionService;
import org.csr.common.user.service.SafeResourceService;
import org.csr.core.Constants;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * ClassName:AgenciesFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-2上午10:26:25 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("agenciesFacade")
public class AgenciesFacadeImpl extends SimpleBasisFacade<AgenciesNode, Agencies, Long> implements AgenciesFacade,InitDataOrganization {

	@Resource
	protected AgenciesService agenciesService;
	@Resource
	protected AgenciesDao agenciesDao;
	@Resource
	protected SafeResourceCollectionService safeResourceCollectionService;
	@Resource
	protected SafeResourceService safeResourceService;
	
	@Override
	public List<AgenciesNode> findFirstLevelList() {
		List<Agencies> list = agenciesDao.findByParam(new AndEqParam("parentId", Agencies.ROOT));
		if(ObjUtil.isEmpty(list)){
			return new ArrayList<AgenciesNode>(0);
		}
		return PersistableUtil.toListBeans(list, new SetBean<Agencies>() {
			@Override
			public AgenciesNode setValue(Agencies doMain) {
				return AgenciesNode.wrapBean(doMain);
			}
		});
	}
	
	@Override
	public List<AgenciesNode> findCompanyList(String name) {
		List<Integer> listParam = new ArrayList<>();
		listParam.add(AgenciesType.PARENT);
		listParam.add(AgenciesType.ROOT);
		
		List<Agencies> list = agenciesDao.findListByTypeName(listParam, name);
		if(ObjUtil.isEmpty(list)){
			return new ArrayList<AgenciesNode>(0);
		}
		return PersistableUtil.toListBeans(list, new SetBean<Agencies>() {
			@Override
			public AgenciesNode setValue(Agencies doMain) {
				return AgenciesNode.wrapBean(doMain);
			}
		});
	}
	
	
	public boolean findHasAuthorityByUserId(Long agenciesId){
		
		return agenciesService.findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(), agenciesId);
	}
	
	@Override
	public List<AgenciesNode> findCountChildByIds(Long id) {
		if(ObjUtil.isEmpty(id)){
			return new ArrayList<AgenciesNode>(0);
		}
		List<Agencies>  doMain=agenciesService.findCountChildByIds(id);
		List<AgenciesNode> listNode = PersistableUtil.toListBeans(doMain, new SetBean<Agencies>(){
			@Override
			public AgenciesNode setValue(Agencies doMain) {
				return wrapBean(doMain);
			}
		});
		return listNode;
	}

	
	@Override
	public PagedInfo<AgenciesNode> findPage(Page page) {
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			PagedInfo<Agencies> list = agenciesService.findAllPage(page);
			PagedInfo<AgenciesNode> pageNode = PersistableUtil.toPagedInfoBeans(list, new SetBean<Agencies>() {
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					return AgenciesNode.wrapBean(doMain);
				}
			});
			return pageNode;
		}else{
			CategoryListBean bean = safeResourceService.findCategoryListBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(),SafeResourceType.AGENCIES);
			PagedInfo<Agencies> paged = agenciesService.findPageByCategoryIdsOrgId(page,bean.getCategoryIds(),bean.getOrgIds());
			PagedInfo<AgenciesNode> pageNode=PersistableUtil.toPagedInfoBeans(paged, new SetBean<Agencies>(){
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					return wrapBean(doMain);
				}
			});
			return pageNode;
		}
	}

	@Override
	public List<AgenciesNode> findList(String name,List<Integer> types) {
		List<AgenciesNode> listNode;
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			List<Agencies> list = agenciesDao.findListByTypeName(types, name);
			listNode = PersistableUtil.toListBeans(list, new SetBean<Agencies>() {
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					return AgenciesNode.wrapBean(doMain);
				}
			});
		}else{
			CategoryListBean bean = safeResourceService.findCategoryListBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(),SafeResourceType.AGENCIES);
			final List<Long> categoryIds = bean.getCategoryIds();
			//查询出  带父类 的全分类。
			List<Agencies> categorylist = agenciesService.findListByCategoryIdsOrgIdName(categoryIds,types,bean.getOrgIds(),name);
			// 标识出，能够操作的权限节点。如果，是只赋予域需要区分出来。
			listNode = PersistableUtil.toListBeans(categorylist, new SetBean<Agencies>(){
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					AgenciesNode node = wrapBean(doMain);
					node.setPopedom(YesorNo.YES);
					return node;
				}
			});
		}
		
		return listNode;
	}

	
	@Override
	public List<AgenciesNode> findParentNodeList(String name) {
		List<AgenciesNode> listNode;
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			List<Agencies> list = agenciesService.findParentNodeList(name);
			listNode = PersistableUtil.toListBeans(list, new SetBean<Agencies>() {
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					return AgenciesNode.wrapBean(doMain);
				}
			});
		}else{
			CategoryListBean bean = safeResourceService.findCategoryListBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(),SafeResourceType.AGENCIES);
			final List<Long> categoryIds = bean.getCategoryIds();
			//查询出  带父类 的全分类。
			List<Agencies> categorylist = agenciesService.findListByCategoryIdsOrgIdName(categoryIds,ObjUtil.toList(AgenciesType.ROOT,AgenciesType.PARENT),bean.getOrgIds(),name);
			// 标识出，能够操作的权限节点。如果，是只赋予域需要区分出来。
			listNode = PersistableUtil.toListBeans(categorylist, new SetBean<Agencies>(){
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					AgenciesNode node = wrapBean(doMain);
					node.setPopedom(YesorNo.YES);
					return node;
				}
			});
		}
		
		return listNode;
	}
	
	
	@Override
	public AgenciesNode save(Agencies agencies) {
		agenciesService.save(agencies);
		return wrapBean(agencies);
	}

	@Override
	public AgenciesNode update(Agencies agencies) {
		agenciesService.update(agencies);
		return wrapBean(agencies);
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		return agenciesService.checkNameIsExist(id, name);
	}

	@Override
	public boolean checkCodeIsExist(Long id, String code) {
		return agenciesService.checkCodeIsExist(id, code);
	}

	@Override
	public BaseDao<Agencies, Long> getDao() {
		return agenciesDao;
	}

	@Override
	public AgenciesNode wrapBean(Agencies doMain) {
		return AgenciesNode.wrapBean(doMain);
	}

	@Override
	public void saveSafeResource(Long safeResourceCollectionId, List<Long> orgId,List<Long> categoryIds) {
		if(ObjUtil.isEmpty(safeResourceCollectionId) ){
			Exceptions.service("", "请选择要修改的安全资源集合");
		}
		SafeResourceCollection safeResourceCollection = safeResourceCollectionService.findById(safeResourceCollectionId);
		if(ObjUtil.isEmpty(safeResourceCollection) ){
			Exceptions.service("", "要修改的安全资源集合不存在");
		}
		
		safeResourceService.delete(safeResourceCollectionId, SafeResourceType.AGENCIES);
		if(ObjUtil.isEmpty(orgId)){
			//-->直接保存
			saveSafeResourceCategoryId(categoryIds, safeResourceCollection);
		}else{
			saveSafeResourceOrg(orgId, safeResourceCollection);
			if(ObjUtil.isNotEmpty(categoryIds)){
				categoryIds = agenciesDao.findByIdsOrgIds(orgId,categoryIds);
			}
			saveSafeResourceCategoryId(categoryIds, safeResourceCollection);
		}
	}
	
	/**
	 * 保存域
	 * @param categoryIds
	 * @param safeResourceCollection
	 */
	private void saveSafeResourceOrg(List<Long> orgids,SafeResourceCollection safeResourceCollection){
		//-->对类别的处理。
		for(int i=0;i<orgids.size();i++){
			StringBuilder stringBuilder=new StringBuilder("o-"+orgids.get(i));
			safeResourceService.save(orgids.get(i), stringBuilder.toString(), SafeResourceType.AGENCIES, safeResourceCollection);
		}
	}
	/**
	 * 保存资源,
	 * @param categoryIds
	 * @param safeResourceCollection
	 */
	private void saveSafeResourceCategoryId(List<Long> categoryIds,SafeResourceCollection safeResourceCollection){
		//-->对类别的处理。
		for(int i=0;i<categoryIds.size();i++){
			StringBuilder stringBuilder=new StringBuilder("c-"+categoryIds.get(i));
			safeResourceService.save(categoryIds.get(i), stringBuilder.toString(), SafeResourceType.AGENCIES, safeResourceCollection);
		}
	}

	@Override
	public int delete(Long[] ids) {
		return agenciesService.delete(ids);
	}

	@Override
	public List<AgenciesNode> findByOrgid(Long orgid) {
		List<Agencies> orgList = agenciesService.findByOrgid(orgid);
		List<AgenciesNode> listNode = PersistableUtil.toListBeans(orgList, new SetBean<Agencies>(){
			@Override
			public AgenciesNode setValue(Agencies doMain) {
				return wrapBean(doMain);
			}
		});
		return listNode;
	}

	@Override
	public List<AgenciesNode> findListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgIds) {
		 List<Agencies> orgList = agenciesService.findListByCategoryIdsOrgId(categoryIds, orgIds);
			List<AgenciesNode> listNode = PersistableUtil.toListBeans(orgList, new SetBean<Agencies>(){
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					return wrapBean(doMain);
				}
			});
			return listNode;
	}

	@Override
	public List<Long> findIdsListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgIds) {
		return agenciesService.findIdsListByCategoryIdsOrgId(categoryIds, orgIds);
	}
	
	@Override
	public void activatingOrganization(Organization organization,Long safeResourceCollectionId) {
		SafeResource safeResource= new SafeResource();
		safeResource.setCode(organization.getId()+"o");
		safeResource.setSafeResourceCollection(new SafeResourceCollection(safeResourceCollectionId));
		safeResource.setTipId(organization.getId());
		safeResource.setTipType(SafeResourceTipType.ORGANIZATION);
		safeResource.setType(SafeResourceType.AGENCIES);
		safeResourceService.save(safeResource);
	}

	@Override
	public void updateDataOrganization(Organization organization) {
		
	}

	
	/**
	 * 查询不包含，的id并且
	 * @param name
	 * @param types
	 * @return
	 */
	@Override
	public List<AgenciesNode> findNotIncludedList(List<Integer> types,String name,final Long agenciesId) {
		List<AgenciesNode> listNode=null;
		final List<Agencies> childByIds = agenciesDao.findCountChildByIds(agenciesId,types);
		if(ObjUtil.isNotEmpty(agenciesId)){
			Agencies agencies = agenciesDao.findById(agenciesId);
			if(ObjUtil.isNotEmpty(agencies)){
				agencies.setChildCount(2);
				childByIds.add(agencies);
			}
		}
	
		
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			List<Agencies> list = agenciesDao.findListByTypeName(types, name);
			listNode = PersistableUtil.toListBeans(list, new SetBean<Agencies>() {
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					if(!childByIds.contains(doMain) && !doMain.getId().equals(agenciesId)){
						return AgenciesNode.wrapBean(doMain);
					}
					return null;
				}
			});
		}else{
			CategoryListBean bean = safeResourceService.findCategoryListBeanByUserSafeResourceType(UserSessionContext.getUserSession().getUserId(),SafeResourceType.AGENCIES);
			final List<Long> categoryIds = bean.getCategoryIds();
			
			Iterator<Long> itids=categoryIds.iterator();
			while (itids.hasNext()) {
				Long categoryId =   itids.next();
				if(categoryId.equals(categoryId)){
					itids.remove();
					continue;
				}
				for (int i = 0; i < childByIds.size(); i++) {
					Agencies agencies = childByIds.get(i);
					
					if(ObjUtil.isNotEmpty(agencies)){
						if(categoryId.equals(agencies.getId())){
							itids.remove();
						}
					}
				}
			}
				
			//查询出  带父类 的全分类。
			List<Agencies> categorylist = agenciesService.findListByCategoryIdsOrgIdName(categoryIds,types,bean.getOrgIds(),name);
			// 标识出，能够操作的权限节点。如果，是只赋予域需要区分出来。
			listNode = PersistableUtil.toListBeans(categorylist, new SetBean<Agencies>(){
				@Override
				public AgenciesNode setValue(Agencies doMain) {
					AgenciesNode node = wrapBean(doMain);
					node.setPopedom(YesorNo.YES);
					return node;
				}
			});
		}
		return listNode;
	}
	
	@Override
	public AgenciesNode findByCode(String agenciesCode) {
		if(ObjUtil.isBlank(agenciesCode)) {
			return null;
		}
		Agencies agencies = agenciesDao.existParam(new AndEqParam("code",agenciesCode));
		return AgenciesNode.wrapBean(agencies);
	}

	@Override
	public void createAndSaveAgencies(Map<String, MultipartFile> fileMap,
			Long agenciesId, StringBuffer errors, Byte general) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AgenciesNode> findAllListByNameType(String name,List<Integer> types) {
		List<Agencies> list = agenciesDao.findListByTypeName(types, name);
		return PersistableUtil.toListBeans(list, new SetBean<Agencies>() {
			@Override
			public AgenciesNode setValue(Agencies doMain) {
				return AgenciesNode.wrapBean(doMain);
			}
		});
	}

	@Override
	public List<Long> findbIdsByCodes(List<String> agenciesCodes) {
		if(ObjUtil.isEmpty(agenciesCodes)) {
			return new ArrayList<Long>(0);
		}
		return 	agenciesDao.findFieldListByParam("id", new AndInParam("code", agenciesCodes));
	}

	@Override
	public void initDataOrganization(Organization organization) {
		// TODO Auto-generated method stub
		
	}

	
}

