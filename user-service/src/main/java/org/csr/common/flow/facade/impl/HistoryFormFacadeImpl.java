package org.csr.common.flow.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.dao.HistoryFormDao;
import org.csr.common.flow.domain.HistoryForm;
import org.csr.common.flow.entity.HistoryFormBean;
import org.csr.common.flow.facade.HistoryFormFacade;
import org.csr.common.flow.service.HistoryFormService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;
import org.springframework.stereotype.Service;
@Service("historyFormFacade")
public class HistoryFormFacadeImpl extends SimpleBasisFacade<HistoryFormBean, HistoryForm, Long> implements HistoryFormFacade{
	@Resource
	private HistoryFormService historyFormService;
	@Resource
	private HistoryFormDao historyFormDao;
	@Override
	public List<HistoryForm> findByUserTaskInstanceId(Long userTaskInstanceId,
			List<String> list) {
		
		return historyFormService.findByUserTaskInstanceId(userTaskInstanceId, list);
	}

	@Override
	public List<HistoryFormBean> listTaskProcessByUserTaskInstanceId(Long userTaskInstanceId) {
		List<HistoryForm> taskProcessList = historyFormService.findByUserTaskInstanceId(userTaskInstanceId,ObjUtil.toList("RejectNode"));
		List<HistoryFormBean> paged = PersistableUtil.toListBeans(taskProcessList, new SetBean<HistoryForm>(){
			@Override
			public VOBase<Long> setValue(HistoryForm doMain) {
				return HistoryFormBean.wrapBean(doMain);
			}
		});
		return paged;
	}

	@Override
	public HistoryFormBean wrapBean(HistoryForm doMain) {
		return HistoryFormBean.wrapBean(doMain);
	}

	@Override
	public BaseDao<HistoryForm, Long> getDao() {
		return historyFormDao;
	}
}
