package org.jeecg.modules.exception;

import org.jeecg.modules.utils.BaseUtils;

/**
 * @Author: GH
 * @Date: 2019/12/25 22:07
 * @Version 1.0
 */
public class BusinessException extends RuntimeException {


    private static final long serialVersionUID = 5333594641625781010L;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message,Throwable e){
        super(message, e);
    }

    public BusinessException(Throwable e){
        BaseUtils.loggerError(e);
    }
}