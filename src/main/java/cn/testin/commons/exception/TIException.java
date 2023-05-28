package cn.testin.commons.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

// 异常处理类
@EqualsAndHashCode(callSuper = true)
@Getter
@Data
public class TIException extends RuntimeException {

    private static final long serialVersionUID = 6263240026051435010L;
    /**
     * 服务器状态码 默认500
     */
    private Integer code = 500;


    public TIException(String message) {
        super(message);
    }

    public TIException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public TIException(Integer code, Throwable t) {
        super(t);
        this.code = code;
    }

    public TIException(Throwable t) {
        super(t);
    }

    public static void throwException(String message) {
        throw new TIException(message);
    }

    public static void throwException(Integer code, String message) {
        throw new TIException(code, message);
    }


    public static void throwException(Integer code, Throwable t) {
        throw new TIException(code, t);
    }

    public static void throwException(Throwable t) {
        throw new TIException(t);
    }
}
