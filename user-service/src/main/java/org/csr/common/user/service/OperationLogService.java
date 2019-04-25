package org.csr.common.user.service;

import java.util.Date;
import java.util.List;

import org.csr.common.user.domain.OperationLog;
import org.csr.core.UserSession;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.service.BasisService;
import org.csr.core.queue.MessageService;

/**
 * ClassName:OperationLogService.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public interface OperationLogService extends BasisService<OperationLog, Long>,
	MessageService<UserSession> {

	/**
	 * @description:查询操作日志表信息
	 * @param: page：分页信息
	 * @return: ResultInfo
	 * @author:wangxiujuan
	 */
	public PagedInfo<OperationLog> findListPageByLoginLogId(Page page,
			Long loginLogId);
	
	/**
	 * findByOperationTime:查询date之前的数据
	 * @author huayj
	 * @param date
	 * @return
	 * @return List<OperationLogResult>
	 * @date&time 2016-1-12 下午5:02:34
	 * @since JDK 1.7
	 */
	public List<OperationLog> findByOperationTime(Date date);
}
