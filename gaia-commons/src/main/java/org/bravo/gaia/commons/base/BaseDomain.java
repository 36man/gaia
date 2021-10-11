package org.bravo.gaia.commons.base;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.bravo.gaia.commons.annotation.Sensitive;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 领域模型基类
 * @author lijian
 * @since 2021.10.08
 */
public abstract class BaseDomain implements Serializable {

    /** serialVersionUID */
    @Serial
    private static final long serialVersionUID = 8077842703435876062L;

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
