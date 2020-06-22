package darren.googlecloudtts;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public enum ESSMLlVoiceGender {
    SSML_VOICE_GENDER_UNSPECIFIED,
    MALE,
    FEMALE,
    NEUTRAL,
    NONE;

    public static ESSMLlVoiceGender convert(String ssmlGender) {
        if (ssmlGender.compareTo(SSML_VOICE_GENDER_UNSPECIFIED.toString()) == 0) {
            return SSML_VOICE_GENDER_UNSPECIFIED;
        }
        if (ssmlGender.compareTo(MALE.toString()) == 0) {
            return MALE;
        }
        if (ssmlGender.compareTo(FEMALE.toString()) == 0) {
            return FEMALE;
        } else if (ssmlGender.compareTo(NEUTRAL.toString()) == 0) {
            return NEUTRAL;
        } else {
            return NONE;
        }
    }
}
