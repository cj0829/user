package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.DistrictProvince;
import org.csr.common.user.entity.DistrictProvinceBean;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:DistrictProvinceService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface DistrictProvinceService extends BasisService<DistrictProvince, Long> {

	/**
	 * @description:获取区域关联行政区
	 * @param: districtId：区域id
	 * @return: list
	 * @author:wangxiujuan
	 */
	public List<DistrictProvinceBean> findByDistrictId(Long districtId);

	/**
	 * @description:获取区域关联行政区
	 * @param: districtId：区域id
	 * @return: list
	 * @author:wangxiujuan
	 */
	public String findProvinceIdsByDistrictId(Long districtId);

	/**
	 * @description:获取区域关联行政区
	 * @param: districtId：区域id
	 * @return: list
	 * @author:wangxiujuan
	 */
	public String findProvinceNamesByDistrictId(Long districtId);

	/**
	 * @description:保存区域关联行政区
	 * @param: districtId：区域id,provinceIds: 行政区id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean save(Long roleId, String districtProvinceIds);

}
