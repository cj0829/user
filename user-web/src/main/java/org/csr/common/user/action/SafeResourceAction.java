package org.csr.common.user.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.SafeResourceTipType;
import org.csr.common.user.entity.SafeResourceBean;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:SafeResource.java <br/>
 * Date:     Wed Dec 02 11:54:01 CST 2015
 * @author   liurui-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  action
 */
 
@Controller
@Scope("prototype")
@RequestMapping(value="/safeResource")
public class SafeResourceAction extends BasisAction{

	protected String preList="common/safeResource/safeResourceList";
	protected String preNotAuthorizedUserList="common/safeResource/safeResourceNotAuthorizedUserList";
	protected String preAuthorizedUserList="common/safeResource/safeResourceAuthorizedUserList";
	@Resource
	private AgenciesFacade agenciesFacade;
	@Resource
	private SafeResourceFacade safeResourceFacade;
	@Resource
	private UserFacade userFacade;
	
	/**
	 * 
	 * preList: 资源列表页 <br/>
	 * @author liurui
	 * @param model
	 * @param safeResourceCollectionId
	 * @param type
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preList",method=RequestMethod.GET)
	public String preList(ModelMap model,@RequestParam(value="safeResourceCollectionId")Long safeResourceCollectionId){
		List<SafeResourceBean> srs=safeResourceFacade.findByCollectionId(safeResourceCollectionId, ObjUtil.toList(SafeResourceTipType.ORGANIZATION));
		List<Long> tipIds=PersistableUtil.arrayTransforList(srs, new ToValue<SafeResourceBean, Long>(){
			@Override
			public Long getValue(SafeResourceBean doMain) {
				return doMain.getTipId();
			}
			
		});
		if(ObjUtil.isNotEmpty(tipIds)){
			List<Long> list=new ArrayList<Long>(tipIds);
			model.addAttribute("tipId",list.get(0));
		}
		model.addAttribute("safeResourceCollectionId",safeResourceCollectionId);
		return preList;
	}

	/**
	 * @author  liurui-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/list",method=RequestMethod.POST)
	public ModelAndView list(){
		PagedInfo<SafeResourceBean> result=safeResourceFacade.findAllPage(page);
		return resultExcludeJson(result);
	}
	/**
	 * 
	 * preNotAuthorizedUserList: 没有被授予当前资源权限的用户 <br/>
	 * @author liurui
	 * @param model
	 * @param safeResourceCollectionId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preNotAuthorizedUserList",method=RequestMethod.GET)
	public String preNotAuthorizedUserList(ModelMap model,@RequestParam(value="safeResourceCollectionId")Long safeResourceCollectionId){
		model.addAttribute("safeResourceCollectionId",safeResourceCollectionId);
		return preNotAuthorizedUserList;
	}
	/**
	 * 
	 * notAuthorizedUserList: 指定资源权限没被授予的用户 <br/>
	 * @author liurui
	 * @param safeResourceCollectionId
	 * @param loginName
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/notAuthorizedUserList",method=RequestMethod.POST)
	public ModelAndView notAuthorizedUserList(@RequestParam("safeResourceCollectionId")Long safeResourceCollectionId,
    		@RequestParam(value="loginName",required=false)String loginName,
    		@RequestParam(value="name",required=false)String name){
		PagedInfo<UserBean>  pages=userFacade.findUnJoinUserByCollectionId(page, safeResourceCollectionId,loginName,name,null);
		return resultExcludeJson(pages);
	}
	/**
	 * 
	 * preAuthorizedUserList: 被授予了当前资源权限的用户 <br/>
	 * @author liurui
	 * @param model
	 * @param safeResourceCollectionId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preAuthorizedUserList",method=RequestMethod.GET)
	public String preAuthorizedUserList(ModelMap model,@RequestParam("safeResourceCollectionId")Long safeResourceCollectionId){
		model.addAttribute("safeResourceCollectionId",safeResourceCollectionId);
		return preAuthorizedUserList;
	}
	/**
	 * 
	 * authorizedUserList: 指定资源权限被授予的用户 <br/>
	 * @author liurui
	 * @param safeResourceCollectionId
	 * @param loginName
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/authorizedUserList",method=RequestMethod.POST)
	public ModelAndView authorizedUserList(@RequestParam("safeResourceCollectionId")Long safeResourceCollectionId,
			@RequestParam(value="loginName",required=false)String loginName,
			@RequestParam(value="name",required=false)String name){
		PagedInfo<UserBean>  pages=userFacade.findJoinUserByCollectionId(page, safeResourceCollectionId,loginName,name,null);
		return resultExcludeJson(pages);
	}
	
//	/**
//	 * 
//	 * addSafeResource: 保存分配的资源 <br/>
//	 * @author liurui
//	 * @param orgId
//	 * @param categoryIds
//	 * @param resourceIds
//	 * @param safeResourceCollection
//	 * @return
//	 * @since JDK 1.7
//	 */
//	@RequestMapping(value="ajax/addSafeResource",method=RequestMethod.POST)
//	public ModelAndView addSafeResource(Long[] orgIds,Long[] categoryIds,Long safeResourceCollectionId){
//		agenciesFacade.saveSafeResource(safeResourceCollectionId,ObjUtil.toList(orgIds), ObjUtil.toList(categoryIds));
//		return successMsgJson("");
//	}
}
