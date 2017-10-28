package br.com.felipebonezi.microsoft.speech_recognition.models.helpers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HttpConnector {

    private Map<String, List<String>> mHeaders;

    private HttpConnector() {
        this.mHeaders = new HashMap<>();
    }

    public static HttpConnector initialize() {
        return new HttpConnector();
    }

    public HttpConnector url(String url) {
        // TODO Make HTTP request using `POST` method.
        return this;
    }

    public HttpConnector addHeader(String key, String value) {
        // TODO Map the header into a `Map`.
        return this;
    }

    public HttpConnector addHeader(String key, String... values) {
        // TODO Map the header into a `Map`.
        return this;
    }

    public String post(File audioFile) {
        // TODO Make the request and get the string response.
        return null;
    }
}
