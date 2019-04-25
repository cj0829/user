package org.csr.common.user.entity;

import org.csr.common.user.domain.PointsLevelRules;
import org.csr.core.web.bean.VOBase;

public class PointsLevelRulesBean extends VOBase<Long>{

	private static final long serialVersionUID = 1L;
	private Long id;
	private Integer points;
	private String levelName;
	private Integer level;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getPoints() {
		return this.points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getLevelName() {
		return this.levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Integer getLevel() {
		return this.level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public static PointsLevelRulesBean wrapBean(PointsLevelRules doMain) {
		PointsLevelRulesBean bean=new PointsLevelRulesBean();
		bean.setId(doMain.getId());
		bean.setLevel(doMain.getLevel());
		bean.setLevelName(doMain.getLevelName());
		bean.setPoints(doMain.getPoints());
		return bean;
	}
}
