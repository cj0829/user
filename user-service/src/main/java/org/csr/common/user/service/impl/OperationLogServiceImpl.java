package org.csr.common.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.OperationLogDao;
import org.csr.common.user.domain.OperationLog;
import org.csr.common.user.service.OperationLogService;
import org.csr.core.UserSession;
import org.csr.core.constant.OperationLogType;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.message.OperLogMessage;
import org.csr.core.persistence.param.AndLTParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.queue.Message;
import org.csr.core.security.message.BrowseLogMessage;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:OperationLogServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("operationLogService")
public class OperationLogServiceImpl extends SimpleBasisService<OperationLog, Long>
		implements OperationLogService {
	@Resource
	private OperationLogDao operationLogDao;

	@Override
	public BaseDao<OperationLog, Long> getDao() {
		return operationLogDao;
	}
	/**
     * @description:查询操作日志表列表信息
     * @param: page： 分页
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<OperationLog> findListPage(Page page) {
    	return operationLogDao.findListPage(page);
    }
	
	/**
     * @description:查询操作日志表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<OperationLog> findListPageByLoginLogId(Page page,Long loginLogId){
    	return operationLogDao.findListPageByLoginLogId(page, loginLogId);
    }
    /**
     * 保存，浏览日志
     * @see org.csr.core.queue.MessageService#processMessages(java.util.List)
     */
	@Override
	public void processMessages(Message<UserSession> message) {
		try {
			if(ObjUtil.isNotEmpty(message)){
				if(message instanceof BrowseLogMessage){
					OperationLog log=new OperationLog();
					SecurityResourceBean functionPoint =(SecurityResourceBean)message.body().getSecurityResource();
					log.setFunctionPointCode(functionPoint.getCode());
					String functionName = "";
					if(ObjUtil.isNotBlank(functionPoint.getpName())){
						functionName = functionPoint.getpName()+"-";
					}
					log.setFunctionPointCodeName(functionName+functionPoint.getName());
					log.setOperationTime(new Date());
					log.setOperationLogType(OperationLogType.BROWSE);
					
					log.setLoginLogId(message.body().getLoginLogId());
					log.setUserId(message.body().getUserId());
					log.setOperation(functionPoint.getName());
//						log.setRoot(m.body().getRoot());
					log.setUserName(message.body().getUserName());
					log.setLoginName(message.body().getLoginName());
					operationLogDao.save(log);
				}
				if(message instanceof OperLogMessage){
					OperationLog log=new OperationLog();
					OperLogMessage om= (OperLogMessage) message;
					SecurityResourceBean functionPoint =(SecurityResourceBean)message.body().getSecurityResource();
					log.setFunctionPointCode(functionPoint.getCode());
					String functionName = "";
					if(ObjUtil.isNotBlank(functionPoint.getpName())){
						functionName = functionPoint.getpName()+"-";
					}
					log.setFunctionPointCodeName(functionName+functionPoint.getName());
					log.setOperationTime(new Date());
					log.setOperationLogType(om.getLogType());
					log.setLoginLogId(message.body().getLoginLogId());
//						log.setOldContent(om.getOldContent());
//						log.setNewContent(om.getNewContent());
					log.setUserId(message.body().getUserId());
					log.setOperation(functionPoint.getName());
//						log.setRoot(m.body().getRoot());
					log.setUserName(message.body().getUserName());
					log.setLoginName(message.body().getLoginName());
					operationLogDao.save(log);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<OperationLog> findByOperationTime(Date date) {
		return operationLogDao.findByParam(new AndLTParam("operationTime", date));
	}
	
}
