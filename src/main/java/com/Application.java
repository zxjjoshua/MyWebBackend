package com;


import com.Article.ArticleDaoImpl;
import com.Blog.BlogDaoImpl;


import com.Blog.BlogController;
import com.Config.ConfigureController;

import static spark.Spark.*;

import com.Config.ConfigureDaoImpl;
import com.common.DataBase;
import com.util.*;
import com.util.PATH;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.io.InputStream;


public class Application {
    public static BlogDaoImpl Blogs;
    public static ConfigureDaoImpl Configures;
    public static ArticleDaoImpl Articles;
    public static SqlSessionFactory sqlPool;
    public static DataBase DB;
    public static void main(String[] args){
        Blogs=new BlogDaoImpl();
        Articles=new ArticleDaoImpl();
        Configures=new ConfigureDaoImpl();
//        DB=new DataBase();
//        if (!DB.init()){
//            System.out.println("Error with database init");
//        }
        String resource="MybatisConfig.xml";
        try{
            InputStream input=Resources.getResourceAsStream(resource);
            sqlPool=new SqlSessionFactoryBuilder().build(input);

        }catch (IOException e){
            e.printStackTrace();
            System.out.println("database connection failed");
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
        }
        port(3333);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);


        before("*",                  Filters.addTrailingSlashes);
        before("*",                  Filters.handleLocaleChange);
//        options("/*",
//                (request, response) -> {
//
//                    String accessControlRequestHeaders = request
//                            .headers("Access-Control-Request-Headers");
//                    if (accessControlRequestHeaders != null) {
//                        response.header("Access-Control-Allow-Headers",
//                                accessControlRequestHeaders);
//                    }
//
//                    String accessControlRequestMethod = request
//                            .headers("Access-Control-Request-Method");
//                    if (accessControlRequestMethod != null) {
//                        response.header("Access-Control-Allow-Methods",
//                                accessControlRequestMethod);
//                    }
//
//                    return "OK";
//                });
//        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
//        get(PATH.API.BLOGINFO,                BlogController.fecthBlogInfo, new JsonUtil());
//        get(PATH.API.ONE_BLOG,                BlogController.fetchOneBlogById, new JsonUtil());
//        get(PATH.API.BLOGS,                BlogController.fetchAllBlogs, new JsonUtil());
        path("/api/blogs", ()->{ BlogController.Controller();});


//        get("test/", )

    }

}
