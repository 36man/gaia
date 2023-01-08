package org.bravo.gaia.app.boot.controller;

import org.bravo.gaia.commons.base.HttpResult;
import org.bravo.gaia.commons.enums.SystemErrorCodeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 系统默认的错误页面配置controller
 *
 * @author lijian
 * @version $Id: ContainerErrorController.java, v 0.1 2018年04月10日 20:22 lijian Exp $
 */
@Controller
@RequestMapping(value = "/")
public class ContainerErrorController {

    @Value("${web.errorPage._400:/400}")
    private String errorPage400;
    @Value("${web.errorPage._403:/403}")
    private String errorPage403;
    @Value("${web.errorPage._404:/404}")
    private String errorPage404;
    @Value("${web.errorPage._405:/405}")
    private String errorPage405;
    @Value("${web.errorPage._500:/500}")
    private String errorPage500;

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE,value="400")
    public String errorHtml400(){
        return removePrefixSlash(errorPage400);
    }


    @RequestMapping(value="400")
    public @ResponseBody HttpResult error400(){
        return HttpResult.fail(SystemErrorCodeEnum.SYSTEM_400.getCode(),"400");
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE,value="403")
    public String errorHtml403(){
        return removePrefixSlash(errorPage403);
    }

    @RequestMapping(value="403")
    public @ResponseBody HttpResult error403(){
        return HttpResult.fail(SystemErrorCodeEnum.SYSTEM_403.getCode(),"403");
    }

    @RequestMapping(value="404")
    public @ResponseBody HttpResult error404(){
        return HttpResult.fail(
                SystemErrorCodeEnum.SYSTEM_404.getCode(),"404");
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE, value="404")
    public String error404Html(){
        return removePrefixSlash(errorPage404);
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE,value="405")
    public String errorHtml405(){
        return removePrefixSlash(errorPage405);
    }


    @RequestMapping(value="405")
    public @ResponseBody HttpResult error405(){
        return HttpResult.fail(SystemErrorCodeEnum.SYSTEM_405.getCode(),"405");
    }

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE,value="500")
    public String errorHtml500(){
        return removePrefixSlash(errorPage500);
    }

    @RequestMapping(value="500")
    public @ResponseBody HttpResult error500(){
        return HttpResult.fail(SystemErrorCodeEnum.SYSTEM_ERROR.getCode(),"500");
    }


    private String removePrefixSlash(String str) {
        return str.substring(1);
    }

}