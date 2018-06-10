package com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonplaceholderService {
    @GET("users")
    Observable<List<User>> getUsers();

    @GET("posts")
    Observable<List<Post>> getUserPosts(@Query("userId") int userId);
}
