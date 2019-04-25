package org.csr.common.flow.facade.impl;

import javax.annotation.Resource;

import org.csr.common.flow.dao.TaskInstanceDao;
import org.csr.common.flow.domain.TaskInstance;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.Form;
import org.csr.common.flow.entity.TaskInstanceBean;
import org.csr.common.flow.facade.TaskInstanceFacade;
import org.csr.common.flow.service.FormSerivce;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.common.flow.support.FormSerivceFactory;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;
@Service("taskInstanceFacade")
public class TaskInstanceFacadeImpl extends SimpleBasisFacade<TaskInstanceBean, TaskInstance, Long> implements TaskInstanceFacade{
	@Resource
	private TaskInstanceService taskInstanceService;
	@Resource
	private TaskInstanceDao taskInstanceDao;
	@Resource
	FormSerivceFactory formSerivceFactory;
	
	@Override
	public void updateCloseTask(Long userTaskInstanceId) {
		taskInstanceService.updateCloseTask(userTaskInstanceId);
		
	}

	@Override
	public void updateTaskRebut(Long[] userTaskInstanceId, String opinion) {
		taskInstanceService.updateTaskRebut(userTaskInstanceId, opinion);
		
	}

	@Override
	public TaskInstanceBean wrapBean(TaskInstance doMain) {
		
		return null;
	}

	@Override
	public void taskPass(Long[] userTaskInstanceId, String opinion) {
		taskInstanceService.updateTaskPass(userTaskInstanceId,opinion,new BusinessStorage() {
			public boolean save(UserTaskInstance userTaskInstance) {
				FormSerivce formSerivce=formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
				return formSerivce.saveForm(userTaskInstance);
			}

//			@Override
//			public void end(UserTaskInstance userTaskInstance) {
//				FormSerivce formSerivce=formSerivceFactory.registrationForm(userTaskInstance.getFormObj());
////				formSerivce.endForm(userTaskInstance);
//			}
		});
		
	}

	@Override
	public BaseDao<TaskInstance, Long> getDao() {
		return taskInstanceDao;
	}

	@Override
	public TaskInstance createTaskInstance(Long taskTempId, Form form,Integer operType, String name, Long[] userIds, boolean isStartFolw) {
		return taskInstanceService.createTaskInstance(taskTempId, form, operType, name, userIds, isStartFolw);
	}

}
