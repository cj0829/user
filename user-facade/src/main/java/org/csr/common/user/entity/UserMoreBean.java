package org.csr.common.user.entity;

import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserMore;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

public class UserMoreBean extends VOBase<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键：id */
	private Long id;
	/** 用户 */
	private UserBean user;
	/** 固定电话 */
	private String homePhone;
	/** 手机电话 */
	private String mobile;
	/** 通信地址 */
	private String address;
	/** 自定义字段1 */
	private String customize1;
	/** 自定义字段2 */
	private String customize2;
	/** 自定义字段3 */
	private String customize3;
	/** 自定义字段4 */
	private String customize4;
	/** 自定义字段5 */
	private String customize5;
	/** 自定义字段6 */
	private String customize6;
	/** 自定义字段7 */
	private String customize7;
	/** 自定义字段8 */
	private String customize8;
	/** 自定义字段9 */
	private String customize9;
	/** 自定义字段10 */
	private String customize10;

	// ==========================================================
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserBean getUser() {
		return user;
	}

	public void setUser(UserBean user) {
		this.user = user;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomize1() {
		return customize1;
	}

	public void setCustomize1(String customize1) {
		this.customize1 = customize1;
	}

	public String getCustomize2() {
		return customize2;
	}

	public void setCustomize2(String customize2) {
		this.customize2 = customize2;
	}

	public String getCustomize3() {
		return customize3;
	}

	public void setCustomize3(String customize3) {
		this.customize3 = customize3;
	}

	public String getCustomize4() {
		return customize4;
	}

	public void setCustomize4(String customize4) {
		this.customize4 = customize4;
	}

	public String getCustomize5() {
		return customize5;
	}

	public void setCustomize5(String customize5) {
		this.customize5 = customize5;
	}

	public String getCustomize6() {
		return customize6;
	}

	public void setCustomize6(String customize6) {
		this.customize6 = customize6;
	}

	public String getCustomize7() {
		return customize7;
	}

	public void setCustomize7(String customize7) {
		this.customize7 = customize7;
	}

	public String getCustomize8() {
		return customize8;
	}

	public void setCustomize8(String customize8) {
		this.customize8 = customize8;
	}

	public String getCustomize9() {
		return customize9;
	}

	public void setCustomize9(String customize9) {
		this.customize9 = customize9;
	}

	public String getCustomize10() {
		return customize10;
	}

	public void setCustomize10(String customize10) {
		this.customize10 = customize10;
	}

	public static UserMoreBean wrapBean(UserMore doMain) {
		UserMoreBean bean = new UserMoreBean();
		bean.setId(doMain.getId());
		User user = doMain.getUser();
		if (ObjUtil.isNotEmpty(user)) {
			bean.setUser(UserBean.wrapBean(user));
			bean.setMobile(user.getMobile());
		}
		bean.setHomePhone(doMain.getHomePhone());
		bean.setAddress(doMain.getAddress());
		bean.setCustomize1(doMain.getCustomize1());
		bean.setCustomize2(doMain.getCustomize2());
		bean.setCustomize3(doMain.getCustomize3());
		bean.setCustomize4(doMain.getCustomize4());
		bean.setCustomize5(doMain.getCustomize5());
		bean.setCustomize6(doMain.getCustomize6());
		bean.setCustomize7(doMain.getCustomize7());
		bean.setCustomize8(doMain.getCustomize8());
		bean.setCustomize9(doMain.getCustomize9());
		bean.setCustomize10(doMain.getCustomize10());
		return bean;
	}
}
