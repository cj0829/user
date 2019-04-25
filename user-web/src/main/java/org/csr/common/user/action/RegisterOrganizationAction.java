package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.ParameterType;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.entity.RegisterBean;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.OrganizationFacade;
import org.csr.common.user.facade.RegisterOrganizationFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.bean.OrganizationParameterResult;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.business.service.OrganizationParameterService;
import org.csr.core.persistence.business.service.ParameterService;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:RegisterOrganizationAction.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value="/registerOrganization")
public class RegisterOrganizationAction extends BasisAction{

	final String preList="common/registerOrganization/registerList";
	final String preInfo="common/registerOrganization/registerInfo";
	final String preAdd="common/registerOrganization/registerAdd";
	final String preUpdate="common/registerOrganization/registerUpdate";
	final String preListOrganizationParameter="common/registerOrganization/listOrganizationParameter";
	final String preUpdateOrganizationParameter="common/registerOrganization/updateOrganizationParameter";
	@Resource
	OrganizationFacade orgaionaryFacade;
	@Resource
	private FunctionpointFacade functionpointFacade;
	@Resource
	private OrganizationParameterService organizationParameterService;
	@Resource
	private RegisterOrganizationFacade registerOrganizationFacade;
	@Resource
	private ParameterService parameterService;

	/**
	 * preList: 进入注册机构列表页面 <br/>
	 * 
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preList",method=RequestMethod.GET)
	public String preList(){
		return preList;
	}

	/**
	 * preInfo: 查询单个机构的详细信息 <br/>
	 * 
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preInfo",method=RequestMethod.GET)
	public String preInfo(){
		return preInfo;
	}

	/**
	 * @description:获取，第一级机构列表，
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/organizationList",method=RequestMethod.POST)
	public ModelAndView organizationList(){
		PagedInfo<Organization> result=registerOrganizationFacade.findOrganizationPage(page);
		return resultExcludeJson(result);
	}

	/**
	 * @description:注册页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preAdd",method=RequestMethod.GET)
	public String preAdd(ModelMap model){
		List<FunctionPointNode> fps=(List<FunctionPointNode>) functionpointFacade.findCanBeAuthorizedWrapStructure(true);
		model.addAttribute("functionPointList",fps);
		return preAdd;
	}

	/**
	 * @description:注册组织机构
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/add",method=RequestMethod.POST)
	public ModelAndView add(RegisterBean register,Byte createOrChoose){
		UserBean user=registerOrganizationFacade.saveOrganization(register,createOrChoose);
		return successMsgJson("",user);
	}

	/**
	 * @description:编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preUpdate",method=RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam("organizationId") Long organizationId){
		List<FunctionPointNode> fps=(List<FunctionPointNode>) functionpointFacade.findCanBeAuthorizedWrapStructure(true);
		model.addAttribute("functionPointList",fps);
		model.addAttribute("register",registerOrganizationFacade.findRegisterResult(organizationId));
		return preUpdate;
	}

	/**
	 * @description:编辑注册机构
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/update",method=RequestMethod.POST)
	public ModelAndView update(RegisterBean register){
		UserBean user=registerOrganizationFacade.updateOrganization(register);
		return successMsgJson("",user);
	}

	/**
	 * resetAdminPassword: 初始化修改管理员密码 <br/>
	 * 
	 * @author caijin
	 * @param organizationId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/resetAdminPassword",method=RequestMethod.POST)
	public ModelAndView resetAdminPassword(@RequestParam("organizationId") Long organizationId){
		registerOrganizationFacade.updateAdminPassword(organizationId);
		return successMsgJson("");
	}

	/**
	 * freeze: 冻结机构 <br/>
	 * 
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/freeze",method=RequestMethod.POST)
	public ModelAndView freeze(@RequestParam("organizationId") Long organizationId){
		orgaionaryFacade.updateFreeze(organizationId);
		return successMsgJson("");
	}

	/**
	 * 激活机构 activating: 描述方法的作用 <br/>
	 * 
	 * @author caijin
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/activating",method=RequestMethod.POST)
	public ModelAndView activating(@RequestParam("organizationId") Long organizationId){
		orgaionaryFacade.updateActivating(organizationId);
		return successMsgJson("");
	}

	/**
	 * @description:删除注册机构
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/delete",method=RequestMethod.POST)
	public ModelAndView delete(@RequestParam("organizationId") Long organizationId){
		registerOrganizationFacade.delete(organizationId);
		return successMsgJson("");
	}

	// ///////////////////// 机构参数

	/**
	 * @description:进入系统类型的机构参数页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preListOrganizationParameter",method=RequestMethod.GET)
	public String preListOrganizationParameter(ModelMap model,@RequestParam("organizationId") Long organizationId){
		model.addAttribute("organizationId",organizationId);
		return preListOrganizationParameter;
	}

	/**
	 * @description:查询机构参数列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/listOrganizationParameter",method=RequestMethod.POST)
	public ModelAndView listOrganizationParameter(@RequestParam("organizationId") Long organizationId){

		Byte[] types=new Byte[]{ParameterType.SYSTEM,ParameterType.USER};
		PagedInfo<OrganizationParameterResult> result=organizationParameterService.findListPageForOrgId(page,organizationId,types);
		return resultExcludeJson(result);
	}

	/**
	 * 
	 * @description:进入机构参数编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preUpdateOrganizationParameter",method=RequestMethod.GET)
	public String preUpdateOrganizationParameter(ModelMap model,@RequestParam("organizationId") Long organizationId,@RequestParam("parameterId") Long parameterId){
		OrganizationParameterResult parameter=organizationParameterService.findByParameterForRoot(organizationId,parameterId);
		model.addAttribute("parameter",parameter);
		return preUpdateOrganizationParameter;
	}

	/**
	 * 
	 * @description:机构参数编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/updateOrganizationParameter")
	public ModelAndView updateOrganizationParameter(@RequestParam("organizationId") Long organizationId,@RequestParam("parameterId") Long parameterId,@RequestParam("value") String value) {
		organizationParameterService.update(organizationId, parameterId, value);
		return successMsgJson("");
	}
	/**
	 * @description:查询名称是否存在
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/checkUpdateName", method = RequestMethod.POST)
	public ModelAndView checkUpdateName(Long id,String name) {
		if(registerOrganizationFacade.checkUpdateName(id, name)){
			Exceptions.service("", "您的机构名称已经存在");
		}
		return successMsgJson("");
	}
}
