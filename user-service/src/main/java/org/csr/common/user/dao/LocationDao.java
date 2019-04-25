package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.Location;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:Location.java <br/>
 * Date: Wed Aug 23 15:49:29 CST 2017 <br/>
 * 
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 位置 dao接口
 */
public interface LocationDao extends BaseDao<Location,Long>{

	public PagedInfo<Location> findDropDownList(Page page,List<Long> selecteds);
}
