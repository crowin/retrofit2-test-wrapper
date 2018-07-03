package com.alexanders.retrotest.api.services.people;

import com.alexanders.retrotest.api.services.BaseClient;
import com.alexanders.retrotest.app.ResponseContext;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * @author astolnikov: 03.07.2018
 */
public class PeopleClient extends BaseClient {
    private PeopleInterface service = apiClient.create(PeopleInterface.class);

    public ResponseContext<PeopleEntity, String> get(int id) {
        Call<ResponseBody> callResponse = service.get(id);
        return getResponseContext(callResponse, PeopleEntity.class, String.class);
    }
}
