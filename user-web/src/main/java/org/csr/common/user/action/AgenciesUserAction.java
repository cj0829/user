package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.facade.AgenciesUserFacade;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:AgenciesUser.java <br/>
 * Date:     Wed Oct 11 19:54:07 CST 2017
 * @author   summy <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  action
 */
@Controller
@Scope("prototype")
@RequestMapping(value="/agenciesUser")
public class AgenciesUserAction extends BasisAction{

	final String preList="user/agenciesUser/agenciesUserList";
	final String preInfo="user/agenciesUser/agenciesUserInfo";
	final String preAdd="user/agenciesUser/agenciesUserAdd";
	final String preUpdate="user/agenciesUser/agenciesUserUpdate";
	@Resource
	private AgenciesUserFacade agenciesUserFacade;

	
	/**
	 * 这里的查询，只是在，组织机构里面使用。<br>
	 * 也必须根据当前用户的权限来查询。
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/updateAll", method = RequestMethod.POST)
	public ModelAndView updateAll(Long agenciesId) {
		int siaze = agenciesUserFacade.updateAllByAgenciesId(agenciesId);
		return successMsgJson("",siaze);
	}
}
