package com.shop.e_comerce.exeptions;

public class AlreadyExistExeption extends RuntimeException{
    public AlreadyExistExeption(String message){
        super(message);
    }
}
