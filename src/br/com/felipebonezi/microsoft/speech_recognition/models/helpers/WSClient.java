package br.com.felipebonezi.microsoft.speech_recognition.models.helpers;

import br.com.felipebonezi.microsoft.speech_recognition.models.classes.*;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.OutputFormat;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;
import com.oracle.javafx.jmx.json.JSONDocument;
import com.oracle.javafx.jmx.json.JSONFactory;
import com.oracle.javafx.jmx.json.JSONReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Properties;

public class WSClient {

    /**
     * Recommended headers to setup for Microsoft Speech Recognition Service.
     */
    static final class Header {

        /**
         * Each time that you call the service, you must pass your subscription key in the Ocp-Apim-Subscription-Key header.
         * The Microsoft Speech Service also supports passing authorization token instead of subscription key.
         */
        static final String OCP_APIM_SUBSCRIPTION_KEY = "Ocp-Apim-Subscription-Key";

        /**
         * The Content-type field describes the format and codec of the audio stream.
         * Currently only wav file and PCM Mono 16000 encoding is supported, and the Content-type
         * value for this format is audio/wav; codec=audio/pcm; samplerate=16000.
         */
        static final String CONTENT_TYPE = "Content-Type";

        /**
         * The field Transfer-Encoding is optional.
         * Setting this field to chunked allows you to chop the audio into small chunks.
         */
        static final String TRANSFER_ENCODING = "Transfer-Encoding";

        /**
         * The field Accept is optional.
         * Setting this field to inform the Microsoft WS that you is allowed to Accept a specific response data.
         */
        static final String ACCEPT = "Accept";

    }

    /**
     * All parameters that returns on Http Request using `application/json` as `Accept` header.
     */
    static final class Parameter {
        static final String RECOGNITION_STATUS = "RecognitionStatus";
        static final String OFFSET = "Offset";
        static final String DURATION = "Duration";
        static final String N_BEST = "NBest";
        static final String CONFIDENCE = "confidence";
        static final String LEXICAL = "lexical";
        static final String ITN = "ITN";
        static final String MASKED_ITN = "MaskedITN";
        static final String DISPLAY = "Display";
    }

    /**
     * All API response status.
     */
    static final class Status {
        static final String SUCCESS = "Success";
    }

    /**
     * Base URL used to make requests to Speech Recognition API.
     */
    private static final String BASE_URL = "https://speech.platform.bing.com/speech/recognition/%s/cognitiveservices/v1?language=%s&format=%s";

    /**
     * Microsoft Subscription Key used to authenticate you on Microsoft WS.
     * This field is required!
     */
    private String mSubscriptionKey;

    public WSClient() {
        // TODO Refactor this code to load Properties file.
        Properties prop = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("conf/microsoft-config.properties");
        try {
            prop.load(stream);
            this.mSubscriptionKey = prop.getProperty("microsoft.speechRecognition.subscriptionKey");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WSClient(String subscriptionKey) {
        this.mSubscriptionKey = subscriptionKey;
    }

    private boolean checkRequirements(Audio audio) {
        // TODO Throws an Exception or not?
        if (this.mSubscriptionKey == null)
            return false;
        else if (audio == null)
            return false;

        RecognitionMode recognitionMode = audio.getRecognitionMode();
        if (recognitionMode == null)
            return false;

        Language language = audio.getLanguage();
        if (language == null)
            return false;

        if (!language.isSupported(recognitionMode))
            return false;

        String languageTag = language.getTag();
        if (languageTag == null || languageTag.isEmpty())
            return false;

        OutputFormat format = audio.getOutputFormat();
        if (format == null)
            return false;

        File audioFile = audio.getAudioFile();
        if (audioFile == null || !audioFile.exists())
            return false;

        return true;
    }

    public SpeechRecognition recognize(Audio audio) {
        if (!checkRequirements(audio)) {
            // TODO Throws an Exception or not?
            return null;
        }

        RecognitionMode recognitionMode = audio.getRecognitionMode();
        String recognitionModeNam = recognitionMode.name();

        Language language = audio.getLanguage();
        String languageTag = language.getTag();

        OutputFormat format = audio.getOutputFormat();
        String formatName = format.name();

        File audioFile = audio.getAudioFile();

        String url = String.format(BASE_URL, recognitionModeNam, languageTag, formatName);
        String result = HttpConnector.initialize()
                .url(url)
                .addHeader(Header.OCP_APIM_SUBSCRIPTION_KEY, this.mSubscriptionKey)
                .addHeader(Header.ACCEPT, "application/json")
                .addHeader(Header.TRANSFER_ENCODING, "chunked")
                .addHeader(Header.CONTENT_TYPE, "audio/wav", "codec=audio/pcm", "samplerate=16000")
                .post(audioFile);

        if (result == null || result.isEmpty()) {
            // TODO Throws an Exception or not?
            return null;
        }

        // TODO Check if WS returns as `text/xml` or `application/json`.
        JSONFactory instance = JSONFactory.instance();
        JSONReader jsonReader = instance.makeReader(new StringReader(result));

        JSONDocument jsonDocument = jsonReader.build();
        if (jsonDocument == null) {
            // TODO Throws an Exception or not?
            return null;
        }

        String status = jsonDocument.getString(Parameter.RECOGNITION_STATUS);
        if (status == null || !status.equalsIgnoreCase(Status.SUCCESS)) {
            // TODO Throws an Exception or not?
            return null;
        }

        Number offset = jsonDocument.getNumber(Parameter.OFFSET);
        Number duration = jsonDocument.getNumber(Parameter.DURATION);

        JSONDocument jsonNBest = jsonDocument.get(Parameter.N_BEST);
        Number confidence = jsonNBest.getNumber(Parameter.CONFIDENCE);
        String lexical = jsonNBest.getString(Parameter.LEXICAL);
        String itn = jsonNBest.getString(Parameter.ITN);
        String maskedITN = jsonNBest.getString(Parameter.MASKED_ITN);
        String display = jsonNBest.getString(Parameter.DISPLAY);

        NBest nbest = new NBest(confidence.doubleValue(), lexical, itn, maskedITN, display);
        return new SpeechRecognition(offset.longValue(), duration.longValue(), nbest);
    }

}
