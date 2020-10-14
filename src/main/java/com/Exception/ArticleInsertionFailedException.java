package com.Exception;

public class ArticleInsertionFailedException extends Exception{
    private String Msg;
    public ArticleInsertionFailedException(){
        Msg="error happened when inserting a new article";
    }
}
