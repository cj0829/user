package org.csr.common.user.dao.impl;

import org.csr.common.user.dao.RemindTemplateDao;
import org.csr.common.user.domain.RemindTemplate;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;
 
/**
 * ClassName:RemindTemplateDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-21下午4:08:24 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("remindTemplateDao")
public class RemindTemplateDaoImpl extends JpaDao<RemindTemplate,Long> implements
		RemindTemplateDao {
	
	@Override
	public Class<RemindTemplate> entityClass() {
		return RemindTemplate.class;
	}

}
