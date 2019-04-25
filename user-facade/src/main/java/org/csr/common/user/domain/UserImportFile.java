package org.csr.common.user.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.AutoSetProperty;
import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

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
@Entity
@Table(name = "u_UserImportFile")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch = "用户文件导入",en="pmt_common_UserImportFile")
public class UserImportFile extends SimpleDomain<Long>{
	/**
	 * 
	 */
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	private Date upLoadDate;
	/**上传者*/
	private Long upLoadUserId;
	/**试题未通过数*/
	private Integer userUnPassTotal=0;
	/**试题已通过数*/
	private Integer userPassTotal=0;
	/**试题待批数*/
	private Integer userEffectedTotal=0;
	@AutoSetProperty(message="机构组织")
	private Agencies agencies;
	/**域id*/
	private Long orgId;
	
	@Id
	@GeneratedValue(generator = "globalGenerator")
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch="id",en = "id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "fileName")
	@Comment(ch="文件名",en = "fileName")
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name = "originalFileName")
	@Comment(ch="原文件名",en = "originalFileName")
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	@Column(name = "filePath")
	@Comment(ch="文件路径",en = "filePath")
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	@Column(name = "userTotal")
	@Comment(ch="用户总数",en = "userTotal")
	public Integer getUserTotal() {
		return userTotal;
	}
	public void setUserTotal(Integer userTotal) {
		this.userTotal = userTotal;
	}
	@Column(name = "auditStatus")
	@Comment(ch="审核状态",en = "auditStatus")
	public Byte getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Byte auditStatus) {
		this.auditStatus = auditStatus;
	}
	@Column(name="upLoadDate")
	@Comment(ch="上传时间",en="upLoadDate")
	public Date getUpLoadDate() {
		return upLoadDate;
	}
	public void setUpLoadDate(Date upLoadDate) {
		this.upLoadDate = upLoadDate;
	}
	@Column(name = "upLoadUserId")
	@Comment(ch="上传者",en = "upLoadUserId")
	public Long getUpLoadUserId() {
		return upLoadUserId;
	}
	public void setUpLoadUserId(Long upLoadUserId) {
		this.upLoadUserId = upLoadUserId;
	}
	@Column(name = "userUnPassTotal")
	@Comment(ch="用户未通过数",en = "userUnPassTotal")
	public Integer getUserUnPassTotal() {
		return userUnPassTotal;
	}
	public void setUserUnPassTotal(Integer userUnPassTotal) {
		this.userUnPassTotal = userUnPassTotal;
	}
	@Column(name = "userPassTotal")
	@Comment(ch="用户已通过数",en = "userPassTotal")
	public Integer getUserPassTotal() {
		return userPassTotal;
	}
	public void setUserPassTotal(Integer userPassTotal) {
		this.userPassTotal = userPassTotal;
	}
	@Column(name = "userEffectedTotal")
	@Comment(ch="用户待批数",en = "userEffectedTotal")
	public Integer getUserEffectedTotal() {
		return userEffectedTotal;
	}
	public void setUserEffectedTotal(Integer userEffectedTotal) {
		this.userEffectedTotal = userEffectedTotal;
	}
	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name="agenciesId", referencedColumnName="id",nullable=true,insertable=true,updatable=true) })
    @Comment(ch="机构",en="agenciesId")
	public Agencies getAgencies() {
		return agencies;
	}
	public void setAgencies(Agencies agencies) {
		this.agencies = agencies;
	}
	
	@Column(name = "orgId", nullable = false)
	@Comment(ch="域id",en = "orgId")
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	
}

