package com.alexanders.retrotest.api.services.people;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author astolnikov: 03.07.2018
 */
public interface PeopleInterface {

    @GET("people/{id}")
    Call<ResponseBody> get(@Path("id") int userId);
}
