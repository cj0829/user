package org.csr.common.flow.facade;

import org.csr.common.flow.domain.TaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.entity.TaskInstanceBean;
import org.csr.core.persistence.service.BasisFacade;

public interface TaskInstanceFacade extends BasisFacade<TaskInstanceBean, Long>{

	
	/**
	 * 根据流程模板，创建新工作流程实例 <br/>
	 * 不同的流程模板，对应的流程不一样。 <br/>
	 * 任务发布之后，需要根据任务模板判断当前任务是否需要启动,根据挑剔是否提交流程
	 * @author caijin
	 * @param taskTempId
	 * @param form
	 * @param operType
	 * @param name
	 * @param userIds
	 * @param isStartFolw
	 * @return
	 * @since JDK 1.7
	 */
	public TaskInstance createTaskInstance(Long taskTempId,Form form,Integer operType,String name,Long[] userIds,boolean isStartFolw);
	
	void updateCloseTask(Long userTaskInstanceId);

	void updateTaskRebut(Long[] userTaskInstanceId, String opinion);

	void taskPass(Long[] userTaskInstanceId, String opinion);



	

}
