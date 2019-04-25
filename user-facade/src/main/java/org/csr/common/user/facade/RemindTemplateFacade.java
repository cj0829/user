/**
 * Project Name:user-facade
 * File Name:RemindTemplateFacade.java
 * Package Name:org.csr.common.user.facade
 * Date:2016-9-1下午3:23:58
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.user.facade;

import org.csr.common.user.domain.RemindTemplate;
import org.csr.common.user.entity.RemindTemplateBean;
import org.csr.core.persistence.service.BasisFacade;

/**
 * ClassName:RemindTemplateFacade.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-1下午3:23:58 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public interface RemindTemplateFacade extends BasisFacade<RemindTemplateBean, Long>{

	void update(RemindTemplate remindTemplate);

	void save(RemindTemplate remindTemplate);

	int deleteSimple(Long[] remindTemplateIds);

	boolean checkAddRemindTemplateCode(String code);

	boolean checkUpdateRemindTemplateCode(Long id, String code);

}

