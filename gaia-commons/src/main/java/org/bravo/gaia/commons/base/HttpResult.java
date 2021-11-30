package org.bravo.gaia.commons.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bravo.gaia.commons.errorcode.ErrorCode;
import org.bravo.gaia.commons.exception.PlatformException;

/**
 * 返回给客户端(前端)的信息
 *
 * @author lijian
 * @since 2021/07/13
 */
public class HttpResult {

    public final static String KEY_CODE = "code";
    public final static String KEY_MSG = "message";
    public final static String KEY_DATA = "data";
    public final static String KEY_SUCCESS = "success";

    public final static String DEFAULT_SUCCESS_CODE = "200";
    public final static String DEFAULT_FAIL_CODE = "500";
    public final static String DEFAULT_SUCCESS_MSG = "success";
    public final static String DEFAULT_FAIL_MSG = "fail";

    public static final String ACCESS_DENY_CODE = "accessDeny";
    public static final String ACCESS_DENIED_EXCEPTION = "AccessDeniedException";

    @Setter
    @Getter
    private String code;
    @Setter
    @Getter
    private String message;
    @Setter
    @Getter
    private Object data;
    @Setter
    @Getter
    private boolean success;

    public static HttpResult ok(Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(DEFAULT_SUCCESS_CODE);
        httpResult.setMessage(DEFAULT_SUCCESS_MSG);
        httpResult.setData(data);
        httpResult.setSuccess(true);
        return httpResult;
    }

    public static HttpResult ok() {
        return ok(null);
    }

    public static HttpResult fail(ErrorCode errorCode, Object data) {
        HttpResult httpResult = new HttpResult();
        httpResult.setCode(errorCode != null ? errorCode.str() : DEFAULT_FAIL_CODE);
        httpResult.setMessage(errorCode != null ? errorCode.getErrorDesc() : DEFAULT_FAIL_MSG);
        httpResult.setData(data);
        httpResult.setSuccess(false);
        return httpResult;
    }

    public static HttpResult fail(Object data) {
        return fail(null, data);
    }

    public static HttpResult fail() {
        return fail(null, null);
    }

    public static HttpResult fail(Exception ex) {
        // 403 error
        if (ACCESS_DENIED_EXCEPTION.equals(ex.getClass().getSimpleName())) {
            HttpResult httpResult = new HttpResult();
            httpResult.setSuccess(false);
            httpResult.setMessage(ex.getMessage());
            httpResult.setCode(ACCESS_DENY_CODE);
            return httpResult;
        }

        String errorCode = StringUtils.EMPTY;
        String errorMsg = StringUtils.EMPTY;
        // platform exception
        if (ex instanceof PlatformException) { // exist error code
            if (((PlatformException) ex).getErrorContext().getCurrentErrorCode() != null) {
                errorCode = ((PlatformException) ex).getErrorContext().getTopErrCode();
                errorMsg = ((PlatformException) ex).getErrorContext().getCurrentErrorCode().getErrorDesc();
            } else { // not exist error code
                errorMsg = ex.getMessage();
            }
        }

        // supply the default value if error code or error message not exist
        if (StringUtils.isBlank(errorCode)) {
            errorCode = DEFAULT_FAIL_CODE;
        }
        if (StringUtils.isBlank(errorMsg)) {
            errorMsg = DEFAULT_FAIL_MSG;
        }

        HttpResult httpResult = new HttpResult();
        httpResult.setCode(errorCode);
        httpResult.setMessage(errorMsg);
        httpResult.setSuccess(false);
        return httpResult;
    }

    public static HttpResult build(ErrorCode errorCode, Object data, boolean success) {
        HttpResult httpResult = new HttpResult();
        httpResult.setData(data);
        httpResult.setSuccess(success);
        httpResult.setCode(errorCode != null ? errorCode.str() : null);
        httpResult.setMessage(errorCode != null ? errorCode.getErrorDesc() : null);
        return httpResult;
    }

}