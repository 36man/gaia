package org.bravo.gaia.app.boot.config;

import org.bravo.gaia.commons.base.HttpResult;
import org.bravo.gaia.commons.enums.SystemErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.MultipartConfigElement;

/**
 * 文件上传大小异常拦截处理
 * @author hejianbing
 * @version @Id: CustomExceptionController.java, v 0.1 2019年04月20日 09:54 hejianbing Exp $
 */
@ControllerAdvice
public class MultipartExceptionAdvice {
    @Autowired(required = false)
    private MultipartConfigElement multipartConfigElement;

    @ResponseBody
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public HttpResult errorHandler(Exception ex) {
        HttpResult httpResult = new HttpResult();
        httpResult.setSuccess(false);
        if (null != multipartConfigElement) {
            Long maxRequestSize = multipartConfigElement.getMaxRequestSize();
            String errorMsg = SystemErrorCodeEnum.FILE_SIZE_EXCEED.getErrorDesc()
                    + "(max" + maxRequestSize / (1024 * 1024) + "M)";
            httpResult.setCode(SystemErrorCodeEnum.FILE_SIZE_EXCEED.getCode().str());
            httpResult.setMessage(errorMsg);
        } else {
            httpResult.setMessage(ex.getMessage());
        }
        return httpResult;
    }
}