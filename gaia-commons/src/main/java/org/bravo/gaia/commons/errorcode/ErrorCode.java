package org.bravo.gaia.commons.errorcode;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统错误码
 * <p>错误码码位总共17位，不多不少，必须满足；
 * ┌────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────┬────────┬────────┬───────┐
 * │ 1  │  2 │ 3  │ 4  │ 5  │ 6  │ 7  │ 8  │ 9  │ 10 │ 11 │ 12 │ 13 │ 14 │   15   │   16   │   17  │
 * ├────┴────┴────┴────┼────┴────┴────┼────┴────┴────┼────┴────┴────┴────┼────────┼────────┼───────┤
 * │                   │              │              │                   │        │        │       │
 * │   业务线的名称      │  业务域的名称  │   业务模块名称 │     错误码序列号    │ 码类型  │ 扩展位  │ 扩展位 │
 * └───────────────────┴──────────────┴──────────────┴───────────────────┴────────┴────────┴───────┴
 *
 * @author lijian
 * @since 2021/09/22
 */
public class ErrorCode implements Serializable {

    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 2377988816412754062L;

    /**
     * 默认占位符
     */
    private static final String DEFAULT_PLACEHOLDER = "0";

    @Getter
    /** 业务线code 第1-4位 用大写英文描述 */
    private String bizUnitCode;

    @Getter
    /** 业务域code 第5-7位 用大写英文描述 */
    private String bizDomainCode;

    @Getter
    /** 业务模块code 第8-10位 用大写英文描述 */
    private String bizModuleCode;

    @Getter
    /** 错误码序列号 第11-14位 用数字描述 */
    private String codeSequence;

    @Getter
    /** 错误码类型 第15位 */
    private ErrorCodeType codeType;

    @Getter
    /** 扩展位 */
    private String extendSlot;

    @Getter
    /** 错误描述 */
    private String errorDesc;

    // ~~~ 构造方法
    public ErrorCode() {
    }

    public ErrorCode(String bizUnitCode, String bizDomainCode, String bizModuleCode, String codeSequence,
                     ErrorCodeType errorCodeType, String errorDesc) {
        this.bizUnitCode = bizUnitCode;
        this.bizDomainCode = bizDomainCode;
        this.bizModuleCode = bizModuleCode;
        this.codeSequence = codeSequence;
        this.codeType = errorCodeType;
        this.errorDesc = errorDesc;
        this.extendSlot = DEFAULT_PLACEHOLDER + DEFAULT_PLACEHOLDER;
        check();
    }

    /**
     * 根据单一字符串构造错误码
     */
    public ErrorCode(String codeString) {
        this(codeString, null);
    }

    /**
     * 根据单一字符串构造错误码
     */
    public ErrorCode(String codeString, String errorDesc) {
        //长度检查
        checkLength(codeString, 17);
        //拆分错误码
        splitResultCode(codeString);
        this.errorDesc = errorDesc;
    }

    @Override
    public String toString() {
        //错误检查
        check();

        //组装错误码字符串
        return fetchResultCodeString();
    }

    @Override
    public int hashCode() {
        return this.fetchResultCodeString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return StringUtils.equals(this.fetchResultCodeString(), (((ErrorCode) obj).fetchResultCodeString()));
    }

    // ~~~ 内部方法

    private void check() {
        checkLength(this.bizUnitCode, 4);
        checkLength(this.bizDomainCode, 3);
        checkLength(this.bizModuleCode, 3);
        checkLength(this.codeSequence, 4);
        if (this.codeType == null) {
            throw new IllegalArgumentException("codeType is null");
        }
        checkLength(this.codeType.toString(), 1);
        checkLength(this.extendSlot, 2);
    }

    /**
     * 解析和拆分错误码。
     *
     * @param resultCode 错误码字符串
     */
    private void splitResultCode(String resultCode) {
        char[] chars = resultCode.toCharArray();
        this.bizUnitCode = char2Str(chars[0], chars[1], chars[2], chars[3]);
        this.bizDomainCode = char2Str(chars[4], chars[5], chars[6]);
        this.bizModuleCode = char2Str(chars[7], chars[8], chars[9]);
        this.codeSequence = char2Str(chars[10], chars[11], chars[12], chars[13]);
        this.codeType = ErrorCodeType.BIZ_ERROR.getCodeType(String.valueOf(chars[14]));
        this.extendSlot = char2Str(chars[15], chars[16]);
    }

    private String char2Str(char... ch) {
        StringBuilder sb = new StringBuilder();
        for (char c : ch) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 字符串长度检查。
     */
    private void checkLength(String codeString, int length) {
        if (codeString == null || codeString.length() != length) {
            throw new IllegalArgumentException(codeString);
        }
    }

    /**
     * 返回错误码字符串的格式
     */
    private String fetchResultCodeString() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.bizUnitCode);
        sb.append(this.bizDomainCode);
        sb.append(this.bizModuleCode);
        sb.append(this.codeSequence);
        sb.append(this.codeType.toString());
        sb.append(this.extendSlot);

        return sb.toString();
    }

}
