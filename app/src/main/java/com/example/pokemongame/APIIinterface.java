package com.example.pokemongame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIIinterface {

    @GET("api/v2/pokemon/{uid}")
    Call<UserModel> getNameData(@Path("uid") String uid);
}
