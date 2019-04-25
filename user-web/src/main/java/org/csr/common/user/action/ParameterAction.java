package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.DictionaryUtil;
import org.csr.core.persistence.business.domain.Parameters;
import org.csr.core.persistence.business.service.ParameterService;
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
 * ClassName:ParameterAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/parameter")
public class ParameterAction extends BasisAction {
	
	final String preList="common/parameter/parameterList";
	final String preInfo="common/parameter/parameterInfo";
	final String preAdd="common/parameter/parameterAdd";
	final String preUpdate="common/parameter/parameterUpdate";
    @Resource
    private ParameterService parameterService;
    /**
     * @description:进入参数管理页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preList",method = RequestMethod.GET)
    public String preList(ModelMap model) {
    	model.addAttribute("parameterType", DictionaryUtil.getDictToJson("parameterType"));
		return preList;
    }

    /**
     * @description:查询参数列表
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/list",method = RequestMethod.POST)
    public ModelAndView list() {
    	PagedInfo<Parameters> result = parameterService.findAllPage(page);
		return resultExcludeJson(result);
    }

    /**
     * @description:查看详细信息
     */
    @RequestMapping(value = "preInfo",method = RequestMethod.GET)
    public String preInfo(ModelMap model,@RequestParam("id")Long id) {
    	model.addAttribute("parameterType", DictionaryUtil.getDictToJson("parameterType"));
		model.addAttribute("parameter", parameterService.findById(id));
		return preInfo;
    }

    /**
     * @description:进入参数添加页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAdd",method = RequestMethod.GET)
    public String preAdd(ModelMap model) {
		model.addAttribute("parameterType", DictionaryUtil.getDictToJson("parameterType"));
		return preAdd;
    }

    /**
     * @description:保存新增参数信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/add",method = RequestMethod.POST)
    public ModelAndView add(Parameters parameter) {
		if (!parameterService.save(parameter)) {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("parameterNameIsExist"));
		}
		return successMsgJson("");
    }

    /**
     * @description:进入参数编辑页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preUpdate",method = RequestMethod.GET)
    public String preUpdate(ModelMap model,@RequestParam("id")Long id) {
    	model.addAttribute("parameterType", DictionaryUtil.getDictToJson("parameterType"));
    	model.addAttribute("parameter", parameterService.findById(id));
		return preUpdate;
    }

    /**
     * @description:保存修改参数信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    public ModelAndView update(Parameters parameter) {
    	Parameters parameter2 = parameterService.findById(parameter.getId());
		parameter2.setName(parameter.getName());
		parameter2.setParameterType(parameter.getParameterType());
		parameter2.setParameterValue(parameter.getParameterValue());
		parameter2.setRemark(parameter.getRemark());
		parameterService.update(parameter2);
		return successMsgJson("");
    }

    /**
     * @description:删除参数信息
     * @param:
     * @return: String
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

    /**
     * @description:检测添加参数名是否存在
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/findAddParameterName",method = RequestMethod.POST)
    public ModelAndView findAddParameterName(@RequestParam("name")String name) {
		if (parameterService.checkAddParameterName(name)) {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("parameterNameIsExist"));
		}
		return successMsgJson("");
    }

    /**
     * @description:检测修改参数名是否存在
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/findUpdateParameterName",method = RequestMethod.POST)
    public ModelAndView findUpdateParameterName(@RequestParam("id")Long id,@RequestParam("name")String name) {
		if (parameterService.checkUpdateParameterName(id,name)) {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("parameterNameIsExist"));
		}
		return successMsgJson("");
    }
    
    /**
     * 查询名称是否唯一
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(Long id,String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (parameterService.checkNameIsExist(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
    
}
