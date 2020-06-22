package darren.googlecloudtts;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public enum EAudioEncoding {
    AUDIO_ENCODING_UNSPECIFIED("AUDIO_ENCODING_UNSPECIFIED"),
    LINEAR16("LINEAR16"),
    MP3("MP3"),
    OGG_OPUS("OGG_OPUS");

    String mDescription;

    EAudioEncoding(String description) {
        mDescription = description;
    }

    @Override
    public String toString() {
        return mDescription;
    }
}
