package com.example.smart.util;


import com.example.smart.entity.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.transform.Result;

/**
 * @author Brandon.
 * @date 2019/5/10.
 * @time 22:42.
 */

@Slf4j
@ControllerAdvice
@ResponseBody
public class WebExceptionHandle {

    @ExceptionHandler
    public ResultBean getNullException(NullPointerException e){
        log.info("出现空值",e);
        return ResultBean.error(-1,"NULL!");
    }
    @ExceptionHandler
    public ResultBean getException(Exception e){
        log.info("出现未知错误",e);
        return ResultBean.error(-99,"未知错误！");
    }
}
