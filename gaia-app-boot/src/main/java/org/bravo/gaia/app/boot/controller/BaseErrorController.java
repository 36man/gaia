package org.bravo.gaia.app.boot.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * spring boot 自定义error处理器
 * 捕获全局异常处理器之外的异常400,403,403,405,500
 * @author lijian
 * @since 2021/11/30
 */
@Controller
public class BaseErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorResult = "/500";
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                errorResult = "/400";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorResult = "/404";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorResult = "/404";
            }else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                errorResult = "/405";
            }
        }
        return "redirect:" + errorResult;
    }

}
