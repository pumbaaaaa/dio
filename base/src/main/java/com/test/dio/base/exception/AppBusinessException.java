package com.test.dio.base.exception;

public class AppBusinessException extends RuntimeException {

    private String msg;

    public AppBusinessException() {super();}

    public AppBusinessException(String msg) {super(msg);}

    public String getMsg() {return this.msg;}
}
