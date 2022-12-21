package org.bravo.gaia.commons.enums;

import lombok.Getter;
import org.bravo.gaia.commons.constant.SystemConstant;
import org.bravo.gaia.commons.errorcode.ErrorCode;
import org.bravo.gaia.commons.errorcode.ErrorCodeType;
import org.bravo.gaia.commons.errorcode.IErrorCode;

/**
 * 系统错误码枚举
 * @author lijian
 * @version @Id: SystemErrorCodeEnum.java, v 0.1 2019年01月15日 00:29 lijian Exp $
 */
public enum SystemErrorCodeEnum implements IErrorCode {

    /** 通用系统错误 */
    SYSTEM_ERROR(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(), SystemConstant.GENERIC_MODULE_NAME,
            "0000", ErrorCodeType.SYS_ERROR, "通用系统错误"),

    /** 参数异常 */
    PARAM_ILLEGAL(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(), SystemConstant.GENERIC_MODULE_NAME,
            "0001", ErrorCodeType.BIZ_ERROR, "参数异常"),

    /** 上传文件太大 */
    FILE_SIZE_EXCEED(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(), SystemConstant.GENERIC_MODULE_NAME,
            "0002", ErrorCodeType.BIZ_ERROR, "上传文件太大, 请检查"),

    SYSTEM_404(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(),
            SystemConstant.GENERIC_MODULE_NAME, "0003",ErrorCodeType.SYS_ERROR, "您访问资源不存在"),

    SYSTEM_403(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(),
            SystemConstant.GENERIC_MODULE_NAME, "0004", ErrorCodeType.SYS_ERROR,"您访问的资源不可用,服务器拒绝处理"),

    SYSTEM_405(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(),
            SystemConstant.GENERIC_MODULE_NAME, "0005", ErrorCodeType.SYS_ERROR,"您访问的资源拒绝,访问方法不被允许"),

    SYSTEM_400(BizUnitName.GENERIC.getCode(), BizDomainName.GENERIC.getCode(),
            SystemConstant.GENERIC_MODULE_NAME, "0006", ErrorCodeType.SYS_ERROR,"您访问的资源请求无效");;

    /** 错误码等级 */
    private String        bizUnitCode;

    /** 错误码类型 */
    private String        bizDomainCode;

    /** 系统平台 */
    private String        bizModuleCode;

    /** 错误码编号 */
    private String        codeSequence;

    private ErrorCodeType codeType;

    private ErrorCode     errorCode;

    @Getter
    /** 错误描述 */
    private String        errorDesc;

    /**
     * 构造体
     */
    SystemErrorCodeEnum(String bizUnitCode, String bizDomainCode,
                        String bizModuleCode, String codeSequence,
                        ErrorCodeType codeType, String errorDesc) {
        this.bizUnitCode = bizUnitCode;
        this.bizDomainCode = bizDomainCode;
        this.bizModuleCode = bizModuleCode;
        this.codeSequence = codeSequence;
        this.codeType = codeType;
        this.errorDesc = errorDesc;
        this.errorCode = new ErrorCode(this.bizUnitCode, this.bizDomainCode,
                this.bizModuleCode, this.codeSequence, this.codeType, this.errorDesc);
    }
    

    @Override
    public ErrorCode getCode() {
        return this.errorCode;
    }
}
