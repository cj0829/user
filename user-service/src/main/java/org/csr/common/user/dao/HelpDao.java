package org.csr.common.user.dao;

import java.util.List;

import org.csr.common.user.domain.Help;
import org.csr.core.persistence.BaseDao;

/**
 * ClassName:HelpDao.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:09:15 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface HelpDao extends BaseDao<Help,Long> {
   
    /**
     * @description:根据功能点代码获取帮助
     * @param: code：代码
     * @return: RemindTemplate 
     */ 
    public List<Help> queryByCode( String code);
    
}
