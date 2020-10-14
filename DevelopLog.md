### 2020-09-15

##### todo-list:

1. store blogs into database

2. login

3. multiple thread, message queue for posting

4. cookie

---


### 2020-09-27
##### todo-list
- design data schema for Article
- design a way to get article with url, upload article, edit article

###### problem
1. No setter found for the keyProperty 'com.Blog.Blog.id' in 'java.lang.Class'.

- This could bethe problem of insert casting problem in mybatis. Observing that the class is java.lang.Class, while our target class is Blog. So we need to find out how to configure the class.
- problem solved, this is becausethe session.insert(statement, parameter) needs 2 argument, while only statement is provided.

---

### 2020-09-30
##### Done
- design data schema for Article
- design a way to get article with url, upload article, edit article

##### todo-list
- now starting from frontend, find what api we need next. We have no clear target for next step, so we need come back to frontend to find out what we need next

---

### 2020-10-01

##### todo-list
1. need a configuration class to read config

##### problem
1. how to pass parameters with url, how to use js to generatesuch urls. 
Because for get requests, we can't put body in such requests. So we need to find fow to generate such urls.  

    - problem solved by using **querystring.stringify(data)**

2. the PageNav button couldn't update the blog list page.

    - problem solved by passing the getBlogList function of **Blog** to its child **PageNav**, 
    so that the button can trigger the blog update. Notice, in one component, we can't do setState in ComponentDidUpdate or
    ComponentWillUpdate, because those function will be triggered if setState is used, so setState trigger update, 
    and update do setState, and blah, infinite loop. 
    
### 2020-10-03
##### todo-list
1. need a configuration class to read config
2. we need an api for frontend to get som basic information, like how many posts in total, and the default blog count per page.
3. update the database so that the data can be used. to do this, we need a script to upload local blog files.
4. tag category maybe needed in the future


---
### 2020-10-05
##### todo-list
1. need a configuration class to read config
2. we need an api for frontend to get som basic information, like how many posts in total, and the default blog count per page.
3. update the database so that the data can be used. to do this, we need a script to upload local blog files.
4. tag category maybe needed in the future

current need:
PageNav

---
### 2020-10-06
PageNav done
##### problem
- PageNav need to be fixed with display style
- insert or update content of Article should not be done via cmd window, because the line discipline would change
the format of markdown. the best way to do it is via JDBC or by "psql -f file.sql" with sql script in file.sql.

---
### 2020-10-07
##### todo-list
1. design api for blog upload and blog update.


---
### 2020-10-08

##### problem
1. a significant processing period is observed, we need to find the solution.
2. logger module needs to be done.

----
### 2020-10-09
1. I used slj4log, it;s good enough for logging.
2. The cause for the processing delay is likely to be the frequent query via mybatis.
problem is highly likely because of the query cost of mybatis, I will finish upload part and end here.

---
### 2020-10-10
- I was stuck bu a problem about uploading a new blog, when I insert a new blog, the blogid should be generated from 
the database, which is psql, however, the url of that blog is constructed with base url and its blogid. 
So when inserting a new blog, the blogid has not be produced yet. 
There are 3 solutions:
1. use procedure, first insert the blog, and then update the url of this new blog.
2. use function, in psql there is a pgSQL, provides strong ability to process complex functions.
3. triggers, create a trigger update the url field after a new blog insertion.
 
the 3rd one seems best, but I solved it with solution2. 
``` sql
create or replace function insertBlog(author varchar(30), title varchar(30),
postdatetime timestamp, brief varchar(100), url varchar(200)) returns int
as $$
declare
newId int;
 BEGIN
 insert into Blogs ("author", "title", "postdatetime", "brief", "url") 
 	values(author, title, postdatetime, brief, url) 
 	returning id into newId;
 update blogs set url=concat('/Blog/Post/', newId) where blogs.id=newId;
 return newId;
 END
 $$ LANGUAGE plpgsql;
```
 and then use ```select insertBlog(argument)```  in mybatis can solve this problem with ease.
 One thing to notice, I used <select> block in mybatic for calling the function, because in cmd, I also
 need to used **select insertBlog(argument)** to call the function, and this funciton would return a result table. 
 So this function call can be regarded as a select opertion, and the returned value can also be retrived just 
 like select statement.
 
 
 ---
 
 ### 2020-10-12
 1. today I completed uploading local files part on frontend, and change the way of passing article from JSON to 
 FormData, so the backend would need some change to parse the request correctly.
 
 ##### problem
 - The frontend has changed its data type from JSON to FormData, so backend needs modification, too.
 - Reason for changing from JSON to FormData, FormData provides better confidentiality, even in browser, user can't see 
 contents of forms.
 - after capturing local packect, I'm sure that using json to post data would result in form data security problem.
 With FormData, the form data is encrypted. However, I think this simple encryption can be easily 
 
 ### 2020-10-13
 ##### Finished
 - upload api on backend is completed
 
 ##### Todo
 - front end needs to process the response form backend.
 - deploy
 - the unexpected lag of request should be given a solution.