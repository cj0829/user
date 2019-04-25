package org.csr.common.user.entity;

import java.util.Date;

import org.csr.common.user.domain.UserPointsLog;
import org.csr.core.web.bean.VOBase;


public class UserPointsLogBean extends VOBase<Long>{
	private static final long serialVersionUID = -1102990763390121382L;
	private Long id;
	private UserBean user;
	private String operation;
	private Integer points1;
	private Integer points2;
	private Date operationDate;
	protected String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Integer getPoints1() {
		return points1;
	}
	public void setPoints1(Integer points1) {
		this.points1 = points1;
	}
	public Integer getPoints2() {
		return points2;
	}
	public void setPoints2(Integer points2) {
		this.points2 = points2;
	}
	public Date getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
//	public UserPointsLogBean(Long id, UserBean user, String operation,Integer points1, Integer points2, Date operationDate, String remark) {
//		super();
//		this.id = id;
//		this.user = user;
//		this.operation = operation;
//		this.points1 = points1;
//		this.points2 = points2;
//		this.operationDate = operationDate;
//		this.remark = remark;
//	}
//	public UserPointsLogBean() {
//		super();
//	}
	
	public static UserPointsLogBean wrapBean(UserPointsLog doMain) {
		UserPointsLogBean bean=new UserPointsLogBean();
		bean.setId(doMain.getId());
		bean.setOperation(doMain.getOperation());
		bean.setPoints1(doMain.getPoints1());
		bean.setPoints2(doMain.getPoints2());
		bean.setOperationDate(doMain.getOperationDate());
		bean.setRemark(doMain.getRemark());
		return bean;
	}
}
