package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.entity.OrganizationNode;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;
 

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
public interface OrganizationFacade{

	PagedInfo<OrganizationNode> findAllListPage(Page page);
	
	List<OrganizationNode> findHasChildListAll(Param... params);
	
	void update(Long adminUserId, Organization orga);

	void save(Long parentId, Long adminUserId, Organization orga);

	boolean checkOrganizationName(Long parentId, String name);

	boolean findHasUpdateOrganizationName(Long parentId, Long id, String name);

	boolean checkOrganizationAliases(Long id, String aliases);

	void deleteOrganization(Long organizationId);

	void updateFreeze(Long organizationId);

	void updateActivating(Long organizationId);

	String findNameById(Long id, String string);

	OrganizationNode findById(Long id);

	List<OrganizationNode> findAllList();

	PagedInfo<OrganizationNode> findAllPage(Page page);


	
	
}
