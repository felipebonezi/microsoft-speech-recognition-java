package br.com.felipebonezi.microsoft.speech_recognition.models.classes;

import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.OutputFormat;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;

import java.io.File;

public class Audio {

    private final RecognitionMode recognitionMode;
    private final Language language;
    private final OutputFormat outputFormat;
    private File audioFile;

    public Audio(RecognitionMode recognitionMode, Language language, OutputFormat outputFormat) {
        this.recognitionMode = recognitionMode;
        this.language = language;
        this.outputFormat = outputFormat;
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
