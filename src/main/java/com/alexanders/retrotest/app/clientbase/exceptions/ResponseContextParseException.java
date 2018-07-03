package com.alexanders.retrotest.app.clientbase.exceptions;

import com.alexanders.retrotest.app.clientbase.ResponseRaw;
import model.ErrorMessage;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author astolnikov: 09.04.2018
 */

/**
 * Custom exception class which can to use without try-catch block if this need your
 */
public class ResponseContextParseException extends RuntimeException {
    private String message;

    public <T> ResponseContextParseException addInfo(T something) {
        StringBuilder builder = new StringBuilder()
                .append(String.format("Can't parse something in %s: \n", something.getClass()));
        if (something instanceof ResponseRaw) {
            ResponseRaw raw = (ResponseRaw) something;
            message = builder
                    .append(String.format("---- Request url: %s\n", raw.getRequestUrl()))
                    .append(String.format("---- Response body: %s\n", raw.getBody()))
                    .append(String.format("---- Error code: %s\n", raw.getCode()))
                    .toString();
        } else if ((something instanceof ResponseBody)) {
            ResponseBody body = (ResponseBody) something;
            try {
                message = builder.append(String.format("---- Request body: %s", body.string())).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (something instanceof ErrorMessage) {
            ErrorMessage error = (ErrorMessage) something;
            message = String.format("\nBad response!\nresponse message: %s", error.getMessage());
        } else message = String.valueOf(something);
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
