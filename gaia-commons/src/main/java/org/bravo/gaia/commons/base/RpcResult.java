package org.bravo.gaia.commons.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bravo.gaia.commons.context.ErrorContext;
import org.bravo.gaia.commons.errorcode.ErrorCode;
import org.bravo.gaia.commons.util.ToStringUtil;

import java.io.Serializable;

/**
 * rpc结果类
 * <p>所有rpc接口的返回值必须使用此类
 * <p>所有rpc接口中异常均被拦截，只会返回结果基类下的对象
 *
 * @author lijian
 * @version $Id: BaseResult.java, v 0.1 2021年10月8日 下午3:18:39 lijian Exp $
 */
public class RpcResult<T> implements Serializable {

    private static final long serialVersionUID = -8520269513099293464L;

    @Setter
    @Getter
    /** 是否处理成功 */
    protected boolean success;

    @Getter
    @Setter
    /** 错误上下文 */
    protected ErrorContext errorContext = new ErrorContext();

    @Setter
    @Getter
    /** 返回值 */
    protected T resultObj;

    @Getter
    @Setter
    /** 错误堆栈 */
    private String errorStackTrace;

    /**
     * 栈顶错误码的字符串表达形式
     */
    private String topErrorCode;

    /**
     * 默认构造函数
     */
    public RpcResult() {

    }

    /**
     * 构造函数
     *
     * @param success      处理结果
     * @param errorContext 错误上下文
     */
    public RpcResult(boolean success, ErrorContext errorContext) {
        this.success = success;
        this.errorContext = errorContext;
    }

    /**
     * 向错误上下文中添加错误对象
     *
     * @param errorCode 错误对象
     */
    public void addErrorCode(ErrorCode errorCode) {
        if (errorCode != null) {
            this.errorContext.pushErrorCode(errorCode);
        }
    }

    /**
     * 向错误上下文中添加另外一个错误上下文
     * @param errorContext 错误上下文
     */
    public void addErrorContext(ErrorContext errorContext) {
        if (errorContext != null) {
            this.errorContext.addErrorContext(errorContext);
        }
    }

    public static <T> RpcResult<T> success(T resultObj) {
        RpcResult<T> result = new RpcResult<>();
        result.setResultObj(resultObj);
        result.setSuccess(true);
        return result;
    }

    public static <T> RpcResult<T> success() {
        return success(null);
    }

    public static <T> RpcResult<T> fail(T resultObj) {
        return fail(resultObj, null);
    }

    public static <T> RpcResult<T> fail() {
        return fail(null);
    }

    public static <T> RpcResult<T> fail(T resultObj, ErrorCode errorCode) {
        RpcResult<T> result = new RpcResult<>();
        result.setResultObj(resultObj);
        result.setSuccess(false);
        if (errorCode != null) {
            result.getErrorContext().pushErrorCode(errorCode);
        }
        return result;
    }

    public String getTopErrorCode() {
        ErrorCode errorCode = this.errorContext.getCurrentErrorCode();
        String errorCodeStr = StringUtils.EMPTY;
        if (errorCode != null) {
            errorCodeStr = errorCode.str();
        }
        return errorCodeStr;
    }

    @Override
    public String toString() {
        return ToStringUtil.obj2String(this);
    }

}
