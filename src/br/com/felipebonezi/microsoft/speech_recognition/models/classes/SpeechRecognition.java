package br.com.felipebonezi.microsoft.speech_recognition.models.classes;

public class SpeechRecognition {

    private final long offset;
    private final long duration;
    private final NBest nbest;

    public SpeechRecognition(long offset, long duration, NBest nbest) {
        this.offset = offset;
        this.duration = duration;
        this.nbest = nbest;
    }

    public long getOffset() {
        return offset;
    }

    public long getDuration() {
        return duration;
    }

    public NBest getNbest() {
        return nbest;
    }
}
