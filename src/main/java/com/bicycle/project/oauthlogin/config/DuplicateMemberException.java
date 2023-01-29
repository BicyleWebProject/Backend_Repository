package com.bicycle.project.oauthlogin.config;

public class DuplicateMemberException extends RuntimeException{
    public DuplicateMemberException() {
        super();
    }

    public DuplicateMemberException(String message) {
        super(message);
    }

    public DuplicateMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateMemberException(Throwable cause) {
        super(cause);
    }

}
