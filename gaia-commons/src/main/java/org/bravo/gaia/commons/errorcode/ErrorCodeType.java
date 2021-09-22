package org.bravo.gaia.commons.errorcode;

import lombok.Setter;

/**
 * 错误码类型
 * 
 * @author lijian
 * @version $Id: ErrorCodeType.java, v 0.1 2018年1月6日 下午3:01:55 lijian Exp $
 */
public enum ErrorCodeType {

    BIZ_ERROR("1", "业务错误"),
    SYS_ERROR("2", "系统错误"),
    THIRD_ERROR("3", "第三方错误");

    @Setter
    private String code;
    @Setter
    private String desc;

    ErrorCodeType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public ErrorCodeType getCodeType(String code) {
        for (ErrorCodeType errorCodeType : values()) {
            if (errorCodeType.code.equals(code)) {
                return errorCodeType;
            }
        }
        return null;
    }

}
