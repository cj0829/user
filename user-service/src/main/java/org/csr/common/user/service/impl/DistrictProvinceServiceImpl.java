/**
 * Project Name:common
 * File Name:DistrictProvinceServiceImpl.java
 * Package Name:org.csr.common.user.service.impl
 * Date:2014-1-27上午9:59:27
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */
package org.csr.common.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.DistrictProvinceDao;
import org.csr.common.user.domain.District;
import org.csr.common.user.domain.DistrictProvince;
import org.csr.common.user.domain.Province;
import org.csr.common.user.entity.DistrictProvinceBean;
import org.csr.common.user.service.DistrictProvinceService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.springframework.stereotype.Service;

/**
 * ClassName:DistrictProvinceServiceImpl <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
@Service("districtProvinceService")
public class DistrictProvinceServiceImpl extends
		SimpleBasisService<DistrictProvince, Long> implements
		DistrictProvinceService {

	@Resource
	private DistrictProvinceDao districtProvinceDao;

	@Override
	public BaseDao<DistrictProvince, Long> getDao() {
		return districtProvinceDao;
	}

	/**
	 * @description:获取区域关联行政区
	 * @param: districtId：区域id
	 * @return: list
	 * @author:wangxiujuan
	 */
	public List<DistrictProvinceBean> findByDistrictId(Long districtId) {
		List<DistrictProvinceBean> dpList = districtProvinceDao.findByDistrictId(districtId);
		return dpList;
	}

	/**
	 * @description:获取区域关联行政区
	 * @param: districtId：区域id
	 * @return: list
	 * @author:wangxiujuan
	 */
	public String findProvinceIdsByDistrictId(Long districtId) {
		List<DistrictProvinceBean> dpList = districtProvinceDao
				.findByDistrictId(districtId);
		String provinceIds = "";
		for (int i = 0; i < dpList.size(); i++) {
			DistrictProvinceBean dp = dpList.get(i);
			Long provinceId = dp.getProvinceId();
			provinceIds = provinceIds + provinceId + ",";
		}
		if (dpList.size() > 0) {
			provinceIds = provinceIds.substring(0, provinceIds.length() - 1);
		}
		return provinceIds;
	}

	/**
	 * @description:获取区域关联行政区
	 * @param: districtId：区域id
	 * @return: list
	 * @author:wangxiujuan
	 */
	public String findProvinceNamesByDistrictId(Long districtId) {
		List<DistrictProvinceBean> dpList = districtProvinceDao.findByDistrictId(districtId);
		String provinceNames = "";
		for (int i = 0; i < dpList.size(); i++) {
			DistrictProvinceBean dp = (DistrictProvinceBean) dpList.get(i);
			String provinceName = dp.getProvinceName();
			provinceNames = provinceNames + provinceName + ",";
		}
		if (dpList.size() > 0) {
			provinceNames = provinceNames.substring(0,provinceNames.length() - 1);
		}
		return provinceNames;
	}

	/**
	 * @description:保存区域关联行政区
	 * @param: districtId：区域id,provinceIds: 行政区id数组
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean save(Long districtId, String provinceIds) {
		DistrictProvince districtProvince = null;
		List<DistrictProvinceBean> dpList = this.findByDistrictId(districtId);
		if (dpList != null && dpList.size() > 0) {
			for (int i = 0; i < dpList.size(); i++) {
				DistrictProvinceBean dp = (DistrictProvinceBean) dpList.get(i);
				districtProvinceDao.deleteById(dp.getId());
			}
		}
		String[] ids = provinceIds.split(",");
		for (String id : ids) {
			districtProvince = new DistrictProvince();
			districtProvince.setDistrict(new District(districtId));
			districtProvince.setProvince(new Province(Long.parseLong(id)));
			districtProvinceDao.save(districtProvince);
		}
		return true;
	}

}
