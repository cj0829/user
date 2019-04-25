package org.csr.common.user.dao.impl;

import java.util.Date;
import java.util.List;

import org.csr.common.user.dao.LoginLogDao;
import org.csr.common.user.domain.LoginLog;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.Finder;
import org.csr.core.persistence.query.FinderImpl;
import org.csr.core.persistence.query.jpa.JpaDao;
import org.springframework.stereotype.Repository;

/**
 * ClassName:LoginLogDaoImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-28上午10:10:34 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Repository("loginLogDao")
public class LoginLogDaoImpl extends JpaDao<LoginLog,Long> implements LoginLogDao {
	@Override
	public Class<LoginLog> entityClass() {
		return LoginLog.class;
	}
    /**
     * @description:查询登陆日志表信息
     * @param: page：分页信息
     * @return: ResultInfo
     * @author: Huayj
     */
	@Override
    public PagedInfo<LoginLog> findAllPage(Page page) {
    	Finder finder=FinderImpl.create("select ll from LoginLog ll ").insertEnd(" order by ll.loginTime desc");
		return findPage(page,finder);
    }
	@Override
	public List<LoginLog> findbyLoginTime(Date date) {
		Finder finder=FinderImpl.create("select ll from LoginLog ll where ll.loginTime <=:loginTime ").insertEnd(" order by ll.loginTime desc");
		finder.setParam("loginTime",date);
		return find(finder);
	}

}
