package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.flow.service.TaskNodeApprovalChainService;
import org.csr.common.user.dao.UserImportFileDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserImportFile;
import org.csr.common.user.domain.UserImportStrategy;
import org.csr.common.user.service.UserImportFileService;
import org.csr.common.user.service.UserImportStrategyService;
import org.csr.common.user.service.UserService;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserImportFile.java <br/>
 * Date: Thu Aug 27 18:00:34 CST 2015
 * 
 * @author huayj-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 用户管理文件导入 service实现
 */
@Service("userImportFileService")
public class UserImportFileServiceImpl extends
		SimpleBasisService<UserImportFile, Long> implements
		UserImportFileService {
	@Resource
	private UserImportFileDao userImportFileDao;
	@Resource
	TaskNodeApprovalChainService taskNodeApprovalChainService;
	@Resource
	private UserImportStrategyService userImportStrategyService;
	@Resource
	private UserService userService;
	@Override
	public BaseDao<UserImportFile, Long> getDao() {
		return userImportFileDao;
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		UserImportFile userImportFile = userImportFileDao.existParam(new AndEqParam("name", name));
		if (ObjUtil.isNotEmpty(id)) {
			if (ObjUtil.isEmpty(userImportFile)
					|| userImportFile.getId().equals(id)) {
				return false;
			}
		}
		if (ObjUtil.isEmpty(userImportFile)) {
			return false;
		}
		return true;
	}

	@Override
	public PagedInfo<UserImportFile> findApproveUserImportFilePage(Page page,
			Long userId) {
		User user = userService.findById(userId);
		if (ObjUtil.isEmpty(user)) {
			Exceptions.service("", "您要审批的用户不存在");
		}

		List<UserImportStrategy> userStrategyList = userImportStrategyService.findAllList();
		List<Long> taskTempIds = PersistableUtil.arrayTransforList(
				userStrategyList, new ToValue<UserImportStrategy, Long>() {
					@Override
					public Long getValue(UserImportStrategy obj) {
						return obj.getTaskTemp().getId();
					}
				});

		// 查询当前人能够处理的节点。
		List<TaskNodeApprovalChain> taskNodeApprovalChainList = taskNodeApprovalChainService.findPeopleByTaskTempId(taskTempIds, userId);
		List<Long> userIds = new ArrayList<Long>(
				PersistableUtil.arrayTransforList(taskNodeApprovalChainList,
						new ToValue<TaskNodeApprovalChain, Long>() {
							@Override
							public Long getValue(TaskNodeApprovalChain obj) {
								User node = obj.getApprover();
								if (ObjUtil.isNotEmpty(node)) {
									return node.getId();
								}
								return null;
							}
						}));

		// 查询节点
		List<TaskNodeApprovalChain> releationChain = taskNodeApprovalChainService
				.findReleationByTaskTempIdTaskNode(taskTempIds, "TaskNode");
		PagedInfo<UserImportFile> userImportFileList = userImportFileDao
				.findPageByUserIdsAndChainIds(page, releationChain, userIds);
		return userImportFileList;
	}

	@Override
	public Long countByCreated(Long userId) {
		return userImportFileDao.countByCreated(userId);
	}

	@Override
	public boolean verifyExists(Agencies Agencies) {
		if(ObjUtil.isNotEmpty(Agencies)){
			userImportFileDao.deleteByParams(new AndEqParam("agencies.id", Agencies.getId()));
		}
		return false;
	}
}
