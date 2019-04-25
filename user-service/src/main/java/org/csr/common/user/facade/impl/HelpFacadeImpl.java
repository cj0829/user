package org.csr.common.user.facade.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.HelpDao;
import org.csr.common.user.domain.Help;
import org.csr.common.user.entity.HelpBean;
import org.csr.common.user.facade.HelpFacade;
import org.csr.common.user.service.HelpService;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SetBean;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.persistence.util.PersistableUtil;
import org.springframework.stereotype.Service;

@Service("helpFacade")
public class HelpFacadeImpl extends
		SimpleBasisFacade<HelpBean,Help, Long> implements
		HelpFacade {
	@Resource
	private HelpService helpService;
	@Resource
	private HelpDao helpDao;

	@Override
	public HelpBean wrapBean(Help doMain) {
		return HelpBean.wrapBean(doMain);
	}

//	@Override
//	public Help wrapDomain(HelpBean entity) {
//		Help doMain = new Help(entity.getId());
//		doMain.setFunctionPointCode(entity.getFunctionPointCode());
//		doMain.setTitle(entity.getTitle());
//		doMain.setContent(entity.getContent());
//		return doMain;
//	}
	@Override
	public List<HelpBean> queryByCode(String functionPointCode) {
		return PersistableUtil.toListBeans(helpService.queryByCode(functionPointCode), new SetBean<Help>() {
			@Override
			public HelpBean setValue(Help doMain) {
				return wrapBean(doMain);
			}
		});
	}

	@Override
	public boolean save(Help help) {
		return helpService.save(help);
	}

	@Override
	public void update(Help help) {
		Help help2 = helpService.findById(help.getId());
		help2.setFunctionPointCode(help.getFunctionPointCode());
		help2.setTitle(help.getTitle());
		help2.setContent(help.getContent());
		helpService.updateSimple(help2);
	}

	@Override
	public BaseDao<Help, Long> getDao() {
		return helpDao;
	}
}
