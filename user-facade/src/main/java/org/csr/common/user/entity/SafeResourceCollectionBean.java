package org.csr.common.user.entity;
import org.csr.common.user.domain.SafeResourceCollection;
import org.csr.core.web.bean.VOBase;

public class SafeResourceCollectionBean extends VOBase<Long> {

	private static final long serialVersionUID = 1L;

	/**id*/
	private Long id;
	/**名称*/
	private String name;
	/**备注*/
	private String remarks;
	/**是否位系统资源*/
	private Byte isSystem;
	/**系统资源*/
	private Long root;
	/**是否选择当前节点*/
	private boolean checked = false;
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Byte getIsSystem(){
		return isSystem;
	}
	public void setIsSystem(Byte isSystem){
		this.isSystem=isSystem;
	}
	public boolean isNew() {
		return null==this.id;
	}
	public Long getRoot() {
		return root;
	}
	public void setRoot(Long root) {
		this.root = root;
	}
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public static SafeResourceCollectionBean wrapBean(SafeResourceCollection doMain) {
		SafeResourceCollectionBean bean = new SafeResourceCollectionBean();
		bean.setId(doMain.getId());
		bean.setName(doMain.getName());
		bean.setRemarks(doMain.getRemarks());
		bean.setIsSystem(doMain.getIsSystem());
		bean.setRoot(doMain.getRoot());
		return bean;
	}
}
