package org.bravo.gaia.commons.enums;

import lombok.Getter;

/**
 * 业务域名称
 * @author lijian
 * @since 2021/10/8
 */
public enum BizDomainName {

    /** 框架级别使用的业务域(非特定业务域) */
    GENERIC("GEC", "泛化"),

    COMMON("COM", "公共域"),

    TRANSACTION("TRT", "交易域"),

    GOODS("GOG", "商品域"),

    PAYMENT("PAY", "支付域"),

    MARKETING("MAT", "营销域"),

    STOCK("STK", "库存域"),

    FULFILLMENT("OFC", "履约域"),

    CONTENT("COT", "内容域"),

    USER("USR", "用户域"),

    FLOW("FLO", "流量域")
    ;

    @Getter
    private final String code;
    @Getter
    private final String name;

    BizDomainName(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.code;
    }

    public static BizDomainName getBizUnitNameEnum(String code) {
        for (BizDomainName bizUnitName : values()) {
            if (bizUnitName.code.equals(code)) {
                return bizUnitName;
            }
        }
        return null;
    }
}
