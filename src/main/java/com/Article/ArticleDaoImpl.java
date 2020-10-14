package com.Article;

import com.Blog.Blog;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Optional;

import static com.Application.Articles;
import static com.Application.sqlPool;

public class ArticleDaoImpl implements ArticleDao{
    @Override
    public Optional<Article> get(long id) {
        SqlSession session=null;
        session=sqlPool.openSession();
        Article article=null;
        try{
            article= session.selectOne("getArticleById",id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return Optional.of(article);
    }

    public Article getArticleById(long id) {
        SqlSession session=null;
        session=sqlPool.openSession();
        Article article=null;
        try{
            article= session.selectOne("getArticleById",id);

        }catch (Exception e){
            e.printStackTrace();
        }

        return article;
    }

    public boolean insertArticle(Article article){
        SqlSession sesison=null;
        sesison=sqlPool.openSession();
        try{
            sesison.insert("insertArticle", article);
            sesison.commit();
        }catch (Exception e){
            sesison.rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<Article> getAll() {
        return null;
    }

    @Override
    public void save(Article article) {

    }

    @Override
    public void update(Article article, String[] params) {

    }

    public void updateArticle(Article article){
        SqlSession sesison=null;
        sesison=sqlPool.openSession();
        try{
            sesison.update("updateArticle", article);
            sesison.commit();
        }catch (Exception e){
            sesison.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Article article) {

    }
}
