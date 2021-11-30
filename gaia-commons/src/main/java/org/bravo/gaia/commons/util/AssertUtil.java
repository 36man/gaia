package org.bravo.gaia.commons.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.bravo.gaia.commons.errorcode.ErrorCode;
import org.bravo.gaia.commons.errorcode.IErrorCode;
import org.bravo.gaia.commons.exception.PlatformException;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 断言工具类
 *
 * @author alex.lj
 * @version @Id: AssertUtil.java, v 0.1 2018年09月08日 20:45 alex.lj Exp $
 */
public final class AssertUtil {

    public static void state(boolean expression, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (!expression) {
            throw new PlatformException(errorCode);
        }
    }

    public static void state(boolean expression, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (!expression) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void state(boolean expression, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        state(expression, errorCodeEnum.getCode());
    }

    public static void state(boolean expression, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        state(expression, errorCodeEnum.getCode(), errorMsg);
    }

    public static void state(boolean expression, String errorMsg) {
        checkArgument(errorMsg);

        if (!expression) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void state(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (!expression) {
            throw new PlatformException(errorCode);
        }
    }

    public static void isTrue(boolean expression, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (!expression) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isTrue(boolean expression, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isTrue(expression, errorCodeEnum.getCode());
    }

    public static void isTrue(boolean expression, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isTrue(expression, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isTrue(boolean expression, String errorMsg) {
        checkArgument(errorMsg);

        if (!expression) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isTrue(boolean expression, Supplier<String> messageSupplier) {
        if (!expression) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNull(Object object, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (object != null) {
            throw new PlatformException(errorCode);
        }
    }

    public static void isNull(Object object, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (object != null) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isNull(Object object, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isNull(object, errorCodeEnum.getCode());
    }

    public static void isNull(Object object, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isNull(object, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isNull(Object object, String errorMsg) {
        checkArgument(errorMsg);

        if (object != null) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isNull(Object object, Supplier<String> messageSupplier) {
        if (object != null) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void notNull(Object object, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (object == null) {
            throw new PlatformException(errorCode);
        }
    }

    public static void notNull(Object object, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (object == null) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void notNull(Object object, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        notNull(object, errorCodeEnum.getCode());
    }

    public static void notNull(Object object, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        notNull(object, errorCodeEnum.getCode(), errorMsg);
    }

    public static void notNull(Object object, String errorMsg) {
        checkArgument(errorMsg);

        if (object == null) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void notNull(Object object, Supplier<String> messageSupplier) {
        if (object == null) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isNotBlank(String text, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (StringUtils.isBlank(text)) {
            throw new PlatformException(errorCode);
        }
    }

    public static void isNotBlank(String text, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (StringUtils.isBlank(text)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isNotBlank(String text, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isNotBlank(text, errorCodeEnum.getCode());
    }

    public static void isNotBlank(String text, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isNotBlank(text, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isNotBlank(String text, String errorMsg) {
        checkArgument(errorMsg);

        if (StringUtils.isBlank(text)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isNotBlank(String text, Supplier<String> messageSupplier) {
        if (StringUtils.isBlank(text)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isBlank(String text, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (StringUtils.isNotBlank(text)) {
            throw new PlatformException(errorCode);
        }
    }

    public static void isBlank(String text, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (StringUtils.isNotBlank(text)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isBlank(String text, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isBlank(text, errorCodeEnum.getCode());
    }

    public static void isBlank(String text, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isBlank(text, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isBlank(String text, String errorMsg) {
        checkArgument(errorMsg);

        if (StringUtils.isNotBlank(text)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isBlank(String text, Supplier<String> messageSupplier) {
        if (StringUtils.isNotBlank(text)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(errorCode);
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void notEmpty(Collection<?> collection, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        notEmpty(collection, errorCodeEnum.getCode());
    }

    public static void notEmpty(Collection<?> collection, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        notEmpty(collection, errorCodeEnum.getCode(), errorMsg);
    }

    public static void notEmpty(Collection<?> collection, String errorMsg) {
        checkArgument(errorMsg);

        if (CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void notEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isEmpty(Collection<?> collection, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (!CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(errorCode);
        }
    }
    public static void isEmpty(Collection<?> collection, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (!CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isEmpty(Collection<?> collection, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isEmpty(collection, errorCodeEnum.getCode());
    }
    public static void isEmpty(Collection<?> collection, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isEmpty(collection, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isEmpty(Collection<?> collection, String errorMsg) {
        checkArgument(errorMsg);

        if (!CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isEmpty(Collection<?> collection, Supplier<String> messageSupplier) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void noNullElements(Object[] array, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new PlatformException(errorCode);
                }
            }
        }
    }

    public static void noNullElements(Object[] array, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
                }
            }
        }
    }

    public static void noNullElements(Object[] array, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        noNullElements(array, errorCodeEnum.getCode());
    }

    public static void noNullElements(Object[] array, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        noNullElements(array, errorCodeEnum.getCode(), errorMsg);
    }

    public static void noNullElements(Object[] array, String errorMsg) {
        checkArgument(errorMsg);

        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new PlatformException(errorMsg);
                }
            }
        }
    }

    public static void noNullElements(Object[] array, Supplier<String> messageSupplier) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new PlatformException(nullSafeGet(messageSupplier));
                }
            }
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (MapUtils.isEmpty(map)) {
            throw new PlatformException(errorCode);
        }
    }

    public static void notEmpty(Map<?, ?> map, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (MapUtils.isEmpty(map)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void notEmpty(Map<?, ?> map, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        notEmpty(map, errorCodeEnum.getCode());
    }

    public static void notEmpty(Map<?, ?> map, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        notEmpty(map, errorCodeEnum.getCode(), errorMsg);
    }

    public static void notEmpty(Map<?, ?> map, String errorMsg) {
        checkArgument(errorMsg);

        if (MapUtils.isEmpty(map)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void notEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
        if (MapUtils.isEmpty(map)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isEmpty(Map<?, ?> map, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (MapUtils.isNotEmpty(map)) {
            throw new PlatformException(errorCode);
        }
    }

    public static void isEmpty(Map<?, ?> map, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (MapUtils.isNotEmpty(map)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isEmpty(Map<?, ?> map, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isEmpty(map, errorCodeEnum.getCode());
    }

    public static void isEmpty(Map<?, ?> map, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isEmpty(map, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isEmpty(Map<?, ?> map, String errorMsg) {
        checkArgument(errorMsg);

        if (MapUtils.isNotEmpty(map)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isEmpty(Map<?, ?> map, Supplier<String> messageSupplier) {
        if (MapUtils.isNotEmpty(map)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, ErrorCode errorCode) {
        checkArgument(errorCode);

        if (!type.isInstance(obj)) {
            throw new PlatformException(errorCode);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, ErrorCode errorCode, String errorMsg) {
        checkArgument(errorCode);
        checkArgument(errorMsg);

        if (!type.isInstance(obj)) {
            throw new PlatformException(errorCode.getErrorDesc() + ":" + errorMsg, errorCode);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, IErrorCode errorCodeEnum) {
        checkArgument(errorCodeEnum);

        isInstanceOf(type, obj, errorCodeEnum.getCode());
    }

    public static void isInstanceOf(Class<?> type, Object obj, IErrorCode errorCodeEnum, String errorMsg) {
        checkArgument(errorCodeEnum);
        checkArgument(errorMsg);

        isInstanceOf(type, obj, errorCodeEnum.getCode(), errorMsg);
    }

    public static void isInstanceOf(Class<?> type, Object obj, String errorMsg) {
        checkArgument(errorMsg);

        if (!type.isInstance(obj)) {
            throw new PlatformException(errorMsg);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, Supplier<String> messageSupplier) {
        if (!type.isInstance(obj)) {
            throw new PlatformException(nullSafeGet(messageSupplier));
        }
    }




    //~~~ 私有方法

    private static String nullSafeGet(Supplier<String> messageSupplier) {
        if (messageSupplier == null) {
            throw new IllegalArgumentException("messageSupplier is null");
        }

        return messageSupplier.get();
    }

    private static void checkArgument(IErrorCode errorCodeEnum) {
        if (errorCodeEnum == null && errorCodeEnum.getCode() == null) {
            throw new IllegalArgumentException("error is null");
        }
    }

    private static void checkArgument(ErrorCode errorCode) {
        if (errorCode == null) {
            throw new IllegalArgumentException("error is null");
        }
    }

    private static void checkArgument(String errorMsg) {
        if (StringUtils.isBlank(errorMsg)) {
            throw new IllegalArgumentException("errorMsg is blank");
        }
    }

}
