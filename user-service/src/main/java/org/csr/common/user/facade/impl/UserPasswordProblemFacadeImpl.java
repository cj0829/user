package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserPasswordProblemDao;
import org.csr.common.user.domain.UserPasswordProblem;
import org.csr.common.user.entity.UserPasswordProblemBean;
import org.csr.common.user.facade.UserInitData;
import org.csr.common.user.facade.UserPasswordProblemFacade;
import org.csr.common.user.service.UserPasswordProblemService;
import org.csr.common.user.service.UserService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.web.bean.VOBase;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("userPasswordProblemFacade")
@Lazy(false)
public class UserPasswordProblemFacadeImpl extends SimpleBasisFacade<UserPasswordProblemBean, UserPasswordProblem, Long> implements
		UserPasswordProblemFacade,UserInitData {

	@Resource
	private UserPasswordProblemService userPasswordProblemService;
	@Resource
	private UserPasswordProblemDao userPasswordProblemDao;
	@Resource
	private UserService userService;
	
	@Override
	public UserPasswordProblemBean findMapByUserLoginName(String userLoginName) {
		UserPasswordProblem passwordProblem = userPasswordProblemService.findMapByUserLoginName(userLoginName);
		return wrapBean(passwordProblem);
	}

	@Override
	public UserPasswordProblemBean wrapBean(UserPasswordProblem doMain) {
		return UserPasswordProblemBean.wrapBean(doMain);
	}

//	@Override
//	public UserPasswordProblem wrapDomain(UserPasswordProblemBean bean) {
//		UserPasswordProblem doMain = new UserPasswordProblem();
//		doMain.setId(bean.getId());
//		doMain.setUser(userService.findById(bean.getUserId()));
//		doMain.setQuestion(bean.getQuestion());
//		doMain.setAnswer(bean.getAnswer());
//		doMain.setStatus(bean.getStatus());
//		return doMain;
//	}

	@Override
	public void updateUserAndPasswordProblem(Long userId, String oldPassword,
			String password, Long problemId, String problemName, String answer) {
		userPasswordProblemService.updateUserAndPasswordProblem(userId, oldPassword, password, problemId, problemName, answer);
		
	}

	@Override
	public Object findByUserIdAndStatus(Long userId, Byte yes) {
		return userPasswordProblemService.findByUserIdAndStatus(userId, yes);
		 
	}

	@Override
	public boolean checkNameIsExist(Long id, Long userId, String name) {
		return userPasswordProblemService.checkNameIsExist(id, userId, name);
		
		 
	}

	@Override
	public void savePasswordProblem(Long userId, String question) {
		userPasswordProblemService.savePasswordProblem(userId, question);
		
	}

	@Override
	public List<UserPasswordProblemBean> findUserPreferencesList(Long userId) {
		List<UserPasswordProblem> result = userPasswordProblemService.findByUserId(userId);
		List<UserPasswordProblemBean> ListBean = PersistableUtil.toListBeans(result, new SetBean<UserPasswordProblem>() {
					@Override
					public VOBase<Long> setValue(UserPasswordProblem doMain) {
						return UserPasswordProblemBean.wrapBean(doMain);
					}
				});
		return ListBean;
	}

	@Override
	public BaseDao<UserPasswordProblem, Long> getDao() {
		return userPasswordProblemDao;
	}

	@Override
	public void delInitData(Long userId) {
		userPasswordProblemDao.deleteByParams(new AndEqParam("user.id",userId));
	}

}
