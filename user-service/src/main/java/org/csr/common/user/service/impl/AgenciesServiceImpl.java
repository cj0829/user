package org.csr.common.user.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.constant.AgenciesType;
import org.csr.common.user.constant.SafeResourceTipType;
import org.csr.common.user.constant.SafeResourceType;
import org.csr.common.user.dao.AgenciesDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.SafeResource;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.entity.CategoryListBean;
import org.csr.common.user.facade.DeleteAgencies;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.SafeResourceService;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.common.user.service.UserService;
import org.csr.common.user.service.ValidateAgencies;
import org.csr.core.Constants;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndLEParam;
import org.csr.core.persistence.param.AndLikeParam;
import org.csr.core.persistence.param.AndNeParam;
import org.csr.core.persistence.param.AndNotInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;

/**
 * ClassName:Agencies.java <br/>
 * Date:     Tue Sep 09 23:05:42 CST 2014
 * @author   n-caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 */
public class AgenciesServiceImpl extends SimpleBasisService<Agencies, Long> implements AgenciesService  {
    @Resource
    protected AgenciesDao agenciesDao;
    @Resource
    protected UserService userService;
    @Resource
	protected SafeResourceService safeResourceService;
    @Resource
    protected UserSafeResourceCollectionService userSafeResourceCollectionService;
	
    private List<DeleteAgencies> deleteAgencies;
	
    //注册初始化save调用
  	protected final static Map<String,ValidateAgencies> validateAgenciesMap = new HashMap<String,ValidateAgencies>();
  	/**
	 * registrationInitDataOrganizationService: 注册服务，在saveOrg时调用，如果需要在初始化域时，初始化对应的数据，可以实现此接口 <br/>
	 * @author liurui
	 * @param initDataOrganization
	 * @return
	 * @since JDK 1.7
	 */
	public static boolean registrationValidateAgenciesService(ValidateAgencies validateAgencies){
		try {
			validateAgenciesMap.put(validateAgencies.toString(), validateAgencies);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
    @Override
	public BaseDao<Agencies, Long> getDao() {
		return agenciesDao;
	}
    
    
    
    @Override
	public boolean findHasAuthorityByUserId(Long userId, Long agenciesId) {
    	if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			return true;
		}
    	if(ObjUtil.isEmpty(userId) || ObjUtil.isEmpty(agenciesId)){
    		return false;
    	}
    	if(agenciesId.equals(UserSessionContext.getUserSession().getAgenciesId())){
    		return true;
    	}
    	//增加当前的权限
		List<Agencies> childList = findCountChildByIds(UserSessionContext.getUserSession().getAgenciesId());
		List<Long> childids = PersistableUtil.arrayTransforList(childList);
		if(childids.contains(agenciesId)){
			return true;
		}
		
    	List<UserSafeResourceCollection> usrcList = userSafeResourceCollectionService.findByUserId(userId);
		List<Long> collectionIds = new ArrayList<Long>();
		for (UserSafeResourceCollection usrc : usrcList) {
			collectionIds.add(usrc.getSafeResourceCollection().getId());
		}
		List<SafeResource> srList =safeResourceService.findByCollectionIdsType(collectionIds, SafeResourceType.AGENCIES);// 资源。
		CategoryListBean bean = new CategoryListBean(collectionIds);
		for (SafeResource sr : srList) {
			if (SafeResourceTipType.ORGANIZATION.equals(sr.getTipType())) {// 域层
				bean.getOrgIds().add(sr.getTipId());
			}
			if (SafeResourceTipType.CATEGORY.equals(sr.getTipType())) {// 类别层
				bean.getCategoryIds().add(sr.getTipId());
			}
		}
		final List<Long> categoryIds = bean.getCategoryIds();
		if(categoryIds.contains(agenciesId)){
			return true;
		}
		if(ObjUtil.isNotEmpty(bean)){
			return agenciesDao.findByIdsOrgId(bean.getOrgIds(), agenciesId);
		}else{
			return false;
		}
	}
	@Override
	public PagedInfo<Agencies> findPageByCategoryIdsOrgId(Page page,List<Long> categoryIds,List<Long> orgId){
		
		if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)){
    		return PersistableUtil.createPagedInfo(0, page, new ArrayList<Agencies>(0));
    	}
		return agenciesDao.findPageByCategoryIdsOrgId(page,categoryIds,orgId);
	}
	
	 @Override
   	public List<Agencies> findListByCategoryIdsOrgIdName(List<Long> categoryIds,List<Integer> types,List<Long> orgId,String name) {
    	if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)){
    		return new ArrayList<Agencies>(0);
    	}
    	//这里都是有权限的
    	List<Agencies> agList=agenciesDao.findListByCategoryIdsOrgIdName(categoryIds,types,orgId,name);
    	
   		return agList;
   	}
	   	
	
	
    @Override
   	public List<Agencies> findListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgId) {
    	if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)){
    		return new ArrayList<Agencies>(0);
    	}
    	//这里都是有权限的
    	List<Agencies> agList=agenciesDao.findListByCategoryIdsOrgId(categoryIds,orgId);
    	
   		return agList;
   	}
   	
   	@Override
   	public Agencies save(Agencies agencies) {
//   		if(ObjUtil.isEmpty(agencies.getType())){
//   			Exceptions.service("orgaNameIsExist","您没有选择机构类型");
//   		}
   		//检测code唯一性
   		checkCodeIsExist(null, agencies.getCode());
   		//获取父机构
   		Agencies pNode = agenciesDao.findById(agencies.getParentId());
   		if(ObjUtil.isEmpty(pNode)){
   			pNode=agenciesDao.findById(Agencies.ROOT);
   		}
   		//说明是当前为
   		if(Agencies.ROOT.equals(pNode.getId())){
   			//当节点为内部机构时
   			if(ObjUtil.isEmpty(agencies.getOrg()) || ObjUtil.isEmpty(agencies.getOrg().getId())){
   				Exceptions.service("","您没有选择域");
   	   		}
   		}else{
   			//添加时直接设置为父 机构的
   			agencies.setOrg(pNode.getOrg());
   		}
   		if(ObjUtil.isNotEmpty(UserSessionContext.getUserSession())){
   			boolean has = findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(), agencies.getParentId());
   			if(!has){
   				Exceptions.service("","您没有权限操作当前机构");
   			}
   		}
			//当前如果不是班级的话。那么其他
		if(ObjUtil.isNotEmpty(pNode.getType()) && AgenciesType.LEAF.equals(pNode.getType())||AgenciesType.COMPANY.equals(pNode.getType())){
			Exceptions.service("","当前父节点，不能够添加子节点");
		}
   		
   		if(ObjUtil.isEmpty(agencies.getOrg()) || ObjUtil.isEmpty(agencies.getOrg().getId())){
   			Exceptions.service("orgaNameIsExist","您没有选择域");
   		}
   		
   		
   		agencies.setParentId(pNode.getId());
   		return agenciesDao.save(agencies);
   	}
   	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		Agencies agencies = agenciesDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(agencies) || agencies.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(agencies)) {
		    return false;
		}
		return true;
	}
	
	public Agencies update(Agencies agencies){
		
		//如果需要判断这里更改
		Agencies oldagencies = agenciesDao.findById(agencies.getId());
		//检测code唯一性
   		checkCodeIsExist(agencies.getId(), agencies.getCode());
		
		boolean has = findHasAuthorityByUserId(UserSessionContext.getUserSession().getUserId(),agencies.getId());
		if(!has){
			Exceptions.service("","您没有权限操作当前机构");
		}
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		oldagencies.setName(agencies.getName());
		
		//如果当前用户是超级管理员的时候，可以修改机构类型，所属域，父id
		if(Constants.USER_SUPER.equals(UserSessionContext.getUserSession().getLoginName())){
			//设置域 不能修改域
			if(ObjUtil.isNotEmpty(agencies.getOrg()) && ObjUtil.isNotEmpty(agencies.getOrg().getId())){
				oldagencies.setOrg(agencies.getOrg());
			}
			//设置 机构
			if(ObjUtil.isNotEmpty(agencies.getParentId())){
				oldagencies.setParentId(agencies.getParentId());
			}
			
			//设置 机构 类型
			if(ObjUtil.isNotEmpty(agencies.getType())){
				oldagencies.setType(agencies.getType());
			}
		}
		
		oldagencies.setCode(agencies.getCode());
		oldagencies.setAddress1(agencies.getAddress1());
		
		return agenciesDao.update(oldagencies);
	}
	
	@Override
	public int delete(Long[] ids) {
		
		Long childNum = agenciesDao.findCountChildById(ObjUtil.toList(ids));
		if(ObjUtil.longGTzero(childNum)){
			Exceptions.service("100010", "存在被引用的子机构，无法删除");
		}
		if(ObjUtil.isNotEmpty(deleteAgencies)){
			for (DeleteAgencies deleteAgencie : deleteAgencies) {
				List<Agencies> list = agenciesDao.findByIds(ids);
				
				for (Agencies agencies : list) {
					if(deleteAgencie.verifyExists(agencies)){
						Exceptions.service("100010", "此机构下有数据。");
					}
				}
			}
		}
		
		Long count = userService.findCountUserByAgenciesIds(ids);
		if(count==0){
			return agenciesDao.deleteByIds(ids);
		}else{
			Exceptions.service("100010", "此机构下有用户，无法删除，请您先删除用户");
		}
		return 0;
	}
	
	@Override
	public PagedInfo<Agencies> findByNotExitsAgenciesIds(Page page,List<Long> agenciesIds) {
		page.toParam().add(new AndNeParam("id", 1l));
		if(ObjUtil.isNotEmpty(agenciesIds)){
			page.toParam().add(new AndNotInParam("id", agenciesIds));
		}
		return agenciesDao.findAllPage(page);
	}

	@Override
	public boolean checkCodeIsExist(Long id, String code) {
		Agencies agencies = agenciesDao.existParam(new AndEqParam("code",code));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(agencies) || agencies.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(agencies)) {
		    return false;
		}
		return true;
	}


	@Override
	public List<Agencies> findCountChildByIds(Long id) {
		return agenciesDao.findCountChildByIds(id,null);
	}


	@Override
	public List<Agencies> findByOrgid(Long orgId) {
		if(ObjUtil.isEmpty(orgId)){
			return new ArrayList<Agencies>();
		}
		return agenciesDao.findByParam(new AndEqParam("org.id", orgId));
	}


	@Override
	public List<Long> findIdsListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgIds) {
		if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgIds)){
    		return new ArrayList<Long>(0);
    	}
    	//这里都是有权限的
    	return agenciesDao.findIdsListByCategoryIdsOrgId(categoryIds,orgIds);
	}
	
	public List<DeleteAgencies> getDeleteAgencies() {
		return deleteAgencies;
	}
	public void setDeleteAgencies(List<DeleteAgencies> deleteAgencies) {
		this.deleteAgencies = deleteAgencies;
	}
	
	
	@Override
	public List<Agencies> findParentNodeList(String name) {
		List<Param> listParam = new ArrayList<>();
		listParam.add(new AndLEParam("type", AgenciesType.PARENT));
		listParam.add(new AndNeParam("type", AgenciesType.ROOT));
		if(ObjUtil.isNotBlank(name)){
			listParam.add(new AndLikeParam("name", name));
		}
		return agenciesDao.findByParam(listParam);
	}
	
	
}
