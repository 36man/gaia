/**
 * bravo.org
 * Copyright (c) 2018-2019 ALL Rights Reserved
 */
package org.bravo.gaia.app.boot.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * string trim controller处理
 * 处理http request parameter里面的入参
 * @author lijian
 * @version @Id: StringTrimControllerAdvice.java, v 0.1 2019年07月30日 19:04 lijian Exp $
 */
@ControllerAdvice
public class StringTrimControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor propertyEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, propertyEditor);
    }

}
