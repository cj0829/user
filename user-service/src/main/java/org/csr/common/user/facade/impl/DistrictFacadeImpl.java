package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.DistrictDao;
import org.csr.common.user.domain.District;
import org.csr.common.user.entity.DistrictBean;
import org.csr.common.user.facade.DistrictFacade;
import org.csr.common.user.service.DistrictService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("districtFacade")
public class DistrictFacadeImpl extends SimpleBasisFacade<DistrictBean, District, Long> implements DistrictFacade {
	@Resource
	private DistrictService districtService;
	@Resource
	private DistrictDao districtDao;

	@Override
	public DistrictBean wrapBean(District doMain) {
		return new DistrictBean(doMain.getId(),doMain.getName(),doMain.getName());
	}
//	@Override
//	public District wrapDomain(DistrictBean entity) {
//		District doMain = new District(entity.getId());
//		doMain.setName(entity.getName());
//		doMain.setRemark(entity.getRemark());
//		doMain.setRoot(entity.getRoot());
//		return doMain;
//	}
	@Override
	public boolean save(District district, String provinceIds) {
		//District wrapDomain = wrapDomain(district);
		return districtService.save(district, provinceIds);
	}

	@Override
	public void update(District district, String provinceIds) {
		//District wrapDomain = wrapDomain(district);
		districtService.update(district, provinceIds);
	}

	@Override
	public boolean checkAddDistrictName(String name) {
		return districtService.checkAddDistrictName(name);
	}

	@Override
	public boolean checkUpdateDistrictName(Long id, String name) {
		return districtService.checkUpdateDistrictName(id,name);
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		return districtService.checkNameIsExist(id,name);
	}

	@Override
	public void update(String provinceIds,District district) {
		District district2 = districtService.findById(district.getId());
		district2.setName(district.getName());
		district2.setRemark(district.getRemark());
		update(district2, provinceIds);
	}
	@Override
	public BaseDao<District, Long> getDao() {
		return districtDao;
	}
}
