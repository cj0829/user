/**
 * Project Name:user-Service
 * File Name:RemindTemplateFacadeImpl.java
 * Package Name:org.csr.common.user.facade.impl
 * Date:2016-9-2上午10:02:54
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.RemindTemplateDao;
import org.csr.common.user.domain.RemindTemplate;
import org.csr.common.user.entity.RemindTemplateBean;
import org.csr.common.user.facade.RemindTemplateFacade;
import org.csr.common.user.service.RemindTemplateService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.springframework.stereotype.Service;

/**
 * ClassName:RemindTemplateFacadeImpl.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-2上午10:02:54 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
@Service("remindTemplateFacade")
public class RemindTemplateFacadeImpl extends SimpleBasisFacade<RemindTemplateBean, RemindTemplate, Long> implements RemindTemplateFacade {
	
	@Resource
	private RemindTemplateService remindTemplateService;
	@Resource
	private RemindTemplateDao remindTemplateDao;
	
	@Override
	public RemindTemplateBean wrapBean(RemindTemplate doMain) {
		return RemindTemplateBean.wrapBean(doMain);
	}

//	@Override
//	public RemindTemplate wrapDomain(RemindTemplateBean entity) {
//		RemindTemplate doMain = new RemindTemplate(entity.getId());
//		doMain.setCode(entity.getCode());
//		doMain.setMailTemplate(entity.getMailTemplate());
//		doMain.setSmsTemplate(entity.getSmsTemplate());
//		doMain.setRemark(entity.getRemark());
//		return doMain;
//	}

	@Override
	public void update(RemindTemplate remindTemplate) {
		remindTemplateService.update(remindTemplate);
	}

	@Override
	public void save(RemindTemplate remindTemplate) {
		remindTemplateService.save(remindTemplate);
	}

	@Override
	public int deleteSimple(Long[] remindTemplateIds) {
		return remindTemplateService.deleteSimple(remindTemplateIds);
	}

	@Override
	public boolean checkAddRemindTemplateCode(String code) {
		return remindTemplateService.checkAddRemindTemplateCode(code);
	}

	@Override
	public boolean checkUpdateRemindTemplateCode(Long id, String code) {
		return remindTemplateService.checkUpdateRemindTemplateCode(id, code);
	}

	@Override
	public BaseDao<RemindTemplate, Long> getDao() {
		return remindTemplateDao;
	}

}

