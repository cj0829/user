package org.csr.common.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.csr.common.user.dao.HelpDao;
import org.csr.common.user.domain.FunctionPoint;
import org.csr.common.user.domain.Help;
import org.csr.common.user.service.FunctionpointService;
import org.csr.common.user.service.HelpService;
import org.csr.core.constant.YesorNo;
import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.param.AndEqParam;
import org.csr.core.persistence.service.SimpleBasisService;
import org.csr.core.util.ObjUtil;
import org.springframework.stereotype.Service;

/**
 * ClassName:HelpServiceImpl.java <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-2-18上午10:34:22 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Service("helpService")
public class HelpServiceImpl extends SimpleBasisService<Help,Long> implements HelpService {
    @Resource
    private HelpDao helpDao;
    @Resource
    private FunctionpointService functionpointService;
    
    @Override
	public BaseDao<Help, Long> getDao() {
		return helpDao;
	}

	/**
	 * 保存帮助 ,需要检查帮助code是否存在
	 * @see org.csr.core.service.SimpleBasisService#save(java.lang.Object)
	 */
	public boolean save(Help help) {
		if (!checkHelpCode(help.getFunctionPointCode())) {
			helpDao.save(help);
			FunctionPoint fp = functionpointService.findByCode(help.getFunctionPointCode());
			fp.setHelpStatus(YesorNo.NO);
			functionpointService.updateSimple(fp);
			return true;
		}
		return false;
	}
  
	/**
	 * 删除功能，需要修改功能点状态
	 * @see org.csr.core.service.SimpleBasisService#deleteById(java.io.Serializable)
	 */
	public void delete(Long id) {
		if(ObjUtil.isEmpty(id)){
			Exceptions.service("", "");
		}
		Help help = helpDao.findById(id);
		helpDao.deleteById(id);
		FunctionPoint fp = functionpointService.findByCode(help.getFunctionPointCode());
		fp.setHelpStatus(YesorNo.YES);
		functionpointService.saveSimple(fp);
	}
    
    /**
     * @description:根据功能点代码获取帮助
     * @param: code：代码
     * @return: RemindTemplate 
     */ 
    public List<Help> queryByCode( String code){
    	return helpDao.queryByCode(code);
    }
    /**
     * 
     * checkHelpCode: 检查帮助code是否存在 <br/>
     * @author caijin
     * @param code
     * @return
     * @since JDK 1.7
     */
    private boolean checkHelpCode(String code){
    	AndEqParam and = new AndEqParam("functionPointCode", code);
		if(ObjUtil.isNotEmpty(helpDao.existParam(and))){
			return true;
		}
		return false;
    }

	
}
