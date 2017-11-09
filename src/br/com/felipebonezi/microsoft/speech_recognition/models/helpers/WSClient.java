package br.com.felipebonezi.microsoft.speech_recognition.models.helpers;

import br.com.felipebonezi.microsoft.speech_recognition.models.classes.Audio;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.Language;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.SpeechRecognition;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.OutputFormat;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;
import br.com.felipebonezi.microsoft.speech_recognition.models.exceptions.MicrosoftException;
import com.oracle.javafx.jmx.json.JSONDocument;
import com.oracle.javafx.jmx.json.JSONFactory;
import com.oracle.javafx.jmx.json.JSONReader;

import java.io.File;
import java.io.StringReader;
import java.util.ResourceBundle;

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
        static final String DISPLAY_TEXT = "DisplayText";
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
        ResourceBundle bundle = ResourceBundle.getBundle("microsoft-config");
        if (bundle != null) {
            this.mSubscriptionKey = bundle.getString("microsoft.speechRecognition.subscriptionKey");
        }
    }

    public WSClient(String subscriptionKey) {
        this.mSubscriptionKey = subscriptionKey;
    }

    /**
     * This method is responsible of check all required fields and parameters before do anything - i.e. like recognize an audio file.
     * @param audio - Audio class.
     * @throws MicrosoftException - throw an exception if any field is missing or invalid.
     */
    private void checkRequirements(Audio audio) throws MicrosoftException {
        if (this.mSubscriptionKey == null)
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_SUBSCRIPTION_KEY, "You must use a valid 'Subscription Key' - see more at https://azure.microsoft.com/try/cognitive-services/");
        else if (audio == null)
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_AUDIO_CLASS, "The parameter 'audio' can't be null.");

        RecognitionMode recognitionMode = audio.getRecognitionMode();
        if (recognitionMode == null)
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_RECOGNITION_MODE, "The parameter 'audio.recognitionMode' can't be null.");

        Language language = audio.getLanguage();
        if (language == null)
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_LANGUAGE, "The parameter 'audio.language' can't be null.");

        if (!language.isSupported(recognitionMode))
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_LANGUAGE_NOT_SUPPORTED, String.format("The language isn't supported over the recognition mode (%s).", recognitionMode.name()));

        String languageTag = language.getTag();
        if (languageTag == null || languageTag.isEmpty())
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_LANGUAGE, "The parameter 'audio.language.tag' can't be null or empty.");

        OutputFormat format = audio.getOutputFormat();
        if (format == null)
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_OUTPUT_FORMAT, "The parameter 'audio.outputFormat' can't be null.");

        File audioFile = audio.getAudioFile();
        if (audioFile == null || !audioFile.exists())
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_AUDIO_FILE, "The parameter 'audio.audioFile' can't be null or doesn't exist.");
    }

    /**
     * This method is responsible of recognize an audio using `Microsoft Speech Recognition` API.
     * @param audio - Audio class.
     * @return SpeechRecognition class with all data returned by Microsoft API.
     * @throws MicrosoftException - throw and exception if any field is missing or invalid.
     */
    public SpeechRecognition recognize(Audio audio) throws MicrosoftException {
        checkRequirements(audio);

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
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_HTTP_RESULT, "The result is null or empty.");
        }

        JSONFactory instance = JSONFactory.instance();
        JSONReader jsonReader = instance.makeReader(new StringReader(result));
        JSONDocument jsonDocument = jsonReader.build();
        if (jsonDocument == null) {
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_JSON, "We can't parse the result as JSON.");
        }

        String status = jsonDocument.getString(Parameter.RECOGNITION_STATUS);
        if (status == null || !status.equalsIgnoreCase(Status.SUCCESS)) {
            throw new MicrosoftException(MicrosoftException.Code.ERROR_INVALID_JSON, "JSON hasn't 'status' parameter or is unsuccessfully result.");
        }

        Number offset = jsonDocument.getNumber(Parameter.OFFSET);
        Number duration = jsonDocument.getNumber(Parameter.DURATION);
        String text = jsonDocument.getString(Parameter.DISPLAY_TEXT);

        return new SpeechRecognition(offset.longValue(), duration.longValue(), text);
    }

}
