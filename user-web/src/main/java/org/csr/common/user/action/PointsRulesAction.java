package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.domain.PointsRules;
import org.csr.common.user.entity.PointsRulesBean;
import org.csr.common.user.facade.PointsRulesFacade;
import org.csr.core.exception.Exceptions;
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
 * ClassName:PointsRulesAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-21下午5:27:55 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/pointsRules")
public class PointsRulesAction extends BasisAction {
	
	final String preList = "common/pointsRules/pointsRulesList";
	final String preInfo = "common/pointsRules/pointsRulesInfo";
	final String preAdd = "common/pointsRules/pointsRulesAdd";
	final String preUpdate = "common/pointsRules/pointsRulesUpdate";
	
    @Resource
    private PointsRulesFacade pointsRulesFacade;

    /**
     * @description:进入积分规则管理页面
     * @param:
     * @return: String
     */
    
    @RequestMapping(value = "preList",method = RequestMethod.GET)
    public String preList() {
    	return preList;
    }

    /**
     * @description:查询积分规则列表
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/list",method = RequestMethod.POST)
    public ModelAndView list() {
    	PagedInfo<PointsRulesBean> result = pointsRulesFacade.findAllPage(page);
		return resultExcludeJson(result);
    }

    /**
     * @description:查看详细信息
     */
    @RequestMapping(value = "preInfo",method = RequestMethod.GET)
    public String preInfo(ModelMap model,@RequestParam("id")Long id) {
    	model.addAttribute("pointsRules", pointsRulesFacade.findById(id));
		return preInfo;
    }

    /**
     * @description:进入积分规则添加页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAdd",method = RequestMethod.GET)
    public String preAdd() {
    	return preAdd;
    }

    /**
     * @description:保存新增积分规则信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/add",method = RequestMethod.POST)
    public ModelAndView add(PointsRules pointsRules,@RequestParam("functionPointId")Long functionPointId) {
    	pointsRulesFacade.save(pointsRules,functionPointId);
		return successMsgJson("");
    }

    /**
     * @description:进入积分规则编辑页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preUpdate",method = RequestMethod.GET)
    public String preUpdate(ModelMap model,@RequestParam("id")Long id) {
    	model.addAttribute("pointsRules", pointsRulesFacade.findById(id));
		return preUpdate;
    }

    /**
     * @description:保存修改积分规则信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    public ModelAndView update(ModelMap model,PointsRules pointsRules,@RequestParam("functionPointId")Long functionPointId) {
		pointsRulesFacade.update(pointsRules,functionPointId);
		return successMsgJson("");
    }

    /**
     * @description:删除积分规则信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/delete",method = RequestMethod.POST)
    public ModelAndView delete(ModelMap model,@RequestParam("pointsRulesIds")Long[] pointsRulesIds) {
		if (pointsRulesIds != null && pointsRulesIds.length > 0) {
		    pointsRulesFacade.deleteSimple(pointsRulesIds);
		} else {
		    return errorMsgJson(PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		return successMsgJson("");
    }
    
    /**
	 * @description:查询用户列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/checkUpdateName", method = RequestMethod.POST)
	public ModelAndView checkUpdateName(Long id,String operation) {
		if(pointsRulesFacade.checkUpdateName(id, operation)){
			Exceptions.service("", "您的积分规则名称已经存在");
		}
		return successMsgJson("");
	}
}
