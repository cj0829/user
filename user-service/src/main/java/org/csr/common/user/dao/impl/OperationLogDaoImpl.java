package org.csr.common.user.dao.impl;

import org.csr.common.user.dao.OperationLogDao;
import org.csr.common.user.domain.OperationLog;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

@Repository("operationLogDao")
public class OperationLogDaoImpl extends JpaDao<OperationLog,Long> implements
		OperationLogDao {
	
	@Override
	public Class<OperationLog> entityClass() {
		return OperationLog.class;
	}
	/**
     * @description:查询操作日志表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<OperationLog> findListPage(Page page) {
    	Finder finder=FinderImpl.create("select ol from OperationLog ol ").insertEnd(" order by ol.operationTime desc");
		return findPage(page,finder);
    }
    
    /**
     * @description:查询操作日志表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<OperationLog> findListPageByLoginLogId(Page page,Long loginLogId) {
    	Finder finder=FinderImpl.create("select ol from OperationLog ol,User u where ol.userId = u.id and ol.loginLogId = :loginLogId");
    	finder.setParam("loginLogId", loginLogId);
    	return findPage(page,finder);
    }

}
