package org.csr.common.user.service;

import java.util.List;

import org.csr.common.user.domain.Help;
import org.csr.core.persistence.service.BasisService;

/**
 * ClassName:HelpService <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述： 帮助 <br/>
 * 公用方法描述：  <br/>
 */
public interface HelpService extends BasisService<Help, Long>{

	/**
	 * save: 保存帮助 ,需要检查帮助code是否存在 <br/>
	 * @author caijin
	 * @param help
	 * @return
	 * @since JDK 1.7
	 */
	public boolean save(Help help);
	/**
	 * delete: 删除功能，需要修改功能点状态<br/>
	 * @author caijin
	 * @param id
	 * @since JDK 1.7
	 */
	public void delete(Long id);
    /**
     * @description:根据功能点代码获取帮助
     * @param: code：代码
     * @return: RemindTemplate 
     */ 
    public List<Help> queryByCode( String code);

}
