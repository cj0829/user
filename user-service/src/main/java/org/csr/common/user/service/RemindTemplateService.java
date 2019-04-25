package org.csr.common.user.service;

import org.csr.common.user.domain.RemindTemplate;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:RemindTemplateService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-21下午4:05:27 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  模板接口<br/>
 * 公用方法描述：  <br/>
 */
public interface RemindTemplateService extends BasisService<RemindTemplate, Long>{
	/**
	 * @description:保存模板
	 * @param: RemindTemplate：模板对象
	 * @return: void 
	 */
	public void save(RemindTemplate remindTemplate);
	/**
	 * update: 修改模板，不修改模板名称 <br/>
	 * @author caijin
	 * @param remindTemplate
	 * @since JDK 1.7
	 */
	void update(RemindTemplate remindTemplate);
	/**
 	 * @description:根据代码获取模板
 	 * @param: code：代码
 	 * @return: RemindTemplate 
 	 */  
	public RemindTemplate queryByCode(String code);
	
	/**
	 * @description:用于验证添加模板代码是否唯一
	 * @param: RemindTemplate：模板对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean checkAddRemindTemplateCode(String code);
	
	/**
	 * @description:用于验证修改模板代码是否唯一
	 * @param: RemindTemplate：模板对象
	 * @return: boolean
	 * @author:wangxiujuan
	 */
	public boolean checkUpdateRemindTemplateCode(Long id,String code);

	
}
