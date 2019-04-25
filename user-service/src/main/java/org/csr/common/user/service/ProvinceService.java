package org.csr.common.user.service;

import org.csr.common.user.domain.Province;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;
 

/**
 * ClassName:ProvinceService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface ProvinceService extends BasisService<Province, Long> {
 
	/**
	 * findRootLevelList: 查询第一级的行政区对象 并返回 分页对象 <br/>
	 * @author caijin
	 * @param page 
	 * @return
	 * @since JDK 1.7
	 */
	public PagedInfo<Province> findRootLevelList(Page page);
	
	/**
	 * @description:删除一个或多个对象
	 * 				先删除子类对象
	 * @param: 
	 * @return: void 
	 */
	public void delete(String deleteIds);

	/**
	 * checkProvinceName: 验证城市是否重复 ，如果 不传 [上级城市id] 默认在全局查询<br/>
	 * @author caijin
	 * @param prentId 上级城市id
	 * @param provName 城市id
	 * @return
	 * @since JDK 1.7
	 */
	public boolean checkProvinceName(Long parentId,String provName); 
	
	
	/**
	 * checkUpdateProvinceName: 验证城市是否重复 ，如果 不传 [上级城市id] 默认在全局查询<br/>
	 * @author caijin
	 * @param prentId 上级城市id
	 * @param id 上级城市id
	 * @param provName 城市id
	 * @return
	 * @since JDK 1.7
	 */
	public boolean checkUpdateProvinceName(Long parentId,Long id,String provName);

	/**
	 * 
	 * update: 修改行政区 <br/>
	 * @param prov
	 * @author liurui
	 * @date 2016-1-7
	 * @since JDK 1.7
	 */
	public void update(Province prov);

}
