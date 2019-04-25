package org.csr.common.user.facade.impl;

import java.io.ByteArrayInputStream;

import javax.annotation.Resource;

import org.csr.common.storage.constant.ImageConstant;
import org.csr.common.storage.dao.StorageSchemeDao;
import org.csr.common.storage.domain.Datastream;
import org.csr.common.storage.entity.DownloadFileBean;
import org.csr.common.storage.service.DatastreamService;
import org.csr.common.storage.service.FileSystemStorageService;
import org.csr.common.storage.supper.FileSystemContext;
import org.csr.common.user.dao.UserDao;
import org.csr.common.user.domain.User;
import org.csr.common.user.entity.UserBean;
import org.csr.common.user.facade.AvatarFacade;
import org.csr.core.constant.YesorNo;
import org.csr.core.persistence.BaseDao;
import org.csr.core.persistence.service.SimpleBasisFacade;
import org.csr.core.util.crop.CropImg;
import org.csr.core.util.crop.ImageCropUtil;
import org.springframework.stereotype.Service;

@Service("avatarFacade")
public class AvatarFacadeImpl extends SimpleBasisFacade<UserBean, User, Long> implements AvatarFacade{

	@Resource
	private UserDao userDao;
	@Resource
	private DatastreamService datastreamService;
	@Resource
	private StorageSchemeDao schemeDao;
	@Resource
	FileSystemStorageService fileSystemStorageService;
	@Override
	public BaseDao<User, Long> getDao() {
		return userDao;
	}

	@Override
	public UserBean wrapBean(User doMain) {
		return UserBean.wrapBean(doMain);
	}

	@Override
	public UserBean imageCrop(Long userId,Long streamId ,Integer photoType, int top, int left, int width,int height) {
		
		checkParameter(streamId, "没有指定文件");
		checkParameter(userId, "请您选择要修改的用户");
		
		User user=userDao.findById(userId);
		checkParameter(user, "选择的用户不存在");
		
		try {
			DownloadFileBean downloadDatastream = datastreamService.downloadDatastream(streamId);
			
			byte[] srcImg = ImageCropUtil.saveImageAfterCrop(downloadDatastream.getFile(), top, left, width, height);
			CropImg bigImg = ImageCropUtil.imageCrop(new ByteArrayInputStream(srcImg),ImageConstant.BIG_WIDTH, ImageConstant.BIG_HEIGHT, true);
			Datastream head = fileSystemStorageService.uploadFile(bigImg, userId+"_120x120."+downloadDatastream.getExtName(), FileSystemContext.TYPE_PHOTO);
			
			CropImg middleimg = ImageCropUtil.imageCrop(new ByteArrayInputStream(srcImg),ImageConstant.MIDDLE_WIDTH,ImageConstant.MIDDLE_HEIGHT, true);
			Datastream middle = fileSystemStorageService.uploadFile(middleimg, userId+"_80x80."+downloadDatastream.getExtName(), FileSystemContext.TYPE_PHOTO);
			
			CropImg smallimg = ImageCropUtil.imageCrop(new ByteArrayInputStream(srcImg),ImageConstant.SMALL_WIDTH,ImageConstant.SMALL_HEIGHT, true);
			Datastream avatar = fileSystemStorageService.uploadFile(smallimg, userId+"_50x50."+downloadDatastream.getExtName(), FileSystemContext.TYPE_PHOTO);
			user.setHead(head);
			user.setMiddleHead(middle);
			user.setAvatar(avatar);
			if(YesorNo.NO.equals(photoType)){
				datastreamService.deleteDatastream(streamId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return UserBean.wrapBean(user);
	}

}
