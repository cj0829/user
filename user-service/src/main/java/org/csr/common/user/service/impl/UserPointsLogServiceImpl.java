package org.csr.common.user.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.csr.common.user.dao.UserPointsLogDao;
import org.csr.common.user.domain.PointsRules;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserPointsLog;
import org.csr.common.user.service.PointsRulesService;
import org.csr.common.user.service.UserPointsLogService;
import org.csr.common.user.service.UserService;
import org.csr.core.SecurityResource;
import org.csr.core.UserSession;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.queue.Message;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Repository;

/**
 * ClassName:UserPointsLogServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("userPointsLogService")
public class UserPointsLogServiceImpl  extends SimpleBasisService<UserPointsLog, Long>
	implements UserPointsLogService {

	@Resource
	UserPointsLogDao userPointsLogDao;
	@Resource
	UserService userService;
	@Resource
	PointsRulesService pointsRulesService;
	@Override
	public BaseDao<UserPointsLog, Long> getDao() {
		return userPointsLogDao;
	}
	/**
	 * @description:查询积分规则表信息
	 * @param: page：分页信息
	 * @return: ResultInfo
	 * @author:wangxiujuan
	 */
	public void changeUserPoint(Long userId, PointsRules pointsRules) {
		User user = userService.findById(userId);
		if (ObjUtil.isEmpty(user)) {
			return ;
		}
		if (ObjUtil.isEmpty(pointsRules)) {
			return ;
		}
		UserPointsLog pl = new UserPointsLog();
		pl.setUserId(userId);
		pl.setOperation(pointsRules.getOperation());
		pl.setOperationDate(new Date());
		pl.setPoints1(pointsRules.getPoints1());
		pl.setPoints2(pointsRules.getPoints2());
		// 保存积分日志
		userPointsLogDao.save(pl);
		Long[] points = userPointsLogDao.sumUserPoint(user.getId());
		//TODO 2017-05-28 09:52 ，积分日志，还需要重新设计
//		user.setPoints1(points[0]!=null?points[0].intValue():0);
//		user.setPoints2(points[1]!=null?points[1].intValue():0);
		userService.saveSimple(user);
	}
	@Override
	public void processMessages(Message<UserSession> message) {
		try {
			if(ObjUtil.isNotEmpty(message)){
				UserSession user = message.body();
				SecurityResource security=user.getSecurityResource();
				if(ObjUtil.isNotEmpty(security)){
					PointsRules pr = pointsRulesService.findByFunctionPointId(user.getPrimaryOrgId(), security.getId());
					changeUserPoint(user.getUserId(), pr);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
