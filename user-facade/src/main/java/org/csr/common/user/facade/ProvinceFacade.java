package org.csr.common.user.facade;

import org.csr.common.user.domain.Province;
import org.csr.common.user.entity.ProvinceNode;
import org.csr.core.persistence.service.BasisFacade;

public interface ProvinceFacade extends BasisFacade<ProvinceNode, Long>{

	void saveSimple(Province prov);

	void update(Province prov);

	void delete(String ids);

	boolean checkProvinceName(Long parentId, String name);

	boolean checkUpdateProvinceName(Long parentId, Long id, String name);

}
