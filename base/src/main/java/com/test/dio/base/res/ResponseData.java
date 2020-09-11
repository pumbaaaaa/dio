package com.test.dio.base.res;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 5778573516446596671L;

    private String status;
    private String code;
    private String message;
    private T data;

    public ResponseData() {
    }

    public ResponseData(String status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ResponseData(String status, String code, T data) {
        this.status = status;
        this.code = code;
        this.data = data;
    }

}
