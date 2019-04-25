package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.Location;
import org.csr.common.user.entity.LocationBean;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisFacade;


/**
 * @(#)Sss.java 
 *       
 * ClassName:Location.java <br/>
 * Date: Wed Aug 23 15:49:29 CST 2017 <br/>
 * @author summy <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 位置 Facade接口
 */
public interface LocationFacade extends BasisFacade<LocationBean, Long>{

	/**
	 * save: 保存方法<br/>
	 * @author summy
	 * @param location 
	 */
	public LocationBean save(Location location);

	/**
	 * update: 编辑方法<br/>
	 * @author summy
	 * @param location 
	 */
	public LocationBean update(Location location);


	/**
	 * findDropDownList: 查询下拉的数据。如果当前有选择的，把选择的数据也查询到里面<br/>
	 * @author summy
	 * @param page
	 * @param selecteds
	 */
	public PagedInfo<LocationBean> findDropDownList(Page page,List<Long> selecteds);


	public boolean checkNameIsExist(Long id, String name);
}
