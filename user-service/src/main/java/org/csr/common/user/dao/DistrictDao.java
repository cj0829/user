package org.csr.common.user.dao;

import org.csr.common.user.domain.District;
import org.csr.common.user.entity.DistrictBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:DistrictDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:09:28 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface DistrictDao extends BaseDao<District,Long> {
    /**
     * @description:查询区域表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author:wangxiujuan
     */
    public PagedInfo<DistrictBean> findListPage(Page page);

}
