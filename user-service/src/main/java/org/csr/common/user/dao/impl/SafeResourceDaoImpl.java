package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.SafeResourceDao;
import org.csr.common.user.domain.SafeResource;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:SafeResource.java <br/>
 * Date: Wed Dec 02 11:54:01 CST 2015 <br/>
 * 
 * @author liurui-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 dao实现
 */
@Repository("safeResourceDao")
public class SafeResourceDaoImpl extends JpaDao<SafeResource, Long> implements
		SafeResourceDao {

	@Override
	public Class<SafeResource> entityClass() {
		return SafeResource.class;
	}

	@Override
	public List<Long> collectionResource(Long collectionId,
			List<Long> resourceIds, Integer resourceType, List<Integer> tipType) {
		Finder finder = FinderImpl.create("select sr.tipId from SafeResource sr where sr.safeResourceCollection.id=:collectionId");
		finder.setParam("collectionId", collectionId);
		
		if(ObjUtil.isNotEmpty(resourceIds)){
			finder.append(" and sr.tipId in(:resourceIds)");
			finder.setParam("resourceIds", resourceIds);
		}
		if (ObjUtil.isNotEmpty(resourceType)) {
			finder.append(" and sr.type=:type");
			finder.setParam("type", resourceType);
		}
		if (ObjUtil.isNotEmpty(tipType)) {
			finder.append(" and sr.tipType in (:tipType)");
			finder.setParam("tipType", tipType);
		}
		return find(finder);
	}

	@Override
	public void delete(Long collectionId, List<Long> tipIds, Integer type) {
		Finder finder = FinderImpl
				.create("delete from SafeResource sr where sr.safeResourceCollection.id=:collectionId and sr.tipId in(:tipIds)");
		finder.setParam("collectionId", collectionId);
		finder.setParam("tipIds", tipIds);
		if (ObjUtil.isNotEmpty(type)) {
			finder.append(" and sr.type=:type");
			finder.setParam("type", type);
		}
		batchHandle(finder);
	}

	@Override
	public int delete(Long collectionId, Integer type) {
		Finder finder = FinderImpl
				.create("delete from SafeResource sr where sr.safeResourceCollection.id=:collectionId ");
		finder.setParam("collectionId", collectionId);
		finder.append(" and sr.type=:type");
		finder.setParam("type", type);
		return batchHandle(finder);
	}

	@Override
	public void delete(Long collectionId) {
		Finder finder = FinderImpl
				.create("delete from SafeResource sr where sr.safeResourceCollection.id=:collectionId");
		finder.setParam("collectionId", collectionId);
		batchHandle(finder);
	}

//	@Override
//	public Long findCountByOrgIdTipId(List<Long> safeResourceCollectionIds,
//			List<Long> orgids, List<Long> resourceIds) {
//		Finder finder = FinderImpl
//				.create("select count(sr.id) from SafeResource sr where sr.safeResourceCollection.id in(:collectionId) ");
//		if (ObjUtil.isEmpty(safeResourceCollectionIds)
//				&& ObjUtil.isEmpty(orgids)) {
//			return 0l;
//		}
//
//		finder.append("(");
//		if (ObjUtil.isNotEmpty(orgids)) {
//			finder.append("(");
//		}
//		if (ObjUtil.isNotEmpty(orgids)) {
//
//		}
//		if (ObjUtil.isNotEmpty(resourceIds)) {
//
//		}
//		finder.append(")");
//		finder.setParam("collectionId", collectionId);
//		return countOriginalHql(finder);
//	}

}
