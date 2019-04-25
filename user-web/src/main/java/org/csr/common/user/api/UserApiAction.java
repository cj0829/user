//package org.csr.common.user.api;
//
//import java.io.UnsupportedEncodingException;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.csr.common.user.domain.User;
//import org.csr.common.user.facade.UserApiFacade;
//import org.csr.core.persistence.business.domain.UserStatus;
//import org.csr.core.util.Md5PwdEncoder;
//import org.csr.core.util.ObjUtil;
//import org.csr.core.util.PropertiesUtil;
//import org.csr.core.web.controller.BasisAction;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// * ClassName:Agencies.java <br/>
// * Date: Tue Sep 09 23:05:42 CST 2014
// * 
// * @author n-caijin <br/>
// * @version 1.0 <br/>
// * @since JDK 1.7
// */
//@Controller
//@Scope("prototype")
//@RequestMapping(value = "/user/api")
//public class UserApiAction extends BasisAction {
//
//	UserApiFacade userApiFacade;
//
//	/**
//	 * @description:保存新增用户信息
//	 * @param:
//	 * @return: String
//	 */
//	@RequestMapping(value = "add",method=RequestMethod.POST)
//	public ModelAndView add(HttpServletRequest request) throws UnsupportedEncodingException{
//		
//		DefaultMultipartHttpServletRequest multipart = (DefaultMultipartHttpServletRequest) request;
//		if(ObjUtil.isEmpty(multipart.getFileMap())){
//			return errorMsgJson("必须上传现场照片");
//		}
//		CommonsMultipartFile image = (CommonsMultipartFile) multipart.getFileMap().get("image");
//		
//		if(ObjUtil.isEmpty(image)){
//			return null;
//		}
//		// 设置用户状态
////		user.setUserStatus(UserStatus.NORMAL);
////		if (!userApiFacade.saveUser(user, null)) {
////			return errorMsgJson(PropertiesUtil.getExceptionMsg("loginNameIsExist"));
////		}
//		return successMsgJson("");
//	}
//
//	/**
//	 * @description:保存修改用户信息
//	 * @param:
//	 * @return: String
//	 */
//	@RequestMapping(value = "update",method=RequestMethod.POST)
//	public ModelAndView update(User user, @RequestParam(value = "roleIds", required = false) Long[] roleIds,
//			@RequestParam(value = "safeResourceIds", required = false) Long[] safeResourceIds) {
//		userApiFacade.updateUser(user, null);
//		return successMsgJson("");
//	}
//
////	/**
////	 * @description:删除用户信息
////	 * @param:
////	 * @return: String
////	 */
////	@RequestMapping(value = "delete", method = RequestMethod.POST)
////	public ModelAndView delete(@RequestParam(value = "ids") Long[] ids) {
////		if (ids != null && ids.length > 0) {
////			userApiFacade.deleteUser(ids);
////		} else {
////			return errorMsgJson(PropertiesUtil.getExceptionMsg("noSelectData"));
////		}
////		return successMsgJson("");
////	}
//}
