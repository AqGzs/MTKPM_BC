package com.example.do_an.DPattern;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.do_an.adapter.MessagesAdapter;
import com.example.do_an.model.ApiResponse;
import com.example.do_an.model.ChatPayload;
import com.example.do_an.model.GPTService;
import com.example.do_an.model.Message;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ChatRepository {
    private GPTService gptService;

    public ChatRepository() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request originalRequest = chain.request();
                    Request.Builder builder = originalRequest.newBuilder()
                            .header("Authorization", "Bearer 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") // Replace YOUR_API_KEY with your actual OpenAI API key
                            .method(originalRequest.method(), originalRequest.body());
                    Request newRequest = builder.build();
                    return chain.proceed(newRequest);
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                .build();

        gptService = retrofit.create(GPTService.class);
    }

    public interface ResponseCallback {
        void onResponse(String response);
        void onFailure(String error);
    }

    public void getResponseFromGPT(String userInput, final ResponseCallback callback) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "You are a helpful assistant.", false));
        messages.add(new Message("user", "Hello!", true));

        ChatPayload payload = new ChatPayload("gpt-3.5-turbo", messages);


        Call<ApiResponse> call = gptService.postChatCompletion(payload);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String textResponse = apiResponse.getChoices().get(0).getText();
                    callback.onResponse(textResponse);
                } else {
                    // Handle unsuccessful response, potentially parsing error body
                    callback.onFailure("Received an unsuccessful response.");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle network errors
                callback.onFailure("Network error or serialization issue: " + t.getMessage());
            }
        });
    }
}


