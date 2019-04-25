package org.csr.common.user.entity;

import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.RoleFunctionPoint;
import org.csr.core.util.ObjUtil;
import org.csr.core.web.bean.VOBase;
  
/**
 * ClassName:RoleFunctionPointResult.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class RoleFunctionPointBean extends VOBase<Long>{ 
	private static final long serialVersionUID = 1L;
	private Long id;
	private String parentCode;
	private String name;
	private String code;
	private String url; 
	private Integer isControlPrivilege;
	
	public RoleFunctionPointBean(Long id,String parentCode, String name, String code,String url,Integer isControlPrivilege) { 
		this.id=id;
		this.parentCode = parentCode;
		this.name = name;
		this.code = code;
		this.url = url;
		this.isControlPrivilege = isControlPrivilege;
	}
	
	public RoleFunctionPointBean() {}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIsControlPrivilege() {
		return isControlPrivilege;
	}

	public void setIsControlPrivilege(Integer isControlPrivilege) {
		this.isControlPrivilege = isControlPrivilege;
	}  
	public static RoleFunctionPointBean wrapBean(RoleFunctionPoint doMain) {
		RoleFunctionPointBean bean=new RoleFunctionPointBean();
		bean.setId(doMain.getId());
		FunctionPoint fp=doMain.getFunctionPoint();
		if(ObjUtil.isNotEmpty(fp)){
			bean.setCode(fp.getCode());
			bean.setName(fp.getName());
			bean.setParentCode(fp.getFunctionPoint().getCode());
			bean.setUrl(fp.getUrlRule());
		}
		return bean;
	}
}
