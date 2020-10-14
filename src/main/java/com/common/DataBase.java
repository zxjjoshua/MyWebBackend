package com.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.Article.Article;
import com.Blog.BlogDaoImpl;
import com.Article.ArticleDaoImpl;
import com.Config.ConfigureDaoImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DataBase {
    public static BlogDaoImpl Blogs;
    public static ArticleDaoImpl Article;
    public static ConfigureDaoImpl Configure;
    public static SqlSessionFactory sqlPool;
    public DataBase(){
        Blogs=new BlogDaoImpl();
        Article=new ArticleDaoImpl();
        Configure=new ConfigureDaoImpl();
        this.init();
    }

    public boolean init(){
        String resource="MybatisConfig.xml";
        try{
            InputStream input= Resources.getResourceAsStream(resource);
            sqlPool=new SqlSessionFactoryBuilder().build(input);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("database connection failed");
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean init(String resource){
        if(resource.length()==0){
            return this.init();
        }
        try{
            InputStream input= Resources.getResourceAsStream(resource);
            sqlPool=new SqlSessionFactoryBuilder().build(input);
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("database connection failed");
            System.exit(0);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public SqlSessionFactory getSqlPool(){
        return sqlPool;
    }



}
