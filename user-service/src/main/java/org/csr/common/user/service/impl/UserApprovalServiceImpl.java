package org.csr.common.user.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormOperType;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.common.user.constant.ImportApprovalStatus;
import org.csr.common.user.constant.MessageType;
import org.csr.common.user.dao.UserDao;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserImportFile;
import org.csr.common.user.safeResource.Category;
import org.csr.common.user.service.GlobalMessageService;
import org.csr.common.user.service.UserApprovalService;
import org.csr.common.user.service.UserImportFileService;
import org.csr.common.user.service.UserService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserCommand.java <br/>
 * Date:     Thu Sep 17 11:29:36 CST 2015
 * @author   huayj-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("userApprovalService")
public class UserApprovalServiceImpl extends SimpleBasisService<User, Long> implements UserApprovalService {
    @Resource
    private UserDao userDao;
    @Resource
    private UserImportFileService userImportFileService;
    @Resource
    private UserService userService;
    @Resource
    private TaskInstanceService taskInstanceService;
    @Resource
	protected GlobalMessageService globalMessageService;
    
    @Override
	public BaseDao<User, Long> getDao() {
		return userDao;
	}
	///
	@Override
	public void beforeForm(UserTaskInstance userTaskInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean saveForm(UserTaskInstance userTaskInstance) {
		if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType())){
			User user=userDao.findById(userTaskInstance.getFormObj().getId());
			user.setUserStatus(UserStatus.NORMAL);
			user.setUserTaskInstance(null);
			userService.updateSimple(user);
			UserImportFile userImportFile = userImportFileService.findById(user.getFileId());
			
			//需要重新计算审核通过和未通过数
			Integer userPassTotal = userImportFile.getUserPassTotal();
			userImportFile.setUserPassTotal(userPassTotal+1);
		}else if(FormOperType.REGUSER.equals(userTaskInstance.getFormOperType())){
			User user=userDao.findById(userTaskInstance.getFormObj().getId());
			user.setUserStatus(UserStatus.NORMAL);
			user.setUserTaskInstance(null);
			userService.updateSimple(user);
		}
		
		return false;
	}

	@Override
	public void updateCloseTask(UserTaskInstance userTaskInstance) {
		User user=userService.findById(userTaskInstance.getFormId());
		if(ObjUtil.isNotEmpty(user)){
			userService.deleteSimple(user);
		}
	}

	@Override
	public void createTaskInstanceAfter(UserTaskInstance userTaskInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleFolw(UserTaskInstance userTaskInstance) {
		User user=userDao.findById(userTaskInstance.getFormObj().getId());
		if(ObjUtil.isNotEmpty(user)){
			if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType()) && userTaskInstance.getCurrent().getNodeType().equals("RejectNode")){
				UserImportFile userImportFile = userImportFileService.findById(user.getFileId());
				//需要重新计算审核通过和未通过数
				Integer userUnPassTotal = userImportFile.getUserUnPassTotal();
				if(userUnPassTotal+ObjUtil.toInt(userImportFile.getUserPassTotal())<userImportFile.getUserTotal()){
					userImportFile.setUserUnPassTotal(userUnPassTotal+1);
				}
				user.setUserStatus(ImportApprovalStatus.REFUSAL);
				userDao.update(user);
			}else if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType()) && userTaskInstance.getCurrent().getNodeType().equals("TaskNode")){
				UserImportFile userImportFile = userImportFileService.findById(user.getFileId());
				//需要重新计算审核通过和未通过数
				Integer userUnPassTotal = userImportFile.getUserUnPassTotal();
				if(userUnPassTotal>0){
					userImportFile.setUserUnPassTotal(userUnPassTotal-1);
				}
				user.setUserStatus(ImportApprovalStatus.PENDING);
				userDao.update(user);
			}
		}
		if(userTaskInstance.getCurrent().getNodeType().equals("RejectNode")){
			if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType())){
				//TODO 2016.3.23 试题导入驳回发送消息
				String key = MessageType.USERIMPORTREJECT+"_"+user.getCreatedBy();
				String messageContent = "请您注意，您有用户导入被驳回！";
				globalMessageService.createMessage(user.getCreatedBy(),MessageType.USERIMPORTREJECT,user,key,messageContent);
			}
		}
		
	}

	@Override
	public void updateOrg(Long categoryItemId, Long toParentId, Long orgId) {}

	@Override
	public void updateMergeCurrentNode(Long categoryItemId, Long toParentId, Long orgId) {}

    


	@Override
	public int updatePass(Long[] userCommandIds) {
		List<User> commands=userDao.findByIds(userCommandIds);
		if(ObjUtil.isEmpty(commands)){
			return 0;
		}
		int totle=0;
		for (final User user : commands) {
			UserTaskInstance userTask=user.getUserTaskInstance();
			//并提交流程
			if(ObjUtil.isNotEmpty(userTask)){
				totle++;
				taskInstanceService.updateTaskPass(userTask.getId(),user, "同意通过",new BusinessStorage() {
					@Override
					public boolean save(UserTaskInstance userTaskInstance) {
						return saveForm(userTaskInstance);
					}
				});
			}
		}
		return totle;
	}


	@Override
	public int updateRefusa(Long[] userCommandIds, String explain) {
		List<User> commands=userDao.findByIds(userCommandIds);
		if(ObjUtil.isEmpty(commands)){
			return 0;
		}
		List<Long> userTaskInstanceIds=PersistableUtil.arrayTransforList(commands, new ToValue<User, Long>() {
			@Override
			public Long getValue(User obj) {
				if(ObjUtil.isNotEmpty(obj.getUserTaskInstance())){
					return obj.getUserTaskInstance().getId();
				}
				return null;
			}
		});
		return 	taskInstanceService.updateTaskRebut(userTaskInstanceIds.toArray(new Long[userTaskInstanceIds.size()]),explain);
	}
	@Override
	public void deleteCategoryItemChild(Category categoryItem) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public long checkCategoryItemHasChild(Category categoryItem) {
		// TODO Auto-generated method stub
		return 0;
	}
}
