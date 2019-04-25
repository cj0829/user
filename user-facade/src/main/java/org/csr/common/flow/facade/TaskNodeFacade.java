package org.csr.common.flow.facade;

import java.util.List;

import org.csr.common.flow.domain.TaskNode;
import org.csr.common.flow.entity.TaskNodeBean;
import org.csr.core.persistence.service.BasisFacade;

public interface TaskNodeFacade extends BasisFacade<TaskNodeBean, Long>{
	public TaskNode wrapDomain(TaskNodeBean entity);


	public List<TaskNodeBean> listByTaskTemp(Long taskTempId);
}
