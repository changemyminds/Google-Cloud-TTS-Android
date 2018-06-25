package darren.gcptts.tts.android;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Created by USER on 2018/6/25.
 */

public class AndroidTTS implements TextToSpeech.OnInitListener {
    private static final String TAG = AndroidTTS.class.getName();

    private TextToSpeech mTextToSpeech;
    private boolean mIsEnable;
    private AndroidVoice mAndroidVoice;

    public AndroidTTS(Context context) {
        mTextToSpeech = new TextToSpeech(context, this);
        mAndroidVoice = null;
    }

    public AndroidTTS(Context context, AndroidVoice androidVoice) {
        this(context);
        mAndroidVoice = androidVoice;
    }

    public void setAndroidVoice(AndroidVoice androidVoice) {
        mAndroidVoice = androidVoice;
    }

    void speak(String text) {
        if (mTextToSpeech != null && mIsEnable) {
            if (mAndroidVoice != null) {
                mTextToSpeech.setLanguage(mAndroidVoice.getLocale());
                mTextToSpeech.setPitch(mAndroidVoice.getPitch());
                mTextToSpeech.setSpeechRate(mAndroidVoice.getSpeakingRate());
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
            } else {
                mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    void stop() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
        }
    }

    void exit() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTextToSpeech.setSpeechRate(1.0f);
            int resultLang = mTextToSpeech.setLanguage(Locale.ENGLISH);
            mIsEnable = !(resultLang == TextToSpeech.LANG_MISSING_DATA ||
                    resultLang == TextToSpeech.LANG_NOT_SUPPORTED);
        } else {
            Log.e(TAG, "can't get the android tts library.");
            mIsEnable = false;
        }
    }


}
