package org.bravo.gaia.commons.errorcode;

/**
 * 错误码接口，应用的错误码枚举类必须实现该接口
 * @author lijian
 * @version $Id: IErrorCode.java, v 0.1 2018年1月6日 下午1:09:11 lijian Exp $
 */
public interface IErrorCode {

    /**
     * 获取错误码
     * @return  错误码
     */
    ErrorCode getCode();
    
}
