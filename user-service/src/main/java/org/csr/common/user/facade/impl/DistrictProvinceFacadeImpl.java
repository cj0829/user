package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.DistrictProvinceDao;
import org.csr.common.user.domain.DistrictProvince;
import org.csr.common.user.entity.DistrictProvinceBean;
import org.csr.common.user.facade.DistrictProvinceFacade;
import org.csr.common.user.service.DistrictProvinceService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("districtProvinceFacade")
public class DistrictProvinceFacadeImpl extends
		SimpleBasisFacade<DistrictProvinceBean, DistrictProvince, Long> implements
		DistrictProvinceFacade {
	
	@Resource
	private DistrictProvinceService districtProvinceService;
	@Resource
	private DistrictProvinceDao districtProvinceDao;

	@Override
	public Object findProvinceNamesByDistrictId(Long id) {
		return null;
	}

	@Override
	public DistrictProvinceBean wrapBean(DistrictProvince doMain) {
		return DistrictProvinceBean.wrapBean(doMain);
	}

	@Override
	public BaseDao<DistrictProvince, Long> getDao() {
		return districtProvinceDao;
	}

}
