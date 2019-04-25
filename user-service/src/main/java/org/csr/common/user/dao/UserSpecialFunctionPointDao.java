package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.UserSpecialFunctionPoint;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:UserSpecialFunctionPointDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:08:24 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface UserSpecialFunctionPointDao extends BaseDao<UserSpecialFunctionPoint,Long> {

	/**
	 * findByUserId: 根据用户ID取用户关联功能点<br/>
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	List<UserSpecialFunctionPoint> findByUserId(Long userId);

	/**
	 * findByUserIdFunctionPointId: 查询用户的特殊权限。 <br/>
	 * @author caijin
	 * @param userId
	 * @param id
	 * @return
	 * @since JDK 1.7
	 */
	List<UserSpecialFunctionPoint> findByUserIdFunctionPointId(Long userId, Long id);
}
