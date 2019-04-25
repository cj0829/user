package org.csr.common.user.action;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.csr.common.user.entity.UserBean;
import org.csr.common.user.entity.UserPasswordProblemBean;
import org.csr.common.user.facade.UserFacade;
import org.csr.common.user.facade.UserPasswordProblemFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.mail.SendMail;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:UserPasswordProblem.java <br/>
 * Date:     Fri Mar 20 20:33:20 CST 2015
 * @author   n-caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  action
 */
 
@Controller
@Scope("prototype")
@RequestMapping(value="/userPasswordProblem")
public class UserPasswordProblemAction extends BasisAction{
	@Resource
	private UserPasswordProblemFacade userPasswordProblemFacade;

	final String gotoFindPassword="common/userPasswordProblem/gotoFindPassword";
	/**重新设置密码*/
	final String resetPassword="common/userPasswordProblem/resetPassword";
	@Resource
	private UserFacade userFacade;
	SendMail mail=new SendMail();
	@RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(@RequestParam(value="id",required=false)Long id,
    		@RequestParam(value="userId",required=false)Long userId,
    		@RequestParam(value="name",required=false)String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (userPasswordProblemFacade.checkNameIsExist(id,userId,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
	
	/**
	 * @description:找回密码
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "gotoFindPassword", method = RequestMethod.GET)
	public String gotoFindPassword() {
		return gotoFindPassword;
	}
	/**
	 * @description:找回密码
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findPassword", method = RequestMethod.POST)
	public ModelAndView findPassword(HttpServletRequest request,
			@RequestParam("userLoginName")String userLoginName,
			@RequestParam("answer")String answer,
			@RequestParam("question")String question) {
		UserPasswordProblemBean userPasswordProblem=userPasswordProblemFacade.findMapByUserLoginName(userLoginName);
		if(ObjUtil.isEmpty(userPasswordProblem) || !userPasswordProblem.getAnswer().equals(answer) || !userPasswordProblem.getQuestion().equals(question)){
			Exceptions.service("", "您的用户名或者是提示问题有错！");
		}
		if(ObjUtil.isNotBlank(userPasswordProblem.getUserEmail())){
			//发送邮件
			String uuid=UUID.randomUUID().toString();
			//设置用户修改的uuid
			userFacade.updateSetResetPassword(userPasswordProblem.getUserId(),uuid);
			StringBuffer http=new StringBuffer("http://");
			//获取ip地址
			http.append(request.getServerName());
			Integer port  = request.getServerPort();
			if(ObjUtil.isNotEmpty(port)){
				http.append(":").append(port);
			}
			
			http.append(request.getServletContext().getContextPath()).append("/userPasswordProblem/gotoResetPassword.action?uuid=").append(uuid);
			System.out.println(http.toString());
			mail.sendMail("取回密码提示", "您回答的问题正确，请您重新设置密码:"+http.toString(), userPasswordProblem.getUserEmail());
		}else{
			Exceptions.service("", "您没有设置email无法接受消息！");
		}
		
		return successMsgJson("");
	}
	
	/**
	 * @description:重置密码
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "gotoResetPassword", method = RequestMethod.GET)
	public String resetPassword(ModelMap model,@RequestParam(value="uuid",required=false)String uuid) {
		UserBean user=userFacade.findByResetPassword(uuid);
		if(ObjUtil.isEmpty(user)){
			model.addAttribute("error", "密码设置已失效");
		}else{
			model.addAttribute("userId", user.getId());
		}
		model.addAttribute("uuid", uuid);
		return resetPassword;
	}
	/**
	 * @description:重置密码
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(@RequestParam("userId")Long userId,@RequestParam("newPassword")String newPassword,@RequestParam(value="uuid",required=false)String uuid) {
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "密码设置以失效");
		}
		if(ObjUtil.isEmpty(uuid)){
			Exceptions.service("", "密码设置以失效");
		}
		UserBean user=userFacade.findByResetPassword(uuid);
		if(ObjUtil.isEmpty(user)){
			Exceptions.service("", "密码设置以失效");
		}
		if(userId.equals(user.getId()))
		userFacade.updateSetNewPassword(userId, newPassword);
		return successMsgJson("");
	}
	
}
