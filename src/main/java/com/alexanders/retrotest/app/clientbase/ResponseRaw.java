package com.alexanders.retrotest.app.clientbase;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Headers;

/**
 * Request/Response raw
 */
@Getter @Setter @Accessors(chain = true)
public class ResponseRaw {
    private Headers headers;
    private String body;
    private int code;
    private String requestUrl;
    //TODO getting cookies from here instead header using
}
