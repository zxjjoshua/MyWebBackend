package com.Exception;

public class WrongFileTypeException extends Throwable{
    private String Msg;
    public WrongFileTypeException(){
        Msg="error happened when inserting a new article";
    }
}
