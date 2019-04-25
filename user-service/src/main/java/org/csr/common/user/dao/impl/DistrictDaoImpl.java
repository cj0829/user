package org.csr.common.user.dao.impl;

import org.csr.common.user.dao.DistrictDao;
import org.csr.common.user.domain.District;
import org.csr.common.user.entity.DistrictBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:DistrictDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("districtDao")
public class DistrictDaoImpl extends JpaDao<District,Long> implements DistrictDao {
	
	@Override
	public Class<District> entityClass() {
		return District.class;
	}

    /**
     * @description:查询区域表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<DistrictBean> findListPage(Page page) {
    	Finder finder=FinderImpl.create("select new org.csr.common.user.entity.DistrictBean(d.id,d.name,d.remark,d.root) from District d");
    	return findPage(page,finder);
    }

}
