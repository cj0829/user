package org.csr.common.user.domain;

// Generated Oct 29, 2013 10:37:52 AM by Hibernate Tools 3.2.0.b9

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.csr.core.Comment;
import org.csr.core.persistence.domain.SimpleDomain;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * 积分与级别对应关系
 */
@Entity
@Table(name = "u_PointsLevelRules")
@DynamicInsert(true)
@DynamicUpdate(true)
@Comment(ch="积分与级别对应关系",en="pmt_common_PointsLevelRules")
public class PointsLevelRules extends SimpleDomain<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4664545948392793881L;
	private Long id;
	private Integer points;
	private String levelName;
	private Integer level;

	public PointsLevelRules() {
	}

	public PointsLevelRules(Long id) {
		this.id = id;
	}

	public PointsLevelRules(Long id, Integer points, String levelName,
			Integer level) {
		this.id = id;
		this.points = points;
		this.levelName = levelName;
		this.level = level;
	}
	@Id
	@GeneratedValue(generator = "globalGenerator")  
	@GenericGenerator(name = "globalGenerator", strategy = "org.csr.core.persistence.generator5.GlobalGenerator")
	@Column(name = "id", unique = true, nullable = false)
	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "points")
	@Comment(ch="级别积分数")
	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Column(name = "levelName", length = 32)
	@Comment(ch="级别名称")
	public String getLevelName() {
		return this.levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Column(name = "levels",length=2)
	@Comment(ch="级别")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}
