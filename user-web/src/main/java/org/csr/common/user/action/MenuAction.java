package org.csr.common.user.action;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.domain.Menu;
import org.csr.common.user.facade.MenuFacade;
import org.csr.core.MenuNode;
import org.csr.core.page.PagedInfo;
import org.csr.core.security.resource.MenuNodeBean;
import org.csr.core.tree.TreeNode;
import org.csr.core.tree.TreeNodeHandle;
import org.csr.core.tree.TreeNodeTool;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
 
/**
 * ClassName:MenuAction.java <br/>
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
@RequestMapping(value = "/menu")
public class MenuAction extends BasisAction{
	
	final String preList="common/menu/menuList";
	final String preAdd="common/menu/menuAdd";
	final String preUpdate="common/menu/menuUpdate";
	
	@Resource
	private MenuFacade menuFacade;
	
	/**
	 * @description:进入组织机构列表
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList() {
		return preList;
	}
	
	/**
	 * 获取组织机构列表一级信息<br>
	 * 用来逐级加载数据。
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/treeList", method = RequestMethod.POST)
	public ModelAndView treeList() {
		List<MenuNodeBean> list = menuFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,new TreeNodeHandle<MenuNodeBean>() {
	
			@Override
			public boolean setBean(MenuNodeBean value, TreeNode node,TreeNode parent) {
				return true;
			}

			@Override
			public TreeNode newTreeNode(MenuNodeBean value)throws IllegalArgumentException {
				return value;
			}
	
		});
		return resultExcludeJson(result);
	}

	/**
	 * 
	 * paged: 分页 <br/>
	 * @return
	 * @author liurui
	 * @date 2016-1-5
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/paged", method = RequestMethod.POST)
	public ModelAndView paged() {
		PagedInfo<MenuNodeBean> result = menuFacade.findAllPage(page);
		return resultExcludeJson(result);
	}
	
	/**
	 * 
	 * preAdd: 添加菜单页面 <br/>
	 * @param model
	 * @param id
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preAdd",method = RequestMethod.GET)
	public String preAdd(ModelMap model,@RequestParam(value="id",required=false) Long id){
		if(ObjUtil.isNotEmpty(id)){
			MenuNodeBean menu=menuFacade.findById(id);
			model.addAttribute("parentName",menu.getName());
			model.addAttribute("parentId",id);
		}
		return preAdd;
	}
	
	/**
	 * 
	 * add: 添加菜单 <br/>
	 * @param menu
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/add")
	public ModelAndView add(Menu menu) {
		menuFacade.save(menu);
		return successMsgJson("");
		
	}
	
	/**
	 * 
	 * preUpdate: 编辑菜单页面 <br/>
	 * @param model
	 * @param id
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "preUpdate", method = RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam("id") Long id) {
		MenuNodeBean menu = menuFacade.findById(id);
		MenuNode parent=(MenuNode) menu.getParent();
		if(ObjUtil.isNotEmpty(parent)){
			model.addAttribute("parentName",parent.getName());
		}
		model.addAttribute("menu", menu);
		return preUpdate;
	}
	
	/**
	 * @description:添加组织机构
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(Menu menu) {
		menuFacade.update(menu);
		return successMsgJson("");
	}
	
	/**
	 * 
	 * delete: 删除菜单 <br/>
	 * @param ids
	 * @return
	 * @author liurui
	 * @date 2016-1-6
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(Long[] ids){
		menuFacade.deleteSelfAndChildren(Arrays.asList(ids));
		return successMsgJson("");
	}
	
}
