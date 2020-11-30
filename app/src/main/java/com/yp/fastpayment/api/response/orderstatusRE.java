package com.yp.fastpayment.api.response;

import java.util.List;

public class orderstatusRE {
    private int code;
    private String message;

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

    @Override
    public String toString() {
        return "orderstatusRE{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
