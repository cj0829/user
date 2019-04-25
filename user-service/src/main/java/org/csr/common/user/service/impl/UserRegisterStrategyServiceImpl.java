package org.csr.common.user.service.impl;

import javax.annotation.Resource;

import org.csr.common.flow.domain.TaskTemp;
import org.csr.common.flow.service.impl.FlowStrategyServiceImpl;
import org.csr.common.user.dao.UserRegisterStrategyDao;
import org.csr.common.user.domain.UserRegisterStrategy;
import org.csr.common.user.service.UserRegisterStrategyService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserRegisterStrategy.java <br/>
 * Date: Thu Jul 02 17:32:27 CST 2015
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 用户策略 service实现
 */
@Service("userRegisterStrategyService")
public class UserRegisterStrategyServiceImpl extends
		FlowStrategyServiceImpl<UserRegisterStrategy> implements
		UserRegisterStrategyService {
	@Resource
	private UserRegisterStrategyDao userRegisterStrategyDao;

	@Override
	public BaseDao<UserRegisterStrategy, Long> getDao() {
		return userRegisterStrategyDao;
	}
	public UserRegisterStrategyServiceImpl() {
		this.taskTempName="用户注册审批";
	}
	
	@Override
	public UserRegisterStrategy saveFindByOrgId(Long orgId) {
		UserRegisterStrategy strategy = getDao().existParam(new AndEqParam("orgId", orgId));
		if (ObjUtil.isEmpty(strategy)) {
			try {
				strategy = getDao().newInstance();
				strategy.setIsRegister(YesorNo.NO);
				strategy.setOrgId(orgId);
				//创建一个考试流程
				TaskTemp taskTemp=taskTempService.createExamFlow(taskTempName,orgId);
				strategy.setTaskTemp(taskTemp);
				getDao().save(strategy);
			} catch (InstantiationException | IllegalAccessException e) {
				Exceptions.success("", "无法创建新的策略");
			}
		}
		return strategy;
	}
	
	@Override
	public void update(UserRegisterStrategy strategy) {
		if(ObjUtil.isEmpty(strategy.getOrgId())){
			Exceptions.service("", "您必须选择域！");
		}
		// 如果需要判断这里更改
		UserRegisterStrategy oldexamStrategy = getDao().existParam(new AndEqParam("orgId", strategy.getOrgId()));
		if(ObjUtil.isEmpty(oldexamStrategy)){
			Exceptions.service("", "您要修改的策略不存在！");
		}
		// 目前只有这些策略
		oldexamStrategy.setIsRegister(strategy.getIsRegister());
		getDao().update(oldexamStrategy);
	}
	@Override
	public UserRegisterStrategy update(Long strategyId, Byte enable) {
		UserRegisterStrategy strategy = userRegisterStrategyDao.findById(strategyId);
		if (ObjUtil.isEmpty(strategy)) {
			Exceptions.service("", "您要修改的策略不存在！");
		}
		strategy.setIsRegister(enable);
		userRegisterStrategyDao.update(strategy);
		return strategy;
	}
}
