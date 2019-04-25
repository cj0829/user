package org.csr.common.user.service;

import org.csr.common.user.domain.District;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:DistrictService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface DistrictService extends BasisService<District,Long>{

	/**
	 * save: 保存区域.需要在保存是检查name是否重复。 <br/>
	 * @author caijin
	 * @param district
	 * @return
	 * @since JDK 1.7
	 */
	public boolean save(District district);
    /**
     * @description:保存区域
     * @param: Parameter：区域对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public boolean save(District district,String provinceIds);

    /**
     * @description:修改区域信息
     * @param: Parameter：区域对象
     * @return: void
     * @author:wangxiujuan
     */
    public void update(District district,String provinceIds);
    

    /**
     * @description:用于验证添加区域时区域名唯一
     * @param: Parameter：区域对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public boolean checkAddDistrictName(String name);

    /**
     * @description:用于验证修改区域时区域名唯一
     * @param: Parameter：区域对象
     * @return: boolean
     * @author:wangxiujuan
     */
    public boolean checkUpdateDistrictName(Long id,String name);
    
    /**
     * 检查名称是否已经存在。
     * @param id
     * @param name
     * @return
     */
	public boolean checkNameIsExist(Long id, String name);

}
