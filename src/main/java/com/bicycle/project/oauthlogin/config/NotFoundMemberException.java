package com.bicycle.project.oauthlogin.config;

public class NotFoundMemberException extends RuntimeException{
    public NotFoundMemberException(){
        super();
    }
    public NotFoundMemberException(String msg, Throwable cause){
        super(msg, cause);
    }
    public NotFoundMemberException(String msg){
        super(msg);
    }
    public NotFoundMemberException(Throwable cause){
        super(cause);
    }
}
