package  org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.LocationDao;
import org.csr.common.user.domain.Location;
import org.csr.common.user.entity.LocationBean;
import org.csr.common.user.facade.LocationFacade;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:Location.java <br/>
 * Date:     Wed Aug 23 15:49:29 CST 2017
 * @author   summy <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 位置 Facade实现
 */
 
@Service("locationFacade")
public class LocationFacadeImpl extends SimpleBasisFacade<LocationBean,Location, Long> implements LocationFacade {
	@Resource
	private LocationDao locationDao;

	@Override
	public BaseDao<Location, Long> getDao() {
		return locationDao;
	}
	
	@Override
	public LocationBean wrapBean(Location doMain) {
		return LocationBean.wrapBean(doMain);
	}
	
	@Override
	public LocationBean save(Location location){

   		//获取父机构
   		Location pNode = locationDao.findById(location.getParentId());
   		if(ObjUtil.isEmpty(pNode)){
   			pNode=locationDao.findById(Location.ROOT);
   		}
   		
   		checkParameter(location.getName(), "", "位置地点不能为空");
   		
   		//如果需要判断这里更改
		locationDao.save(location);
		return wrapBean(location);
	}

	@Override
	public LocationBean update(Location location){
	
		//如果需要判断这里更改
		Location oldlocation = locationDao.findById(location.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		oldlocation.setId(location.getId());
		oldlocation.setParentId(location.getParentId());
		oldlocation.setName(location.getName());
		oldlocation.setRemark(location.getRemark());
		oldlocation.setCode(location.getCode());
		oldlocation.setPath(location.getPath());

		locationDao.update(oldlocation);
		return wrapBean(oldlocation);
	}
	

	public PagedInfo<LocationBean> findDropDownList(Page page,List<Long> selecteds){
		PagedInfo<Location> pages = locationDao.findDropDownList(page,selecteds);
		return PersistableUtil.toPagedInfoBeans(pages, new SetBean<Location>() {
			@Override
			public LocationBean setValue(Location doMain) {
				return wrapBean(doMain);
			}
		});
	}
	
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		Location location = locationDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(location) || location.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(location)) {
		    return false;
		}
		return true;
	}

}
