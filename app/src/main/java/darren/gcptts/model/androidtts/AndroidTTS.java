package darren.gcptts.model.androidtts;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

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
        if (!mIsEnable) {

        }

        if (mTextToSpeech != null && mIsEnable) {
            if (mAndroidVoice != null) {
                if (!isSetAndroidVoiceEnable(mAndroidVoice)) {
                    String message = "can't set the value to tts android library";
                    Log.e(TAG, message);
//                    speakFailure(message);
                    return;
                }
            }

            int resultCode = mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);

            if (resultCode== TextToSpeech.ERROR) {
                // speak failed

            }

//            if (isSpeakFail) {
//                speakFailure("TextToSpeech.ERROR");
//            } else {
//                speakSuccess(text);
//            }
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
        return mTextToSpeech.setSpeechRate(androidVoice.getSpeakingRate()) != TextToSpeech.ERROR &&
                mTextToSpeech.setPitch(androidVoice.getPitch()) != TextToSpeech.ERROR &&
                mTextToSpeech.setLanguage(androidVoice.getLocale()) != TextToSpeech.LANG_MISSING_DATA &&
                mTextToSpeech.setLanguage(androidVoice.getLocale()) != TextToSpeech.LANG_NOT_SUPPORTED;
    }
}
