package org.csr.common.user.dao;

import org.csr.common.user.domain.GlobalMessage;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:ExamMessage.java <br/>
 * Date: Tue Sep 22 15:02:54 GMT+08:00 2015 <br/>
 * 
 * @author gongguiwen <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  dao接口
 */
public interface GlobalMessageDao extends BaseDao<GlobalMessage,Long>{

	/**
	 * 根据用户查询用户指定类型的消息，并且按照时间倒序排序。
	 * @param page
	 * @param userId
	 * @param messageTypes
	 * @return
	 */
	PagedInfo<GlobalMessage> findByUserAndMessageType(Page page, Long userId,Integer[] messageTypes);



}
