package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.entity.UserPointsLogBean;
import org.csr.common.user.facade.UserPointsLogFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:UserPointsLogAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-21下午5:28:10 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/userPointsLog")
public class UserPointsLogAction extends BasisAction {
    
	final String preList = "common/userPointsLog/userPointsLogList";

	@Resource
    private UserPointsLogFacade uerPointsLogFacade;

    /**
     * @description:进入积分日志管理页面
     * @param:
     * @return: String
     */
	@RequestMapping(value = "preList",method = RequestMethod.GET)
    public String preList() {
    	return preList;
    }

    /**
     * @description:查询积分日志列表
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/list",method = RequestMethod.POST)
    public ModelAndView list() {
		PagedInfo<UserPointsLogBean> result = uerPointsLogFacade.findAllPage(page);
		return resultExcludeJson(result);
    }

}
