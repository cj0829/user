package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.csr.common.user.constant.AuthenticationMode;
import org.csr.common.user.constant.EnableBan;
import org.csr.common.user.constant.FunctionPointType;
import org.csr.common.user.dao.FunctionpointDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.Role;
import org.csr.common.user.domain.UserRole;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.RoleFunctionPointService;
import org.csr.common.user.service.RoleService;
import org.csr.common.user.service.UserRoleService;
import org.csr.common.user.service.UserService;
import org.csr.core.Constants;
import org.csr.core.Param;
import org.csr.core.SecurityResource;
import org.csr.core.UserSession;
import org.csr.core.cache.CacheApi;
import org.csr.core.cache.CacheFactory;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.page.PagedInfoImpl;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndLikeParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.tree.TreeHandle;
import org.csr.core.tree.TreeNodeTool;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:FunctionpointServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-18上午10:34:17 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("functionpointService")
public class FunctionpointServiceImpl extends
		SimpleBasisService<FunctionPoint, Long> implements FunctionpointService {

	@Resource
	private RoleService roleService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RoleFunctionPointService roleFunctionPointService;
	@Resource
	OrganizationService organizationService;
	@Resource
	UserService userService;
	@Resource
	AgenciesService agenciesService;
	@Resource
	private FunctionpointDao functionpointDao;
	
	CacheApi cacheApi = CacheFactory.createApi(PropertiesUtil.getConfigureValue("cache.type"));
	@Override
	public BaseDao<FunctionPoint, Long> getDao() {
		return functionpointDao;
	}
	
	private static List<SecurityResourceBean> resourcesByDefaul;
	private static Map<Long,FunctionPoint> cacheAllFunctionpoint;
	@PostConstruct
	protected void findIniCache(){
		getCacheSecurityResourceBean();
		findResourcesByDefault();
	}
	

	@Override
	public List<FunctionPoint> findAllByOrgId(Long orgId) {
		Organization org = organizationService.findById(orgId);
		if(ObjUtil.isEmpty(org)){
			Exceptions.service("", "域不存在");
		}
		if(ObjUtil.isEmpty(org.getAdminRoleId())){
			return new ArrayList<FunctionPoint>(0);
		}
		return findAllByRoleId(org.getAdminRoleId());
	}
	
	@Override
	public List<Long> findIdsByOrgId(Long orgId) {
		Organization org = organizationService.findById(orgId);
		if(ObjUtil.isEmpty(org)){
			Exceptions.service("", "域不存在");
		}
		if(ObjUtil.isEmpty(org.getAdminRoleId())){
			return new ArrayList<Long>(0);
		}
		return functionpointDao.findIdsByRoleId(org.getAdminRoleId());
	}
	/**
	 * 询角色下的全部功能点,包含不需要赋权的功能点
	 * @see org.csr.common.user.service.FunctionpointService#findByRoleId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findAllByRoleId(Long roleId) {
		//查询角色权限
    	List<FunctionPoint> list = functionpointDao.findByRoleId(roleId);
    	//查询用户的默认功能点
    	AndEqParam and = new AndEqParam("authenticationMode", AuthenticationMode.DEFAULT);
		List<FunctionPoint> defaultFp = functionpointDao.findByParam(and);
		list.addAll(defaultFp);
		return list;
	}
	/**
	 * 查询角色下的功能点，只需要查询关联表和功能点表，只要角色功能点的关联表存在。就查询出来<br>
	 * 不过滤功能点类型。
	 * @see org.csr.common.user.service.FunctionpointService#findByRoleId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findByRoleId(Long roleId) {
		return functionpointDao.findByRoleId(roleId);
	}
	
	/**
	 * 查询角色下的功能点id集合，只需要查询关联表 <br/>
	 * 不过滤功能点类型。
	 * @see org.csr.common.user.service.FunctionpointService#findIdsByRoleId(java.lang.Long)
	 */
	public List<Long> findIdsByRoleId(Long roleId){
		return functionpointDao.findIdsByRoleId(roleId);
	}
	
	
	@Override
	public List<FunctionPoint> findAllModule() {
		return functionpointDao.findAllModule();
	}
	
	@Override
	public List<FunctionPoint> findAllFunction() {
		return functionpointDao.findAllFunction();
	};
	
	/**
	 * 根据用户id查询，用户所有的角色中的全部功能点
	 * @see org.csr.common.user.service.FunctionpointService#findRoleFunctionPointByUserId(java.lang.Long)
	 */
	public List<FunctionPoint> findRoleFunctionPointByUserId(Long userId){
		return functionpointDao.findRoleFunctionPointByUserId(userId);
	}
	
	/**
	 * 查询全部能授权的功能点,如果structure=true，只查询库
	 * @see org.csr.common.user.service.FunctionpointService#findAllCanBeAuthorized()
	 */
	@Override
	public List<FunctionPoint> findClassAllCanBeAuthorized() {
		return functionpointDao.findAllCanBeAuthorized(true);
	}
	/**
	 * 查询全部能授权的功能点 ,只包含功能点，不查询功能点库 <br/>
	 * 只查询功能点为 3.需要授权 类型的功能点 <br/>
	 * @see org.csr.common.user.service.FunctionpointService#findAllCanBeAuthorized()
	 */
	@Override
	public List<FunctionPoint> findAllCanBeAuthorized() {
		return functionpointDao.findAllCanBeAuthorized(false);
	}
	
	
	@Override
	public List<Long> findIdAllCanBeAuthorized() {
		return functionpointDao.findIdAllCanBeAuthorized(false);
	}
	/**
	 * 根据用户id查询，用户所存在的功能点<br/>
	 * 如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。<br/>
	 * 并且包含已存在的权限，和去掉的的权限。
	 * @see org.csr.common.user.service.FunctionpointService#findAuthorizeByUserId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findAuthorizeByUserId(Long userId) {
//		//查询 增加 权限
    	List<FunctionPoint> list = findSpecialByUserId(userId,YesorNo.YES);
    	//查询用户的功能点
		List<FunctionPoint> list2 = functionpointDao.findRoleFunctionPointByUserId(userId);
		list.removeAll(list2);
		list.addAll(list2);
		//查询  需要去除的 权限
		List<FunctionPoint> list3 = findSpecialByUserId(userId,YesorNo.NO);;
		list.removeAll(list3);
		return list;
	}
	
	
	/**
	 * 根据用户id查询，用户所存在的功能点<br/>
	 * 如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。<br/>
	 * 并且包含已存在的权限，和去掉的的权限。
	 * @see org.csr.common.user.service.FunctionpointService#findAuthorizeByUserId(java.lang.Long)
	 */
	@Override
	public List<Long> findIdAuthorizeByUserId(Long userId) {
		//查询 增加 权限
    	List<Long> list = 	functionpointDao.findIdSpecialByUserId(userId,YesorNo.YES);
    	//查询用户的功能点
		List<Long> list2 = functionpointDao.findIdRoleFunctionPointByUserId(userId);
		list.removeAll(list2);
		list.addAll(list2);
		//查询  需要去除的 权限
		List<Long> list3 = functionpointDao.findIdSpecialByUserId(userId,YesorNo.NO);
		
		
		list.removeAll(list3);
		return list;
//		return functionpointDao.findIdAuthorizeByUserId(userId);
	}
	@Override
	public List<FunctionPoint> findAuthorizeByUserId(Long userId, Long orgId) {
		List<UserRole> userRoleList=userRoleService.findUserRoleList(userId);
    	//查询当前域下的全部权限
		List<FunctionPoint> orgFnList=this.findAllByOrgId(orgId);
		if(ObjUtil.isEmpty(userRoleList)){
			for (FunctionPoint orgfn : orgFnList) {
				orgfn.setSource("特权");
			}
			Map<Long,FunctionPoint> fn=PersistableUtil.toMap(orgFnList);
			List<Long> existFnIdlList = this.findIdAuthorizeByUserId(userId);
			if(ObjUtil.isNotEmpty(existFnIdlList)){
				for (Long fnId : existFnIdlList) {
					FunctionPoint existFn=fn.get(fnId);
					if(ObjUtil.isNotEmpty(existFn)){
						existFn.setExist(true);
					}
				}
			}
			return orgFnList;
		}else{
			Map<Long,FunctionPoint> newFnMap = new LinkedHashMap<Long,FunctionPoint>();
			for (UserRole userRole : userRoleList) {
				List<FunctionPoint> roleFnList=functionpointDao.findByRoleId(userRole.getRole().getId());
				for (FunctionPoint rolefn : roleFnList) {
					FunctionPoint newFn=newFnMap.get(rolefn.getId());
					if(ObjUtil.isEmpty(newFn)){
						newFn=rolefn;
						newFnMap.put(newFn.getId(), newFn);
					}
					if(ObjUtil.isBlank(newFn.getSource())){
						newFn.setSource(userRole.getRole().getName());
					}else{
						newFn.setSource(newFn.getSource()+" "+userRole.getRole().getName());
					}
				}
			}
			for (FunctionPoint orgfn : orgFnList) {
				FunctionPoint newFn=newFnMap.get(orgfn.getId());
				if(ObjUtil.isEmpty(newFn)){
					newFn=orgfn;
					newFnMap.put(newFn.getId(), newFn);
				}
				if(ObjUtil.isBlank(newFn.getSource())){
					newFn.setSource("特权");
				}else{
					newFn.setSource(newFn.getSource()+" 特权");
				}
			}
			List<Long> existFnIdlList = this.findIdAuthorizeByUserId(userId);
			if(ObjUtil.isNotEmpty(existFnIdlList)){
				for (Long fnId : existFnIdlList) {
					FunctionPoint existFn=newFnMap.get(fnId);
					if(ObjUtil.isNotEmpty(existFn)){
						existFn.setExist(true);
					}
				}
			}
			return new ArrayList<FunctionPoint>(newFnMap.values());
		}
	}
	public List<FunctionPoint> findUserSpecialByUserId(Long userId,Long orgId){
		List<FunctionPoint> orgFnList=this.findAllByOrgId(orgId);
		if(ObjUtil.isNotEmpty(orgFnList)){
			Map<Long,FunctionPoint> fn=PersistableUtil.toMap(orgFnList);
			List<Long> specialFnIds=functionpointDao.findIdSpecialByUserId(userId,YesorNo.YES);
			if(ObjUtil.isNotEmpty(specialFnIds)){
				for (Long fnId : specialFnIds) {
					FunctionPoint existFn=fn.get(fnId);
					if(ObjUtil.isNotEmpty(existFn)){
						existFn.setExist(true);
					}
				}
			}
			return new ArrayList<FunctionPoint>(fn.values());
		}
		return new ArrayList<FunctionPoint>(0);
	}	
	/**
	 * 查询用户下没有的功能点（可选）. 替代 ：findUnJoinFunctionPointByUserId
	 * @see org.csr.common.user.service.FunctionpointService#findUnAuthorizeByUserId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findUnAuthorizeByUserId(Long userId,Long orgId) {
		//查询 取消的权限
    	List<FunctionPoint> list = findSpecialByUserId(userId,YesorNo.NO);
    	//查询没有赋值的权限
		List<FunctionPoint> list2=functionpointDao.findUnAuthorizeByUserId(userId,orgId);
		list.addAll(list2);
		//查询 增加的权限
		List<FunctionPoint> list3 = findSpecialByUserId(userId,YesorNo.YES);
		list.removeAll(list3);
		return list;
	}
	
	/**
	 * 根据用户，及权限状态查询。当前状态的下的功能点。<br/>
	 * 关联，用户权限表来查询。会检查给予的权限状态，是否正确<br/>
	 * 返回值：1. 赋予的特殊权限集合。2.取消的特殊权限集合
	 * @see org.csr.common.user.service.FunctionpointService#findSpecialByUserId(java.lang.Long)
	 */
	@Override
	public List<FunctionPoint> findSpecialByUserId(Long userId,Byte isAddPrivilege) {
		if(ObjUtil.isEmpty(isAddPrivilege)){
			Exceptions.service("", "您的特权集合类型不能为空");
		}
		return functionpointDao.findSpecialByUserId(userId,isAddPrivilege);
	}
	/**
	 * 根据用户，及权限状态查询。当前状态的下的功能点。<br/>
	 * 关联，用户权限表来查询。会检查给予的权限状态，是否正确<br/>
	 * 返回值：1. 赋予的特殊权限集合。2.取消的特殊权限集合
	 * @see org.csr.common.user.service.FunctionpointService#findSpecialByUserId(java.lang.Long)
	 */
	@Override
	public List<Long> findIdSpecialByUserId(Long userId,Byte isAddPrivilege) {
		if(ObjUtil.isEmpty(isAddPrivilege)){
			Exceptions.service("", "您的特权集合类型不能为空");
		}
		return functionpointDao.findIdSpecialByUserId(userId,isAddPrivilege);
	}
	
	
	
	private Map<Long,FunctionPoint> getCacheSecurityResourceBean(){
		if(ObjUtil.isEmpty(cacheAllFunctionpoint)){
			cacheAllFunctionpoint = PersistableUtil.toMap(functionpointDao.findAll());
			return cacheAllFunctionpoint;
		}
		return cacheAllFunctionpoint;
	}
	
	private List<FunctionPoint> getdAuthorizeByUserId(Long userId){
		ArrayList<FunctionPoint> fpList = new ArrayList<FunctionPoint>();
		List<Long> fpIdList = findIdAuthorizeByUserId(userId);
		if(ObjUtil.isNotEmpty(fpIdList)){
			for (Long id : fpIdList) {
				FunctionPoint fp=getCacheSecurityResourceBean().get(id);
				if(ObjUtil.isNotEmpty(fp)){
					fpList.add(getCacheSecurityResourceBean().get(id));
				}
			}
		}
		return fpList;
	}
	/**
	 * 根据id角色Id取功能点详细
	 * @see org.csr.core.security.resource.SecurityService#findResourcesByUser(org.csr.core.security.userdetails.UserSession)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<SecurityResourceBean> findResourcesByUser(UserSession user) {
		//提供，查询用户数据，并缓存到缓存中，重新登录的时候会重新查询
		PagedInfo<SecurityResourceBean> resourcesByUser=(PagedInfo<SecurityResourceBean>) cacheApi.findSerializableCache(user.getUserId()+"_ResourcesByUser");
		if(ObjUtil.isEmpty(resourcesByUser)){
			List<FunctionPoint> listFunctionPoint=null;
			if (Constants.USER_SUPER.equals(user.getLoginName())) {
				//需要把菜单数据也查询出来
				listFunctionPoint=functionpointDao.findAllCanBeAuthorized(false);
			} else {
				listFunctionPoint=getdAuthorizeByUserId(user.getUserId());
				
				List<FunctionPoint> roleListFunctionPoint=null;
				Agencies agencies = agenciesService.findById(user.getAgenciesId());
				if(ObjUtil.isNotEmpty(agencies) && ObjUtil.isNotEmpty(agencies.getOrg())){
					roleListFunctionPoint = functionpointDao.findDefaultByOrganizationId(agencies.getOrg().getAdminRoleId());
				}
				if(ObjUtil.isNotEmpty(listFunctionPoint)){
					if(ObjUtil.isNotEmpty(listFunctionPoint)){
						listFunctionPoint.addAll(roleListFunctionPoint);
					}
				}else{
					listFunctionPoint=roleListFunctionPoint;
				}
			}
			List<SecurityResourceBean> listFunctionPointBean= PersistableUtil.toListBeans(listFunctionPoint, new SetBean<FunctionPoint>() {
				@Override
				public SecurityResourceBean setValue(FunctionPoint doMain) {
					SecurityResourceBean bean=SecurityResourceBean.toNode(doMain);
					FunctionPoint f=doMain.getFunctionPoint();
					if(ObjUtil.isNotEmpty(f)){
						bean.setpName(f.getName());
					}
					return bean;
				}
			});
			resourcesByUser=new PagedInfoImpl<>(listFunctionPointBean);
			cacheApi.createSerializableCache(user.getUserId()+"_ResourcesByUser", resourcesByUser);
		}
		return resourcesByUser.getRows();
	}
	/**
	 * 查询默认权限
	 * @see org.csr.core.security.resource.SecurityService#findResourcesByDefault(org.csr.core.security.userdetails.UserSession)
	 */
	@Override
	public List<SecurityResourceBean> findResourcesByDefault() {
		if(ObjUtil.isEmpty(resourcesByDefaul)){
			AndEqParam and = new AndEqParam("authenticationMode", AuthenticationMode.DEFAULT);
			resourcesByDefaul = PersistableUtil.toListBeans(functionpointDao.findByParam(and), new SetBean<FunctionPoint>() {
				@Override
				public SecurityResourceBean setValue(FunctionPoint doMain) {
					SecurityResourceBean bean=SecurityResourceBean.toNode(doMain);
					FunctionPoint f=doMain.getFunctionPoint();
					if(ObjUtil.isNotEmpty(f)){
						bean.setpName(f.getName());
					}
					return bean;
				}
			});
		}
		return resourcesByDefaul;
		
	}
	@Override
	public List<Long> findIdRoleFunctionPointByUserId(Long userId) {
		if(ObjUtil.isEmpty(userId)){
			return new ArrayList<Long>(0);
		}
		return functionpointDao.findIdRoleFunctionPointByUserId(userId);
	}
	/**
	 * findByType: 查询，库<br/>
	 * @author caijin
	 * @param code
	 * @return
	 * @since JDK 1.7
	 */
	@Override
	public List<FunctionPoint> findByType(Integer functionPointType) {
		AndEqParam and = new AndEqParam("functionPointType",functionPointType) ;
		return functionpointDao.findByParam(and);
	}
	/**
	 * findByCode: 根据功能点Code 查询功能点 <br/>
	 * @author caijin
	 * @param functionPointCode
	 * @return
	 * @since JDK 1.7
	 */
	@Override
	public FunctionPoint findByCode(String functionPointCode) {
		AndEqParam and = new AndEqParam("code", functionPointCode);
		return functionpointDao.existParam(and);
	}

	/*
	 * @description:添加/更新功能点信息 先判断是更新还是添加 然后判断功能名称是否已经存在和功能代码是否重复，若存在则返回提示信息
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bluedot.csp.admin.system.service.FunctionpointService#save(com.bluedot
	 * .csp.common.domain.admin.system.FunctionPoint)
	 */
	public void save(FunctionPoint funciton, Long parentId) {
		
		//类型不能为空
		if (ObjUtil.isEmpty(funciton.getFunctionPointType())){
			Exceptions.service("functionPointCodeIsExist", PropertiesUtil.getExceptionMsg("functionPointCodeIsExist"));
		}
		//类型不再范围范围中
		if(!checkType(funciton.getFunctionPointType())){
			Exceptions.service("functionPointType",PropertiesUtil.getExceptionMsg("functionPointType"));
		}
		Role role=roleService.findById(Role.global);
		//系统全局角色不能为空,
		if (ObjUtil.isEmpty(role)){
			Exceptions.service("", "系统全局角色不存在");
		}
		//检查用户是否重复
		FunctionPoint nameCount = checkFunctionPointNameOnly(parentId,funciton.getName());
		if (ObjUtil.isNotEmpty(nameCount)){
			Exceptions.service("functionPointNameIsExist", PropertiesUtil.getExceptionMsg("functionPointNameIsExist"));
		}
		//当保存功能点时，需要验证code是否重复
		if(FunctionPointType.FUNCTIONPOINT.equals(funciton.getFunctionPointType())){
			FunctionPoint codeCount = checkFunctionPointCodeOnly(funciton.getCode());
			if (ObjUtil.isNotEmpty(codeCount)){
				Exceptions.service("functionPointCodeIsExist", "");
			}
		}
		if(ObjUtil.isNotEmpty(parentId)){
			FunctionPoint parent=findById(parentId);
			funciton.setFunctionPoint(parent);
		}
		functionpointDao.save(funciton);
		//给系统角色，补充功能点
		if (FunctionPointType.FUNCTIONPOINT.equals(funciton.getFunctionPointType()) 
				|| AuthenticationMode.MUST.equals(funciton.getAuthenticationMode())){
			roleFunctionPointService.save(role.getId(), funciton.getId());
		}
	}

	/**
	 * 修改功能点，需要验证功能点名称，功能点编码的唯一性 <br/>
	 * @see org.csr.common.user.service.FunctionpointService#update(org.csr.common.user.domain.FunctionPoint)
	 */
	public void update(FunctionPoint funciton, Long parentId) {

		FunctionPoint nameCount = checkFunctionPointNameOnly(parentId,funciton.getName());
		//验证功能点名称
		if (nameCount != null) {
			if (!nameCount.getId().equals(funciton.getId()))
				Exceptions.service("FunctionPointNameHasExist", "");
		}
		//当保存功能点时，需要验证code是否重复
		if(FunctionPointType.FUNCTIONPOINT.equals(funciton.getFunctionPointType())){
			FunctionPoint codeCount = checkFunctionPointCodeOnly(funciton.getCode());
			if (codeCount != null) {
				if (!codeCount.getId().equals(funciton.getId()))
					Exceptions.service("FunctionPointCodeHasExist", "");
			}
		}
		
		FunctionPoint oldFunction = functionpointDao.findById(funciton.getId());
		oldFunction.setName(funciton.getName());
		oldFunction.setCode(funciton.getCode());
		oldFunction.setIcon(funciton.getIcon());
		oldFunction.setDufIcon(funciton.getDufIcon());
		oldFunction.setForwardUrl(funciton.getForwardUrl());
		oldFunction.setUrlRule(funciton.getUrlRule());
		oldFunction.setRemark(funciton.getRemark());
		oldFunction.setHelpStatus(funciton.getHelpStatus());
		oldFunction.setIsAnonymous(funciton.getIsAnonymous());
		//展示可以不需要浏览
//		oldFunction.setBrowseLogLevel(funciton.getBrowseLogLevel());
		oldFunction.setOperationLogLevel(funciton.getOperationLogLevel());
		oldFunction.setAuthenticationMode(funciton.getAuthenticationMode());
		functionpointDao.update(oldFunction);
	}

	/**
	 * 删除方法，需要在删除时，先删除子节点
	 * 
	 * @see org.csr.core.service.SimpleBasisService#deleteByIds(java.lang.String)
	 */
	public void deleteByIds(String deleteIds) {
		deleteChildren(deleteIds);
		String[] ids = deleteIds.split(Constants.OPERATE_COLON);
		for (String id : ids) {
			if (id != null && !"".equals(id)) {
				functionpointDao.deleteById(Long.valueOf(id));
				roleFunctionPointService.deleteByFunctionPointIds(new Long[]{Long.valueOf(id)});
			}
		}
	}

	/**
	 * @description:判断功能点名称是否唯一
	 * @param:
	 * @return: Boolean
	 */
	@Override
	public FunctionPoint checkFunctionPointNameOnly(Long parentId,String name) {
		Param and = new AndEqParam("name", name);
		Param parent=new AndEqParam("functionPoint.id",parentId);
		return functionpointDao.existParam(and,parent);
	}

	/**
	 * @description:判断功能点代号是否唯一
	 * @param:
	 * @return: Boolean
	 */
	@Override
	public FunctionPoint checkFunctionPointCodeOnly(String code) {
		AndEqParam and = new AndEqParam("code", code);
		return functionpointDao.existParam(and);
	}

	/**
	 * 
	 * @see org.csr.common.user.service.FunctionpointService#wrapTree(java.util.List, int)
	 */
	@Override
	public List<FunctionPoint> wrapStructure(List<FunctionPoint> list,boolean isType){
		if(ObjUtil.isEmpty(list)){
			return new ArrayList<FunctionPoint>();
		}
		//是否需要自动把库取出来
		if(isType){
			Map<Long,FunctionPoint> map=new TreeMap<Long,FunctionPoint>();
			for(FunctionPoint fp:list){
				FunctionPoint parent=getFunctionpointType(fp);
				if(ObjUtil.isNotEmpty(parent)){
					map.put(parent.getId(), parent);
					FunctionPoint top=parent.getFunctionPoint();
					if(ObjUtil.isNotEmpty(top) && ObjUtil.isEmpty(map.get(top.getId()))){
						map.put(top.getId(), top);
					}
				}
			}
			list.addAll(map.values());
		}
		return wrapTree(list);
	}

	
	@Override
	public List<FunctionPoint> wrapCompleteStructure(List<FunctionPoint> list,boolean isType){
		if(ObjUtil.isEmpty(list)){
			return new ArrayList<FunctionPoint>();
		}
		
		Map<Long,FunctionPoint> oldFn=PersistableUtil.toMap(list);
		
		//是否需要自动把库取出来
		if(isType){
			Map<Long,FunctionPoint> map=new TreeMap<Long,FunctionPoint>();
			for(FunctionPoint fp:list){
				FunctionPoint parent=getFunctionpointType(fp);
				if(ObjUtil.isNotEmpty(parent)){
					map.put(parent.getId(), parent);
					FunctionPoint top=parent.getFunctionPoint();
					if(ObjUtil.isNotEmpty(top) && ObjUtil.isEmpty(map.get(top.getId()))){
						map.put(top.getId(), top);
					}
				}
			}
			list.addAll(map.values());
			Map<Long,FunctionPoint> newMap=new TreeMap<Long,FunctionPoint>();
			for(FunctionPoint fp:list){
				FunctionPoint parent=fp.getFunctionPoint();
				if(ObjUtil.isNotEmpty(parent) && FunctionPointType.FUNCTIONPOINT.equals(parent.getFunctionPointType())){
					FunctionPoint fun=oldFn.get(parent.getId());
					if(ObjUtil.isEmpty(fun)){
						FunctionPoint funNew=newMap.get(parent.getId());
						if(ObjUtil.isEmpty(funNew)){
							parent.setHave(YesorNo.YES);
							newMap.put(parent.getId(), parent);
						}
					}
				}
			}
			list.addAll(newMap.values());
		}
		return wrapTree(list);
	}
	
	/**
	 * getFunctionpointType: 获取功能点的根节点，功能点必须挂在功能点库下，不然功能点无法赋值。 <br/>
	 * @author caijin
	 * @param fp
	 * @return
	 * @since JDK 1.7
	 */
	private FunctionPoint getFunctionpointType(FunctionPoint fp) {
		if(ObjUtil.isNotEmpty(fp)){
			FunctionPoint parent=fp.getFunctionPoint();
			if(ObjUtil.isNotEmpty(parent)){
				if(FunctionPointType.FUNCTIONPOINT.equals(parent.getFunctionPointType())){
					return getFunctionpointType(parent);
				}else if(FunctionPointType.TYPE.equals(parent.getFunctionPointType())){
					return parent;
				}
			}
		}
		return null;
	}
	
	private List<FunctionPoint> wrapTree(List<FunctionPoint> list){
		final Map<Long,ArrayList<FunctionPoint>> parentList=new HashMap<Long,ArrayList<FunctionPoint>>();
		List<FunctionPoint> listTree = TreeNodeTool.toResult(list,new TreeHandle<FunctionPoint,FunctionPoint,Long>(){
			public Long rootId() {
				return null;
			}
			public boolean setBean(FunctionPoint value, FunctionPoint node,FunctionPoint parent) {
				return true;
			}
			public FunctionPoint newTreeNode(FunctionPoint value)throws IllegalArgumentException {
				return value;
			}
			public Long getId(FunctionPoint node) {
				return node.getId();
			}
			public Long getParentId(FunctionPoint node) {
				if(FunctionPointType.TYPE.equals(node.getFunctionPointType()) && ObjUtil.isEmpty(node.getFunctionPoint())){
					return null;
				}
				return node.getFunctionPoint().getId();
			}
			public List<FunctionPoint> children(FunctionPoint parent) {
				List<FunctionPoint> list =parentList.get(parent.getId());
				if(list==null){
					parentList.put(parent.getId(), new ArrayList<FunctionPoint>());
					parent.setChildren(parentList.get(parent.getId()));
				}
				return parentList.get(parent.getId());
			}
		});
		return listTree;
	}
	@Override
	public List<? extends SecurityResource> findResourcesByAnonymous(UserSession user) {
		@SuppressWarnings("unchecked")
		PagedInfo<FunctionPoint> resourcesByAnonymous=(PagedInfo<FunctionPoint>) cacheApi.findSerializableCache("resourcesByAnonymous");
		if(ObjUtil.isEmpty(resourcesByAnonymous)){
			AndEqParam and = new AndEqParam("isAnonymous",YesorNo.NO);
			List<FunctionPoint> fpList=functionpointDao.findByParam(and);
			resourcesByAnonymous=new PagedInfoImpl<>(fpList);
			return fpList;
		}
		return resourcesByAnonymous.getRows();
	}
	@Override
	public PagedInfo<FunctionPoint> findAllFunctionpoint(Page page,
			Byte functionPointType, String name) {
		if(ObjUtil.isEmpty(functionPointType)){
			functionPointType=FunctionPointType.FUNCTIONPOINT;
		}
		page.toParam().add(new AndEqParam("functionPointType", functionPointType));
		if(ObjUtil.isNotBlank(name)){
			page.toParam().add(new AndLikeParam("name", name)) ;
		}
	
		return functionpointDao.findAllPage(page);
	}
	
	@Override
	public void enableModule(Long id) {
		functionpointDao.updateStatus(id, EnableBan.Enable);
	}

	@Override
	public void disableModule(Long id) {
		functionpointDao.updateStatus(id, EnableBan.Disable);
	}


	
	
	
	/**
	 * @description:递归删除一个或多个对象的子类
	 * @param:
	 * @return: void
	 */
	private void deleteChildren(String deleteIds) {
		if (!checkDelete(deleteIds)){
			Exceptions.service("FunctionPointNotDeleted", "");
		}
			
		String[] ids = deleteIds.split(Constants.OPERATE_COLON);
		for (String id : ids) {
			if (id != null && !"".equals(id)) {
				List<Long> funIds = functionpointDao.findChildrenIds(ObjUtil.tolong(id));
				functionpointDao.deleteByIds(funIds.toArray(new Long[funIds.size()]));
				roleFunctionPointService.deleteByFunctionPointIds(funIds.toArray(new Long[funIds.size()]));
			}
		}
	}

	private boolean checkType(Byte functionPointType){
		if(FunctionPointType.TYPE.equals(functionPointType)){
			return true;
		}
		if(FunctionPointType.FUNCTIONPOINT.equals(functionPointType)){
			return true;
		}	
		return false;
	}
	
	/**
	 * 检查是否有关联其他业务表。如果有关联，则不能删除
	 * 
	 * @param deleteIds
	 * @return
	 */
	private Boolean checkDelete(String deleteIds) {
		StringBuffer sb = new StringBuffer("");
		String[] ids = deleteIds.split(Constants.OPERATE_COLON);
		boolean bool = true;
		for (String id : ids) {
			if (id != null && !"".equals(id)) {
				if (bool) {
					bool = false;
					sb.append(id);
				} else {
					sb.append(Constants.SPLITCHAR2).append(id);
				}
			}
		}
		return true;
	}

	@Override
	public List<Long> findChildrenIds(Long id) {
		return functionpointDao.findChildrenIds(id);
	}

	@Override
	public boolean validateFunctionPoint(Long userId, Long fnId) {
		if(ObjUtil.isEmpty(userId) || ObjUtil.isEmpty(fnId)){
			return false;
		}
		return functionpointDao.validateFunctionPoint(userId,fnId);
	}


}
