package com.member.exception;

public class MemberNotFoundExceptionHandler extends RuntimeException {
    private static final long serialVersionUID = 1l;

    public MemberNotFoundExceptionHandler(String message) {
        super(message);
    }
}
