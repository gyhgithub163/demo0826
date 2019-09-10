package com.example.demo0826.z_customTool.result;
/*
* 状态码
* 作用:声明状态码，需配合Response返回接口状态。
* */
public enum  ResponseCode {
    SUCCESS(15,"执行成功"),
    ERROR(16,"执行失败"),
    EMPTY(17,"数据不存在");

    private int code;
    private String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
