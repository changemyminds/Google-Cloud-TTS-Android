package darren.gcptts.tts.gcp;

/**
 * Created by USER on 2018/6/23.
 */

public class Input {
    private String mText;
    private boolean mIsEnableSSML;

    Input(String text) {
        mText = text;
        mIsEnableSSML = false;
    }

    public Input(String text, boolean isSSML) {
        mText = text;
        mIsEnableSSML = isSSML;
    }

    @Override
    public String toString() {
        String text = "'" + this.getClass().getSimpleName().toLowerCase() + "':{";
        text += (mIsEnableSSML) ? "'ssml':'" + mText + "'}" : "'text':'" + mText + "'}";
        return text;
    }
}
