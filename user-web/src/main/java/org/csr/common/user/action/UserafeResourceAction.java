package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.entity.SafeResourceCollectionBean;
import org.csr.common.user.entity.UserSafeResourceCollectionBean;
import org.csr.common.user.facade.SafeResourceCollectionFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.common.user.facade.UserSafeResourceCollectionFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.ToValue;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/userafeResource")
public class UserafeResourceAction extends BasisAction {
	
	protected String preUserAddSafeResource = "common/user/userAddSafeResource";
	
	@Resource
	private UserSafeResourceCollectionFacade userSafeResourceCollectionFacade;
	@Resource
	private SafeResourceCollectionFacade safeResourceCollectionFacade;
	@Resource
	private SafeResourceFacade safeResourceFacade;
	/**
	 * 给用户授权安全资源
	 * @param model
	 * @param agenciesId
	 * @return
	 */
	@RequestMapping(value="preUserAddSafeResource",method=RequestMethod.GET)
	public String preUserAddSafeResource(@RequestParam(value="userId")Long userId,@RequestParam(value="agenciesId",required=false)Long agenciesId){
		setRequest("userId", userId);
		setRequest("agenciesId", agenciesId);
		return preUserAddSafeResource;
	}
	
	/**
	 * @description:查询未授权的安全角色
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/listAddSafeResource", method = RequestMethod.POST)
	public ModelAndView listAddSafeResource(@RequestParam(value="userId")Long userId,@RequestParam(value="name",required=false)String name) {
		if(ObjUtil.isEmpty(userId)){
			errorMsgJson("您没有选择用户");
		}
		
		PagedInfo<SafeResourceCollectionBean> result=safeResourceCollectionFacade.findAllPage(page);
		List<UserSafeResourceCollectionBean> userSafeResourceCollection = userSafeResourceCollectionFacade.findByUserId(userId);
		List<Long> oldIds = PersistableUtil.arrayTransforList(userSafeResourceCollection,new ToValue<UserSafeResourceCollectionBean, Long>() {
			@Override
			public Long getValue(UserSafeResourceCollectionBean obj) {
				return obj.getSafeResourceCollectionBean().getId();
			}
		});
		List<SafeResourceCollectionBean> sefeDatas=safeResourceCollectionFacade.findByIds(oldIds);
		for (SafeResourceCollectionBean safeResourceCollectionBean : sefeDatas) {
			if(ObjUtil.isNotEmpty(safeResourceCollectionBean)){
				SafeResourceCollectionBean hasRow = result.hasRow(safeResourceCollectionBean);
				if(ObjUtil.isEmpty(hasRow)){
					result.getRows().add(safeResourceCollectionBean);
				}else{
					hasRow.setChecked(true);
				}
			}
		}
    	return resultExcludeJson(result);
	}
	
	/**
	 * @description:保存用户角色
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/updateUserSafeResourceCollection", method = RequestMethod.POST)
	public ModelAndView updateUserSafeResourceCollection(@RequestParam(value="userId")Long userId,@RequestParam(value="safeResourceCollectionIds")Long[] safeResourceCollectionIds) {
		userSafeResourceCollectionFacade.updateUserSafeResourceCollection(userId, ObjUtil.toList(safeResourceCollectionIds));
		return successMsgJson("");
	}
}
