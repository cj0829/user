package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.ProvinceDao;
import org.csr.common.user.domain.Province;
import org.csr.common.user.entity.ProvinceNode;
import org.csr.common.user.facade.ProvinceFacade;
import org.csr.common.user.service.ProvinceService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

@Service("provinceFacade")
public class ProvinceFacadeImpl extends SimpleBasisFacade<ProvinceNode, Province, Long> implements ProvinceFacade{

	@Resource
	private ProvinceService provinceService; 
	@Resource
	private ProvinceDao provinceDao; 
	@Override
	public void saveSimple(Province prov) {
		provinceService.saveSimple(prov);
	}

	@Override
	public void update(Province prov) {
		provinceService.update(prov);
	}

	@Override
	public void delete(String ids) {
		provinceService.delete(ids);
	}

	@Override
	public boolean checkProvinceName(Long parentId, String name) {
		return provinceService.checkProvinceName(parentId, name);
	}

	@Override
	public boolean checkUpdateProvinceName(Long parentId, Long id, String name) {
		return provinceService.checkUpdateProvinceName(parentId, id, name);
	}

	@Override
	public ProvinceNode wrapBean(Province doMain) {
		return ProvinceNode.wrapBean(doMain);
	}

	@Override
	public BaseDao<Province, Long> getDao() {
		return provinceDao;
	}

//	@Override
//	public Province wrapDomain(ProvinceNode bean) {
//		Province doMain = new Province();
//		doMain.setId(bean.getId());
//		doMain.setName(bean.getName());
//		doMain.setCode(bean.getCode());
//		doMain.setParentId(bean.getParentId());
//		doMain.setRemark(bean.getRemark());
//		doMain.setRank(bean.getRank());
//		return doMain;
//	}

}
