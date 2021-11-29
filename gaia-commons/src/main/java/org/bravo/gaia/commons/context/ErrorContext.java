package org.bravo.gaia.commons.context;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.bravo.gaia.commons.constant.DateConstant;
import org.bravo.gaia.commons.errorcode.ErrorCode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 错误上下文对象
 * <p>内容包含有：标准错误堆栈
 * @author lijian
 * @since 2021.07.13
 */
public class ErrorContext implements Serializable {

    private static final long   serialVersionUID = -2191953263464121647L;

    /**
     * 错误码-栈
     */
    @Setter
    @Getter
    private Stack<ErrorCodeWrapper>    errorStack       = new Stack<>();

    private String topErrCode;

    private static final String SPLIT            = "\n";

    public ErrorContext() {

    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class ErrorCodeWrapper {
        private ErrorCode errorCode;
        private Date pushTime;

        @Override
        public String toString() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateConstant.STD_MILLIS_SECOND_FORMAT);
            String dateStr = DateConstant.DEFAULT_STD_ZERO_MILLIS_SECOND_DATE;
            if (pushTime != null) {
                dateStr = simpleDateFormat.format(this.pushTime);
            }
            return "pushTime=" + dateStr + " : " + "errorCode=" + errorCode;
        }
    }

    /**
     * 获取当前错误码
     */
    public ErrorCode getCurrentErrorCode() {
        ErrorCode errorCode = null;
        try {
            errorCode = errorStack.peek().getErrorCode();
        } catch (EmptyStackException e) {
            // ignore the exception
        }
        return errorCode;
    }

    /**
     * 获取原始错误码
     */
    public ErrorCode getRootErrorCode() {
        if (errorStack.size() > 0) {
            return errorStack.get(0).getErrorCode();
        }
        return null;
    }

    /**
     * 向错误堆栈中添加错误对象
     * 
     * @param errorCode 错误码
     */
    public void pushErrorCode(ErrorCode errorCode) {
        errorStack.push(new ErrorCodeWrapper(errorCode, new Date()));
    }

    /**
     * 向错误堆栈中添加另外一个堆栈
     *
     * @param errorContext 错误码
     */
    public void addErrorContext(ErrorContext errorContext) {
        if (errorContext != null) {
            for (ErrorCodeWrapper errorCode : errorContext.errorStack) {
                this.errorStack.push(errorCode);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = errorStack.size(); i > 0; i--) {
            if (i == errorStack.size()) {
                sb.append(errorStack.get(i - 1));
            } else {
                sb.append(SPLIT).append(errorStack.get(i - 1));
            }
        }

        return sb.toString();
    }

    /**
     * Get the top error code with string style
     */
    public String getTopErrCode() {
        ErrorCode currentErrorCode = getCurrentErrorCode();
        if (currentErrorCode != null) {
            return currentErrorCode.str();
        }
        return StringUtils.EMPTY;
    }

}
