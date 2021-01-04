package darren.googlecloudtts.parameter;

/**
 * Author: Changemyminds.
 * Date: 2018/6/23.
 * Description:
 * Reference:
 */
public class VoiceSelectionParams {
    private String languageCode;
    private String name;
    private SsmlVoiceGender ssmlGender;

    public VoiceSelectionParams(String languageCode, String name) {
        this(languageCode, name, SsmlVoiceGender.SSML_VOICE_GENDER_UNSPECIFIED);
    }

    public VoiceSelectionParams(String languageCode, String name, SsmlVoiceGender eSSMLlGender) {
        this.languageCode = languageCode;
        this.name = name;
        this.ssmlGender = eSSMLlGender;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public String getName() {
        return name;
    }

    public SsmlVoiceGender getSsmlGender() {
        return ssmlGender;
    }
}
