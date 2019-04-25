package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.GlobalMessage;
import org.csr.core.Persistable;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;

/*
 * @(#)Sss.java 
 *       
 * ClassName:ExamMessage.java <br/>
 * Date: Tue Sep 22 15:02:54 GMT+08:00 2015 <br/>
 * @author gongguiwen <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 *  service接口
 */
public interface GlobalMessageService extends BasisService<GlobalMessage, Long> {

	/**
	 * 查询消息
	 * @param messageType
	 * @param objectId
	 * @return
	 */
	//List<GlobalMessage> findMessageContent(Integer[] messageTypes,Long userId);
	
	/**
	 * 创建消息
	 * @param forcesubmitexam
	 * @param ue
	 * @param id
	 */
//	@SuppressWarnings("rawtypes")
//	void createMessage(Integer messageType, Persistable p, Long id,Long userId,String messageContent);
	
	@SuppressWarnings("rawtypes")
	void createMessage(Long userId,Integer messageType, Persistable p,String key,String messageContent);
	
	/**
	 * 查询用户指定类型的消息。
	 * @param page
	 * @param userId
	 * @param messageTypes
	 * @return
	 */
	public PagedInfo<GlobalMessage> findByUserAndMessageType(Page page,Long userId,Integer[] messageTypes);

	/**
	 * 查询用户指定消息类型个数。
	 * @param messageType
	 * @param id
	 * @param no
	 * @return
	 */
	Long findCountUserMessageNum(List<Integer> messageType, Long userId, Integer isRead);
}
