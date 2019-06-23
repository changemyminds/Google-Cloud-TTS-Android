package darren.gcptts.model;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import darren.gcptts.model.android.AndroidTTS;

/**
 * Author: Changemyminds.
 * Date: 2018/6/24.
 * Description:
 * Reference:
 */
public class AndroidTTSAdapter extends AndroidTTS implements ISpeech, AndroidTTS.ISpeakListener {
    private List<ISpeechListener> mSpeechListeners;

    public AndroidTTSAdapter(Context context) {
        super(context);
        mSpeechListeners = new ArrayList<>();
        addSpeakListener(this);
    }

    @Override
    public void start(String text) {
        speak(text);
    }

    @Override
    public void resume() {
        // not implements
    }

    @Override
    public void pause() {
        // not implements
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void exit() {
        // release resources
        super.exit();
        removeSpeakListener(this);
        mSpeechListeners.clear();
    }

    @Override
    public void addSpeechListener(ISpeechListener speechListener) {
        mSpeechListeners.add(speechListener);
    }

    @Override
    public void removeSpeechListener(ISpeechListener speechListener) {
        mSpeechListeners.remove(speechListener);
    }

    @Override
    public void onSuccess(String message) {
        for (ISpeechListener speechListener : mSpeechListeners) {
            speechListener.onSuccess(message);
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        for (ISpeechListener speechListener : mSpeechListeners) {
            speechListener.onFailure(null, new Exception(errorMessage));
        }
    }
}
