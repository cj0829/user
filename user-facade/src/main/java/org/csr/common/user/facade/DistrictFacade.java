package org.csr.common.user.facade;

import org.csr.common.user.domain.District;
import org.csr.common.user.entity.DistrictBean;
import org.csr.core.persistence.service.BasisFacade;

public interface DistrictFacade extends BasisFacade<DistrictBean, Long>{

	boolean save(District district, String provinceIds);

	void update(District district2, String provinceIds);

	boolean checkAddDistrictName(String name);

	boolean checkUpdateDistrictName(Long id, String name);

	boolean checkNameIsExist(Long id, String name);

	void update(String provinceIds, District district);

}
