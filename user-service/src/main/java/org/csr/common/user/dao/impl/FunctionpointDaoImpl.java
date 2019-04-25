package org.csr.common.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.csr.common.user.constant.AuthenticationMode;
import org.csr.common.user.constant.FunctionPointType;
import org.csr.common.user.constant.RoleType;
import org.csr.common.user.dao.FunctionpointDao;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:FunctionpointDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("functionpointDao")
public class FunctionpointDaoImpl extends JpaDao<FunctionPoint, Long>  implements FunctionpointDao {
	
	
	@Override
	public Class<FunctionPoint> entityClass() {
		return FunctionPoint.class;
	}
	@Override
	public List<FunctionPoint> findAllModule() {
		Finder finder=FinderImpl.create("select fp from FunctionPoint fp where ");
		finder.setCacheable(true);
		finder.append(" fp.functionPoint is null ");
		finder.append(" and fp.functionPointType = ").append(FunctionPointType.TYPE);
		finder.setCacheable(true);
		return find(finder);
	}
	
	@Override
	public List<FunctionPoint> findAllFunction() {
		Finder finder=FinderImpl.create("select fp from FunctionPoint fp where ");
		finder.setCacheable(true);
		finder.append(" fp.functionPoint is not null ");
		finder.append(" and fp.functionPointType = ").append(FunctionPointType.TYPE);
		return find(finder);
	}
	/**
	 * 查询全部能授权的功能点,如果structure=true，只查询库
	 * @see org.csr.common.user.dao.FunctionpointDao#findAllCanBeAuthorized()
	 */
	@Override
	public List<FunctionPoint> findAllCanBeAuthorized(boolean structure) {
		Finder finder=FinderImpl.create("select distinct fp from FunctionPoint fp where 1=1 ");
		if(structure){
			finder.append(" and fp.functionPointType = ").append(FunctionPointType.TYPE);
		}else{
			finder.append(" and fp.authenticationMode = ").append(AuthenticationMode.MUST);
		}
		finder.insertEnd(" order by fp.code asc");
		finder.setCacheable(true);
		return find(finder);
	}
	/**
	 * 查询全部能授权的功能点,如果structure=true，只查询库
	 * @see org.csr.common.user.dao.FunctionpointDao#findAllCanBeAuthorized()
	 */
	@Override
	public List<Long> findIdAllCanBeAuthorized(boolean structure) {
		Finder finder=FinderImpl.create("select distinct fp.id from FunctionPoint fp where 1=1 ");
		if(structure){
			finder.append(" and fp.functionPointType = ").append(FunctionPointType.TYPE);
		}else{
			finder.append(" and fp.authenticationMode = ").append(AuthenticationMode.MUST);
		}
		finder.insertEnd(" order by fp.code asc");
		finder.setCacheable(true);
		return find(finder);
	}
	
	/**
	 *  根据用户id查询，用户查询已经授权的全部功能点<br/>
	 *  如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。没有跟权限关联,
	 * @see org.csr.common.user.dao.FunctionpointDao#findRoleFunctionPointByUserId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findRoleFunctionPointByUserId(Long userId) {
		Finder finder=FinderImpl.create("select fp from FunctionPoint fp where fp.id in");
		finder.append("(select rfp.functionPoint.id from RoleFunctionPoint rfp ");
		//只查询类型为功能点
		finder.append(" where rfp.functionPoint.functionPointType = ").append(FunctionPointType.FUNCTIONPOINT);
		//子查询
		finder.append(" and rfp.role.id in (select ur.role.id from UserRole ur where ur.user.id = :userId))");
		
		finder.setParam("userId", userId);
		finder.insertEnd(" order by fp.code asc");
		finder.setCacheable(true);
		return find(finder);
	}
	
	/**
	 *  根据用户id查询，用户查询已经授权的全部功能点<br/>
	 *  如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。没有跟权限关联,
	 * @see org.csr.common.user.dao.FunctionpointDao#findRoleFunctionPointByUserId(java.lang.Long)
	 */
	@Override
	public List<Long> findIdRoleFunctionPointByUserId(Long userId) {
		Finder finder=FinderImpl.create("select rfp.functionPoint.id from RoleFunctionPoint rfp");
		//只查询类型为功能点
		finder.append(" where rfp.functionPoint.functionPointType = ").append(FunctionPointType.FUNCTIONPOINT);
		//子查询
		finder.append(" and rfp.role.id in (select ur.role.id from UserRole ur where ur.user.id = :userId)");
		finder.setParam("userId", userId);
		finder.setCacheable(true);
		return find(finder);
	}
	
	/**
	 *  根据用户id查询 ，用户没有授权的功能点。<br/>
	 * 查询范围，在damin角色中查询<br/>
	 * @see org.csr.common.user.dao.FunctionpointDao#findUnAuthorizeByUserId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findUnAuthorizeByUserId(Long userId,Long orgId){
		try {
			//查询admin role的全部功能点。
			Finder finder=FinderImpl.create("select fp from FunctionPoint fp where" );
			finder.append(" fp.id in (select rfp.functionPoint.id from RoleFunctionPoint rfp where rfp.role.orgId=:orgId and rfp.role.roleType = :roleType)");
			//只查询类型为功能点
			finder.append(" and fp.functionPointType = ").append(FunctionPointType.FUNCTIONPOINT);
			finder.append(" and fp.id not in ");
			finder.append("(select nrfp.functionPoint.id from RoleFunctionPoint nrfp where nrfp.role.id  in ");
			finder.append("(select nur.role.id from UserRole nur where nur.user.id = :userId))");
			
			finder.setParam("orgId",orgId);
			finder.setParam("roleType",RoleType.SYSTEM);
			finder.setParam("userId",userId);
			
			finder.insertEnd(" order by fp.code asc");
			finder.setCacheable(true);
			return find(finder);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据用户查询已经授权过的权限，返回功能点<br/>
	 * 关联，用户权限表来查询，目前提供用户权限使用 <br/>
	 * 查询关联表状态[isAddPrivilege]为：1.增加权限就是给用户赋予该功能的权限
	 * @see org.csr.common.user.dao.FunctionpointDao#findSpecialByUserId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findSpecialByUserId(Long userId,Byte isAddPrivilege) {
		Finder finder=FinderImpl.create(" select fp from FunctionPoint fp where fp.id in");
		finder.append("(select ufp.functionPoint.id from UserSpecialFunctionPoint ufp where");
		finder.append(" ufp.isAddPrivilege = ").append(isAddPrivilege);
		finder.append(" and ufp.functionPoint.functionPointType = ").append(FunctionPointType.FUNCTIONPOINT);
		finder.append(" and ufp.user.id = :userId)","userId", userId);
		finder.insertEnd(" order by fp.code asc");
		finder.setCacheable(true);
		return find(finder);
	}
	/**
	 * 根据用户查询已经授权过的权限，返回功能点<br/>
	 * 关联，用户权限表来查询，目前提供用户权限使用 <br/>
	 * 查询关联表状态[isAddPrivilege]为：1.增加权限就是给用户赋予该功能的权限
	 * @see org.csr.common.user.dao.FunctionpointDao#findSpecialByUserId(java.lang.Long)
	 */
	@Override
	public List<Long> findIdSpecialByUserId(Long userId,Byte isAddPrivilege) {
		Finder finder=FinderImpl.create("select ufp.functionPoint.id from UserSpecialFunctionPoint ufp where");
		finder.append(" ufp.isAddPrivilege = ").append(isAddPrivilege);
		finder.append(" and ufp.functionPoint.functionPointType = ").append(FunctionPointType.FUNCTIONPOINT);
		finder.append(" and ufp.user.id = :userId","userId", userId);
		finder.setCacheable(true);
		return find(finder);
	}
	
	@Override
	public List<Long> findIdAuthorizeByUserId(Long userId){
		Finder finder=FinderImpl.create("select fp.id from FunctionPoint fp where (fp.id in");
		//查询是否在存在角色权限中
		finder.append("(select rfp.functionPoint.id from RoleFunctionPoint rfp where ");
		finder.append(" rfp.role.id in (select ur.role.id from UserRole ur where ur.user.id = :userId))");
//		//查询是否在存特权增加中
		finder.append(" or fp.id in (select ufp.functionPoint.id from UserSpecialFunctionPoint ufp ");
		finder.append(" where ufp.isAddPrivilege = ").append(YesorNo.YES);
		finder.append(" and ufp.user.id = :userId))");
//		//并且，不再特权删除中
		finder.append(" and fp.id not in (select nufp.functionPoint.id from UserSpecialFunctionPoint nufp ");
		finder.append(" where nufp.isAddPrivilege = ").append(YesorNo.NO);
		finder.append(" and nufp.user.id = :userId)");
		finder.setParam("userId", userId);
		finder.setCacheable(true);
		
		return find(finder);
	}
	
	/**
	 * 查询角色下的功能点，只需要查询角色关联表RoleFunctionPoint与功能点表 FunctionPoint就行<br/>
	 * 返回功能点集合
	 * @see org.csr.common.user.dao.FunctionpointDao#findByRoleId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findByRoleId(Long roleId) {
		if(roleId==null){
			return new ArrayList<>(0);
		}
		Finder finder=FinderImpl.create("select rfp from RoleFunctionPoint rfp where ");
		finder.append(" rfp.role.id=:roleId");
		finder.setParam("roleId", roleId);
		finder.insertEnd(" order by rfp.functionPoint.code asc");
		finder.setCacheable(true);
		List<RoleFunctionPoint> roleFunctionPointList = find(finder);
		
		return PersistableUtil.toListBeans(roleFunctionPointList, new SetBean<RoleFunctionPoint>() {
			@Override
			public FunctionPoint setValue(RoleFunctionPoint doMain) {
				Byte authenticationMode = doMain.getAuthenticationMode();
				if(ObjUtil.isNotEmpty(authenticationMode)){
					doMain.getFunctionPoint().setAuthenticationMode(authenticationMode);
				}
				return doMain.getFunctionPoint();
			}
		});
	}
	
	/**
	 * 查询角色下的功能点，只需要查询角色关联表RoleFunctionPoint与功能点表 FunctionPoint就行<br/>
	 * 返回功能点
	 * @see org.csr.common.user.dao.FunctionpointDao#findIdsByRoleId(java.lang.Long)
	 */
	@Override
	public List<Long> findIdsByRoleId(Long roleId) {
		if(roleId==null){
			return new ArrayList<>(0);
		}
		Finder finder=FinderImpl.create("select distinct rfp.functionPoint.id from RoleFunctionPoint rfp where ");
		finder.append("rfp.role.id=:roleId","roleId", roleId);
		finder.setCacheable(true);
		return find(finder);
	}
	/**
	 * @description:查找子类的id
	 * @param: 
	 * @return: List 
	 */
	@Override
	public List<Long> findChildrenIds(Long id){
		Finder finder=FinderImpl.create("select id,(select count(ds.id) from FunctionPoint ds where ds.functionPoint.id=d.id) from FunctionPoint d");
		finder.append(" where d.functionPoint.id= :parentId","parentId", id);
		List<Object[]> list = find(finder);
		finder.setCacheable(true);
		return arrayToLong(list);
	}
	/* @description:根据父类id删除子类数据
	 * (non-Javadoc)
	 */
	public void deleteChildren(Long parentId){
		Finder finder=FinderImpl.create("delete from FunctionPoint fp where fp.functionPoint.id= :parentId");
		finder.setParam("parentId", parentId);
		batchHandle(finder);
	}
	@Override
	public int updateStatus(Long id, int Status) {
		List<Long> funids=findChildrenIds(id);
		funids.add(id);
		if(ObjUtil.isNotEmpty(funids)){
			Finder finder=FinderImpl.create("update FunctionPoint f set f.enableBan=:enableBan");
			finder.setParam("enableBan", Status);
			finder.append(" where f.id in (:funids)","funids",funids);
			return batchHandle(finder);
		}else{
			return 0;
		}
	}
	
	private List<Long> arrayToLong(List<Object[]> list){
		List<Long> organizationList = new ArrayList<Long>();
		for(int i=0;ObjUtil.isNotEmpty(list) && i<list.size();i++){
			Object[] obj = list.get(i);
			Long id = (Long) obj[0];
			if(obj[1] != null && (Long)obj[1] > 0){
				organizationList.addAll(findChildrenIds(id));
			}
			organizationList.add(id);
		}
		return organizationList;
	}
	@Override
	public boolean validateFunctionPoint(Long userId, Long fnId) {
		Finder finder=FinderImpl.create("select count(fp.id) from FunctionPoint fp where (fp.id in");
		//查询是否在存在角色权限中
		finder.append("(select rfp.functionPoint.id from RoleFunctionPoint rfp where ");
		finder.append(" rfp.functionPoint.id = :fnId");
		finder.append(" and rfp.role.id in (select ur.role.id from UserRole ur where ur.user.id = :userId))");
//		//查询是否在存特权增加中
		finder.append(" or fp.id in (select ufp.functionPoint.id from UserSpecialFunctionPoint ufp ");
		finder.append(" where ufp.isAddPrivilege = ").append(YesorNo.YES);
		finder.append(" and ufp.functionPoint.id = :fnId");
		finder.append(" and ufp.user.id = :userId))");
//		//并且，不再特权删除中
		finder.append(" and fp.id not in (select nufp.functionPoint.id from UserSpecialFunctionPoint nufp ");
		finder.append(" where nufp.isAddPrivilege = ").append(YesorNo.NO);
		finder.append(" and nufp.functionPoint.id = :fnId");
		finder.append(" and nufp.user.id = :userId)");
		
		finder.setParam("fnId", fnId);
		finder.setParam("userId", userId);
		finder.setCacheable(true);
		Long isHave=countOriginalHql(finder);
		if(ObjUtil.isNotEmpty(isHave) && isHave>0){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public List<FunctionPoint> findDefaultByOrganizationId(Long adminRoleId) {
		Finder finder=FinderImpl.create("select rfp.functionPoint from RoleFunctionPoint rfp");
		finder.append(" where rfp.authenticationMode = ").append(AuthenticationMode.DEFAULT);
		finder.append(" and rfp.role.id =:adminRoleId");
		finder.setParam("adminRoleId", adminRoleId);
		finder.setCacheable(true);
		return find(finder);
	}
}
