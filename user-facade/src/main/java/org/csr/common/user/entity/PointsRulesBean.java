package org.csr.common.user.entity;

import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.PointsRules;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

public class PointsRulesBean extends VOBase<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String operation;
	private Long functionPointId;
	private String functionPointName;
	private String functionPointCode;
	private Integer points1;
	private String pointsMark1;
	private Integer points2;
	private String pointsMark2;
	private Integer countDay;
	private String remark;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public Long getFunctionPointId() {
		return functionPointId;
	}
	public void setFunctionPointId(Long functionPointId) {
		this.functionPointId = functionPointId;
	}
	public String getFunctionPointName() {
		return functionPointName;
	}
	public void setFunctionPointName(String functionPointName) {
		this.functionPointName = functionPointName;
	}
	public String getFunctionPointCode() {
		return functionPointCode;
	}
	public void setFunctionPointCode(String functionPointCode) {
		this.functionPointCode = functionPointCode;
	}
	public Integer getPoints1() {
		return points1;
	}
	public void setPoints1(Integer points1) {
		this.points1 = points1;
	}
	public String getPointsMark1() {
		return pointsMark1;
	}
	public void setPointsMark1(String pointsMark1) {
		this.pointsMark1 = pointsMark1;
	}
	public Integer getPoints2() {
		return points2;
	}
	public void setPoints2(Integer points2) {
		this.points2 = points2;
	}
	public String getPointsMark2() {
		return pointsMark2;
	}
	public void setPointsMark2(String pointsMark2) {
		this.pointsMark2 = pointsMark2;
	}
	public Integer getCountDay() {
		return countDay;
	}
	public void setCountDay(Integer countDay) {
		this.countDay = countDay;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public static PointsRulesBean wrapBean(PointsRules doMain) {
		PointsRulesBean bean=new PointsRulesBean();
		bean.setId(doMain.getId());
		bean.setCountDay(doMain.getCountDay());
		FunctionPoint fp=doMain.getFunctionPoint();
		if(ObjUtil.isNotEmpty(fp)){
			bean.setFunctionPointId(fp.getId());
			bean.setFunctionPointName(fp.getName());
			bean.setFunctionPointCode(fp.getCode());
		}
		bean.setOperation(doMain.getOperation());
		bean.setPoints1(doMain.getPoints1());
		bean.setPoints2(doMain.getPoints2());
		bean.setPointsMark1(doMain.getPointsMark1());
		bean.setPointsMark2(doMain.getPointsMark2());
		bean.setRemark(doMain.getRemark());
		return bean;
	}
}
