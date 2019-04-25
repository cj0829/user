package org.csr.common.user.dao.impl;


import java.util.Arrays;

import org.csr.common.user.dao.GlobalMessageDao;
import org.csr.common.user.domain.GlobalMessage;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;


/**
 * ClassName:ExamMessage.java <br/>
 * Date: Tue Sep 22 15:02:54 GMT+08:00 2015 <br/>
 * 
 * @author gongguiwen <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao实现
 */
@Repository("globalMessageDao")
public class GlobalMessageDaoImpl extends JpaDao<GlobalMessage,Long> implements GlobalMessageDao{

	@Override
	public Class<GlobalMessage> entityClass(){
		return GlobalMessage.class;
	}

	@Override
	public PagedInfo<GlobalMessage> findByUserAndMessageType(Page page,Long userId, Integer[] messageTypes) {
		Finder finder = FinderImpl.create("select p from GlobalMessage p where p.userId=:userId");
		finder.append(" and p.messageType in (:messageTypes)", "messageTypes",Arrays.asList(messageTypes));
		finder.insertEnd(" order by p.createTime desc");
		finder.setParam("userId",userId);
		return findPage(page, finder);
	}
}
