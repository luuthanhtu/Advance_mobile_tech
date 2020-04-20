package util;

import android.app.Application;

public class GameReviewApi extends Application {
    private String username;
    private String userId;
    private static GameReviewApi instance;

    public static GameReviewApi getInstance() {
        if (instance == null)
            instance = new GameReviewApi();
        return instance;

    }

    public GameReviewApi(){}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
