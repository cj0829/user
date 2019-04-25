package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserSpecialFunctionPointDao;
import org.csr.common.user.domain.UserSpecialFunctionPoint;
import org.csr.common.user.entity.FunctionPointNode;
import org.csr.common.user.entity.UserSpecialFunctionPointNode;
import org.csr.common.user.facade.UserInitData;
import org.csr.common.user.facade.UserSpecialFunctionPointFacade;
import org.csr.common.user.service.UserSpecialFunctionPointService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("userSpecialFunctionPointFacade")
public class UserSpecialFunctionPointFacadeImpl extends SimpleBasisFacade<UserSpecialFunctionPointNode, UserSpecialFunctionPoint, Long> 
	implements UserSpecialFunctionPointFacade,UserInitData {
	
	@Resource
	UserSpecialFunctionPointService userSpecialFunctionPointService;
	@Resource
	UserSpecialFunctionPointDao userSpecialFunctionPointDao;

	
	@Override
	public UserSpecialFunctionPointNode wrapBean(UserSpecialFunctionPoint doMain) {
		return UserSpecialFunctionPointNode.wrapBean(doMain);
	}

	@Override
	public void saveUserSpecialFunctionPoint(Long userId,Long[] functionPointIds) {
		userSpecialFunctionPointService.saveUserSpecialFunctionPoint(userId, functionPointIds);
	}

	@Override
	public void deleteUserSpecialFunctionPoint(Long userId,Long[] functionPointIds) {
		userSpecialFunctionPointService.deleteUserSpecialFunctionPoint(userId, functionPointIds);
	}

	@Override
	public Object findExistEnableUserSpecialFunctionPoint(Long userId, Long root) {
		// TODO Auto-generated method stub
		return userSpecialFunctionPointService.findExistEnableUserSpecialFunctionPoint(userId, root);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object wrapCompleteStructure(Object findExistEnableUserSpecialFunctionPoint, boolean b) {
		// TODO Auto-generated method stub
		return userSpecialFunctionPointService.wrapCompleteStructure((List<UserSpecialFunctionPoint>) findExistEnableUserSpecialFunctionPoint, b);
	}

	@Override
	public List<FunctionPointNode> findExistEnableUserSpecialFunctionPointWrapCompleteStructure(Long userId, Long orgId, boolean isType) {
//		userSpecialFunctionPointService.wrapCompleteStructure(findExistEnableUserSpecialFunctionPoint, b)
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FunctionPointNode> findExistDisableUserSpecialFunctionPointWrapCompleteStructure(Long userId, Long orgId, boolean isType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delExistEnableUserSpecialFunctionPoint(List<Long> ids) {
		userSpecialFunctionPointService.delExistEnableUserSpecialFunctionPoint(ids);
	}

	@Override
	public void delExistDisableUserSpecialFunctionPoint(List<Long> ids) {
		userSpecialFunctionPointService.delExistDisableUserSpecialFunctionPoint(ids);
	}

	@Override
	public BaseDao<UserSpecialFunctionPoint, Long> getDao() {
		return userSpecialFunctionPointDao;
	}

	@Override
	public void delInitData(Long userId) {
		userSpecialFunctionPointDao.deleteByParams(new AndEqParam("user.id",userId));
	}
	
//	@Override
//	public List<UserSpecialFunctionPointNode> wrapCompleteStructure(List<UserSpecialFunctionPoint> list,boolean isType) {
//		if(ObjUtil.isEmpty(list)){
//			return new ArrayList<UserSpecialFunctionPointNode>(0);
//		}
//		
//		Map<Long,UserSpecialFunctionPoint> oldFn=ObjUtil.toMap(list);
//		//是否需要自动把库取出来
//		if(isType){
//			Map<Long,FunctionPoint> map=new TreeMap<Long,FunctionPoint>();
//			for(UserSpecialFunctionPoint fp:list){
//				FunctionPoint parent=getFunctionpointType(fp.getFunctionPoint());
//				if(ObjUtil.isNotEmpty(parent)){
//					map.put(parent.getId(), parent);
//					FunctionPoint top=parent.getFunctionPoint();
//					if(ObjUtil.isNotEmpty(top) && ObjUtil.isEmpty(map.get(top.getId()))){
//						map.put(top.getId(), top);
//					}
//				}
//			}
//			list.addAll(map.values());
//			Map<Long,FunctionPoint> newMap=new TreeMap<Long,FunctionPoint>();
//			for(FunctionPoint fp:list){
//				FunctionPoint parent=fp.getFunctionPoint();
//				if(ObjUtil.isNotEmpty(parent) && FunctionPointType.FUNCTIONPOINT.equals(parent.getFunctionPointType())){
//					FunctionPoint fun=oldFn.get(parent.getId());
//					if(ObjUtil.isEmpty(fun)){
//						FunctionPoint funNew=newMap.get(parent.getId());
//						if(ObjUtil.isEmpty(funNew)){
//							parent.setHave(YesorNo.YES);
//							newMap.put(parent.getId(), parent);
//						}
//					}
//				}
//			}
//			list.addAll(newMap.values());
//		}
//		return wrapTree(list);
//	}
	
//	/**
//	 * getFunctionpointType: 获取功能点的根节点，功能点必须挂在功能点库下，不然功能点无法赋值。 <br/>
//	 * @author caijin
//	 * @param fp
//	 * @return
//	 * @since JDK 1.7
//	 */
//	private FunctionPoint getFunctionpointType(FunctionPoint fp) {
//		if(ObjUtil.isNotEmpty(fp)){
//			FunctionPoint parent=fp.getFunctionPoint();
//			if(ObjUtil.isNotEmpty(parent)){
//				if(FunctionPointType.FUNCTIONPOINT.equals(parent.getFunctionPointType())){
//					return getFunctionpointType(parent);
//				}else if(FunctionPointType.TYPE.equals(parent.getFunctionPointType())){
//					return parent;
//				}
//			}
//		}
//		return null;
//	}
//	private List<FunctionPoint> wrapTree(List<FunctionPoint> list){
//		final Map<Long,ArrayList<FunctionPoint>> parentList=new HashMap<Long,ArrayList<FunctionPoint>>();
//		List<FunctionPoint> listTree = TreeNodeTool.toResult(list,new TreeHandle<FunctionPoint,FunctionPoint,Long>(){
//			public Long rootId() {
//				return null;
//			}
//			public boolean setBean(FunctionPoint value, FunctionPoint node,FunctionPoint parent) {
//				return true;
//			}
//			public FunctionPoint newTreeNode(FunctionPoint value)throws IllegalArgumentException {
//				return value;
//			}
//			public Long getId(FunctionPoint node) {
//				return node.getId();
//			}
//			public Long getParentId(FunctionPoint node) {
//				if(FunctionPointType.TYPE.equals(node.getFunctionPointType()) && ObjUtil.isEmpty(node.getFunctionPoint())){
//					return null;
//				}
//				return node.getFunctionPoint().getId();
//			}
//			public List<FunctionPoint> children(FunctionPoint parent) {
//				List<FunctionPoint> list =parentList.get(parent.getId());
//				if(list==null){
//					parentList.put(parent.getId(), new ArrayList<FunctionPoint>());
//					parent.setChildren(parentList.get(parent.getId()));
//				}
//				return parentList.get(parent.getId());
//			}
//		});
//		return listTree;
//	}

}
