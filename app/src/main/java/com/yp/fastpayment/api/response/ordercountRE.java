package com.yp.fastpayment.api.response;

import java.util.List;

public class ordercountRE {
//     "code": 200,
//             "message": "SUCCESS",
//             "data": 45
    private int code;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ordercountRE{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class Data{
        private int len;
        private List<String> cancelList;

        public int getLen() {
            return len;
        }

        public void setLen(int len) {
            this.len = len;
        }

        public List<String> getCancelList() {
            return cancelList;
        }

        public void setCancelList(List<String> cancelList) {
            this.cancelList = cancelList;
        }
    }
}
