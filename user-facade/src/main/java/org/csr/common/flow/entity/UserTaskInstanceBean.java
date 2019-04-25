package org.csr.common.flow.entity;

import org.csr.common.flow.domain.UserTaskInstance;
import org.csr.common.user.domain.User;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:TaskInstanceResult.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014年3月17日下午11:01:47 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
public class UserTaskInstanceBean extends VOBase<Long> implements UserTaskImpl {

	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * @since JDK 1.7
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * serialVersionUID:(用一句话描述这个变量表示什么).
	 * 
	 * @since JDK 1.7
	 */
	private Long id;
	/** 当前流程的 id */
	private Long currentId;
	/** 用户 id */
	private Long userId;
	/** 当前流程的 名称 */
	private String currentName;
	/** 任务实例 id */
	private Long taskInstanceId;
	/** 任务实例 名称 */
	private String taskInstanceName;

	/** 表单数据clas名称数据 */
	private String className;
	/**是谁修改的 */
	private Long updateUserId;
	/**是谁修改的 */
	private String updateUserName;
	
	/** 当前节点的处理意见 */
	private String opinion;
	/** 当前流程节点是否能处理 */
	private Byte isHandle;
	/** 当前流程节点是否能编辑 */
	private Byte isEdit;
	
	
	public UserTaskInstanceBean() {
	}

	public UserTaskInstanceBean(Long id, Long currentId, Long userId, String currentName,
			Long taskInstanceId, String taskInstanceName) {
		this.id = id;
		this.currentId = currentId;
		this.userId = userId;
		this.currentName = currentName;
		this.taskInstanceId = taskInstanceId;
		this.taskInstanceName = taskInstanceName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCurrentId() {
		return currentId;
	}

	public void setCurrentId(Long currentId) {
		this.currentId = currentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public Long getTaskInstanceId() {
		return taskInstanceId;
	}

	public void setTaskInstanceId(Long taskInstanceId) {
		this.taskInstanceId = taskInstanceId;
	}

	public String getTaskInstanceName() {
		return taskInstanceName;
	}

	public void setTaskInstanceName(String taskInstanceName) {
		this.taskInstanceName = taskInstanceName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	@Override
	public Long getFormId() {

		return null;
	}

	@Override
	public void setFormId(Long formId) {

	}

	@Override
	public Form getFormObj() {

		return null;
	}

	@Override
	public void setFormObj(Form formObj) {

	}

	public Byte getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Byte isHandle) {
		this.isHandle = isHandle;
	}
	

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	
	public Byte getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Byte isEdit) {
		this.isEdit = isEdit;
	}

	public static UserTaskInstanceBean toBean(UserTaskInstance doMain) {
		UserTaskInstanceBean bean = new UserTaskInstanceBean();
		bean.setId(doMain.getId());
		bean.setCurrentId(doMain.getCurrent().getId());
		bean.setCurrentName(doMain.getCurrent().getName());
		bean.setIsEdit(doMain.getCurrent().getIsEdit());
		bean.setTaskInstanceId(doMain.getTaskInstance().getId());
		bean.setTaskInstanceName(doMain.getTaskInstance().getName());
		bean.setClassName(doMain.getClassName());
		bean.setOpinion(doMain.getOpinion());
		return bean;
	}

	public static UserTaskInstanceBean toBean(UserTaskInstance doMain,User updateUser) {
		UserTaskInstanceBean bean = new UserTaskInstanceBean();
		bean.setId(doMain.getId());
		bean.setCurrentId(doMain.getCurrent().getId());
		bean.setCurrentName(doMain.getCurrent().getName());
		bean.setTaskInstanceId(doMain.getTaskInstance().getId());
		bean.setTaskInstanceName(doMain.getTaskInstance().getName());
		bean.setClassName(doMain.getClassName());
		bean.setOpinion(doMain.getOpinion());
		if(ObjUtil.isNotEmpty(updateUser)){
			bean.setUpdateUserName(updateUser.getName());
			bean.setUserId(updateUser.getId());
		}
		return bean;
	}
	public static UserTaskInstanceBean wrapBean(UserTaskInstance doMain) {
		UserTaskInstanceBean bean=new UserTaskInstanceBean();
		bean.setId(doMain.getId());
		bean.setCurrentId(doMain.getCurrent().getId());
		bean.setCurrentName(doMain.getCurrent().getName());
		bean.setUserId(doMain.getUser().getId());
		bean.setTaskInstanceId(doMain.getTaskInstance().getId());
		bean.setTaskInstanceName(doMain.getTaskInstance().getName());
		bean.setClassName(doMain.getClassName());
		bean.setUpdateUserId(doMain.getUser().getId());
		bean.setUpdateUserName(doMain.getUser().getName());
		bean.setOpinion(doMain.getOpinion());
		
		return bean;
	}
}
