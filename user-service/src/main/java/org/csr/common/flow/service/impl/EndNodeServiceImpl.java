package org.csr.common.flow.service.impl;


import javax.annotation.Resource;

import org.csr.common.flow.dao.EndNodeDao;
import org.csr.common.flow.domain.EndNode;
import org.csr.common.flow.service.EndNodeService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:EndNode.java <br/>
 * Date:     Mon Jun 29 17:29:25 CST 2015
 * @author   n-caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("endNodeService")
public class EndNodeServiceImpl extends SimpleBasisService<EndNode, Long> implements EndNodeService {
    @Resource
    private EndNodeDao endNodeDao;
    
    @Override
	public BaseDao<EndNode, Long> getDao() {
		return endNodeDao;
	}
	
	@Override
	public void save(EndNode endNode){
		//如果需要判断这里更改
		endNodeDao.save(endNode);
	}

	@Override
	public void update(EndNode endNode){
		//如果需要判断这里更改
		EndNode oldendNode = endNodeDao.findById(endNode.getId());
		
		//大家需要检查一下，有些在domain对象中没有Commnt标签，是否需要在这里存在！
		oldendNode.setEndTime(endNode.getEndTime());

		endNodeDao.update(oldendNode);
	}
	
	@Override
	public boolean checkNameIsExist(Long id, String name) {
		EndNode endNode = endNodeDao.existParam(new AndEqParam("name",name));
		if (ObjUtil.isNotEmpty(id)) {
		    if (ObjUtil.isEmpty(endNode) || endNode.getId().equals(id)) {
			    return false;
			}
		}
		if (ObjUtil.isEmpty(endNode)) {
		    return false;
		}
		return true;
	}
}
