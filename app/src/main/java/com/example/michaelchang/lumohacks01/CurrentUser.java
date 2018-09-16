package com.example.michaelchang.lumohacks01;

public class CurrentUser {
    private String username;

    private static CurrentUser instance;
    private CurrentUser() {
        // No way to instantiate outside of CurrentUser class
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
