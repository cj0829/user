package org.csr.common.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.csr.common.user.dao.GlobalMessageDao;
import org.csr.common.user.domain.GlobalMessage;
import org.csr.common.user.service.GlobalMessageService;
import org.csr.core.Param;
import org.csr.core.Persistable;
import org.csr.core.cache.CacheApi;
import org.csr.core.cache.CacheFactory;
import org.csr.core.constant.YesorNo;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.param.AndInParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:ExamMessage.java <br/>
 * Date:     Tue Sep 22 15:02:54 GMT+08:00 2015
 * @author   gongguiwen <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *  service实现
 */
@Service("globalMessageService")
public class GlobalMessageServiceImpl extends SimpleBasisService<GlobalMessage, Long> implements GlobalMessageService {
    @Resource
    private GlobalMessageDao globalMessageDao;
    
	public final static Map<Long,Map<Integer,GlobalMessage>> messageMap;
	static{
		messageMap = new HashMap<Long,Map<Integer,GlobalMessage>>();
	}
    
    @Override
	public BaseDao<GlobalMessage, Long> getDao() {
		return globalMessageDao;
	}

//	@Override
//	public List<GlobalMessage> findMessageContent(Integer[] messageTypes,Long userId) {
//		Map<Integer,GlobalMessage> map = messageMap.get(userId);
//		List<GlobalMessage> messageList=new ArrayList<GlobalMessage>();
//		if(ObjUtil.isNotEmpty(map)){
//			for(int i=0;i<messageTypes.length;i++){
//				GlobalMessage examMessage=map.remove(messageTypes[i]);
//				if(ObjUtil.isNotEmpty(examMessage)){
//					messageList.add(examMessage);
//				}
//			}
//			if(messageMap.size()>1000){
//				Iterator<Map.Entry<Long,Map<Integer,GlobalMessage>>> entries = messageMap.entrySet().iterator();
//				while (entries.hasNext()) {
//				    Map.Entry<Long,Map<Integer,GlobalMessage>> entry = entries.next();
//				    if(ObjUtil.isEmpty(messageMap.get(entry.getKey()))){
//				    	messageMap.remove(entry.getKey());
//				    }
//				}
//			}
//		}
//		return messageList;
//	}
	
//	@SuppressWarnings("rawtypes")
//	@Override
//	public void createMessage(Integer messageType, Persistable p, Long id,Long userId,String messageContent) {
//		GlobalMessage examMessage = new GlobalMessage();
//		examMessage.setMessageType(messageType);
//		examMessage.setObject(p);
//		if(MessageType.FORCESUBMITEXAM.equals(messageType)){
//			examMessage.setMessageContent("老师强制提交了您的考试！");
//		}else if(MessageType.UNIFIEDEXAM.equals(messageType)){
//			examMessage.setMessageContent("老师统一提交考生试卷！");
//		}else if(MessageType.WARNINGMESSAGE.equals(messageType)){
//			examMessage.setMessageContent(messageContent);
//		}else if(MessageType.RELEASEEXAMMESSAGE.equals(messageType)){
//			examMessage.setMessageContent("请您注意，您有新的考试！");
//		}else if(MessageType.MARKINGMESSAGE.equals(messageType)){
//			examMessage.setMessageContent("您有新的试卷需要批阅！");
//		}
//		globalMessageDao.save(examMessage);
//		
//		//TODO 2015.10.13--11.55 读取配置文件信息，根据配置文件的信息来判断缓存写到哪。假设读取到的配置文件是将缓存写到本地
//		//messageMap中键的组成是【缓存类型_用户ID_缓存对象ID】
//		//如果是试卷发布成功的消息，则key值只是类型和用户id。
//		String key="";
//		if(MessageType.RELEASEEXAMMESSAGE.equals(messageType) || MessageType.MARKINGMESSAGE.equals(messageType)){
//			key = messageType+"_"+userId;
//		}else{
//			key = messageType+"_"+userId+"_"+id;
//		}
//		String propertiesName = ParameterUtil.getParamValue("cache_type");
//		CacheApi cacheApi = CacheFactory.createApi(propertiesName);
//		cacheApi.createCache(key,examMessage);
//	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void createMessage(Long userId,Integer messageType, Persistable p,String key,String messageContent) {
		GlobalMessage globalMessage = new GlobalMessage();
		globalMessage.setMessageType(messageType);
		globalMessage.setObject(p);
		globalMessage.setMessageContent(messageContent);
		globalMessage.setIsRead(YesorNo.NO);
		globalMessage.setUserId(userId);
		globalMessageDao.save(globalMessage);
		//TODO 2015.10.13--11.55 读取配置文件信息，根据配置文件的信息来判断缓存写到哪。假设读取到的配置文件是将缓存写到本地
		//messageMap中键的组成是【缓存类型_用户ID_缓存对象ID】
		//如果是试卷发布成功,和阅卷指派的消息，则key值只是类型和用户id。
		CacheApi cacheApi = CacheFactory.createApi(PropertiesUtil.getConfigureValue("cache.type"));
		cacheApi.createCache(key,globalMessage);
	}

	@Override
	public PagedInfo<GlobalMessage> findByUserAndMessageType(Page page,Long userId, Integer[] messageTypes) {
		if(ObjUtil.isEmpty(userId)){
			return PersistableUtil.createPagedInfo(0, page, new ArrayList<GlobalMessage>(0));
		}
		PagedInfo<GlobalMessage> result = globalMessageDao.findByUserAndMessageType(page,userId,messageTypes);
		return result;
	}

	@Override
	public Long findCountUserMessageNum(List<Integer> messageTypes, Long userId,Integer isRead) {
		if(ObjUtil.isEmpty(userId) || ObjUtil.isEmpty(messageTypes)){
			return 0l;
		}
		List<Param> params = createParam(true);
		params.add(new AndInParam("messageType", messageTypes));
		params.add(new AndEqParam("userId", userId));
		params.add(new AndEqParam("isRead", isRead));
		return globalMessageDao.countParam(params);
	}
	
}






