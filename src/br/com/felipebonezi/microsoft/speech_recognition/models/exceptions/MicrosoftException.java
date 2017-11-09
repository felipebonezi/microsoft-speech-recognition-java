package br.com.felipebonezi.microsoft.speech_recognition.models.exceptions;

public class MicrosoftException extends Exception {

    /**
     * All error codes used by the project.
     */
    public static final class Code {
        /**
         * Invalid HTTP request - i.e. An exception was throw from the request method.
         * Check if your connection is all right. Maybe the API is unavailable!
         */
        public static final int ERROR_INVALID_HTTP_REQUEST = 400;

        /**
         * Invalid HTTP result - i.e. An exception was throw from the result method.
         * Check if all parameters are fine. Maybe the API is unavailable!
         */
        public static final int ERROR_INVALID_HTTP_RESULT = 401;

        /**
         * The JSON returned by the API is invalid or with wrong format.
         */
        public static final int ERROR_INVALID_JSON = 500;

        /**
         * You must provide the `Subscription Key' using the `microsoft-config.properties` file or
         * initializing 'WSClient' using the second constructor 'WSClient("YOUR_SUBSCRIPTION_HERE")'.
         */
        public static final int ERROR_INVALID_SUBSCRIPTION_KEY = 501;

        /**
         * You must provide an `Audio File` to use `Microsoft Speech Recognition API`.
         */
        public static final int ERROR_INVALID_AUDIO_FILE = 502;

        /**
         * You must instantiate a valid `Audio` class.
         */
        public static final int ERROR_INVALID_AUDIO_CLASS = 503;

        /**
         * You must provide a valid `Recognition Mode`.
         */
        public static final int ERROR_INVALID_RECOGNITION_MODE = 504;

        /**
         * You must provide a valid `Language` class.
         */
        public static final int ERROR_INVALID_LANGUAGE = 505;

        /**
         * You must provide a supported `Language` for the specified `Recognition Mode`.
         */
        public static final int ERROR_INVALID_LANGUAGE_NOT_SUPPORTED = 506;

        /**
         * You must provide a valid `Output Format`.
         */
        public static final int ERROR_INVALID_OUTPUT_FORMAT = 507;
    }

    /**
     * Exception code mapped at `MicrosoftException.Code` static class.
     */
    private final int code;

    public MicrosoftException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
