package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.entity.UserSafeResourceCollectionBean;
import org.csr.common.user.facade.UserSafeResourceCollectionFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
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
 * ClassName:UserSafeResourceCollection.java <br/>
 * Date:     Fri Dec 04 09:21:42 CST 2015
 * @author   liurui-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  action
 */
 
@Controller
@Scope("prototype")
@RequestMapping(value="/userSafeResourceCollection")
public class UserSafeResourceCollectionAction extends BasisAction{

	final String preList="common/userSafeResourceCollection/userSafeResourceCollectionList";
	final String preInfo="common/userSafeResourceCollection/userSafeResourceCollectionInfo";
	final String preAdd="common/userSafeResourceCollection/userSafeResourceCollectionAdd";
	final String preUpdate="common/userSafeResourceCollection/userSafeResourceCollectionUpdate";
	@Resource
	private UserSafeResourceCollectionFacade userSafeResourceCollectionFacade;

	/**
	 * 
	 * add: 保存 <br/>
	 * @author liurui
	 * @param userSafeResourceCollection
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/add",method=RequestMethod.POST)
	public ModelAndView add(UserSafeResourceCollection userSafeResourceCollection){
		userSafeResourceCollectionFacade.save(userSafeResourceCollection);;
		return successMsgJson("");
	}
	/**
	 * 
	 * batchAdd: 批量保存 <br/>
	 * @author liurui
	 * @param safeResourceCollectionIds
	 * @param userIds
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/batchAdd",method=RequestMethod.POST)
	public ModelAndView batchAdd(@RequestParam(value="safeResourceCollectionIds",required=false)Long[] safeResourceCollectionIds,
			@RequestParam(value="userIds",required=false)Long[] userIds){
		userSafeResourceCollectionFacade.batchSave(safeResourceCollectionIds,userIds);
		return successMsgJson("");
	}

	/**
	 * 
	 * deleteAuthorized: 取消授权 <br/>
	 * @author liurui
	 * @param safeResourceCollectionId
	 * @param userId
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/deleteAuthorized",method=RequestMethod.POST)
	public ModelAndView deleteAuthorized(@RequestParam(value="safeResourceCollectionId",required=false)Long safeResourceCollectionId,
			@RequestParam(value="userId",required=false)Long userId){
		userSafeResourceCollectionFacade.deleteAuthorized(safeResourceCollectionId,userId);
		return successMsgJson("");
	}
	/**
	 * @author  liurui-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preList",method=RequestMethod.GET)
	public String preList(){
		return preList;
	}

	/**
	 * @author  liurui-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/list",method=RequestMethod.POST)
	public ModelAndView list(){
		PagedInfo<UserSafeResourceCollectionBean> result=userSafeResourceCollectionFacade.findAllPage(page);
		return resultExcludeJson(result);
	}

	/**
	 * @author  liurui-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preAdd",method=RequestMethod.GET)
	public String preAdd(ModelMap model){
		return preAdd;
	}
	/**
	 * @author  liurui-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preUpdate",method=RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam(value="id",required=false)Long id){
		model.addAttribute("userSafeResourceCollection",userSafeResourceCollectionFacade.findById(id));
		return preUpdate;
	}

	/**
	 * @author  liurui-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/update",method=RequestMethod.POST)
	public ModelAndView update(UserSafeResourceCollection userSafeResourceCollection){
		userSafeResourceCollectionFacade.update(userSafeResourceCollection);
		return successMsgJson("");
	}
	
	@RequestMapping(value="ajax/delete",method=RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value="ids",required=false)Long[] ids){
		userSafeResourceCollectionFacade.deleteSimple(ids);
		return successMsgJson("");
	}
	
	@RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(@RequestParam(value="id",required=false)Long id,
    		@RequestParam(value="name",required=false)String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (userSafeResourceCollectionFacade.checkNameIsExist(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
}
