package org.bravo.gaia.commons.enums;

import lombok.Getter;

/**
 * 业务线(单元)名称
 * @author lijian
 * @since 2021/10/8
 */
public enum BizUnitName {

    MAIN_E_MERCHANT("MAIN", "国内电商"),

    ;
    
    @Getter
    private final String code;
    @Getter
    private final String name;

    BizUnitName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public static BizUnitName getBizUnitNameEnum(String code) {
        for (BizUnitName bizUnitName : values()) {
            if (bizUnitName.code.equals(code)) {
                return bizUnitName;
            }
        }
        return null;
    }
}
