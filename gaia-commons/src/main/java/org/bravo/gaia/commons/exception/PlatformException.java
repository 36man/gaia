package org.bravo.gaia.commons.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bravo.gaia.commons.context.ErrorContext;
import org.bravo.gaia.commons.errorcode.ErrorCode;

import java.io.Serial;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公用异常
 * 通常来说，在应用中使用异常有两种方式，
 * <ol>
 *     <li>针对于不同的错误场景，定义不同的异常类，从而在不同的业务场景抛出不同的异常类对象</li>
 *     <li>统一使用同一个异常类对象，在不同的错误场景通过对象中包含的属性值(错误码)来进行区分</li>
 * </ol>
 * 公用异常类采用的是第二种方式来使用异常，一个platformException对象包含一个errorContext，而errorContext中包含了多个错误码(errorCode)，
 * 多个错误码(errorCode)采用栈进行数据组织。不同错误场景下，errorContext中包含的错误码不同，从而对不同错误场景进行区分
 * <p>推荐用法: 应用程序在遇到错误或者异常时，通过抛出platformException异常对象来传递错误</p>
 *
 * @author lijian
 * @since 2021/09/23
 */
public class PlatformException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5143695406381565749L;

    /**
     * 错误上下文
     */
    @Getter
    protected ErrorContext errorContext = new ErrorContext();

    /**
     * 根据throwable创建对象，如果throwable的类型是是platformException，那么
     * 会取出platformException中的errorContext，并设置到自身属性中
     *
     * @param throwable throwable
     */
    public PlatformException(Throwable throwable) {
        super(throwable);
        obtainAndSetErrorContext(throwable);
    }

    /**
     * 根据PlatformException创建对象,会取出platformException中的errorContext，并设置到自身属性中
     *
     * @param e e
     */
    public PlatformException(PlatformException e) {
        obtainAndSetErrorContext(e);
    }

    /**
     * 错误码中的错误信息(errorDesc)会被参数"errorMsgParams"替换
     * 该构造函数中format方法被调用了两次，因为异常对象的创建不是一个频率较高的操作，因此这次允许该性能开销
     *
     * @param errorCode      错误码
     * @param errorMsgParams 错误信息参数，格式为 "{}"
     */
    public PlatformException(ErrorCode errorCode, Object... errorMsgParams) {
        super(format(errorCode.getErrorDesc(), errorMsgParams));
        ErrorCode copy = errorCode.copy();
        copy.setErrorDesc(format(copy.getErrorDesc(), errorMsgParams));
        this.errorContext.pushErrorCode(copy);
    }

    public PlatformException(ErrorContext errorContext) {
        super(errorContext == null ?
                StringUtils.EMPTY : errorContext.getCurrentErrorCode() == null
                ? StringUtils.EMPTY : errorContext.getCurrentErrorCode().getErrorDesc());

        if (errorContext != null) {
            this.errorContext = errorContext;
        }
    }

    public PlatformException(String msg, Object... msgParams) {
        super(format(msg, msgParams));
    }

    public PlatformException(String msg, ErrorCode errorCode, Object... msgParams) {
        super(format(msg, msgParams));

        if (errorCode != null) {
            this.errorContext.pushErrorCode(errorCode);
        }
    }

    public PlatformException(String msg, Throwable throwable, Object... msgParams) {
        super(format(msg, msgParams), throwable);

        obtainAndSetErrorContext(throwable);
    }

    /**
     * 向错误上下文中添加错误对象
     *
     * @param errorCode 错误对象
     */
    public void addErrorCode(ErrorCode errorCode, Object... msgParams) {
        ErrorCode copy = errorCode.copy();
        copy.setErrorDesc(format(copy.getErrorDesc(), msgParams));
        this.errorContext.pushErrorCode(copy);
    }

    /**
     * 向错误上下文中添加另外一个错误上下文
     *
     * @param errorContext 错误对象
     */
    public void addErrorContext(ErrorContext errorContext) {
        this.errorContext.addErrorContext(errorContext);
    }

    @Override
    public String getMessage() {
        return buildMessage(super.getMessage(), getCause());
    }


    //~~~ 内部方法

    /**
     * Build a message for the given base message and root cause.
     *
     * @param message the base message
     * @param cause   the root cause
     * @return the full exception message
     */
    private String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            return ExceptionUtils.getStackTrace(cause);
        } else {
            return message;
        }
    }

    /**
     * 填充errorCode
     */
    private void obtainAndSetErrorContext(Throwable throwable) {
        if (throwable != null) {
            if (throwable instanceof PlatformException) {
                this.errorContext = ((PlatformException) throwable).errorContext;
            }
        }
    }

    /**
     * 替换错误信息中的占位符
     * 例如： 原始字符串为errorMessage is {} {} {}, 参数为1, 2, 3
     * 替换后的字符串为errorMessage is 1 2 3
     */
    private static String format(String format, Object... arguments) {
        String source = "\\{\\}";
        Pattern p = Pattern.compile(source);
        Matcher m = p.matcher(format);

        int i = 0; // arguments
        while (m.find() && i < arguments.length) {
            format = format.replaceFirst(source, String.valueOf(arguments[i++]));
        }
        return format;
    }

}
