package com.example.do_an.model;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import com.example.do_an.model.ApiResponse;
import com.example.do_an.model.ChatRequest;


public interface GPTService {
        @POST("chat/completions")
        Call<ApiResponse> postChatCompletion(@Body ChatPayload payload);
    }
