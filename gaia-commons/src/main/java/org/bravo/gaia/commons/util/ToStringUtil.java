package org.bravo.gaia.commons.util;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.lang.reflect.Field;

/**
 * @author lijian
 * @since 2021/10/13
 */
public class ToStringUtil {

    private static final String IGNORE_FIELD_SERIAL = "serialVersionUID";

    /**
     * convert an obj to string
     * @param obj the spec obj
     * @return obj's string pattern
     */
    public static String obj2String(Object obj) {
        return new ReflectionToStringBuilder(
                obj, ToStringStyle.SHORT_PREFIX_STYLE) {
            @Override
            protected boolean accept(Field field) {
                DesensitizedUtil.desensitizedField(field, obj);
                return !field.getName().equals(IGNORE_FIELD_SERIAL);
            }
        }.toString();
    }

}
