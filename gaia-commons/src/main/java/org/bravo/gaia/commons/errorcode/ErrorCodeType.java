package org.bravo.gaia.commons.errorcode;

import lombok.Setter;

/**
 * 错误码类型
 * @author lijian
 * @version $Id: ErrorCodeType.java, v 0.1 2021年9月16日 下午3:01:55 lijian Exp $
 */
public enum ErrorCodeType {

    /**
     * 业务错误是后端系统中可以让用户感知的正常逻辑错误，该类型的错误码所对应的错误信息可以不用让前端系统翻译而直接通知到用户
     */
    BIZ_ERROR("1", "业务错误"),

    /**
     * 系统错误是后端系统内部的技术错误，通常不会让前端用户感知，所以前端系统识别到该错误码后将会对错误提示信息进行翻译
     */
    SYS_ERROR("2", "系统错误"),
    ;

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
