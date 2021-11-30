/**
 * bravo.org
 * Copyright (c) 2018-2019 All Rights Reserved
 */
package org.bravo.gaia.log.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bravo.gaia.commons.base.RpcResult;
import org.bravo.gaia.commons.enums.SystemErrorCodeEnum;
import org.bravo.gaia.commons.errorcode.ErrorCode;
import org.bravo.gaia.commons.exception.PlatformException;
import org.bravo.gaia.log.GaiaLogger;
import org.bravo.gaia.log.annotation.NeedLog;
import org.bravo.gaia.log.util.LogFormatter;
import org.slf4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 通用日志拦截器
 * @author lijian
 * @version @Id: LogInterceptor.java, v 0.1 2018年09月08日 22:05 lijian Exp $
 */
@Configuration
@Aspect
@Slf4j
public class LogInterceptor {

    @Pointcut("@annotation(org.bravo.gaia.log.annotation.NeedLog)")
    public void logPointcut(){}

    @Around("logPointcut()")
    public Object methodAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        NeedLog needLog = null;
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;

            Method realMethod = ReflectionUtils.findMethod(AopUtils.getTargetClass(joinPoint.getTarget()), methodSignature.getName(),
                    methodSignature.getMethod().getParameterTypes());
            assert realMethod != null;
            needLog = realMethod.getDeclaredAnnotation(NeedLog.class);
        }
        Throwable currentThrowable = null;

        Object result = null;
        try {
            //执行目标方法
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            //如果当前使用log注解，则判断是否需要判断异常，并获取当前异常堆栈
            if (null != needLog) {
                currentThrowable = throwable;
            }
            throw throwable;
        } finally {
            //根据当前目标方法执行情况记录日志
            record(joinPoint, needLog, currentThrowable, result);
        }

        return result;
    }

    /**
     * 记录日志
     */
    private void record(ProceedingJoinPoint joinPoint, NeedLog needLog,
                        Throwable throwable, Object result) {
        if (null != needLog) {
            Logger errorLogger = GaiaLogger.getGlobalErrorLogger();

            boolean isError = false;

            ErrorCode errorCode = null;
            //如果当前执行出错，将堆栈信息输出到error logger
            if (null != throwable) {
                isError = true;
                errorLogger.error(ExceptionUtils.getStackTrace(throwable));
                if (throwable instanceof PlatformException) {
                    errorCode = ((PlatformException) throwable).getErrorContext().getCurrentErrorCode();
                } else {
                    errorCode = SystemErrorCodeEnum.SYSTEM_ERROR.getCode();
                }
            }
            //如果返回的结果为RpcResult
            if (result instanceof RpcResult) {
                RpcResult<?> baseResult = (RpcResult<?>) result;
                if (!baseResult.isSuccess()
                        || baseResult.getErrorContext().getCurrentErrorCode() != null) {
                    isError = true;
                    errorCode = baseResult.getErrorContext().getCurrentErrorCode();
                    errorLogger.error(StringUtils.isNotBlank(baseResult.getErrorStackTrace()) ? baseResult.getErrorStackTrace() : errorCode.getErrorDesc());
                }
            }

            //全局普通logger记录方法综合执行情况
            log.info(
                    LogFormatter.assembleLog(
                            needLog.sceneCode(), needLog.sceneName(), joinPoint.getArgs(),
                            errorCode, !isError, result
                    )
            );
        }
    }
}
