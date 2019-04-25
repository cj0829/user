package org.csr.common.user.service.impl;

import javax.annotation.Resource;

import org.csr.common.user.dao.RemindTemplateDao;
import org.csr.common.user.domain.RemindTemplate;
import org.csr.common.user.service.RemindTemplateService;
import org.csr.core.Param;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:RemindTemplateServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:08:06 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("remindTemplateService")
public class RemindTemplateServiceImpl extends
	SimpleBasisService<RemindTemplate, Long> implements RemindTemplateService {
    @Resource
    private RemindTemplateDao remindTemplateDao;
    
    @Override
	public BaseDao<RemindTemplate, Long> getDao() {
		return remindTemplateDao;
	}
	/**
	 * @description:保存模板
	 * @param: RemindTemplate：模板对象
	 * @return: void 
	 */
	public void save(RemindTemplate remindTemplate) {
		if(ObjUtil.isEmpty(remindTemplate)){
			Exceptions.service("remindTemplateCodeIsExist", PropertiesUtil.getExceptionMsg("remindTemplateCodeIsExist"));
		}
	    if (checkAddRemindTemplateCode(remindTemplate.getCode())) {
	    	Exceptions.service("remindTemplateCodeIsExist", PropertiesUtil.getExceptionMsg("remindTemplateCodeIsExist"));
	    }
	    remindTemplateDao.save(remindTemplate);
	}
	
	
	@Override
	public void update(RemindTemplate remindTemplate) {
		if(ObjUtil.isEmpty(remindTemplate)){
			Exceptions.service("remindTemplateCodeIsExist", PropertiesUtil.getExceptionMsg("remindTemplateCodeIsExist"));
		}
		RemindTemplate remindTemplate2 = remindTemplateDao.findById(remindTemplate.getId());
		if(ObjUtil.isEmpty(remindTemplate2)){
			Exceptions.service("remindTemplateCodeIsExist", PropertiesUtil.getExceptionMsg("remindTemplateCodeIsExist"));
		}
		remindTemplate2.setMailTemplate(remindTemplate.getMailTemplate());
		remindTemplate2.setSmsTemplate(remindTemplate.getSmsTemplate());
		remindTemplate2.setRemark(remindTemplate.getRemark());
		remindTemplateDao.update(remindTemplate2);
	}
	/**
 	 * @description:根据代码获取模板
 	 * @param: code：代码
 	 * @return: RemindTemplate 
 	 */  
	public RemindTemplate queryByCode(String code){
		Param param = new AndEqParam("code", code);
		return remindTemplateDao.existParam(param);
	}
	/**
	 * 用于验证添加模板代码是否唯一
	 * @see org.csr.common.user.service.RemindTemplateService#checkAddRemindTemplateCode(java.lang.String)
	 */
	@Override
	public boolean checkAddRemindTemplateCode(String code) {
		if(ObjUtil.isEmpty(queryByCode(code))){
			return false;
		}else{
			return true;
		}
	}
	/**
	 *  用于验证xi模板代码是否唯一
	 * @see org.csr.common.user.service.RemindTemplateService#checkUpdateRemindTemplateCode(java.lang.String)
	 */
	@Override
	public boolean checkUpdateRemindTemplateCode(Long id,String code) {
		RemindTemplate rt=queryByCode(code);
		if(ObjUtil.isEmpty(rt) || rt.getId().equals(id)){
			return false;
		}else{
			return true;
		}
	}
	
}
