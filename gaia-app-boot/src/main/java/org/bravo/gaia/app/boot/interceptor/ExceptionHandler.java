package org.bravo.gaia.app.boot.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bravo.gaia.commons.base.HttpResult;
import org.bravo.gaia.commons.constant.WebConstant;
import org.bravo.gaia.log.GaiaLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 定义MVC的异常处理，当MVC出现异常时候，此类中的方法进行全局异常捕获并进行处理
 * @author lijian
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    @Value("${web.errorPage._500:/500}")
    private String errorPage500;
    @Value("${web.errorPage._403:/403}")
    private String errorPage403;

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView();

        //assemble error message
        HttpResult httpResult = HttpResult.fail(ex);

        if (StringUtils.equals(request.getHeader(WebConstant.HTTP_REQUEST_TYPE),
                WebConstant.HTTP_REQUEST_TYPE_AJAX)) {
            //send to client
            sendMessage(response, httpResult);
        } else {
            //view request
            //set value to request scope，return 500 template
            modelAndView.addObject(HttpResult.KEY_SUCCESS, Boolean.FALSE.toString());
            modelAndView.addObject(HttpResult.KEY_DATA, httpResult.getData());
            modelAndView.addObject(HttpResult.KEY_MSG, httpResult.getMessage());
            modelAndView.addObject(HttpResult.KEY_CODE, httpResult.getCode());

            if (ex.getClass().getSimpleName().equals(HttpResult.ACCESS_DENIED_EXCEPTION)) {
                modelAndView.setViewName(errorPage403);
            }
            else {
                modelAndView.setViewName(errorPage500);
            }
        }

        //log error
        GaiaLogger.getGlobalErrorLogger().error(ExceptionUtils.getStackTrace(ex));

        return modelAndView;
    }

    private static void sendMessage(HttpServletResponse response, HttpResult httpResult) {
        response.setHeader(WebConstant.CONTENT_TYPE, WebConstant.CONTEXT_TYPE_JSON);
        try {
            String errorMessageJson = objectMapper.writeValueAsString(httpResult);
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessageJson.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        } catch (IOException e) {
            GaiaLogger.getGlobalErrorLogger().error("servlet response write error:" + e.getMessage());
            GaiaLogger.getGlobalErrorLogger().error(ExceptionUtils.getStackTrace(e));
            e.printStackTrace();
        }
    }

}
