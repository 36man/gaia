package org.bravo.gaia.commons.template;

/**
 * 服务模板的缺省适配器
 */
public class DefaultServiceCallback<R> implements ServiceCallback<R> {

    @Override
    public void checkParam() {
        //ignore
    }

    @Override
    public R process() {
        return null;
    }
}
