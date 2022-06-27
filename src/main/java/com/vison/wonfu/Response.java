package com.vison.wonfu;
import org.slf4j.MDC;

public class Response {
    private int code;
    private String msg;
    private Object data;
    private String uniReqNo;

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.uniReqNo = MDC.get("trace-id");
    }

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = new Object();
        this.uniReqNo = MDC.get("trace-id");
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getUniReqNo() {
        return uniReqNo;
    }

    public void setUniReqNo(String uniReqNo) {
        this.uniReqNo = uniReqNo;
    }
}
