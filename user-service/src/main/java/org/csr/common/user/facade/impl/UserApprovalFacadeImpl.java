package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserDao;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.UserApprovalBean;
import org.csr.common.user.facade.UserApprovalFacade;
import org.csr.common.user.service.UserApprovalService;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.util.PersistableUtil;
import org.springframework.stereotype.Service;

@Service("userApprovalFacade")
public class UserApprovalFacadeImpl implements UserApprovalFacade{

	@Resource
	private UserApprovalService userApprovalService;
	@Resource
	private UserDao UserDao;
	
	@Override
	public int updatePass(Long[] userCommandIds) {
		return userApprovalService.updatePass(userCommandIds);
	}

	@Override
	public int updateRefusa(Long[] userCommandIds, String explain) {
		return userApprovalService.updateRefusa(userCommandIds, explain);
	}

	@Override
	public PagedInfo<UserApprovalBean> findAllPage(Page page) {
		PagedInfo<User> paged = UserDao.findAllPage(page);
		return PersistableUtil.toPagedInfoBeans(paged, new SetBean<User>() {
			@Override
			public UserApprovalBean setValue(User doMain) {
				return UserApprovalBean.toBean(doMain);
			}
		});
	}

}
