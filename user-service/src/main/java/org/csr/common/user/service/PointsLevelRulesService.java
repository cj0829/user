package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.PointsLevelRules;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:PointsLevelRulesService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface PointsLevelRulesService extends BasisService<PointsLevelRules, Long> {
	/**
     * @description:查询积分等级表信息
     * @param: points：分数
     * @return: List
     * @author:wangxiujuan
     */
    public List<PointsLevelRules> findListByPoints(Integer points);
    
    /**
     * @description:查询积分等级表信息
     * @param: points：分数
     * @return: List
     * @author:wangxiujuan
     */
    public PointsLevelRules findListByLevel(Integer level);

	/**
	 * checkUpdateName:
	 * @author huayj
	 * @param id
	 * @param levelName
	 * @return
	 * @return boolean
	 * @date&time 2016-1-7 下午4:31:33
	 * @since JDK 1.7
	 */
	public boolean checkUpdateName(Long id, String levelName);
}
