package org.csr.common.user.facade;

import java.util.Date;
import java.util.List;

import org.csr.common.user.entity.OperationLogBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisFacade;

public interface OperationLogFacade extends BasisFacade<OperationLogBean, Long>{

	PagedInfo<OperationLogBean> findListPageByLoginLogId(Page page,Long loginLogId);

	List<OperationLogBean> findByOperationTime(Date date);


}
