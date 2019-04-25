package org.csr.common.user.service.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.DistrictDao;
import org.csr.common.user.domain.District;
import org.csr.common.user.service.DistrictProvinceService;
import org.csr.common.user.service.DistrictService;
import org.csr.core.Param;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:DistrictServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-18上午10:34:09 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("districtService")
public class DistrictServiceImpl extends SimpleBasisService<District, Long>
		implements DistrictService {

	@Resource
	private DistrictDao districtDao;
	@Resource
	private DistrictProvinceService districtProvinceService;

	@Override
	public BaseDao<District, Long> getDao() {
		return districtDao;
	}

	/**
	 * 保存区域.需要在保存是检查name是否重复。
	 * @see org.csr.core.service.SimpleBasisService#save(java.lang.Object)
	 */
	public boolean save(District district) {
		if (!checkAddDistrictName(district.getName())) {
			districtDao.save(district);
			return true;
		}
		return false;
	}

	/**
	 * @description:保存区域
	 * @param: Parameter：区域对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean save(District district, String provinceIds) {
		if (!checkAddDistrictName(district.getName())) {
			districtDao.save(district);
			Long districtIdLong = district.getId();
			districtProvinceService.save(districtIdLong, provinceIds);
			return true;
		}
		return false;
	}

	/**
	 * @description:修改区域信息
	 * @param: Parameter：区域对象
	 * @return: void
	 * @author:wangxiujuan
	 */
	public void update(District district, String provinceIds) {
		districtDao.update(district);
		districtProvinceService.save(district.getId(), provinceIds);
	}

	/**
	 * @description:用于验证添加区域名是否唯一
	 * @param: Parameter:区域对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean checkAddDistrictName(String name) {
		District para = existDistrictName(name);
		if (ObjUtil.isNotEmpty(para)) {
			return true;
		}
		return false;
	}

	/**
	 * @description:用于验证修改区域名是否唯一
	 * @param: Parameter:区域对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean checkUpdateDistrictName(Long id, String name) {
		District para = existDistrictName(name);
		if (ObjUtil.isEmpty(para) || para.getId().equals(id)) {
			return false;
		}
		return true;
	}

	/**
	 * 查询是否存在name
	 * 
	 * @param name
	 * @return
	 */
	private District existDistrictName(String name) {
		Param param = new AndEqParam("name", name);
		return districtDao.existParam(param);
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		District district = districtDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(district) || district.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(district)) {
		    return false;
		}
		return true;
	}

}
