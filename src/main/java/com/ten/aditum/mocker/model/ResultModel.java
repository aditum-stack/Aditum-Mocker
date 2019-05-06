package com.ten.aditum.mocker.model;

import lombok.Data;

/**
 * JSON返回数据
 */
@Data
public class ResultModel {

    private int code;
    private String msg;
    private Object data;

    public ResultModel() {
    }

    public ResultModel(AditumCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = "";
    }

    public ResultModel(AditumCode code, Object data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" + "code=" + code + ", msg=" + msg + ", data=" + data + "}";
    }
}
