package com.example.smart.entity;

import lombok.Data;

import javax.xml.transform.Result;
import java.util.Collection;

/**
 * @author Brandon.
 * @date 2019/5/10.
 * @time 22:34.
 */

@Data
public class ResultBean<T> {
    private int code;
    private String message;
    private Object data;

    public static ResultBean error(int code, String message){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(code);
        resultBean.setMessage(message);
        return resultBean;
    }
    public static ResultBean success(){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(200);
        resultBean.setMessage("success");
        return resultBean;
    }
    public static ResultBean success(Object data){
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(200);
        resultBean.setMessage("success");
        resultBean.setData(data);
        return resultBean;
    }
}
