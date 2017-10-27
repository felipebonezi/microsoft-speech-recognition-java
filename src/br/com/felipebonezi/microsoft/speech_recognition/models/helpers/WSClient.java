package br.com.felipebonezi.microsoft.speech_recognition.models.helpers;

import br.com.felipebonezi.microsoft.speech_recognition.models.classes.Audio;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.AudioConfig;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.Language;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.SpeechRecognition;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.OutputFormat;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;
import com.oracle.javafx.jmx.json.JSONDocument;
import com.oracle.javafx.jmx.json.JSONFactory;
import com.oracle.javafx.jmx.json.JSONReader;

import java.io.StringReader;

public class WSClient {

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

    }

    private static final String BASE_URL = "https://speech.platform.bing.com/speech/recognition/%s/cognitiveservices/v1?language=%s&format=%s";

    private final String mSubscriptionKey;
    private final HttpConnector mHttpConnector;

    public WSClient() {
        // TODO Get `SUBSCRIPTION KEY` from `microsoft-config.properties` file.
        this.mSubscriptionKey = null;
        this.mHttpConnector = new HttpConnector();
    }

    public void recognize(Audio audio) {
        recognize(audio, AudioConfig.initializeDefault());
    }

    public SpeechRecognition recognize(Audio audio, AudioConfig config) {
        if (audio == null) {
            return null;
        }

        if (config == null) {
            return null;
        }

        RecognitionMode recognitionMode = audio.getRecognitionMode();
        String recognitionModeNam = recognitionMode.name();

        Language language = audio.getLanguage();
        String languageTag = language.getTag();

        OutputFormat format = audio.getOutputFormat();
        String formatName = format.name();

        String url = String.format(BASE_URL, recognitionModeNam, languageTag, formatName);
        String result = this.mHttpConnector.url(url)
                .addHeader()
                .addHeader()
                .addHeader()
                .post();

        if (result == null || result.isEmpty()) {
            return null;
        }

        JSONFactory instance = JSONFactory.instance();
        JSONReader jsonReader = instance.makeReader(new StringReader(result));
        JSONDocument jsonDocument = jsonReader.build();

        // TODO Read JSON result and parse to `SpeechRecognition` class.

        SpeechRecognition speechRecognition = new SpeechRecognition();
        return speechRecognition;
    }

}
