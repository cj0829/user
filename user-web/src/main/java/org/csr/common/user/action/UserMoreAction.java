package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.domain.UserMore;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.entity.UserMoreBean;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserMoreFacade;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:UserMore.java <br/>
 * Date:     Thu Dec 01 10:06:41 CST 2016
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  action
 */
 
@Controller
@Scope("prototype")
@RequestMapping(value="/userMore")
public class UserMoreAction extends BasisAction{

	final String preAdd="common/user/userMoreAdd";
	@Resource
	private UserMoreFacade userMoreFacade;
	@Resource
	private UserFacade userFacade;


	/**
	 * @author  liurui
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preAdd",method=RequestMethod.GET)
	public String preAdd(Long userId){
		UserBean userBean = userFacade.findById(userId);
		UserMoreBean more=userMoreFacade.findByUserId(userId);
		if(ObjUtil.isNotEmpty(more)){
			more.setEmail(userBean.getEmail());
		}
		setRequest("userMore",more);
		setRequest("userBean", userBean.getName());
		setRequest("userId", userId);
		return preAdd;
	}


	/**修改或创建用户
	 * @author  liurui
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/update",method=RequestMethod.POST)
	public ModelAndView updateOrCreateUser(UserMore userMore,Long userId){
		userMoreFacade.updateOrCreateUser(userMore,userId);
		return successMsgJson("");
	}
	
}
