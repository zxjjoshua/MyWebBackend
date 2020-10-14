package com.Exception;

public class NoBlogIdInsertArticleException extends Throwable{
    private String Msg;
    public NoBlogIdInsertArticleException(){
        Msg="trying to insert an article but no article id provided";
    }

    NoBlogIdInsertArticleException(int id){
        Msg="trying to insert an article but no article id provided, the blogid is "+id;
    }


}
