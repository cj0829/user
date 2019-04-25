package org.csr.common.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "u_userMore")
@Comment(ch = "更多用户信息", en = "u_userMore")
public class UserMore extends SimpleDomain<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 主键：id */
	private Long id;
	/** 用户 */
	private User user;
	/** 固定电话 */
	private String homePhone;
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

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "userId", referencedColumnName = "id", nullable = true, insertable = true, updatable = true) })
	@Comment(ch = "用户", en = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "homePhone", length = 20)
	@Comment(ch = "固定电话", en = "homePhone", len = 20)
	@Length(min = 0, max = 20)
	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	@Column(name = "address", length = 100)
	@Comment(ch = "通信地址", en = "address", len = 100)
	@Length(min = 0, max = 100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "customize1", length = 128)
	@Comment(ch = "自定义1", en = "customize1", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize1() {
		return customize1;
	}

	public void setCustomize1(String customize1) {
		this.customize1 = customize1;
	}

	@Column(name = "customize2", length = 128)
	@Comment(ch = "自定义2", en = "customize2", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize2() {
		return customize2;
	}

	public void setCustomize2(String customize2) {
		this.customize2 = customize2;
	}

	@Column(name = "customize3", length = 128)
	@Comment(ch = "自定义3", en = "customize3", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize3() {
		return customize3;
	}

	public void setCustomize3(String customize3) {
		this.customize3 = customize3;
	}

	@Column(name = "customize4", length = 128)
	@Comment(ch = "自定义4", en = "customize4", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize4() {
		return customize4;
	}

	public void setCustomize4(String customize4) {
		this.customize4 = customize4;
	}

	@Column(name = "customize5", length = 128)
	@Comment(ch = "自定义5", en = "customize5", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize5() {
		return customize5;
	}

	public void setCustomize5(String customize5) {
		this.customize5 = customize5;
	}

	@Column(name = "customize6", length = 128)
	@Comment(ch = "自定义6", en = "customize6", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize6() {
		return customize6;
	}

	public void setCustomize6(String customize6) {
		this.customize6 = customize6;
	}

	@Column(name = "customize7", length = 128)
	@Comment(ch = "自定义7", en = "customize7", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize7() {
		return customize7;
	}

	public void setCustomize7(String customize7) {
		this.customize7 = customize7;
	}

	@Column(name = "customize8", length = 128)
	@Comment(ch = "自定义8", en = "customize8", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize8() {
		return customize8;
	}

	public void setCustomize8(String customize8) {
		this.customize8 = customize8;
	}

	@Column(name = "customize9", length = 128)
	@Comment(ch = "自定义9", en = "customize9", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize9() {
		return customize9;
	}

	public void setCustomize9(String customize9) {
		this.customize9 = customize9;
	}

	@Column(name = "customize10", length = 128)
	@Comment(ch = "自定义10", en = "customize10", len = 128)
	@Length(min = 0, max = 128)
	public String getCustomize10() {
		return customize10;
	}

	public void setCustomize10(String customize10) {
		this.customize10 = customize10;
	}

}
