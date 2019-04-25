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
import org.csr.common.user.entity.OperationLogBean;
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
 * ClassName:OperationLogAction.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/operationLog")
public class OperationLogAction extends BasisAction {
	
	final String preList="common/operationLog/operationLogList";
	final String preInfo="common/operationLog/operationLogInfo";
	
    @Resource
    protected OperationLogFacade operationLogFacade;

    /**
     * @description:进入操作日志管理页面
     * @param:
     * @return: String
     */
    @RequestMapping(value = "preList",method = RequestMethod.GET)
    public String preList(ModelMap model) {
    	return preList;
    }

    /**
     * @description:查询操作日志列表
     * @param:
     * @return: String
     */
    @RequestMapping(value = "ajax/list",method = RequestMethod.POST)
    public ModelAndView list() {
    	PagedInfo<OperationLogBean> result = operationLogFacade.findAllPage(page);
		return resultExcludeJson(result);
    }
    
    /**
     * @description:查看操作日志详细信息
     */
    @RequestMapping(value = "preInfo",method = RequestMethod.GET)
    public String preInfo(ModelMap model,@RequestParam("id")Long id) {
    	model.addAttribute("operationLog",operationLogFacade.findById(id));
		return preInfo;
    }
    
    /**
     * operationLogExportToExcel: 导出monthAgo个月以前的数据
     * @author huayj
     * @param request
     * @param response
     * @param monthAgo:多少个月以前
     * @return
     * @since JDK 1.7
     */
    @RequestMapping(value="ajax/operationLogExportToExcel")
	public ModelAndView operationLogExportToExcel(HttpServletRequest request,HttpServletResponse response,Integer monthAgo,Byte isDelete){
    	Date date = DateUtil.addDate(new Date(), monthAgo);
    	List<OperationLogBean> list = operationLogFacade.findByOperationTime(date);
		int rowNum = 0;
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet();
		HSSFRow row = sheet.createRow(rowNum++);
		row.createCell(0).setCellValue("操作名");
		row.createCell(1).setCellValue("用户名");
		row.createCell(2).setCellValue("姓名");
		row.createCell(3).setCellValue("操作时间");
		row.createCell(4).setCellValue("操作类型");
		row.createCell(5).setCellValue("功能点编码");
		row.createCell(6).setCellValue("功能点名称");
		for (OperationLogBean bean : list) {
			row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(bean.getOperation());
			row.createCell(1).setCellValue(bean.getLoginName());
			row.createCell(2).setCellValue(bean.getUserName());
			row.createCell(3).setCellValue(bean.getOperationTime());
			row.createCell(4).setCellValue(DictionaryUtil.getDictName("operationLogType", ObjUtil.toString(bean.getOperationLogType())));
			row.createCell(5).setCellValue(bean.getFunctionPointCode());
			row.createCell(6).setCellValue(bean.getFunctionPointCodeName());
		}
		response.setCharacterEncoding("utf-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/xls");
		response.setHeader("Content-Disposition", "attachment;fileName=operationLog_"+DateUtil.parseDate(new Date())+".xls");
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
		if(YesorNo.YES.equals(isDelete)){
			List<Long> idsList = PersistableUtil.arrayTransforList(list);
			operationLogFacade.deleteSimple(idsList.toArray(new Long[idsList.size()]));
		}
		return null;
	}
}
