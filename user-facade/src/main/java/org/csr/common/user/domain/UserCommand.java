/**
 * 
 */
package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.common.flow.domain.FormDomain;
import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;

/**
 *ClassName:UserCommand.java<br/>
 *Date:2015-9-17 上午11:11:20
 *@author huayj
 *@version 1.0
 *@since JDK 1.7
 *功能描述:
 */
@Entity
@Table(name = "u_UserCommand")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch = "用户文件导入",en="pmt_common_UserCommand")
public class UserCommand extends FormDomain{
	private static final long serialVersionUID = 2367371079302114863L;
	
	private long id;
	
	private String name;
	@AutoSetProperty(message="用户")
	private User user;
	/**用户导入文件Id*/
	private Long fileId;
	/**用户状态*/
	private Integer userStatus;
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "库ID", en = "id", search = true)
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name="userId", referencedColumnName="id",nullable=true,insertable=true,updatable=true) })
    @Comment(ch="用户",en="userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "fileId")
	@Comment(ch="用户导入文件Id",en="fileId")
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	@Column(name = "userStatus")
	@Comment(ch="用户状态",en="userStatus")
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	@Length(min=0,max=32)
	@Column(name = "callName", length = 32)
	@Comment(ch = "昵称", en = "callName", len = 32)
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
