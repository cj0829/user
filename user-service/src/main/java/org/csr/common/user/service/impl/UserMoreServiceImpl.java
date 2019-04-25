package org.csr.common.user.service.impl;


import javax.annotation.Resource;

import org.csr.common.user.dao.UserMoreDao;
import org.csr.common.user.domain.UserMore;
import org.csr.common.user.service.UserMoreService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserMore.java <br/>
 * Date:     Thu Dec 01 10:06:41 CST 2016
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("userMoreService")
public class UserMoreServiceImpl extends SimpleBasisService<UserMore, Long> implements UserMoreService {
    @Resource
    private UserMoreDao userMoreDao;
    
    @Override
	public BaseDao<UserMore, Long> getDao() {
		return userMoreDao;
	}

    
	@Override
	public void save(UserMore userMore){
		//如果需要判断这里更改
		
		userMoreDao.save(userMore);
	}

	@Override
	public void update(UserMore userMore){
	
		//如果需要判断这里更改
		UserMore olduserMore = userMoreDao.findById(userMore.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
//		olduserMore.setU_userMore(userMore.getU_userMore());
//		olduserMore.setUserId(userMore.getUserId());
		olduserMore.setHomePhone(userMore.getHomePhone());
		olduserMore.setAddress(userMore.getAddress());

		userMoreDao.update(olduserMore);
	}
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		UserMore userMore = userMoreDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(userMore) || userMore.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(userMore)) {
		    return false;
		}
		return true;
	}

}
