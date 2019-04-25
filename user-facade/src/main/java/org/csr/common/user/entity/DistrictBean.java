package org.csr.common.user.entity;

import org.csr.core.web.bean.VOBase;


/**
 * ClassName:DistrictResult.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class DistrictBean extends VOBase<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 946008722913646517L;
	private Long id;
    private String name;
    private String remark;

    public DistrictBean(Long id, String name, String remark) {
		this.id = id;
		this.name = name;
		this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
