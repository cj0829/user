package org.csr.common.user.dao;

import org.csr.common.user.domain.UserPointsLog;
import org.csr.core.persistence.BaseDao;


/**
 * ClassName:UserPointsLogDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface UserPointsLogDao extends BaseDao<UserPointsLog,Long>{
  
	public Long[] sumUserPoint(Long id);
    
}
