package org.csr.common.user.action;

import javax.annotation.Resource;

import org.csr.common.user.domain.District;
import org.csr.common.user.entity.DistrictBean;
import org.csr.common.user.facade.DistrictFacade;
import org.csr.common.user.facade.DistrictProvinceFacade;
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
 * ClassName:DistrictAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/district")
public class DistrictAction extends BasisAction {
	
	final String preList="common/district/districtList";
	final String preInfo="common/district/districtInfo";
	final String preAdd="common/district/districtAdd";
	final String preUpdate="common/district/districtUpdate";
	final String preDistrictProvince="common/district/preDistrictProvince";
	
    @Resource
    private DistrictFacade districtFacade;
    @Resource
    private DistrictProvinceFacade districtProvinceFacade;

    /**
     * @description:进入区域管理页面
     * @param:
     * @return: String
     */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList() {
		return preList;
	}

    /**
     * @description:查询区域列表
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/list", method = RequestMethod.POST)
	public ModelAndView list() {
		PagedInfo<DistrictBean> result = districtFacade.findAllPage(page);
		return resultExcludeJson(result);
	}

    /**
     * @description:查看详细信息
     */
	@RequestMapping(value = "preInfo", method = RequestMethod.GET)
	public String preInfo(ModelMap model, @RequestParam("id")Long id) {
		model.addAttribute("district", districtFacade.findById(id));
		model.addAttribute("provinceNames",districtProvinceFacade.findProvinceNamesByDistrictId(id));
		return preInfo;
	}

    /**
     * @description:进入区域添加页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preAdd",method = RequestMethod.GET)
    public String preAdd() {
    	return preAdd;
    }

    /**
     * @description:保存新增区域信息
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/add", method = RequestMethod.POST)
	public ModelAndView add(District district,@RequestParam("provinceIds")String provinceIds) {
		if (!districtFacade.save(district, provinceIds)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("districtNameIsExist"));
		}
		return successMsgJson("");
	}

    /**
     * @description:进入区域编辑页面
     * @param:
     * @return: String
     */
	@RequestMapping(value = "preUpdate", method = RequestMethod.GET)
	public String preUpdate(ModelMap model, @RequestParam("id")Long id) {
		model.addAttribute("district", districtFacade.findById(id));
		model.addAttribute("provinceIds",districtProvinceFacade.findProvinceNamesByDistrictId(id));
		return preUpdate;
	}

    /**
     * @description:保存修改区域信息
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(District district,@RequestParam("provinceIds")String provinceIds) {
		districtFacade.update(provinceIds,district);
		return successMsgJson("");
	}

    /**
     * @description:删除区域信息
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("districtIds")Long[] districtIds) {
		if (districtIds != null && districtIds.length > 0) {
			districtFacade.deleteSimple(districtIds);
		} else {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("noSelectData"));
		}
		return successMsgJson("");
	}

    /**
     * @description:检测添加区域名是否存在
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/findAddDistrictName", method = RequestMethod.POST)
	public ModelAndView findAddDistrictName(@RequestParam("name")String name) {
		if (districtFacade.checkAddDistrictName(name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("districtNameIsExist"));
		}
		return successMsgJson("");
	}

    /**
     * @description:检测修改区域名是否存在
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/findUpdateDistrictName", method = RequestMethod.POST)
	public ModelAndView findUpdateDistrictName(@RequestParam("id")Long id, @RequestParam("name")String name) {
		if (districtFacade.checkUpdateDistrictName(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("districtNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
     * 查询名称是否唯一
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(Long id,String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (districtFacade.checkNameIsExist(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
}
