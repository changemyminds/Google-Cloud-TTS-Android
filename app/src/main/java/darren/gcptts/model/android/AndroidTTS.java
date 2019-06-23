package darren.gcptts.model.android;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Changemyminds.
 * Date: 2018/6/25.
 * Description:
 * Reference:
 */
public class AndroidTTS implements TextToSpeech.OnInitListener {
    private static final String TAG = AndroidTTS.class.getName();

    private TextToSpeech mTextToSpeech;
    private AndroidVoice mAndroidVoice;
    private List<ISpeakListener> mSpeakListeners = new ArrayList<>();
    private boolean mIsEnable;

    public AndroidTTS(Context context) {
        mIsEnable = false;
        mTextToSpeech = new TextToSpeech(context, this);
    }

    public AndroidTTS(Context context, AndroidVoice androidVoice) {
        this(context);
        mAndroidVoice = androidVoice;
    }

    public void setAndroidVoice(AndroidVoice androidVoice) {
        mAndroidVoice = androidVoice;
    }

    public void speak(String text) {
        if (mTextToSpeech != null && mIsEnable) {
            if (mAndroidVoice != null) {
                if (!isSetAndroidVoiceEnable(mAndroidVoice)) {
                    String message = "can't set the value to tts android library";
                    Log.e(TAG, message);
                    speakFailure(message);
                    return;
                }
            }

            boolean isSpeakFail;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                isSpeakFail = (mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null) == TextToSpeech.ERROR);
            } else {
                isSpeakFail = (mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null) == TextToSpeech.ERROR);
            }

            if (isSpeakFail) {
                speakFailure("TextToSpeech.ERROR");
            } else {
                speakSuccess(text);
            }
        }
    }

    public void stop() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
        }
    }

    public void exit() {
        if (mTextToSpeech != null) {
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
            mTextToSpeech = null;
        }
    }

    public void addSpeakListener(ISpeakListener speakListener) {
        mSpeakListeners.add(speakListener);
    }

    public void removeSpeakListener(ISpeakListener speakListener) {
        mSpeakListeners.remove(speakListener);
    }

    public void removeSpeakListener() {
        mSpeakListeners.clear();
    }

    private void speakSuccess(String message) {
        for (ISpeakListener speakListener : mSpeakListeners) {
            speakListener.onSuccess(message);
        }
    }

    private void speakFailure(String errorMessage) {
        for (ISpeakListener speakListener : mSpeakListeners) {
            speakListener.onFailure(errorMessage);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            if (mAndroidVoice == null) {
                mAndroidVoice = new AndroidVoice.Builder().build();
            }

            mIsEnable = isSetAndroidVoiceEnable(mAndroidVoice);
            if (!mIsEnable) {
                Log.e(TAG, "can't get the android tts library.");
            }
        } else {
            Log.e(TAG, "can't get the android tts library.");
            mIsEnable = false;
        }
    }

    private boolean isSetAndroidVoiceEnable(AndroidVoice androidVoice) {
        if (mTextToSpeech.setSpeechRate(androidVoice.getSpeakingRate()) == TextToSpeech.ERROR ||
                mTextToSpeech.setPitch(androidVoice.getPitch()) == TextToSpeech.ERROR ||
                mTextToSpeech.setLanguage(androidVoice.getLocale()) == TextToSpeech.LANG_MISSING_DATA ||
                mTextToSpeech.setLanguage(androidVoice.getLocale()) == TextToSpeech.LANG_NOT_SUPPORTED) {
            return false;
        }

        return true;
    }


    public interface ISpeakListener {
        void onSuccess(String message);

        void onFailure(String errorMessage);
    }

}
