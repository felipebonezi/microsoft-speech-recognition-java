package br.com.felipebonezi.microsoft.speech_recognition.models.classes;

import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.OutputFormat;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;

import java.io.File;

/**
 * Class to model the `Audio` request to use the Speech Recognition API.
 * All the parameters are required and need to have valid values to work.
 */
public class Audio {

    /**
     * Recognition mode of this audio.
     */
    private final RecognitionMode recognitionMode;

    /**
     * Language of this audio.
     * Default value is `EN_US`.
     */
    private final Language language;

    /**
     * Output format of this audio.
     * Default value is `SIMPLE`.
     */
    private final OutputFormat outputFormat;

    /**
     * File of this audio.
     * You must provide this if you want to use the API!
     */
    private File audioFile;

    public Audio(RecognitionMode recognitionMode) {
        this(recognitionMode, new Language(), OutputFormat.SIMPLE);
    }

    public Audio(RecognitionMode recognitionMode, OutputFormat outputFormat) {
        this(recognitionMode, new Language(), outputFormat);
    }

    public Audio(RecognitionMode recognitionMode, Language language) {
        this(recognitionMode, language, OutputFormat.SIMPLE);
    }

    public Audio(RecognitionMode recognitionMode, Language language, OutputFormat outputFormat) {
        this(recognitionMode, language, outputFormat, null);
    }

    public Audio(RecognitionMode recognitionMode, Language language, OutputFormat outputFormat, File audioFile) {
        this.recognitionMode = recognitionMode;
        this.language = language;
        this.outputFormat = outputFormat;
        this.audioFile = audioFile;
    }

    public RecognitionMode getRecognitionMode() {
        return recognitionMode;
    }

    public Language getLanguage() {
        return language;
    }

    public OutputFormat getOutputFormat() {
        return outputFormat;
    }

    public File getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(File audioFile) {
        this.audioFile = audioFile;
    }

}
