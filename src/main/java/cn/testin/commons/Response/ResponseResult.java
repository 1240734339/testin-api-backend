package cn.testin.commons.Response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author WangG
 * @title: ResponseResult
 * @description: 通用的响应实体, 用于和前端交互
 */
@Data
@Setter
@Getter
public class ResponseResult<T> implements Serializable {


    private static final long serialVersionUID = -7821828377243576738L;

    /**
     * 返回编码
     **/
    private Integer code = 200;


    /**
     * 返回信息
     **/
    private String msg = "成功";

    /**
     * 返回数据
     **/
    private T data;

    public ResponseResult() {
    }


    public ResponseResult(Integer code, String Message) {
        this.code = code;
        this.msg = Message;
    }


    public static ResponseResult success() {
        return new ResponseResult(200, "成功");
    }

    public static ResponseResult success(String msg) {
        ResponseResult result = new ResponseResult();
        result.setMsg(msg);
        result.setCode(200);
        return result;
    }

    public static ResponseResult<Object> success(Object data) {
        ResponseResult<Object> result = new ResponseResult<>(200, "成功");
        result.setData(data);
        return result;
    }

    public static ResponseResult<Object> success(String msg, Object data) {
        ResponseResult<Object> result = new ResponseResult<>(200, msg);
        result.setData(data);
        return result;
    }


    public static ResponseResult<Object> error(Integer code, String msg) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResponseResult<Object> error(Integer code, Object data) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.setCode(code);
        result.setMsg("失败");
        result.setData(data);
        return result;
    }

    public static ResponseResult<Object> error(Integer code, String msg, Object data) {
        ResponseResult<Object> result = new ResponseResult<Object>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


}
