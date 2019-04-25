package org.csr.common.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.csr.core.exception.Exceptions;
import org.csr.core.persistence.business.bean.DictionaryNode;
import org.csr.core.persistence.business.domain.Dictionary;
import org.csr.core.persistence.business.service.DictionaryService;
import org.csr.core.tree.TreeNode;
import org.csr.core.tree.TreeNodeHandle;
import org.csr.core.tree.TreeNodeTool;
import org.csr.core.util.DictionaryJsonUtil;
import org.csr.core.util.ObjUtil;
import org.csr.core.util.PropertiesUtil;
import org.csr.core.web.controller.BasisAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 * ClassName:DictionaryAction <br/>
 * System Name：    用户管理系统 <br/>
 * Date:     2014-1-27 上午9:31:56 <br/>
 * @author   caijin <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/dictionary")
public class DictionaryAction extends BasisAction {
	final String preList="common/dictionary/dictionaryList";
	final String preAdd="common/dictionary/dictionaryAdd";
	final String preUpdate="common/dictionary/dictionaryUpdate";
	//获取全局的字典数据。作为js数据
	final String preSituationDictionary="common/dictionary";
	//
	static String jsonString;
	@Resource
	private DictionaryService dictionaryService;
	
	/**
	 * @description:进入数据字典列表
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "situationDictionary",method = RequestMethod.GET)
	public String preSituationDictionary(Model model,HttpServletResponse response){
		if(ObjUtil.isBlank(jsonString)){
			jsonString=DictionaryJsonUtil.getAllJsonString(dictionaryService.findAllCacheList());
		}
		model.addAttribute("jsonString", jsonString);
//		model.addAttribute("imgServiceUrl",((FileUpload)ClassBeanFactory.getBean("fileUpload")).getFileService());
		return preSituationDictionary;
	}
	/**
	 * @description:进入数据字典列表
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preList",method = RequestMethod.GET)
	public String preList(){
		return preList;
	}
	
	/**
	 * @description:获取数据字典列表一级信息
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/list",method = RequestMethod.POST)
	public ModelAndView list(ModelMap model,@RequestParam(value="dictType",required=false)String dictType){
		List<Dictionary> dicts;
		if(ObjUtil.isNotEmpty(dictType)){
			dicts=dictionaryService.findByDictType(dictType);
		}else{
			dicts=dictionaryService.findAllList();
		}
		@SuppressWarnings("rawtypes")
		List<TreeNode> rulst=TreeNodeTool.toResult(dicts,new TreeNodeHandle<Dictionary>(){
			@Override
			public boolean setBean(Dictionary value, TreeNode node, TreeNode parent) {
				return true;
			}
			@Override
			public TreeNode newTreeNode(Dictionary value){
				return DictionaryNode.toNode(value);
			}
		});
		return resultExcludeJson(rulst,"dictionary");
	}
	
	/**
	 * @description:进入添加数据字典页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preAdd",method = RequestMethod.GET)
	public String preAdd(){
		return preAdd;
	}
	/**
	 * 添加子字典数据
	 * @return
	 */
	@RequestMapping(value = "preAddChildren",method = RequestMethod.GET)
	public String preAddChildren(ModelMap model,@RequestParam("id")Long id){
		if(id != null  && id>0){
			String functionName = dictionaryService.findDictTypeById(id);
			model.addAttribute("dictType", functionName);
			model.addAttribute("dictId", id);
		}
		return preAdd;
	}
	
	/**
	 * @description:添加数据字典
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/add",method = RequestMethod.POST)
	public ModelAndView add(Dictionary dict,@RequestParam("parentId")Long parentId){
		dictionaryService.save(dict,parentId);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("dict", DictionaryNode.toNode(dict));
		map.put("parentId", parentId);
		return successMsgJson("", map);
	}
	
	/**
	 * @description:进入编辑字典表页面
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "preUpdate")
	public String preUpdate(ModelMap model,@RequestParam("id")Long id){
		if(id != null  && id>0){
			Dictionary dict = dictionaryService.findById(id);
			model.addAttribute("dict", dict);
		}
		return preUpdate;
	}
	
	/**
	 * @description:修改数据字典子类
	 * @param: 
	 * @return: String 
	 */
	@RequestMapping(value = "ajax/update",method = RequestMethod.POST)
	public ModelAndView update(Dictionary dict,@RequestParam(value="parentId",required=false)Long parentId){
		dictionaryService.save(dict, parentId);
		return successMsgJson("",DictionaryNode.toNode(dict));
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "ajax/delete",method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("deleteIds")String deleteIds){
		dictionaryService.delete(deleteIds);
		return successMsgJson("");
	}
	
	/**
	 * 比较数据字典名称是否存在
	 * @return
	 */
	@RequestMapping(value = "ajax/findHasDictType",method = RequestMethod.POST)
	public ModelAndView findHasDictType(@RequestParam(value="parentId",required=false)Long parentId,@RequestParam("dictType")String dictType){
		Dictionary dictionary=dictionaryService.checkdictTypeOnlyInChild(parentId, dictType);
		if(dictionary!=null){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("provNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * 比较数据字典值是否存在
	 * @return
	 */
	@RequestMapping(value = "ajax/findHasDictValue",method = RequestMethod.POST)
	public ModelAndView findHasDictValue(@RequestParam(value="parentId",required=false)Long parentId,@RequestParam("dictValue")String dictValue){
		Dictionary dictionary=dictionaryService.checkdictValueOnlyInChild(parentId, dictValue);
		if(dictionary!=null){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("provNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * 修改数据字典比较
	 * @param parentId
	 * @param dictType
	 * @return
	 */
	@RequestMapping(value = "ajax/findUpdateHasDictType",method = RequestMethod.POST)
	public ModelAndView findUpdateHasDictType(@RequestParam("id")Long id,@RequestParam(value="parentId",required=false)Long parentId,@RequestParam("dictType")String dictType){
		Dictionary dictionary=dictionaryService.checkdictTypeOnlyInChild(parentId,dictType);
		if(dictionary!=null && !dictionary.getId().equals(id)){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("provNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
	 * 修改比较<br>
	 * 比较数据字典值是否存在
	 * @return
	 */
	@RequestMapping(value = "ajax/findUpdateHasDictValue",method = RequestMethod.POST)
	public ModelAndView findUpdateHasDictValue(@RequestParam("id")Long id,@RequestParam(value="parentId",required=false)Long parentId,@RequestParam("dictValue")String dictValue){
		Dictionary dictionary=dictionaryService.checkdictValueOnlyInChild(parentId,dictValue);
		if(dictionary!=null && !dictionary.getId().equals(id)){
			 return errorMsgJson(PropertiesUtil.getExceptionMsg("provNameIsExist"));
		}
		return successMsgJson("");
	}
	
	/**
     * 查询名称是否唯一
     * @param id
     * @param name
     * @return
     */
    @RequestMapping(value = "ajax/findName", method = RequestMethod.POST)
    public ModelAndView findName(Long id,String name) {
		if (ObjUtil.isEmpty(name)) {
		    Exceptions.service("1000109", "未正确接收到您所输入的名称,请联系管理员");
		}
		if (dictionaryService.checkNameIsExist(id,name)) {
			return errorMsgJson(PropertiesUtil.getExceptionMsg("NameIsExist"));
		}
		return successMsgJson("");
    }
	
}
