package org.bravo.gaia.app.boot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * 系统默认的错误页面配置controller
 *
 * @author lijian
 * @version $Id: ContainerErrorController.java, v 0.1 2018年04月10日 20:22 lijian Exp $
 */
@Controller
@RequestMapping(value = "/")
public class ContainerViewController {

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

    @RequestMapping(value="400")
    public String error400(){
        return removePrefixSlash(errorPage400);
    }

    @RequestMapping(value="403")
    public String error403(){
        return removePrefixSlash(errorPage403);
    }

    @RequestMapping(value="404")
    public String error404(){
        return removePrefixSlash(errorPage404);
    }

    @RequestMapping(value="405")
    public String error405(){
        return removePrefixSlash(errorPage405);
    }

    @RequestMapping(value="500")
    public String error500(){
        return removePrefixSlash(errorPage500);
    }

    private String removePrefixSlash(String str) {
        return str.substring(1);
    }

}