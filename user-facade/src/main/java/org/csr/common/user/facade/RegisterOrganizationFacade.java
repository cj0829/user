package org.csr.common.user.facade;

import org.csr.common.user.entity.RegisterBean;
import org.csr.common.user.entity.UserBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;

/**
 * ClassName:RegisterOrganizationService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface RegisterOrganizationFacade {
	
	
	/**
	 * saveAuthorize: 保存或编辑授权机构，赋权库和功能点 <br/>
	 * @author caijin
	 * @param orgId
	 * @param funs
	 * @return
	 * @since JDK 1.7
	 */
	public Organization saveAuthorize(Long orgId,Long[] funs);
	
	/**
	 * saveAuthorize: 保存或编辑授权机构，赋权库和功能点 <br/>
	 * 只按照，功能点，自己的id赋权。
	 * @author caijin
	 * @param orgId
	 * @param funs
	 * @return
	 * @since JDK 1.7
	 */
	public Organization saveMyselfAuthorize(Long orgId,Long[] funs);
	
	/**
	 * 注册保存机构。创建机构，创建系统管理员用户，<br>
	 * 创建管理员角色，分配功能点。
	 * @param register
	 * @author caijin
	 * @param createOrChoose 
	 * @return
	 */
	public UserBean saveOrganization(RegisterBean register, Byte createOrChoose);

	/**
	 * 修改注册机构，修改用户名，机构名，功能点
	 * @param register
	 * @author caijin
	 */
	public UserBean updateOrganization(RegisterBean register);
	
	/**
	 * 删除注册机构，如果，机构，还没有新增用户，及子结构，可以被删除<br>
	 * 并删除机构下面相关所有信息<br>
	 * 包含有，管理员，角色，角色关联的功能点，及机构对应的参数配置。
	 * @param organizationId
	 * @author caijin
	 */
	public void delete(Long organizationId);
	
	/**
	 * 初始化修改管理员密码
	 * @param organizationId
	 * @author caijin
	 */
	public void updateAdminPassword(Long organizationId);

	/**
	 * 查询当前机构下的全部注册信息，包含机构信息<br>
	 * 角色信息，用户信息，功能点信息，参数信息
	 * @param organizationId
	 * @return
	 * @author caijin
	 */
	public RegisterBean findRegisterResult(Long organizationId);

	/**
	 * findOrganizationPage: 查询全的根节点机构，并查询，当前根节点机构的admin员名称 <br/>
	 * @author caijin
	 * @param page
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Organization> findOrganizationPage(Page page);

	/**
	 * checkUpdateName:
	 * @author huayj
	 * @param id
	 * @param name
	 * @return
	 * @return boolean
	 * @date&time 2016-1-11 上午10:29:44
	 * @since JDK 1.7
	 */
	public boolean checkUpdateName(Long id, String name);

	/**
	 * 给当前机构，设置，不需要授权的功能点。能够直接查询出的权限
	 * @param organizationId
	 * @param functionPointIds
	 */
	public void saveOrgDefaultPermissions(Long organizationId,Long[] functionPointIds);

	
}
