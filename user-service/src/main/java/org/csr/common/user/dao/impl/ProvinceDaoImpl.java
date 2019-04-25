package org.csr.common.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.csr.common.user.dao.ProvinceDao;
import org.csr.common.user.domain.Province;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:ProvinceDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:10:05 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("provinceDao")
public class ProvinceDaoImpl  extends JpaDao<Province,Long>  implements ProvinceDao{
	
	
	@Override
	public Class<Province> entityClass() {
		return Province.class;
	}
	/**
	 * 查询全部第一级（省）城市
	 * @param page 分查询
	 * @return
	 */
	@Override
	public PagedInfo<Province> findRootLevelList(Page page) {
		Finder finder = FinderImpl.create("select d,select count(ds.id) from Province ds where ds.parentId=d.id) from Province d ");
		finder.append(" where d.parentId is null");
		List<Object[]> list = findByPage(page,finder);
		List<Province> provinceList = new ArrayList<Province>();
		Province prov;
		for(Object[] obj : list){
			prov = (Province) obj[0];
			if(obj[1] != null && (Long)obj[1] > 0)
				prov.setChildCount(((Long)obj[1]).intValue());
			else
				prov.setChildCount(0);
			provinceList.add(prov);
		}
		return createPagedInfo(finder, page, provinceList);
	}
	/**
	 * 查询子集合ids
	 * @param id
	 * @return
	 */
	@Override
	public List<Long> findChildrenIds(Long id) {
		Finder finder = FinderImpl.create("select d.id from Province d where d.parentId= :parentId");
		finder.setParam("parentId", id);
		List<Long> list = this.find(finder);
		return list;
	}

	/**
	 * 删除子集合
	 * @param parentId
	 */
	@Override
	public void deleteChildren(Long parentId) {
		Finder finder = FinderImpl.create("delete from Province d where d.parentId= :parentId");
		finder.setParam("parentId", parentId);
		this.batchHandle(finder);
	}
	

}
