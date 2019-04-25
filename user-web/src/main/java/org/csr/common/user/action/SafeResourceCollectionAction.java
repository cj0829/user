package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.entity.SafeResourceCollectionBean;
import org.csr.common.user.facade.SafeResourceCollectionFacade;
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
 * ClassName:SafeResourceCollection.java <br/>
 * Date:     Tue Dec 01 17:02:25 CST 2015
 * @author   liurui-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  action
 */
 
@Controller
@Scope("prototype")
@RequestMapping(value="/safeResourceCollection")
public class SafeResourceCollectionAction extends BasisAction{

	protected String preList="common/safeResourceCollection/safeResourceCollectionList";
	protected String preAdd="common/safeResourceCollection/safeResourceCollectionAdd";
	protected String preUpdate="common/safeResourceCollection/safeResourceCollectionUpdate";
	@Resource
	private SafeResourceCollectionFacade safeResourceCollectionFacade;
	
	/**
	 * 
	 * preList: 资源库列表 <br/>
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preList",method=RequestMethod.GET)
	public String preList(){
		return preList;
	}
	/**
	 * 
	 * list: 查询资源列表 <br/>
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/list",method=RequestMethod.POST)
	public ModelAndView list(){
		PagedInfo<SafeResourceCollectionBean> result=safeResourceCollectionFacade.findAllPage(page);
		return resultExcludeJson(result);
	}

	/**
	 * 
	 * preAdd: 新建资源库页面 <br/>
	 * @param model
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preAdd",method=RequestMethod.GET)
	public String preAdd(){
		return preAdd;
	}
	/**
	 * 
	 * add: 添加资源 <br/>
	 * @author liurui
	 * @param safeResourceCollection
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/add",method=RequestMethod.POST)
	public ModelAndView add(SafeResourceCollection safeResourceCollection){
		safeResourceCollectionFacade.save(safeResourceCollection);
		return successMsgJson("");
	}

	/**
	 * 
	 * preUpdate: 修改资源库页面 <br/>
	 * @param model
	 * @param id
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value="preUpdate",method=RequestMethod.GET)
	public String preUpdate(ModelMap model,Long id){
		model.addAttribute("safeResourceCollection",safeResourceCollectionFacade.findById(id));
		return preUpdate;
	}
	/**
	 * 
	 * update: 修改资源 <br/>
	 * @author liurui
	 * @param safeResourceCollection
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/update",method=RequestMethod.POST)
	public ModelAndView update(SafeResourceCollection safeResourceCollection){
		safeResourceCollectionFacade.update(safeResourceCollection);
		return successMsgJson("");
	}
	/**
	 * 
	 * delete: 删除资源 <br/>
	 * @author liurui
	 * @param ids
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value="ajax/delete",method=RequestMethod.POST)
	public ModelAndView delete(Long id){
		safeResourceCollectionFacade.delete(id);
		return successMsgJson("");
	}
	/**
	 * 
	 * findName: 描述方法的作用 <br/>
	 * @author liurui
	 * @param id
	 * @param name
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(Long id,String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (safeResourceCollectionFacade.checkNameIsExist(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
	
	/**
	 * @description:根据参数查询用户列表
	 * @author yjY
	 * @param loginName
	 * @param name
	 * @param familyName
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ajax/findDropDownList", method = RequestMethod.POST)
	public ModelAndView findDropDownList(@RequestParam(value="oldIds",required=false)Long[] oldIds,
			@RequestParam(value="name",required=false)String name) {// 此处
		PagedInfo<SafeResourceCollectionBean> result=safeResourceCollectionFacade.findAllPage(page);
		if(ObjUtil.isNotEmpty(oldIds)){
			List<SafeResourceCollectionBean> sefeDatas=safeResourceCollectionFacade.findByIds(oldIds);
			for (SafeResourceCollectionBean safeResourceCollectionBean : sefeDatas) {
				if(ObjUtil.isNotEmpty(safeResourceCollectionBean)){
					if(ObjUtil.isEmpty(result.hasRow(safeResourceCollectionBean))){
						result.getRows().add(safeResourceCollectionBean);
					}
				}
			}
		}
		return resultExcludeJson(result);
	}
}
