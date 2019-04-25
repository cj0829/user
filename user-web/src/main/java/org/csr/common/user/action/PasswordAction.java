package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.UserFacade;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.Md5PwdEncoder;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value="/password")
public class PasswordAction extends BasisAction{
	@Resource
	private UserFacade userFacade;
	
	@RequestMapping(value="ajax/validate")
	public ModelAndView validate(String oldPass){
		UserBean userBean=userFacade.findById(UserSessionContext.getUserSession().getUserId());
		if(userBean.getPassword().equals(Md5PwdEncoder.encodePassword(oldPass))){
			return successMsgJson("");
		}
		return errorMsgJson("");
	}
	/**
	 * 
	 * saveUpdatePassword: 重置密码 <br/>
	 * @author admin
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/update")
	public ModelAndView saveUpdatePassword(String password) {
		userFacade.updateSetNewPassword(UserSessionContext.getUserSession().getUserId(),password);
		return successMsgJson("");
	}
}
