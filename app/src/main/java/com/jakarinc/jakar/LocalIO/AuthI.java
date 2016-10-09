package com.jakarinc.jakar.LocalIO;

public interface AuthI {
    String getUserId();

    void logOut();


    void logIn(String userId);
}
