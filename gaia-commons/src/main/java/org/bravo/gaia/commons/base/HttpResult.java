package org.bravo.gaia.commons.base;

import lombok.Getter;
import lombok.Setter;
import org.bravo.gaia.commons.errorcode.ErrorCode;

/**
 * 返回给客户端(前端)的信息
 * @author lijian
 * @since 2021/07/13
 */
public class HttpResult<T> {

    public final static String ERROR_CODE = "code";
    public final static String ERROR_MSG = "msg";
    public final static String DATA = "data";
    public final static String DEFAULT_SUCCESS_CODE = "200";
    public final static String DEFAULT_FAIL_CODE = "200";
    public final static String DEFAULT_SUCCESS_MSG = "success";
    public final static String DEFAULT_FAIL_MSG = "fail";

    @Setter
    @Getter
    private String code;
    @Setter
    @Getter
    private String message;
    @Setter
    @Getter
    private T data;
    @Setter
    @Getter
    private boolean success;

    public static <T> HttpResult<T> ok(T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(DEFAULT_SUCCESS_CODE);
        httpResult.setMessage(DEFAULT_SUCCESS_MSG);
        httpResult.setData(data);
        httpResult.setSuccess(true);
        return httpResult;
    }

    public static <T> HttpResult<T> ok() {
        return ok(null);
    }

    public static <T> HttpResult<T> fail(ErrorCode errorCode, T data) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setCode(errorCode != null ? errorCode.str() : DEFAULT_FAIL_CODE);
        httpResult.setMessage(errorCode != null ? errorCode.getErrorDesc() : DEFAULT_FAIL_MSG);
        httpResult.setData(data);
        httpResult.setSuccess(false);
        return httpResult;
    }

    public static <T> HttpResult<T> fail(T data) {
        return fail(null, data);
    }

    public static <T> HttpResult<T> fail() {
        return fail(null, null);
    }

    public static <T> HttpResult<T> build(ErrorCode errorCode, T data, boolean success) {
        HttpResult<T> httpResult = new HttpResult<>();
        httpResult.setData(data);
        httpResult.setSuccess(success);
        httpResult.setCode(errorCode != null ? errorCode.str() : null);
        httpResult.setMessage(errorCode != null ? errorCode.getErrorDesc() : null);
        return httpResult;
    }

}