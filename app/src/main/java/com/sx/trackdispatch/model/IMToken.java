package com.sx.trackdispatch.model;

import java.io.Serializable;

/**
 * @author
 */
public class IMToken implements Serializable {

    public enum RestCode {
        SUCCESS(0, "success"),
        ERROR_INVALID_MOBILE(1, "无效的电话号码"),
        ERROR_SEND_SMS_OVER_FREQUENCY(3, "请求验证码太频繁"),
        ERROR_SERVER_ERROR(4, "服务器异常"),
        ERROR_CODE_EXPIRED(5, "验证码已过期"),
        ERROR_CODE_INCORRECT(6, "验证码错误"),
        ERROR_SERVER_CONFIG_ERROR(7, "服务器配置错误"),
        ERROR_SESSION_EXPIRED(8, "会话不存在或已过期"),
        ERROR_SESSION_NOT_VERIFIED(9, "会话没有验证"),
        ERROR_SESSION_NOT_SCANED(10, "会话没有被扫码"),
        ERROR_SERVER_NOT_IMPLEMENT(11, "功能没有实现"),
        ERROR_GROUP_ANNOUNCEMENT_NOT_EXIST(12, "群公告不存在");
        public int code;
        public String msg;

        RestCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }

    private int code;
    private String message;
    private Result result;

    public static IMToken ok(Result object) {
        return new IMToken(RestCode.SUCCESS, object);
    }

    public static IMToken error(RestCode code) {
        return new IMToken(code, null);
    }

    private IMToken(RestCode code, Result result) {
        this.code = code.code;
        this.message = code.msg;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{
        private String userId;
        private String token;
        private boolean register;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public boolean isRegister() {
            return register;
        }

        public void setRegister(boolean register) {
            this.register = register;
        }
    }

}
