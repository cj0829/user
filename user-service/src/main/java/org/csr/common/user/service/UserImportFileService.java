package org.csr.common.user.service;


import org.csr.common.user.domain.UserImportFile;
import org.csr.common.user.facade.DeleteAgencies;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:UserImportFile.java <br/>
 * Date: Thu Aug 27 18:00:34 CST 2015 <br/>
 * @author huayj-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 用户管理文件导入 service接口
 */
public interface UserImportFileService extends BasisService<UserImportFile, Long>,DeleteAgencies {


	public boolean checkNameIsExist(Long id, String name);

	/**
	 * 跟用户审批查询出当前能够审批的，那个文件
	 * @author caijin
	 * @param page
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<UserImportFile> findApproveUserImportFilePage(Page page, Long userId);

	/**
	 * 统计未通过数
	 * @author caijin
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	public Long countByCreated(Long userId);


}
