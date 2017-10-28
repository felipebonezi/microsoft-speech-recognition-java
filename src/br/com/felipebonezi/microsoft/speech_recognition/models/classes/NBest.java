package br.com.felipebonezi.microsoft.speech_recognition.models.classes;

public class NBest {

    private final double confidence;
    private final String lexical;
    private final String itn;
    private final String maskedITN;
    private final String display;

    public NBest(double confidence, String lexical, String itn, String maskedITN, String display) {
        this.confidence = confidence;
        this.lexical = lexical;
        this.itn = itn;
        this.maskedITN = maskedITN;
        this.display = display;
    }

    public Number getConfidence() {
        return confidence;
    }

    public String getLexical() {
        return lexical;
    }

    public String getItn() {
        return itn;
    }

    public String getMaskedITN() {
        return maskedITN;
    }

    public String getDisplay() {
        return display;
    }
}
