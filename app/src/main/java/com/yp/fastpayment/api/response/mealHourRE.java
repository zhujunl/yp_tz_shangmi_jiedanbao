package com.yp.fastpayment.api.response;

import java.util.List;

public class mealHourRE {
    private int code;
    private String message;
    private List<HourData> data;

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

    public List<HourData> getData() {
        return data;
    }

    public void setData(List<HourData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "mealHourRE{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

   public static class HourData{
        private String name;
        private String startTime;
        private String endTime;

       public String getName() {
           return name;
       }

       public void setName(String name) {
           this.name = name;
       }

       public String getStartTime() {
           return startTime;
       }

       public void setStartTime(String startTime) {
           this.startTime = startTime;
       }

       public String getEndTime() {
           return endTime;
       }

       public void setEndTime(String endTime) {
           this.endTime = endTime;
       }
   }
}
