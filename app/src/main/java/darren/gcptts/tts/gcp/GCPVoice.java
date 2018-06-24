package darren.gcptts.tts.gcp;

/**
 * Created by USER on 2018/6/23.
 */

public class GCPVoice {
    private String mLanguageCode;
    private String mName;
    private ESSMLlVoiceGender mESSMLlGender;
    private int mNaturalSampleRateHertz;

    public GCPVoice(String languageCode, String name) {
        mLanguageCode = languageCode;
        mName = name;
        mESSMLlGender = ESSMLlVoiceGender.NONE;
        mNaturalSampleRateHertz = 0;
    }

    public GCPVoice(String languageCode, String name, ESSMLlVoiceGender eSSMLlGender) {
        mLanguageCode = languageCode;
        mName = name;
        mESSMLlGender = eSSMLlGender;
        mNaturalSampleRateHertz = 0;
    }

    public GCPVoice(String languageCode, String name, ESSMLlVoiceGender eSSMLlGender,
                    int naturalSampleRateHertz) {
        mLanguageCode = languageCode;
        mName = name;
        mESSMLlGender = eSSMLlGender;
        mNaturalSampleRateHertz = naturalSampleRateHertz;
    }

    @Override
    public String toString() {
        String text = "'voice':{";
        text += "'languageCode':'" + mLanguageCode + "',";
        text += "'name':'" + mName + "'";
        text += (mESSMLlGender == ESSMLlVoiceGender.NONE) ? "" :
                ",'ssmlGender':'" + mESSMLlGender.toString() + "'";
        text += (mNaturalSampleRateHertz == 0) ? "" :
                ",'naturalSampleRateHertz':'" + String.valueOf(mNaturalSampleRateHertz) + "'";
        text += "}";
        return text;
    }

    public String getLanguageCode() {
        return mLanguageCode;
    }

    public String getName() {
        return mName;
    }

    public ESSMLlVoiceGender getESSMLlGender() {
        return mESSMLlGender;
    }

    public int getNaturalSampleRateHertz() {
        return mNaturalSampleRateHertz;
    }
}
