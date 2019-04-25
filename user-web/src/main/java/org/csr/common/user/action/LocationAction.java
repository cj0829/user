package org.csr.common.user.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.domain.Location;
import org.csr.common.user.entity.LocationBean;
import org.csr.common.user.facade.LocationFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/**
 * ClassName:Location.java <br/>
 * Date:     Wed Aug 23 15:49:29 CST 2017
 * @author   summy <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 位置 action
 */
@Controller
@Scope("prototype")
@RequestMapping(value="/user/location")
public class LocationAction extends BasisAction{

	final String preList="common/location/locationList";
	final String preInfo="common/location/locationInfo";
	final String preAdd="common/location/locationAdd";
	final String preUpdate="common/location/locationUpdate";
	@Resource
	private LocationFacade locationFacade;

	/**
	 * 进入位置 列表页面
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preList",method=RequestMethod.GET)
	public String preList(){
		return preList;
	}

	/**
	 * 查询位置 列表数据
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/list",method=RequestMethod.POST)
	public ModelAndView list(){
		PagedInfo<LocationBean> result=locationFacade.findAllPage(page);
		return resultExcludeJson(result);
	}

	/**
	 *  进入位置 添加页面，
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preAdd",method=RequestMethod.GET)
	public String preAdd(Long parentId){
		if (ObjUtil.isNotEmpty(parentId) && parentId > 0) {
			LocationBean parent = locationFacade.findById(parentId);
			setRequest("parentName", parent.getName());
			setRequest("parentId", parentId);
		}
		return preAdd;
	}
	
	/**
	 * 保存位置  数据
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/add",method=RequestMethod.POST)
	public ModelAndView add(Location location){
		locationFacade.save(location);
		return successMsgJson("");
	}

	/**
	 * 进入位置 修改页面，
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preUpdate",method=RequestMethod.GET)
	public String preUpdate(Long id){
		setRequest("location",locationFacade.findById(id));
		return preUpdate;
	}

	/**
	 * 修改位置 数据，
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/update",method=RequestMethod.POST)
	public ModelAndView update(Location location){
		locationFacade.update(location);
		return successMsgJson("");
	}
	
	/**
	 * 删除 位置数据，
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/delete",method=RequestMethod.POST)
	public ModelAndView delete(Long[] ids){
		locationFacade.deleteSimple(ids);
		return successMsgJson("");
	}
	
    
    /**
	 * 查询下拉选择列表
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ajax/findDropDownList", method = RequestMethod.POST)
	public ModelAndView findDropDownList(@RequestParam(value="selecteds",required=false)String selecteds) {
		// 此处
		List<Long> seleids =new ArrayList<Long>();
		if (ObjUtil.isNotBlank(selecteds)) {
			String[] ids = selecteds.split(",");
			if (ObjUtil.isNotEmpty(ids)) {
				for (String idstr : ids) {
					seleids.add(ObjUtil.toLong(idstr));
				}
			}

		}
		PagedInfo<LocationBean> result = locationFacade.findDropDownList(page,seleids);// 此处
		return resultExcludeJson(result);
	}
	
	  /**
	 * 验证数据，可以定义别的验证
	 * @author  summy
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(Long id,String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (locationFacade.checkNameIsExist(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
}
