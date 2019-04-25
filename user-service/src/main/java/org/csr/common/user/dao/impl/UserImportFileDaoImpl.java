package org.csr.common.user.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.csr.common.flow.domain.TaskNodeApprovalChain;
import org.csr.common.user.dao.UserImportFileDao;
import org.csr.common.user.domain.UserImportFile;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:UserImportFile.java <br/>
 * Date: Thu Aug 27 18:00:34 CST 2015 <br/>
 * 
 * @author huayj-PC <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 用户管理文件导入 dao实现
 */
@Repository("userImportFileDao")
public class UserImportFileDaoImpl extends JpaDao<UserImportFile, Long> implements UserImportFileDao {

	@Override
	public Class<UserImportFile> entityClass() {
		return UserImportFile.class;
	}

	@Override
	public PagedInfo<UserImportFile> findPageByUserIdsAndChainIds(Page page,
			List<TaskNodeApprovalChain> taskNodeApprovalChainList, List<Long> userIds) {
		
		if (ObjUtil.isEmpty(userIds) && ObjUtil.isEmpty(taskNodeApprovalChainList)) {
			return createPagedInfo(0, page, new ArrayList<UserImportFile>(0));
		}
		Finder finder = FinderImpl.create("select u from UserImportFile u where (");
		
		if (ObjUtil.isNotEmpty(taskNodeApprovalChainList)) {
			finder.append("(");
			boolean first = false;
			for (int i = 0; i < taskNodeApprovalChainList.size(); i++) {
				TaskNodeApprovalChain chain = taskNodeApprovalChainList.get(i);
				if (first) {
					finder.append(" or ");
				}
				Long orgTaskTemp=chain.getTaskNode().getTaskTemp().getOrgId();
				System.out.println(orgTaskTemp+" " +chain.getTaskNode().getTaskTemp().getId()+"   "+chain.getTaskNode().getTaskTemp().getName());
				finder.append("(u.orgId ="+orgTaskTemp+" and u.upLoadUserId in (" + getUserLevel(finder, i, chain) + ")", "userId_" + i + "",UserSessionContext.getUserSession().getUserId()).append(")");
				first = true;
			}
			finder.append(")");

		}
		if (ObjUtil.isNotEmpty(userIds)) {
			for (Long userid : userIds) {
				if(userid.equals(UserSessionContext.getUserSession().getUserId())){
					finder.append(" or 1=1");
					break;
				}
			}
		}
		finder.append(")");
		return findPage(page, finder);
	}

	private String getUserLevel(Finder finder, int i, TaskNodeApprovalChain taskNodeApprovalChain) {
		StringBuffer qhl = new StringBuffer();
		if (TaskNodeApprovalChain.Self.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append(":userId_" + i);
//			qhl.append(UserSessionContext.getUserSession().getUserId());
		} else if (TaskNodeApprovalChain.Manager.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id=:userId_" + i);
//			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id="+UserSessionContext.getUserSession().getUserId());
		} else if (TaskNodeApprovalChain.Manager2.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id in(");
			qhl.append("select ul2.id from User ul2 where ul2.managerUser.id=:userId_" + i + ")");
//			qhl.append("select ul2.id from User ul2 where ul2.managerUser.id="+UserSessionContext.getUserSession().getUserId() + ")");
		} else if (TaskNodeApprovalChain.Manager3.equals(taskNodeApprovalChain.getApproverReleation())) {
			qhl.append("select ul1.id from User ul1 where ul1.managerUser.id in(");
			qhl.append("select ul2.id from User ul2 where ul2.managerUser.id in(");
			qhl.append("select ul3.id from User ul3 where ul3.managerUser.id=:userId_" + i + "))");
//			qhl.append("select ul3.id from User ul3 where ul3.managerUser.id="+UserSessionContext.getUserSession().getUserId() + "))");
		}
		return qhl.toString();
	}

	@Override
	public Long countByCreated(Long userId) {
		Finder finder=FinderImpl.create("select sum(u.userUnPassTotal) from UserImportFile u where u.upLoadUser.id=:userId");
		finder.setParam("userId",userId);
		return this.countOriginalHql(finder);
	}

}
