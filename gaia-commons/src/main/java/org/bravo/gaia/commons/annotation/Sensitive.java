package org.bravo.gaia.commons.annotation;


import org.bravo.gaia.commons.desensitize.DesensitizeStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识脱敏属性
 * @author lijian
 * @since 2020.07.13
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sensitive {

    DesensitizeStrategy desensitizeStrategy() default DesensitizeStrategy.NONE;

}
