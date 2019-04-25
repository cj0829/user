package org.csr.common.user.entity;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

public class UserSafeResourceCollectionBean extends VOBase<Long> {

	private static final long serialVersionUID = 1L;

	/**id*/
	private Long id;
	private UserBean userBean;
	/**资源的id*/
	private SafeResourceCollectionBean safeResourceCollectionBean;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public UserBean getUserBean() {
		return userBean;
	}
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	public SafeResourceCollectionBean getSafeResourceCollectionBean() {
		return safeResourceCollectionBean;
	}
	public void setSafeResourceCollectionBean(SafeResourceCollectionBean safeResourceCollectionBean) {
		this.safeResourceCollectionBean = safeResourceCollectionBean;
	}
//	public static UserSafeResourceCollectionBean toBean(UserSafeResourceCollection doMain) {
//		UserSafeResourceCollectionBean bean = new UserSafeResourceCollectionBean();
//		bean.setId(doMain.getId());
//		User user=doMain.getUser();
//		if(ObjUtil.isNotEmpty(user)){
//			bean.setUserBean(UserResult.toBean(user));
//		}
//		SafeResourceCollection src=doMain.getSafeResourceCollection();
//		if(ObjUtil.isNotEmpty(src)){
//			bean.setSafeResourceCollectionBean(SafeResourceCollectionBean.toBean(src));
//		}
//		return bean;
//	}
	
	public static UserSafeResourceCollectionBean wrapBean(UserSafeResourceCollection doMain) {
		UserSafeResourceCollectionBean bean=new UserSafeResourceCollectionBean();
		bean.setId(doMain.getId());
		User user=doMain.getUser();
		if(ObjUtil.isNotEmpty(user)){
			bean.setUserBean(UserBean.wrapBean(user));
		}
		SafeResourceCollection src=doMain.getSafeResourceCollection();
		if(ObjUtil.isNotEmpty(src)){
			bean.setSafeResourceCollectionBean(SafeResourceCollectionBean.wrapBean(src));
		}
		return bean;
		
	}
}
