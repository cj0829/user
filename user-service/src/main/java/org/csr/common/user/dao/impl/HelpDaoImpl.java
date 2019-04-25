package org.csr.common.user.dao.impl;

import java.util.List;

import org.csr.common.user.dao.HelpDao;
import org.csr.common.user.domain.Help;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:HelpDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("helpDao")
public class HelpDaoImpl extends JpaDao<Help,Long> implements HelpDao {
  
	@Override
	public Class<Help> entityClass() {
		return Help.class;
	}
	
	/**
	 * 根据功能点代码获取帮助
	 * @see org.csr.common.user.dao.HelpDao#queryByCode(java.lang.String)
	 */
    public List<Help> queryByCode(String code) {
    	Finder finder = FinderImpl.create("select h from Help h where h.functionPointCode='");
    	finder.append(code).append("'");
		return this.find(finder);
    }
	
}
