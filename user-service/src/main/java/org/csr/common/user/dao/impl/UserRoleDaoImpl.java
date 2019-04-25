package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.UserRoleDao;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.UserRole;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:UserRoleDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:09:47 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl extends JpaDao<UserRole,Long> implements UserRoleDao {
	@Override
	public Class<UserRole> entityClass() {
		return UserRole.class;
	}
	
	/**
 	 * @description:根据用户Id和角色Id查询用户角色
 	 * @param: userId：用户id，roleId：角色id
 	 * @return: UserRole 
 	 * @author:wangxiujuan
 	 */ 
	public UserRole findUserRoleById(Long userId,Long roleId){
		Finder finder=FinderImpl.create("select u from UserRole u where u.user.id=:userId and u.role=:roleId");
		finder.setParam("userId",userId);
		finder.setParam("roleId",new Role(roleId));
		List<UserRole> list=find(finder);
		if(ObjUtil.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
 	}
	
	/**
	 * 根据用户id删除用户角色userId：用户id
	 * @see org.csr.common.user.dao.UserRoleDao#deleteByUserId(java.lang.Long)
	 */
	public void deleteByUserId(Long userId){
		Finder finder=FinderImpl.create("delete from UserRole u where u.user.id=:userId");
		finder.setParam("userId", userId);
		this.batchHandle(finder);
 	} 
	
	/**
	 * 根据用户id和角色Id删除用户角色userId：用户id
	 * @see org.csr.common.user.dao.UserRoleDao#deleteByUserIdRoleId(java.lang.Long, java.lang.Long)
	 */
	public int deleteByUserIdRoleId(Long userId,Long roleId){
		Finder finder=FinderImpl.create("delete from UserRole u where u.user.id=:userId and u.role.id = :roleId");
		finder.setParam("userId", userId);
		finder.setParam("roleId", roleId);
		return this.batchHandle(finder);
 	} 
	
	/**
	 * 检验角色是否有用户使用roleId：角色id
	 * @see org.csr.common.user.dao.UserRoleDao#checkUserRoleByRoleId(java.lang.Long)
	 */
	public boolean checkUserRoleByRoleId(Long roleId){
		Finder finder=FinderImpl.create("select count(*) from UserRole u where u.role.id='").append(roleId).append("'");
 		Long count = this.count(finder);
 		if(count > 0){
 		    return false;
 		}
 		return true;
 	}

	@Override
	public List<Long> findByUserId(Long userId) {
		Finder finder=FinderImpl.create("select ur.role.id from UserRole ur where ur.user.id=:userId");
		finder.setParam("userId",userId);
		return find(finder);
	}

}
