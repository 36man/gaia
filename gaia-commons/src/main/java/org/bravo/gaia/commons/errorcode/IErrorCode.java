package org.bravo.gaia.commons.errorcode;

/**
 * 错误码接口，应用的错误码枚举类必须实现该接口
 * @author lijian
 * @since 2021/09/22
 */
public interface IErrorCode {

    /**
     * 获取错误码
     * @return  错误码
     */
    ErrorCode getCode();
    
}
