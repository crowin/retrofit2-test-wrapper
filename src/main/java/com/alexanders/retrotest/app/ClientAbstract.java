package com.alexanders.retrotest.app;

import com.alexanders.retrotest.app.clientbase.exceptions.ResponseContextParseException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;

/**
 * Base abstract class for all rest clients
 */
@Slf4j
public abstract class ClientAbstract {
    protected String currentAuthCookies;
    protected Retrofit apiClient;
    protected JsonFormatter json = new JsonFormatter();

    public ClientAbstract(ClientBuilderInterface builder) {
        apiClient = new Retrofit.Builder()
                .baseUrl(builder.getBaseUrl())
                .client(new HttpConnectionHelper().getOkHttpClient(builder.isUsedSSL()))
                .addConverterFactory(GsonConverterFactory.create(json.getGson()))
                .build();
    }

    /**
     *
     * @param responseBody
     * @param responseType entity type
     * @param <T> entity type which equals type
     * @return custom response
     */
    protected  <T, K> ResponseContext<T, K> getResponseContext(Call<ResponseBody> responseBody, Type responseType, Type requestType) {
        Response<ResponseBody> call;
        try {
            call = responseBody.execute();
        } catch (Exception e) {
            log.error("Response wasn't parse.\nRequest body:\n{}\nError:\n{}", responseBody.request().body().toString(), e.getMessage());
            throw new ResponseContextParseException().addInfo(responseBody);
        }
        return new ResponseContext<>(call, responseType, requestType);
    }

    /**
     * First, it authorization performs. After that, it cookies extracts and returns
     * @param cookies
     * @return
     */
    protected String setCookies(String cookies) {
        if (!currentAuthCookies.equals(cookies)) {
            currentAuthCookies = cookies;
        }
        return currentAuthCookies;
    }
}
