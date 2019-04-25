package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.csr.core.Comment;
import org.csr.core.Persistable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 行政区数据表
 */
@Entity
@Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
@Table(name="u_Province")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="行政区数据表",en="pmt_common_Province")
public class Province implements Persistable<Long>{

	/**
	 * 
	 */
	private static final long serialVersionUID=-7144766698031017056L;
	private Long id;
	private Long parentId;
	private String code;
	private String name;
	private String remark;
	private Integer rank;
	private Integer childCount;

	public Province(){
	}

	public Province(Long id){
		this.id=id;
	}

	public Province(Long id,Long parentId,String code,String name,String remark,Integer rank){
		this.id=id;
		this.parentId=parentId;
		this.code=code;
		this.name=name;
		this.remark=remark;
		this.rank=rank;
	}

	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name="id",unique=true,nullable=false)
	@Override
	public Long getId(){
		return id;
	}

	@Override
	public void setId(Long id){
		this.id=id;
	}

	@Column(name="parentId")
	@Comment(ch="父行政区ID",en="parentId")
	public Long getParentId(){
		return this.parentId;
	}

	public void setParentId(Long parentId){
		this.parentId=parentId;
	}

	@Column(name="code",length=32)
	@Comment(ch="行政区编码",en="code")
	public String getCode(){
		return this.code;
	}

	public void setCode(String code){
		this.code=code;
	}

	@Column(name="name",length=64)
	@Comment(ch="行政区名称",en="name")
	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name=name;
	}

	@Column(name="remark",length=255)
	@Comment(ch="备注",en="remark")
	public String getRemark(){
		return this.remark;
	}

	public void setRemark(String remark){
		this.remark=remark;
	}

	@Column(name="rank")
	@Comment(ch="显示顺序",en="rank")
	public Integer getRank(){
		return this.rank;
	}

	public void setRank(Integer rank){
		this.rank=rank;
	}

	@Transient
	public Integer getChildCount(){
		return childCount;
	}

	public void setChildCount(Integer childCount){
		this.childCount=childCount;
	}

	@Transient
	public boolean isNew(){
		return null==getId();
	}
}
