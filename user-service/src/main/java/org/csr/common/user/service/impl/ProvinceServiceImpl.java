package org.csr.common.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.ProvinceDao;
import org.csr.common.user.domain.Province;
import org.csr.common.user.service.ProvinceService;
import org.csr.core.Constants;
import org.csr.core.Param;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:ProvinceServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("provionaryService")
public class ProvinceServiceImpl extends SimpleBasisService<Province, Long> 
	implements ProvinceService {
	@Resource
	private ProvinceDao provinceDao;

	@Override
	public BaseDao<Province, Long> getDao() {
		return provinceDao;
	}
	
	public PagedInfo<Province> findRootLevelList(Page page){
		return provinceDao.findRootLevelList(page);
	}
	
	/**
	 * 删除
	 */
	@Override
	public void delete(String deleteIds) {
		deleteChildren(deleteIds);
		String[] ids = deleteIds.split(Constants.OPERATE_COLON);
		for(String id : ids){
			if(ObjUtil.isNotBlank(id)){
				provinceDao.deleteById(Long.valueOf(id));
			}
		}
	}
	/**
	 * @description:递归删除一个或多个对象的子类
	 * @param: 
	 * @return: void 
	 */
	private void deleteChildren(String deleteIds){
		String[] ids = deleteIds.split(Constants.OPERATE_COLON);
		for(String id : ids){
			if(ObjUtil.isNotBlank(id)){
				String dictIds = this.findChildrenIds(Long.valueOf(id));
				if(ObjUtil.isNotBlank(dictIds)){
					deleteChildren(dictIds);
				}
				provinceDao.deleteChildren(Long.valueOf(id));
			}
		}
	}
	
	/**
	 * @description:查找子类的id并且拼成字符串
	 * @param: 
	 * @return: String 
	 */
	private String findChildrenIds(Long id){
		List<Long> list = provinceDao.findChildrenIds(id);
		String ids = "";
		for(Long dictId : list){
			ids+=dictId + Constants.OPERATE_COLON;
		}
		if(ids != null && !"".equals(ids)){
			ids = ids.substring(0, ids.length()-1);
		}
		return ids;
	}
	/**
	 * checkProvinceName: 验证城市是否重复 ，如果 不传 [上级城市id] 默认在全局查询<br/>
	 * @author caijin
	 * @param prentId 上级城市id
	 * @param provName 城市id
	 * @return
	 * @since JDK 1.7
	 */
	@Override
	public boolean checkProvinceName(Long parentId, String provName) {
		Param nameParem = new AndEqParam("name", provName);
		Param parentIdParem = new AndEqParam("parentId", parentId);
		Province prov = provinceDao.existParam(parentIdParem,nameParem);
		if(ObjUtil.isNotEmpty(prov)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * checkUpdateProvinceName: 验证城市是否重复 ，如果 不传 [上级城市id] 默认在全局查询<br/>
	 * @author caijin
	 * @param prentId 上级城市id
	 * @param id 上级城市id
	 * @param provName 城市id
	 * @return
	 * @since JDK 1.7
	 */
	@Override
	public boolean checkUpdateProvinceName(Long parentId, Long id,String provName) {
		Param nameParem = new AndEqParam("name", provName);
		Param parentIdParem = new AndEqParam("parentId", parentId);
		Province prov = provinceDao.existParam(nameParem,parentIdParem);
		if (ObjUtil.isEmpty(prov) || prov.getId().equals(id)) {
			return false;
		}
		return true;
	}

	@Override
	public void update(Province prov) {
		Province p=provinceDao.findById(prov.getId());
		p.setCode(prov.getCode());
		p.setName(prov.getName());
		p.setParentId(prov.getParentId());
		p.setRank(prov.getRank());
		p.setRemark(prov.getRemark());
		provinceDao.update(p);
	}

}
