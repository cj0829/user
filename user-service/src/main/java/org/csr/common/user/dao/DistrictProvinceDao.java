package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.DistrictProvince;
import org.csr.common.user.entity.DistrictProvinceBean;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:DistrictProvinceDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:09:22 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface DistrictProvinceDao extends BaseDao<DistrictProvince,Long> {
    /**
     * @description:根据区域ID取区域关联行政区
     * @param: districtId：区域ID
     * @return: DistrictProvince 
     * @author:wangxiujuan
     */ 
    public List<DistrictProvinceBean> findByDistrictId(Long districtId);
}
