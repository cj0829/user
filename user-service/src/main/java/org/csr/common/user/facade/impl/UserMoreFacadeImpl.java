package  org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserMoreDao;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserMore;
import org.csr.common.user.entity.UserMoreBean;
import org.csr.common.user.facade.UserMoreFacade;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.util.ObjUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service("userMoreFacade")
@Lazy(false)
public class UserMoreFacadeImpl extends SimpleBasisFacade<UserMoreBean,UserMore, Long> implements UserMoreFacade {
	@Resource
	private UserMoreDao userMoreDao;

	@Override
	public BaseDao<UserMore, Long> getDao() {
		return userMoreDao;
	}
	
	@Override
	public UserMoreBean wrapBean(UserMore doMain) {
		return UserMoreBean.wrapBean(doMain);
	}
	@Override
	public UserMoreBean findByUserId(Long userId) {
		checkParameter(userId, "请选择要修改的用户");
		UserMore userMore=userMoreDao.findById(userId);
		if(ObjUtil.isNotEmpty(userMore)){
			return wrapBean(userMore);
		}
		return null;
	}
	
	@Override
	public void updateOrCreateUser(UserMore userMore,Long userId) {
		checkParameter(userId,"请选择要修改的用户");
		UserMore olduserMore = userMoreDao.findById(userId);
		if(ObjUtil.isNotEmpty(olduserMore)){
			olduserMore.setHomePhone(userMore.getHomePhone());
			olduserMore.setAddress(userMore.getAddress());
			olduserMore.setCustomize1(userMore.getCustomize1());
			olduserMore.setCustomize2(userMore.getCustomize2());
			olduserMore.setCustomize3(userMore.getCustomize3());
			olduserMore.setCustomize4(userMore.getCustomize4());
			olduserMore.setCustomize5(userMore.getCustomize5());
			olduserMore.setCustomize6(userMore.getCustomize6());
			olduserMore.setCustomize7(userMore.getCustomize7());
			olduserMore.setCustomize8(userMore.getCustomize8());
			olduserMore.setCustomize9(userMore.getCustomize9());
			olduserMore.setCustomize10(userMore.getCustomize10());
			userMoreDao.update(olduserMore);
		}else{
			UserMore userMore2=new UserMore();
			userMore2.setId(userId);
			userMore2.setUser(new User(userId));
			userMore2.setHomePhone(userMore.getHomePhone());
			userMore2.setAddress(userMore.getAddress());
			userMore2.setCustomize1(userMore.getCustomize1());
			userMore2.setCustomize2(userMore.getCustomize2());
			userMore2.setCustomize3(userMore.getCustomize3());
			userMore2.setCustomize4(userMore.getCustomize4());
			userMore2.setCustomize5(userMore.getCustomize5());
			userMore2.setCustomize6(userMore.getCustomize6());
			userMore2.setCustomize7(userMore.getCustomize7());
			userMore2.setCustomize8(userMore.getCustomize8());
			userMore2.setCustomize9(userMore.getCustomize9());
			userMore2.setCustomize10(userMore.getCustomize10());
			userMoreDao.save(userMore2);
		}
	}

	@Override
	public void delInitData(Long userId) {
		userMoreDao.deleteById(userId);
	}
}
