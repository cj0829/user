package org.csr.common.user.service;

import java.util.Date;
import java.util.List;

import org.csr.common.user.domain.LoginLog;
import org.csr.core.UserSession;
import org.csr.core.persistence.service.BasisService;
import org.csr.core.queue.MessageService;
import org.csr.core.security.log.LoginLogSecurity;
/**
 * ClassName:LoginLogService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface LoginLogService extends BasisService<LoginLog, Long>,
	LoginLogSecurity,MessageService<UserSession> {


	/**
	 * update: 修改退出操作登录日志 <br/>
	 * @author caijin
	 * @param loginLogId：登录日志id
	 * @param exitType：退出类型
	 * @since JDK 1.7
	 */
	public void update(Long loginLogId, Byte exitType);
	
	/**
	 * findByOperationTime:查询date之前的数据
	 * @author huayj
	 * @param date
	 * @return
	 * @return List<LoginLog>
	 * @date&time 2016-1-12 下午5:32:41
	 * @since JDK 1.7
	 */
	public List<LoginLog> findByOperationTime(Date date);
}
