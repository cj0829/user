package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.constant.ParameterType;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.bean.OrganizationParameterResult;
import org.csr.core.persistence.business.domain.Parameters;
import org.csr.core.persistence.business.service.OrganizationParameterService;
import org.csr.core.persistence.business.service.ParameterService;
import org.csr.core.userdetails.UserSessionContext;
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
 * ClassName:OrganizationParameterAction.java <br/>
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
@RequestMapping(value = "/organizationParameter")
public class OrganizationParameterAction extends BasisAction{
	final String preList="common/organizationParameter/organizationParameterList";
	final String preAdd="common/organizationParameter/organizationParameterAdd";
	final String preUserParameterUpdate="common/organizationParameter/organizationParameterUpdate";
	@Resource
	private OrganizationParameterService organizationParameterService;
	@Resource
	private ParameterService parameterService;

	/**
	 * 
	 * @description:进入用户类型的机构参数页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model) {
		return preList;
	}

	/**
	 * @description:查询机构参数列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/list", method = RequestMethod.POST)
	public ModelAndView list() {
		Byte[] types = new Byte[] { ParameterType.USER };
		PagedInfo<OrganizationParameterResult> result = organizationParameterService.findListPageForOrgId(page,UserSessionContext.getUserSession().getPrimaryOrgId(),types);
		return resultExcludeJson(result);
	}

	/**
	 * 进入机构参数添加页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "preAdd",method = RequestMethod.GET)
    public String preAdd(ModelMap model) {
		model.addAttribute("parameterType",ParameterType.USER);
		return preAdd;
    }
	
	/**
	 * 保存添加机构参数，其实就是添加参数。
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "ajax/add",method = RequestMethod.POST)
    public ModelAndView add(Parameters parameter) {
		if (!parameterService.save(parameter)) {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("parameterNameIsExist"));
		}
		return successMsgJson("");
    }
	
	/**
	 * 
	 * @description:进入机构参数编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preUserParameterUpdate", method = RequestMethod.GET)
	public String preUserParameterUpdate(ModelMap model,@RequestParam("parameterId")Long parameterId) {
		OrganizationParameterResult parameter = organizationParameterService.findByParameterForRoot(UserSessionContext.getUserSession().getPrimaryOrgId(), parameterId);
		model.addAttribute("parameter", parameter);
		return preUserParameterUpdate;
	}
	/**
	 * @description:保存修改机构参数信息
	 * @param:
	 * @return: String3
	 */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam("organizationId")Long organizationId,@RequestParam("parameterId")Long parameterId,@RequestParam("value")String value) {
		organizationParameterService.update(organizationId, parameterId, value);
		return successMsgJson("");
	}

	/**
	 * 删除机构参数。
	 * @param parameterIds
	 * @return
	 */
	@RequestMapping(value = "ajax/delete",method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("parameterIds")Long[] parameterIds) {
		if (parameterIds != null && parameterIds.length > 0) {
		    parameterService.deleteSimple(parameterIds);
		} else {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		return successMsgJson("");
    }
	
}
