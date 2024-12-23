package com.shop.e_comerce.exeptions;

public class ResourceNotFoundExeption extends RuntimeException {
    public ResourceNotFoundExeption(String message) {
     super(message);
    }
}
