package org.csr.common.user.facade;

import org.csr.core.persistence.business.domain.Organization;

public interface InitDataOrganization {
	
	public void initDataOrganization(Organization organization);
	
	public void updateDataOrganization(Organization organization);

	public void activatingOrganization(Organization org, Long safeResourceCollectionId);
}
