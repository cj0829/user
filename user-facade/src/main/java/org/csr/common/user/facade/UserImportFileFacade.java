package org.csr.common.user.facade;

import java.util.Map;

import org.csr.common.user.entity.UserImportFileBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisFacade;
import org.springframework.web.multipart.MultipartFile;

public interface UserImportFileFacade extends BasisFacade<UserImportFileBean, Long> {

	Long countByCreated(Long userId);

	void createAndSaveUser(Map<String, MultipartFile> fileMap, Long agenciesId,StringBuffer errors, Long managerUserId, Long[] roleIds,Byte userRoleType);

	boolean checkNameIsExist(Long id, String name);

	PagedInfo<UserImportFileBean> findApproveUserImportFilePage(Page page,Long userId);
	
	
}
