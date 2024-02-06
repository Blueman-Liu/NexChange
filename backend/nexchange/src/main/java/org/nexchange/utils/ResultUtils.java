package org.nexchange.utils;

public class ResultUtils {

    /**
     * 成功且带数据带信息
     **/
    public static Result<Object> success(String msg, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 成功带数据不带信息
     */
    public static Result<Object> success(Object object) {
        return success("success", object);
    }

    /**
     * 成功但不带数据
     **/
    public static Result<Object> success(String msg) {
        return success(msg, null);
    }

    /**
     * 成功但不带数据不发消息
     */
    public static Result<Object> success() {
        return success("success", null);
    }

    /**
     * 失败 401 有数据
     **/
    public static Result<Object> error_401(String msg, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(401);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 失败 401 无数据
     **/
    public static Result<Object> error_401(String msg) {
        return error_401(msg, null);
    }

    /**
     * 失败 404 有数据
     **/
    public static Result<Object> error_404(String msg, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(404);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 失败 401 无数据
     **/
    public static Result<Object> error_404(String msg) {
        return error_404(msg, null);
    }

    /**
     * 失败 500 有数据
     **/
    public static Result<Object> error_500(String msg, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(500);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    /**
     * 失败 500 无数据
     **/
    public static Result<Object> error_500(String msg) {
        return error_500(msg, null);
    }

}
