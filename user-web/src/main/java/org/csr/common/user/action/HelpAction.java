package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.domain.Help;
import org.csr.common.user.entity.HelpBean;
import org.csr.common.user.facade.HelpFacade;
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
 * ClassName:HelpAction.java <br/>
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
@RequestMapping(value = "/help")
public class HelpAction extends BasisAction {
	
	final String preAdd="common/help/helpAdd";
	final String preUpdate="common/help/helpUpdate";
    @Resource
    private HelpFacade helpFacade;

    /**
     * @description:进入帮助添加页面
     * @param:
     * @return: String
     */
	@RequestMapping(value = "preAdd", method = RequestMethod.GET)
	public String preAdd(ModelMap model,@RequestParam("functionPointCode")String functionPointCode) {
		model.addAttribute("functionPointCode", functionPointCode);
		return preAdd;
	}

    /**
     * @description:保存新增帮助信息
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/add", method = RequestMethod.POST)
	public ModelAndView add(Help help) {
		if (!helpFacade.save(help)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("helpCodeIsExist"));
		}
		return successMsgJson("");
	}

    /**
     * @description:进入帮助编辑页面
     * @param:
     * @return: String
     */
	@RequestMapping(value = "preUpdate",method = RequestMethod.GET)
    public String preUpdate(ModelMap model,@RequestParam("functionPointCode")String functionPointCode) {
		List<HelpBean> helplist = helpFacade.queryByCode(functionPointCode);
		if(helplist.size() == 1){
			HelpBean help = helplist.get(0);
		    model.addAttribute("help", help);
		}
		return preUpdate;
    }

    /**
     * @description:保存修改帮助信息
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(Help help) {
		helpFacade.update(help);
		return successMsgJson("");
	}

    /**
     * @description:删除帮助信息
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/delete",method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("functionPointCode")String functionPointCode) {
		List<HelpBean> helplist = helpFacade.queryByCode(functionPointCode);
		if(helplist.size() == 1){
			HelpBean help = (HelpBean) helplist.get(0);
			helpFacade.deleteSimple(help.getId());
		}
		return successMsgJson("");
    }
}
