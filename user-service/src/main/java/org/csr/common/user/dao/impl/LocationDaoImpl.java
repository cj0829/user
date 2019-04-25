package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.LocationDao;
import org.csr.common.user.domain.Location;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;


/**
 * ClassName:Location.java <br/>
 * Date: Wed Aug 23 15:49:29 CST 2017 <br/>
 * 
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 位置 dao实现
 */
@Repository("locationDao")
public class LocationDaoImpl extends JpaDao<Location,Long> implements LocationDao{

	@Override
	public Class<Location> entityClass(){
		return Location.class;
	}
	
	public PagedInfo<Location> findDropDownList(Page page,List<Long> selecteds){
		Finder finder = FinderImpl.create("select t from Location t where 1=1");
		if(ObjUtil.isNotEmpty(selecteds)){
			finder.append(" or (t.id in (:selecteds))", "selecteds", selecteds);
		}
		return findPage(page,finder);
	}
}
