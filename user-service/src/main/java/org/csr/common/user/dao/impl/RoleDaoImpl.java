package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.constant.RoleType;
import org.csr.common.user.dao.RoleDao;
import org.csr.common.user.domain.Role;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:RoleDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:10:00 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("roleDao")
public class RoleDaoImpl extends JpaDao<Role,Long> implements RoleDao {

	@Override
	public Class<Role> entityClass() {
		return Role.class;
	}

	 /**
    * @description:根据用户id查询角色
    * @return: admin 角色
    * @author:caijin
    */
	@Override
	public Role findAdminRoleByUserId(Long userId) {
		try {
			Finder finder=FinderImpl.create("select r from Role r,User u where r.roleType = ").append(RoleType.SYSTEM);
			finder.append(" and r.orgId=u.primaryOrgId and u.id = :userId");
			finder.setParam("userId", userId);
			List<Role> roles=this.find(finder);
			if(roles!=null && roles.size()>0){
				return roles.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PagedInfo<Role> findByUserId(Page page, Long userId, String name) {
		Finder finder=FinderImpl.create("select ur.role from UserRole ur where ");
		finder.append(" ur.user.id = :userId","userId", userId);
//		finder.append(" and ur.role.root = :root","root", UserSessionContext.getUserSession().getPrimaryOrgId());
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and ur.role.name like :roleName");
			finder.setParam("roleName", "%"+name+"%");
		}
		return this.findPage(page, finder);
	}
	@Override
	public List<Long> findByUserId(Long userId) {
		Finder finder=FinderImpl.create("select ur.role.id from UserRole ur where ");
		finder.append(" ur.user.id = :userId","userId", userId);
		return this.find(finder);
	}

	@Override
	public PagedInfo<Role> findUnByUserId(Page page, Long userId, String name) {
		Finder finder=FinderImpl.create("select r from Role r where");
		finder.append(" not exists (select ur.role from UserRole ur where ur.role.id = r.id");
		finder.append(" and ur.user.id=").append(userId);
//		finder.append(" and ur.user.root = :root").append(")");
//		finder.append(" and r.root = :root","root", UserSessionContext.getUserSession().getPrimaryOrgId());;
		if(ObjUtil.isNotBlank(name)){
			finder.append(" and ur.role.name like :roleName");
			finder.setParam("roleName", "%"+name+"%");
		}
		return this.findPage(page, finder);
	}

	@Override
	public PagedInfo<Role> findByOrgId(Page page, Long primaryOrgId) {
		Finder finder=FinderImpl.create("select r from Role r where");
		finder.append(" (r.orgId = :orgId","orgId", primaryOrgId);
		finder.append(" or r.roleType=").append(RoleType.GLOBAL).append(")");
		return this.findPage(page, finder);
	}



}
