package org.bravo.gaia.commons.template;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bravo.gaia.commons.base.RpcResult;
import org.bravo.gaia.commons.enums.SystemErrorCodeEnum;
import org.bravo.gaia.commons.exception.PlatformException;

/**
 * 服务模板
 *
 * @author lijian
 * @version @Id: ServiceTemplate.java, v 0.1 2019年01月14日 23:50 lijian Exp $
 */
public class ServiceTemplate {

    /**
     * 业务模板方法
     * @param serviceCallback 回调方法
     * @return 结果
     */
    public static <R> R execute(ServiceCallback<R> serviceCallback) {
        //业务校验
        serviceCallback.checkParam();

        //业务处理
        return serviceCallback.process();
    }

    /**
     * 业务模板方法，无异常
     * @param serviceCallback 回调方法
     * @return 结果
     */
    public static <R> RpcResult<R> executeIgnoreError(ServiceCallback<R> serviceCallback) {
        RpcResult<R> result = new RpcResult<>();
        try {
            //业务校验
            serviceCallback.checkParam();

            //业务处理
            result.setResultObj(serviceCallback.process());

            //处理成功
            result.setSuccess(true);
        } catch (Throwable e) {
            //设置失败标识
            result.setSuccess(false);
            //填充错误上下文
            result.setErrorStackTrace(ExceptionUtils.getStackTrace(e));
            if (e instanceof PlatformException) {
                result.addErrorContext(((PlatformException) e).getErrorContext());
            } else {
                result.addErrorCode(SystemErrorCodeEnum.SYSTEM_ERROR.getCode());
            }

            return result;
        }

        return result;
    }

}
