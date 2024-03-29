package com.t3t.bookstoreapi.keymanager;

public class SecretKeyManagerApiRequestFailedException extends RuntimeException{
    public SecretKeyManagerApiRequestFailedException(String message) {
        super(message);
    }
}
