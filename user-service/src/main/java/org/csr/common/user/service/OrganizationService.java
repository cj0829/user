package org.csr.common.user.service;

import java.util.List;

import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.service.BasisService;
 

/**
 * ClassName:BasisServiceImpl <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 * 
 */
public interface OrganizationService extends BasisService<Organization, Long> {
	
	
	/**
	 * findByUserId: 通过机构管理员查询机构 <br/>
	 * @author caijin
	 * @param id
	 * @since JDK 1.7
	 */
	public List<Organization> findAdminUserByUserId(Long id);
	/**
	 * findLevelOnePage: 查询第一级机构，并返回分页对象<br/>
	 * @author caijin
	 * @param page
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Organization> findOneLevelPage(Page page);
	
	
	/**
	 * 通过组织机构查询全部部门，递归查询<br>
	 * @param root
	 * @return
	 */
	public List<Organization> findListAll(Param... params); 
	/**
	 * 添加/更新数据字典信息
	 * 先判断是更新还是添加
	 * 然后判断数据名称是否已经存在，若存在则返回提示信息
	 * save: 描述方法的作用 <br/>
	 * @author caijin
	 * @param parentId
	 * @param orga
	 * @since JDK 1.7
	 */
	public void save(Long parentId,Long adminUserId,Organization orga);
	
	/**
	 * update: 修改机构 <br/>
	 * @author caijin
	 * @param orga
	 * @since JDK 1.7
	 */
	public void update(Long adminUserId,Organization orga);
	/**
	 * 修改修改名称
	 * @param organizationId
	 * @param status
	 */
	public void updateName(Long organizationId,String name); 
	/**
	 * updateFreeze: 冻结机构 <br/>
	 * @author caijin
	 * @param organizationId
	 * @since JDK 1.7
	 */
	public void updateFreeze(Long organizationId);
	
	/**
	 * updateActivating: 激活机构 <br/>
	 * @author caijin
	 * @param organizationId
	 * @since JDK 1.7
	 */
	public void updateActivating(Long organizationId);
	/**
	 * @description:删除一个或多个对象
	 * 				先删除子类对象
	 * @param: 
	 * @return: void 
	 */
	public void delete(Long id);

	/**
	 * 
	 * checkOrganizationName: 检查新增的机构别名是否重复<br/>
	 * 别名必须为全局的
	 * @author caijin
	 * @param parentId
	 * @param provName
	 * @return
	 * @since JDK 1.7
	 */
	public boolean checkOrganizationAliases(Long id,String aliases);
	
	/**
	 * 
	 * checkOrganizationName: 检查新增的机构名称是否重复<br/>
	 * 如果不输入父机构id，名称将全局查询
	 * @author caijin
	 * @param parentId
	 * @param provName
	 * @return
	 * @since JDK 1.7
	 */
	public boolean checkOrganizationName(Long parentId,String provName);
	/**
	 * findHasUpdateOrganizationName: 这个是检查修改时候，机构名称是否重复<br/>
	 * 如果不输入父机构id，名称将全局查询<br/>
	 * 不比较自己的机构id
	 * @author caijin
	 * @param parentId
	 * @param id
	 * @param provName
	 * @return
	 * @since JDK 1.7
	 */
	public boolean findHasUpdateOrganizationName(Long parentId, Long id,String provName);
	/**
	 * findCountUserByRoleIds:
	 * @author huayj
	 * @param roleIds
	 * @return
	 * @return Long
	 * @date&time 2015-9-18 下午6:01:50
	 * @since JDK 1.7
	 */
	public Long findCountUserByRoleIds(Long[] roleIds);
	
	/**
	 * 
	 * findbyAdminUserIdAndRoleId: 描述方法的作用 <br/>
	 * @author liurui
	 * @param adminUserId
	 * @param adminUserId
	 * @return
	 * @since JDK 1.7
	 */
	public Organization findbyAdminUserIdAndRoleId(Long adminUserId, Long adminRoleId);
	/**
	 * existParam:
	 * @author huayj
	 * @param param
	 * @return
	 * @return Organization
	 * @date&time 2016-1-11 上午10:33:47
	 * @since JDK 1.7
	 */
	public Organization existParam(Param param);
	
	/**
	 * 获取
	 * @return
	 */
	public List<Organization> findHasChildListAll(Param... params);
	
	
}
