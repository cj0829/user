package org.csr.common.user.facade.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.OperationLogDao;
import org.csr.common.user.domain.OperationLog;
import org.csr.common.user.entity.OperationLogBean;
import org.csr.common.user.facade.OperationLogFacade;
import org.csr.common.user.service.OperationLogService;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.springframework.stereotype.Service;


@Service("operationLogFacade")
public class OperationLogFacadeImpl extends SimpleBasisFacade<OperationLogBean,OperationLog, Long> implements OperationLogFacade {
	@Resource
	private OperationLogService operationLogService;
	@Resource
	private OperationLogDao operationLogDao;

	@Override
	public OperationLogBean wrapBean(OperationLog doMain) {
		return OperationLogBean.wrapBean(doMain);
	}

//	@Override
//	public OperationLog wrapDomain(OperationLogBean entity) {
//		OperationLog doMain = new OperationLog();
//		doMain.setId(entity.getId());
//		doMain.setOperation(entity.getOperation());
//		doMain.setOldContent(entity.getOldContent());
//		doMain.setNewContent(entity.getNewContent());
//		doMain.setUserId(entity.getUserId());
//		doMain.setUserName(entity.getUserName());
//		doMain.setOperationTime(entity.getOperationTime());
//		doMain.setOperationLogType(entity.getOperationLogType());
//		doMain.setFunctionPointCode(entity.getFunctionPointCode());
//		doMain.setLoginName(entity.getLoginName());
//		doMain.setFunctionPointCodeName(entity.getFunctionPointCodeName());
//		return doMain;
//	}
	
	@Override
	public PagedInfo<OperationLogBean> findListPageByLoginLogId(Page page,Long loginLogId) {
		return PersistableUtil.toPagedInfoBeans(operationLogService.findListPageByLoginLogId(page, loginLogId),new SetBean<OperationLog>() {
			@Override
			public OperationLogBean setValue(OperationLog doMain) {
				return wrapBean(doMain);
			}
		});
	}
	@Override
	public List<OperationLogBean> findByOperationTime(Date date) {
		return PersistableUtil.toListBeans(operationLogService.findByOperationTime(date),new SetBean<OperationLog>() {
			@Override
			public OperationLogBean setValue(OperationLog doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public BaseDao<OperationLog, Long> getDao() {
		return operationLogDao;
	}

}
