package org.csr.common.user.facade;

import org.csr.common.user.entity.UserApprovalBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;


public interface UserApprovalFacade {

	int updatePass(Long[] userCommandIds);

	int updateRefusa(Long[] userCommandIds, String explain);

	PagedInfo<UserApprovalBean> findAllPage(Page page);


}
