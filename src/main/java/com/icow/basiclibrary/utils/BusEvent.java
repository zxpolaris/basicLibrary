package com.icow.basiclibrary.utils;

public class BusEvent {

    /**
     * 需要定义唯一的EventCode类型
     */
    private int code;

    /**
     * 传递的参数(需要传参的话)
     */
    private Object data;
    private Object data2;
    private Object data3;
    public BusEvent(int code) {
        this.code = code;
    }

    public BusEvent(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public BusEvent(int code, Object data, Object data2) {
        this.code = code;
        this.data = data;
        this.data2 = data2;
    }

    public BusEvent(int code, Object data, Object data2 , Object data3) {
        this.code = code;
        this.data = data;
        this.data2 = data2;
        this.data3 = data3;
    }


    public Object getData3() {
        return data3;
    }

    public void setData3(Object data3) {
        this.data3 = data3;
    }

    public Object getData2() {
        return data2;
    }

    public void setData2(Object data2) {
        this.data2 = data2;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BusEvent{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }
}
