package br.com.felipebonezi.microsoft.speech_recognition.models.helpers;

import br.com.felipebonezi.microsoft.speech_recognition.models.exceptions.MicrosoftException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

class HttpConnector {

    /**
     * API url.
     */
    private String mUrl;

    /**
     * Request headers.
     */
    private Map<String, List<String>> mHeaders;

    private HttpConnector() {
        this.mHeaders = new HashMap<>();
    }

    /**
     * Initialize the connector using this class.
     * @return HttpConnector class.
     */
    static HttpConnector initialize() {
        return new HttpConnector();
    }

    /**
     * Configure the API url.
     * @param url - API url.
     * @return HttpConnector class.
     */
    HttpConnector url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * Add one header to the Map entity.
     * @param key - Header key.
     * @param value - Header value.
     * @return HttpConnector class.
     */
    HttpConnector addHeader(String key, String value) {
        addHeader(key, new String[] {value});
        return this;
    }

    /**
     * Add multiples values for one header to the Map entity.
     * @param key - Header key.
     * @param values - Header value.
     * @return HttpConnector class.
     */
    HttpConnector addHeader(String key, String... values) {
        this.mHeaders.put(key, Arrays.asList(values));
        return this;
    }

    /**
     * Make the POST request.
     * @param audioFile - Audio file.
     * @return String result from the request.
     * @throws MicrosoftException - throw an exception if any parameter is missing or invalid.
     */
    String post(File audioFile) throws MicrosoftException {
        HttpURLConnection urlConnection = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            URL url = new URL(this.mUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
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

            os = urlConnection.getOutputStream();
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

            is = urlConnection.getInputStream();

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
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_HTTP_REQUEST, "An error occurred over HTTP request.");
        } finally {
            try {
                if (os != null)
                    os.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

}
