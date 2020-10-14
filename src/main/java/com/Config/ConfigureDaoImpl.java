package com.Config;

import org.apache.ibatis.session.SqlSession;
import spark.Session;

import static com.Application.sqlPool;

public class ConfigureDaoImpl {


    public DisplayConfigure getDisplayConfigure(){
        SqlSession session=null;
        DisplayConfigure displayConfigure=null;
        try{
            session= sqlPool.openSession();
            displayConfigure=session.selectOne("getDisplayConfigure");
        }catch (Exception e){
            e.printStackTrace();
        }
        return displayConfigure;
    }


}
