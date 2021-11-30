package org.bravo.gaia.app.boot.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 利用spring i18n完成返回给客户端消息的国家化改造工具类
 *
 * @author lijian
 */
public class MessageUtil {

    protected static MessageSourceAccessor accessor;
    protected static MessageSource messageSource;
    protected static Set<String> set = new HashSet<String>();
    private final static int DEFAULT_STATUS_CODE = 0;
    static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    static {
        addLocation("classpath*:i18n/*");
    }

    protected static void initMessageSourceAccessor() {
        String[] baseNames = new String[set.size()];
        set.toArray(baseNames);
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasenames(baseNames);
        reloadableResourceBundleMessageSource.setCacheSeconds(5);
        accessor = new MessageSourceAccessor(reloadableResourceBundleMessageSource);
        messageSource = reloadableResourceBundleMessageSource;
    }

    public static void removeLocation(String locationPattern) {
        try {
            Resource[] resources = resourcePatternResolver.getResources(locationPattern);
            for (int i = 0; i < resources.length; i++) {
                set.remove(getI18nFileLocation(resources[i].getURL().toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initMessageSourceAccessor();
    }

    public static void addLocation(String locationPattern) {
        try {
            Resource[] resources = resourcePatternResolver.getResources(locationPattern);
            for (int i = 0; i < resources.length; i++) {
                set.add(getI18nFileLocation(resources[i].getURL().toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initMessageSourceAccessor();
    }

    private static String getI18nFileLocation(String url) {
        int lastIndex = url.lastIndexOf("/");
        String prefix = url.substring(0, lastIndex + 1);
        String suffix = url.substring(lastIndex + 1);
        suffix = suffix.split("\\.")[0].split("_")[0];
        return prefix + suffix;
    }

    private MessageUtil() {
    }

    public static Message message(int status, String code, Object[] args, Object data) {
        return new Message(status, getMessage(code, args), data);
    }

    public static Message message(int status, String code, Object data) {
        return new Message(status, getMessage(code), data);
    }

    public static Message message(int status, String code, Object[] args) {
        return new Message(status, getMessage(code, args));
    }

    public static Message message(int status, String code) {
        return new Message(status, getMessage(code));
    }

    public static Message message(int status, Object data) {
        return new Message(status, StringUtils.EMPTY, data);
    }

    public static Message message(String code) {
        return new Message(DEFAULT_STATUS_CODE, getMessage(code));
    }

    public static Message message(String code, Object[] args) {
        return new Message(DEFAULT_STATUS_CODE, getMessage(code, args));
    }

    public static Message message(String code, Object[] args, Object data) {
        return new Message(DEFAULT_STATUS_CODE, getMessage(code, args), data);
    }

    public static Message message(String code, Object data) {
        return new Message(DEFAULT_STATUS_CODE, getMessage(code), data);
    }

    public static String getMessage(String code) {
        return accessor.getMessage(code, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, String defaultMessage) {
        return accessor.getMessage(code, defaultMessage, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args) {
        return accessor.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return accessor.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }

}
