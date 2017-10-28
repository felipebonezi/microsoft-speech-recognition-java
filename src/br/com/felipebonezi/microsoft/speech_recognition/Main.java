package br.com.felipebonezi.microsoft.speech_recognition;

import br.com.felipebonezi.microsoft.speech_recognition.models.classes.Audio;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.Language;
import br.com.felipebonezi.microsoft.speech_recognition.models.classes.SpeechRecognition;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.OutputFormat;
import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;
import br.com.felipebonezi.microsoft.speech_recognition.models.helpers.WSClient;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        WSClient wsClient = new WSClient();

        File file1 = new File("/Users/felipebonezi/Documents/GitHub/microsoft-speech-recognition-java/audios/audio1.wav");
        Language language1 = new Language(Language.IETFLanguageTag.PT_BR);
        Audio audio1 = new Audio(RecognitionMode.INTERACTIVE, language1, OutputFormat.DETAILED, file1);
        SpeechRecognition recognize1 = wsClient.recognize(audio1);

        File file2 = new File("/Users/felipebonezi/Documents/GitHub/microsoft-speech-recognition-java/audios/audio2.wav");
        Language language2 = new Language(Language.IETFLanguageTag.PT_BR);
        Audio audio2 = new Audio(RecognitionMode.INTERACTIVE, language2, OutputFormat.DETAILED, file2);
        SpeechRecognition recognize2 = wsClient.recognize(audio2);

        System.out.println("-------");
        System.out.printf("Text # 1: %s%n", recognize1.getText());
        System.out.println("-------");
        System.out.printf("Text # 2: %s%n", recognize2.getText());
        System.out.println("-------");
    }

}
