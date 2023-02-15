package com.member.exception;

public class DependentNotFoundExceptionHandler extends RuntimeException{
    private static final long serialVersionUID = 1l;
    public DependentNotFoundExceptionHandler(String message) {
        super(message);
    }
}
