package org.csr.common.user.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.SafeResourceCollectionDao;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.service.SafeResourceCollectionService;
import org.csr.common.user.service.SafeResourceService;
import org.csr.common.user.service.UserSafeResourceCollectionService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.domain.Organization;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:SafeResourceCollection.java <br/>
 * Date:     Tue Dec 01 17:02:25 CST 2015
 * @author   liurui-PC <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("safeResourceCollectionService")
public class SafeResourceCollectionServiceImpl extends SimpleBasisService<SafeResourceCollection, Long> implements SafeResourceCollectionService {
    @Resource
    private SafeResourceCollectionDao safeResourceCollectionDao;
    @Resource
    private UserSafeResourceCollectionService userSafeResourceCollectionService;
    @Resource
    private SafeResourceService safeResourceService; 
    
    @Override
	public BaseDao<SafeResourceCollection, Long> getDao() {
		return safeResourceCollectionDao;
	}
	
	@Override
	public void save(SafeResourceCollection safeResourceCollection){
		//如果需要判断这里更改
		
		safeResourceCollectionDao.save(safeResourceCollection);
	}

	@Override
	public void update(SafeResourceCollection safeResourceCollection){
	
		//如果需要判断这里更改
		SafeResourceCollection oldsafeResourceCollection = safeResourceCollectionDao.findById(safeResourceCollection.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		oldsafeResourceCollection.setId(safeResourceCollection.getId());
		oldsafeResourceCollection.setName(safeResourceCollection.getName());
		oldsafeResourceCollection.setRemarks(safeResourceCollection.getRemarks());
		oldsafeResourceCollection.setIsSystem(safeResourceCollection.getIsSystem());

		safeResourceCollectionDao.update(oldsafeResourceCollection);
	}
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		SafeResourceCollection safeResourceCollection = safeResourceCollectionDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(safeResourceCollection) || safeResourceCollection.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(safeResourceCollection)) {
		    return false;
		}
		return true;
	}
	@Override
	public Long createSystem(String orgName){
		SafeResourceCollection src=new SafeResourceCollection();
		src.setName(orgName+"系统资源");
		src.setRemarks("系统资源");
		src.setIsSystem(YesorNo.YES);
		safeResourceCollectionDao.save(src);
		return src.getId();
	}

	@Override
	public void delete(Long id) {
		boolean exist=userSafeResourceCollectionService.existByCollectionId(id);
		if(exist){
			Exceptions.service("", "用户正在使用此安全资源，您不能删除此安全资源！");
		}else{
			safeResourceService.delete(id);
			safeResourceCollectionDao.deleteById(id);
		}
	}

	@Override
	public int updateSystemNameByOrg(Organization organization) {
		int i=0;
		if(ObjUtil.isNotEmpty(organization)){
			List<SafeResourceCollection>  safeResourceCollectionList =  safeResourceCollectionDao.findByParam(new AndEqParam("root", organization.getId()),new AndEqParam("isSystem", YesorNo.YES));
			if(ObjUtil.isNotEmpty(safeResourceCollectionList)){
				for (SafeResourceCollection safeResourceCollection : safeResourceCollectionList) {
					safeResourceCollection.setName(organization.getName()+ "系统资源");
					safeResourceCollectionDao.update(safeResourceCollection);
					i++;
				}
			}
		}
		return i;
	}
}
