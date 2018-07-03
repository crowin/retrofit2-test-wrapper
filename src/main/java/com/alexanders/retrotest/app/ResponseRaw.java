package com.alexanders.retrotest.app;

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
}
