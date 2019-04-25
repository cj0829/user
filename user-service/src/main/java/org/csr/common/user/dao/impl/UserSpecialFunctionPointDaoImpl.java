package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.UserSpecialFunctionPointDao;
import org.csr.common.user.domain.UserSpecialFunctionPoint;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:UserSpecialFunctionPointDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:09:41 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("userSpecialFunctionPointDao")
public class UserSpecialFunctionPointDaoImpl extends
		JpaDao<UserSpecialFunctionPoint, Long> implements
		UserSpecialFunctionPointDao {

	@Override
	public Class<UserSpecialFunctionPoint> entityClass() {
		return UserSpecialFunctionPoint.class;
	}
	/**
     * @description:根据用户ID取用户关联功能点
     * @param: roleId：用户ID
     * @return: RoleFunctionPoint 
     * @author:wangxiujuan
     */ 
    public List<UserSpecialFunctionPoint> findByUserId(Long userId){
    	Finder finder=FinderImpl.create("select ufp from UserSpecialFunctionPoint ufp where ufp.userId = :userId");
		finder.setParam("userId", userId);
		return find(finder);
    }
    
    /**
     * @description:根据用户ID和功能点Id取用户关联功能点
     * @param: roleId,functionPointId：用户ID,功能点Id
     * @return: UserSpecialFunctionPoint 
     * @author:wangxiujuan
     */ 
    public List<UserSpecialFunctionPoint> findByUserIdFunctionPointId(Long userId,Long functionPointId){
    	Finder finder=FinderImpl.create("select ufp from UserSpecialFunctionPoint ufp where ufp.user.id = :userId and ufp.functionPoint.id = :functionPointId");
		finder.setParam("userId", userId);
		finder.setParam("functionPointId", functionPointId);
		return find(finder);
    }
}
