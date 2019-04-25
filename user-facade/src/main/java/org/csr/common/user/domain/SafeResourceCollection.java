package org.csr.common.user.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.Persistable;
import org.csr.core.constant.YesorNo;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
/**
 * ClassName:SafeResource.java <br/>
 * System Name：    签到系统  <br/>
 * Date:     2015-11-20上午9:51:15 <br/>
 * @author   liurui <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 安全资源集合：  <br/>
 * 定义所有的数据 访问集合。
 */
@Entity
@Table(name="u_SafeResourceCollection")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(en="pmt_common_SafeResourceCollection")
public class SafeResourceCollection implements Persistable<Long>{
	private static final long serialVersionUID = 1L;

	/**id*/
	private Long id;
	/**名称*/
	private String name;
	/**备注*/
	private String remarks;
	/**系统资源*/
	private Byte isSystem=YesorNo.NO;
	/**系统资源*/
	private Long root;
	
	public SafeResourceCollection(){}
	public SafeResourceCollection(Long id) {
		this.id=id;
	}
	@Override
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")  
	@Column(name = "id", unique = true, nullable = false)
	@Comment(ch = "ID", en = "id", search = true)
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
	}
	@Length(min=1,max=128)
	@Column(name="name",length=128)
	@Comment(ch="安全资源名称",en="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Length(max=128)
	@Column(name="remarks",length = 128)
	@Comment(ch="备注",en="remarks")
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="isSystem")
	@Comment(ch="是否为系统资源",en="isSystem")
	public Byte getIsSystem(){
		return isSystem;
	}
	public void setIsSystem(Byte isSystem){
		this.isSystem=isSystem;
	}
	
	@Column(name="root")
	public Long getRoot() {
		return root;
	}

	public void setRoot(Long root) {
		this.root = root;
	}
}
