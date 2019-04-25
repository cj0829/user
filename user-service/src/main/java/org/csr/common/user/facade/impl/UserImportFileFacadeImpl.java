package org.csr.common.user.facade.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.csr.common.flow.constant.FormOperType;
import org.csr.common.flow.service.TaskInstanceService;
import org.csr.common.user.constant.ImportApprovalStatus;
import org.csr.common.user.constant.UserRoleType;
import org.csr.common.user.dao.UserImportFileDao;
import org.csr.common.user.domain.Agencies;
import org.csr.common.user.domain.User;
import org.csr.common.user.domain.UserImportFile;
import org.csr.common.user.domain.UserImportStrategy;
import org.csr.common.user.entity.UserImportFileBean;
import org.csr.common.user.facade.UserImportFileFacade;
import org.csr.common.user.service.AgenciesService;
import org.csr.common.user.service.RoleService;
import org.csr.common.user.service.UserImportFileService;
import org.csr.common.user.service.UserImportStrategyService;
import org.csr.common.user.service.UserService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.page.Page;
import org.csr.core.page.PagedInfo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.business.ParameterUtil;
import org.csr.core.persistence.business.domain.UserStatus;
import org.csr.core.persistence.business.service.FileService;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.csr.core.userdetails.UserSessionContext;
import org.csr.core.util.ExcelFileUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Service("userImportFileFacade")
public class UserImportFileFacadeImpl extends SimpleBasisFacade<UserImportFileBean, UserImportFile, Long> implements UserImportFileFacade {

	public static final String[] titleArray = { "用户名", "姓名", "性别", "email" };
	@Resource
	protected UserImportFileDao userImportFileDao;
	@Resource
	protected AgenciesService agenciesService;
	@Resource
	protected FileService fileService;
	@Resource
	protected UserService userService;
	@Resource
	protected RoleService roleService;
	@Resource
	protected UserImportStrategyService userImportStrategyService;
	@Resource
	protected TaskInstanceService taskInstanceService;
	@Resource
	protected UserImportFileService userImportFileService;

	
	@Override
	public Long countByCreated(Long userId) {
		return userImportFileService.countByCreated(userId);
		
	}

	@Override
	public void createAndSaveUser(Map<String, MultipartFile> fileMap, Long agenciesId, StringBuffer errors,	Long managerUserId, Long[] roleIds,Byte userRoleType) {
		Agencies agencies = agenciesService.findById(agenciesId);
		User managerUser = null;
		if(ObjUtil.isNotEmpty(managerUserId)){
			managerUser = userService.findById(managerUserId);
		}
		if (fileMap.isEmpty()) {
			errors.append("文件上传失败");
			return;
		}
		Sheet sheet = null;
		ArrayList<ArrayList<String>> list = null;
		UserImportFile userImportFile = null;
		File file = null;
		String sheetName = "用户";
		// 判断文件目录是否存在，如果不存在，则创建
		for (MultipartFile multipartFile : fileMap.values()) {
			if (multipartFile instanceof CommonsMultipartFile) {
				FileItem fileItem = ((CommonsMultipartFile) multipartFile).getFileItem();
				try {
					if (fileItem.getName().endsWith(".xls") || fileItem.getName().endsWith(".xlsx")) {
						
						Boolean isXls = fileItem.getName().endsWith(".xls");
						Workbook workBook = null;
						if (isXls) {
							workBook = new HSSFWorkbook(multipartFile.getInputStream());
						} else {
							workBook = new XSSFWorkbook(multipartFile.getInputStream());
						}
						//文件上传
						file = fileService.createFile(fileItem.getName(), ParameterUtil.getParamValue(null,"userImport_path"));
						fileService.uploadFileSystem(file, fileItem.getInputStream());
						//保存上传的文件信息
						userImportFile = new UserImportFile();
						userImportFile.setFilePath(file.getParent());
						userImportFile.setFileName(file.getName());
						userImportFile.setOriginalFileName(multipartFile.getOriginalFilename());
						userImportFile.setUpLoadUserId(UserSessionContext.getUserSession().getUserId());
						userImportFile.setUpLoadDate(new Date());
						userImportFile.setAgencies(agencies);
						userImportFile.setOrgId(agencies.getOrg().getId());
						userImportFile = userImportFileDao.save(userImportFile);
						//入库
						sheet = workBook.getSheet(sheetName);
						if(ObjUtil.isEmpty(sheet)){
							errors.append(sheetName+" 不存在, ");
							Exceptions.service("", "");
						}
						list = ExcelFileUtil.parseSheet(sheet);
						if(ObjUtil.isEmpty(list)){
							errors.append(sheetName+" 没有要上传的内容,");
							Exceptions.service("", "");
						}
						UserNumber userNumber = saveImportInfo(agencies,managerUser,roleIds,userRoleType,userImportFile.getId(), list,errors, sheetName,true);
						userImportFile.setUserTotal(userNumber.total);
						userImportFile.setUserPassTotal(userNumber.pass);
						userImportFileDao.update(userImportFile);
					} else {
						errors.append(fileItem.getName()+" 文件格式不正确,");
						Exceptions.service("", "");
					}
				} catch (Exception e) {
					e.printStackTrace();
					//删除文件
					file.delete();
					errors.append(fileItem.getName()+" 文件上传失败,");
					Exceptions.service("", e.getMessage()+errors.toString());
				}
			}
		}
	}
	
	
	protected UserNumber saveImportInfo(Agencies agencies,User managerUser,Long[] roleIds,Byte userRoleType, Long userImportFileId, ArrayList<ArrayList<String>> datas, StringBuffer errors,String sheetName, boolean titleRow) {
		List<Integer> loginNameIndex=ExcelFileUtil.getExcelIndex(datas, "用户名");
		
		if(loginNameIndex.size()>1){
			Exceptions.service("", "用户名只能存在一列");
		}
		List<Integer> nameIndex=ExcelFileUtil.getExcelIndex(datas, "姓名");
		if(nameIndex.size()>1){
			Exceptions.service("", "姓名只能存在一列");
		}
		List<Integer> sexIndex=ExcelFileUtil.getExcelIndex(datas, "性别");
		if(sexIndex.size()>1){
			Exceptions.service("", "性别只能存在一列");
		}
		List<Integer> emailIndex=ExcelFileUtil.getExcelIndex(datas, "email");
		if(emailIndex.size()>1){
			Exceptions.service("", "email只能存在一列");
		}
		if(titleRow){
			ArrayList<String> title = datas.get(1);
			for(String str :  titleArray){
				str = str.trim();
				//验证列是否缺少
				if(!title.contains(str)){
					errors.append(sheetName+" 缺少列：" + str);
					Exceptions.service("", "");
				}
			}
		}
		User user = null;
		int line = 0;
		int total = 0;
		int pending  = 0;
		int pass  = 0;
		
		try {
			for (ArrayList<String> data : datas) {
				if (data.size() == 1 || ObjUtil.isBlank(data.get(loginNameIndex.get(0)))) {
					line++;
					continue;
				}
				if (line == 0 || line == 1) {
					line++;
					continue;
				}
				line++;
				total++;
			
				user = new User();
				if(ObjUtil.isEmpty(userRoleType)){
					userRoleType=UserRoleType.ADVANCED;
				}
				user.setUserRoleType(userRoleType);
				
				UserImportStrategy strategy = userImportStrategyService.saveFindByOrgId(agencies.getOrg().getId());
				if (ObjUtil.isEmpty(strategy)) {
					Exceptions.service("", "没有找到新策略");
				}
				
				if(ObjUtil.isEmpty(data.get(loginNameIndex.get(0)))){
					errors.append(line+"行 用户名\""+data.get(loginNameIndex.get(0))+"\"不能为空");
					Exceptions.service("", "");
				}
				user.setLoginName(data.get(loginNameIndex.get(0)));
				user.setName(data.get(nameIndex.get(0)));
				user.setAgencies(agencies);
				user.setPrimaryOrgId(agencies.getOrg().getId());
				
				user.setManagerUser(managerUser);
				user.setSkinName("orange-black");
				if(ObjUtil.isNotBlank(data.get(sexIndex.get(0)))){
					if("男".equals(data.get(sexIndex.get(0)))){
						user.setGender(YesorNo.YES);
					}else if("女".equals(data.get(sexIndex.get(0)))){
						user.setGender(YesorNo.NO);
					}else {
						errors.append(line+"行 性别\""+data.get(sexIndex.get(0))+"\"不正确");
						Exceptions.service("", "");
					}
				}
				if(ObjUtil.isNotBlank(data.get(emailIndex.get(0)))){
					if(!data.get(emailIndex.get(0)).matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")){
						errors.append(line+"行 email\""+data.get(emailIndex.get(0))+"\"不正确");
						Exceptions.service("", "");
					}
				}
				user.setEmail(data.get(emailIndex.get(0)));
				user.setUserStatus(UserStatus.NEW);
			
				if (YesorNo.YES.equals(strategy.getIsImport())) {
					user.setUserStatus(ImportApprovalStatus.PENDING);
					user.setFileId(userImportFileId);
					userService.saveUser(user,roleIds);
					taskInstanceService.createTaskInstance(strategy.getTaskTemp().getId(), user,FormOperType.IMPORT,"用户导入任务：", new Long[] { UserSessionContext.getUserSession().getUserId() }, true);
					pending++;
				} else {
					user.setUserStatus(UserStatus.NORMAL);
					userService.saveUser(user,roleIds);
					pass++;
				}
				if(total%1000==0){
					userService.batchFlush();
					userService.clear();
 				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			errors.append(line+"行 用户名\""+user.getLoginName()+"\" :异常信息"+e.getMessage());
			Exceptions.service("", "");
		}
		return new UserNumber(total,pending,pass);
	}
	

	@Override
	public boolean checkNameIsExist(Long id, String name) {
		
		return userImportFileService.checkNameIsExist(id, name);
	}

	@Override
	public UserImportFileBean wrapBean(UserImportFile doMain) {
		return UserImportFileBean.wrapBean(doMain);
	}

	@Override
	public PagedInfo<UserImportFileBean> findApproveUserImportFilePage(Page page, Long userId) {
		
		PagedInfo<UserImportFile> doMains=userImportFileService.findApproveUserImportFilePage(page, userId);
		return PersistableUtil.toPagedInfoBeans(doMains, new SetBean<UserImportFile>() {

			@Override
			public VOBase<Long> setValue(UserImportFile doMain) {
				return wrapBean(doMain);
			}
		});
	}
	
	public class UserNumber{
		public UserNumber(int total, int pending, int pass) {
			this.total=total;
			this.pending=pending;
			this.pass=pass;
		}
		public int total;
		public int pending;
		public int pass;
	}

	@Override
	public BaseDao<UserImportFile, Long> getDao() {
		return userImportFileDao;
	}
}
