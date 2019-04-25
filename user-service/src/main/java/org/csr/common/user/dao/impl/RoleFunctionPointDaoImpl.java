package org.csr.common.user.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.csr.common.user.constant.AuthenticationMode;
import org.csr.common.user.dao.RoleFunctionPointDao;
import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:RoleFunctionPointDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:09:54 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("roleFunctionPointDao")
public class RoleFunctionPointDaoImpl extends JpaDao<RoleFunctionPoint,Long>  implements RoleFunctionPointDao {

	@Override
	public Class<RoleFunctionPoint> entityClass() {
		return RoleFunctionPoint.class;
	}
	
	@Override
	public RoleFunctionPoint findByRoleFunctionPointId(Long roleId,Long functionPointId) {
		Finder finder=FinderImpl.create("select rfp from RoleFunctionPoint rfp where rfp.role.id = :roleId and rfp.functionPoint.id=:functionPointId");
		finder.setParam("roleId", roleId);
		finder.setParam("functionPointId", functionPointId);
		List<RoleFunctionPoint> list=this.find(finder);
		if(ObjUtil.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
    /**
     * 根据角色ID取角色关联功能点
     * @see org.csr.common.user.dao.RoleFunctionPointDao#findByRoleId(java.lang.Long)
     */
    public List<RoleFunctionPoint> findByRoleId(Long roleId,Byte functionPointType){
    	Finder finder=FinderImpl.create("select rfp from RoleFunctionPoint rfp where rfp.role.id = :roleId");
		finder.setParam("roleId", roleId);
		if(ObjUtil.isNotEmpty(functionPointType)){
			finder.append(" and rfp.functionPoint.functionPointType=:functionPointType", "functionPointType", functionPointType);
		}
		return this.find(finder);
    }
    
    /**
     * 删除角色中指定的功能点
     * @see org.csr.common.user.dao.RoleFunctionPointDao#deleteByRoleId(java.lang.Long)
     */
    @Override
   	public int deleteByRoleId(Long roleId) {
    	Finder finder=FinderImpl.create("delete from RoleFunctionPoint rfp where rfp.role.id = :roleId");
    	finder.setParam("roleId", roleId);
   		return batchHandle(finder);
   	}

    /**
     * 删除角色中指定的功能点
     * 如果功能点为null，则不删除角色中的功能点。
     * @see org.csr.common.user.dao.RoleFunctionPointDao#deleteByRoleId(java.lang.Long, java.lang.Long[])
     */
    @Override
   	public int deleteByRoleId(Long roleId, Long[] functionPointIds) {
    	if(ObjUtil.isEmpty(functionPointIds)){
			return 0;
		}
    	Finder finder=FinderImpl.create("delete from RoleFunctionPoint rfp where rfp.role.id = :roleId ");
    	finder.setParam("roleId", roleId);
       	finder.append(" and rfp.functionPoint.id in (:functionPointIds)","functionPointIds",Arrays.asList(functionPointIds));
      	return batchHandle(finder);
   	}

	@Override
	public int deleteByFunctionPointIds(Long[] functionPointIds) {
		if(ObjUtil.isEmpty(functionPointIds)){
			return 0;
		}
		Finder finder=FinderImpl.create("delete from RoleFunctionPoint rfp where");
       	finder.append(" rfp.functionPoint.id in (:functionPointIds)","functionPointIds",Arrays.asList(functionPointIds));
      	return batchHandle(finder);
	}

	@Override
	public int updateMustAll(Long adminRoleId) {
		if(ObjUtil.isEmpty(adminRoleId)){
			return 0;
		}
		Finder finder=FinderImpl.create("update RoleFunctionPoint rfp set rfp.authenticationMode=:authenticationMode  where");
       	finder.append(" rfp.role.id = :adminRoleId","adminRoleId",adminRoleId);
       	finder.setParam("authenticationMode", AuthenticationMode.MUST);
      	return batchHandle(finder);
	}

	
}
