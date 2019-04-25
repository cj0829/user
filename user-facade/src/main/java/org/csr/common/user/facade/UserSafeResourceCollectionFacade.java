package org.csr.common.user.facade;

import java.util.List;

import org.csr.common.user.domain.UserSafeResourceCollection;
import org.csr.common.user.entity.UserSafeResourceCollectionBean;
import org.csr.core.persistence.service.BasisFacade;

public interface UserSafeResourceCollectionFacade extends BasisFacade<UserSafeResourceCollectionBean, Long>{

	void save(UserSafeResourceCollection userSafeResourceCollection);

	void batchSave(Long[] safeResourceCollectionIds, Long[] userIds);

	void deleteAuthorized(Long safeResourceCollectionId, Long userId);

	void update(UserSafeResourceCollection userSafeResourceCollection);

	int deleteSimple(Long[] ids);

	boolean checkNameIsExist(Long id, String name);

	List<UserSafeResourceCollectionBean> findByUserId(Long userId);

	/**
	 * @param userId
	 * @param list
	 */
	void updateUserSafeResourceCollection(Long userId, List<Long> safeResourceCollectionIds);

}
