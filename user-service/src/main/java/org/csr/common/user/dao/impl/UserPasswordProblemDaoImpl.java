package org.csr.common.user.dao.impl;


import java.util.List;

import org.csr.common.user.dao.UserPasswordProblemDao;
import org.csr.common.user.domain.UserPasswordProblem;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;


/**
 * ClassName:UserPasswordProblem.java <br/>
 * Date: Fri Mar 20 20:33:20 CST 2015 <br/>
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("userPasswordProblemDao")
public class UserPasswordProblemDaoImpl extends JpaDao<UserPasswordProblem,Long> implements UserPasswordProblemDao{

	@Override
	public Class<UserPasswordProblem> entityClass(){
		return UserPasswordProblem.class;
	}

	@Override
	public void setNoByUserId(Long userId) {
		Finder finder = FinderImpl.create("update UserPasswordProblem up set up.status=:status where up.user.id=:userId");
		finder.setParam("status", YesorNo.NO);
		finder.setParam("userId", userId);
		batchHandle(finder);
	}
	@Override 
	public UserPasswordProblem findByUserIdAndStatus(Long userId,Byte status){
		Finder finder = FinderImpl.create("select up from UserPasswordProblem up where up.status=:status and up.user.id=:userId");
		finder.setParam("status", status);
		finder.setParam("userId", userId);
		List<UserPasswordProblem> list=find(finder);
		if(ObjUtil.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override 
	public UserPasswordProblem findMapByUserLoginName(String userLoginName){
		Finder finder = FinderImpl.create("select up from UserPasswordProblem up where up.user.loginName=:userLoginName ");
		finder.setParam("userLoginName", userLoginName);
		List<UserPasswordProblem> list=find(finder);
		if(ObjUtil.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public void updatePasswordProblem(Long id, String answer, Byte status) {
		Finder finder = FinderImpl.create("update UserPasswordProblem up set up.status=:status,up.answer=:answer where up.id=:id");
		finder.setParam("answer", answer);
		finder.setParam("status", status);
		finder.setParam("id", id);
		batchHandle(finder);
	}

}
