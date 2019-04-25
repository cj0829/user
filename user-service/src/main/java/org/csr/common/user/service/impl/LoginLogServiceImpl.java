package org.csr.common.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.ExitType;
import org.csr.common.user.dao.LoginLogDao;
import org.csr.common.user.domain.LoginLog;
import org.csr.common.user.domain.User;
import org.csr.common.user.service.LoginLogService;
import org.csr.core.UserSession;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.queue.Message;
import org.csr.core.security.message.LogoutMessage;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:LoginLogServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("loginLogService")
public class LoginLogServiceImpl extends SimpleBasisService<LoginLog, Long>
		implements LoginLogService {
	@Resource
	private LoginLogDao loginLogDao;

	@Override
	public BaseDao<LoginLog, Long> getDao() {
		return loginLogDao;
	}
	/**
	 * @description:查询登录日志表列表信息
	 * @param: page： 分页
	 * @return: ResultInfo
	 * @author:wangxiujuan
	 */
	@Override
	public PagedInfo<LoginLog> findAllPage(Page page) {
		return loginLogDao.findAllPage(page);
	}
	/**
	 * 记录登入日志
	 * @see org.csr.core.security.log.LoginLogSecurity#loginOperation(org.csr.core.security.userdetails.UserSession)
	 */
	@Override
	public Long saveLoginOperation(UserSession userSession) {
		LoginLog loginLog = new LoginLog();
		User user=new User();
		user.setId(userSession.getUserId());
		loginLog.setId(nextSequence());
		loginLog.setIpAdress(userSession.getClientAddr());
	    loginLog.setUserId(user.getId());
	    loginLog.setUserName(userSession.getUserName());
	    loginLog.setLoginName(userSession.getLoginName());
	    loginLog.setLogintype(userSession.getApptype());
//	    loginLog.setRoot(userSession.getRoot());
	    loginLog.setLoginTime(new Date()); 
	    loginLogDao.save(loginLog);
		return loginLog.getId();
	}
	/**
	 *修改退出操作登录日志loginLogId：登录日志id,exitType：退出类型
	 * @see org.csr.common.user.service.LoginLogService#update(java.lang.Long, java.lang.Integer)
	 */
	public void update(Long loginLogId, Byte exitType) {
		LoginLog loginLog = loginLogDao.findById(loginLogId);
		if(ObjUtil.isEmpty(loginLog)){
			UserSession userSession = UserSessionContext.getUserSession();
			if(ObjUtil.isNotEmpty(userSession)){
				loginLog = new LoginLog();
				User user=new User();
				user.setId(userSession.getUserId());
				loginLog.setId(nextSequence());
				loginLog.setIpAdress(userSession.getClientAddr());
			    loginLog.setUserId(user.getId());
			    loginLog.setUserName(userSession.getUserName());
			    loginLog.setLoginName(userSession.getLoginName());
//			    loginLog.setRoot(userSession.getRoot());
			    loginLog.setExitType(exitType);
				loginLog.setLogoutTime(new Date());
			    loginLogDao.save(loginLog);
			}
		}else{
			loginLog.setExitType(exitType);
			loginLog.setLogoutTime(new Date());
			loginLogDao.update(loginLog);
		}
	}

	/**
	 * 用户退出，记退出日志
	 * @see org.csr.core.queue.MessageService#processMessages(java.util.List)
	 */
	@Override
	public void processMessages(Message<UserSession> msg) {
		if(ObjUtil.isNotEmpty(msg)){
			if(msg instanceof LogoutMessage){
				update(msg.body().getLoginLogId(),ExitType.NORMAL);
			}
		}
	}
	@Override
	public List<LoginLog> findByOperationTime(Date date) {
//		List<LoginLog> list = loginLogDao.findByParam(new AndLTParam("loginTime",date));
		List<LoginLog> list = loginLogDao.findbyLoginTime(date);
		
		return list;
	}

}
