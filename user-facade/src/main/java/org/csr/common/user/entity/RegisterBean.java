package org.csr.common.user.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.csr.core.persistence.business.domain.Parameters;
import org.csr.core.web.bean.VOBase;

/**
 * ClassName:RegisterResult.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
public class RegisterBean extends VOBase<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long organizationId;
	private Long userId;
	private String organame;
	private String loginName;
	private String password;
	private String email;
	private String parameterValues;
	private Long[] functionPointIds;
	private String oldFunctionPointIds;
	//功能点id集合
	private List<Long> roleFunctionPointList;
	
	
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public String getOrganame() {
		return organame;
	}
	public void setOrganame(String organame) {
		this.organame = organame;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long[] getFunctionPointIds() {
		return functionPointIds;
	}
	
	public List<Parameters> getParameterValues() {
		List<Parameters> list=new ArrayList<Parameters>();
		if (StringUtils.isNotBlank(parameterValues)) {
			String[] listStrings = parameterValues.split(",");
			for (int i = 0; i < listStrings.length; i++) {
				if (StringUtils.isNotBlank(parameterValues)) {
					String[] parameter = listStrings[i].trim().split("_");
					if(parameter.length>1 && !parameter[1].trim().isEmpty()){
						Parameters p=new Parameters();
						p.setId(Long.parseLong(parameter[0]));
						p.setParameterValue(parameter[1]);
						list.add(p);
					}
				}
				
			}
		}
		return list;
	}
	
	public void setParameterValues(String parameterValues) {
		this.parameterValues = parameterValues;
	}
	
	public void setFunctionPointIds(Long[] functionPointIds) {
		this.functionPointIds = functionPointIds;
	}
	
	public List<Long> getRoleFunctionPointList() {
		return roleFunctionPointList;
	}
	public void setRoleFunctionPointList(List<Long> roleFunctionPointList) {
		this.roleFunctionPointList = roleFunctionPointList;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOldFunctionPointIds() {
		return oldFunctionPointIds;
	}
	public void setOldFunctionPointIds(String oldFunctionPointIds) {
		this.oldFunctionPointIds = oldFunctionPointIds;
	}
}
