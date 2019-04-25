package org.csr.common.user.action;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.csr.common.user.facade.UserFacade;
import org.csr.core.Constants;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
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
 * ClassName:PersonAction.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value="/person")
public class PersonAction extends BasisAction{
	final String preUpdatePassword="common/person/updatePassword";
	final String preFindSkin="common/person/findSkin";
	
	
	@Resource
	private UserFacade userFacade;

	/**
	 * 退出登录
	 */
	@RequestMapping(value="logout",method=RequestMethod.GET)
	public String logout(){
		// 清除session
		HttpSession session=BasisAction.getRequest().getSession(false);
		if(ObjUtil.isNotEmpty(UserSessionContext.getUserSession())){
			session.invalidate();
		}
		return "redirect:"+PropertiesUtil.getConfigureValue("logout");
	}

	/**
	 * 修改风格
	 */
	@RequestMapping(value="ajax/updateMenuStyle",method=RequestMethod.POST)
	public ModelAndView updateMenuStyle(@RequestParam("menuStyle") String menuStyle){
		userFacade.updateMenuStyle(menuStyle);
		return successMsgJson("");
	}

	/**
	 * 进入修改密码页面
	 */
	@RequestMapping(value="preUpdatePassword",method=RequestMethod.GET)
	public String preUpdatePassword(){
		return preUpdatePassword;
	}

	/**
	 * 修改密码
	 */
	@RequestMapping(value="ajax/updatePassword",method=RequestMethod.POST)
	public ModelAndView updatePassword(@RequestParam("oldPassword")String oldPassword,@RequestParam("newPassword")String newPassword){
		userFacade.updatePassword(UserSessionContext.getUserSession().getUserId(),oldPassword,newPassword);
		return successMsgJson("");
	}

	/**
	 * 获取用户的风格
	 */
	@RequestMapping(value="preFindSkin",method=RequestMethod.GET)
	public String preFindSkin(ModelMap model){
		Cookie[] cookies=BasisAction.getRequest().getCookies();
		model.addAttribute("code","blue");
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if(cookie.getName().startsWith(Constants.USER_COOKIE_KEY)){
					model.addAttribute("code",cookie.getValue());
					break;
				}
			}
		}
		return preFindSkin;
	}

	
	/**
	 * findSetMenu: 描述方法的作用 <br/>
	 * 
	 * @author caijin
	 * @param model
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/findSetMenu")
	public ModelAndView findSetMenu(HttpServletResponse response,ModelMap model,@RequestParam("menuId")Long menuId,@RequestParam("show")boolean show){
		UserSessionBasics userSession = (UserSessionBasics) UserSessionContext.getUserSession();
		userSession.addMenuShow(menuId,show);
		return successMsgJson("");
	}
	
	
}

