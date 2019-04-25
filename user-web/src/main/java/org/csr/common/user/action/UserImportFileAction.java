package org.csr.common.user.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.csr.common.user.entity.UserImportFileBean;
import org.csr.common.user.facade.UserImportFileFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:UserImportFile.java <br/>
 * Date:     Thu Aug 27 18:00:34 CST 2015
 * @author   huayj-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 用户管理文件导入 action
 */
 
@Controller
@Scope("prototype")
@RequestMapping(value="/userImportFile")
public class UserImportFileAction extends BasisAction{

	final String preCreatedList="common/userImportFile/createdList";
	final String preUserUnPassTotal="common/userImportFile/userUnPassTotal";
	final String preUserPassTotal="common/userImportFile/userPassTotal";
	@Resource
	private UserImportFileFacade userImportFileFacade;
	
	final String preImportUser="common/user/importUser";


	/**
	 * preImportUser:用户导入
	 * @author huayj
	 * @param model
	 * @param agenciesId
	 * @return
	 * @return String
	 * @date&time 2015-8-27 下午5:40:39
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preImportUser",method=RequestMethod.GET)
	public String preImportUser(ModelMap model,@RequestParam(value="agenciesId",required=false)Long agenciesId){
		model.addAttribute("agenciesId", agenciesId);
		return preImportUser;
	}
	/**
	 * @author  huayj-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/list",method=RequestMethod.POST)
	public ModelAndView list(){
		PagedInfo<UserImportFileBean> result=userImportFileFacade.findAllPage(page);
	
		return resultExcludeJson(result);
	}
	
	/**
	 * @author  huayj-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preCreatedList",method=RequestMethod.GET)
	public String preCreatedList(ModelMap model){
		return preCreatedList;
	}
	/**
	 * @author  huayj-PC
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/listByCreated",method=RequestMethod.POST)
	public ModelAndView listByCreated(){
		page.toParam().add(new AndEqParam("upLoadUserId", UserSessionContext.getUserSession().getUserId()));
		PagedInfo<UserImportFileBean> result=userImportFileFacade.findAllPage(page);
		
		return resultExcludeJson(result);
	}
	
	@RequestMapping(value="ajax/countByCreated",method=RequestMethod.POST)
	public ModelAndView countByCreated(){
		Long number = userImportFileFacade.countByCreated(UserSessionContext.getUserSession().getUserId());
		return successMsgJson(number.toString());
	}
	
	/**
	 * upload:用户文件上传
	 * @author huayj
	 * @param request
	 * @param agenciesId
	 * @return
	 * @return ModelAndView
	 * @date&time 2015-8-28 上午9:40:30
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/upload",method=RequestMethod.POST)
	public ModelAndView upload(ModelMap model,HttpServletRequest request,
			@RequestParam(value="agenciesId",required=false)Long agenciesId,
			@RequestParam(value="managerUserId",required=false)Long managerUserId,
			@RequestParam(value="roleIds",required=false)Long[] roleIds){
		if(ObjUtil.isEmpty(managerUserId)){
			Exceptions.service("", "管理人不能为空!");
		}
		if(ObjUtil.isEmpty(roleIds)){
			Exceptions.service("", "角色不能为空!");
		}
		model.put("is_Not_Gzip", true);
		StringBuffer errors = new StringBuffer();
		DefaultMultipartHttpServletRequest multipart = (DefaultMultipartHttpServletRequest) request;
		userImportFileFacade.createAndSaveUser(multipart.getFileMap(),agenciesId,errors,managerUserId,roleIds,null);
		if (errors.length() > 0) {
			return errorMsgJson(errors.toString());
		} else {
			return successMsgJson("导入成功");
		}
	}
	
	
	@RequestMapping(value="preUserPassTotal",method=RequestMethod.GET)
	public String preUserPassTotal(ModelMap model,@RequestParam(value="id",required=false)Long id){
		model.addAttribute("id", id);
		return preUserPassTotal;
	}
	
	@RequestMapping(value="preUserUnPassTotal",method=RequestMethod.GET)
	public String preUserUnPassTotal(ModelMap model,@RequestParam(value="id",required=false)Long id){
		model.addAttribute("id", id);
		return preUserUnPassTotal;
	}
}
