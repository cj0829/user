package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.MenuFacade;
import org.csr.core.SecurityResource;
import org.csr.core.UserSession;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
 
/**
 * ClassName:MenuAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/header")
public class UserHeaderMenuAction extends BasisAction{
	
	/**考试头*/
	final String userMenu="common/header/userMenu";
	@Resource
	private FunctionpointFacade functionpointFacade;
	@Resource
	MenuFacade menuFacade;
	
	public List<SecurityResource> isExistSecurityResource(UserSession userSession) {
		//如果当前的权限已经存在，则就不作查询了。
		if(ObjUtil.isNotEmpty(userSession.getResources())){
			return userSession.getResources();
		}
		//验证是否为默认权限
		@SuppressWarnings("unchecked")
		List<SecurityResource> defaults=(List<SecurityResource>) functionpointFacade.findResourcesByDefault();
		@SuppressWarnings("unchecked")
		List<SecurityResource> securityResources=(List<SecurityResource>) functionpointFacade.findResourcesByAnonymous(userSession);
		securityResources.addAll(defaults);
		return  securityResources;
		
	}
	
}
