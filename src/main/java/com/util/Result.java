package com.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.eclipse.jetty.http.HttpStatus;

import javax.annotation.Resources;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result extends HashMap<String, Object> implements Serializable {
    public Result(){
        this.put("isSucc", true);
        this.put("code", HttpStatus.OK_200);
        this.put("msg", "Succ");
    }

    public boolean isSucc(){
        return (boolean)this.get("isSucc");
    }

    public void setSucc(boolean succ){
        this.put("isSucc", succ);
    }

    public int getCode(){
        return (int)this.get("code");
    }

    public void setCode(int code){
        this.put("code", code);
    }

    public String getMsg(){
        return this.get("msg").toString();
    }

    public void setMsg(String msg){
        this.put("msg", msg);
    }

    @JsonIgnore
    public static Result Success(String msg){
        Result resp=new Result();
        resp.setMsg(msg);
        return resp;
    }


    @JsonIgnore
    public static Result Error(String msg){
        Result resp=new Result();
        resp.setSucc(false);
        resp.setMsg(msg);
        return resp;
    }

    @JsonIgnore
    public static Result Success(Map<String,Object> map){
        Result resp=new Result();
        resp.putAll(map);
        return resp;
    }

    @JsonIgnore
    public static Result Error(Map<String, Object> map){
        Result resp=new Result();
        resp.putAll(map);
        return resp;
    }

    @JsonIgnore
    public static Result Success(){
        return new Result();
    }





}