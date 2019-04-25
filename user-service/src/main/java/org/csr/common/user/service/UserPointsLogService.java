package org.csr.common.user.service;

import org.csr.common.user.domain.UserPointsLog;
import org.csr.core.UserSession;
import org.csr.core.persistence.service.BasisService;
import org.csr.core.queue.MessageService;

/**
 * ClassName:UserPointsLogService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface UserPointsLogService extends BasisService<UserPointsLog, Long>,MessageService<UserSession>{
   
}
