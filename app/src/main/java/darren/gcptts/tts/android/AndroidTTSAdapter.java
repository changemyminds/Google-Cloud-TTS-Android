package darren.gcptts.tts.android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import darren.gcptts.tts.ISpeech;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by USER on 2018/6/24.
 */

public class AndroidTTSAdapter implements ISpeech {
    private AndroidTTS mAndroidTTS;

    public AndroidTTSAdapter(AndroidTTS androidTTS) {
        mAndroidTTS = androidTTS;
    }

    @Override
    public void start(String text) {
        mAndroidTTS.speak(text);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        mAndroidTTS.stop();
    }

    @Override
    public void exit() {
        mAndroidTTS.exit();
    }
}
