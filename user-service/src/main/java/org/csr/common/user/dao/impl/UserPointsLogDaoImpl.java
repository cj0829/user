package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.UserPointsLogDao;
import org.csr.common.user.domain.UserPointsLog;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:UserPointsLogDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("userPointsLogDao")
public class UserPointsLogDaoImpl extends JpaDao<UserPointsLog,Long> 
	implements UserPointsLogDao {
	
    @Override
	public Class<UserPointsLog> entityClass() {
		return UserPointsLog.class;
	}
    
    public Long[] sumUserPoint(Long userId){
    	Finder finder=FinderImpl.create("select sum(pl.points1),sum(pl.points2) from UserPointsLog pl where pl.userId=:user");
    	finder.setParam("user", userId);
    	List<Object[]> list = find(finder);
    	if(list != null && list.size() > 0){
    		Object[] sum=list.get(0);
			return new Long[]{(Long) sum[0],(Long) sum[1]};
		}else{
			return new Long[]{0l,0l};
		}
    }
    
}
