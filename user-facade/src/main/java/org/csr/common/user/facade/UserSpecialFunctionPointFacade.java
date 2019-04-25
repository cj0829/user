/**
 * Project Name:user-facade
 * File Name:UserSpecialFunctionPointFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午3:03:06
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.entity.UserSpecialFunctionPointNode;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:UserSpecialFunctionPointFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午3:03:06 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface UserSpecialFunctionPointFacade extends BasisFacade<UserSpecialFunctionPointNode, Long>{

	void saveUserSpecialFunctionPoint(Long userId, Long[] functionPointIds);

	void deleteUserSpecialFunctionPoint(Long userId, Long[] functionPointIds);

	Object findExistEnableUserSpecialFunctionPoint(Long userId, Long root);


	Object wrapCompleteStructure(Object findExistEnableUserSpecialFunctionPoint, boolean b);

	List<FunctionPointNode> findExistEnableUserSpecialFunctionPointWrapCompleteStructure(Long userId, Long orgId, boolean isType);
	
	List<FunctionPointNode> findExistDisableUserSpecialFunctionPointWrapCompleteStructure(Long userId, Long orgId, boolean isType);
	
	void delExistEnableUserSpecialFunctionPoint(List<Long> asList);

	void delExistDisableUserSpecialFunctionPoint(List<Long> asList);

}

