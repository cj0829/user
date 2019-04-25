package org.csr.common.user.dao;


import org.csr.common.user.domain.OperationLog;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

public interface OperationLogDao extends BaseDao<OperationLog,Long>  {

	/**
     * @description:查询操作日志表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<OperationLog> findListPage(Page page);
    
    /**
     * @description:查询操作日志表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<OperationLog> findListPageByLoginLogId(Page page,Long loginLogId);
}
