package br.com.felipebonezi.microsoft.speech_recognition.models.classes;

/**
 * Class to model the result of HTTP request from Speech Recognition API.
 */
public class SpeechRecognition {

    private final long offset;
    private final long duration;
    private final String text;

    public SpeechRecognition(long offset, long duration, String text) {
        this.offset = offset;
        this.duration = duration;
        this.text = text;
    }

    public long getOffset() {
        return offset;
    }

    public long getDuration() {
        return duration;
    }

    public String getText() {
        return text;
    }

}
