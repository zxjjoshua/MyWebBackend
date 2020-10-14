package com.Blog;

import com.Article.Article;
import com.Config.DisplayConfigure;
import com.Exception.ArticleInsertionFailedException;
import com.Exception.WrongFileTypeException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.util.JsonUtil;
import com.util.PATH;
import com.util.Result;
import com.util.Timer;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import spark.Request;
import spark.Response;
import spark.Route;

import static com.Application.*;
import static com.util.RequestUtil.*;
import static spark.Spark.get;
import static spark.Spark.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.fileupload.*;


public class
BlogController {
    final static Logger logger = LoggerFactory.getLogger(BlogController.class);

    //---  /api/blogs/
    public static void Controller(){

        get("/blogarticle/:blogid/",                fetchOneBlogById, new JsonUtil());
        get("/bloglistinfo/",                       fecthBlogInfo, new JsonUtil());
        get("/test/",                               fetchTestData, new JsonUtil());
        post("/blogarticle/",                       uploadBlog, new JsonUtil());
    };

    public static Route fecthBlogInfo = (Request request, Response response) -> {
//        LoginController.ensureUserIsLoggedIn(request, response);
        Timer timer=new Timer();
        timer.setTimer();
        Result resp=new Result();
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        int blogCount=5;
        int blogOffset=0;
        if(request.queryParams("offset")!=null){
            blogOffset=Integer.valueOf(request.queryParams("offset"));
        }
        if(request.queryParams("count")!=null){
            blogCount=Integer.valueOf(request.queryParams("count"));
        }
        logger.info("this is offset "+blogOffset+", and this is count"+blogCount);
        if (true||clientAcceptsJson(request)) {
            try{
                resp.put("TotalCount", Blogs.getBlogCount());
//                resp.put("TotalCount", 9);
                logger.info("TotalCount Done, "+timer.setTmpTimer());
                resp.put("BlogList", Blogs.getLatestBlogs(blogCount,blogOffset));
                logger.info("BlogList Done, "+timer.setTmpTimer());

//                DisplayConfigure disp=new DisplayConfigure();
//                disp.setCountPerPage(5);
//                disp.setUserId(5);
//                resp.put("DisplaySetting", disp);
                resp.put("DisplaySetting", Configures.getDisplayConfigure());
                logger.info("DisplaySetting Done, "+timer.setTmpTimer());
            }
            catch (Exception e){
                resp.setSucc(false);
                e.printStackTrace();
            }

        }
        else{
            resp.setSucc(false);
        }
        logger.info(resp.isSucc()+" "+resp.getCode()+", and process time is "+timer.endTimer());
        return resp;
    };


    public static Route fetchOneBlogById = (Request request, Response response)->{
        Timer timer=new Timer();
        timer.setTimer();
        Result resp=new Result();
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        int id=Integer.valueOf(request.params(":blogid"));
        logger.info("this is blogid: "+request.params(":blogid"));
        if (true||clientAcceptsJson(request)) {

            try{
                resp.put("blog", Blogs.getBlogById(id));
                resp.put("article", Articles.getArticleById(id));
            }
            catch (Exception e){
                resp.setSucc(false);
                e.printStackTrace();
            }

        }
        else{
            resp.setSucc(false);
        }
        logger.info(resp.isSucc()+" "+resp.getCode()+", and process time is "+timer.endTimer());
        return resp;
    };

    public static Route fecthBlogsByRange = (Request request, Response response)->{
        Timer timer=new Timer();
        timer.setTimer();
        Result resp=new Result();
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        logger.info(request.body());
        if (true||clientAcceptsJson(request)) {

            try{
                resp.put("blog", Blogs.getAll());
            }
            catch (Exception e){
                resp.setSucc(false);
                e.printStackTrace();
            }

        }
        else{
            resp.setSucc(false);
        }
        logger.info(resp.isSucc()+" "+resp.getCode()+", and process time is "+timer.endTimer());
        return resp;
    };


    public static Route uploadBlog = (Request request, Response response)->{
        Timer timer=new Timer();
        timer.setTimer();
        Result resp=new Result();
        response.type("application/json");
        response.header("Access-Control-Allow-Origin","*");
        if (clientAcceptsJson(request)){
            try{
                Blog bloginfo=new Blog();
                Article article=new Article();
                if(parseFormdataRequestBodyForBlogArticle(request.raw(), bloginfo, article)){
                    if(bloginfo.getId()==-1){// new upload
                        if (!Blogs.insertBlogArticle(bloginfo, article)){
                            logger.info("uploadBlog: upload blog requets faield");
                            throw new ArticleInsertionFailedException();
                        }
                    } else{// this is an request for update old blog
                        Blogs.updateBlog(bloginfo);
                        Articles.updateArticle(article);
                    }
                }
                else{
                    logger.info(article.toString());
                    resp.setSucc(false);
                }
            }catch (WrongFileTypeException e){
                logger.info("unsupported file type");
                resp.setSucc(false);
                resp.setMsg("unsupported file type");
            }
            catch (ArticleInsertionFailedException e){
                logger.info("article not qualified");
                resp.setSucc(false);
                resp.setMsg("article not qualified");
            } catch (Exception e){
                resp.setSucc(false);
                e.printStackTrace();
            }
        }
        logger.info(resp.isSucc()+" "+resp.getCode()+", and process time is "+timer.endTimer());
        return resp;
    };

    public static Route fetchTestData=(Request resquest, Response response)->{
        Timer timer=new Timer();
        timer.setTimer();
        Result resp=new Result();
        response.type("application/json");
        response.header("Access-Control-Allow-Origin","*");
        resp.put("OK",true);
        logger.info(resp.isSucc()+" "+resp.getCode()+", and process time is "+timer.endTimer());
        return resp;
    };

    private static boolean parseRequestBodyForBlogArticle(String body, Blog blog, Article article){
        Timer timer=new Timer();
        timer.setTimer();

        Gson gson=new Gson();
        JsonObject obj=null;
        try{
            obj=gson.fromJson(body, JsonObject.class);
            int userid=obj.get("userId").getAsInt();
            JsonObject blogbody=obj.getAsJsonObject("blog");
            String author="me";
            int id=blogbody.get("id").getAsInt();
            String title=blogbody.get("title").getAsString();
            String content=blogbody.get("content").getAsString();
            String brief=blogbody.get("brief").getAsString();
            Long time=blogbody.get("date").getAsLong();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime=simpleDateFormat.format(time);
            blog.setId(id);
            blog.setAuthor(author);
            blog.setTitle(title);
            blog.setPostdatetime(dateTime);
            if (brief.length()==0){
                if(content.length()>50){
                    blog.setBrief(content.substring(51));
                }else{
                    blog.setBrief(content);
                }
            }
            article.setId(id);
            article.setContent(content);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    private static boolean parseFormdataRequestBodyForBlogArticle(HttpServletRequest request, Blog blog, Article article) throws WrongFileTypeException {
        try{
            List<FileItem> items=new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();
                    //logger.info(fieldName+": "+fieldValue);
                    // ... (do your job here)
                    switch (fieldName){
                        case "userId":
                            blog.setAuthor("me");
                            break;
                        case "id":
                            blog.setid(Integer.valueOf(fieldValue));
                            article.setId(Integer.valueOf(fieldValue));
                            break;
                        case "title":
                            blog.setTitle(fieldValue);
                            break;
                        case "brief":
                            blog.setBrief(fieldValue);
                            break;
                        case "date":
                            blog.setPostdatetime(Long.valueOf(fieldValue));
                            break;
                        case "content":
                            article.setContent(preProcessStr2SQLFormat(fieldValue));
                            break;
                        default:
                            break;
                    }

                } else {
                    // Process form file field (input type="file").
                    String fieldName = item.getFieldName();
                    String fileName = FilenameUtils.getName(item.getName());
                    String fileType=FilenameUtils.getExtension(item.getName());
                    String contentType=item.getContentType();
                    logger.info("file type is: "+fileType);
                    logger.info("content type is: "+contentType);
                    if (contentType.equals("text/markdown")||contentType.equals("text/plain")||fileType.equals("md")
                            ||fileType.equals("markdown")|| fileType.equals("mdown")
                            ||(fileType.equals("mkdn")|| fileType.equals("mkd"))){
                        InputStream fileContent = item.getInputStream();
                        String text = IOUtils.toString(fileContent, StandardCharsets.UTF_8.name());
                        // ... (do your job here)
                        text=preProcessStr2SQLFormat(text);
                        if(text==null){
                            logger.info("failed to preprocess text");
                        }
                        article.setContent(text);
                    }else{
                        throw new WrongFileTypeException();
                    }

                }
            }
        }catch (FileUploadException e){
            logger.info("Cannot parse multipart request.");
            e.printStackTrace();
            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }



    /*
     * this is a preprocess function for PSql, because Psql use single
     * quote for string, so we need to escape all single quote with
     * double single quotes.
     * @param content: the content to process
     * @return  the modified string, if not able to convert, then return null.
     *
     */
    private static String preProcessStr2SQLFormat(String content){
        try {
            content=content.replace("'", "''");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return content;
    }


    /**
     * To convert the content queried from Psql, we need to transform single quote pairs to single quotes.
     * @param content string queried from Psql.
     * @return modified string, if not able to convert, then return null.
     *
     */
    private static String preProcessSQLFormat2Str(String content){
        try {
            content=content.replace("''", "'");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return content;
    }

}
