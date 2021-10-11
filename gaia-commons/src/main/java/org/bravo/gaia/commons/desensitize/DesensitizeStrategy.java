package org.bravo.gaia.commons.desensitize;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 脱敏策略工具
 * 该工具提供的各项脱敏能力都会柔性能力，所谓的柔性能力就是
 * 当传入的参数不满足脱敏条件时，脱敏结果为原内容
 * @author lijian
 * @since 2021/10/9
 */
public enum DesensitizeStrategy {

    NONE("NONE", "不脱敏") {
        @Override
        public String desensitize(String sensitive) {
            return sensitive;
        }
    },

    CHINESE_NAME("CHINESE_NAME", "脱敏中文姓名") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive)) {
                if (sensitive.length() == 2) {
                    sensitive = sensitive.replaceAll("(.).+", "$1*");
                }
                if (sensitive.length() > 2) {
                    StringBuilder strStar = new StringBuilder();
                    for (int i = 0; i < sensitive.length() - 2; i++) {
                        strStar.append("*");
                    }
                    sensitive = sensitive.replaceAll("(.).+(.)", "$1" + strStar + "$2");
                }
            }
            return sensitive;
        }
    },
    ID_NUMBER("ID_NUMBER", "身份证号脱敏") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive)) {
                if (sensitive.length() == 15){
                    sensitive = sensitive.replaceAll("(\\w{6})\\w*(\\w{3})", "$1******$2");
                }
                if (sensitive.length() == 18){
                    sensitive = sensitive.replaceAll("(\\w{6})\\w*(\\w{3})", "$1*********$2");
                }
            }
            return sensitive;
        }
    },
    ADDRESS("ADDRESS", "地址脱敏") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive)) {
                if (sensitive.length() > 6) {
                    sensitive = StringUtils.overlay(sensitive, "*****", sensitive.length() - 5, sensitive.length());
                }
            }
            return sensitive;
        }
    },
    MOBILE_PHONE("PHONE_NUMBER", "手机脱敏") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive)) {
                if (sensitive.length() > 7) {
                    StringBuilder strStar = new StringBuilder();
                    for (int i = 0; i < sensitive.length() - 7; i++) {
                        strStar.append("*");
                    }
                    sensitive = sensitive.replaceAll("(\\w{3})\\w*(\\w{4})", "$1" + strStar + "$2");
                }

            }
            return sensitive;
        }
    },
    FIX_PHONE("FIX_PHONE", "固定电话脱敏") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive)) {
                if (sensitive.length() > 5) {
                    sensitive = StringUtils.overlay(sensitive, "****", sensitive.length() - 4, sensitive.length());
                }
            }
            return sensitive;
        }
    },
    EMAIL("EMAIL", "邮箱脱敏") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive)) {
                sensitive = sensitive.replaceAll("(^\\w)[^@]*(@.*$)", "$1*$2");
            }
            return sensitive;
        }
    },
    CARD("CARD", "银行卡脱敏") {
        @Override
        public String desensitize(String sensitive) {
            if (StringUtils.isNotBlank(sensitive) && sensitive.length() >= 12) {
                String leftPart = StringUtils.substring(sensitive, 0, 5);
                String rightPart = StringUtils.substring(sensitive, sensitive.length() - 4, sensitive.length());
                StringBuilder strStar = new StringBuilder();
                for (int i = 0; i < sensitive.length() - 9; i++) {
                    strStar.append("*");
                }
                sensitive = leftPart + strStar + rightPart;
            }
            return sensitive;
        }
    }
    ;

    @Getter
    private final String key;
    @Getter
    private final String desc;

    DesensitizeStrategy(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public abstract String desensitize(String sensitive);

    public static DesensitizeStrategy getStrategy(String key) {
        for (DesensitizeStrategy strategy: values()) {
            if (strategy.key.equals(key)) {
                return strategy;
            }
        }
        return null;
    }

}
