package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.domain.FlowStrategy;
import org.csr.common.user.domain.UserImportStrategy;
import org.csr.common.user.domain.UserRegisterStrategy;
import org.csr.common.user.entity.AgenciesNode;
import org.csr.common.user.entity.OrganizationNode;
import org.csr.common.user.entity.UserStrategyBean;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.OrganizationFacade;
import org.csr.common.user.facade.UserImportStrategyFacade;
import org.csr.common.user.facade.UserRegisterStrategyFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToString;
import org.csr.core.web.bean.VOBase;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:QuestionStrategy.java <br/>
 * Date: Thu Jul 02 17:32:27 CST 2015
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 用户策略 action
 */

@Controller
@Scope("prototype")
@RequestMapping(value = "/userStrategy")
public class UserStrategyAction extends BasisAction {

	final String preList = "common/userStrategy/userStrategyList";
	@Resource
	protected OrganizationFacade organizationFacade;
	@Resource
	private AgenciesFacade  agenciesFacade;
	@Resource
	private UserImportStrategyFacade userImportStrategyFacade;
	@Resource
	private UserRegisterStrategyFacade userRegisterStrategyFacade;

	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model, @RequestParam(value="id",required=false)Long id) {
		model.addAttribute("id", id);
		return preList;
	}
	/**
	 * 查询域的策略，如果域策略不存在的话，创建新的策略
	 * 
	 * @author caijin
	 * @param orgId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/ajax/findOrgPage", method = RequestMethod.POST)
	public ModelAndView findOrgPage() {
		PagedInfo<OrganizationNode> orgPage = organizationFacade.findAllPage(page);
		PagedInfo<UserStrategyBean> orgBeanPage=PersistableUtil.toPagedInfoBeans(orgPage,new SetBean<OrganizationNode>() {
			@Override
			public VOBase<Long> setValue(OrganizationNode doMain) {
				List<AgenciesNode> agenciesList=agenciesFacade.findByOrgid(doMain.getId());
				UserImportStrategy importstrategy = userImportStrategyFacade.saveFindByOrgId(doMain.getId());
				UserRegisterStrategy registerstrategy = userRegisterStrategyFacade.saveFindByOrgId(doMain.getId());
				UserStrategyBean bean = UserStrategyBean.wrapBean(doMain,importstrategy,registerstrategy);
				bean.setAgenciesString(PersistableUtil.arrayTransforString(agenciesList, new ToString<AgenciesNode>() {
					@Override
					public String getValue(AgenciesNode obj) {
						return obj.getName();
					}
					
				}));
				return bean;
			}
		});
		return resultExcludeJson(orgBeanPage);
	}

	/**
	 * 查询域的策略，如果域策略不存在的话，创建新的策略
	 * 
	 * @author caijin
	 * @param orgId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "/ajax/findStrategy", method = RequestMethod.POST)
	public ModelAndView findStrategy(@RequestParam(value="orgId",required=false)Long orgId) {
		
		
		PagedInfo<OrganizationNode> orgPage = organizationFacade.findAllPage(page);
		PagedInfo<UserStrategyBean> orgBeanPage=PersistableUtil.toPagedInfoBeans(orgPage,new SetBean<OrganizationNode>() {
			@Override
			public VOBase<Long> setValue(OrganizationNode doMain) {
				UserImportStrategy importstrategy = userImportStrategyFacade.saveFindByOrgId(doMain.getId());
				UserRegisterStrategy registerstrategy = userRegisterStrategyFacade.saveFindByOrgId(doMain.getId());
				return UserStrategyBean.wrapBean(doMain,importstrategy,registerstrategy);
			}
		});
		return resultExcludeJson(orgBeanPage);
	}

	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "/ajax/imporUpdate/updateStrategy")
	public ModelAndView imporUpdate(Long strategyId,Byte enable,Byte type) {
		FlowStrategy strategy=userImportStrategyFacade.update(strategyId,enable);
		return successMsgJson("",ObjUtil.toString(strategy.getTaskTemp().getId()));
	}
	
	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "/ajax/registered/updateStrategy", method = RequestMethod.POST)
	public ModelAndView registeredUpdate(Long strategyId,Byte enable,Byte type) {
		FlowStrategy strategy=userRegisterStrategyFacade.update(strategyId,enable);
		return successMsgJson("",ObjUtil.toString(strategy.getTaskTemp().getId()));
	}
}
