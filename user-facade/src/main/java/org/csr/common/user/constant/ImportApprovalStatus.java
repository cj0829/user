/**
 * 
 */
package org.csr.common.user.constant;

/**
 *ClassName:ImportApprovalStatus.java<br/>
 *Date:2015-9-16 上午11:58:38
 *@author huayj
 *@version 1.0
 *@since JDK 1.7
 *功能描述:导入审批状态
 */
public interface ImportApprovalStatus {
	/**1.待批*/
	public Integer PENDING=1;
	/**2.通过*/
	public Integer PASS = 2;
	/**3.驳回*/
	public Integer REFUSAL=3;
}
