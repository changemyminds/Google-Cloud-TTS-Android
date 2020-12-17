package darren.googlecloudtts.response;

/**
 * Author: Changemyminds.
 * Date: 2020/12/17.
 * Description:
 * Reference:
 */
public class SynthesizeResponse {
    private String audioContent;

    public void setAudioContent(String audioContent) {
        this.audioContent = audioContent;
    }

    public String getAudioContent() {
        return audioContent;
    }
}
