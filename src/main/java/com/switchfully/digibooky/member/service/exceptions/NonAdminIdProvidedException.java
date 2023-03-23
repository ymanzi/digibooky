package com.switchfully.digibooky.member.service.exceptions;

public class NonAdminIdProvidedException extends RuntimeException{

    public NonAdminIdProvidedException(String message) {
        super(message);
    }
}
