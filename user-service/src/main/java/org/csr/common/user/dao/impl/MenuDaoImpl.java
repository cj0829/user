/**
 * Project Name:common
 * File Name:MenuDaoImpl.java
 * Package Name:org.csr.common.user.dao.impl
 * Date:2014-2-17下午10:18:11
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.csr.common.user.constant.RoleType;
import org.csr.common.user.dao.MenuDao;
import org.csr.common.user.domain.Menu;
import org.csr.core.MenuNode;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName: MenuDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Repository("menuDao")
public class MenuDaoImpl extends JpaDao<Menu,Long> implements MenuDao{

	@Override
	public Class<Menu> entityClass() {
		return Menu.class;
	}

	@Override
	public List<? extends MenuNode> findByUserId(Long userId) {
		Finder finder=FinderImpl.create("select distinct fp from FunctionPoint fp, RoleFunctionPoint rfp,UserRole ur");
		finder.append(" where fp.id = rfp.functionPointId and rfp.roleId = ur.role.id and ur.user.id = :userId");
		//查询范围 admin role 功能点中
		finder.append(" and fp.id in (select rfp.functionPointId from RoleFunctionPoint rfp ,Role r ,User u where ");
		finder.append(" rfp.roleId = r.id and r.orgId=u.primaryOrgId and r.roleType = ").append(RoleType.SYSTEM);
		finder.append(" and u.id = :userId)");
		finder.setParam("userId", userId);
		return find(finder);
	}
	
	public List<? extends MenuNode> findChildById(Long id){
		MenuNode root = (MenuNode) findById(id);
		if(ObjUtil.isNotEmpty(root)){
			List<MenuNode> child=new ArrayList<MenuNode>();
			@SuppressWarnings("unchecked")
			List<MenuNode> childIds=(List<MenuNode>) findChilds(root.getId());
			child.add(root);
			child.addAll(childIds);
			if(ObjUtil.isEmpty(childIds)){
				return new ArrayList<>(0);
			}
			return child;
		}
		return new ArrayList<>(0);
	}
	
	
	public List<? extends MenuNode> findChilds(Long... ids){
		if(ObjUtil.isEmpty(ids)){
			return new ArrayList<>(0);
		}
		Finder finder=FinderImpl.create("select d,(select count(ds.id) from "+entityInfo.getEntityName()+" ds where ds.parent.id=d.id) from "+entityInfo.getEntityName()+" d where 1=1");
		finder.append(" d.parent.id in (:ids)", "ids",Arrays.asList(ids));
		List<Object[]> list = find(finder);
		List<MenuNode> childIds=hasChildToIds(list);
		if(ObjUtil.isEmpty(childIds)){
			return new ArrayList<>(0);
		}
		return childIds;
	}
	
	/**
	 * hasChildToIds: 检测统计查询，是否有子类对象。如果有子类对象的，拼接成ids <br/>
	 * @author caijin
	 * @param list 
	 * @return
	 * @since JDK 1.7
	 */
	protected List<MenuNode> hasChildToIds(List<Object[]> list){
		List<MenuNode> organizationList = new ArrayList<MenuNode>();
		for(int i=0;ObjUtil.isNotEmpty(list) && i<list.size();i++){
			Object[] obj = list.get(i);
			if(obj[1] != null && (Long)obj[1] >= 0){
				MenuNode node = (MenuNode) obj[0];
				Long count=(Long)obj[1];
				if(count>0){
					organizationList.addAll(findChilds(node.getId()));
				}
				organizationList.add(node);
			}
		}
		return organizationList;
	}

	@Override
	public List<Menu> findAllOrderByRank() {
		Finder finder=FinderImpl.create("select m from Menu m order by m.rank asc");
		finder.setCacheable(true);
		return find(finder);
	}
}

