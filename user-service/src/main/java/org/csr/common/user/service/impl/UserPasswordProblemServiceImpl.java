package org.csr.common.user.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserPasswordProblemDao;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserPasswordProblem;
import org.csr.common.user.service.UserPasswordProblemService;
import org.csr.common.user.service.UserService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserPasswordProblem.java <br/>
 * Date:     Fri Mar 20 20:33:20 CST 2015
 * @author   n-caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("userPasswordProblemService")
public class UserPasswordProblemServiceImpl extends SimpleBasisService<UserPasswordProblem, Long> implements UserPasswordProblemService {
    @Resource
    private UserPasswordProblemDao userPasswordProblemDao;
    @Resource
    private UserService userService;
    
    @Override
	public BaseDao<UserPasswordProblem, Long> getDao() {
		return userPasswordProblemDao;
	}

	@Override
	public void save(UserPasswordProblem userPasswordProblem){
		//如果需要判断这里更改
		userPasswordProblemDao.save(userPasswordProblem);
	}
	@Override
	public void savePasswordProblem(Long userId, String question) {
		if(ObjUtil.isBlank(question)){
			Exceptions.service("", "必须填写提示问题");
		}
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "必须选择用户");
		}
		User user = userService.findById(userId);
		if(ObjUtil.isEmpty(user)){
			Exceptions.service("", "选择用户不存在");
		}
		if(checkNameIsExist(null, userId, question)){
			Exceptions.service("", "提示问题已经存在");
		}
		UserPasswordProblem userPasswordProblem=new UserPasswordProblem();
		userPasswordProblem.setUser(user);
		userPasswordProblem.setQuestion(question);
		userPasswordProblemDao.save(userPasswordProblem);
	}
	@Override
	public void update(UserPasswordProblem userPasswordProblem){
	
		//如果需要判断这里更改
		UserPasswordProblem olduserPasswordProblem = userPasswordProblemDao.findById(userPasswordProblem.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		olduserPasswordProblem.setId(userPasswordProblem.getId());
		olduserPasswordProblem.setUser(userPasswordProblem.getUser());
		olduserPasswordProblem.setQuestion(userPasswordProblem.getQuestion());
		olduserPasswordProblem.setAnswer(userPasswordProblem.getAnswer());
		olduserPasswordProblem.setStatus(userPasswordProblem.getStatus());

		userPasswordProblemDao.update(olduserPasswordProblem);
	}
	
	@Override
	public boolean checkNameIsExist(Long id,Long userId, String question) {
		User user = new User();
		user.setId(userId);
		UserPasswordProblem userPasswordProblem = userPasswordProblemDao.existParam(new AndEqParam("user",user),new AndEqParam("question",question));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(userPasswordProblem) || userPasswordProblem.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(userPasswordProblem)) {
		    return false;
		}
		return true;
	}

	@Override
	public List<UserPasswordProblem> findByUserId(Long userId) {
		User user = new User();
		user.setId(userId);
		return userPasswordProblemDao.findByParam(new AndEqParam("user",user));
	}

	@Override
	public void updateUserAndPasswordProblem(Long userId, String oldPassword,String password, Long problemId, 
			String problemName, String answer) {
		if(ObjUtil.isNotBlank(password)){
			userService.updateSetNewPassword(userId,password);
		}
		if(ObjUtil.isNotEmpty(problemId)){
			if(ObjUtil.isBlank(answer)){
				Exceptions.service("", "回答不能为空");
			}
			UserPasswordProblem userPasswordProblem = userPasswordProblemDao.findById(problemId);
			if(ObjUtil.isEmpty(userPasswordProblem)){
				Exceptions.service("", "提示问题不存在");
			}
			//需要把其他置为不可用
			userPasswordProblemDao.setNoByUserId(userId);
			//设置为可用
			userPasswordProblemDao.updatePasswordProblem(problemId,answer,YesorNo.YES);
		}
		if(ObjUtil.isNotBlank(problemName)){
			if(ObjUtil.isBlank(answer)){
				Exceptions.service("", "回答不能为空");
			}
			//需要把其他置为不可用
			userPasswordProblemDao.setNoByUserId(userId);
			UserPasswordProblem upp=new UserPasswordProblem();
			upp.setUser(new User(userId));
			upp.setQuestion(problemName);
			upp.setAnswer(answer);
			upp.setStatus(YesorNo.YES);
			userPasswordProblemDao.save(upp);
		}
	}

	@Override
	public UserPasswordProblem findByUserIdAndStatus(Long userId, Byte yes) {
		return userPasswordProblemDao.findByUserIdAndStatus(userId,yes);
	}

	@Override
	public UserPasswordProblem findMapByUserLoginName(String userLoginName) {
		return userPasswordProblemDao.findMapByUserLoginName(userLoginName);
	}



}
