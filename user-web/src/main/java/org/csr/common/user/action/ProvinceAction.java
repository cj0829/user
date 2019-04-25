package org.csr.common.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.domain.Province;
import org.csr.common.user.entity.ProvinceNode;
import org.csr.common.user.facade.ProvinceFacade;
import org.csr.core.tree.TreeNode;
import org.csr.core.tree.TreeNodeHandle;
import org.csr.core.tree.TreeNodeTool;
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
 * ClassName:ProvinceAction.java <br/>
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
@RequestMapping(value = "/province")
public class ProvinceAction extends BasisAction {
	
	final String preList="common/province/provinceList";
	final String preAdd="common/province/provinceAdd";
	final String preUpdate="common/province/provinceUpdate";
	/**
	 * 
	 */
	@Resource
	private ProvinceFacade provinceFacade;
	
	/**
	 * 
	 * preList: 列表页面 <br/>
	 * @return
	 * @author liurui
	 * @date 2016-1-7
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preList",method = RequestMethod.GET)
	public String preList(){
		return preList;
	}
	
	/**
	 * @description:获取行政区列表一级信息
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/list",method = RequestMethod.POST)
	public ModelAndView list(){
		List<ProvinceNode> list=provinceFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result=TreeNodeTool.toResult(list,new TreeNodeHandle<ProvinceNode>(){
			@Override
			public boolean setBean(ProvinceNode value, TreeNode node, TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(ProvinceNode value){
				return value;
			}
		});
		return resultExcludeJson(result);
	}
	
	/**
	 * @description:进入添加行政区页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preAdd",method = RequestMethod.GET)
	public String preAdd(ModelMap model,@RequestParam(value="id",required=false)Long id){
		if(ObjUtil.isNotEmpty(id)){
			ProvinceNode p=provinceFacade.findById(id);
			model.addAttribute("parentName",p.getName());
			model.addAttribute("parentId",id);
		}
		return preAdd;
	}
	/**
	 * @description:进入添加子行政区页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preAddChildren",method = RequestMethod.GET)
	public String preAddChildren(ModelMap model,@RequestParam("id")Long id){
		if(id != null && id>0){
			String provName = provinceFacade.findNameById(id,"name");
			model.addAttribute("provName", provName);
			model.addAttribute("parentId", id);
		}
		return preAdd;
	}
	/**
	 * @description:添加行政区
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/add",method = RequestMethod.POST)
	public ModelAndView add(Province prov){
		provinceFacade.saveSimple(prov);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("prov", prov);
		map.put("parentId", prov.getParentId());
		return successMsgJson("", map);
	}
	
	/**
	 * @description:进入编辑行政区页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preUpdate",method = RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam("id")Long id){
		ProvinceNode prov = provinceFacade.findById(id);
		model.addAttribute("province", prov);
		Long parentId=prov.getParentId();
		if(ObjUtil.isNotEmpty(parentId)){
			String parentName = provinceFacade.findNameById(parentId,"name");
			model.addAttribute("parentName", parentName);
		}
		return preUpdate;
	}
	
	/**
	 * @description:添加行政区
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/update",method = RequestMethod.POST)
	public ModelAndView update(Province prov){
		provinceFacade.update(prov);
		return successMsgJson("");
	}
	
	/**
	 * 删除功能点
	 * @return
	 */
	@RequestMapping(value = "ajax/delete",method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("ids")String ids){
		provinceFacade.delete(ids);
		return successMsgJson("");
	}
	
	/**
	 * @description:获取行政区列表一级信息
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/findAll",method = RequestMethod.POST)
	public ModelAndView findAll(){
		List<ProvinceNode> list=provinceFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result=TreeNodeTool.toResult(list,new TreeNodeHandle<ProvinceNode>(){
			@Override
			public boolean setBean(ProvinceNode value, TreeNode node, TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(ProvinceNode value){
				return value;
			}
		});
		return resultExcludeJson(result);
	}
	
	/**
	 * @description:检测行政区是否存在
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/findHasProvinceName",method = RequestMethod.POST)
	public ModelAndView findHasProvinceName(@RequestParam(value="parentId",required=false)Long parentId,@RequestParam("provName")String provName){
	   if(provinceFacade.checkProvinceName(parentId,provName)){
		   return errorMsgJson(PropertiesUtil.getExceptionMsg("provNameIsExist"));
	   }
	   return successMsgJson("");
	}
	
	
	/**
	 * @description:检测行政区是否存在
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/findHasUpdateProvinceName",method = RequestMethod.POST)
	public ModelAndView findHasUpdateProvinceName(@RequestParam(value="parentId",required=false)Long parentId,@RequestParam("id")Long id,@RequestParam("provName")String provName){
	   if(provinceFacade.checkUpdateProvinceName(parentId, id, provName)){
		   return errorMsgJson(PropertiesUtil.getExceptionMsg("provNameIsExist"));
	   }
	   return successMsgJson("");
	}
	
}
