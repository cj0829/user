package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserSpecialFunctionPointDao;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserSpecialFunctionPoint;
import org.csr.common.user.entity.UserSpecialFunctionPointNode;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.UserSpecialFunctionPointService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.support.CompareValue;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserSpecialFunctionPointServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("userSpecialFunctionPointService")
public class UserSpecialFunctionPointServiceImpl extends
		SimpleBasisService<UserSpecialFunctionPoint, Long> implements
		UserSpecialFunctionPointService {
	
	@Resource
	private UserSpecialFunctionPointDao userSpecialFunctionPointDao;
	@Resource
	private FunctionpointService functionpointService;

	@Override
	public BaseDao<UserSpecialFunctionPoint, Long> getDao() {
		return userSpecialFunctionPointDao;
	}
	

	
	/**
	 * 获取用户功能点
	 * @see org.csr.common.user.service.UserSpecialFunctionPointService#findByUserId(java.lang.Long)
	 */
	public List<UserSpecialFunctionPoint> findByUserId(Long userId) {
		List<UserSpecialFunctionPoint> rfpList = userSpecialFunctionPointDao.findByUserId(userId);
		return rfpList;
	}

	/**
	 * 保存用户功能点
	 * @see org.csr.common.user.service.UserSpecialFunctionPointService#saveUserSpecialFunctionPoint(java.lang.Long, java.lang.Long[])
	 */
	public void saveUserSpecialFunctionPoint(final Long userId,Long[] functionPointIds) {
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "请您选择用户");
		}
		if(ObjUtil.isEmpty(functionPointIds)){
			Exceptions.service("", "请您选择要关联的功能点");
		}
		//用角色中来的权限
		List<Long> existRoleFnIdList = functionpointService.findIdRoleFunctionPointByUserId(userId);
		//在特权中剪掉的权限
		final List<Long> exisYesFnIdList = functionpointService.findIdSpecialByUserId(userId, YesorNo.YES);
		//在特权中增加的权限
		final List<Long> existNoFnIdList = functionpointService.findIdSpecialByUserId(userId, YesorNo.NO);
		
		ObjUtil.compareId(existRoleFnIdList,ObjUtil.asList(functionPointIds) , new CompareValue<Long>(){
			@Override
			public void add(List<Long> addIds) {
				ObjUtil.compareId(exisYesFnIdList, addIds, new CompareValue<Long>(){
					@Override
					public void add(List<Long> fns) {
						if(ObjUtil.isEmpty(fns)){
							return;
						}
						for (Long id : fns) {
							List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
							if(ObjUtil.isNotEmpty(ufpList)){
								for (UserSpecialFunctionPoint uspf:ufpList) {
									userSpecialFunctionPointDao.deleteById(uspf.getId());
								}
							}else{
								UserSpecialFunctionPoint userSpecialFunctionPoint = new UserSpecialFunctionPoint();
								User user=new User();
								user.setId(userId);
								userSpecialFunctionPoint.setUser(user);
								userSpecialFunctionPoint.setFunctionPoint(new FunctionPoint(id));
								userSpecialFunctionPoint.setIsAddPrivilege(YesorNo.YES);
								userSpecialFunctionPointDao.save(userSpecialFunctionPoint);
							}
						}
					}
					@Override
					public void update(List<Long> obj) {
						
					}

					@Override
					public void delete(List<Long> fns) {
						if(ObjUtil.isEmpty(fns)){
							return;
						}
						for (Long id : fns) {
							List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
							if(ObjUtil.isNotEmpty(ufpList)){
								for (UserSpecialFunctionPoint uspf:ufpList) {
									userSpecialFunctionPointDao.deleteById(uspf.getId());
								}
							}
						}
					}
				});
			}
			@Override
			public void update(List<Long> obj) {
				
			}
			@Override
			public void delete(List<Long> delIds) {
				ObjUtil.compareId(existNoFnIdList, delIds, new CompareValue<Long>(){
					@Override
					public void add(List<Long> fns) {
						if(ObjUtil.isEmpty(fns)){
							return;
						}
						for (Long id : fns) {
							List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
							if(ObjUtil.isNotEmpty(ufpList)){
								for (UserSpecialFunctionPoint uspf:ufpList) {
									userSpecialFunctionPointDao.deleteById(uspf.getId());
								}
							}else{
								UserSpecialFunctionPoint userSpecialFunctionPoint = new UserSpecialFunctionPoint();
								User user=new User();
								user.setId(userId);
								userSpecialFunctionPoint.setUser(user);
								userSpecialFunctionPoint.setFunctionPoint(new FunctionPoint(id));
								userSpecialFunctionPoint.setIsAddPrivilege(YesorNo.NO);
								userSpecialFunctionPointDao.save(userSpecialFunctionPoint);
							}
						}
					}
					@Override
					public void update(List<Long> obj) {
						
					}
					@Override
					public void delete(List<Long> fns) {
						if(ObjUtil.isEmpty(fns)){
							return;
						}
						for (Long id : fns) {
							List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
							if(ObjUtil.isNotEmpty(ufpList)){
								for (UserSpecialFunctionPoint uspf:ufpList) {
									userSpecialFunctionPointDao.deleteById(uspf.getId());
								}
							}
						}
					}
					
				});
			}
		});
	}
	/**
	 * 保存用户功能点
	 * @see org.csr.common.user.service.UserSpecialFunctionPointService#saveUserSpecialFunctionPoint(java.lang.Long, java.lang.Long[])
	 */
	@Override
	public void updateUserSpecialFunctionPoint(final Long userId,Long[] functionPointIds) {
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "请您选择用户");
		}
//		if(ObjUtil.isEmpty(functionPointIds)){
//			Exceptions.service("", "请您选择要关联的功能点");
//		}
		
		List<Long> specialFnIds=functionpointService.findIdSpecialByUserId(userId,YesorNo.YES);
		
		ObjUtil.compareId(specialFnIds, ObjUtil.asList(functionPointIds), new CompareValue<Long>(){

			@Override
			public void add(List<Long> functionPointIds) {
				if(ObjUtil.isEmpty(functionPointIds)){
					return;
				}
				for (Long id : functionPointIds) {
					//查询是否，有特殊功能点，存在，如果存在，先删除
					List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
					if(ObjUtil.isNotEmpty(ufpList)){
						for (UserSpecialFunctionPoint uspf:ufpList) {
							userSpecialFunctionPointDao.deleteById(uspf.getId());
						}
					}else{
						UserSpecialFunctionPoint userSpecialFunctionPoint = new UserSpecialFunctionPoint();
						User user=new User();
						user.setId(userId);
						userSpecialFunctionPoint.setUser(user);
						userSpecialFunctionPoint.setFunctionPoint(new FunctionPoint(id));
						userSpecialFunctionPoint.setIsAddPrivilege(YesorNo.YES);
						userSpecialFunctionPointDao.save(userSpecialFunctionPoint);
					}
				}
			}
			

			@Override
			public void update(List<Long> obj) {
			}
			@Override
			public void delete(List<Long> functionPointIds) {
				if(ObjUtil.isEmpty(functionPointIds)){
					return;
				}
				for (Long id : functionPointIds) {
					//查询是否，有特殊功能点，存在，如果存在，先删除
					List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
					if(ObjUtil.isNotEmpty(ufpList)){
						for (UserSpecialFunctionPoint uspf:ufpList) {
							userSpecialFunctionPointDao.deleteById(uspf.getId());
						}
					}
				}
			}
		});
	}
	/**
	 * 保存用户功能点
	 * @see org.csr.common.user.service.UserSpecialFunctionPointService#saveUserSpecialFunctionPoint(java.lang.Long, java.lang.Long[])
	 */
	public void addUserSpecialFunctionPoint(Long userId,Long[] functionPointIds) {
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "请您选择用户");
		}
		if(ObjUtil.isEmpty(functionPointIds)){
			Exceptions.service("", "请您选择要关联的功能点");
		}
		//存在
		if (ObjUtil.isNotEmpty(functionPointIds)) {
			for (Long id : functionPointIds) {
				//查询是否，有特殊功能点，存在，如果存在，先删除
				List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
				if(ObjUtil.isNotEmpty(ufpList)){
					for (UserSpecialFunctionPoint uspf:ufpList) {
						if(YesorNo.NO.equals(uspf.getIsAddPrivilege())){
							userSpecialFunctionPointDao.deleteById(uspf.getId());
						}
					}
				}else{
					UserSpecialFunctionPoint userSpecialFunctionPoint = new UserSpecialFunctionPoint();
					User user=new User();
					user.setId(userId);
					userSpecialFunctionPoint.setUser(user);
					userSpecialFunctionPoint.setFunctionPoint(new FunctionPoint(id));
					userSpecialFunctionPoint.setIsAddPrivilege(YesorNo.YES);
					userSpecialFunctionPointDao.save(userSpecialFunctionPoint);
				}
			}
		}
	}
	
	/**
	 * 保存用户功能点
	 * @see org.csr.common.user.service.UserSpecialFunctionPointService#deleteUserSpecialFunctionPoint(java.lang.Long, java.lang.Long[])
	 */
	public void deleteUserSpecialFunctionPoint(Long userId,Long[] functionPointIds) {
		if(ObjUtil.isEmpty(userId)){
			Exceptions.service("", "请您选择用户");
		}
		if(ObjUtil.isEmpty(functionPointIds)){
			Exceptions.service("", "请您选择要撤掉的功能点");
		}
		//存在
		if (ObjUtil.isNotEmpty(functionPointIds)) {
			for (Long id : functionPointIds) {
				//查询是否，有特殊功能点，存在，如果存在，先删除
				List<UserSpecialFunctionPoint> ufpList = userSpecialFunctionPointDao.findByUserIdFunctionPointId(userId, id);
				if(ObjUtil.isNotEmpty(ufpList)){
					for (UserSpecialFunctionPoint uspf:ufpList) {
						if(YesorNo.YES.equals(uspf.getIsAddPrivilege())){
							userSpecialFunctionPointDao.deleteById(uspf.getId());
						}
					}
				}else{
					UserSpecialFunctionPoint userSpecialFunctionPoint = new UserSpecialFunctionPoint();
					User user=new User();
					user.setId(userId);
					userSpecialFunctionPoint.setUser(user);
					userSpecialFunctionPoint.setFunctionPoint(new FunctionPoint(id));
					userSpecialFunctionPoint.setIsAddPrivilege(YesorNo.NO);
					userSpecialFunctionPointDao.save(userSpecialFunctionPoint);
				}
			}
		}
	}

	/**
	 * toIdLong: 转换id <br/>
	 * @author caijin
	 * @param rfpList
	 * @return
	 * @since JDK 1.7
	 */
	protected Long[] toIdLong(List<UserSpecialFunctionPoint> ufpList) {
		if (ObjUtil.isNotEmpty(ufpList)) {
			List<Long> ids = new ArrayList<Long>();
			for (UserSpecialFunctionPoint id : ufpList) {
				if(ObjUtil.isNotEmpty(id.getId())){
					ids.add(id.getId());
				}
			}
			return ids.toArray(new Long[ufpList.size()]);
		}
		return null;
	}

	@Override
	public int delExistEnableUserSpecialFunctionPoint(List<Long> ids) {
		if(ObjUtil.isNotEmpty(ids)){
			return userSpecialFunctionPointDao.deleteByIds(ids);
		}
		return 0;
	}

	@Override
	public int delExistDisableUserSpecialFunctionPoint(List<Long> ids) {
		if(ObjUtil.isNotEmpty(ids)){
			return userSpecialFunctionPointDao.deleteByIds(ids);
		}
		return 0;
	}

	@Override
	public List<UserSpecialFunctionPoint> findExistEnableUserSpecialFunctionPoint(
			Long userId, Long root) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserSpecialFunctionPoint> findExistDisableUserSpecialFunctionPoint(
			Long userId, Long root) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserSpecialFunctionPointNode> wrapCompleteStructure(
			List<UserSpecialFunctionPoint> findExistEnableUserSpecialFunctionPoint,
			boolean b) {
		// TODO Auto-generated method stub
		return null;
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
