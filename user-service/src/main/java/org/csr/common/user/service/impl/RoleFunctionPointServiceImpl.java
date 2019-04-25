package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.FunctionPointType;
import org.csr.common.user.dao.RoleFunctionPointDao;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.RoleFunctionPointService;
import org.csr.common.user.service.RoleService;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.MatchResult;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:RoleFunctionPointServiceImpl <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("roleFunctionPointService")
public class RoleFunctionPointServiceImpl extends
		SimpleBasisService<RoleFunctionPoint, Long> implements
		RoleFunctionPointService {
	@Resource
	private RoleService roleService;
	@Resource
	private RoleFunctionPointDao roleFunctionPointDao;
	@Resource
	private FunctionpointService functionpointService;
	
	@Override
	public BaseDao<RoleFunctionPoint, Long> getDao() {
		return roleFunctionPointDao;
	}

	/**
	 * 删除角色功能点
	 * 
	 * @see org.csr.common.user.service.RoleFunctionPointService#deleteByRoleId(java.lang.Long)
	 */
	@Override
	public void deleteByRoleId(Long roleId) {
		roleFunctionPointDao.deleteByRoleId(roleId);
	}

	/**
	 * 简单描述该方法的实现功能（可选）.
	 * 
	 * @see org.csr.common.user.service.RoleFunctionPointService#deleteByRoleId(java.lang.Long,
	 *      java.lang.Long[])
	 */
	public void deleteByRoleId(Long roleId, Long[] functionPointIds) {
		roleFunctionPointDao.deleteByRoleId(roleId, functionPointIds);
	}

	@Override
	public void save(Long roleId, Long functionPointId) {
		if (ObjUtil.isEmpty(roleId)) {
			Exceptions.service("updateFunctionGroup", "DictTypeHasExist");
		}
		Role role = roleService.findById(roleId);
		if (ObjUtil.isEmpty(role)) {
			Exceptions.service("updateFunctionGroup", "DictTypeHasExist");
		}
		if(ObjUtil.isEmpty(functionPointId)){
			roleFunctionPointDao.deleteByRoleId(roleId);
			return;
		}
		FunctionPoint functionpoint=functionpointService.findById(functionPointId);
		if(ObjUtil.isEmpty(functionpoint)){
			roleFunctionPointDao.deleteByRoleId(roleId);
			return;
		}
		RoleFunctionPoint roleFunctionPoint = roleFunctionPointDao.findByRoleFunctionPointId(roleId, functionPointId);
		if(ObjUtil.isEmpty(roleFunctionPoint)){
			RoleFunctionPoint newRoleFunctionPoint = new RoleFunctionPoint();
			newRoleFunctionPoint.setFunctionPoint(functionpoint);
			newRoleFunctionPoint.setRole(role);
			roleFunctionPointDao.save(newRoleFunctionPoint);
		}
	}
	
	/**
	 * 给指定角色，修改功能点集合。 <br/>
	 * 具体实现方式，采用和已存在的能点比较。多删，少添加。
	 * 
	 * @see org.csr.common.user.service.RoleFunctionPointService#save(java.lang.Long,
	 *      java.lang.Long[])
	 */
	public void save(Long roleId, Long[] functionPointIds) {
		if (ObjUtil.isEmpty(roleId)) {
			Exceptions.service("updateFunctionGroup", "DictTypeHasExist");
		}
		Role role = roleService.findById(roleId);
		if (ObjUtil.isEmpty(role)) {
			Exceptions.service("updateFunctionGroup", "DictTypeHasExist");
		}
		if(ObjUtil.isEmpty(functionPointIds)){
			roleFunctionPointDao.deleteByRoleId(roleId);
			return;
		}
		//去除重复值
		functionPointIds=ObjUtil.removeDuplicate(functionPointIds);
		// 查询角色功能点ids
		List<RoleFunctionPoint> rfpList = roleFunctionPointDao.findByRoleId(roleId,FunctionPointType.FUNCTIONPOINT);
		// 比较对象
		MatchResult match = MatchResult.matchFunctionPoint(toIdLong(rfpList),functionPointIds);
		// 删除角色中的功能点
		if (ObjUtil.isNotEmpty(match.getDeleteIds())) {
			roleFunctionPointDao.deleteByRoleId(roleId, match.getDeleteIds().toArray(new Long[match.getDeleteIds().size()]));
		}
		// 给角色添加功能点
		if (ObjUtil.isNotEmpty(match.getAddIds())) {
			for (Long id : match.getAddIds()) {
				RoleFunctionPoint roleFunctionPoint = new RoleFunctionPoint();
				roleFunctionPoint.setRole(new Role(roleId));
				roleFunctionPoint.setFunctionPoint(new FunctionPoint(id));
				roleFunctionPointDao.save(roleFunctionPoint);
			}
		}
	}

	/**
	 * 给指定角色，修改功能点集合。 <br/>
	 * 具体实现方式，采用和已存在的能点比较。多删，少添加。
	 * 
	 * @see org.csr.common.user.service.RoleFunctionPointService#save(java.lang.Long,
	 *      java.lang.Long[])
	 */
	public void saveByFunctionPointType(Long roleId, Long[] functionPointIds) {
		if (ObjUtil.isEmpty(roleId)) {
			Exceptions.service("updateFunctionGroup", "DictTypeHasExist");
		}
		Role role = roleService.findById(roleId);
		if (ObjUtil.isEmpty(role)) {
			Exceptions.service("updateFunctionGroup", "DictTypeHasExist");
		}
		if(ObjUtil.isEmpty(functionPointIds)){
			roleFunctionPointDao.deleteByRoleId(roleId);
			return;
		}
		//获取全部子
		//去除重复值
		functionPointIds=ObjUtil.removeDuplicate(functionPointIds);
		// 查询角色功能点ids
		List<RoleFunctionPoint> rfpList = roleFunctionPointDao.findByRoleId(roleId,FunctionPointType.TYPE);
		// 比较对象
		MatchResult match = MatchResult.matchFunctionPoint(toIdLong(rfpList),functionPointIds);
		// 删除角色中的功能点
		if (ObjUtil.isNotEmpty(match.getDeleteIds())) {
			List<Long> delIds=new ArrayList<Long>();
			for (int i = 0; i < match.getDeleteIds().size(); i++) {
				delIds.addAll(functionpointService.findChildrenIds( match.getDeleteIds().get(i)));
			}
			delIds.addAll(match.getDeleteIds());
			roleFunctionPointDao.deleteByRoleId(roleId,ObjUtil.removeDuplicate(delIds));
		}
		// 给角色添加功能点
		if (ObjUtil.isNotEmpty(match.getAddIds())) {
			
			List<Long> addIds=new ArrayList<Long>();
			for (int i = 0; i < match.getAddIds().size(); i++) {
				addIds.addAll(functionpointService.findChildrenIds( match.getAddIds().get(i)));
			}
			addIds.addAll(match.getAddIds());
			for (Long id : ObjUtil.removeDuplicate(addIds)) {
				RoleFunctionPoint roleFunctionPoint = new RoleFunctionPoint();
				roleFunctionPoint.setRole(new Role(roleId));
				roleFunctionPoint.setFunctionPoint(new FunctionPoint(id));
				roleFunctionPointDao.save(roleFunctionPoint);
			}
		}
	}
	
	/**
	 * toIdLong: 描述方法的作用 <br/>
	 * 
	 * @author caijin
	 * @param rfpList
	 * @return
	 * @since JDK 1.7
	 */
	protected List<Long> toIdLong(List<RoleFunctionPoint> rfpList) {
		if (ObjUtil.isNotEmpty(rfpList)) {
			List<Long> ids = new ArrayList<Long>();
			for (RoleFunctionPoint id : rfpList) {
				if(ObjUtil.isNotEmpty(id.getFunctionPoint().getId())){
					ids.add(id.getFunctionPoint().getId());
				}
			}
			return ids;
		}
		return null;
	}

	@Override
	public int deleteByFunctionPointIds(Long[] functionPointIds) {
		if(ObjUtil.isEmpty(functionPointIds)){
			return 0;
		}
		return roleFunctionPointDao.deleteByFunctionPointIds(functionPointIds);
	}

	@Override
	public RoleFunctionPoint findByOrganizationIdFunId(Long adminRoleId,Long funId) {
		return roleFunctionPointDao.existParam(new AndEqParam("role.id", adminRoleId),new AndEqParam("functionPoint.id", funId));
	}

	@Override
	public int updateMustAll(Long adminRoleId) {
		return roleFunctionPointDao.updateMustAll(adminRoleId);
	}
}
