package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.domain.User;
import org.csr.common.user.entity.OrganizationNode;
import org.csr.common.user.facade.OrganizationFacade;
import org.csr.common.user.service.OrganizationService;
import org.csr.common.user.service.UserService;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.dao.OrganizationDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.csr.core.web.bean.VOBase;
import org.springframework.stereotype.Service;

@Service("organizationFacade")
public class OrganizationFacadeImpl extends SimpleBasisFacade<OrganizationNode, Organization, Long> implements OrganizationFacade {
	
	@Resource
	OrganizationService organizationService;
	@Resource
	OrganizationDao organizationDao;
	@Resource
	UserService userService;

	@Override
	public OrganizationNode wrapBean(Organization doMain) {
		return OrganizationNode.wrapBean(doMain);
	}
	@Override
	public List<OrganizationNode> findHasChildListAll(Param... params) {
		List<Organization> result = organizationService.findHasChildListAll();
		List<OrganizationNode> organizationPaged=PersistableUtil.toListBeans(result,new SetBean<Organization>() {
			@Override
			public VOBase<Long> setValue(Organization doMain) {
				OrganizationNode bean = OrganizationNode.wrapBean(doMain);
				return bean;
			}
		});
		return organizationPaged;
	}

//	@Override
//	public Organization wrapDomain(OrganizationNode entity) {
//		Organization org = new Organization(entity.getId());
//		org.setParentId(entity.getParentId());
//		org.setName(entity.getName());
//		org.setAliases(entity.getAliases());
//		org.setAdminUserId(entity.getAdminUserId());
//		org.setAdminRoleId(entity.getAdminRoleId());
//		org.setSafeResourceCollectionId(entity.getSafeResourceCollectionId());
//		org.setOrganizationLevel(entity.getOrganizationLevel());
//		org.setLeader(entity.getLeader());
//		org.setOrganizationStatus(entity.getOrganizationStatus());
//		org.setRemark(entity.getRemark());
//		return org;
//	}

	@Override
	public PagedInfo<OrganizationNode> findAllListPage(Page page) {
		PagedInfo<Organization> result = organizationService.findAllPage(page);
		
		List<Long> ids=PersistableUtil.arrayTransforList(result,new ToValue<Organization,Long>(){
			@Override
			public Long getValue(Organization obj) {
				return obj.getParentId();
			}
	 	});
		List<Long> userIds=PersistableUtil.arrayTransforList(result,new ToValue<Organization,Long>(){
			@Override
			public Long getValue(Organization obj) {
				return obj.getAdminUserId();
			}
	 	});
		
		final Map<Long,Organization> parent = organizationService.findMapByIds(ids.toArray(new Long[ids.size()]));
	 	final Map<Long,User> userMap = userService.findMapByIds(new ArrayList<Long>(userIds));
		PagedInfo<OrganizationNode> organizationPaged=PersistableUtil.toPagedInfoBeans(result,new SetBean<Organization>() {
			@Override
			public VOBase<Long> setValue(Organization doMain) {
				User user = userMap.get(doMain.getAdminUserId());
				OrganizationNode bean = OrganizationNode.wrapBean(doMain);
				if(ObjUtil.isNotEmpty(parent) && ObjUtil.isNotEmpty(parent.get(doMain.getParentId()))){
					bean.setParentName(parent.get(doMain.getParentId()).getName());
					if(ObjUtil.isNotEmpty(user)){
						bean.setAdminUserName(user.getLoginName());
					}
				}
				return bean;
			}
		});
		return organizationPaged;
	}

	@Override
	public void update(Long adminUserId, Organization orga) {
		organizationService.update(adminUserId, orga);
		
	}

	@Override
	public void save(Long parentId, Long adminUserId, Organization orga) {
		organizationService.save(parentId, adminUserId, orga);
		
	}

	@Override
	public boolean checkOrganizationName(Long parentId, String name) {
		
		return organizationService.checkOrganizationName(parentId, name);
	}

	@Override
	public boolean findHasUpdateOrganizationName(Long parentId, Long id,
			String name) {
		
		return organizationService.findHasUpdateOrganizationName(parentId, id, name);
	}

	@Override
	public boolean checkOrganizationAliases(Long id, String aliases) {
		
		return organizationService.checkOrganizationAliases(id, aliases);
	}

	@Override
	public void deleteOrganization(Long organizationId) {
		organizationService.delete(organizationId);
		
	}

	@Override
	public void updateFreeze(Long organizationId) {
		organizationService.updateFreeze(organizationId);
	}

	@Override
	public void updateActivating(Long organizationId) {
		organizationService.updateActivating(organizationId);
	}
	@Override
	public BaseDao<Organization, Long> getDao() {
		return organizationDao;
	}

	
}
