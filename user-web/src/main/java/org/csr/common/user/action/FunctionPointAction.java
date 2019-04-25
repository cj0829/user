package org.csr.common.user.action;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.constant.FunctionPointType;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.MenuFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.security.resource.MenuNodeBean;
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
 * ClassName:FunctionPointAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/functionPoint")
public class FunctionPointAction extends BasisAction {
	
	protected String preList="common/functionPoint/functionPointList";
	protected String preAddClass="common/functionPoint/functionPointAddClass";
	protected String preAdd="common/functionPoint/functionPointAdd";

	protected String preUpdateClass="common/functionPoint/functionPointUpdateClass";
	protected String preUpdate="common/functionPoint/functionPointUpdate";
	
	@Resource
	protected FunctionpointFacade functionpointFacade;
	@Resource
	protected MenuFacade menuFacade;
	/**
	 * @description:进入功能点列表
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList() {
		return preList;
	}
	
	/**
	 * @description:获取功能点列表一级信息
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/list", method = RequestMethod.POST)
	public ModelAndView list(@RequestParam(value="name",required=false)String name) {
		List<FunctionPointNode> funs = functionpointFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(funs,
		new TreeNodeHandle<FunctionPointNode>() {
			@Override
			public boolean setBean(FunctionPointNode value, TreeNode node,TreeNode parent) {
				return true;
			}

			@Override
			public TreeNode newTreeNode(FunctionPointNode value) {
				return value;
			}
		});
		return resultExcludeJson(result, "functionPoint");
	}
	
	 /**
     * @description:查询域的全部功能点
     * @param:
     * @return: String
     */
	@RequestMapping(value = "ajax/findRoleFunctionPointInfo", method = RequestMethod.POST)
	public ModelAndView findRoleFunctionPointInfo() {
		List<FunctionPointNode> list = functionpointFacade.findAllCanBeAuthorized();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,
		new TreeNodeHandle<FunctionPointNode>() {
			@Override
			public boolean setBean(FunctionPointNode value,
					TreeNode node, TreeNode parent) {
				return true;
			}

			public Long getParentId(TreeNode node) {
				FunctionPointNode fnode = (FunctionPointNode) node;
				if (ObjUtil.isEmpty(fnode.getFunctionPoint()) || FunctionPointType.TYPE.equals(fnode.getFunctionPoint().getFunctionPointType())) {
					return null;
				}
				return fnode.getFunctionPoint().getId();
			}

			@Override
			public TreeNode newTreeNode(FunctionPointNode value) {
				return value;
			}
		});
		return resultExcludeJson(result, "functionPoint");
	}
	
	/**
	 * @description:进入添加功能点库页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preAddClass", method = RequestMethod.GET)
	public String preAddClass(ModelMap model) {
		model.addAttribute("typeId", FunctionPointType.TYPE);
		return preAddClass;
	}
	
	/**
	 * @description:进入添加功能点页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preAdd", method = RequestMethod.GET)
	public String preAdd(ModelMap model, @RequestParam("id")Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			String functionName = functionpointFacade.findNameById(id,"name");
			model.addAttribute("functionName", functionName);
			model.addAttribute("functionId", id);
		}
		model.addAttribute("typeId", FunctionPointType.FUNCTIONPOINT);
		return preAdd;
	}
	
	/**
	 * @description:添加功能点
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/add", method = RequestMethod.POST)
	public ModelAndView add(FunctionPoint function,@RequestParam(name="parentId",required=false)Long parentId) {
		functionpointFacade.save(function, parentId);
		return successMsgJson("", function);
	}
	
	/**
	 * @description:进入修改功能点库页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preUpdateClass", method = RequestMethod.GET)
	public String preUpdateClass(ModelMap model,@RequestParam("id")Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			FunctionPointNode function = functionpointFacade.findById(id);
			model.addAttribute("function", function);
		}
		model.addAttribute("typeId", FunctionPointType.TYPE);
		return preUpdateClass;
	}
	/**
	 * @description:进入修改功能点页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preUpdate", method = RequestMethod.GET)
	public String preUpdate(ModelMap model, @RequestParam("id")Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			FunctionPointNode function = functionpointFacade.findById(id);
			String[] urlRules = function.getUrlRule().split(",");
			if(ObjUtil.isEmpty(urlRules)){
				model.addAttribute("urlRules",new String[]{""});
			}else{
				model.addAttribute("urlRules",urlRules);
			}
			model.addAttribute("function", function);
		}
		model.addAttribute("typeId", FunctionPointType.FUNCTIONPOINT);
		return preUpdate;
	}
	
	/**
	 * 修改功能点
	 * @return
	 */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(FunctionPoint function, @RequestParam(name="parentId",required=false)Long parentId,
			@RequestParam(value="urlRules",required=false)String[] urlRules) {
		if(ObjUtil.isNotEmpty(urlRules)){
			StringBuffer urlRule = new StringBuffer();
			boolean b=true;
			for (int i = 0; i < urlRules.length; i++) {
				if(ObjUtil.isNotBlank(urlRules[i])){
					if(b){
						b=false;
					}else{
						urlRule.append(",");
					}
					urlRule.append(urlRules[i]);
				}
			}
			function.setUrlRule(urlRule.toString());
		}
		functionpointFacade.update(function,parentId);
		return successMsgJson("", function);
	}
	
	/**
	 * 删除功能点
	 * @return
	 */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("deleteIds")String deleteIds) {
		functionpointFacade.deleteByIds(deleteIds);
		return successMsgJson("");
	}
	
	/**
	 * 比较功能点名称是否存在
	 * @return
	 */
	@RequestMapping(value = "ajax/findHasAddName",method = RequestMethod.POST)
	public ModelAndView findHasAddName(@RequestParam(name="parentId",required=false)Long parentId,@RequestParam("name")String name){
		FunctionPointNode point=functionpointFacade.checkFunctionPointNameOnly(parentId,name);
		if(point!=null){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("functionPointNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * 比较功能点名称是否存在
	 * @return
	 */
	@RequestMapping(value = "ajax/findHasUpdateName",method = RequestMethod.POST)
	public ModelAndView findHasUpdateName(@RequestParam("id")Long id,@RequestParam(name="parentId",required=false)Long parentId,@RequestParam("name")String name){
		FunctionPointNode point=functionpointFacade.checkFunctionPointNameOnly(parentId, name);
		if(point!=null && !id.equals(point.getId())){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("functionPointNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * 比较功能点编码是否存在
	 * @return
	 */
	@RequestMapping(value = "ajax/findHasCode",method = RequestMethod.POST)
	public ModelAndView findHasCode(@RequestParam(name="id",required=false)Long id,@RequestParam("code")String code){
		FunctionPointNode point=functionpointFacade.checkFunctionPointCodeOnly(code);
		if(point!=null && !id.equals(point.getId())){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("functionPointCodeIsExist"));
		}
		return successMsgJson("");
	}
	
	@RequestMapping(value = "ajax/paged", method = RequestMethod.POST)
	public ModelAndView paged(@RequestParam(value="menuId",required=false)Long menuId) {
		PagedInfo<FunctionPointNode> result=functionpointFacade.findAllPage(page);
		if(page.getPageNumber()==1){
			if(ObjUtil.isNotEmpty(menuId)){
				MenuNodeBean menu=menuFacade.findById(menuId);
				FunctionPointNode fp=(FunctionPointNode) menu.getSecurityResource();
				if(ObjUtil.isNotEmpty(fp)){
					result.getRows().add(0,fp);
				}
			}
		}
		return resultExcludeJson(result);
	}
}
