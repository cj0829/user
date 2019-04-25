/**
 * Project Name:user-Service
 * File Name:Test.java
 * Package Name:org.csr.common.userService
 * Date:2016-9-5下午5:59:09
 * Copyright (c) 2016, csr版权所有 ,All rights reserved 
*/

package org.csr.common.userService;

import java.io.File;

/**
 * ClassName:Test.java <br/>
 * System Name：    在线学习系统 <br/>
 * Date:     2016-9-5下午5:59:09 <br/>
 * @author   Administrator <br/>
 * @version  1.0 <br/>
 * @since    JDK 1.7
 *
 * 功能描述：  <br/>
 * 公用方法描述：  <br/>
 *
 */
public class Test {
	public static void main(String[] args) {
		File file = new File("E:/workspace/my2016/user-facade/src/main/java/com/pmt/user/facade");
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()){
				continue;
			}
			String fileName = files[i].getName();
			String className = fileName.substring(0, fileName.length()-5);
			String lowClassName = className.substring(0,1).toLowerCase()+className.substring(1,className.length()-6)+"Service";
			System.out.println("<dubbo:service retries=\"0\" interface=\"org.csr.common.user.service."+className+"\" ref=\""+lowClassName+"\" />");
		}
	}
}

