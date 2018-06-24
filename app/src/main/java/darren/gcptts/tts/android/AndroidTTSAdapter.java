package darren.gcptts.tts.android;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;

import darren.gcptts.tts.ISpeech;

import static android.os.Build.VERSION_CODES.LOLLIPOP;

/**
 * Created by USER on 2018/6/24.
 */

public class AndroidTTSAdapter implements ISpeech, TextToSpeech.OnInitListener {
    private TextToSpeech mTextToSpeech;
    private boolean mIsEnable;
    private AndroidVoice mAndroidVoice;

    public AndroidTTSAdapter(Context context) {
        mTextToSpeech = new TextToSpeech(context, this);
        mAndroidVoice = null;
    }

    public AndroidTTSAdapter(Context context, AndroidVoice androidVoice) {
        this(context);
        mAndroidVoice = androidVoice;
    }

    @Override
    public void start(String text) {
        if (mTextToSpeech == null && mIsEnable) return;

        if (mAndroidVoice != null) {
            mTextToSpeech.setLanguage(mAndroidVoice.getLocale());
            mTextToSpeech.setPitch(mAndroidVoice.getPitch());
            mTextToSpeech.setSpeechRate(mAndroidVoice.getSpeakingRate());
        }

        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (mTextToSpeech == null && mIsEnable) return;

        mTextToSpeech.stop();
    }

    @Override
    public void exit() {
        if (mTextToSpeech == null && mIsEnable) return;

        stop();
        mTextToSpeech.shutdown();
        mTextToSpeech = null;
    }

    @Override
    public void onInit(int status) {
        mIsEnable = (status == TextToSpeech.SUCCESS);
    }
}
