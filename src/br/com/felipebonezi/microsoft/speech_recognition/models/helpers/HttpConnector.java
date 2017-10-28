package br.com.felipebonezi.microsoft.speech_recognition.models.helpers;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

class HttpConnector {

    private String mUrl;
    private Map<String, List<String>> mHeaders;

    // TODO Check `Builder` pattern.
    private HttpConnector() {
        this.mHeaders = new HashMap<>();
    }

    public static HttpConnector initialize() {
        return new HttpConnector();
    }

    public HttpConnector url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpConnector addHeader(String key, String value) {
        addHeader(key, value);
        return this;
    }

    public HttpConnector addHeader(String key, String... values) {
        this.mHeaders.put(key, Arrays.asList(values));
        return this;
    }

    public String post(File audioFile) {
        // TODO Make the request and get the string response.
        try {
            URL url = new URL(this.mUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");

            Set<String> keys = this.mHeaders.keySet();
            for (String key : keys) {
                List<String> values = this.mHeaders.get(key);

                StringBuilder builder = new StringBuilder();
                for (int i = 0;i < values.size();i++) {
                    builder.append(values.get(i));
                    if (i != values.size() - 1) {
                        // Header separator - e.g. "application/json; charset=utf-8"
                        builder.append("; ");
                    }
                }

                urlConnection.setRequestProperty(key, builder.toString());
            }
            urlConnection.connect();

            OutputStream os = urlConnection.getOutputStream();
            FileInputStream fis = new FileInputStream(audioFile);
            BufferedInputStream bis = new BufferedInputStream(fis);

            int bytesReaded;
            int bufferSize = 1024;
            byte[] fileBuffer = new byte[bufferSize];
            while ((bytesReaded = bis.read(fileBuffer)) > 0) {
                os.write(fileBuffer, 0, bytesReaded);
                os.flush();
            }
            os.close();

            InputStream is = urlConnection.getInputStream();

            final char[] buffer = new char[bufferSize];
            final StringBuilder out = new StringBuilder();
            try (Reader in = new InputStreamReader(is, "UTF-8")) {
                // TODO Check if `CHINESE` letters can be read as `UTF-8`.
                for (;;) {
                    int rsz = in.read(buffer, 0, buffer.length);
                    if (rsz < 0)
                        break;
                    out.append(buffer, 0, rsz);
                }
            }
            is.close();
            return out.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
