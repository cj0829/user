package org.csr.common.user.facade;

import java.util.Date;
import java.util.List;

import org.csr.common.user.entity.LoginLogBean;
import org.csr.core.persistence.service.BasisFacade;

public interface LoginLogFacade extends BasisFacade<LoginLogBean, Long>{

	List<LoginLogBean> findByOperationTime(Date date);

}
