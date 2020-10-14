package com.Blog;
import com.Article.Article;
import com.Exception.NoBlogIdInsertArticleException;
import com.util.Timer;
import org.apache.ibatis.exceptions.PersistenceException;

import static com.Application.sqlPool;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlogDaoImpl implements BlogDao{
    static final Logger logger = LoggerFactory.getLogger(BlogDaoImpl.class);

    // get
    @Override
    public Optional<Blog> get(long id) {
        SqlSession session=null;

        session=sqlPool.openSession();
        Blog blog=null;
        try{
            blog=session.selectOne("getOneBlogById",id);
        }catch (PersistenceException e){
            System.out.println("no such key exists");
            e.printStackTrace();
        }
        return Optional.of(blog);
    }

    public Blog getBlogById(long id) {
        SqlSession session=null;

        session=sqlPool.openSession();
        Blog blog=null;
        try{
            blog=session.selectOne("getOneBlogById",id);
        }catch (PersistenceException e){
            System.out.println("no such key exists");
            e.printStackTrace();
        }
        return blog;
    }

    @Override
    public List<Blog> getAll() {
        SqlSession session=null;
        session=sqlPool.openSession();
        List<Blog> blogs = null;
        try{
            blogs=session.selectList("getBlogs");
        }catch (Exception e){
            e.printStackTrace();
        }
        return blogs;
    }

    public List<Blog> getLatestBlogs(){
        int count=5;
        int offset=0;
        SqlSession session=null;
        session=sqlPool.openSession();
        List<Blog> blogs = null;
        Map map =new HashMap<String, Integer>();
        map.put("count",count);
        map.put("offset",offset);
        try{
            blogs=session.selectList("getLatestBlogByRange",map);
        }catch (Exception e){
            e.printStackTrace();
        }
        return blogs;
    }

    public List<Blog> getLatestBlogs(int count, int offset){
        SqlSession session=null;
        session=sqlPool.openSession();
        List<Blog> blogs = null;
        Map map =new HashMap<String, Integer>();
        map.put("count",count);
        map.put("offset",offset);
        try{
            Timer timer=new Timer();
            timer.setTimer();
            blogs=session.selectList("getLatestBlogByRange",map);
            logger.info("time used, "+timer.endTimer());
        }catch (Exception e){
            e.printStackTrace();
        }
        return blogs;
    }



    public int getBlogCount(){
        SqlSession session = null;
        session=sqlPool.openSession();
        int res=0;
        try{
            res=session.selectOne("getBlogCount");
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void save(Blog blog) {
        SqlSession session=null;
        session=sqlPool.openSession();
        try{
            session.insert("insertBlog", blog);
            session.commit();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Blog t, String[] params) {

    }

    @Override
    public void delete(Blog t) {

    }


    // insert
    public long insertBlog(Blog blog){
        SqlSession session=null;
        try{
            session=sqlPool.openSession();
            session.insert("insertBlog", blog);

            session.commit();

        }catch (Exception e){
            e.printStackTrace();
        }
        return blog.getId();
    }

    public boolean insertBlogArticle(Blog blog, Article article){
        SqlSession session=null;
        try{
            session=sqlPool.openSession();
            int blogid=session.selectOne("selectInsertBlogByFunc",blog);
            blog.setid(blogid);
            if(blogid!=-1){
                article.setId(blogid);
                session.insert("insertArticle", article);
            }else{
                throw new NoBlogIdInsertArticleException();
            }
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }catch (NoBlogIdInsertArticleException e){
            e.printStackTrace();
        }
        return true;
    }

    // update
    public boolean updateBlog(Blog blog){
        SqlSession session=null;
        try{
            session=sqlPool.openSession();
            session.update("updateBlogById", blog);
            session.commit();

        }catch (Exception e){
            session.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // delete
    public boolean deleteBlog(Blog blog){
        SqlSession session = null;
        try{
            session=sqlPool.openSession();
            session.delete("deleteBlogById",blog);
            session.commit();
        }catch (Exception e){
            session.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteBlogArticle(Blog blog){
        SqlSession session=null;
        try{
            session=sqlPool.openSession();
            session.delete("deleteArticleById",blog.getId());
            session.delete("deleteBlogById", blog.getId());
            session.commit();

        }catch (Exception e){
            session.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
