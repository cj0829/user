package org.csr.common.user.entity;
import org.csr.common.user.domain.SafeResource;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.core.AutoSetProperty;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:SafeResource.java <br/>
 * System Name：    签到系统  <br/>
 * Date:     2015-11-20上午9:51:15 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class SafeResourceBean extends VOBase<Long> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**id*/
	private Long id;
	/**编码*/
	private String code;
	/**类型:SafeResourceType*/
	private Integer type;
	/**安全资源集*/
	@AutoSetProperty(message="id")
	private SafeResourceCollectionBean safeResourceCollectionBean;
	/**梢*/
	private Long tipId;
	/**梢类型*/
	private Integer tipType;

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public SafeResourceCollectionBean getSafeResourceCollectionBean() {
		return safeResourceCollectionBean;
	}
	public void setSafeResourceCollectionBean(SafeResourceCollectionBean safeResourceCollectionBean) {
		this.safeResourceCollectionBean = safeResourceCollectionBean;
	}
	public Long getTipId() {
		return tipId;
	}
	public void setTipId(Long tipId) {
		this.tipId = tipId;
	}
	public Integer getTipType() {
		return tipType;
	}
	public void setTipType(Integer tipType) {
		this.tipType = tipType;
	}
	
	public static SafeResourceBean wrapBean(SafeResource doMain) {
		SafeResourceBean bean = new SafeResourceBean();
		bean.setId(doMain.getId());
		bean.setCode(doMain.getCode());
		bean.setType(doMain.getType());
		SafeResourceCollection src=doMain.getSafeResourceCollection();
		if(ObjUtil.isNotEmpty(src)){
			bean.setSafeResourceCollectionBean(SafeResourceCollectionBean.wrapBean(src));
		}
		bean.setTipId(doMain.getTipId());
		bean.setTipType(doMain.getTipType());
		return bean;
	}
}
