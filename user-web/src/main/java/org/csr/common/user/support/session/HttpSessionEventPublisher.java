package org.csr.common.user.support.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;

import org.csr.core.Constants;
import org.csr.core.UserSession;
import org.csr.core.cache.CacheFactory;
import org.csr.core.context.SecurityContextHolder;
import org.csr.core.listener.CloudHttpSessionListener;
import org.csr.core.queue.QueueService;
import org.csr.core.security.event.HttpSessionCreatedEvent;
import org.csr.core.security.event.HttpSessionDestroyedEvent;
import org.csr.core.security.message.LogoutMessage;
import org.csr.core.security.session.SessionRegistryFactory;
import org.csr.core.util.ClassBeanFactory;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * ClassName:HttpSessionEventPublisher.java <br/>
 * System Name：    csr <br/>
 * Date:     2014-2-20下午1:24:34 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class HttpSessionEventPublisher extends CloudHttpSessionListener {
	public void sessionCreated(HttpSessionEvent event) {
		HttpSessionCreatedEvent e = new HttpSessionCreatedEvent(event.getSession());
		getContext(event.getSession().getServletContext()).publishEvent(e);
	}
	/**
	 *  修改用户退出
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSessionDestroyedEvent e = new HttpSessionDestroyedEvent(event.getSession());
		UserSession securityContext = (UserSession)event.getSession().getAttribute(Constants.SECURITY_CONTEXT_KEY);
		//发送消息。用户保存。直接调用。或者就发生命令，
		if(ObjUtil.isNotEmpty(securityContext)){
			System.out.println("关闭系统");
			SessionRegistryFactory.getInstance().removeSessionInformation(event.getSession().getId());
			SecurityContextHolder.clearContext();
	    	event.getSession().removeAttribute(Constants.SECURITY_CONTEXT_KEY);
			QueueService queueService = (QueueService) ClassBeanFactory.getBean("queueService");
			if(ObjUtil.isNotEmpty(queueService)){
				LogoutMessage log=new LogoutMessage(securityContext);
				queueService.sendMessage(log);
			}
	    	CacheFactory.createApi(PropertiesUtil.getConfigureValue("cache.type")).del(securityContext.getUserId()+"_ResourcesByUser");
		}
		getContext(event.getSession().getServletContext()).publishEvent(e);
	
		
	}
	
	/**
	 * @description:ApplicationContext具有发布事件的能力
	 * @param: 
	 * @return: ApplicationContext 
	 */
	ApplicationContext getContext(ServletContext servletContext) {
        return WebApplicationContextUtils.getWebApplicationContext(servletContext);
    }

}
