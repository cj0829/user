package org.csr.common.user.service;

import org.csr.common.user.domain.PointsRules;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:PointsRulesService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface PointsRulesService extends BasisService<PointsRules, Long> {
    /**
     * findById: 根据id取积分规则表明细 <br/>
     * @author caijin
     * @param id
     * @return
     * @since JDK 1.7
     */
    public PointsRules findById(Long id);
    
    /**
     * @description: 查询积分规则，根据功能点id
     * @param: page：
     * @return: PointsRules 返回积分规则
     * @author:
     */
    public PointsRules findByFunctionPointId(Long root,Long functionPointId);

	/**
	 * checkUpdateLoginName:
	 * @author huayj
	 * @param id
	 * @param loginName
	 * @return
	 * @return boolean
	 * @date&time 2016-1-7 下午4:22:23
	 * @since JDK 1.7
	 */
	public boolean checkUpdateName(Long id, String name);

}
