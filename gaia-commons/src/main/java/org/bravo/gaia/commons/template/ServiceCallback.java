package org.bravo.gaia.commons.template;

/**
 * 服务回调
 *
 * @author lijian
 * @version @Id: ServiceCallback.java, v 0.1 2019年01月14日 23:52 lijian Exp $
 */
public interface ServiceCallback<R> {

    /**
     * 业务校验
     */
    void checkParam();

    /**
     * 业务处理
     * @return 业务结果
     */
    R process();
}
