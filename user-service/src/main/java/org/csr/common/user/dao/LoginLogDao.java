package org.csr.common.user.dao;

import java.util.Date;
import java.util.List;

import org.csr.common.user.domain.LoginLog;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:LoginLogDao.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-2-28上午10:09:09 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
public interface LoginLogDao extends BaseDao<LoginLog, Long> {
	
	List<LoginLog> findbyLoginTime(Date date);

}
