package org.csr.common.user.entity;

import org.csr.common.flow.domain.FlowStrategy;
import org.csr.core.AutoSetProperty;
import org.csr.core.constant.YesorNo;
import org.csr.core.web.bean.VOBase;

public class FlowStrategyBean extends VOBase<Long>{
	private static final long serialVersionUID = 461585922808887682L;

	/** 新建 是否需要审批 */
	private Byte isNewApproval = YesorNo.NO;
	/** 修改 是否需要审批 */
	private Byte isEditApproval = YesorNo.NO;
	/** 删除 是否需要审批 */
	private Byte isDeleteApproval = YesorNo.NO;
	/** 变更库  是否需要审批 */
	private Byte isChangeCategoryApproval = YesorNo.NO;

	@AutoSetProperty(message = "域")
	private Long orgId;
	/** 审批流程模板模板 */
	private Long taskTempId;

	public Byte getIsNewApproval() {
		return isNewApproval;
	}

	public void setIsNewApproval(Byte isNewApproval) {
		this.isNewApproval = isNewApproval;
	}

	public Byte getIsEditApproval() {
		return isEditApproval;
	}

	public void setIsEditApproval(Byte isEditApproval) {
		this.isEditApproval = isEditApproval;
	}

	public Byte getIsDeleteApproval() {
		return isDeleteApproval;
	}

	public void setIsDeleteApproval(Byte isDeleteApproval) {
		this.isDeleteApproval = isDeleteApproval;
	}

	public Byte getIsChangeCategoryApproval() {
		return isChangeCategoryApproval;
	}

	public void setIsChangeCategoryApproval(Byte isChangeCategoryApproval) {
		this.isChangeCategoryApproval = isChangeCategoryApproval;
	}

	

	public FlowStrategyBean(Byte isNewApproval, Byte isEditApproval,
			Byte isDeleteApproval, Byte isChangeCategoryApproval
			) {
		super();
		this.isNewApproval = isNewApproval;
		this.isEditApproval = isEditApproval;
		this.isDeleteApproval = isDeleteApproval;
		this.isChangeCategoryApproval = isChangeCategoryApproval;
		
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getTaskTempId() {
		return taskTempId;
	}

	public void setTaskTempId(Long taskTempId) {
		this.taskTempId = taskTempId;
	}

	public FlowStrategyBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public static FlowStrategyBean wrapBean(FlowStrategy doMain) {
		FlowStrategyBean bean= new FlowStrategyBean();
		bean.setId(doMain.getId());
		bean.setIsNewApproval(doMain.getIsNewApproval());
		bean.setIsEditApproval(doMain.getIsEditApproval());
		bean.setIsDeleteApproval(doMain.getIsDeleteApproval());;
		bean.setIsChangeCategoryApproval(doMain.getIsChangeCategoryApproval());
		bean.setOrgId(doMain.getOrgId());
		bean.setTaskTempId(doMain.getTaskTemp().getId());	
		return bean;
	}
}
