package org.csr.common.user.support.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.csr.core.intercepter.HandlerInterceptorAdapter;
import org.csr.core.persistence.business.DictionaryUtil;
import org.csr.core.persistence.business.ParameterUtil;
/**
 * ClassName:CacheUrlInterceptor.java <br/>
 * System Name：    csr <br/>
 * Date:     2014-2-19上午11:20:36 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class CacheUserInterceptor extends HandlerInterceptorAdapter{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		request.setAttribute("system_name", ParameterUtil.getParamValue(null,"system_name"));
		request.setAttribute("skinNames", DictionaryUtil.findDictList("skinName"));
		request.setAttribute("file_server", ParameterUtil.getParamValue(null,"file_server"));
		request.setAttribute("web_root", ParameterUtil.getParamValue(null,"web_root"));
		request.setAttribute("question_maxnum", ParameterUtil.getParamValue(null,"question_maxnum"));
		return true;
	}
	
}
