package com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class JsonplaceholderServiceFactory {
    public static JsonplaceholderService Create(){

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        return retrofit.create(JsonplaceholderService.class);
    }
}
