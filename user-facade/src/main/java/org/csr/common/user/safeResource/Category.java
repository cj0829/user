/**
 * Project Name:exam
 * File Name:Category.java
 * Package Name:com.pmt.common.support
 * Date:2015-12-17下午3:08:54
 * Copyright (c) 2015, 博海云领版权所有 ,All rights reserved 
 */

package org.csr.common.user.safeResource;

import java.io.Serializable;

import org.csr.core.Persistable;
import org.csr.core.persistence.business.domain.Organization;


/**
 * ClassName:Category.java <br/>
 * System Name： 考试系统 <br/>
 * Date: 2015-12-17下午3:08:54 <br/>
 * 
 * @author lishengmei <br/>
 * @version 1.0 <br/>
 * @since JDK 1.7
 * 资源的对象的库接口
 * 
 */
public interface Category<ID extends Serializable,T> extends Persistable<ID> {
	
	public ID getId();

	public void setId(ID id);

	public Integer getType();

	public void setType(Byte type);

	public Byte getIsRoot();

	public void setIsRoot(Integer isRoot);

	public Organization getOrg();

	public void setOrg(Organization org);

	public T getParent();

	public void setParent(T parent);

	public float getRank();

	public void setRank(float rank);

	public void setPopedom(int popedom);
	
	public int getPopedom();
}
