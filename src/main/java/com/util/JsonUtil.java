package com.util;
import com.fasterxml.jackson.databind.*;
import spark.ResponseTransformer;

import java.io.IOException;
import java.util.Map;
import com.google.gson.Gson;

public class JsonUtil implements ResponseTransformer {
    public static String ObjToJson(Object obj){
        ObjectMapper mapper = new ObjectMapper();
        String str=null;
        try{
            str= mapper.writeValueAsString(obj);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(str==null){
                str="None";
            }
        }


        return str;
    }

    public static Object JsonToObject(String json){
        Object obj=new Object();
        Gson gson=new Gson();
        return obj;
    }

    @Override
    public String render(Object obj) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String str=null;
        try{
            str= mapper.writeValueAsString(obj);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(str==null){
                str="None";
            }
        }


        return str;
    }
}
