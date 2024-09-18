package com.sree.user.advice

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggerAdvice {

    private val log: Logger = LoggerFactory.getLogger(LoggerAdvice::class.java)

    /**
     * Defines a pointcut that matches the execution of any method within the "com.sree.user" package and its sub-packages.
     */
    @Pointcut(value = "execution(* com.sree.user.*.*.*(..))")
    fun pointcut() {
        // impementation is not required.
    }

    /**
     * An advice method that is executed around the methods matched by the defined pointcut.
     *
     * @param proceedingJoinPoint provides access to the method being advised, allowing the advice to proceed with the method invocation.
     * @return the result of the method execution.
     * @throws Throwable allows any exception thrown by the advised method to be propagated.
     */
    @Around(value = "pointcut()")
    fun applicationLoggerAdvice(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val methodName = proceedingJoinPoint.signature.name
        val className = proceedingJoinPoint.target.javaClass.name
        val params = proceedingJoinPoint.args
        log.debug("Class Name : {}, Method Name : {}(), Arguments : {}", className, methodName, params);
        val proceed = proceedingJoinPoint.proceed();
        log.debug("Class Name : {}, Method Name : {}(), Response : {}", className, methodName, proceed);
        return proceed;
    }
}