package com.example.demo_for_batch7.exception;

public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){
        super(message);
    }
    public BadApiRequest(){
        super("Bad Request !!");
    }
}
