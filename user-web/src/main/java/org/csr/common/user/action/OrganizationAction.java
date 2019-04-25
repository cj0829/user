package org.csr.common.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.constant.SafeResourceTipType;
import org.csr.common.user.entity.OrganizationNode;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.facade.OrganizationFacade;
import org.csr.common.user.facade.RegisterOrganizationFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.common.user.facade.UserFacade;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.util.PersistableUtil;
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
 * ClassName:OrganizationAction.java <br/>
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
@RequestMapping(value = "/organization")
public class OrganizationAction extends BasisAction{
	
	protected String preList="common/organization/organizationList";
	protected String preAdd="common/organization/organizationAdd";
	protected String preUpdate="common/organization/organizationUpdate";
	String preTree="common/organization/organizationTree";
	private String preMyselfAuthorize="common/organization/roleFunctionPointAdd";
	/**设置默认权限*/
	private String preOrgDefaultPermissionsSet="common/organization/orgDefaultPermissionsSet";
	
	
	@Resource
	protected OrganizationFacade organizationFacade;
	@Resource
	private RegisterOrganizationFacade registerOrganizationFacade;
	@Resource
	private UserFacade userFacade;
	@Resource
	private FunctionpointFacade functionpointFacade;
	@Resource
	private SafeResourceFacade safeResourceFacade;
	
	
	/**
	 * @author  n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/findAllList",method=RequestMethod.POST)
	public ModelAndView findAllList(){
		PagedInfo<OrganizationNode> result = organizationFacade.findAllListPage(page);
		return resultExcludeJson(result);
	}
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
	@RequestMapping(value = "ajax/list", method = RequestMethod.POST)
	public ModelAndView list() {
		PagedInfo<OrganizationNode> result = organizationFacade.findAllListPage(page);
		return resultExcludeJson(result);
	}
	
	
	/**
	 * @description:进入添加组织机构页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preAdd",method = RequestMethod.GET)
	public String preAdd(ModelMap model){
		model.addAttribute("parentId", Organization.global);
		return preAdd;
	}
	
	
	/**
	 * @description:进入添加子组织机构页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preChildrenAdd")
	public String preChildrenAdd(ModelMap model,@RequestParam("id")Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			String parentName = organizationFacade.findNameById(id,"name");
			model.addAttribute("parentName", parentName);
			model.addAttribute("parentId", id);
		}else{
			model.addAttribute("parentId", Organization.global);
		}
		return preAdd;
	}
	/**
	 * @description:添加组织机构
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/add", method = RequestMethod.POST)
	public ModelAndView add(@RequestParam("parentId")Long parentId,
			@RequestParam(value="adminUserId",required=false)Long adminUserId,Organization orga) {
		organizationFacade.save(parentId,adminUserId, orga);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orga", OrganizationNode.wrapBean(orga));
		map.put("parentId", parentId);
		return successMsgJson("", map);
	}
	
	/**
	 * @description:进入添加组织机构页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preUpdate", method = RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam("id")Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			OrganizationNode orga = organizationFacade.findById(id);
			if(ObjUtil.isNotEmpty(orga)){
				String parentName = organizationFacade.findNameById(orga.getParentId(),"name");
				model.addAttribute("parentName", parentName);
				if(ObjUtil.isNotEmpty(orga.getParentId())){
					model.addAttribute("parentId", orga.getParentId());
				}else{
					model.addAttribute("parentId", Organization.global);
				}
			}
			model.addAttribute("orga", orga);
		}
		return preUpdate;
	}
	
	/**
	 * @description:添加组织机构
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam("parentId")Long parentId,
			@RequestParam(value="adminUserId",required=false)Long adminUserId,Organization orga) {
		organizationFacade.update(adminUserId,orga);
		return successMsgJson("", OrganizationNode.wrapBean(orga));
	}
	
	/**
	 * @description:检测组织机构是否存在
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/findHasOrganizationName", method = RequestMethod.POST)
	public ModelAndView findHasOrganizationName(@RequestParam(value="parentId",required=false)Long parentId,
			@RequestParam("name")String name) {
		if (organizationFacade.checkOrganizationName(parentId,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("orgaNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * @description:检测组织机构是否存在
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/findHasUpdateOrganizationName", method = RequestMethod.POST)
	public ModelAndView findHasUpdateOrganizationName(@RequestParam(value="parentId",required=false)Long parentId,
			@RequestParam("id")Long id,@RequestParam("name")String name) {
		if (organizationFacade.findHasUpdateOrganizationName(parentId,id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("orgaNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * @description:检测组织机构是否存在
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/checkOrganizationAliases", method = RequestMethod.POST)
	public ModelAndView checkOrganizationAliases(@RequestParam(value="id",required=false)Long id,
			@RequestParam("aliases")String aliases) {
		if (organizationFacade.checkOrganizationAliases(id,aliases)) {
			return errorMsgJson("域别名已存在");
		}
		return successMsgJson("");
	}
	
	/**
	 * 删除机构
	 * @return
	 */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("organizationId")Long organizationId){
		organizationFacade.deleteOrganization(organizationId);
		return successMsgJson("");
	}
	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preTreeList", method = RequestMethod.GET)
	public String preTree() {
		return preTree;
	}
	
	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findTreeByNotId", method = RequestMethod.POST)
	public ModelAndView findTreeByNotId(final Long id) {
		if(ObjUtil.isEmpty(id)){
			return resultExcludeJson("[]");
		}
		//TODO:2015-04-15 暂时，查询全部机构，
		List<OrganizationNode> list = organizationFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,new TreeNodeHandle<OrganizationNode>() {
			@Override
			public Long rootId() {
//				//暂时，查询全部机构，
//				return Organization.global;
				return 0l;
			}
			@Override
			public boolean setBean(OrganizationNode value, TreeNode node,TreeNode parent) {
				if(id.equals(value.getId())){
					return false;
				}else{
					return true;
				}
			}
			@Override
			public TreeNode newTreeNode(OrganizationNode value) {
				UserBean user = null;
				if(ObjUtil.isNotEmpty(value.getAdminUserId())){
					user = userFacade.findById(value.getAdminUserId());
				}
				if(ObjUtil.isNotEmpty(user)){
					value.setAdminUserName(user.getLoginName());
				}
				return value;
			}
		});
		return resultExcludeJson(result);
	}
	
	
	
	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/treeList", method = RequestMethod.POST)
	public ModelAndView tree() {
		//TODO:2015-04-15 暂时，查询全部机构，
		List<OrganizationNode> list = organizationFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,new TreeNodeHandle<OrganizationNode>() {
			@Override
			public Long rootId() {
//				//暂时，查询全部机构，
//				return Organization.global;
				return 0l;
			}
			@Override
			public boolean setBean(OrganizationNode value, TreeNode node,TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(OrganizationNode value) {
				UserBean user = null;
				if(ObjUtil.isNotEmpty(value.getAdminUserId())){
					user = userFacade.findById(value.getAdminUserId());
				}
				if(ObjUtil.isNotEmpty(user)){
					value.setAdminUserName(user.getLoginName());
				}
				return value;
			}
		});
		return resultExcludeJson(result);
	}

	/**
	 * @description:进入授权功能页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="preMyselfAuthorize",method=RequestMethod.GET)
	public String preMyselfAuthorize(ModelMap model,@RequestParam(value="organizationId",required=false)Long organizationId){
		
		OrganizationNode organization=organizationFacade.findById(organizationId);
		model.addAttribute("functionPointList",functionpointFacade.findCanBeAuthorizedWrapStructure(true));
    	model.addAttribute("roleFunctionPointList",functionpointFacade.findIdsByRoleId(organization.getAdminRoleId()));
		
		model.addAttribute("organizationId",organizationId);
		return preMyselfAuthorize;
	}
	
	/**
	 * @description:进入授权功能页面，用户给机构直接赋权。
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/saveMyselfAuthorize",method=RequestMethod.POST)
	public ModelAndView saveMyselfAuthorize(ModelMap model,
			@RequestParam(value="organizationId",required=false)Long organizationId,
			@RequestParam(value="functionPointIds",required=false)Long[] functionPointIds){
		registerOrganizationFacade.saveMyselfAuthorize(organizationId, functionPointIds);
		return successMsgJson("");
	}
	
    
	
	/**
	 * 查询安全资源提供安全资源编辑页面使用。
	 * /common/safeResource/safeResourceList.jsp
	 * 查询出所有的安全资源，并在前台，给予
	 * @param collectionId
	 * @param oldId
	 * @return
	 */
	@RequestMapping(value="ajax/resourceList",method=RequestMethod.POST)
	public ModelAndView resourceList(@RequestParam(value="collectionId",required=false)Long collectionId){
		
		PagedInfo<OrganizationNode> result = organizationFacade.findAllPage(page);
		
		List<Long> orgIds=PersistableUtil.arrayTransforList(result.getRows());
		final List<Long> oIds=safeResourceFacade.collectionResource(collectionId, orgIds, null, ObjUtil.toList(SafeResourceTipType.ORGANIZATION));
		for(OrganizationNode node:result){
			if(oIds.contains(node.getId())){//在资源库中。
				node.setChecked(true);
			}
		}
		return resultExcludeJson(result);
	}
	
	/**
	 * 查询安全资源提供安全资源编辑页面使用。
	 * /common/safeResource/safeResourceList.jsp
	 * 查询出所有的安全资源，并在前台，给予
	 * @param collectionId
	 * @param oldId
	 * @return
	 */
	@RequestMapping(value="ajax/activatingOrganization",method=RequestMethod.POST)
	public ModelAndView activatingOrganization(@RequestParam(value="organizationId",required=false)Long organizationId){
		organizationFacade.updateActivating(organizationId);
		return successMsgJson("激活成功");
	}
	
	
	/**
	 * @description:获取全部的组织机构（如果是根据root登录，那么需要根据root过滤）
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/findDropDownTree")
	public ModelAndView findDropDownTree(){
		List<OrganizationNode> list = organizationFacade.findHasChildListAll();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,
		new TreeNodeHandle<OrganizationNode>() {
			@Override
			public Long rootId() {
//				return 0l;
				return Organization.global;
			}

			@Override
			public boolean setBean(OrganizationNode value, TreeNode node,TreeNode parent) {
				return true;
			}

			@Override
			public TreeNode newTreeNode(OrganizationNode value) {
				return value;
			}

		});
		return resultExcludeJson(result);
	}
	
	/**
	 * 设置域可以显示的默认权限
     * @description:
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preOrgDefaultPermissions", method = RequestMethod.GET)
    public String preRoleFunctionPointInfo(@RequestParam(value="organizationId",required=false)Long organizationId){
    	OrganizationNode organization=organizationFacade.findById(organizationId);
    	getRequest().setAttribute("functionPointList", functionpointFacade.findAllByRoleIdWrapStructure(organization.getAdminRoleId(),true));
    	getRequest().setAttribute("organizationId",organizationId);
		return preOrgDefaultPermissionsSet;
    }
	
    
    /**
	 * @description:进入授权功能页面，用户给机构直接赋权。
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value="ajax/saveOrgDefaultPermissions",method=RequestMethod.POST)
	public ModelAndView saveOrgDefaultPermissions(ModelMap model,
			@RequestParam(value="organizationId",required=false)Long organizationId,
			@RequestParam(value="functionPointIds",required=false)Long[] functionPointIds){
		registerOrganizationFacade.saveOrgDefaultPermissions(organizationId, functionPointIds);
		return successMsgJson("");
	}
	
}
