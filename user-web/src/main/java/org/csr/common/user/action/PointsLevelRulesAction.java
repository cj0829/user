package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.domain.PointsLevelRules;
import org.csr.common.user.entity.PointsLevelRulesBean;
import org.csr.common.user.facade.PointsLevelRulesFacade;
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
 * ClassName:PointsLevelRulesAction.java <br/>
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
@RequestMapping(value = "/pointsLevelRules")
public class PointsLevelRulesAction extends BasisAction {
    @Resource
    private PointsLevelRulesFacade pointsLevelRulesFacade;
    
    final String preList="common/pointsLevelRules/pointsLevelRulesList";
	final String preInfo="common/pointsLevelRules/pointsLevelRulesInfo";
	
	final String preAdd="common/pointsLevelRules/pointsLevelRulesAdd";
	final String preUpdate="common/pointsLevelRules/pointsLevelRulesUpdate";

    /**
     * @description:进入积分等级管理页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preList",method = RequestMethod.GET)
    public String preList() {
    	return preList;
    }

    /**
     * @description:查询积分等级列表
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/list",method = RequestMethod.POST)
    public ModelAndView list() {
    	
		 PagedInfo<PointsLevelRulesBean> result = pointsLevelRulesFacade.findAllPage(page);
		return resultExcludeJson(result);
    }

    /**
     * @description:进入积分等级添加页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAdd",method = RequestMethod.GET)
    public String preAdd() {
    	return preAdd;
    }

    /**
     * @description:保存新增积分等级信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/add",method = RequestMethod.POST)
    public ModelAndView add(PointsLevelRules pointsLevelRules) {
		pointsLevelRulesFacade.saveSimple(pointsLevelRules);
		return successMsgJson("");
    }

    /**
     * @description:进入积分等级编辑页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preUpdate",method = RequestMethod.GET)
    public String preUpdate(ModelMap model,@RequestParam("id")Long id) {
    	model.addAttribute("pointsLevelRules", pointsLevelRulesFacade.findById(id));
		return preUpdate;
    }

    /**
     * @description:保存修改积分等级信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    public ModelAndView update(PointsLevelRules pointsLevelRules) {
//		PointsLevelRulesBean pointsLevelRules2 = pointsLevelRulesFacade.findById(pointsLevelRules.getId());
//		pointsLevelRules2.setLevelName(pointsLevelRules.getLevelName());
//		pointsLevelRules2.setLevel(pointsLevelRules.getLevel());
//		pointsLevelRules2.setPoints(pointsLevelRules.getPoints());
		pointsLevelRulesFacade.updateSimple(pointsLevelRules);
		return successMsgJson("");
    }

    /**
     * @description:删除积分等级信息
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/delete",method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("pointsLevelRulesIds")Long[] pointsLevelRulesIds) {
		if (pointsLevelRulesIds != null && pointsLevelRulesIds.length > 0) {
		    pointsLevelRulesFacade.deleteSimple(pointsLevelRulesIds);
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
  	public ModelAndView checkUpdateName(Long id,String levelName) {
  		if(pointsLevelRulesFacade.checkUpdateName(id, levelName)){
  			Exceptions.service("", "您的积分等级名称已经存在");
  		}
  		return successMsgJson("");
  	}
}
