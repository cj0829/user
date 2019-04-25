package org.csr.common.user.facade;

import org.csr.common.user.entity.DistrictProvinceBean;
import org.csr.core.persistence.service.BasisFacade;

public interface DistrictProvinceFacade extends BasisFacade<DistrictProvinceBean, Long>{

	Object findProvinceNamesByDistrictId(Long id);


}
