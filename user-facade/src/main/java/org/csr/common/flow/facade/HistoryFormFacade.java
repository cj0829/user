package org.csr.common.flow.facade;

import java.util.List;

import org.csr.common.flow.domain.HistoryForm;
import org.csr.common.flow.entity.HistoryFormBean;
import org.csr.core.persistence.service.BasisFacade;

public interface HistoryFormFacade extends BasisFacade<HistoryFormBean, Long>{

	List<HistoryForm> findByUserTaskInstanceId(Long userTaskInstanceId,
			List<String> list);

	List<HistoryFormBean> listTaskProcessByUserTaskInstanceId(
			Long userTaskInstanceId);

}
