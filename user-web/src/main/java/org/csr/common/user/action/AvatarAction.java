package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.storage.service.StorageSchemeService;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.AvatarFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/avatar")
public class AvatarAction extends BasisAction{
	
	final String myHeaderImg="common/user/userHeaderImg";
	@Resource
	private UserFacade userFacade;
	@Resource
	private AvatarFacade avatarFacade;
	@Resource
	private StorageSchemeService storageSchemeService;
	
	/**
	 * 
	 * myHeaderImg: 头像照片 <br/>
	 * @author admin
	 * @param spaceKey
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="userHeaderImg")
	public String userHeaderImg(Long userId) {
		UserBean user=userFacade.findById(userId);
		setRequest("avatar",user.getAvatar());
		setRequest("middleHead",user.getMiddleHead());
		setRequest("head",user.getHead());
		setRequest("userId",userId);
		return myHeaderImg;
	}
	/**
	 * 切图
	 * @param streamId 要切图的id
	 * @param photoType  来源，上传，或者 从相册中来  0：上传 、1：相册
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 * @return
	 */
	@RequestMapping(value="ajax/crop")
	public ModelAndView imageCrop(@RequestParam("streamId")Long streamId,@RequestParam("userId")Long userId,Integer photoType,int left,int top,int width,int height){
		UserBean user=avatarFacade.imageCrop(userId,streamId,photoType,top,left,width,height);
		UserSessionBasics usb=UserSessionContext.getUserSession();
		if(usb.getUserId().equals(userId)){
			usb.setAvatarUrl(user.getAvatarUrl());
			usb.setHeadUrl(user.getHeadUrl());
		}
		return successMsgJson("");
	}
}
