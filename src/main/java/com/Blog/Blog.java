package com.Blog;


import org.apache.ibatis.annotations.Insert;

import java.sql.Timestamp;

public class Blog {

    private int id;
    private String author;
    private String title;
    private Timestamp postdatetime;
    private String brief;
    private String url;
    public Blog(){}

    public Blog(int id, String author, String title, String brief,String url, String postdatetime){
        this.id=id;
        this.author=author;
        this.title=title;
        this.brief=brief;
        this.postdatetime= Timestamp.valueOf(postdatetime);
        this.url=url;

    }

    //
    // ----------  getters and setters  --------
    //
    public long getId(){
        return this.id;
    }

    public void setId(int id){this.id=id;}

    public void setid(int id){this.id=id;}

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){this.author=author;}

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){this.title=title;}

    public Timestamp getPostdatetime(){return this.postdatetime;}

    public void setPostdatetime(String postdatetime){this.postdatetime=Timestamp.valueOf(postdatetime);}

    public void setPostdatetime(Timestamp postdatetime){this.postdatetime=postdatetime;}

    public void setPostdatetime(Long postdatetime){this.postdatetime=new Timestamp(postdatetime);}

    public String getBrief(){ return this.brief; }

    public void setBrief(String brief){ this.brief=brief; }

    public String getUrl(){ return this.url;}

    public void setUrl(String url){ this.url=url;}


    @Override
    public java.lang.String toString() {
        return "Blog{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", date='" + postdatetime +'\''+
                ", brief='" + brief +'\''+
                ", url='" + url +
                '}';
    }
}
