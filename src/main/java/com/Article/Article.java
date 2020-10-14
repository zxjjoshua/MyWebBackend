package com.Article;

public class Article {
    private long id;
    private String content;

    public Article(){}

    public Article(long id, String content){
        this.id=id;
        this.content=content;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
