package org.csr.common.user.entity;

import java.util.Date;

import org.csr.common.user.domain.UserImportFile;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName: UserImportFile.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2015-8-13下午3:31:11 <br/>
 * @author   huayj <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  导入用户文件<br/>
 * 公用方法描述：  <br/>
 *
 */
public class UserImportFileBean extends VOBase<Long> {
	private static final long serialVersionUID = 3012122323702036807L;
	private Long id;
	/**文件名*/
	private String fileName;
	/**原文件名*/
	private String originalFileName;
	/**文件路径*/
	private String filePath;
	/**试题总数*/
	private Integer userTotal;
	/**审核状态*/
	private Byte auditStatus;
	/**上传时间*/
	private Date upLoadDate;
	/**上传者*/
	private UserBean upLoadUser;
	/**试题未通过数*/
	private Integer userUnPassTotal=0;
	/**试题已通过数*/
	private Integer userPassTotal=0;
	private String agenciesName;
	private Long agenciesId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Integer getUserTotal() {
		return userTotal;
	}
	public void setUserTotal(Integer userTotal) {
		this.userTotal = userTotal;
	}
	public Byte getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Date getUpLoadDate() {
		return upLoadDate;
	}
	public void setUpLoadDate(Date upLoadDate) {
		this.upLoadDate = upLoadDate;
	}
	public UserBean getUpLoadUser() {
		return upLoadUser;
	}
	public void setUpLoadUser(UserBean upLoadUser) {
		this.upLoadUser = upLoadUser;
	}
	public Integer getUserUnPassTotal() {
		return userUnPassTotal;
	}
	public void setUserUnPassTotal(Integer userUnPassTotal) {
		this.userUnPassTotal = userUnPassTotal;
	}
	public Integer getUserPassTotal() {
		return userPassTotal;
	}
	public void setUserPassTotal(Integer userPassTotal) {
		this.userPassTotal = userPassTotal;
	}
	public Integer getUserEffectedTotal() {
		return userTotal-userPassTotal-userUnPassTotal;
	}
	
	public String getAgenciesName() {
		return agenciesName;
	}
	public void setAgenciesName(String agenciesName) {
		this.agenciesName = agenciesName;
	}
	public Long getAgenciesId() {
		return agenciesId;
	}
	public void setAgenciesId(Long agenciesId) {
		this.agenciesId = agenciesId;
	}
	
	public static UserImportFileBean wrapBean(UserImportFile doMain) {
		UserImportFileBean bean = new UserImportFileBean();
		bean.setId(doMain.getId());
		bean.setFileName(doMain.getFileName());
		bean.setOriginalFileName(doMain.getOriginalFileName());
		bean.setFilePath(doMain.getFilePath());
		bean.setUserTotal(doMain.getUserTotal());
		bean.setAuditStatus(doMain.getAuditStatus());
		bean.setUpLoadDate(doMain.getUpLoadDate());
//		bean.setUpLoadUser(doMain.getUpLoadUser());
		bean.setUserUnPassTotal(doMain.getUserUnPassTotal());
		bean.setUserPassTotal(doMain.getUserPassTotal());
		if(ObjUtil.isNotEmpty(doMain.getAgencies())){
			bean.setAgenciesName(doMain.getAgencies().getName());
			bean.setAgenciesId(doMain.getAgencies().getId());
		}
		return bean;
	}
}

