package com.util;


public class PATH {
    public static class Web {
        public static final String INDEX = "/index/";
        public static final String LOGIN = "/login/";
        public static final String LOGOUT = "/logout/";

        public static final String BOOKS = "/books/";
        public static final String ONE_BOOK = "/books/:isbn/";

        public static final String BLOGS = "/blogs";
        public static final String ONE_BLOG="/blogs/:blogid";
    }
    public static class API{
        public static final String INDEX = "api/index/";
        public static final String LOGIN = "api/login/";
        public static final String LOGOUT = "api/logout/";

        public static final String BLOGINFO="api/blogs/bloginfo/";
        public static final String BLOGS = "api/blogs/";
        public static final String ONE_BLOG="api/blogs/:blogid/";

        public static final String ONE_ARTICLE="api/blogs/:blogid/";


    }
}
