package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.Province;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:ProvinceDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:08:50 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface ProvinceDao extends BaseDao<Province,Long> {

	/**
	 * 查询全部第一级（省）城市
	 * @param page 分查询
	 * @return
	 */
	PagedInfo<Province> findRootLevelList(Page page);

	/**
	 * 查询子集合ids
	 * @param id
	 * @return
	 */
	List<Long> findChildrenIds(Long id);

	/**
	 * 删除子集合
	 * @param parentId
	 */
	void deleteChildren(Long parentId);


}
