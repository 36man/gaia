package org.bravo.gaia.commons.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bravo.gaia.commons.annotation.Sensitive;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * DTO基类
 * @author lijian
 * @since 2021/7/13
 */
public class BaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 781768640533536838L;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(
                this, ToStringStyle.SHORT_PREFIX_STYLE) {
            @Override
            protected boolean accept(Field field) {
                Sensitive sensitive = field.getAnnotation(Sensitive.class);
                //todo do sensitive logic
                return true;
            }
        }.toString();
    }
}
