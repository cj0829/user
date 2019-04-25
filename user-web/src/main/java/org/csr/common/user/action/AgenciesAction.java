package org.csr.common.user.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.constant.AgenciesType;
import org.csr.common.user.constant.SafeResourceTipType;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.entity.AgenciesNode;
import org.csr.common.user.facade.AgenciesFacade;
import org.csr.common.user.facade.SafeResourceFacade;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndLEParam;
import org.csr.core.persistence.param.AndLikeParam;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.tree.TreeNode;
import org.csr.core.tree.TreeNodeHandle;
import org.csr.core.tree.TreeNodeTool;
import org.csr.core.userdetails.UserSessionContext;
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
 * ClassName:Agencies.java <br/>
 * Date: Tue Sep 09 23:05:42 CST 2014
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/agencies")
public class AgenciesAction extends BasisAction {
	final String preTree = "common/agencies/agenciesTree";
	final String preList = "common/agencies/agenciesList";
	final String preInfo = "common/agencies/agenciesInfo";
	final String preAdd = "common/agencies/agenciesAdd";
	final String preUpdate = "common/agencies/agenciesUpdate";
	final String prePrescriptionRuleAdd="";
	
	@Resource
	private AgenciesFacade agenciesFacade;
	@Resource
	private SafeResourceFacade safeResourceFacade;
	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model) {
		return preList;
	}

	/**
	 * 这里的查询，只是在，组织机构里面使用。<br>
	 * 也必须根据当前用户的权限来查询。
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/list", method = RequestMethod.POST)
	public ModelAndView list() {
		PagedInfo<AgenciesNode> result = agenciesFacade.findPage(page);
		return resultExcludeJson(result);
	}

	/**
	 * 进入
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preTreeList", method = RequestMethod.GET)
	public String preTreeList(ModelMap model) {
		return preTree;
	}

	/**
	 * 这里的查询必须带上权限查询。<br>
	 * 注册用户<br>
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/treeList", method = RequestMethod.POST)
	public ModelAndView treeList(String name) {
		if(ObjUtil.isNotBlank(name)){
			try {
				name = URLDecoder.decode(name, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		final Map<Long,AgenciesNode> map = PersistableUtil.toMap(agenciesFacade.findList(name,null)) ;
		List<TreeNode> result  = TreeNodeTool.toResult(new ArrayList<AgenciesNode>(map.values()),new TreeNodeHandle<AgenciesNode>() {
			@Override
			public boolean isRoot(TreeNode node) {
				AgenciesNode agenciesNode = map.get(node.getParentId());
				if(ObjUtil.isEmpty(agenciesNode)){
					return true;
				}
				return false;
			}
			
			@Override
			public boolean setBean(AgenciesNode value, TreeNode node,TreeNode parent) {
				if(ObjUtil.isEmpty(parent)){
					value.setState("open");
				}else if(isRoot(parent)){
					value.setState("open");
				}
				return true;
			}
			@Override
			public TreeNode newTreeNode(AgenciesNode node) {
				return node;
			}
		});
		return resultExcludeJson(result);
	}
	
	
	
	/**
	 * 这里的查询必须带上权限查询。<br>
	 * 注册用户<br>
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findTreePrentList", method = RequestMethod.POST)
	public ModelAndView treePrentList(String name) {
		if(ObjUtil.isNotBlank(name)){
			try {
				name = URLDecoder.decode(name, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		final Map<Long,AgenciesNode> map = PersistableUtil.toMap(agenciesFacade.findParentNodeList(name)) ;
		List<TreeNode> result  = TreeNodeTool.toResult(new ArrayList<AgenciesNode>(map.values()),new TreeNodeHandle<AgenciesNode>() {
			@Override
			public boolean isRoot(TreeNode node) {
				AgenciesNode agenciesNode = map.get(node.getParentId());
				if(ObjUtil.isEmpty(agenciesNode)){
					return true;
				}
				return false;
			}
			
			@Override
			public boolean setBean(AgenciesNode value, TreeNode node,TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(AgenciesNode node) {
				node.setState("open");
				return node;
			}
		});
		return resultExcludeJson(result);
	}
	
	
	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preAdd", method = RequestMethod.GET)
	public String preAdd(ModelMap model) {
		Long primaryOrgId = UserSessionContext.getUserSession().getPrimaryOrgId();
		if(!Organization.global.equals(primaryOrgId)){
			model.addAttribute("organizationId", primaryOrgId);
		}
		return preAdd;
	}

	/**
	 * @description:进入添加子组织机构页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preChildrenAdd")
	public String preChildrenAdd(ModelMap model,@RequestParam(value="id",required=false)Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			AgenciesNode parent = agenciesFacade.findById(id);
			model.addAttribute("parentName", parent.getName());
			model.addAttribute("parentId", id);
			model.addAttribute("organizationId", parent.getOrganizationId());
		}else{
			model.addAttribute("organizationId", UserSessionContext.getUserSession().getPrimaryOrgId());
		}
		return preAdd;
	}

	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/add", method = RequestMethod.POST)
	public ModelAndView add(Agencies agencies) {
		AgenciesNode node =agenciesFacade.save(agencies);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orga", node);
		map.put("parentId", agencies.getParentId());
		return successMsgJson("", map);
	}

	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preUpdate", method = RequestMethod.GET)
	public String preUpdate(ModelMap model,@RequestParam(value="id",required=false)Long id) {
		if (ObjUtil.isNotEmpty(id) && id > 0) {
			AgenciesNode agencies = agenciesFacade.findById(id);
			if(ObjUtil.isNotEmpty(agencies.getParentId())){
				AgenciesNode parentAgencies = agenciesFacade.findById(agencies.getParentId());
				if(ObjUtil.isNotEmpty(parentAgencies)){
					model.addAttribute("parentName", parentAgencies.getName());
				}
			}
			model.addAttribute("agencies", agencies);
		}
		return preUpdate;
	}

	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/update", method = RequestMethod.POST)
	public ModelAndView update(Agencies agencies) {
		agenciesFacade.update(agencies);
		return successMsgJson("");
	}

	/**
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/delete", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam(value="ids",required=false)Long[] ids) {
		agenciesFacade.delete(ids);
		return successMsgJson("");
	}

	@RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
	public ModelAndView findName(@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="name",required=false)String name) {
		if (ObjUtil.isEmpty(name)) {
			Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (agenciesFacade.checkNameIsExist(id, name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * checkCode:查看组织编码是否存在
	 * @author huayj
	 * @param id
	 * @param code
	 * @return
	 * @return ModelAndView
	 * @date&time 2016-2-23 下午5:08:04
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/checkCode", method = RequestMethod.POST)
	public ModelAndView checkCode(@RequestParam(value="id",required=false)Long id,
			@RequestParam(value="code",required=false)String code) {
		if (ObjUtil.isEmpty(code)) {
			Exceptions.service("1000109", "未正确接收到您所输入的组织机构编码,请联系管理员");
		}
		if (agenciesFacade.checkCodeIsExist(id, code)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
	}
	
	
	/**
	 * 
	 * @param id
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "ajax/resourceList", method = RequestMethod.POST)
	public ModelAndView resourceList(@RequestParam(value="collectionId",required=false)Long collectionId,
			@RequestParam(value="orgIds",required=false)Long[] orgIds) {
		
		final List<Long> oIds=safeResourceFacade.collectionResource(collectionId, ObjUtil.asList(orgIds), null, ObjUtil.toList(SafeResourceTipType.CATEGORY));
		List<AgenciesNode> list = agenciesFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,new TreeNodeHandle<AgenciesNode>() {
			@Override
			public Long rootId() {
				return ObjUtil.toLong(1);
			}
			@Override
			public boolean setBean(AgenciesNode value, TreeNode node,TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(AgenciesNode node) {
				
				node.setState("open");
				if(oIds.contains(node.getId())){
					node.setChecked(true);
				}
				return node;
			}
		});
		return resultExcludeJson(result);
	}
	
	/**
	 * 这里的查询必须带上权限查询。<br>
	 * 注册用户<br>
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findDropDownTree", method = RequestMethod.POST)
	public ModelAndView findDropDownTree() {
		List<AgenciesNode> list = agenciesFacade.findAllList();
		@SuppressWarnings("rawtypes")
		List<TreeNode> result = TreeNodeTool.toResult(list,new TreeNodeHandle<AgenciesNode>() {
			@Override
			public Long rootId() {
				return ObjUtil.toLong(1);
			}
			@Override
			public boolean setBean(AgenciesNode value, TreeNode node,TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(AgenciesNode node) {
				//AgenciesNode node = AgenciesNode.toNode(value);
//				node.setState("open");
				return node;
			}
		});
		return resultExcludeJson(result);
	}
	
	/**
	 * 这里的查询，只是在，组织机构里面使用。<br>
	 * 也必须根据当前用户的权限来查询。
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findDropDownList", method = RequestMethod.POST)
	public ModelAndView findDropDownList(@RequestParam(value="name",required=false)String name,@RequestParam(value="oldId",required=false)String oldId) {
		if(ObjUtil.isNotBlank(name)){
			page.toParam().add(new AndLikeParam("name", name));
		}
		PagedInfo<AgenciesNode> result = agenciesFacade.findPage(page);
		
		if(ObjUtil.isNotBlank(oldId)){
			AgenciesNode user=agenciesFacade.findById(ObjUtil.toLong(oldId));
			if(ObjUtil.isNotEmpty(user)){
				if(ObjUtil.isEmpty(result.hasRow(user))){
					result.getRows().add(user);
				}
			}
		}
		return resultExcludeJson(result);
	}
	

	/**
	 * 这里的查询，只是在，只查询公司。<br>
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findCompanyDropDownList", method = RequestMethod.POST)
	public ModelAndView findCompanyDropDownList(@RequestParam(value="name",required=false)String name,@RequestParam(value="oldId",required=false)String oldId) {
		if(ObjUtil.isNotBlank(name)){
			page.toParam().add(new AndLikeParam("name", name));
		}
		page.toParam().add(new AndLEParam("type", AgenciesType.PARENT));
		PagedInfo<AgenciesNode> result = agenciesFacade.findAllPage(page);
		
		if(ObjUtil.isNotBlank(oldId)){
			AgenciesNode user=agenciesFacade.findById(ObjUtil.toLong(oldId));
			if(ObjUtil.isNotEmpty(user)){
				if(ObjUtil.isEmpty(result.hasRow(user))){
					result.getRows().add(user);
				}
			}
		}
		return resultExcludeJson(result);
	}
	
	@RequestMapping(value = "ajax/treeCompanyList", method = RequestMethod.POST)
	public ModelAndView treeCompanyList(String name) {
		if(ObjUtil.isNotBlank(name)){
			try {
				name = URLDecoder.decode(name, "utf-8");
			} catch (UnsupportedEncodingException e) {
			}
		}
		final Map<Long,AgenciesNode> map = PersistableUtil.toMap(agenciesFacade.findCompanyList(name)) ;
		List<TreeNode> result  = TreeNodeTool.toResult(new ArrayList<AgenciesNode>(map.values()),new TreeNodeHandle<AgenciesNode>() {
			@Override
			public boolean isRoot(TreeNode node) {
				AgenciesNode agenciesNode = map.get(node.getParentId());
				if(ObjUtil.isEmpty(agenciesNode)){
					return true;
				}
				return false;
			}
			@Override
			public boolean setBean(AgenciesNode value, TreeNode node,TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(AgenciesNode node) {
				System.out.println(node.getState());
				return node;
			}
		});
		return resultExcludeJson(result);
	}
	
	/**
	 * 这里的查询必须带上权限查询。<br>
	 * 注册用户<br>
	 * @author n-caijin
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/findTreeNotIncludedList", method = RequestMethod.POST)
	public ModelAndView treeNotIncludedList(Long id) {
		
		final Map<Long,AgenciesNode> map = PersistableUtil.toMap(agenciesFacade.findNotIncludedList(null,null,id)) ;
		List<TreeNode> result  = TreeNodeTool.toResult(new ArrayList<AgenciesNode>(map.values()),new TreeNodeHandle<AgenciesNode>() {
			@Override
			public boolean isRoot(TreeNode node) {
				AgenciesNode agenciesNode = map.get(node.getParentId());
				if(ObjUtil.isEmpty(agenciesNode)){
					return true;
				}
				return false;
			}
			@Override
			public boolean setBean(AgenciesNode value, TreeNode node,TreeNode parent) {
				if(ObjUtil.isEmpty(parent)){
					value.setState("open");
				}else if(isRoot(parent)){
					value.setState("open");
				}
				return true;
			}
			@Override
			public TreeNode newTreeNode(AgenciesNode node) {
				return node;
			}
		});
		return resultExcludeJson(result);
	}
}
