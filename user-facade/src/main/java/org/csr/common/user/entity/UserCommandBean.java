/**
 * 
 */
package org.csr.common.user.entity;
import java.util.Date;

import org.csr.common.flow.entity.FormBean;
import org.csr.common.flow.entity.UserTaskInstanceBean;
import org.csr.common.user.domain.UserCommand;

/**
 *ClassName:UserCommand.java<br/>
 *Date:2015-9-17 上午11:11:20
 *@author huayj
 *@version 1.0
 *@since JDK 1.7
 *功能描述:
 */
public class UserCommandBean extends FormBean {

	private static final long serialVersionUID = 2367371079302114863L;
	private UserBean user;
	/**用户导入文件Id*/
	private Long fileId;
	/**用户状态*/
	private Integer userStatus;
	/**创建时间*/
	private Date createTime;
	public Long getId() {
		return super.id;
	}
	public void setId(Long id) {
		super.id = id;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public static UserCommandBean toBean(UserCommand doMain) {
		UserCommandBean bean = new UserCommandBean();
		bean.setId(doMain.getId());
		bean.setUser(UserBean.wrapBean(doMain.getUser()));
		bean.setFileId(doMain.getFileId());
		bean.setUserStatus(doMain.getUserStatus());
		bean.setUserTaskInstance(UserTaskInstanceBean.toBean(doMain.getUserTaskInstance()));
		bean.setCreateTime(doMain.getCreateTime());
		return bean;
	}
}
