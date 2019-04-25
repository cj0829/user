//package org.csr.common.user.api;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.csr.common.user.entity.AgenciesNode;
//import org.csr.common.user.facade.AgenciesFacade;
//import org.csr.core.persistence.util.PersistableUtil;
//import org.csr.core.tree.TreeNode;
//import org.csr.core.tree.TreeNodeHandle;
//import org.csr.core.tree.TreeNodeTool;
//import org.csr.core.util.ObjUtil;
//import org.csr.core.web.controller.BasisAction;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
///**
// * ClassName:Agencies.java <br/>
// * Date: Tue Sep 09 23:05:42 CST 2014
// * 
// * @author n-caijin <br/>
// * @version 1.0 <br/>
// * @since JDK 1.7
// */
//@Controller
//@Scope("prototype")
//@RequestMapping(value = "/agencies/api")
//public class AgenciesApi extends BasisAction {
//
//	@Resource
//	private AgenciesFacade agenciesFacade;
//
//	/**
//	 * 这里的查询必须带上权限查询。<br>
//	 * 注册用户<br>
//	 * 
//	 * @author n-caijin
//	 * @param:
//	 * @return: String
//	 */
//	@RequestMapping(value = "tree", method = RequestMethod.POST)
//	public ModelAndView treeList(String name) {
//		if (ObjUtil.isNotBlank(name)) {
//			try {
//				name = URLDecoder.decode(name, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//			}
//		}
//		final Map<Long, AgenciesNode> map = PersistableUtil.toMap(agenciesFacade.findAllListByNameType(name, null));
//		List<TreeNode> result = TreeNodeTool.toResult(new ArrayList<AgenciesNode>(map.values()),
//				new TreeNodeHandle<AgenciesNode>() {
//					@Override
//					public boolean isRoot(TreeNode node) {
//						AgenciesNode agenciesNode = map.get(node.getParentId());
//						if (ObjUtil.isEmpty(agenciesNode)) {
//							return true;
//						}
//						return false;
//					}
//
//					@Override
//					public boolean setBean(AgenciesNode value, TreeNode node, TreeNode parent) {
//						if (ObjUtil.isEmpty(parent)) {
//							value.setState("open");
//						} else if (isRoot(parent)) {
//							value.setState("open");
//						}
//						return true;
//					}
//
//					@Override
//					public TreeNode newTreeNode(AgenciesNode node) {
//						return node;
//					}
//				});
//		return resultExcludeJson(result);
//	}
//
//}
