package org.csr.common.user.dao;


import java.util.List;

import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.user.domain.UserImportFile;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:UserImportFile.java <br/>
 * Date: Thu Aug 27 18:00:34 CST 2015 <br/>
 * 
 * @author huayj-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 用户管理文件导入 dao接口
 */
public interface UserImportFileDao extends BaseDao<UserImportFile,Long>{

	/**
	 * 查询当前用户能够审批的上传用户文件
	 * @author caijin
	 * @param page
	 * @param releationChain
	 * @param userIds
	 * @return
	 * @since JDK 1.7
	 */
	PagedInfo<UserImportFile> findPageByUserIdsAndChainIds(Page page,List<TaskNodeApprovalChain> releationChain, List<Long> userIds);

	Long countByCreated(Long userId);


}
