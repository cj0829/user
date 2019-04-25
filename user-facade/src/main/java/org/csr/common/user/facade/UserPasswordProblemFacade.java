package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.entity.UserPasswordProblemBean;
import org.csr.core.persistence.service.BasisFacade;

public interface UserPasswordProblemFacade extends BasisFacade<UserPasswordProblemBean, Long>{

	void updateUserAndPasswordProblem(Long userId, String oldPassword,String password, Long problemId, String problemName, String answer);

	Object findByUserIdAndStatus(Long userId, Byte yes);
	
	UserPasswordProblemBean findMapByUserLoginName(String userLoginName);

	boolean checkNameIsExist(Long id, Long userId, String name);

	void savePasswordProblem(Long userId, String question);

	void deleteSimple(Long userPasswordProblemId);

	List<UserPasswordProblemBean> findUserPreferencesList(Long userId);


	
}
