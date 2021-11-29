package org.bravo.gaia.commons.base;

import org.bravo.gaia.commons.util.ToStringUtil;

import java.io.Serializable;

/**
 * DTO基类
 * @author lijian
 * @since 2021/7/13
 */
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 781768640533536838L;

    @Override
    public String toString() {
        return ToStringUtil.obj2String(this);
    }
}
