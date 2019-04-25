/**
 * Project Name:common
 * File Name:HistoryFormDaoImpl.java
 * Package Name:org.csr.common.flow.dao.impl
 * Date:2014-4-8下午5:05:11
 * Copyright (c) 2014, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.dao.impl;

import org.csr.common.flow.dao.HistoryFormDao;
import org.csr.common.flow.domain.HistoryForm;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName: HistoryFormDaoImpl.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-4-8下午5:05:11 <br/>
 * 
 * @author yjY <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 * 
 */
@Repository("historyFormDao")
public class HistoryFormDaoImpl extends JpaDao<HistoryForm, Long> implements
		HistoryFormDao {

	@Override
	public Class<HistoryForm> entityClass() {
		return HistoryForm.class;
	}
}
