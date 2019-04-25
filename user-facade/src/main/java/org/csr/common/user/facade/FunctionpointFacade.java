package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.core.persistence.service.BasisFacade;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.security.resource.SecurityService;
import org.csr.core.userdetails.UserSessionBasics;

public interface FunctionpointFacade extends BasisFacade<FunctionPointNode, Long>, SecurityService {

	List<FunctionPointNode> findAllCanBeAuthorized();

	List<FunctionPointNode> findCanBeAuthorizedWrapStructure(boolean isType);

	void save(FunctionPoint function, Long parentId);

	void update(FunctionPoint function, Long parentId);

	void deleteByIds(String deleteIds);

	FunctionPointNode checkFunctionPointNameOnly(Long parentId, String name);

	FunctionPointNode checkFunctionPointCodeOnly(String code);

	Object findIdsByRoleId(Long roleId);

	List<FunctionPointNode> findByRoleIdWrapStructure(Long roleId,boolean isType);

	List<FunctionPointNode> findAllByRoleIdWrapStructure(Long roleId,boolean isType);

	List<SecurityResourceBean> findResourcesByUser(UserSessionBasics userSession);

	List<FunctionPointNode> findUnAuthorizeByUserIdWrapCompleteStructure(Long userId, Long orgId, boolean isType);

	List<FunctionPoint> findAuthorizeByUserId(Long userId);

	List<FunctionPoint> wrapStructure1(List<FunctionPoint> list, boolean b);

	

}
