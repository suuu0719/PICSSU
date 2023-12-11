package com.example.picssu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("login")
    Call<LoginResponse> postLogin(
            @Body LoginRequest loginRequest);
}
