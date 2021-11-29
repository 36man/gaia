package org.bravo.gaia.commons.base;

import org.bravo.gaia.commons.util.ToStringUtil;

import java.io.Serializable;

/**
 * 领域模型基类
 * @author lijian
 * @since 2021.10.08
 */
public abstract class BaseDomain implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 8077842703435876062L;

    @Override
    public String toString() {
        return ToStringUtil.obj2String(this);
    }

}
