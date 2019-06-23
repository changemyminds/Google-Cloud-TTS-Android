package darren.gcptts.model;

import java.util.ArrayList;
import java.util.List;
import darren.gcptts.model.gcp.GCPTTS;

/**
 * Author: Changemyminds.
 * Date: 2018/6/25.
 * Description:
 * Reference:
 */
public class GCPTTSAdapter extends GCPTTS implements ISpeech, GCPTTS.ISpeakListener {
    private List<ISpeechListener> mSpeechListeners;

    public GCPTTSAdapter() {
        mSpeechListeners = new ArrayList<>();
        addSpeakListener(this);
    }

    @Override
    public void start(String text) {
        super.start(text);
    }

    @Override
    public void resume() {
        super.resumeAudio();
    }

    @Override
    public void pause() {
        super.pauseAudio();
    }

    @Override
    public void stop() {
        super.stopAudio();
    }

    @Override
    public void exit() {
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
    public void onFailure(String message, Exception e) {
        for (ISpeechListener speechListener : mSpeechListeners) {
            speechListener.onFailure(message, e);
        }
    }
}
