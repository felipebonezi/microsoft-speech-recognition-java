package br.com.felipebonezi.microsoft.speech_recognition.models.classes;

import br.com.felipebonezi.microsoft.speech_recognition.models.enumerates.RecognitionMode;

import java.util.*;

public class Language {

    /**
     * The Microsoft speech recognition API supports the following languages.
     * An IETF language tag is an abbreviated language code (for example, en for English, pt-BR for Brazilian Portuguese,
     * or nan-Hant-TW for Min Nan Chinese as spoken in Taiwan using traditional Han characters) defined by
     * the Internet Engineering Task Force (IETF) in the BCP 47 document series, which is currently composed of
     * normative RFC 5646 (referencing the related RFC 5645) and RFC 4647, along with the normative content
     * of the IANA Language Subtag Registry.[1][2][3][4] Components of language tags are drawn from
     * ISO 639, ISO 15924, ISO 3166-1, and UN M.49.
     */
    public static final class IETFLanguageTag {
        public static final String PT_BR = "pt-BR";

        public static final String AR_EG = "ar-EG";
        public static final String CA_ES = "ca-ES";
        public static final String DE_DE = "de-DE";
        public static final String DA_DK = "da-DK";
        public static final String EN_AU = "en-AU";
        public static final String EN_CA = "en-CA";
        public static final String EN_GB = "en-GB";
        public static final String EN_IN = "en-IN";
        public static final String EN_NZ = "en-NZ";
        public static final String EN_US = "en-US";
        public static final String ES_ES = "es-ES";
        public static final String ES_MX = "es-MX";
        public static final String FI_FI = "fi-FI";
        public static final String FR_FR = "fr-FR";
        public static final String HI_IN = "hi-IN";
        public static final String IT_IT = "it-IT";
        public static final String JA_JP = "ja-JP";
        public static final String KO_KR = "ko-KR";
        public static final String NB_NO = "nb-NO";
        public static final String NL_NL = "nl-NL";
        public static final String PL_PL = "pl-PL";
        public static final String RU_RU = "ru-RU";
        public static final String SV_SE = "sv-SE";
        public static final String ZH_CN = "zh-CN";
        public static final String ZH_HK = "zh-HK";
        public static final String ZH_TW = "zh-TW";
    }

    private static final Map<RecognitionMode, List<String>> SUPPORTED_LANGUAGES;

    static {
        SUPPORTED_LANGUAGES = new HashMap<>();
        SUPPORTED_LANGUAGES.put(RecognitionMode.INTERACTIVE, Arrays.asList(
                IETFLanguageTag.AR_EG, IETFLanguageTag.CA_ES, IETFLanguageTag.DA_DK, IETFLanguageTag.EN_AU,
                IETFLanguageTag.EN_CA, IETFLanguageTag.EN_GB, IETFLanguageTag.EN_IN, IETFLanguageTag.EN_NZ,
                IETFLanguageTag.EN_US, IETFLanguageTag.ES_ES, IETFLanguageTag.ES_MX, IETFLanguageTag.FI_FI,
                IETFLanguageTag.FR_FR, IETFLanguageTag.HI_IN, IETFLanguageTag.IT_IT, IETFLanguageTag.JA_JP,
                IETFLanguageTag.PT_BR, IETFLanguageTag.KO_KR, IETFLanguageTag.NB_NO, IETFLanguageTag.NL_NL,
                IETFLanguageTag.PL_PL, IETFLanguageTag.RU_RU, IETFLanguageTag.SV_SE, IETFLanguageTag.ZH_CN,
                IETFLanguageTag.ZH_HK, IETFLanguageTag.ZH_TW
        ));
        SUPPORTED_LANGUAGES.put(RecognitionMode.DICTATION, Arrays.asList(
                IETFLanguageTag.AR_EG, IETFLanguageTag.CA_ES, IETFLanguageTag.DA_DK, IETFLanguageTag.EN_AU,
                IETFLanguageTag.EN_CA, IETFLanguageTag.EN_GB, IETFLanguageTag.EN_IN, IETFLanguageTag.EN_NZ,
                IETFLanguageTag.EN_US, IETFLanguageTag.ES_ES, IETFLanguageTag.ES_MX, IETFLanguageTag.FI_FI,
                IETFLanguageTag.FR_FR, IETFLanguageTag.HI_IN, IETFLanguageTag.IT_IT, IETFLanguageTag.JA_JP,
                IETFLanguageTag.PT_BR, IETFLanguageTag.KO_KR, IETFLanguageTag.NB_NO, IETFLanguageTag.NL_NL,
                IETFLanguageTag.PL_PL, IETFLanguageTag.RU_RU, IETFLanguageTag.SV_SE, IETFLanguageTag.ZH_CN,
                IETFLanguageTag.ZH_HK, IETFLanguageTag.ZH_TW
        ));
        SUPPORTED_LANGUAGES.put(RecognitionMode.CONVERSATION, Arrays.asList(
                IETFLanguageTag.AR_EG, IETFLanguageTag.DE_DE, IETFLanguageTag.EN_US, IETFLanguageTag.ES_ES,
                IETFLanguageTag.FR_FR, IETFLanguageTag.IT_IT, IETFLanguageTag.JA_JP, IETFLanguageTag.PT_BR,
                IETFLanguageTag.RU_RU, IETFLanguageTag.ZH_CN
        ));
    }

    private final String tag;

    public Language() {
        this(IETFLanguageTag.EN_US);
    }

    public Language(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public boolean isSupported(RecognitionMode recognitionMode) {
        List<String> set = SUPPORTED_LANGUAGES.get(recognitionMode);
        return set.contains(this.tag);
    }

}
