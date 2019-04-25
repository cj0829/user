package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.domain.RemindTemplate;
import org.csr.common.user.entity.RemindTemplateBean;
import org.csr.common.user.facade.RemindTemplateFacade;
import org.csr.core.page.PagedInfo;
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
 * ClassName:RemindTemplateAction.java <br/>
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
@RequestMapping(value = "/remindTemplate")
public class RemindTemplateAction extends BasisAction {
	
	final String preList = "common/remindTemplate/remindTemplateList";
	final String preInfo = "common/remindTemplate/remindTemplateInfo";
	final String preAdd = "common/remindTemplate/remindTemplateAdd";
	final String preUpdate = "common/remindTemplate/remindTemplateUpdate";

	 @Resource
	 private RemindTemplateFacade remindTemplateFacade;

	/**
	 * @description:进入模版管理页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preList",method = RequestMethod.GET)
	public String preList() {
		return preList;
	}

	/**
	 * @description:查询模版列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/list",method = RequestMethod.POST)
	public ModelAndView list() {
		PagedInfo<RemindTemplateBean> result = remindTemplateFacade.findAllPage(page);
		return resultExcludeJson(result);
	}

	/**
	 * @description:查看详细信息
	 */
	@RequestMapping(value = "preInfo",method = RequestMethod.GET)
	public String preInfo(ModelMap model,@RequestParam("id")Long id) {
		model.addAttribute("remindTemplate", remindTemplateFacade.findById(id));
		return preInfo;
	}

	/**
	 * @description:进入模版添加页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preAdd",method = RequestMethod.GET)
	public String preAdd() {
		return preAdd;
	}

	/**
	 * @description:保存新增模版信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/add",method = RequestMethod.POST)
	public ModelAndView add(RemindTemplate remindTemplate) {
		remindTemplateFacade.save(remindTemplate);
		return successMsgJson("");
	}

	/**
	 * @description:进入模版编辑页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preUpdate",method = RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam("id")Long id) {
		model.addAttribute("remindTemplate", remindTemplateFacade.findById(id));
		return preUpdate;
	}

	/**
	 * @description:保存修改模版信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/update",method = RequestMethod.POST)
	public ModelAndView update(RemindTemplate remindTemplate) {
		remindTemplateFacade.update(remindTemplate);
		return successMsgJson("");
	}

	/**
	 * @description:删除模版信息
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/delete")
	public ModelAndView delete(@RequestParam("remindTemplateIds")Long[] remindTemplateIds) {
		if (remindTemplateIds != null && remindTemplateIds.length > 0) {
			remindTemplateFacade.deleteSimple(remindTemplateIds);
		} else {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		return successMsgJson("");
	}

	/**
	 * @description:检测添加模版名是否存在
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findAddRemindTemplateCode",method = RequestMethod.POST)
	public ModelAndView findAddRemindTemplateCode(@RequestParam("code")String code) {
		if (remindTemplateFacade.checkAddRemindTemplateCode(code)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("remindTemplateCodeIsExist"));
		}
		return successMsgJson("");
	}

	/**
	 * @description:检测添加模版名是否存在
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findUpdateRemindTemplateCode",method = RequestMethod.POST)
	public ModelAndView findUpdateRemindTemplateCode(@RequestParam("id")Long id,@RequestParam("code")String code) {
		if (remindTemplateFacade.checkUpdateRemindTemplateCode(id,code)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("remindTemplateCodeIsExist"));
		}
		return successMsgJson("");
	}
}
