package org.bravo.gaia.log;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * gaia提供的基础日志工具
 * @author lijian
 * @version $Id: GaiaLogUtil.java, v 0.1 2018年04月13日 11:56 lijian Exp $
 */
public final class GaiaLogger {

    public final static String GLOBAL_ERROR_LOGGER = "errors";

    public static Logger getGlobalErrorLogger() {
        return LoggerFactory.getLogger(GLOBAL_ERROR_LOGGER);
    }

}