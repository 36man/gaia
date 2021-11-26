package org.bravo.gaia.commons.util;

import org.bravo.gaia.commons.annotation.Sensitive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * 脱敏工具
 * @author lijian
 * @since 2021/10/13
 */
public class DesensitizedUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DesensitizedUtil.class);

    /**
     * desensitized the value for the field
     * @param field the field
     * @param obj the target object
     */
    public static void desensitizedField(Field field, Object obj) {
        Sensitive sensitive = field.getAnnotation(Sensitive.class);
        if (sensitive != null && field.getType() == String.class) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                String desensitize = sensitive.desensitizeStrategy().desensitize(String.valueOf(value));
                field.set(obj, desensitize);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                LOG.warn(e.getMessage());
            }
        }
    }

}
