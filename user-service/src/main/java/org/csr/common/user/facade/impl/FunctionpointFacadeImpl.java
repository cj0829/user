package org.csr.common.user.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.csr.common.user.constant.FunctionPointType;
import org.csr.common.user.dao.FunctionpointDao;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.facade.FunctionpointFacade;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.UserSpecialFunctionPointService;
import org.csr.core.SecurityResource;
import org.csr.core.UserSession;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.security.resource.SecurityResourceBean;
import org.csr.core.tree.TreeHandle;
import org.csr.core.tree.TreeNode;
import org.csr.core.tree.TreeNodeTool;
import org.csr.core.userdetails.UserSessionBasics;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

@Service("functionpointFacade")
public class FunctionpointFacadeImpl extends SimpleBasisFacade<FunctionPointNode, FunctionPoint, Long> implements FunctionpointFacade {
	@Resource
	FunctionpointService functionpointService;
	@Resource
	FunctionpointDao functionpointDao;
	@Resource
	UserSpecialFunctionPointService userSpecialFunctionPointService;

	@Override
	public FunctionPointNode wrapBean(FunctionPoint doMain) {
		return FunctionPointNode.wrapBean(doMain);
	}

//	@Override
//	public FunctionPoint wrapDomain(FunctionPointNode entity) {
//		FunctionPoint domain = new FunctionPoint();
//		domain.setId(entity.getId());
//		domain.setName(entity.getName());
//		domain.setCode(entity.getCode());
//		domain.setForwardUrl(entity.getForwardUrl());
//		domain.setUrlRule(entity.getUrlRule());
//		domain.setIcon(entity.getIcon());
//		domain.setDufIcon(entity.getDufIcon());
//		domain.setEnableBan(entity.getEnableBan());
//		domain.setFunctionPointType(entity.getFunctionPointType());
//		if(ObjUtil.isNotEmpty(entity.getFunctionPoint())){
//			domain.setFunctionPoint(new FunctionPoint(entity.getFunctionPoint().getId()));
//		}
//		domain.setBrowseLogLevel(entity.getBrowseLogLevel());
//		domain.setOperationLogLevel(entity.getOperationLogLevel());
//		domain.setIsAnonymous(entity.getIsAnonymous());
//		domain.setAuthenticationMode(entity.getAuthenticationMode());
//		domain.setHelpStatus(entity.getHelpStatus());
//		domain.setRemark(entity.getRemark());
//		return domain;
//	}

	@Override
	public List<? extends SecurityResource> findResourcesByUser(UserSession user) {
		return functionpointService.findResourcesByUser(user);
	}

	@Override
	public List<? extends SecurityResource> findResourcesByDefault() {
		return functionpointService.findResourcesByDefault();
	}

	@Override
	public List<? extends SecurityResource> findResourcesByAnonymous(UserSession user) {
		return functionpointService.findResourcesByAnonymous(user);
	}

	
	@Override
	public List<FunctionPointNode> findAllCanBeAuthorized() {
		return PersistableUtil.toListBeans(functionpointService.findAllCanBeAuthorized(), new SetBean<FunctionPoint>() {
			@Override
			public FunctionPointNode setValue(FunctionPoint doMain) {
				return wrapBean(doMain);
			}
		});
	}
	@Override
	public List<FunctionPointNode> findCanBeAuthorizedWrapStructure(boolean isType) {
		List<FunctionPoint> all = functionpointService.findAllCanBeAuthorized();
		return wrapStructure(all, isType);
	}
	
	@Override
	public void save(FunctionPoint functionPoint, Long parentId) {
		//FunctionPoint functionPoint = wrapDomain(functionNode);
		functionpointService.save(functionPoint, parentId);
		//functionNode.setId(functionPoint.getId());
	}

	@Override
	public void update(FunctionPoint function, Long parentId) {
		functionpointService.update(function, parentId);
	}

	@Override
	public void deleteByIds(String deleteIds) {
		functionpointService.deleteByIds(deleteIds);
	}

	@Override
	public FunctionPointNode checkFunctionPointNameOnly(Long parentId,String name) {
		return wrapBean(functionpointService.checkFunctionPointNameOnly(parentId, name));
	}

	@Override
	public FunctionPointNode checkFunctionPointCodeOnly(String code) {
		return wrapBean(functionpointService.checkFunctionPointCodeOnly(code));
	}

	
	@Override
	public List<Long> findIdsByRoleId(Long adminRoleId) {
		return functionpointService.findIdsByRoleId(adminRoleId);
	}
	
	@Override
	public List<FunctionPointNode> findUnAuthorizeByUserIdWrapCompleteStructure(Long userId, Long orgId, boolean isType) {
		return wrapCompleteStructure(functionpointService.findUnAuthorizeByUserId(userId, orgId), isType);
	}

	@Override
	public List<FunctionPointNode> findByRoleIdWrapStructure(Long adminId,boolean isType) {
		List<FunctionPoint> roleFunctionPointList = functionpointService.findByRoleId(adminId);
		return wrapStructure(roleFunctionPointList, isType);
	}
	
	@Override
	public List<FunctionPointNode> findAllByRoleIdWrapStructure(Long roleId,boolean isType) {
		List<FunctionPoint> roleFunctionPointList = functionpointService.findAllByRoleId(roleId);
		return wrapStructure(roleFunctionPointList, isType);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecurityResourceBean> findResourcesByUser(UserSessionBasics userSession) {
		return (List<SecurityResourceBean>) functionpointService.findResourcesByUser(userSession);
	}
	///////////===================私有方法=========================////////////
	
	private List<FunctionPointNode> wrapCompleteStructure(List<FunctionPoint> list,boolean isType){
		if(ObjUtil.isEmpty(list)){
			return new ArrayList<FunctionPointNode>();
		}
		
		Map<Long,FunctionPoint> oldFn=PersistableUtil.toMap(list);
		
		//是否需要自动把库取出来
		if(isType){
			Map<Long,FunctionPoint> map=new TreeMap<Long,FunctionPoint>();
			for(FunctionPoint fp:list){
				FunctionPoint parent=getFunctionpointType(fp);
				if(ObjUtil.isNotEmpty(parent)){
					map.put(parent.getId(), parent);
					FunctionPoint top=parent.getFunctionPoint();
					if(ObjUtil.isNotEmpty(top) && ObjUtil.isEmpty(map.get(top.getId()))){
						map.put(top.getId(), top);
					}
				}
			}
			list.addAll(map.values());
			Map<Long,FunctionPoint> newMap=new TreeMap<Long,FunctionPoint>();
			for(FunctionPoint fp:list){
				FunctionPoint parent=fp.getFunctionPoint();
				if(ObjUtil.isNotEmpty(parent) && FunctionPointType.FUNCTIONPOINT.equals(parent.getFunctionPointType())){
					FunctionPoint fun=oldFn.get(parent.getId());
					if(ObjUtil.isEmpty(fun)){
						FunctionPoint funNew=newMap.get(parent.getId());
						if(ObjUtil.isEmpty(funNew)){
							parent.setHave(YesorNo.YES);
							newMap.put(parent.getId(), parent);
						}
					}
				}
			}
			list.addAll(newMap.values());
		}
		return wrapTree(list);
	}
	
	
	private List<FunctionPointNode> wrapStructure(List<FunctionPoint> list,boolean isType){
		if(ObjUtil.isEmpty(list)){
			return new ArrayList<FunctionPointNode>();
		}
		//是否需要自动把库取出来
		if(isType){
			Map<Long,FunctionPoint> map=new TreeMap<Long,FunctionPoint>();
			for(FunctionPoint fp:list){
				FunctionPoint parent=getFunctionpointType(fp);
				if(ObjUtil.isNotEmpty(parent)){
					map.put(parent.getId(), parent);
					FunctionPoint top=parent.getFunctionPoint();
					if(ObjUtil.isNotEmpty(top) && ObjUtil.isEmpty(map.get(top.getId()))){
						map.put(top.getId(), top);
					}
				}
			}
			list.addAll(map.values());
		}
		return wrapTree(list);
	}
	/**
	 * getFunctionpointType: 获取功能点的根节点，功能点必须挂在功能点库下，不然功能点无法赋值。 <br/>
	 * @author caijin
	 * @param fp
	 * @return
	 * @since JDK 1.7
	 */
	private FunctionPoint getFunctionpointType(FunctionPoint fp) {
		if(ObjUtil.isNotEmpty(fp)){
			FunctionPoint parent=fp.getFunctionPoint();
			if(ObjUtil.isNotEmpty(parent)){
				if(FunctionPointType.FUNCTIONPOINT.equals(parent.getFunctionPointType())){
//					System.out.println("============"+parent.getId());
					return getFunctionpointType(parent);
				}else if(FunctionPointType.TYPE.equals(parent.getFunctionPointType())){
					return parent;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<FunctionPointNode> wrapTree(List<FunctionPoint> list){
		final Map<Long,List> parentList=new HashMap<Long,List>();
		List<FunctionPointNode> listTree = TreeNodeTool.toResult(list,new TreeHandle<FunctionPoint,FunctionPointNode,Long>(){
			@Override
			public Long rootId() {
				return null;
			}
			@Override
			public boolean setBean(FunctionPoint value, FunctionPointNode node,FunctionPointNode parent) {
				return true;
			}
			@Override
			public FunctionPointNode newTreeNode(FunctionPoint value) throws IllegalArgumentException {
				return wrapBean(value);
			}

			@Override
			public Long getId(FunctionPointNode node) {
				if(ObjUtil.isNotEmpty(node)){
					return node.getId();
				}
				return null;
			}
			@Override
			public Long getParentId(FunctionPointNode node) {
				if(ObjUtil.isEmpty(node)){
					return null;
				}
				if(FunctionPointType.TYPE.equals(node.getFunctionPointType()) && ObjUtil.isEmpty(node.getFunctionPoint())){
					return null;
				}
				return node.getFunctionPoint().getId();
			}
			@Override
			public List<FunctionPointNode> children(FunctionPointNode parent) {
				List<TreeNode> list =parentList.get(parent.getId());
				if(list==null){
					parentList.put(parent.getId(), new ArrayList<TreeNode>());
					parent.setChildren(parentList.get(parent.getId()));
				}
				return parentList.get(parent.getId());
			}
		});
		return listTree;
	}

	@Override
	public List<FunctionPoint> findAuthorizeByUserId(Long userId) {
		
		return functionpointService.findAuthorizeByUserId(userId);
	}

	@Override
	public List<FunctionPoint> wrapStructure1(List<FunctionPoint> list,
			boolean b) {
		
		return functionpointService.wrapStructure(list, b);
	}

	@Override
	public BaseDao<FunctionPoint, Long> getDao() {
		return functionpointDao;
	}

	
}
