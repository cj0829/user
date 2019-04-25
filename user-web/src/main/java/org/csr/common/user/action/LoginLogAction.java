package org.csr.common.user.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.csr.common.user.entity.LoginLogBean;
import org.csr.common.user.entity.OperationLogBean;
import org.csr.common.user.facade.LoginLogFacade;
import org.csr.common.user.facade.OperationLogFacade;
import org.csr.core.constant.YesorNo;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.business.DictionaryUtil;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.util.DateUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * ClassName:LoginLogAction.java <br/>
 * System Name： 用户管理系统 <br/>
 * Date: 2014-1-27 上午9:31:56 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 
 *        功能描述： <br/>
 *        公用方法描述： <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/loginLog")
public class LoginLogAction extends BasisAction {

	final String preList = "common/loginLog/loginLogList";
	final String preOperationLogPage = "common/loginLog/operationLogList";

	@Resource
	private LoginLogFacade loginLogFacade;
	@Resource
	private OperationLogFacade operationLogFacade;

	/**
	 * @description:进入登录日志管理页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preList", method = RequestMethod.GET)
	public String preList(ModelMap model) {
		return preList;
	}

	/**
	 * @description:查询登录日志列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/list", method = RequestMethod.POST)
	public ModelAndView list() {
		PagedInfo<LoginLogBean> result = loginLogFacade.findAllPage(page);
		return resultExcludeJson(result);
	}

	/**
	 * @description:进入操作日志管理页面
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "preListOperationLog", method = RequestMethod.GET)
	public String preListOperationLog(ModelMap model,
			@RequestParam("loginLogId") Long loginLogId) {
		model.addAttribute("loginLogId", loginLogId);
		return preOperationLogPage;
	}

	/**
	 * @description:查询操作日志列表
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "ajax/listOperationLog")
	public ModelAndView listOperationLog(@RequestParam("loginLogId") Long loginLogId) {
		PagedInfo<OperationLogBean> result = operationLogFacade.findListPageByLoginLogId(page, loginLogId);
		return resultExcludeJson(result);
	}

	/**
	 * loginLogExportToExcel: 导出monthAgo个月以前的数据
	 * 
	 * @author huayj
	 * @param request
	 * @param response
	 * @param monthAgo
	 *            : 多少个月以前
	 * @return
	 * @since JDK 1.7
	 */
	@RequestMapping(value = "ajax/loginLogExportToExcel")
	public ModelAndView loginLogExportToExcel(HttpServletRequest request,
			HttpServletResponse response, Integer monthAgo, Byte isDelete) {
		Date date = DateUtil.addDate(new Date(), monthAgo);
		List<LoginLogBean> list = loginLogFacade.findByOperationTime(date);
		int rowNum = 0;
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(rowNum++);
		row.createCell(0).setCellValue("用户名");
		row.createCell(1).setCellValue("姓名");
		row.createCell(2).setCellValue("登录时间");
		row.createCell(3).setCellValue("退出时间");
		row.createCell(4).setCellValue("IP地址");
		row.createCell(5).setCellValue("退出方式");
		for (LoginLogBean bean : list) {
			row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(bean.getLoginName());
			row.createCell(1).setCellValue(bean.getUserName());
			row.createCell(2).setCellValue(DateUtil.parseDateTimeToSec(bean.getLoginTime()));
			if (ObjUtil.isNotEmpty(DateUtil.parseDateTimeToSec(bean.getLogoutTime()))) {
				row.createCell(3).setCellValue(bean.getLogoutTime());
			}
			row.createCell(4).setCellValue(bean.getIpAdress());
			if (ObjUtil.isNotEmpty(bean.getExitType())) {
				row.createCell(5).setCellValue(DictionaryUtil.getDictName("exitType",ObjUtil.toString(bean.getExitType())));
			}
		}
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/xls");
		response.setHeader("Content-Disposition","attachment;fileName=loginLog_"+ DateUtil.parseDate(new Date()) + ".xls");
		ServletOutputStream os = null;
		try {
			os = response.getOutputStream();
			wb.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			return errorMsgJson(e.toString());
		}
		if (YesorNo.YES.equals(isDelete)) {
			List<Long> idsList = PersistableUtil.arrayTransforList(list);
			loginLogFacade.deleteSimple(idsList.toArray(new Long[idsList.size()]));
		}
		return null;
	}
}
