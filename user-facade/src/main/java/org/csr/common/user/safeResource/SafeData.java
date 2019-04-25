package org.csr.common.user.safeResource;

import java.io.Serializable;

import org.csr.core.Persistable;

public interface SafeData<ID extends Serializable> extends Persistable<ID> {

}
