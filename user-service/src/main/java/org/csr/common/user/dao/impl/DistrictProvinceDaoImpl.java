package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.DistrictProvinceDao;
import org.csr.common.user.domain.DistrictProvince;
import org.csr.common.user.entity.DistrictProvinceBean;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:DistrictProvinceDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("districtProvinceDao")
public class DistrictProvinceDaoImpl extends JpaDao<DistrictProvince,Long> implements DistrictProvinceDao {
    
	@Override
	public Class<DistrictProvince> entityClass() {
		return DistrictProvince.class;
	}
    /**
     * @description:根据区域ID取区域关联行政区
     * @param: districtId：区域ID
     * @return: DistrictProvince 
     * @author:wangxiujuan
     */ 
    public List<DistrictProvinceBean> findByDistrictId(Long districtId){
    	Finder finder=FinderImpl.create("select new org.csr.common.user.entity.DistrictProvinceBean(dp.id,dp.district.id,dp.province.id,dp.province.name) from DistrictProvince dp Where dp.district.id = :districtId");
		finder.setParam("districtId", districtId);
		List<DistrictProvinceBean> list = find(finder);
		return list;
    }
    
}
