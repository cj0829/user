package org.csr.common.user.entity;

import org.csr.common.user.domain.District;
import org.csr.common.user.domain.DistrictProvince;
import org.csr.common.user.domain.Province;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;


/**
 * ClassName:DistrictProvinceResult.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class DistrictProvinceBean extends VOBase<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4127714783764973963L;
	private Long id;
    private Long districtId;
    private Long provinceId;
    private String provinceName;

    public DistrictProvinceBean(Long id, Long districtId, Long provinceId, String provinceName) {
	this.id = id;
	this.districtId = districtId;
	this.provinceId = provinceId;
	this.provinceName = provinceName;
    }

    public DistrictProvinceBean() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

	public static DistrictProvinceBean wrapBean(DistrictProvince doMain) {
		DistrictProvinceBean bean=new DistrictProvinceBean();
		bean.setId(doMain.getId());
		District distinct=doMain.getDistrict();
		if(ObjUtil.isNotEmpty(distinct)){
			bean.setDistrictId(distinct.getId());
		}
		Province province=doMain.getProvince();
		if(ObjUtil.isNotEmpty(province)){
			bean.setProvinceId(province.getId());
			bean.setProvinceName(province.getName());
		}
		return bean;
	}

}
