package org.csr.common.flow.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.flow.domain.FlowStrategy;
import org.csr.common.flow.domain.TaskTemp;
import org.csr.common.flow.service.FlowStrategyService;
import org.csr.common.flow.service.TaskTempService;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;

/**
 * ClassName:ExamStrategy.java <br/>
 * Date: Tue Jun 23 12:04:34 CST 2015
 * 
 * @author n-caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 service实现
 */
public abstract class FlowStrategyServiceImpl<Strategy extends FlowStrategy>
		extends SimpleBasisService<Strategy, Long> implements
		FlowStrategyService<Strategy> {
	@Resource
	protected TaskTempService taskTempService;
	
	protected String taskTempName;
	
	//TODO :查询此处，需要加锁
	@Override
	public Strategy saveFindByOrgId(Long orgId) {
		if(ObjUtil.isEmpty(orgId)){
			Exceptions.success("", "无法创建新的策略");
		}
		List<Strategy> strategyList = getDao().findByParam(new AndEqParam("orgId", orgId));
		Strategy strategy = null;
		if (ObjUtil.isEmpty(strategyList)) {
			try {
				strategy = getDao().newInstance();
				strategy.setOrgId(orgId);
				//创建一个考试流程
				TaskTemp taskTemp=taskTempService.createExamFlow(taskTempName,orgId);
				strategy.setTaskTemp(taskTemp);
				getDao().save(strategy);
			} catch (InstantiationException | IllegalAccessException e) {
				Exceptions.success("", "无法创建新的策略");
			}
			return strategy;
		}else{
			boolean one=true;
			for (Strategy findObject : strategyList) {
				if(one){
					one=false;
					strategy=findObject;
					continue;
				}
				getDao().delete(findObject);
			}
		}
		return strategy;
	}

	@Override
	public void save(Strategy strategy) {
		// 如果需要判断这里更改
		getDao().save(strategy);
	}
	@Override
	public void update(Strategy strategy) {
		if(ObjUtil.isEmpty(strategy.getOrgId())){
			Exceptions.service("", "您必须选择域！");
		}
		// 如果需要判断这里更改
		Strategy oldexamStrategy = getDao().existParam(new AndEqParam("orgId", strategy.getOrgId()));
		if(ObjUtil.isEmpty(oldexamStrategy)){
			Exceptions.service("", "您要修改的策略不存在！");
		}
		// 目前只有这些策略
		oldexamStrategy.setIsDeleteApproval(strategy.getIsDeleteApproval());
		oldexamStrategy.setIsEditApproval(strategy.getIsEditApproval());
		oldexamStrategy.setIsNewApproval(strategy.getIsNewApproval());
		oldexamStrategy.setIsChangeCategoryApproval(strategy.getIsChangeCategoryApproval());
		getDao().update(oldexamStrategy);
	}

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		if(ObjUtil.isEmpty(name)){
			return false;
		}
		FlowStrategy examStrategy = getDao().existParam(new AndEqParam("name", name));
		if (ObjUtil.isNotEmpty(id)) {
			if (ObjUtil.isEmpty(examStrategy) || examStrategy.getId().equals(id)) {
				return false;
			}
		}
		if (ObjUtil.isEmpty(examStrategy)) {
			return false;
		}
		return true;
	}
}
