package darren.gcptts.tts;

import java.util.ArrayList;
import java.util.List;

import darren.gcptts.tts.android.AndroidTTSAdapter;
import darren.gcptts.tts.gcp.GCPTTS;
import darren.gcptts.tts.gcp.GCPTTSAdapter;

/**
 * Created by USER on 2018/6/24.
 */

public class TextToSpeechManger {
    private ISpeech mSpeech;

    public TextToSpeechManger(GCPTTSAdapter gcpttsAdapter) {
        mSpeech = gcpttsAdapter;
    }

    public TextToSpeechManger(AndroidTTSAdapter androidTTSAdapter) {
        mSpeech = androidTTSAdapter;
    }

    public void speak(String text) {
        mSpeech.start(text);
    }

    public void pause() {
        mSpeech.pause();
    }

    public void resume() {
        mSpeech.resume();
    }

    public void stop() {
        mSpeech.stop();
    }

    public void exit() {
        mSpeech.exit();
    }
}
