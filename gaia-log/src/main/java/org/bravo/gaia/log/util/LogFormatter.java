/**
 * bravo.org
 * Copyright (c) 2018-2019 ALL Rights Reserved
 */
package org.bravo.gaia.log.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.bravo.gaia.commons.errorcode.ErrorCode;

/**
 * 日志格式化
 * @author lijian
 * @version @Id: LogFormatter.java, v 0.1 2019年10月23日 11:14 lijian Exp $
 */
public final class LogFormatter {

    private LogFormatter() {
    }

    public static final String NULL_STR           = "null";
    public static final String WRAPPER_CHAR       = "*";
    public static final String SCENE_CODE_TITLE   = "场景码";
    public static final String SCENE_NAME_TITLE   = "场景";
    public static final String SUCCESS_TITLE      = "是否执行成功";
    public static final String ERROR_CODE_TITLE   = "错误码";
    public static final String REQUEST_BODY_TITLE = "请求体";
    public static final String RESULT_TITLE       = "结果返回";
    public static final String SPLITOR            = " : ";
    public static final String STRING_PLACEHOLDER = "%s";
    public static final String RAW_LOGGER_PATTERN = "BUSINESS_RAW_LOG: [sceneCode:%s, sceneName:%s, SUCCESS:%s, errorCode:%s, params:%s, result:%s]";


    /**
     * 构建日志信息
     * @param sceneCode 场景码
     * @param sceneName 场景描述
     * @param success 是否执行成功
     * @return
     */
    public static String assembleLog(String sceneCode, String sceneName, boolean success) {

        return assembleLog(sceneCode, sceneName, null, null, success, null);
    }

    /**
     * 构建日志信息
     * @param sceneCode 场景码
     * @param sceneName 场景描述
     * @param request 请求参数
     * @param success 是否执行成功
     * @return 格式化后的日志信息
     */
    public static String assembleLog(String sceneCode, String sceneName,
                                     Object[] request, boolean success) {

        return assembleLog(sceneCode, sceneName, request, null, success, null);
    }

    /**
     * 构建日志信息
     * @param sceneCode 场景码
     * @param sceneName 场景描述
     * @param request 请求参数
     * @param errorCode 错误码
     * @param success 是否执行成功
     * @return 格式化后的日志信息
     */
    public static String assembleLog(String sceneCode, String sceneName,
                                     Object[] request, ErrorCode errorCode, boolean success) {

        return assembleLog(sceneCode, sceneName, request, errorCode, success, null);
    }



    /**
     * 构建日志信息
     * @param sceneCode 场景码
     * @param sceneName 场景描述
     * @param request 请求参数
     * @param errorCode 错误码
     * @param success 是否执行成功
     * @param result 结果
     * @return 格式化后的日志信息
     */
    public static String assembleLog(String sceneCode, String sceneName, Object[] request,
                                     ErrorCode errorCode, boolean success, Object result) {
        StringBuilder logMsg = new StringBuilder(StringUtils.EMPTY);

        StringBuffer natureMsg = new StringBuffer(StringUtils.EMPTY);
        natureMsg.append(CharUtils.LF + WRAPPER_CHAR + SCENE_CODE_TITLE + WRAPPER_CHAR
                + SPLITOR + WRAPPER_CHAR + STRING_PLACEHOLDER + WRAPPER_CHAR + CharUtils.LF);
        natureMsg.append(WRAPPER_CHAR + SCENE_NAME_TITLE + WRAPPER_CHAR
                + SPLITOR + WRAPPER_CHAR + STRING_PLACEHOLDER + WRAPPER_CHAR + CharUtils.LF);
        natureMsg.append(WRAPPER_CHAR + SUCCESS_TITLE + WRAPPER_CHAR + SPLITOR + WRAPPER_CHAR
                + STRING_PLACEHOLDER + WRAPPER_CHAR + CharUtils.LF);
        natureMsg.append(WRAPPER_CHAR + ERROR_CODE_TITLE +  WRAPPER_CHAR + SPLITOR
                + WRAPPER_CHAR + STRING_PLACEHOLDER + WRAPPER_CHAR + CharUtils.LF);
        natureMsg.append(WRAPPER_CHAR+ REQUEST_BODY_TITLE + WRAPPER_CHAR + SPLITOR + WRAPPER_CHAR
                + STRING_PLACEHOLDER + WRAPPER_CHAR  + CharUtils.LF);
        natureMsg.append(WRAPPER_CHAR+ RESULT_TITLE + WRAPPER_CHAR + SPLITOR
                + WRAPPER_CHAR + STRING_PLACEHOLDER + WRAPPER_CHAR);
        logMsg.append(String.format(natureMsg.toString(), sceneCode, sceneName,
                String.valueOf(success), errorCode == null ? null : errorCode.str(),
                assembleRequestBody(request), result) + CharUtils.LF);

        StringBuffer digestMsg = new StringBuffer(StringUtils.EMPTY);
        digestMsg.append(String.format(RAW_LOGGER_PATTERN,
                sceneCode, sceneName, String.valueOf(success),
                errorCode == null ? null : errorCode.str(),
                assembleRequestBody(request),
                result) + CharUtils.LF);
        logMsg.append(digestMsg);

        return logMsg.toString();
    }

    /**
     * 构建日志请求体
     */
    private static String assembleRequestBody(Object[] request) {
        StringBuilder requestBody = new StringBuilder();

        if (request != null) {
            for (int i = 0; i < request.length; i++) {
                if (i == request.length - 1) {
                    requestBody.append(request[i]);
                } else {
                    requestBody.append(request[i]).append(",");
                }
            }
        }

        return StringUtils.equals(requestBody.toString(), StringUtils.EMPTY)
                    ? NULL_STR : requestBody.toString();
    }
    
}
