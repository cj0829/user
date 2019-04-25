package org.csr.common.user.facade;

import org.csr.common.user.entity.UserBean;
import org.csr.core.persistence.service.BasisFacade;

public interface AvatarFacade extends BasisFacade<UserBean, Long>{

	/**
	 * 生成大中小头像
	 * @param streamId 要切图的id
	 * @param photoType  来源，上传，或者 从相册中来  0：上传 、1：相册
	 * @param top
	 * @param left
	 * @param width
	 * @param height
	 */
	public UserBean imageCrop(Long userId,Long streamId,Integer photoType, int top, int left, int width, int height);

}
