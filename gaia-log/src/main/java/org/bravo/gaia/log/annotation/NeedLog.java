/**
 * bravo.org
 * Copyright (c) 2018-2019 All Rights Reserved
 */
package org.bravo.gaia.log.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通用日志注解
 * @author lijian
 * @version @Id: NeedLog.java, v 0.1 2018年09月08日 22:13 lijian Exp $
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NeedLog {

    /** 场景名称 */
    String sceneCode();

    /** 场景名称 */
    String sceneName() default StringUtils.EMPTY;

}
