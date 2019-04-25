package org.csr.common.user.service.impl;


import javax.annotation.Resource;

import org.csr.common.user.dao.LocationDao;
import org.csr.common.user.domain.Location;
import org.csr.common.user.service.LocationService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.springframework.stereotype.Service;

/**
 * ClassName:Location.java <br/>
 * Date:     Wed Aug 23 15:49:29 CST 2017
 * @author   summy <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 位置 service实现
 */
@Service("locationService")
public class LocationServiceImpl extends SimpleBasisService<Location, Long> implements LocationService {
    @Resource
    private LocationDao locationDao;
    
    @Override
	public BaseDao<Location, Long> getDao() {
		return locationDao;
	}
	
}
