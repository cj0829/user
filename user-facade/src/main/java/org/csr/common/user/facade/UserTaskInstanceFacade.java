package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisFacade;

public interface UserTaskInstanceFacade extends BasisFacade<UserTaskInstanceBean, Long>{

	PagedInfo<UserTaskInstanceBean> findApprovePage(Page page,
			List<Long> taskTempIds, String string, Long userId);

	String validationProcess(Long[] formId);
	
	public UserTaskInstance wrapDomain(UserTaskInstanceBean entity);

//	PagedInfo<UserBean> toPagedInfoBeans(PagedInfo<UserTaskInstanceBean> ciList,
//			SetBean<UserTaskInstanceBean> setBean);

}
