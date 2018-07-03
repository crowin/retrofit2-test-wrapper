package com.alexanders.retrotest.app;

import com.alexanders.retrotest.app.clientbase.exceptions.ResponseContextParseException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import model.ErrorMessage;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
@Slf4j
public class ResponseContext <T,K> {
    @Getter private ResponseRaw response;
    @Getter private Request request;
    private T responseEntity;
    private K requestEntity;
    private Boolean isBadResponse = false;
    @Getter private ErrorMessage error;

    /**
     * Instantiate all fields for Context class
     * @param call retrofit authResponse
     * @param responseType responseEntity classType
     */
    public ResponseContext(Response<ResponseBody> call, Type responseType, Type requestType) {
        try {
            response = new ResponseRaw()
                    .setCode(call.code()).setHeaders(call.headers()).setRequestUrl(call.raw().request().url().toString());
            if (call.body() != null) {
                response.setBody(call.body().string());
            }
            else if (call.errorBody() != null) {
                response.setBody(call.errorBody().string());
                isBadResponse = true;
                log.error("Bad request\n {}", call.errorBody().string());
            }
            request = call.raw().request();
        } catch (IOException e) {
            e.getCause();
        }
        fromJsonBy(responseType, requestType);
    }

    /**
     * JSON deserialization to some responseEntity
     * @param responseType responseEntity classType
     */
    private void fromJsonBy(Type responseType, Type requestType) {
        JsonFormatter json = new JsonFormatter();
        try {
            if (isBadResponse) {
                error = new ErrorMessage(response.getBody());
                log.error("Bad request\n {}", response.getBody());
            } else {
                responseEntity = json.get(response.getBody(), responseType);
                if (request.body() != null) {
                    requestEntity = json.get(getJsonFromRequestBody(request.body()), requestType);
                }
            }
        } catch (Exception e) {
            throw new ResponseContextParseException().addInfo(response.getBody() + "\n" + e.getMessage());
        }
    }

    private String getJsonFromRequestBody(RequestBody body) {
        BufferedSink buffer = new Buffer();
        try {
            body.writeTo(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ((Buffer) buffer).readUtf8();
    }

    public Boolean isBadResponse() {
        return isBadResponse;
    }

    public T getResponseEntity() {
        if (responseEntity != null) {
            return responseEntity;
        } else throw new ResponseContextParseException().addInfo(this.getError());
    }

    public K getRequestEntity() {
        if (requestEntity != null) {
            return requestEntity;
        } else throw new ResponseContextParseException().addInfo(this.getError());
    }
}