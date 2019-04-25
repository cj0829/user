package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.dao.UserTaskInstanceDao;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.flow.service.UserTaskInstanceService;
import org.csr.common.user.facade.UserTaskInstanceFacade;
import org.csr.common.user.service.UserService;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.web.bean.VOBase;
import org.springframework.stereotype.Service;

@Service("userTaskInstanceFacade")
public class UserTaskInstanceFacadeImpl extends SimpleBasisFacade<UserTaskInstanceBean, UserTaskInstance, Long> implements UserTaskInstanceFacade{

	@Resource
	private UserTaskInstanceService userTaskInstanceService; 
	@Resource
	private UserTaskInstanceDao userTaskInstanceDao; 
	@Resource
	private UserService userService;
	
	@Override
	public UserTaskInstanceBean wrapBean(UserTaskInstance doMain) {
		return UserTaskInstanceBean.wrapBean(doMain);
	}


	public UserTaskInstance wrapDomain(UserTaskInstanceBean entity) {
		UserTaskInstance doMain=new UserTaskInstance();
		doMain.setId(entity.getId());
		doMain.setUser(userService.findById(entity.getId()));
		doMain.setFormId(entity.getFormId());
		doMain.setFormObj(entity.getFormObj());
		doMain.setClassName(entity.getClassName());
		doMain.setOpinion(entity.getOpinion());
		
		return doMain;
	}

	@Override
	public PagedInfo<UserTaskInstanceBean> findApprovePage(Page page,
			List<Long> taskTempIds, String string, Long userId) {
		PagedInfo<UserTaskInstance> doMains=userTaskInstanceService.findApprovePage(page, taskTempIds, string, userId);
		
		return PersistableUtil.toPagedInfoBeans(doMains, new SetBean<UserTaskInstance>(){

			@Override
			public VOBase<Long> setValue(UserTaskInstance doMain) {
				return wrapBean(doMain);
			}
			
		});
	}

	@Override
	public String validationProcess(Long[] formId) {
	
		return userTaskInstanceService.validationProcess(formId);
	}


	@Override
	public BaseDao<UserTaskInstance, Long> getDao() {
		return userTaskInstanceDao;
	}
}
