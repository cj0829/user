package org.csr.common.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.csr.common.user.dao.AgenciesDao;
import org.csr.common.user.domain.Agencies;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:Agencies.java <br/>
 * Date: Tue Sep 09 23:05:42 CST 2014 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 */
@Repository("agenciesDao")
public class AgenciesDaoImpl extends JpaDao<Agencies, Long> implements
		AgenciesDao {

	@Override
	public Class<Agencies> entityClass() {
		return Agencies.class;
	}
	
	
	@Override
	public List<Agencies> findChildByIds(Long id,List<Integer> types) {
		if(ObjUtil.isEmpty(id)){
			return new ArrayList<Agencies>(0);
		}
		
		Finder finder = FinderImpl.create("select d from Agencies d ");
		finder.append(" where d.parentId = :id", "id", id);
		if(ObjUtil.isNotEmpty(types)){
			finder.append(" and d.type in (:type)", "type",types);
		}
		finder.setCacheable(true);
		return find(finder);
	}
	
	@Override
	public List<Agencies> findCountChildByIds(Long id,List<Integer> types) {
		if(ObjUtil.isEmpty(id)){
			return new ArrayList<Agencies>(0);
		}
		
		Finder finder = FinderImpl.create("select d,(select count(ds.id) from Agencies ds where ds.parentId=d.id) from Agencies d ");
		finder.append(" where d.parentId = :id", "id", id);
		if(ObjUtil.isNotEmpty(types)){
			finder.append(" and d.type in (:type)", "type",types);
		}
		finder.setCacheable(true);
		List<Object[]> list = find(finder);
		return arrayToOrganization(list,types);
	}
	
	@Override
	public List<Agencies> findListByTypeName(List<Integer> types,String name) {
		Finder finder = FinderImpl.create("select d,(select count(ds.id) from Agencies ds where ds.parentId=d.id ");
		if(ObjUtil.isNotEmpty(types)){
			finder.append("and ds.type in (:type)");
		}
		
		finder.append(") from Agencies d where 1=1 ");
		
		if(ObjUtil.isNotEmpty(types)){
			finder.append(" and d.type in (:type)", "type",types);
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and d.name like :name", "name", "%"+name+"%");
		}
		finder.setCacheable(true);
		List<Object[]> list = find(finder);
		
		List<Agencies> organizationList = new ArrayList<Agencies>();
		for (int i = 0; ObjUtil.isNotEmpty(list) && i < list.size(); i++) {
			Object[] obj = list.get(i);
			Agencies orga = (Agencies) obj[0];
			if (obj[1] != null && (Long) obj[1] > 0) {
				orga.setChildCount(((Long) obj[1]).intValue());
			} else {
				orga.setChildCount(0);
			}
			organizationList.add(orga);
		}
		return organizationList;
	}
	

	@Override
	public PagedInfo<Agencies> findPageByCategoryIdsOrgId(Page page,
			List<Long> categoryIds, List<Long> orgId) {
		if (ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)) {
			return PersistableUtil.createPagedInfo(0, page, new ArrayList<Agencies>(0));
		}
		
		Finder finder = FinderImpl.create("select d from Agencies d where d.parentId is not null ");
		finder.append("and (");

		if (ObjUtil.isNotEmpty(categoryIds)) {
			finder.append(" d.id in (:categoryIds)", "categoryIds", categoryIds);
		}
	
		if (ObjUtil.isNotEmpty(orgId)) {
			if (ObjUtil.isNotEmpty(categoryIds)) {
				finder.append(" or ");
			}
			finder.append(" d.org.id in (:orgId)", "orgId", orgId);
		}
		
		finder.append(")");
		finder.setCacheable(true);
		return findPage(page, finder);
	}
	
	
	@Override
	public List<Agencies> findListByCategoryIdsOrgIdName(List<Long> categoryIds ,List<Integer> types, List<Long> orgId,String name) {
		if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)){
    		return new ArrayList<Agencies>(0);
    	}
		ArrayList<Agencies> arrayList = new ArrayList<Agencies>();
		//查询机构
		if (ObjUtil.isNotEmpty(categoryIds)) {
			Finder finder = FinderImpl.create("select d,(select count(ds.id) from Agencies ds where ds.parentId=d.id) from Agencies d where d.parentId is not null ");
			finder.append("and (");
			finder.append(" d.id in (:categoryIds)", "categoryIds", categoryIds);
			
			finder.append(")");
			if(ObjUtil.isNotBlank(name)){
				finder.append(" and d.name like :name", "name", "%"+name+"%");
			}
			if(ObjUtil.isNotEmpty(types)){
				finder.append(" and d.type in (:type)", "type",types);
			}
			finder.setCacheable(true);
			List<Object[]> list = find(finder);
			arrayList.addAll(arrayToOrganizationName(list,types,name));
		}
		
		//查询域
		if (ObjUtil.isNotEmpty(orgId)) {
			Finder finderOrg = FinderImpl.create("select d from Agencies d where d.parentId is not null ");
			finderOrg.append("and (");
			finderOrg.append(" d.org.id in (:orgId)", "orgId", orgId);
			finderOrg.append(")");
			if(ObjUtil.isNotBlank(name)){
				finderOrg.append(" and d.name like :name", "name", "%"+name+"%");
			}
			if(ObjUtil.isNotEmpty(types)){
				finderOrg.append(" and d.type in (:type)", "type",types);
			}
			finderOrg.setCacheable(true);
			List<Agencies> orgList = find(finderOrg);
			arrayList.addAll(orgList);
		}
		
		UserSessionBasics userSession = UserSessionContext.getUserSession();
		if(ObjUtil.isNotEmpty(userSession)){
			Agencies agencies = findById(userSession.getAgenciesId());
			if(arrayList.indexOf(agencies)==-1){
				arrayList.add(agencies);
			}
			
		}
		
		if(ObjUtil.isEmpty(arrayList)){
			return arrayList;
		}
		return new ArrayList<>(PersistableUtil.toMap(arrayList).values());
	}
	

	@Override
	public List<Agencies> findListByCategoryIdsOrgId(List<Long> categoryIds, List<Long> orgId) {
		if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)){
    		return new ArrayList<Agencies>(0);
    	}
		ArrayList<Agencies> arrayList = new ArrayList<Agencies>();
		//查询机构
		if (ObjUtil.isNotEmpty(categoryIds)) {
			Finder finder = FinderImpl.create("select d,(select count(ds.id) from Agencies ds where ds.parentId=d.id) from Agencies d where d.parentId is not null ");
			finder.append("and (");
			finder.append(" d.id in (:categoryIds)", "categoryIds", categoryIds);
			finder.append(")");
			finder.setCacheable(true);
			List<Object[]> list = find(finder);
			arrayList.addAll(arrayToOrganization(list,null));
		}
		
		//查询域
		if (ObjUtil.isNotEmpty(orgId)) {
			Finder finderOrg = FinderImpl.create("select d from Agencies d where d.parentId is not null ");
			finderOrg.append("and (");
			finderOrg.append(" d.org.id in (:orgId)", "orgId", orgId);
			finderOrg.append(")");
			finderOrg.setCacheable(true);
			List<Agencies> orgList = find(finderOrg);
			arrayList.addAll(orgList);
		}
		if(ObjUtil.isEmpty(arrayList)){
			return arrayList;
		}
		return new ArrayList<>(PersistableUtil.toMap(arrayList).values());
	}

	@Override
	public List<Long> findByIdsOrgIds(List<Long> orgIds, List<Long> categoryIds) {
		Finder finder = FinderImpl.create("select d.id from Agencies d where d.org.id not in (:orgIds) and d.id in (:categoryIds) ");
		finder.setCacheable(true);
		finder.setParam("orgIds",orgIds);
		finder.setParam("categoryIds",categoryIds);
		return find(finder);
	}
	

	@Override
	public List<Long> findIdsListByCategoryIdsOrgId(List<Long> categoryIds,List<Long> orgId) {
		if(ObjUtil.isEmpty(categoryIds) && ObjUtil.isEmpty(orgId)){
    		return new ArrayList<Long>(0);
    	}
		Finder finder = FinderImpl.create("select d.id from Agencies d where d.parentId is not null ");
		finder.append("and (");

		if (ObjUtil.isNotEmpty(categoryIds)) {
			finder.append(" d.id in (:categoryIds)", "categoryIds", categoryIds);
		}
		if (ObjUtil.isNotEmpty(orgId)) {
			if (ObjUtil.isNotEmpty(categoryIds)) {
				finder.append(" or ");
			}
			finder.append(" d.org.id in (:orgId)", "orgId", orgId);
		}
		finder.append(")");
		finder.setCacheable(true);
		return find(finder);
	}

	@Override
	public List<Agencies> findPermissionsByIds(List<Long> categoryIds) {
		if(ObjUtil.isEmpty(categoryIds)){
    		return new ArrayList<Agencies>(0);
    	}
		Finder finder = FinderImpl.create("select d from Agencies d where ");

		finder.append(" d.id in (:categoryIds)", "categoryIds", categoryIds);
		finder.setCacheable(true);
		return find(finder);
	}
	
	@Override
	public Long findCountChildById(List<Long> parentIds) {
		Finder finder = FinderImpl.create("select count(d.id) from Agencies d ");
		finder.append(" where d.parentId in (:parentIds)", "parentIds", parentIds);
		finder.setCacheable(true);
		return countOriginalHql(finder);
	}

	@Override
	public boolean findByIdsOrgId(List<Long> orgIds, Long agenciesId) {
		if(ObjUtil.isEmpty(orgIds) && ObjUtil.isEmpty(agenciesId)){
			return false;
		}
		List<Agencies> list = findListByCategoryIdsOrgId(ObjUtil.toList(agenciesId), orgIds);
		
		if(ObjUtil.isEmpty(list)){
			return false;
		}
		List<Long> arrayTransforList = PersistableUtil.arrayTransforList(list);
		if(arrayTransforList.contains(agenciesId)){
			return true;
		}else{
			return false;
		}
	}
	
	private List<Agencies> arrayToOrganizationName(List<Object[]> list,List<Integer> types,String name) {
		List<Agencies> organizationList = new ArrayList<Agencies>();
		for (int i = 0; ObjUtil.isNotEmpty(list) && i < list.size(); i++) {
			Object[] obj = list.get(i);
			Agencies orga = (Agencies) obj[0];
			if (obj[1] != null && (Long) obj[1] > 0) {
				orga.setChildCount(((Long) obj[1]).intValue());
				organizationList.addAll(findCountChildByIdsName(orga.getId(),types,name));
			} else {
				orga.setChildCount(0);
			}
			organizationList.add(orga);
		}
		return organizationList;
	}
	
	
	private List<Agencies> findCountChildByIdsName(Long id,List<Integer> types,String name) {
		Finder finder = FinderImpl.create("select d,(select count(ds.id) from Agencies ds where ds.parentId=d.id) from Agencies d ");
		finder.append(" where d.parentId = :id", "id", id);
		
		if(ObjUtil.isNotEmpty(types)){
			finder.append(" and d.type in (:type)", "type",types);
		}
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and d.name like :name", "name", "%"+name+"%");
		}
		finder.setCacheable(true);
		List<Object[]> list = find(finder);
		return arrayToOrganization(list,types);
	}
	

	private List<Agencies> arrayToOrganization(List<Object[]> list,List<Integer> types) {
		List<Agencies> organizationList = new ArrayList<Agencies>();
		for (int i = 0; ObjUtil.isNotEmpty(list) && i < list.size(); i++) {
			Object[] obj = list.get(i);
			Agencies orga = (Agencies) obj[0];
			if (obj[1] != null && (Long) obj[1] > 0) {
				orga.setChildCount(((Long) obj[1]).intValue());
				organizationList.addAll(findCountChildByIds(orga.getId(),types));
			} else {
				orga.setChildCount(0);
			}
			organizationList.add(orga);
		}
		return organizationList;
	}
}
