/**
 * Project Name:exam
 * File Name:FormSerivceFactory.java
 * Package Name:org.csr.common.flow.support
 * Date:2015年7月9日上午10:39:01
 * Copyright (c) 2015, csr版权所有 ,All rights reserved 
 */

package org.csr.common.flow.support;

import java.util.HashMap;
import java.util.Map;

import org.csr.common.flow.entity.Form;
import org.csr.common.flow.service.FormSerivce;
import org.csr.core.util.ClassBeanFactory;
import org.csr.core.util.ObjUtil;

/**
 * ClassName: FormSerivceFactory.java <br/>
 * System Name： elearning系统 <br/>
 * Date: 2015年7月9日上午10:39:01 <br/>
 * 
 * @author caijin <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7 功能描述： <br/>
 */
public class FormSerivceFactory {

	protected static Map<String, String> configMap = new HashMap<String, String>();
	protected static Map<Integer, String> categoryItemTypeMap = new HashMap<Integer, String>();
	
	// static{
	// configMap.put("com.pmt.exam.domain.CategoryItem", "categoryItemService");
	// configMap.put("com.pmt.exam.result.TestPaperBean", "testPaperService");
	// configMap.put("com.pmt.exam.domain.TestPaper", "testPaperService");
	// configMap.put("com.pmt.exam.result.ExaminationBean",
	// "examinationService");
	// configMap.put("com.pmt.exam.result.QuestionJsonBean", "questionService");
	// configMap.put("com.pmt.exam.domain.Question", "questionService");
	// configMap.put("com.pmt.exam.domain.UserCommand", "sysUserService");
	// configMap.put("com.pmt.exam.domain.UserExamination",
	// "userExaminationService");
	//
	// categoryItemTypeMap.put(ExamType.TESTPAPER, "testPaperService");
	// categoryItemTypeMap.put(ExamType.EXAMINATION, "examinationService");
	// categoryItemTypeMap.put(ExamType.QUESTION, "questionService");
	// categoryItemTypeMap.put(ExamType.EXAMTEMPLATE, "examTemplateService");
	// categoryItemTypeMap.put(ExamType.PAPERTEMPLATE, "paperTemplateService");
	// }
	public FormSerivce registrationForm(Form form) {
		return (FormSerivce) ClassBeanFactory.getBean(configMap.get(form.getClass().getName()));
	}

	public FormSerivce categoryItemTypeForm(Integer categoryItemType) {
		return (FormSerivce) ClassBeanFactory.getBean(categoryItemTypeMap.get(categoryItemType));
	}

	public void setFormSerivceMap(Map<String, String> formSerivceMap) {
		configMap.putAll(formSerivceMap);
	}

	public void setCategoryItemTypeMap(Map<Integer, String> categoryItemType) {
		categoryItemTypeMap.putAll(categoryItemType);
	}
}
