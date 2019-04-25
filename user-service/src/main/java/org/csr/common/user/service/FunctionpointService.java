package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.FunctionPoint;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;
import org.csr.core.security.resource.SecurityService;

/**
 * ClassName:FunctionpointService <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述： 功能点服务 <br/>
 * 公用方法描述：  <br/>
 */
public interface FunctionpointService extends SecurityService,BasisService<FunctionPoint, Long> {

	/**
	 * findByType: 描述方法的作用 <br/>
	 * @author caijin
	 * @param functionPointType
	 * @return
	 * @since JDK 1.7
	 */
	List<FunctionPoint> findByType(Integer functionPointType);
	/**
	 * findByCode: 根据功能点Code 查询功能点 <br/>
	 * @author caijin
	 * @param functionPointCode
	 * @return
	 * @since JDK 1.7
	 */
	public FunctionPoint findByCode(String functionPointCode);
	
	/**
	 * findAllModule: 查询第一级，定义第一级为模块 <br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllModule();
	
	/**
	 * findAllModule: 查询除了第一级的,除了第一级的，其他的全部分离。 <br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllFunction();
	
	/**
	 * 查询全部子节点
	 * @author caijin
	 * @param id
	 * @return 
	 * @since JDK 1.7
	 */
	public List<Long> findChildrenIds(Long id);
	/**
	 * findAllFunctionpoint: 根据功能类型查询，功能点，或者查询库 <br/>
	 * @author caijin
	 * @param page
	 * @param functionPointType 功能点库
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<FunctionPoint> findAllFunctionpoint(Page page,Byte functionPointType,String name);
	
	/**
	 * 查询全部能授权库 只查询库
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findClassAllCanBeAuthorized();

	/**
	 * findAllCanBeAuthorized: 查询全部能授权的功能点 ,只包含功能点，不查询功能点库 <br/>
	 * 只查询功能点为 3.需要授权 类型的功能点 <br/>
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllCanBeAuthorized();

	
	/**
	 * 查询机构的权限，保护默认权限。包含功能点库，级功能点数据。根据机构的adminRoleId字段查询。<br/>
	 * @author caijin
	 * @param orgId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllByOrgId(Long orgId);
	
	/**
	 * 查询机构的权限id，不包含默认权限。包含功能点库，级功能点数据。根据机构的adminRoleId字段查询。<br/>
	 * @author caijin
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findIdsByOrgId(Long orgId);
	
	/**
	 * findAllByRoleId: 查询角色下的全部功能点,包含不需要赋权的功能点<br/>
	 * @author caijin
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAllByRoleId(Long roleId);
	/**
	 * findByRoleId: 查询角色下的功能点，只需要查询关联表和功能点表，只要角色功能点的关联表存在。就查询出来<br>
	 * 不过滤功能点类型。
	 * @author caijin
	 * @param role
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findByRoleId(Long roleId);
	
	/**
	 * findIdsByRoleId: 查询角色下的功能点id集合，只需要查询关联表 <br/>
	 * 不过滤功能点类型。
	 * @author caijin
	 * @param roleId
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findIdsByRoleId(Long roleId);
	
	/**
	 * findRoleFunctionPointByUserId: 根据用户id查询，用户所有的角色中的全部功能点 <br/>
	 *  如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。没有跟权限关联<br>
	 *  不包含权限的增减
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findRoleFunctionPointByUserId(Long userId);
	
	/**
	 * findAuthorizeByUserId: 根据用户id查询，用户所存在的功能点<br/>
	 * 如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。<br/>
	 * 并且包含，已存在的权限和去掉的的权限。
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAuthorizeByUserId(Long userId);
	
	/**
	 * 用户能够授予的所有权限，包含用户角色，及域的用户权限 <br/>
	 * 这个方法是提供。
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findAuthorizeByUserId(Long userId,Long orgId);
	
	/**
	 * 查询所有的特权的权限，不包含已有的（角色）权限 <br/>
	 * 只是有增加的特权。不包含删除的特权。
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findUserSpecialByUserId(Long userId,Long orgId);
	/**
	 * findUnAuthorizeByUserId: 查询用户下没有的功能点 <br/>
	 * 根据相对应的权限--对应的orgId权限
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findUnAuthorizeByUserId(Long userId,Long orgId);
	
	/**
	 * findSpecialByUserId: 根据用户，及权限状态查询。当前状态的下的功能点。<br/>
	 * 关联，用户权限表来查询。会检查给予的权限状态，是否正确<br/>
	 * 如果，是增加权限的话，需要判断增加的权限是否在admin Role 里的功能点。如果是不存在的话。剔除<br>
	 * 返回值：1. 赋予的特殊权限集合。2.取消的特殊权限集合
	 * 如果 isAddPrivilege = null 将查询出全部
	 * @author caijin
	 * @param userId
	 * @param isAddPrivilege   true:增加权限就是给用户赋予该功能的权限； false :否则就是把该功能权限取消
	 * @return
	 * @since JDK 1.7
	 */
	public List<FunctionPoint> findSpecialByUserId(Long userId,Byte isAddPrivilege);
	
	/**
	 * findSpecialByUserId: 根据用户，及权限状态查询。当前状态的下的功能点。<br/>
	 * 关联，用户权限表来查询。会检查给予的权限状态，是否正确<br/>
	 * 如果，是增加权限的话，需要判断增加的权限是否在admin Role 里的功能点。如果是不存在的话。剔除<br>
	 * 返回值：1. 赋予的特殊权限集合。2.取消的特殊权限集合
	 * 如果 isAddPrivilege = null 将查询出全部
	 * @author caijin
	 * @param userId
	 * @param isAddPrivilege   true:增加权限就是给用户赋予该功能的权限； false :否则就是把该功能权限取消
	 * @return
	 * @since JDK 1.7
	 */
	public List<Long> findIdSpecialByUserId(Long userId,Byte isAddPrivilege);
	/**
	 * save: 添加功能点信息，需要验证功能点名称，功能点编码的唯一性 <br/>
	 * @author caijin
	 * @param fun
	 * @param parentId
	 * @since JDK 1.7
	 */
	public void save(FunctionPoint fun, Long parentId);

	/**
	 * update: 修改功能点，需要验证功能点名称，功能点编码的唯一性 <br/>
	 * @author caijin
	 * @param parentId 
	 * @param funciton
	 * @since JDK 1.7
	 */
	public void update(FunctionPoint function, Long parentId);
	

	/**
	 * activateModule: 启用模块 <br/>
	 * @author caijin
	 * @param id
	 * @since JDK 1.7
	 */
	public void enableModule(Long id);


	/**
	 * activateModule: 禁用模块 <br/>
	 * @author caijin
	 * @param id
	 * @since JDK 1.7
	 */
	public void disableModule(Long id);
	
	/**
	 * deleteByIds: 删除方法，需要在删除时，先删除子节点 <br/>
	 * @author caijin
	 * @param deleteIds
	 * @since JDK 1.7
	 */
	public void deleteByIds(String deleteIds);
	/**
	 * 判断功能点是否唯一
	 * 
	 * @param name
	 * @return
	 */
	FunctionPoint checkFunctionPointNameOnly(Long parentId,String name);

	/**
	 * 判断功能点代号是否唯一
	 * 
	 * @param code
	 * @return
	 */
	FunctionPoint checkFunctionPointCodeOnly(String code);
	/**
	 * wrapStructure: 包装功能点List成为tree结构。<br>
	 * 如果，list中有库的，isType需要为false，这样才不会有重复值。<br>
	 * 如果，list没有库，isType需要为true，这样，获取出的，才会有库。<br>
	 * @author caijin
	 * @param list
	 * @param level
	 * @return
	 * @since JDK 1.7
	 */
	List<FunctionPoint> wrapStructure(List<FunctionPoint> list,boolean isType);
	
	/**
	 * wrapCompleteStructure: 包装功能点List成为tree结构。与wrapStructure 的区别是，如果父节点不存在的话，也带上并标识出来 <br>
	 * 如果，list中有库的，isType需要为false，这样才不会有重复值。<br>
	 * 如果，list没有库，isType需要为true，这样，获取出的，才会有库。<br>
	 * @param list
	 * @param isType
	 * @return
	 */
	List<FunctionPoint> wrapCompleteStructure(List<FunctionPoint> list,boolean isType);
	/**
	 * findIdAllCanBeAuthorized:
	 * @author huayj
	 * @return
	 * @return List<Long>
	 * @date&time 2015-9-18 上午10:06:43
	 * @since JDK 1.7
	 */
	List<Long> findIdAllCanBeAuthorized();
	
	/**
	 * 根据用户id查询，用户所存在的功能点<br/>
	 * 如果，用户存在多个角色，相关的角色中所有的权功能点，全部查询出来。<br/>
	 * 并且包含已存在的权限，和去掉的的权限。
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	List<Long> findIdAuthorizeByUserId(Long userId);
	

	/**
	 * 根据用户查询用户下的角色中的全部功能点。
	 * @param userId
	 * @return
	 */
	List<Long> findIdRoleFunctionPointByUserId(Long userId);
	
	/**
	 * 验证用户是否，有指定的权限
	 * @param userId
	 * @param fnId
	 * @return
	 */
	boolean validateFunctionPoint(Long userId, Long fnId);
	

}
