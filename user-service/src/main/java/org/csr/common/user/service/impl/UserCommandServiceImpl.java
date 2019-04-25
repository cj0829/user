package org.csr.common.user.service.impl;


import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.constant.FormOperType;
import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.flow.support.BusinessStorage;
import org.csr.common.user.constant.ImportApprovalStatus;
import org.csr.common.user.constant.MessageType;
import org.csr.common.user.dao.UserCommandDao;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserCommand;
import org.csr.common.user.domain.UserImportFile;
import org.csr.common.user.safeResource.Category;
import org.csr.common.user.service.GlobalMessageService;
import org.csr.common.user.service.UserCommandService;
import org.csr.common.user.service.UserImportFileService;
import org.csr.common.user.service.UserService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.param.AndEqParam;
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
@Service("userCommandService")
public class UserCommandServiceImpl extends SimpleBasisService<UserCommand, Long> implements UserCommandService {
    @Resource
    private UserCommandDao userCommandDao;
    @Resource
    private UserImportFileService userImportFileService;
    @Resource
    private UserService userService;
    @Resource
    private TaskInstanceService taskInstanceService;
    @Resource
	protected GlobalMessageService globalMessageService;
    
    @Override
	public BaseDao<UserCommand, Long> getDao() {
		return userCommandDao;
	}
	///
	@Override
	public void beforeForm(UserTaskInstance userTaskInstance) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean saveForm(UserTaskInstance userTaskInstance) {
		if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType())){
			UserCommand userCommand=userCommandDao.findById(userTaskInstance.getFormObj().getId());
			User user = userService.findById(userCommand.getUser().getId());
			user.setUserStatus(UserStatus.NORMAL);
			userService.updateSimple(user);
			UserImportFile userImportFile = userImportFileService.findById(userCommand.getFileId());
			
			//需要重新计算审核通过和未通过数
			Integer userPassTotal = userImportFile.getUserPassTotal();
			userImportFile.setUserPassTotal(userPassTotal+1);
//			if(ImportApprovalStatus.REFUSAL.equals(userCommand.getUserStatus())){
//				userImportFile.setUserUnPassTotal(userImportFile.getUserUnPassTotal()-1);
//			}
//			userCommand.setUserStatus(ImportApprovalStatus.PASS);
//			userCommand.setUserTaskInstance(null);
			userCommandDao.delete(userCommand);
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

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteCategoryItemChild(Category categoryItem) {
	}
	@SuppressWarnings("rawtypes")
	@Override
	public long checkCategoryItemHasChild(Category categoryItem) {
		return 0;
	}
	@Override
	public void handleFolw(UserTaskInstance userTaskInstance) {
		UserCommand userCommand=userCommandDao.findById(userTaskInstance.getFormObj().getId());
		if(ObjUtil.isNotEmpty(userCommand)){
			if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType()) && userTaskInstance.getCurrent().getNodeType().equals("RejectNode")){
				UserImportFile userImportFile = userImportFileService.findById(userCommand.getFileId());
				//需要重新计算审核通过和未通过数
				Integer userUnPassTotal = userImportFile.getUserUnPassTotal();
				if(userUnPassTotal+ObjUtil.toInt(userImportFile.getUserPassTotal())<userImportFile.getUserTotal()){
					userImportFile.setUserUnPassTotal(userUnPassTotal+1);
				}
				userCommand.setUserStatus(ImportApprovalStatus.REFUSAL);
				userCommandDao.update(userCommand);
			}else if(FormOperType.IMPORT.equals(userTaskInstance.getFormOperType()) && userTaskInstance.getCurrent().getNodeType().equals("TaskNode")){
				UserImportFile userImportFile = userImportFileService.findById(userCommand.getFileId());
				//需要重新计算审核通过和未通过数
				Integer userUnPassTotal = userImportFile.getUserUnPassTotal();
				if(userUnPassTotal>0){
					userImportFile.setUserUnPassTotal(userUnPassTotal-1);
				}
				userCommand.setUserStatus(ImportApprovalStatus.PENDING);
				userCommandDao.update(userCommand);
			}
		}
		if(userTaskInstance.getCurrent().getNodeType().equals("RejectNode")){
			if(FormOperType.IMPORT.equals(userCommand.getOperType())){
				//TODO 2016.3.23 试题导入驳回发送消息
				String key = MessageType.USERIMPORTREJECT+"_"+userCommand.getCreatedBy();
				String messageContent = "请您注意，您有用户导入被驳回！";
				globalMessageService.createMessage(userCommand.getCreatedBy(),MessageType.USERIMPORTREJECT,userCommand,key,messageContent);
			}
		}
		
	}

	@Override
	public void updateOrg(Long categoryItemId, Long toParentId, Long orgId) {}

	@Override
	public void updateMergeCurrentNode(Long categoryItemId, Long toParentId, Long orgId) {}

    
	@Override
	public void save(UserCommand userCommand){
		//如果需要判断这里更改
		
		userCommandDao.save(userCommand);
	}

	@Override
	public void update(UserCommand userCommand){
	
		//如果需要判断这里更改
		UserCommand olduserCommand = userCommandDao.findById(userCommand.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		olduserCommand.setId(userCommand.getId());
		olduserCommand.setUser(userCommand.getUser());
		olduserCommand.setFileId(userCommand.getFileId());

		userCommandDao.update(olduserCommand);
	}
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		UserCommand userCommand = userCommandDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(userCommand) || userCommand.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(userCommand)) {
		    return false;
		}
		return true;
	}


	@Override
	public int updatePass(Long[] userCommandIds) {
		List<UserCommand> commands=userCommandDao.findByIds(userCommandIds);
		if(ObjUtil.isEmpty(commands)){
			return 0;
		}
		int totle=0;
		for (UserCommand userCommand : commands) {
			UserTaskInstance userTask=userCommand.getUserTaskInstance();
			//并提交流程
			if(ObjUtil.isNotEmpty(userTask)){
				totle++;
				taskInstanceService.updateTaskPass(userTask.getId(),userCommand, "同意通过",new BusinessStorage() {
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
		List<UserCommand> commands=userCommandDao.findByIds(userCommandIds);
		if(ObjUtil.isEmpty(commands)){
			return 0;
		}
		Collection<Long> userTaskInstanceIds=PersistableUtil.arrayTransforCollection(commands, new ToValue<UserCommand, Long>() {
			@Override
			public Long getValue(UserCommand obj) {
				if(ObjUtil.isNotEmpty(obj.getUserTaskInstance())){
					return obj.getUserTaskInstance().getId();
				}
				return null;
			}
		});
		return 	taskInstanceService.updateTaskRebut(userTaskInstanceIds.toArray(new Long[userTaskInstanceIds.size()]),explain);
	}
	
	
}
